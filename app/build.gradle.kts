plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
    alias(libs.plugins.compose)
    kotlin("plugin.serialization")
}

android {
    namespace = "com.mariiadeveloper.unicornmessenger"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.mariiadeveloper.unicornmessenger"
        minSdk = 24
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    // HILT
    implementation(libs.hilt.android)
    implementation(libs.androidx.runtime.livedata)
    ksp(libs.hilt.android.compiler)
    // ROOM
    implementation(libs.room.ktx)
    implementation(libs.room.runtime)
    ksp(libs.androidx.room.compiler)
    // GLIDE
    implementation(libs.glide)
    // RETROFIT
    implementation(libs.retrofit)
    implementation(libs.retrofit.converterJson)
    // OKHTTP
    implementation(libs.okhttp)
    // SERIALIZATION
    implementation(libs.kotlinx.serialization)
    // NAVIGATION
    implementation(libs.androidx.navigation.compose)
    // DATASTORE
    implementation(libs.datastore.preferences)
    // COUNTRY PICKER
    implementation(libs.komposecountrycodepicker)
    //implementation("io.github.joelkanyi:komposecountrycodepicker:1.1.2")
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}