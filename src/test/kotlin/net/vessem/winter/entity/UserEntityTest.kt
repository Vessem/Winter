package net.vessem.winter.entity

import net.vessem.winter.repository.UserRepository
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import kotlin.jvm.optionals.getOrNull
import kotlin.test.assertEquals
import kotlin.test.assertNotNull


@DataJpaTest
class UserEntityTest {

	@Autowired
	private lateinit var userRepository: UserRepository

	@AfterEach
	fun tearDown() {
		userRepository.deleteAll()
	}

	@Test
	fun saveUserEntityTest() {
		val userEntity = UserEntity().also {
			it.username = "Test User 1"
			it.email = "test@mail.com"
			it.level = 0
		}

		val savedUserEntity = userRepository.saveAndFlush(userEntity)

		assertNotNull(savedUserEntity)
		assertEquals(userEntity.username, savedUserEntity.username)
		assertEquals(userEntity.email, savedUserEntity.email)
		assertEquals(userEntity.level, savedUserEntity.level)
	}

	@Test
	fun getUserEntityByIdTest() {
		val userEntity = UserEntity().also {
			it.username = "Test User 1"
			it.email = "test@mail.com"
			it.level = 0
		}

		val savedUserEntity = userRepository.saveAndFlush(userEntity)
		val foundUserEntity = userRepository.findById(savedUserEntity.id).getOrNull()

		assertNotNull(foundUserEntity)
		assertEquals(savedUserEntity.id, foundUserEntity.id)
		assertEquals(savedUserEntity.username, foundUserEntity.username)
		assertEquals(savedUserEntity.email, foundUserEntity.email)
		assertEquals(savedUserEntity.level, foundUserEntity.level)
	}
}