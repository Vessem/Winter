package net.vessem.winter.dto

import lombok.Data
import net.vessem.winter.entity.LanguageCardEntity

@Data
data class LanguageCard(
	var id: Long = -1,
	var front: String,
	var back: String,
) {

	fun toEntity(): LanguageCardEntity {
		val entity = LanguageCardEntity()
		entity.id = id
		entity.front = front
		entity.back = back
		return entity
	}

	companion object {
		fun fromEntity(languageCardEntity: LanguageCardEntity): LanguageCard {
			return LanguageCard(
				languageCardEntity.id,
				languageCardEntity.front,
				languageCardEntity.back
			)
		}
	}
}