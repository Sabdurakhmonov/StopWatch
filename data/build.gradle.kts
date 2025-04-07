import org.gradle.kotlin.dsl.implementation

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

    //Chuck
    debugImplementation("com.github.chuckerteam.chucker:library:4.0.0")
    releaseImplementation("com.github.chuckerteam.chucker:library-no-op:3.5.2")

    //RETROFIT
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")

    //Swipe refresh
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")

    //location
    implementation ("com.google.android.gms:play-services-location:21.0.1")


}