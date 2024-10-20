package net.vessem.winter.services

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import net.vessem.winter.dto.User
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.util.*

@Service
class JwtService {
	@Value("\${jwt.secret}")
	private lateinit var secretKey: String

	@Value("\${jwt.expire-after}")
	private var expireAfter: Long = 3600000 // 1 hour

	@Value("\${jwt.expire-refresh-after}")
	private var expireRefreshAfter: Long = 3600000 * 24 * 3 // 3 days

	fun generateToken(user: User): String {
		return Jwts
			.builder()
			.subject(user.id.toString())
			.claim("refresh", false)
			.issuedAt(Date(System.currentTimeMillis()))
			.expiration(Date(System.currentTimeMillis() + expireAfter))
			.signWith(Keys.hmacShaKeyFor(secretKey.toByteArray()))
			.compact()
	}

	fun generateRefreshToken(user: User): String {
		return Jwts
			.builder()
			.subject(user.id.toString())
			.claim("refresh", true)
			.issuedAt(Date(System.currentTimeMillis()))
			.expiration(Date(System.currentTimeMillis() + expireRefreshAfter))
			.signWith(Keys.hmacShaKeyFor(secretKey.toByteArray()))
			.compact()
	}

	fun isRefreshToken(token: String): Boolean {
		try {
			return Jwts
				.parser()
				.verifyWith(Keys.hmacShaKeyFor(secretKey.toByteArray()))
				.build()
				.parseSignedClaims(token)
				.payload
				.getOrDefault("refresh", false) as Boolean
		} catch (e: Exception) {
			return false
		}
	}

	fun isTokenValid(token: String, user: User): Boolean {
		try {
			val payload = Jwts
				.parser()
				.verifyWith(Keys.hmacShaKeyFor(secretKey.toByteArray()))
				.build()
				.parseSignedClaims(token)
				.payload

			if (!(payload.get("refresh") as Boolean) && payload.expiration.after(Date(System.currentTimeMillis() + expireAfter))) return false
			if (payload.get("refresh") as Boolean && payload.expiration.after(Date(System.currentTimeMillis() + expireRefreshAfter))) return false
			if (payload.issuedAt.before(user.lastUpdated)) return false
			if (payload.subject != user.id.toString()) return false

			return true
		} catch (e: Exception) {
			return false
		}
	}

	fun getUserIdFromToken(token: String): Long? {
		return Jwts
			.parser()
			.verifyWith(Keys.hmacShaKeyFor(secretKey.toByteArray()))
			.build()
			.parseSignedClaims(token)
			.payload.subject.toLongOrNull()
	}
}