buildscript {
    dependencies {
        classpath("com.google.firebase:firebase-crashlytics-gradle:3.0.2")
    }
}
@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.kotlinAndroid) apply false
    alias(libs.plugins.hiltAndroid) apply false
    alias(libs.plugins.gmsGoogleService) apply false
}

tasks.register("clean", Delete::class).configure {
    delete(rootProject.buildDir)
}