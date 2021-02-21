package net.razvan.http4kconnect.jwk.fake

import java.util.*

data class JWKResponse(val keys: List<Map<String, Any>>)
data class JWTRequest(
    val subject: String = UUID.randomUUID().toString(),
    val issuer: String = "FakeServer",
    val exipirationMilliseconds: Long = 3600 * 1000L,
    val claims: Map<String, Any> = emptyMap()
)