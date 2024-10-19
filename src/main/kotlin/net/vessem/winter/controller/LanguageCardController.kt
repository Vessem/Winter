package net.vessem.winter.controller

import net.vessem.winter.dto.LanguageCard
import net.vessem.winter.exception.NotFoundException
import net.vessem.winter.repository.LanguageCardRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/card")
class LanguageCardController {
	@Autowired
	private lateinit var languageCardRepository: LanguageCardRepository

	/**
	 * Get a language card by its ID
	 *
	 * @param id the ID of the card
	 * @see LanguageCard
	 */
	@GetMapping("/{id}")
	fun getLanguageCardById(@PathVariable id: String): ResponseEntity<LanguageCard> {
		try {
			val languageCardEntity = languageCardRepository.getLanguageCardEntityById(id.toLong())
			if (languageCardEntity.isEmpty) throw NotFoundException("LanguageCard not found")

			return ResponseEntity.ok(LanguageCard.fromEntity(languageCardEntity.get()))
		} catch (e: NumberFormatException) {
			return ResponseEntity.badRequest().build()
		} catch (e: NotFoundException) {
			return ResponseEntity.notFound().build()
		}
	}
}