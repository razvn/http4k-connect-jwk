import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.4.30"
    `maven-publish`
}

allprojects {
    apply(plugin="org.jetbrains.kotlin.jvm")
    apply(plugin="maven-publish")
    repositories {
        mavenCentral()
    }
    group = "net.razvan.kotlin"
    version = "1.0.0.0"
    description = project.name

    val http4k_version: String by project
    val http4k_connect_version: String by project
    val forkhandles_version: String by project
    val junit_version: String by project

    dependencies {
        implementation(platform("org.jetbrains.kotlin:kotlin-bom"))
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

        implementation(platform("org.http4k:http4k-bom:$http4k_version"))
        implementation(platform("dev.forkhandles:forkhandles-bom:$forkhandles_version"))

        api("org.http4k:http4k-core")
        api("dev.forkhandles:result4k")

        implementation("org.http4k:http4k-format-moshi")

        implementation(platform("org.http4k:http4k-connect-bom:$http4k_connect_version"))
        api("org.http4k:http4k-connect-core")

        testImplementation("org.http4k:http4k-testing-hamkrest")
        testImplementation("org.http4k:http4k-testing-kotest")

        testImplementation(platform("org.junit:junit-bom:$junit_version"))
        testImplementation("org.junit.jupiter:junit-jupiter")

        if (project.name.endsWith("fake")) {
            val clientProject = project.name.removeSuffix("-fake")
            // println("clientProject: $clientProject")
            api("org.http4k:http4k-connect-core-fake")
            api(project(":$clientProject"))
            testImplementation(project(":$clientProject", "testArtifacts"))
        }
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }
    java.sourceCompatibility = JavaVersion.VERSION_11

    val compileKotlin: KotlinCompile by tasks
    compileKotlin.kotlinOptions {
        jvmTarget = "11"
    }
    val compileTestKotlin: KotlinCompile by tasks
    compileTestKotlin.kotlinOptions {
        jvmTarget = "11"
    }

    tasks.jar {
        manifest {
            attributes(mapOf("Implementation-Title" to project.name,
                "Implementation-Version" to project.version))
        }
    }

    val testConfig = configurations.create("testArtifacts") {
        extendsFrom(configurations["testRuntime"])
    }

    tasks.register("testJar", Jar::class.java) {
        dependsOn("testClasses")
        archiveClassifier.set("test")
        from(sourceSets["test"].output)
    }

    artifacts {
        add("testArtifacts", tasks.named<Jar>("testJar") )
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
}
