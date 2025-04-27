plugins {
    id("id.suspendfun.pokedexapp.lib-convention")
}

android {
    namespace = "id.suspendfun.lib_network"

    packaging {
        resources.excludes.add("META-INF/*")
    }
}

dependencies {
    implementation(libs.retrofit)
    implementation(libs.gson.converter)
}
