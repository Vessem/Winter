package net.vessem.winter.dto

import net.vessem.winter.entity.CourseEntity
import net.vessem.winter.entity.CourseLevelEntity
import net.vessem.winter.entity.LanguageCardEntity
import org.junit.Test
import kotlin.reflect.full.declaredMemberProperties
import kotlin.test.assertEquals

class CourseTest {

	@Test
	fun toEntityTest() {
		sameFieldsTest()
		val course = Course(
			1, Language.ENG, Language.DUT, setOf(
				CourseLevel(
					1, "Level 1", setOf(
						LanguageCard(1, "hello", "hallo"),
						LanguageCard(2, "me", "ik"),
						LanguageCard(3, "night", "nacht")
					)
				),
				CourseLevel(
					2, "Level 2", setOf(
						LanguageCard(4, "town", "dorp"),
						LanguageCard(5, "city", "stad"),
						LanguageCard(6, "country", "land")
					)
				)
			)
		)
		val courseEntity = course.toEntity()

		val tests = hashMapOf(
			Pair(course.id, courseEntity.id),
			Pair(course.languageFrom, courseEntity.languageFrom),
			Pair(course.languageCourse, courseEntity.languageCourse),
			Pair(course.levels, courseEntity.levels.stream().map(CourseLevel::fromEntity).toList().toSet()),
		)

		assertEquals(tests.size, courseEntity::class.declaredMemberProperties.size) // Make sure all fields are tested
		tests.forEach { (dto, entity) -> assertEquals(dto, entity) }
	}

	@Test
	fun fromEntityTest() {
		val courseEntity = CourseEntity().also { courseEntity ->
			courseEntity.id = 1
			courseEntity.languageFrom = Language.ENG
			courseEntity.languageCourse = Language.DUT
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
		val course = Course.fromEntity(courseEntity)

		val tests = hashMapOf(
			Pair(courseEntity.id, course.id),
			Pair(courseEntity.languageFrom, course.languageFrom),
			Pair(courseEntity.languageCourse, course.languageCourse),
			Pair(courseEntity.levels.stream().map(CourseLevel::fromEntity).toList().toSet(), course.levels),
		)

		assertEquals(tests.size, course::class.declaredMemberProperties.size) // Make sure all fields are tested
		tests.forEach { (entity, dto) -> assertEquals(entity, dto) }
	}

	@Test
	fun sameFieldsTest() {
		val courseFields = Course::class.declaredMemberProperties.map { it.name }.toSet()
		val courseEntityFields = CourseEntity::class.declaredMemberProperties.map { it.name }.toSet()

		assertEquals(courseFields, courseEntityFields)
	}
}