package net.vessem.winter.entity

import jakarta.persistence.*
import net.vessem.winter.dto.Language

@Entity(name = "course")
@Table(name = "courses")
open class CourseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	open var id: Long = 0

	@Enumerated(EnumType.ORDINAL)
	@Column(name = "language_from", nullable = false)
	open lateinit var languageFrom: Language

	@Enumerated(EnumType.ORDINAL)
	@Column(name = "language_course", nullable = false)
	open lateinit var languageCourse: Language

	@OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
	@CollectionTable(name = "courses__course_levels", joinColumns = [JoinColumn(name = "course_id")])
	open var levels: MutableSet<CourseLevelEntity> = mutableSetOf()
}