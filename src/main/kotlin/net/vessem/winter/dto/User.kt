package net.vessem.winter.dto

import lombok.Data
import net.vessem.winter.entity.UserEntity

@Data
data class User(
	var id: Long = -1,
	var username: String,
	var email: String,
	var level: Int = 0
) {

	fun toEntity(): UserEntity {
		val entity = UserEntity()
		entity.id = id
		entity.username = username
		entity.email = email
		entity.level = level
		return entity
	}

	companion object {
		fun fromEntity(userEntity: UserEntity): User {
			return User(
				userEntity.id,
				userEntity.username,
				userEntity.email,
				userEntity.level
			)
		}
	}
}