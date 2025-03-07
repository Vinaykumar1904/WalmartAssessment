plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.example.walmartcountryassessment"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.walmartcountryassessment"
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    //dagger
    implementation(libs.dagger.hilt)
    kapt("com.google.dagger:hilt-android-compiler:2.51")
    kapt("androidx.hilt:hilt-compiler:1.2.0")
    //retrofit
    implementation (libs.retrofit)
    //Gson -> json data to java or kotlin format
    implementation (libs.retrofit.gson.converter)
    implementation(libs.okhttp.logging.interceptor)
    implementation (libs.lifecycle.viewmodel.ktx)
    // MockK for Android instrumentation tests (if needed)
    androidTestImplementation("io.mockk:mockk-android:1.13.7")
    // LiveData & ViewModel testing
    testImplementation("androidx.arch.core:core-testing:2.2.0")
    // Turbine for Flow testing (Highly recommended)
    testImplementation("app.cash.turbine:turbine:1.0.0")
    testImplementation ("org.junit.jupiter:junit-jupiter-api:5.7.2")
    testImplementation ("org.junit.jupiter:junit-jupiter-engine:5.7.2")
    testImplementation ("com.google.dagger:hilt-android-testing:2.40.5")
    testImplementation("io.mockk:mockk:1.13.4")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.4")

}