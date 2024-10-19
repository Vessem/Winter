package net.vessem.winter.exception

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class NotFoundExceptionTest {

	@Test
	fun notFoundExceptionTest() {
		assertThrows<NotFoundException> {
			throw NotFoundException()
		}

		assertThrows<NotFoundException> {
			throw NotFoundException("message")
		}

		assertThrows<NotFoundException> {
			throw NotFoundException("message", Throwable())
		}

		assertThrows<NotFoundException> {
			throw NotFoundException(Throwable())
		}
	}
}