package net.razvan.http4kconnect.jwk.client

import dev.forkhandles.result4k.Result
import net.razvan.http4kconnect.jwk.client.action.JwkAction
import org.http4k.connect.Http4kConnectAdapter
import org.http4k.connect.RemoteFailure

@Http4kConnectAdapter
interface Jwk {
    operator fun <R : Any> invoke(action: JwkAction<R>): Result<R, RemoteFailure>

    companion object
}
