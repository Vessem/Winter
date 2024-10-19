package net.vessem.winter.dto

import org.junit.jupiter.api.Test
import java.util.*
import kotlin.test.assertContains


class LanguageTest {

	@Test
	fun languageValidISOCodesTest() {
		Language.entries.forEach {
			assertContains(
				Locale.getAvailableLocales().map { lang -> lang.getISO3Language() }.toSet(),
				it.name.lowercase()
			)
		}
	}
}