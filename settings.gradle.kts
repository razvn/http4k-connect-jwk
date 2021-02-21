includeSystem("jwk")

fun includeSystem(system: String) {
    val projectName = "http4k-connect-$system"
    includeWithName(projectName, "$system/client")
    includeWithName("$projectName-fake", "$system/fake")
}

fun includeWithName(projectName: String, file: String) {
    include(":$projectName")
    project(":$projectName").projectDir = File(file)
}

pluginManagement {
    repositories {
        gradlePluginPortal()
    }
}
