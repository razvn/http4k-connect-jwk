import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("kapt")
    `java-library`
    `maven-publish`
}


val http4kConnectVersion = "2.13.0.0"
val nimbusJoseJwtVersion = "9.1.3"
dependencies {
    api("org.http4k:http4k-core")
    api("dev.forkhandles:result4k")
    api("com.nimbusds:nimbus-jose-jwt:$nimbusJoseJwtVersion")

    api("org.http4k:http4k-connect-core")
    kapt("org.http4k:http4k-connect-kapt-generator:$http4kConnectVersion")

    implementation(kotlin("stdlib-jdk8"))
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "net.razvan"
            artifactId = "http4k-connect-jwk-client"
            version = version
            from(components["java"])
        }
    }
}

val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = "11"
}
val compileTestKotlin: KotlinCompile by tasks
compileTestKotlin.kotlinOptions {
    jvmTarget = "11"
}
