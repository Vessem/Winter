package net.vessem.winter.repository

import net.vessem.winter.entity.LanguageCardEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.scheduling.annotation.Async
import java.util.*

interface LanguageCardRepository : JpaRepository<LanguageCardEntity, Long>,
	JpaSpecificationExecutor<LanguageCardEntity> {

	@Async
	fun getLanguageCardEntityById(id: Long): Optional<LanguageCardEntity>

}