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
        val http4k_version: String by project
        val http4k_connect_version: String by project
        val forkhandles_version: String by project
        val junit_version: String by project

        implementation(platform("org.jetbrains.kotlin:kotlin-bom"))
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

        implementation(platform("org.http4k:http4k-bom:$http4k_version"))
        implementation("org.http4k:http4k-format-moshi")

        implementation(platform("org.http4k:http4k-connect-bom:$http4k_connect_version"))
        implementation(platform("dev.forkhandles:forkhandles-bom:$forkhandles_version"))

        testImplementation("org.http4k:http4k-testing-hamkrest")
        testImplementation("org.http4k:http4k-testing-kotest")

        testImplementation(platform("org.junit:junit-bom:$junit_version"))
        // testImplementation("org.junit.jupiter:junit-jupiter")
        testImplementation("org.junit.jupiter:junit-jupiter-api")
        testImplementation("org.junit.jupiter:junit-jupiter-engine")
    }

    repositories {
        mavenCentral()
        jcenter()
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
        archiveBaseName.set("http4k-connect-jwk")
        archiveAppendix.set(project.name)
        manifest {
            attributes(
                mapOf(
                    "Implementation-Title" to archiveFileName.get().substringBeforeLast('-'),
                    "Implementation-Vendor" to "net.razvan",
                    "Implementation-Version" to project.version
                )
            )
        }
    }

    java {
        withSourcesJar()
    }
}
