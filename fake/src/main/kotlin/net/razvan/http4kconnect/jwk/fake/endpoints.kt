package net.razvan.http4kconnect.jwk.fake

import com.nimbusds.jose.JOSEObjectType
import com.nimbusds.jose.JWSAlgorithm
import com.nimbusds.jose.JWSHeader
import com.nimbusds.jose.crypto.ECDSASigner
import com.nimbusds.jose.jwk.Curve
import com.nimbusds.jose.jwk.ECKey
import com.nimbusds.jose.jwk.KeyUse
import com.nimbusds.jose.jwk.gen.ECKeyGenerator
import com.nimbusds.jwt.JWTClaimsSet
import com.nimbusds.jwt.SignedJWT
import org.http4k.connect.storage.Storage
import org.http4k.core.*
import org.http4k.format.Moshi.auto
import org.http4k.lens.Query
import org.http4k.lens.string
import org.http4k.routing.bind
import java.util.*

fun listJwkSet(storage: Storage<ECKey>) = "/jwks" bind Method.GET to {
    Response(Status.OK).with(
        bodyJwksResponseLens of JWKResponse(listOf(storage.getOrDefault(KEY).toJSONObject()))
    )
}

fun renewJwkSet(storage: Storage<ECKey>) = "/renew" bind Method.GET to { req ->
    storage.remove(KEY)
    storage.getOrDefault(KEY, queryKidLens(req)).let { key ->
        Response(Status.OK).with(
            bodyJwksResponseLens of JWKResponse(listOf(key.toJSONObject()))
        )
    }
}

fun createJwt(storage: Storage<ECKey>) = "/jwt" bind Method.POST to { req ->
    val jwt = createJWT(bodyJwtRequestLens(req), storage.getOrDefault(KEY))
    Response(Status.OK).with(
        bodyJwtResponseLens of jwt
    )
}

private val bodyJwksResponseLens by lazy { Body.auto<JWKResponse>().toLens() }
private val queryKidLens by lazy { Query.defaulted("kid", UUID.randomUUID().toString()) }
private val bodyJwtRequestLens by lazy { Body.auto<JWTRequest>().toLens() }
private val bodyJwtResponseLens by lazy { Body.string(ContentType.TEXT_PLAIN).toLens() }

private const val KEY = "key"

private fun generateKey(localKid: String = UUID.randomUUID().toString()) = ECKeyGenerator(Curve.P_384)
    .keyUse(KeyUse.SIGNATURE)
    .keyID(localKid)
    .generate()

private fun createJWT(input: JWTRequest, key: ECKey): String {
    val claimsBuilder = JWTClaimsSet.Builder()
        .subject(input.subject)
        .issuer(input.issuer)
        .expirationTime(Date(Date().time + input.exipirationMilliseconds))
    input.claims.forEach { (k, v) ->
        claimsBuilder.claim(k, v)
    }
    val claimsSet = claimsBuilder.build()
    val headers = JWSHeader.Builder(JWSAlgorithm.ES384).keyID(key.keyID).type(JOSEObjectType.JWT).build()
    val signedJWT = SignedJWT(
        headers,
        claimsSet
    )
    signedJWT.sign(ECDSASigner(key))

    return signedJWT.serialize()
}

private fun Storage<ECKey>.getOrDefault(key: String, kid: String? = null): ECKey {
    return this[key] ?: run {
        val k = kid?.let { generateKey(kid) } ?: generateKey()
        this[key] = k
        k
    }
}