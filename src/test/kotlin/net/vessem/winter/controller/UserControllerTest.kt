package net.vessem.winter.controller

import net.vessem.winter.entity.UserEntity
import net.vessem.winter.repository.UserRepository
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.util.*

@ExtendWith(SpringExtension::class)
@WebMvcTest(UserController::class)
class UserControllerTest {

	@Autowired
	private lateinit var mockMvc: MockMvc

	@MockBean
	private lateinit var userRepository: UserRepository

	@Test
	fun getUserWithInvalidIdTest() {
		this.mockMvc.perform(
			get("/user/id")
		)
			.andDo { print(it) }
			.andExpect(status().isBadRequest)
	}

	@Test
	fun getUserWithMissingIdTest() {
		this.mockMvc.perform(
			get("/user/-1")
		)
			.andDo { print(it) }
			.andExpect(status().isNotFound)
	}

	@Test
	fun getUserWithIdTest() {
		val userEntity = UserEntity().also {
			it.username = "Test User 1"
			it.email = "test@mail.com"
			it.level = 0
		}

		given(userRepository.getUserEntityById(1)).willReturn(Optional.of(userEntity))

		this.mockMvc.perform(
			get("/user/1")
		)
			.andDo { print(it) }
			.andExpect(status().isOk)
	}
}