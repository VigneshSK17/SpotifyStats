plugins {
    id("com.android.application")

    id("com.google.gms.google-services")
}

android {
    namespace = "com.t1r2340.spotifystats"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.t1r2340.spotifystats"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        manifestPlaceholders["redirectSchemeName"] = "spotifystats"
        manifestPlaceholders["redirectHostName"] = "auth"

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

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    implementation("androidx.recyclerview:recyclerview:1.3.0")
    implementation("androidx.navigation:navigation-fragment:2.7.6")
    implementation("androidx.navigation:navigation-ui:2.7.6")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    implementation(platform("com.google.firebase:firebase-bom:32.7.3"))
    implementation("com.google.firebase:firebase-firestore")
    implementation("com.google.firebase:firebase-auth")
    implementation("com.firebaseui:firebase-ui-auth:7.2.0")

    implementation("com.spotify.android:auth:2.1.1")
    implementation("com.squareup.okhttp3:okhttp:4.9.3")

    implementation("com.google.code.gson:gson:2.8.6")
}