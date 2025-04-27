plugins {
    id("id.suspendfun.pokedexapp.feature-convention")
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "id.suspendfun.feature_pokemon"
}

dependencies {
    implementation(project(":libs:lib_network"))
    implementation(project(":libs:lib_room"))
    implementation(libs.room.ktx)
    kapt(libs.room.compiler)
}