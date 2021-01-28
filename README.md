# JWK Server Client

The JWK connector provides the following Actions:

*  `getJwkSet`: Retrieve the JwkSet from the server 

### Example usage

```kotlin
const val USE_REAL_CLIENT = false

fun main() {
    // we can connect to the real service or the fake (drop in replacement)
    val http: HttpHandler = if (USE_REAL_CLIENT) JavaHttpClient() else FakeJwk()
    
    // create a client
    val jwkClient = Jwk.Http(http.debug())
    
    // if you want to use the real service but use the default client you can use
    val jwkClientDefault  = Jwk.Http()

    val url =  "http://localhost:24588/jwks"    
    // operation return a Result monad of the API type
    val jwkSet: Result<JWKSet, RemoteFailure> = jwkClient.getJwkSet(url)
    
    println(jwkSet)
}
```

### Default Fake port: `24588`

To start:

```
FakeExample().start()
```

You can also specify the port: 
```
FakeExample().start(1337)
```

## Fake services

### List of current key
Lists the current valid key
- method: `GET`
- url: `/jwks`


### Renew the current key
Create a new key
- method: `GET`
- url: `/renew`

You can specify the `kid` name of the key if you want using the *query parameter* `kid=value`
> Ex: `/renew?kid=NewKidOnTheBlock`

### Create a JWT
Create a JWT signed by the current key
- method: `POST`
- url: `/jwt`

You can specify information to use generated JWT payload using the optional JSON body of the query.
```json
{
  "subject": "subject value",
  "issuer": "issuer value",
  "expirationMilliseconds": 60000,
  "claims": {
    "claim1": "claim1 value",
    "claim2": "claim2 value"
  }
}
```
Fields:
 - `subject`: type: `String` - will be used as JWT `sub` payload field
 - `issuer`: type: `String` - will be used as JWT `iss` payload field
 - `expirationMilliseconds`: type `Numeric` - will be used for calculating `exp` field (current_time + expirationMilliseconds)
 - `claims`: type Object - each pair field (type: String)-value (type: Any) will be used as addition claim-value in the jwt

Ex: 
```shell
curl -d '{"issuer": "fakeIss", "subject": "a fake subject", "claims": {"user": "Tom", "age": 38}}' -X POST http://localhost:24588/jwt
```
will create a JWT with the payload:
```json
{
  "iss": "fakeIss",
  "sub": "a fake subject",
  "user": "Tom",
  "age": 38,
  "exp": 1611418451
}
```

## Dependencies for using the lib

In order to use the lib you have to also add the dependencies:
```kotlin
    implementation(platform("org.http4k:http4k-connect-bom:$http4kConnectVersion"))
    implementation("org.http4k:http4k-connect-core")
    implementation("org.http4k:http4k-connect-core-fake")
    implementation("com.nimbusds:nimbus-jose-jwt:$nimbusJoseJwtVersion")
```
