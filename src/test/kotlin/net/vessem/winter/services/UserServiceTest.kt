package net.vessem.winter.services

import com.google.gson.JsonObject
import net.vessem.winter.entity.UserEntity
import net.vessem.winter.exception.BadRequestException
import net.vessem.winter.repository.UserRepository
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension

import java.util.*
import kotlin.test.assertEquals

@ExtendWith(MockitoExtension::class)
class UserServiceTest {

	@Mock
	private lateinit var userRepository: UserRepository

	@InjectMocks
	private lateinit var userService: UserService

	@Test
	fun getOrCreateNewUserWithEmptyDataTest() {
		assertThrows<BadRequestException> { userService.getOrCreateNewUser(JsonObject()) }
	}

	@Test
	fun getUserTest() {
		val json = JsonObject()
		json.addProperty("email", "test@mail.com")
		json.addProperty("given_name", "test")
		json.addProperty("family_name", "user")

		given(userRepository.getUserEntityByEmailIgnoreCase(json.get("email").asString)).willReturn(Optional.of(
			UserEntity().also {
				it.email = "test@mail.com"
				it.username = json.get("given_name").asString + json.get("family_name").asString
				it.level = 0
			}
		))

		val user = userService.getOrCreateNewUser(json)

		assertEquals(json.get("email").asString, user.email)
		assertEquals(json.get("given_name").asString + json.get("family_name").asString, user.username)
		assertEquals(0, user.level)
	}

	@Test
	fun createUserTest() {
		val json = JsonObject()
		json.addProperty("email", "test@mail.com")
		json.addProperty("given_name", "test")
		json.addProperty("family_name", "user")

		given(userRepository.getUserEntityByEmailIgnoreCase(json.get("email").asString)).willReturn(Optional.empty())
		`when`(userRepository.saveAndFlush(Mockito.any(UserEntity::class.java))).thenAnswer { it.getArgument(0) }

		val user = userService.getOrCreateNewUser(json)

		assertEquals(json.get("email").asString, user.email)
		assertEquals(json.get("given_name").asString + " " + json.get("family_name").asString, user.username)
		assertEquals(0, user.level)
	}
}