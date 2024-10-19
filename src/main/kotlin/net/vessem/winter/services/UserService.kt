package net.vessem.winter.services

import com.google.gson.JsonObject
import net.vessem.winter.dto.User
import net.vessem.winter.entity.UserEntity
import net.vessem.winter.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserService {
	@Autowired
	private lateinit var userRepository: UserRepository

	fun getOrCreateNewUser(userdata: JsonObject): User {
		var userEntity = userRepository.getUserEntityByEmailIgnoreCase(userdata["email"].toString().replace("\"", ""))

		if (userEntity.isEmpty) {
			// Create new user
			val newUserEntity = UserEntity()
			newUserEntity.email = userdata["email"].toString().replace("\"", "")
			newUserEntity.username =
				userdata["given_name"].toString().replace("\"", "") + " " + userdata["family_name"].toString()
					.replace("\"", "")
			userEntity = Optional.of(userRepository.saveAndFlush(newUserEntity))
		}

		return User.fromEntity(userEntity.get())
	}
}