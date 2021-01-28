plugins {
    id("org.jetbrains.kotlin.jvm") version "1.4.21-2"
    id("org.jetbrains.kotlin.kapt") version "1.4.21-2"
    `java-library`
    `maven-publish`
}

version = "0.1.0"

repositories {
    jcenter()
}

val http4kVersion = "4.1.1.2"
val http4kConnectVersion = "2.13.0.0"
val forkhandlesVersion = "1.8.1.0"
val junitVersion = "5.7.0"
val nimbusJoseJwtVersion = "9.1.3"

dependencies {
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    implementation(platform("org.http4k:http4k-bom:$http4kVersion"))
    implementation(platform("dev.forkhandles:forkhandles-bom:$forkhandlesVersion"))

    api("org.http4k:http4k-core")
    api("dev.forkhandles:result4k")
    kapt("org.http4k:http4k-connect-kapt-generator:$http4kConnectVersion")

    implementation("org.http4k:http4k-format-moshi")
    implementation(platform("org.http4k:http4k-connect-bom:$http4kConnectVersion"))

    api("org.http4k:http4k-connect-core")
    api("org.http4k:http4k-connect-core-fake")
    api("com.nimbusds:nimbus-jose-jwt:$nimbusJoseJwtVersion")

    testImplementation("org.http4k:http4k-testing-hamkrest")
    testImplementation("org.http4k:http4k-testing-kotest")

    testImplementation(platform("org.junit:junit-bom:$junitVersion"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
java.sourceCompatibility = JavaVersion.VERSION_11
tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        jvmTarget = "11"
    }
}

tasks.jar {
    manifest {
        attributes(mapOf("Implementation-Title" to project.name,
            "Implementation-Version" to project.version))
    }
}

java {
    withSourcesJar()
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "net.razvan"
            artifactId = "http4k-connect-jwk"
            version = "0.1.0.0"
            from(components["java"])
        }
    }
}
