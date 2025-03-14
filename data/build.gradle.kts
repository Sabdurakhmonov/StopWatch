plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)

    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "uz.abdurakhmonov.data"
    compileSdk = 35

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
}

dependencies {
    //hilt
    implementation(libs.hilt.android)
    kapt(libs.dagger.hilt.android.compiler)
    implementation (libs.androidx.hilt.navigation.compose)

    //data store
    implementation (libs.androidx.datastore.preferences)
    //json
    implementation(libs.gson)
    implementation(libs.kotlinx.serialization.json)
    //coroutine
    implementation(libs.kotlinx.coroutines.core)
}