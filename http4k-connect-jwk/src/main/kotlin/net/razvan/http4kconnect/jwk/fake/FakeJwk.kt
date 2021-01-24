package net.razvan.http4kconnect.jwk.fake

import com.nimbusds.jose.jwk.ECKey
import org.http4k.connect.ChaosFake
import org.http4k.connect.storage.InMemory
import org.http4k.connect.storage.Storage
import org.http4k.core.*
import org.http4k.routing.routes

class FakeJwk : ChaosFake() {
    private val storage: Storage<ECKey> = Storage.InMemory()
    override val app: HttpHandler = routes(
        listJwkSet(storage),
        renewJwkSet(storage),
        createJwt(storage)
    )
}


fun main() {
    FakeJwk().start()
}

