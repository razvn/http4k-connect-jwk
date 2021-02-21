package net.razvan.http4kconnect.jwk.client

import net.razvan.http4kconnect.jwk.client.action.GetJwkSet

fun Jwk.getJwkSet(url: String) = invoke(GetJwkSet(url))
