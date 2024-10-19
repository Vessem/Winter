package net.vessem.winter.entity

import net.vessem.winter.repository.LanguageCardRepository
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import kotlin.jvm.optionals.getOrNull
import kotlin.test.assertEquals
import kotlin.test.assertNotNull


@DataJpaTest
class LanguageCardEntityTest {

	@Autowired
	private lateinit var languageCardRepository: LanguageCardRepository

	@AfterEach
	fun tearDown() {
		languageCardRepository.deleteAll()
	}

	@Test
	fun saveLanguageCardEntityTest() {
		val languageCardEntity = LanguageCardEntity().also {
			it.front = "hello"
			it.back = "hallo"
		}

		val savedLanguageCardEntity = languageCardRepository.saveAndFlush(languageCardEntity)

		assertNotNull(savedLanguageCardEntity)
		assertEquals(languageCardEntity.front, savedLanguageCardEntity.front)
		assertEquals(languageCardEntity.back, savedLanguageCardEntity.back)
	}

	@Test
	fun getLanguageCardEntityByIdTest() {
		val languageCardEntity = LanguageCardEntity().also {
			it.front = "hello"
			it.back = "hallo"
		}

		val savedLanguageCardEntity = languageCardRepository.saveAndFlush(languageCardEntity)
		val foundLanguageCardEntity = languageCardRepository.findById(savedLanguageCardEntity.id).getOrNull()

		assertNotNull(foundLanguageCardEntity)
		assertEquals(savedLanguageCardEntity.id, foundLanguageCardEntity.id)
		assertEquals(savedLanguageCardEntity.front, foundLanguageCardEntity.front)
		assertEquals(savedLanguageCardEntity.back, foundLanguageCardEntity.back)
	}
}