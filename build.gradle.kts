import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    java
    kotlin("jvm") version "1.4.21" apply false
    kotlin("kapt") version "1.4.21" apply false
}


subprojects {
    apply {
        plugin("org.jetbrains.kotlin.jvm")
    }

    version = "0.1.0"

    dependencies {
        val http4kVersion = "4.1.1.2"
        val http4kConnectVersion = "2.13.0.0"
        val forkhandlesVersion = "1.8.1.0"
        val junitVersion = "5.7.0"
        val nimbusJoseJwtVersion = "9.1.3"

        implementation(platform("org.jetbrains.kotlin:kotlin-bom"))
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

        implementation(platform("org.http4k:http4k-bom:$http4kVersion"))
        implementation("org.http4k:http4k-format-moshi")

        implementation(platform("org.http4k:http4k-connect-bom:$http4kConnectVersion"))
        implementation(platform("dev.forkhandles:forkhandles-bom:$forkhandlesVersion"))

        testImplementation("org.http4k:http4k-testing-hamkrest")
        testImplementation("org.http4k:http4k-testing-kotest")

        testImplementation(platform("org.junit:junit-bom:$junitVersion"))
        testImplementation("org.junit.jupiter:junit-jupiter")
    }

    repositories {
        jcenter()
        mavenCentral()
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
        archivesBaseName = project.name
        manifest {
            attributes(
                mapOf(
                    "Implementation-Title" to project.name,
                    "Implementation-Version" to project.version
                )
            )
        }
    }

    java {
        withSourcesJar()
    }
}
