package net.vessem.winter.repository

import net.vessem.winter.entity.CourseEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.scheduling.annotation.Async
import java.util.*

interface CourseRepository : JpaRepository<CourseEntity, Long>, JpaSpecificationExecutor<CourseEntity> {

	@Async
	fun getCourseEntityById(id: Long): Optional<CourseEntity>
}