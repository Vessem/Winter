package net.vessem.winter.controller

import net.vessem.winter.dto.User
import net.vessem.winter.exception.NotFoundException
import net.vessem.winter.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/user")
class UserController {
	@Autowired
	private lateinit var userRepository: UserRepository

	/**
	 * Get a user by their ID
	 *
	 * @param id the ID of a user
	 * @see User
	 */
	@GetMapping("/{id}")
	fun getUserById(@PathVariable id: String): ResponseEntity<User> {
		try {
			val user = userRepository.getUserEntityById(id.toLong())
			if (user.isEmpty) throw NotFoundException("User not found")

			return ResponseEntity.ok(User.fromEntity(user.get()))
		} catch (e: NumberFormatException) {
			return ResponseEntity.badRequest().build()
		} catch (e: NotFoundException) {
			return ResponseEntity.notFound().build()
		}
	}
}