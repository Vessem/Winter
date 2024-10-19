package net.vessem.winter.controller

import jakarta.servlet.http.HttpServletRequest
import net.vessem.winter.dto.Course
import net.vessem.winter.dto.Language
import net.vessem.winter.entity.CourseEntity
import net.vessem.winter.entity.CourseLevelEntity
import net.vessem.winter.entity.LanguageCardEntity
import net.vessem.winter.repository.CourseLevelRepository
import net.vessem.winter.repository.CourseRepository
import net.vessem.winter.repository.LanguageCardRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/internal")
class InternalController {

	@Autowired
	private lateinit var courseRepository: CourseRepository

	@Autowired
	private lateinit var courseLevelRepository: CourseLevelRepository

	@Autowired
	private lateinit var languageCardRepository: LanguageCardRepository

	@GetMapping("/fillcourse")
	fun fillDatabaseWithTestData(httpServletRequest: HttpServletRequest): ResponseEntity<Course> {
		if (httpServletRequest.getHeader(HttpHeaders.ORIGIN) != "localhost") return ResponseEntity.status(HttpStatus.FORBIDDEN)
			.build()

		val languageCardEntitiesOne = languageCardRepository.saveAll(mutableSetOf(
			LanguageCardEntity().also {
				it.front = "hello"
				it.back = "hallo"
			},
			LanguageCardEntity().also {
				it.front = "me"
				it.back = "ik"
			},
			LanguageCardEntity().also {
				it.front = "night"
				it.back = "nacht"
			}
		)).toMutableSet()

		val languageCardEntitiesTwo = languageCardRepository.saveAllAndFlush(mutableSetOf(
			LanguageCardEntity().also {
				it.front = "town"
				it.back = "dorp"
			},
			LanguageCardEntity().also {
				it.front = "city"
				it.back = "stad"
			},
			LanguageCardEntity().also {
				it.front = "country"
				it.back = "land"
			}
		)).toMutableSet()

		val courseLevelEntities = courseLevelRepository.saveAllAndFlush(mutableSetOf(
			CourseLevelEntity().also {
				it.name = "Level 1"
				it.cards = languageCardEntitiesOne
			},
			CourseLevelEntity().also {
				it.name = "Level 2"
				it.cards = languageCardEntitiesTwo
			}
		)).toMutableSet()


		val courseEntity = courseRepository.saveAndFlush(CourseEntity().also {
			it.languageFrom = Language.ENG
			it.languageCourse = Language.NLD
			it.levels = courseLevelEntities
		})

		return ResponseEntity.ok(Course.fromEntity(courseEntity))
	}
}