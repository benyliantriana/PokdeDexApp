package id.suspendfun.pokedexapp

import id.suspendfun.pokedexapp.common.libs

plugins {
    `android-library`
    `kotlin-android`
    `kotlin-kapt`
    `kotlin-parcelize`
    `kotlinx-serialization`
    kotlin("kapt")
}

android {
    compileSdk = 35

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
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

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.7.5"
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    packaging {
        resources.excludes.add("META-INF/*")
    }

    tasks.withType<Test> {
        useJUnit()
    }

    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle)
    implementation(libs.androidx.lifecycle.compose)

    implementation(libs.coil)
    implementation(libs.coil.network)

    implementation(libs.compose.ui)

    implementation(libs.hilt)
    implementation(libs.hilt.navigation.compose)
    kapt(libs.hilt.compiler)

    implementation(libs.kotlinx.serialization.json)
    implementation(libs.material3)
    implementation(libs.retrofit)
    implementation(libs.androidx.paging.common.android)
    implementation(libs.androidx.paging.compose)

    implementation(project(":libs:lib_base"))
    implementation(project(":libs:lib_ui"))

    testImplementation(libs.androidx.ui.test.manifest)
    testImplementation(libs.androidx.ui.test.junit)
    testImplementation(libs.junit4)
    testImplementation(libs.robolectric)
}

kapt {
    correctErrorTypes = true
}
