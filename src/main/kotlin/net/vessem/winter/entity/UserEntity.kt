package net.vessem.winter.entity

import jakarta.persistence.*

@Entity(name = "user")
@Table(name = "users")
open class UserEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	open var id: Long? = null

	@Column(name = "username", nullable = false, unique = true)
	open var username: String? = null
}