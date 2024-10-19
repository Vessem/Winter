package net.vessem.winter.dto

import lombok.Data
import net.vessem.winter.entity.CourseEntity

@Data
data class Course(
	var id: Long = -1,
	var languageFrom: Language,
	var languageCourse: Language,
	var levels: Set<CourseLevel>
) {

	fun toEntity(): CourseEntity {
		val entity = CourseEntity()
		entity.id = id
		entity.languageFrom = languageFrom
		entity.languageCourse = languageCourse
		entity.levels = levels.stream().map(CourseLevel::toEntity).toList().toMutableSet()
		return entity
	}

	companion object {
		fun fromEntity(courseEntity: CourseEntity): Course {
			return Course(
				courseEntity.id,
				courseEntity.languageFrom,
				courseEntity.languageCourse,
				courseEntity.levels.stream().map(CourseLevel::fromEntity).toList().toSet()
			)
		}
	}
}