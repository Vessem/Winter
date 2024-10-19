package net.vessem.winter.repository

import net.vessem.winter.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.scheduling.annotation.Async
import java.util.*

interface UserRepository : JpaRepository<UserEntity, Long>, JpaSpecificationExecutor<UserEntity> {

	@Async
	fun getUserEntityById(id: Long): Optional<UserEntity>

	@Async
	fun getUserEntityByEmailIgnoreCase(email: String): Optional<UserEntity>
}