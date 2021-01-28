import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("http4k-connect-jwk.client")
    kotlin("kapt")
    kotlin("jvm")
    `maven-publish`
}

val http4kConnectVersion = "2.13.0.0"
dependencies {

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
repositories {
    mavenCentral()
}
val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = "1.8"
}
val compileTestKotlin: KotlinCompile by tasks
compileTestKotlin.kotlinOptions {
    jvmTarget = "1.8"
}