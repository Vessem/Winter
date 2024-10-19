package net.vessem.winter.dto

import net.vessem.winter.entity.UserEntity
import org.junit.Test
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import kotlin.reflect.full.declaredMemberProperties
import kotlin.test.assertEquals

@DataJpaTest
class UserTest {

	@Test
	fun toEntityTest() {
		sameFieldsTest()
		val user = User(1, "Test User 1", "test@mail.com", 0)
		val userEntity = user.toEntity()

		assertEquals(4, userEntity::class.declaredMemberProperties.size) // Make sure all fields are tested
		assertEquals(user.id, userEntity.id)
		assertEquals(user.username, userEntity.username)
		assertEquals(user.email, userEntity.email)
		assertEquals(user.level, userEntity.level)
	}

	@Test
	fun fromEntityTest() {
		val userEntity = UserEntity().also {
			it.id = 1
			it.username = "Test User 1"
			it.email = "test@mail.com"
			it.level = 0
		}
		val user = User.fromEntity(userEntity)

		assertEquals(4, user::class.declaredMemberProperties.size) // Make sure all fields are tested
		assertEquals(userEntity.id, user.id)
		assertEquals(userEntity.username, user.username)
		assertEquals(userEntity.email, user.email)
		assertEquals(userEntity.level, user.level)
	}

	@Test
	fun sameFieldsTest() {
		val userFields = User::class.declaredMemberProperties.map { it.name }.toSet()
		val userEntityFields = UserEntity::class.declaredMemberProperties.map { it.name }.toSet()

		assertEquals(userFields, userEntityFields)
	}
}