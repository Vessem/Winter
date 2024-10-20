package net.vessem.winter.dto

import lombok.Data
import net.vessem.winter.entity.UserEntity
import java.sql.Date

@Data
data class User(
	var id: Long = -1,
	var username: String,
	var email: String,
	var lastUpdated: Date
) {

	fun toEntity(): UserEntity {
		val entity = UserEntity()
		entity.id = id
		entity.username = username
		entity.email = email
		entity.lastUpdated = lastUpdated
		return entity
	}

	companion object {
		fun fromEntity(userEntity: UserEntity): User {
			return User(
				userEntity.id,
				userEntity.username,
				userEntity.email,
				userEntity.lastUpdated
			)
		}
	}
}