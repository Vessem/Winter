package net.vessem.winter.services

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import net.vessem.winter.dto.User
import net.vessem.winter.exception.BadRequestException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.client.RestClientException
import org.springframework.web.client.RestTemplate

@Service
class AuthService {
	@Autowired
	private lateinit var userService: UserService

	fun getGoogleAuthToken(code: String): Map<String, Any> {
		val restTemplate = RestTemplate()
		val httpHeaders = HttpHeaders()
		httpHeaders.contentType = MediaType.APPLICATION_FORM_URLENCODED

		val params = LinkedMultiValueMap<String, String>()
		params.add("code", code)
		params.add("redirect_uri", System.getenv("GOOGLE_CALLBACK"))
		params.add("client_id", System.getenv("GOOGLE_CLIENT_ID"))
		params.add("client_secret", System.getenv("GOOGLE_CLIENT_SECRET"))
		params.add("scope", "https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.profile")
		params.add("scope", "https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email")
		params.add("scope", "openid")
		params.add("grant_type", "authorization_code")

		val requestEntity = HttpEntity<MultiValueMap<String, String>>(params, httpHeaders)
		try {
			val response =
				restTemplate.postForObject("https://oauth2.googleapis.com/token", requestEntity, String::class.java)

			return Gson().fromJson(
				response, object : TypeToken<HashMap<String?, Any?>?>() {}.type
			)
		} catch (e: RestClientException) {
			throw BadRequestException(e)
		}
	}

	fun getProfileFromGoogle(accessToken: String): User {
		val restTemplate = RestTemplate()
		val httpHeaders = HttpHeaders()
		httpHeaders.setBearerAuth(accessToken)

		val requestEntity = HttpEntity<String>(httpHeaders)

		try {
			val response = restTemplate.exchange(
				"https://www.googleapis.com/oauth2/v2/userinfo", HttpMethod.GET, requestEntity,
				String::class.java
			)
			val jsonObject = Gson().fromJson(response.body, JsonObject::class.java)
			return userService.getOrCreateNewUser(jsonObject)
		} catch (e: RestClientException) {
			throw BadRequestException(e)
		}
	}
}