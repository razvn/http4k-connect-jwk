package net.razvan.http4kconnect.jwk

import net.razvan.http4kconnect.jwk.fake.FakeJwk
import org.http4k.core.HttpHandler

class FakeJwkTest : JwkContact {
    override val http: HttpHandler = FakeJwk()
}
