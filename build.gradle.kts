buildscript {
    dependencies {
        classpath("com.google.gms:google-services:4.4.0")
    }
}
plugins {
    kotlin("multiplatform").apply(false)
    id("com.android.application").apply(false)
    id("com.android.library").apply(false)
    id("org.jetbrains.compose").apply(false)
    id("com.google.firebase.crashlytics") version "2.9.9" apply false
}


// Top-level build file where you can add configuration options common to all sub-projects/modules.

