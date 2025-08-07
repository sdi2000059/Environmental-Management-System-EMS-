plugins {
    id("com.android.application")
}

android {
    namespace = "com.example.iot_ui"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.example.iot_ui"
        minSdk = 26
        //noinspection ExpiredTargetSdkVersion
        targetSdk = 30
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
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    // AppCompat - Android Core Library
    implementation("androidx.appcompat:appcompat:1.6.1")

    // Material UI for Android
    implementation("com.google.android.material:material:1.8.0")

    // Layout for UI
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    // View Pager 2 - swipe between fragments
    implementation("androidx.viewpager2:viewpager2:1.0.0")

    // Eclipse Paho - MQTT Library
    implementation("org.eclipse.paho:org.eclipse.paho.client.mqttv3:1.1.0")
    implementation("org.eclipse.paho:org.eclipse.paho.android.service:1.1.1")

    // Unit Testing
    testImplementation("junit:junit:4.13.2")

    // UI Testing
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}