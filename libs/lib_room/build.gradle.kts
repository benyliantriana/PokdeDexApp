plugins {
    id("id.suspendfun.pokedexapp.lib-convention")
}
android {
    namespace = "id.suspendfun.lib_room"
}

dependencies {
    implementation(libs.room.ktx)
    implementation(libs.room.paging)
    implementation(libs.androidx.paging.common.android)
    kapt(libs.room.compiler)
}
