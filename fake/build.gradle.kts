plugins {
    id("http4k-connect-jwk.fake")
    kotlin("jvm")
    `maven-publish`
}

dependencies {
    api("org.http4k:http4k-connect-core-fake")

    implementation(project(":client"))
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
