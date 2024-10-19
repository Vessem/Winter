package net.vessem.winter.entity

import net.vessem.winter.dto.Language
import net.vessem.winter.repository.CourseRepository
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import kotlin.jvm.optionals.getOrNull
import kotlin.test.assertEquals
import kotlin.test.assertNotNull


@DataJpaTest
class CourseEntityTest {

	@Autowired
	private lateinit var courseRepository: CourseRepository

	@AfterEach
	fun tearDown() {
		courseRepository.deleteAll()
	}

	@Test
	fun saveCourseEntityTest() {
		val courseEntity = CourseEntity().also { courseEntity ->
			courseEntity.languageFrom = Language.ENG
			courseEntity.languageCourse = Language.NLD
			courseEntity.levels = mutableSetOf(
				CourseLevelEntity().also { courseLevelEntity ->
					courseLevelEntity.name = "Level 1"
					courseLevelEntity.cards = mutableSetOf(
						LanguageCardEntity().also { languageCardEntity ->
							languageCardEntity.front = "hello"
							languageCardEntity.back = "hallo"
						},
						LanguageCardEntity().also { languageCardEntity ->
							languageCardEntity.front = "me"
							languageCardEntity.back = "ik"
						},
						LanguageCardEntity().also { languageCardEntity ->
							languageCardEntity.front = "night"
							languageCardEntity.back = "nacht"
						}
					)
				},
				CourseLevelEntity().also { courseLevelEntity ->
					courseLevelEntity.name = "Level 2"
					courseLevelEntity.cards = mutableSetOf(
						LanguageCardEntity().also { languageCardEntity ->
							languageCardEntity.front = "town"
							languageCardEntity.back = "dorp"
						},
						LanguageCardEntity().also { languageCardEntity ->
							languageCardEntity.front = "city"
							languageCardEntity.back = "stad"
						},
						LanguageCardEntity().also { languageCardEntity ->
							languageCardEntity.front = "country"
							languageCardEntity.back = "land"
						}
					)
				}
			)
		}

		val savedCourseEntity = courseRepository.saveAndFlush(courseEntity)

		assertNotNull(savedCourseEntity)
		assertEquals(courseEntity.languageFrom, savedCourseEntity.languageFrom)
		assertEquals(courseEntity.languageCourse, savedCourseEntity.languageCourse)
		assertEquals(courseEntity.levels, savedCourseEntity.levels)
	}

	@Test
	fun getCourseEntityByIdTest() {
		val courseEntity = CourseEntity().also { courseEntity ->
			courseEntity.languageFrom = Language.ENG
			courseEntity.languageCourse = Language.NLD
			courseEntity.levels = mutableSetOf(
				CourseLevelEntity().also { courseLevelEntity ->
					courseLevelEntity.name = "Level 1"
					courseLevelEntity.cards = mutableSetOf(
						LanguageCardEntity().also { languageCardEntity ->
							languageCardEntity.front = "hello"
							languageCardEntity.back = "hallo"
						},
						LanguageCardEntity().also { languageCardEntity ->
							languageCardEntity.front = "me"
							languageCardEntity.back = "ik"
						},
						LanguageCardEntity().also { languageCardEntity ->
							languageCardEntity.front = "night"
							languageCardEntity.back = "nacht"
						}
					)
				},
				CourseLevelEntity().also { courseLevelEntity ->
					courseLevelEntity.name = "Level 2"
					courseLevelEntity.cards = mutableSetOf(
						LanguageCardEntity().also { languageCardEntity ->
							languageCardEntity.front = "town"
							languageCardEntity.back = "dorp"
						},
						LanguageCardEntity().also { languageCardEntity ->
							languageCardEntity.front = "city"
							languageCardEntity.back = "stad"
						},
						LanguageCardEntity().also { languageCardEntity ->
							languageCardEntity.front = "country"
							languageCardEntity.back = "land"
						}
					)
				}
			)
		}

		val savedCourseEntity = courseRepository.saveAndFlush(courseEntity)
		val foundCourseEntity = courseRepository.findById(savedCourseEntity.id).getOrNull()

		assertNotNull(foundCourseEntity)
		assertEquals(savedCourseEntity.id, foundCourseEntity.id)
		assertEquals(savedCourseEntity.languageFrom, foundCourseEntity.languageFrom)
		assertEquals(savedCourseEntity.languageCourse, foundCourseEntity.languageCourse)
		assertEquals(savedCourseEntity.levels, foundCourseEntity.levels)
	}
}