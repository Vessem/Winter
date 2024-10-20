package net.vessem.winter.controller

import net.vessem.winter.repository.UserRepository
import net.vessem.winter.services.AuthService
import net.vessem.winter.services.JwtService
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@ExtendWith(SpringExtension::class)
@WebMvcTest(AuthController::class)
class AuthControllerTest {

	@Autowired
	private lateinit var mockMvc: MockMvc

	@MockBean
	@Suppress("unused") // Not used, but test won't run without it
	private lateinit var authService: AuthService

	@MockBean
	@Suppress("unused") // Not used, but test won't run without it
	private lateinit var jwtService: JwtService

	@MockBean
	@Suppress("unused") // Not used, but test won't run without it
	private lateinit var userRepository: UserRepository

	@Test
	fun authUserWithGoogleRedirects() {
		this.mockMvc.perform(
			get("/auth/google")
		)
			.andDo { print(it) }
			.andExpect(status().is3xxRedirection)
	}

	@Test
	fun authUserWithMissingParams() {
		this.mockMvc.perform(
			get("/auth/google/callback")
		)
			.andDo { print(it) }
			.andExpect(status().isBadRequest)
	}

	@Test
	fun authUserWithBadParams() {
		this.mockMvc.perform(
			get("/auth/google/callback?code=CODE&scope=SCOPE&authuser=AUTHUSER&prompt=PROMPT")
		)
			.andDo { print(it) }
			.andExpect(status().isBadRequest)
	}
}