package net.vessem.winter.entity

import jakarta.persistence.*

@Entity(name = "language_card")
@Table(name = "language_cards")
open class LanguageCardEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	open var id: Long = 0

	@Column(name = "front", nullable = false)
	open lateinit var front: String

	@Column(name = "back", nullable = false)
	open lateinit var back: String
}