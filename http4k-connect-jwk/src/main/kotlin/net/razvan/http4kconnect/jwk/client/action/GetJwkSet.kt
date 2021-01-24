package net.razvan.http4kconnect.jwk.client.action

import com.nimbusds.jose.jwk.JWKSet
import dev.forkhandles.result4k.Failure
import dev.forkhandles.result4k.Result
import dev.forkhandles.result4k.Success
import org.http4k.connect.RemoteFailure
import org.http4k.core.*
import org.http4k.lens.string

data class GetJwkSet(val url: String) : JwkAction<JWKSet> {
    override fun toRequest() = Request(Method.GET, uri())

    override fun toResult(response: Response): Result<JWKSet, RemoteFailure> = when {
        response.status.successful -> Success(reponseJwksLens(response))
        else -> Failure(RemoteFailure(Method.GET, uri(), response.status))
    }

    private fun uri() = Uri.of(url)

    private val reponseJwksLens =
        Body.string(ContentType.TEXT_PLAIN).map({ JWKSet.parse(it) }, { it.toString() }).toLens()
}
