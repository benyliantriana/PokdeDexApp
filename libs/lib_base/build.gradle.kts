plugins {
    id("id.suspendfun.pokedexapp.lib-convention")
}

android {
    namespace = "id.suspendfun.lib_base"

    packaging {
        resources.excludes.add("META-INF/*")
    }
}

dependencies {
}
