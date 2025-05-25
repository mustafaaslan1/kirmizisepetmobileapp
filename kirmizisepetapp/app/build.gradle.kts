plugins {
    alias(libs.plugins.android.application)
    id("com.google.gms.google-services") // Firebase için eklendi
}

android {
    namespace = "com.example.kirmizisepetapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.kirmizisepetapp"
        minSdk = 25
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
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation ("com.github.bumptech.glide:glide:4.16.0")
    annotationProcessor("com.github.bumptech.glide:compiler:4.16.0")
    implementation("com.airbnb.android:lottie:6.1.0")
    // 🔥 Firebase Authentication
    implementation("com.google.firebase:firebase-auth:22.3.1")
    // 🔥 Firebase Realtime Database (isteğe bağlı)
    implementation("com.google.firebase:firebase-database:20.3.0")
}

