package net.vessem.winter.controller

import net.vessem.winter.dto.Course
import net.vessem.winter.dto.User
import net.vessem.winter.exception.NotFoundException
import net.vessem.winter.repository.CourseRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import java.util.*
import kotlin.jvm.optionals.getOrDefault

@Controller
@RequestMapping("/course")
class CourseController {
	@Autowired
	private lateinit var courseRepository: CourseRepository

	/**
	 * Get a course by its ID
	 *
	 * @param id the ID of the course
	 * @param includeLevels if levels should be included in the response
	 * @see User
	 */
	@GetMapping("/{id}")
	fun getCourseById(
		@PathVariable id: String,
		@RequestParam("include_levels") includeLevels: Optional<Boolean>
	): ResponseEntity<Course> {
		try {
			val courseEntity = courseRepository.getCourseEntityById(id.toLong())
			if (courseEntity.isEmpty) throw NotFoundException("Course not found")

			val course = Course.fromEntity(courseEntity.get())
			if (!includeLevels.getOrDefault(false)) course.levels = emptySet()

			return ResponseEntity.ok(course)
		} catch (e: NumberFormatException) {
			return ResponseEntity.badRequest().build()
		} catch (e: NotFoundException) {
			return ResponseEntity.notFound().build()
		}
	}
}