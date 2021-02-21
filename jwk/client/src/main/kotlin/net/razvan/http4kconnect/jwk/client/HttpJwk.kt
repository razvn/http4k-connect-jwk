package net.razvan.http4kconnect.jwk.client

import dev.forkhandles.result4k.Result
import net.razvan.http4kconnect.jwk.client.action.JwkAction
import org.http4k.client.JavaHttpClient
import org.http4k.connect.RemoteFailure
import org.http4k.core.HttpHandler

fun Jwk.Companion.Http(http: HttpHandler = JavaHttpClient()) = object : Jwk {
    override fun <R : Any> invoke(action: JwkAction<R>): Result<R, RemoteFailure> =
        action.toResult(http(action.toRequest()))
}