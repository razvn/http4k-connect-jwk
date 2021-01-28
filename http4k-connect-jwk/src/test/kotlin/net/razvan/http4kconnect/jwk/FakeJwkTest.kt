package net.razvan.http4kconnect.jwk

import dev.forkhandles.result4k.Failure
import io.kotest.matchers.should
import io.kotest.matchers.types.beOfType
import net.razvan.http4kconnect.jwk.client.Http
import net.razvan.http4kconnect.jwk.client.Jwk
import net.razvan.http4kconnect.jwk.client.getJwkSet
import net.razvan.http4kconnect.jwk.fake.FakeJwk
import org.http4k.connect.RemoteFailure
import org.http4k.core.HttpHandler
import org.http4k.core.Response
import org.http4k.core.Status.Companion.OK
import org.junit.jupiter.api.Test

class FakeJwkTest : JwkContact {
    override val http: HttpHandler = FakeJwk()

    @Test
    fun `get bad format`() {
        val customHandler: HttpHandler = { _ ->
            Response(OK)
                    .body("""
                        { 
                          "msg": "Not a JWK"
                        }
                    """.trimIndent())
        }
        val service = Jwk.Http(customHandler)
        val resp = service.getJwkSet("/jwks")
        println(resp)
        resp should beOfType<Failure<RemoteFailure>>()
    }
}
