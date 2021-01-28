pluginManagement {
    repositories {
        mavenCentral()
        maven("https://plugins.gradle.org/m2/")
    }
}
rootProject.name = "htt4k-connect-jwk"
include("client", "fake")
