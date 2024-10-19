package net.vessem.winter.controller

import net.vessem.winter.entity.LanguageCardEntity
import net.vessem.winter.repository.LanguageCardRepository
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

@ExtendWith(SpringExtension::class)
@WebMvcTest(LanguageCardController::class)
class LanguageCardControllerTest {

	@Autowired
	private lateinit var mockMvc: MockMvc

	@MockBean
	private lateinit var languageCardRepository: LanguageCardRepository

	@Test
	fun getLanguageCardWithInvalidIdTest() {
		this.mockMvc.perform(
			get("/card/id")
		)
			.andDo { print(it) }
			.andExpect(status().isBadRequest)
	}

	@Test
	fun getLanguageCardWithMissingIdTest() {
		this.mockMvc.perform(
			get("/card/-1")
		)
			.andDo { print(it) }
			.andExpect(status().isNotFound)
	}

	@Test
	fun getLanguageCardWithIdTest() {
		val languageCardEntity = LanguageCardEntity().also {
			it.id = 3
			it.front = "night"
			it.back = "nacht"
		}

		given(languageCardRepository.getLanguageCardEntityById(1)).willReturn(Optional.of(languageCardEntity))

		this.mockMvc.perform(
			get("/card/1")
		)
			.andDo { print(it) }
			.andExpect(status().isOk)
	}
}