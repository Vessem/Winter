package net.vessem.winter.controller

import net.vessem.winter.dto.User
import net.vessem.winter.exception.BadRequestException
import net.vessem.winter.services.AuthService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.servlet.view.RedirectView

@Controller
@RequestMapping("/auth")
class AuthController {

	@Autowired
	private lateinit var authService: AuthService

	@GetMapping("/google")
	fun authUserWithGoogle(): RedirectView {
		return RedirectView(
			"https://accounts.google.com/o/oauth2/v2/auth?redirect_uri=${System.getenv("GOOGLE_CALLBACK")}&response_type=code&client_id=${
				System.getenv(
					"GOOGLE_CLIENT_ID"
				)
			}&scope=https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.profile+openid&access_type=offline"
		)
	}

	@GetMapping("/google/callback")
	fun googleAuthCallback(
		@RequestParam("code") code: String,
		@RequestParam("scope") scope: String,
		@RequestParam("authuser") authUser: String,
		@RequestParam("prompt") prompt: String
	): ResponseEntity<User> {
		try {
			val tokens = authService.getGoogleAuthToken(code)
			val user = authService.getProfileFromGoogle(tokens.getOrDefault("access_token", "").toString())

			val response = ResponseEntity.ok(user)
			val headers = HttpHeaders()
			headers.addAll(response.headers)
			headers.setBearerAuth(tokens.getOrDefault("id_token", "").toString().replace("\"", ""))
			return ResponseEntity.status(HttpStatus.OK).headers(headers)
				.body(user) // Create new response entity because you can't reset the headers
		} catch (e: BadRequestException) {
			return ResponseEntity.badRequest().build()
		} catch (e: Exception) {
			return ResponseEntity.internalServerError().build()
		}

	}
}