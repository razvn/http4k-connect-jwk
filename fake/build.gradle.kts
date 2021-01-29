plugins {
    kotlin("jvm")
    `java-library`
    `maven-publish`
}

dependencies {
    api("org.http4k:http4k-connect-core-fake")

    implementation(project(":client"))

    implementation("org.http4k:http4k-core")
    implementation("dev.forkhandles:result4k")
    implementation("org.http4k:http4k-connect-core")
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "net.razvan"
            artifactId = "http4k-connect-jwk-fake"
            version = version
            from(components["java"])
        }
    }
}
