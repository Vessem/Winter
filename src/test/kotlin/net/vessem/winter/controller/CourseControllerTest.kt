package net.vessem.winter.controller

import net.vessem.winter.dto.Language
import net.vessem.winter.entity.CourseEntity
import net.vessem.winter.entity.CourseLevelEntity
import net.vessem.winter.entity.LanguageCardEntity
import net.vessem.winter.repository.CourseRepository
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.util.*
import kotlin.test.assertNotEquals

@ExtendWith(SpringExtension::class)
@WebMvcTest(CourseController::class)
class CourseControllerTest {

	@Autowired
	private lateinit var mockMvc: MockMvc

	@MockBean
	private lateinit var courseRepository: CourseRepository

	@Test
	fun getCourseWithInvalidIdTest() {
		this.mockMvc.perform(
			get("/course/id")
		)
			.andDo { print(it) }
			.andExpect(status().isBadRequest)
	}

	@Test
	fun getCourseWithMissingIdTest() {
		this.mockMvc.perform(
			get("/course/-1")
		)
			.andDo { print(it) }
			.andExpect(status().isNotFound)
	}

	@Test
	fun getCourseWithIdTest() {
		val courseEntity = CourseEntity().also { courseEntity ->
			courseEntity.id = 1
			courseEntity.languageFrom = Language.ENG
			courseEntity.languageCourse = Language.NLD
			courseEntity.levels = mutableSetOf(
				CourseLevelEntity().also { courseLevelEntity ->
					courseLevelEntity.id = 1
					courseLevelEntity.name = "Level 1"
					courseLevelEntity.cards = mutableSetOf(
						LanguageCardEntity().also { languageCardEntity ->
							languageCardEntity.id = 1
							languageCardEntity.front = "hello"
							languageCardEntity.back = "hallo"
						},
						LanguageCardEntity().also { languageCardEntity ->
							languageCardEntity.id = 2
							languageCardEntity.front = "me"
							languageCardEntity.back = "ik"
						},
						LanguageCardEntity().also { languageCardEntity ->
							languageCardEntity.id = 3
							languageCardEntity.front = "night"
							languageCardEntity.back = "nacht"
						}
					)
				},
				CourseLevelEntity().also { courseLevelEntity ->
					courseLevelEntity.id = 2
					courseLevelEntity.name = "Level 2"
					courseLevelEntity.cards = mutableSetOf(
						LanguageCardEntity().also { languageCardEntity ->
							languageCardEntity.id = 4
							languageCardEntity.front = "town"
							languageCardEntity.back = "dorp"
						},
						LanguageCardEntity().also { languageCardEntity ->
							languageCardEntity.id = 5
							languageCardEntity.front = "city"
							languageCardEntity.back = "stad"
						},
						LanguageCardEntity().also { languageCardEntity ->
							languageCardEntity.id = 6
							languageCardEntity.front = "country"
							languageCardEntity.back = "land"
						}
					)
				}
			)
		}

		given(courseRepository.getCourseEntityById(1)).willReturn(Optional.of(courseEntity))

		val withLevels = this.mockMvc.perform(
			get("/course/1?include_levels=true")
		)
			.andDo { print(it) }
			.andExpect(status().isOk)
			.andReturn()

		val withoutLevels = this.mockMvc.perform(
			get("/course/1")
		)
			.andDo { print(it) }
			.andExpect(status().isOk)
			.andReturn()

		assertNotEquals(withLevels.response.contentAsString.length, withoutLevels.response.contentAsString.length)
	}
}