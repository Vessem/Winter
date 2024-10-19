package net.vessem.winter.entity

import net.vessem.winter.repository.CourseLevelRepository
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import kotlin.jvm.optionals.getOrNull
import kotlin.test.assertEquals
import kotlin.test.assertNotNull


@DataJpaTest
class CourseLevelEntityTest {

	@Autowired
	private lateinit var courseLevelRepository: CourseLevelRepository

	@AfterEach
	fun tearDown() {
		courseLevelRepository.deleteAll()
	}

	@Test
	fun saveCourseLevelEntityTest() {
		val courseLevelEntity = CourseLevelEntity().also { courseLevelEntity ->
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
		}

		val savedCourseLevelEntity = courseLevelRepository.saveAndFlush(courseLevelEntity)

		assertNotNull(savedCourseLevelEntity)
		assertEquals(courseLevelEntity.name, savedCourseLevelEntity.name)
		assertEquals(courseLevelEntity.cards, savedCourseLevelEntity.cards)
	}

	@Test
	fun getCourseLevelEntityByIdTest() {
		val courseLevelEntity = CourseLevelEntity().also { courseLevelEntity ->
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
		}

		val savedCourseLevelEntity = courseLevelRepository.saveAndFlush(courseLevelEntity)
		val foundCourseLevelEntity = courseLevelRepository.findById(savedCourseLevelEntity.id).getOrNull()

		assertNotNull(foundCourseLevelEntity)
		assertEquals(savedCourseLevelEntity.id, foundCourseLevelEntity.id)
		assertEquals(savedCourseLevelEntity.name, foundCourseLevelEntity.name)
		assertEquals(savedCourseLevelEntity.cards, foundCourseLevelEntity.cards)
	}
}