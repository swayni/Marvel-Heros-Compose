plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("kotlin-parcelize")
    id("kotlinx-serialization")
    id("com.google.dagger.hilt.android")
    id("kotlin-kapt")
    id("io.realm.kotlin")
}

android {
    namespace = "com.swayni.marvelheroescompose"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.swayni.marvelheroescompose"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField("String", "BASE_URL", properties["baseApiString"].toString())
        buildConfigField("String", "API_KEY", properties["baseApiKey"].toString())
        buildConfigField("String", "PRIVATE_KEY", properties["baseApiPriveteKey"].toString())
        buildConfigField("String", "DATABASE_NAME", properties["baseDatabaseName"].toString())
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

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.14"
    }

    buildFeatures{
        compose = true
        buildConfig = true
    }

    kapt {
        correctErrorTypes = true
    }

    packaging {
        resources {
            excludes.add("META-INF/services/javax.annotation.processing.Processor")
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.material)
    implementation(libs.androidx.ui.tooling.preview.android)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation(libs.material)


    implementation(libs.androidx.activity.compose)
    implementation(libs.compose.material3)
    implementation(libs.compose.material.iconsExtended)

    implementation(libs.coil.compose)

    implementation(libs.hilt.android)
    debugImplementation(libs.androidx.ui.tooling)
    kapt(libs.hilt.compiler)
    implementation(libs.androidx.hilt.navigation.compose)
    kapt(libs.hilt.android.compiler)

    implementation(libs.gson)

    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.adapter.rxjava2)

    //realm
    implementation(libs.realm.kotlin.library.base)
}