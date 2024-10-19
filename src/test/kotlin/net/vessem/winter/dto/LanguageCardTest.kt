package net.vessem.winter.dto

import net.vessem.winter.entity.LanguageCardEntity
import org.junit.jupiter.api.Test
import kotlin.reflect.full.declaredMemberProperties
import kotlin.test.assertEquals

class LanguageCardTest {

	@Test
	fun toEntityTest() {
		sameFieldsTest()
		val languageCard = LanguageCard(1, "hello", "hallo")
		val languageCardEntity = languageCard.toEntity()

		val tests = hashMapOf(
			Pair(languageCard.id, languageCardEntity.id),
			Pair(languageCard.front, languageCardEntity.front),
			Pair(languageCard.back, languageCardEntity.back),
		)

		assertEquals(
			tests.size,
			languageCardEntity::class.declaredMemberProperties.size
		) // Make sure all fields are tested
		tests.forEach { (dto, entity) -> assertEquals(dto, entity) }
	}

	@Test
	fun fromEntityTest() {
		val languageCardEntity = LanguageCardEntity().also {
			it.id = 1
			it.front = "hello"
			it.back = "hallo"
		}
		val languageCard = LanguageCard.fromEntity(languageCardEntity)

		val tests = hashMapOf(
			Pair(languageCardEntity.id, languageCard.id),
			Pair(languageCardEntity.front, languageCard.front),
			Pair(languageCardEntity.back, languageCard.back)
		)

		assertEquals(tests.size, languageCard::class.declaredMemberProperties.size) // Make sure all fields are tested
		tests.forEach { (entity, dto) -> assertEquals(entity, dto) }
	}

	@Test
	fun sameFieldsTest() {
		val languageCardFields = LanguageCard::class.declaredMemberProperties.map { it.name }.toSet()
		val languageCardEntityFields = LanguageCardEntity::class.declaredMemberProperties.map { it.name }.toSet()

		assertEquals(languageCardFields, languageCardEntityFields)
	}
}