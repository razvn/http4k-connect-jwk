import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("kapt")
    `java-library`
    `maven-publish`
}

val http4k_connect_version: String by project
val nimbus_jose_jwt_version: String by project

dependencies {
    implementation(kotlin("stdlib-jdk8"))

    api("org.http4k:http4k-core")
    api("dev.forkhandles:result4k")
    api("com.nimbusds:nimbus-jose-jwt:$nimbus_jose_jwt_version")

    api("org.http4k:http4k-connect-core")
    kapt("org.http4k:http4k-connect-kapt-generator:$http4k_connect_version")

    testImplementation(project(":fake"))
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
