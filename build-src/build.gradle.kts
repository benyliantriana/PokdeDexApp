plugins {
    `kotlin-dsl`
}

dependencies {
    implementation(libs.plugin.android)
    implementation(libs.plugin.kotlin)
    implementation(libs.plugin.kotlin.serialization)

    /**
     * this line contains hack from: https://github.com/gradle/gradle/issues/15383
     * because version catalog can't be accessed from another class beside build.gradle.kts
     */
    implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))
}
