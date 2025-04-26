plugins {
    id("id.suspendfun.pokedexapp.lib-convention")
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "id.suspendfun.lib_ui"

    packaging {
        resources.excludes.add("META-INF/*")
    }
}

dependencies {
    implementation(libs.activity.compose)
    implementation(libs.material3)
}
