plugins {
    id("org.jetbrains.kotlin.jvm") version "1.4.21-2"

    `java-library`
}

version = "0.1.0"

repositories {
    jcenter()
}

val http4kVersion = "4.1.1.1"
val http4kConnectVersion = "2.13.0.0"
val junitVersion = "5.7.0"
val nimbusJoseJwtVersion = "9.1.3"

dependencies {
    // Align versions of all Kotlin components
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))

    // Use the Kotlin JDK 8 standard library.
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    // This dependency is used internally, and not exposed to consumers on their own compile classpath.
    implementation("com.google.guava:guava:29.0-jre")

    // Use the Kotlin test library.
    testImplementation("org.jetbrains.kotlin:kotlin-test")

    // Use the Kotlin JUnit integration.
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit")

    // This dependency is exported to consumers, that is to say found on their compile classpath.
    api("org.apache.commons:commons-math3:3.6.1")


    implementation(platform("org.http4k:http4k-bom:$http4kVersion"))
    implementation("org.http4k:http4k-core")

    implementation("org.http4k:http4k-format-moshi")
    implementation(platform("org.http4k:http4k-connect-bom:$http4kConnectVersion"))
    implementation("org.http4k:http4k-connect-core")
    implementation("org.http4k:http4k-connect-core-fake")
    implementation("com.nimbusds:nimbus-jose-jwt:$nimbusJoseJwtVersion")

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