package net.vessem.winter.controller

import net.vessem.winter.dto.User
import net.vessem.winter.exception.BadRequestException
import net.vessem.winter.repository.UserRepository
import net.vessem.winter.services.AuthService
import net.vessem.winter.services.JwtService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.CookieValue
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.servlet.view.RedirectView

@Controller
@RequestMapping("/auth")
class AuthController {
	@Autowired
	private lateinit var authService: AuthService

	@Autowired
	private lateinit var jwtService: JwtService

	@Autowired
	private lateinit var userRepository: UserRepository

	@GetMapping("refresh")
	fun authUserWithRefresh(@CookieValue("refresh_token") refreshToken: String): ResponseEntity<Unit> {
		try {
			if (refreshToken.isBlank()) throw BadRequestException("Token cannot be empty")
			if (!jwtService.isRefreshToken(refreshToken)) throw BadRequestException("Token passed is not a valid refresh token")

			val userId = jwtService.getUserIdFromToken(refreshToken) ?: throw BadRequestException("Token has no subject")
			val userEntity = userRepository.getUserEntityById(userId)

			if (userEntity.isEmpty) throw BadRequestException("Invalid token subject")

			val user = User.fromEntity(userEntity.get())

			if (!jwtService.isTokenValid(refreshToken, user)) throw BadRequestException("Invalid refresh token")

			val headers = HttpHeaders().also {
				it.addAll((ResponseEntity.ok(user).headers))
				it.set(HttpHeaders.SET_COOKIE, "token=" + jwtService.generateToken(user))
				it.set(HttpHeaders.SET_COOKIE, "refresh_token=" + jwtService.generateRefreshToken(user))
			}

			return ResponseEntity.status(HttpStatus.OK).headers(headers).build()
		} catch (e: BadRequestException) {
			return ResponseEntity.badRequest().build()
		}
	}

	@GetMapping("refresh/new")
	fun getRefreshTokenWithJwt(@CookieValue("token") token: String): ResponseEntity<Unit> {
		try {
			if (jwtService.isRefreshToken(token)) throw BadRequestException("Token cannot be a refresh token")

			val userId = jwtService.getUserIdFromToken(token) ?: throw BadRequestException("Token has no subject")
			val userEntity = userRepository.getUserEntityById(userId)

			if (userEntity.isEmpty) throw BadRequestException("Invalid token subject")

			val user = User.fromEntity(userEntity.get())

			if (!jwtService.isTokenValid(token, user)) throw BadRequestException("Invalid token")

			val headers = HttpHeaders().also {
				it.addAll((ResponseEntity.ok(user).headers))
				it.set(HttpHeaders.SET_COOKIE, "refresh_token=" + jwtService.generateRefreshToken(user))
			}

			return ResponseEntity.status(HttpStatus.OK).headers(headers).build()
		} catch (e: BadRequestException) {
			return ResponseEntity.badRequest().build()
		}
	}

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
			if (tokens.isEmpty()) throw BadRequestException("No token from google")

			val user = authService.getProfileFromGoogle(tokens.getOrDefault("access_token", "").toString())

			val response = ResponseEntity.ok(user)
			val headers = HttpHeaders().also {
				it.addAll(response.headers)
				it.set(HttpHeaders.SET_COOKIE, "token=" + jwtService.generateToken(user))
			}

			return ResponseEntity.status(HttpStatus.OK).headers(headers)
				.body(user) // Create new response entity because you can't reset the headers

		} catch (e: BadRequestException) {
			return ResponseEntity.badRequest().build()
		} catch (e: Exception) {
			return ResponseEntity.internalServerError().build()
		}

	}
}