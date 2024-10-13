package net.vessem.winter.dto

import net.vessem.winter.entity.UserEntity
import org.junit.Test
import kotlin.test.assertEquals

class UserTest {

	@Test
	fun toEntityTest() {
		val user = User(1, "Test User 1")
		val userEntity = user.toEntity()

		assertEquals(user.id, userEntity.id)
		assertEquals(user.username, userEntity.username)
	}

	@Test
	fun fromEntityTest() {
		val userEntity = UserEntity()
		userEntity.id = 1
		userEntity.username = "Test User 1"
		val user = User.fromEntity(userEntity)

		assertEquals(userEntity.id, user.id)
		assertEquals(userEntity.username, user.username)
	}
}