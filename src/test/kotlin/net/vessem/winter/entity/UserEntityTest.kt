package net.vessem.winter.entity

import net.vessem.winter.dto.User
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
		val userEntity = User(1, "Test User 1").toEntity()
		val savedUserEntity = userRepository.saveAndFlush(userEntity)

		assertEquals(1, savedUserEntity.id)
		assertEquals(userEntity.username, savedUserEntity.username)
	}

	@Test
	fun getUserEntityByIdTest() {
		val user = User(-1, "Test User 1")
		val savedUserEntity = userRepository.saveAndFlush(user.toEntity())

		val foundUserEntity = userRepository.findById(savedUserEntity.id!!).getOrNull()
		assertNotNull(foundUserEntity)
		assertEquals(savedUserEntity.id, foundUserEntity!!.id)
		assertEquals(savedUserEntity.username, foundUserEntity.username)
	}
}