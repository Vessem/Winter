package net.vessem.winter.dto

import net.vessem.winter.entity.CourseLevelEntity
import net.vessem.winter.entity.LanguageCardEntity
import org.junit.jupiter.api.Test
import kotlin.reflect.full.declaredMemberProperties
import kotlin.test.assertEquals

class CourseLevelTest {

	@Test
	fun toEntityTest() {
		sameFieldsTest()
		val courseLevel = CourseLevel(
			1, "Level 1", setOf(
				LanguageCard(1, "hello", "hallo"),
				LanguageCard(2, "me", "ik"),
				LanguageCard(3, "night", "nacht")
			)
		)
		val courseLevelEntity = courseLevel.toEntity()

		val tests = hashMapOf(
			Pair(courseLevel.id, courseLevelEntity.id),
			Pair(courseLevel.name, courseLevelEntity.name),
			Pair(courseLevel.cards, courseLevelEntity.cards.stream().map(LanguageCard::fromEntity).toList().toSet()),
		)

		assertEquals(
			tests.size,
			courseLevelEntity::class.declaredMemberProperties.size
		) // Make sure all fields are tested
		tests.forEach { (dto, entity) -> assertEquals(dto, entity) }
	}

	@Test
	fun fromEntityTest() {
		val courseLevelEntity = CourseLevelEntity().also { courseLevelEntity ->
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
		}

		val courseLevel = CourseLevel.fromEntity(courseLevelEntity)

		val tests = hashMapOf(
			Pair(courseLevelEntity.id, courseLevel.id),
			Pair(courseLevelEntity.name, courseLevel.name),
			Pair(courseLevelEntity.cards.stream().map(LanguageCard::fromEntity).toList().toSet(), courseLevel.cards),
		)

		assertEquals(tests.size, courseLevel::class.declaredMemberProperties.size) // Make sure all fields are tested
		tests.forEach { (entity, dto) -> assertEquals(entity, dto) }
	}

	@Test
	fun sameFieldsTest() {
		val courseFields = CourseLevel::class.declaredMemberProperties.map { it.name }.toSet()
		val courseEntityFields = CourseLevelEntity::class.declaredMemberProperties.map { it.name }.toSet()

		assertEquals(courseFields, courseEntityFields)
	}
}