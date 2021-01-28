package net.razvan.http4kconnect.jwk

import org.http4k.client.JavaHttpClient
import org.http4k.core.HttpHandler
import org.http4k.core.Uri
import org.http4k.core.then
import org.http4k.filter.ClientFilters
import org.http4k.filter.debug
import org.junit.jupiter.api.Assumptions.assumeTrue
import org.junit.jupiter.api.BeforeEach

class RealJwkTest : JwkContact {
    @BeforeEach
    fun setUp() {
        // this disable the tests for real url if set to `false`.
        // it should be temporary be set to true to validate that tests are valid against real client too but then disable
        // as real client is not always available.
        assumeTrue(false)
    }

    override val http: HttpHandler = ClientFilters.SetBaseUriFrom(Uri.of("/mocks")) // real client URL is http://localhost:8080/mocks/jwks
        .then(ClientFilters.SetHostFrom(Uri.of("http://localhost:8080")))
        .then(JavaHttpClient().debug())
}
