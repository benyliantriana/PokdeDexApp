pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "PokeDexApp"

include(":app")
include(":libs:lib_base")
include(":features:feature_pokemon")

includeBuild("build-src")

// this line is required, somehow the convention has some blocking process, even no test classes there
gradle.startParameter.excludedTaskNames.addAll(listOf(":build-src:testClasses"))
include(":libs:lib_ui")
include(":libs:lib_network")
