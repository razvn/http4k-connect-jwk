package net.razvan.http4kconnect.jwk.fake.exemples

import com.nimbusds.jose.jwk.JWKSet
import net.razvan.http4kconnect.jwk.client.Http
import net.razvan.http4kconnect.jwk.client.Jwk
import dev.forkhandles.result4k.Result
import net.razvan.http4kconnect.jwk.client.action.GetJwkSet
import net.razvan.http4kconnect.jwk.fake.FakeJwk
import org.http4k.client.JavaHttpClient
import org.http4k.connect.RemoteFailure
import org.http4k.core.HttpHandler
import org.http4k.filter.debug

const val USE_REAL_CLIENT = false

fun main() {
    // we can connect to the real service or the fake (drop in replacement)
    val http: HttpHandler = if (USE_REAL_CLIENT) JavaHttpClient() else FakeJwk()

    // create a client
    val jwkClient = Jwk.Http(http.debug())

    // if you want to use the real service but use the default client you can use
    val jwkClientDefault = Jwk.Http()

    val url =  "http://localhost:24588/jwks"

    // operation return a Result monad of the API type
    val jwkSet: Result<JWKSet, RemoteFailure> = jwkClient(GetJwkSet(url))
    println(jwkSet)
}