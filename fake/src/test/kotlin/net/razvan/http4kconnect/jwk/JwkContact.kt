package net.razvan.http4kconnect.jwk

import com.nimbusds.jose.jwk.JWKSet
import dev.forkhandles.result4k.Failure
import net.razvan.http4kconnect.jwk.client.Http
import net.razvan.http4kconnect.jwk.client.Jwk
import net.razvan.http4kconnect.jwk.client.action.GetJwkSet
import dev.forkhandles.result4k.Success
import io.kotest.matchers.should
import io.kotest.matchers.types.beOfType
import net.razvan.http4kconnect.jwk.client.getJwkSet
import org.http4k.connect.RemoteFailure
import org.http4k.core.HttpHandler
import org.junit.jupiter.api.Test

interface JwkContact {
    val http: HttpHandler

    @Test
    fun `get jwk`() {
        val service = Jwk.Http(http)
        val resp = service.getJwkSet("/jwks")
        resp should beOfType<Success<JWKSet>>()
    }
}
