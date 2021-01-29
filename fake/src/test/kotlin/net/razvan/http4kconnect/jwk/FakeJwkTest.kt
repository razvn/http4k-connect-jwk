package net.razvan.http4kconnect.jwk

import com.nimbusds.jose.jwk.JWKSet
import dev.forkhandles.result4k.Success
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldNotBeEqualIgnoringCase
import io.kotest.matchers.types.beOfType
import net.razvan.http4kconnect.jwk.client.Http
import net.razvan.http4kconnect.jwk.client.Jwk
import net.razvan.http4kconnect.jwk.client.getJwkSet
import net.razvan.http4kconnect.jwk.fake.FakeJwk
import org.http4k.core.HttpHandler
import org.http4k.core.Method
import org.http4k.core.Request
import org.http4k.core.Status
import org.junit.jupiter.api.Test

class FakeJwkTest {

    private val handler: HttpHandler = FakeJwk()

    @Test
    fun `exposes list of jwt`() {
        val service = Jwk.Http(handler)
        val resp = service.getJwkSet("/jwks")
        resp should beOfType<Success<JWKSet>>()
    }

    @Test
    fun `renew creates a nes set`() {
        val beforeRequest = handler(Request(Method.GET, "/jwks"))
        beforeRequest.status shouldBe Status.OK

        val resp = handler(Request(Method.GET, "/renew"))
        resp.status shouldBe Status.OK

        val afterRequest = handler(Request(Method.GET, "/jwks"))
        afterRequest.status shouldBe Status.OK

        beforeRequest.bodyString() shouldNotBeEqualIgnoringCase afterRequest.bodyString()
    }
}
