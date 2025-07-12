plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.dee.core_network"
    compileSdk = 35

    defaultConfig {
        minSdk = 28

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

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    api(libs.retrofit)
    api(libs.converter.moshi)
    api(platform(libs.okhttp.bom))
    api(libs.okhttp)
    api(libs.logging.interceptor)
    api(libs.moshi.kotlin)
    ksp(libs.moshi.kotlin.codegen)

    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)
    debugApi(libs.library.chucker)
    releaseApi(libs.library.chucker.no.op)

    // test
    testImplementation(libs.mockwebserver)


}