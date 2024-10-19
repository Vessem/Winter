package net.vessem.winter.exception

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class BadRequestExceptionTest {

	@Test
	fun badRequestExceptionTest() {
		assertThrows<BadRequestException> {
			throw BadRequestException()
		}

		assertThrows<BadRequestException> {
			throw BadRequestException("message")
		}

		assertThrows<BadRequestException> {
			throw BadRequestException("message", Throwable())
		}

		assertThrows<BadRequestException> {
			throw BadRequestException(Throwable())
		}
	}
}