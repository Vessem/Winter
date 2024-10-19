package net.vessem.winter.services

import com.google.gson.JsonObject
import net.vessem.winter.dto.User
import net.vessem.winter.entity.UserEntity
import net.vessem.winter.exception.BadRequestException
import net.vessem.winter.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserService {
	@Autowired
	private lateinit var userRepository: UserRepository

	fun getOrCreateNewUser(userdata: JsonObject): User {
		if (userdata.isEmpty) throw BadRequestException("Userdata is empty")
		if (userdata["email"] == null || userdata["email"].toString().isBlank()) throw BadRequestException("No email in userdata")
		if (
			(userdata["given_name"] == null ||
			userdata["given_name"].toString().isBlank()) &&
			(userdata["family_name"] != null ||
			userdata["family_name"].toString().isNotBlank())
			) throw BadRequestException("No username in userdata")
		var userEntity = userRepository.getUserEntityByEmailIgnoreCase(userdata["email"].toString().replace("\"", ""))

		if (userEntity.isEmpty) {
			// Create new user
			val newUserEntity = UserEntity().also {
				it.email = userdata["email"].asString
				it.username = userdata["given_name"].asString + " " + userdata["family_name"].asString
				it.level = 0
			}
			userEntity = Optional.of(userRepository.saveAndFlush(newUserEntity))
		}

		return User.fromEntity(userEntity.get())
	}
}