package net.vessem.winter

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class WinterApplication

fun main(args: Array<String>) {
	runApplication<WinterApplication>(*args)
}
