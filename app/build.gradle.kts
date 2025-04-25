plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.hilt)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.detekt)
    kotlin("kapt")
}

android {
    namespace = "id.suspendfun.pokedexapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "id.suspendfun.pokedexapp"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.7.5"
    }
    packaging {
        resources.excludes.add("META-INF/*")
    }
}

dependencies {
    implementation(libs.activity.compose)
    implementation(libs.hilt)
    kapt(libs.hilt.compiler)
}

kapt {
    correctErrorTypes = true
}
