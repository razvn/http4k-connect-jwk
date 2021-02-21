package net.razvan.http4kconnect.jwk.fake.examples

import com.nimbusds.jose.jwk.JWKSet
import dev.forkhandles.result4k.Failure
import dev.forkhandles.result4k.Result
import dev.forkhandles.result4k.Success
import net.razvan.http4kconnect.jwk.client.Http
import net.razvan.http4kconnect.jwk.client.Jwk
import net.razvan.http4kconnect.jwk.client.getJwkSet
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

    // if you want to use the real service with the default client you can use
    val jwkClientDefault = Jwk.Http()

    val url =  "http://localhost:24588/jwks"

    // operation return a Result monad of the API type
    val jwkSet: Result<JWKSet, RemoteFailure> = jwkClient.getJwkSet(url)
    println(jwkSet)

    when(jwkSet) {
        is Success -> println("JWK successfully retrieved")
        is Failure -> println("Retrieved failed because: ${jwkSet.reason.message}")
    }
}
