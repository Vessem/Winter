package net.vessem.winter.entity

import jakarta.persistence.*

@Entity(name = "course_level")
@Table(name = "course_levels")
open class CourseLevelEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	open var id: Long = 0

	@Column(name = "name", nullable = false)
	open lateinit var name: String

	@Suppress("JpaAttributeTypeInspection") // False positive
	@ElementCollection(targetClass = LanguageCardEntity::class, fetch = FetchType.EAGER)
	@CollectionTable(name = "course_levels__language_cards", joinColumns = [JoinColumn(name = "course_level_id")])
	open var cards: MutableSet<LanguageCardEntity> = mutableSetOf()
}