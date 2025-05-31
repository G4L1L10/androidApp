plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "ai.bandroom"
    compileSdk = 35

    defaultConfig {
        applicationId = "ai.bandroom"
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
    // Android Core
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)

    // Jetpack Compose
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    // OkHttp (Core)
    implementation("com.squareup.okhttp3:okhttp:4.12.0")

// ✅ JavaNetCookieJar is in this artifact:
    implementation("com.squareup.okhttp3:okhttp-urlconnection:4.12.0")


    // Jetpack Compose Navigation
    implementation("androidx.navigation:navigation-compose:2.7.5")

    // Lifecycle Compose Extensions
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.6.2")

    // DataStore for token storage
    implementation("androidx.datastore:datastore-preferences:1.0.0")

    // Retrofit for API calls
    implementation(libs.retrofit)
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    // OkHttp for networking + cookies
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")

    // Dependency Injection: Koin for Compose
    implementation("io.insert-koin:koin-androidx-compose:3.5.3")

    // Material Icons Extended (for icons like School, Person, etc.)
    implementation("androidx.compose.material:material-icons-extended")


    // Unit & UI Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}
