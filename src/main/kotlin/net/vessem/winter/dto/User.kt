package net.vessem.winter.dto

import lombok.Data
import net.vessem.winter.entity.UserEntity

@Data
data class User(
	var id: Long = -1,
	var username: String
) {

	fun toEntity(): UserEntity {
		val entity = UserEntity()
		entity.id = id
		entity.username = username
		return entity
	}

	companion object {
		fun fromEntity(userEntity: UserEntity): User {
			return User(
				userEntity.id!!,
				userEntity.username!!
			)
		}
	}
}