package net.vessem.winter.repository

import net.vessem.winter.entity.CourseLevelEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.scheduling.annotation.Async
import java.util.*

interface CourseLevelRepository : JpaRepository<CourseLevelEntity, Long>, JpaSpecificationExecutor<CourseLevelEntity> {

	@Async
	fun getCourseLevelEntityById(id: Long): Optional<CourseLevelEntity>
}