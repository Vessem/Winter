package net.vessem.winter.exception

class BadRequestException : Exception {
	constructor() : super()
	constructor(message: String) : super(message)
	constructor(message: String, cause: Throwable) : super(message, cause)
	constructor(cause: Throwable) : super(cause)
}