package net.vessem.winter.dto

import lombok.Data
import net.vessem.winter.entity.CourseLevelEntity

@Data
data class CourseLevel(
	var id: Long = -1,
	var name: String,
	var cards: Set<LanguageCard>
) {

	fun toEntity(): CourseLevelEntity {
		val entity = CourseLevelEntity()
		entity.id = id
		entity.name = name
		entity.cards = cards.stream().map(LanguageCard::toEntity).toList().toMutableSet()
		return entity
	}

	companion object {
		fun fromEntity(courseLevelEntity: CourseLevelEntity): CourseLevel {
			return CourseLevel(
				courseLevelEntity.id,
				courseLevelEntity.name,
				courseLevelEntity.cards.stream().map(LanguageCard::fromEntity).toList().toSet()
			)
		}
	}
}