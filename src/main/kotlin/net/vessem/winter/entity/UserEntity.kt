package net.vessem.winter.entity

import jakarta.persistence.*
import org.hibernate.annotations.ColumnDefault

@Entity(name = "user")
@Table(name = "users")
open class UserEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	open var id: Long = 0

	@Column(name = "username", nullable = false)
	open lateinit var username: String

	@Column(name = "email", nullable = false, unique = true)
	open lateinit var email: String

	@ColumnDefault("0")
	@Column(name = "level", nullable = false)
	open var level: Int = 0
}