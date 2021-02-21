package net.razvan.http4kconnect.jwk.client.action

import dev.forkhandles.result4k.Result
import org.http4k.connect.Action
import org.http4k.connect.RemoteFailure

interface JwkAction<T> : Action<Result<T, RemoteFailure>>
