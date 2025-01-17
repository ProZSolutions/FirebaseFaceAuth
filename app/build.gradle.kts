plugins {
    id("com.android.application")
    id("com.google.gms.google-services")

}

android {
    namespace = "in.proz.apms"
    compileSdk = 34

    defaultConfig {
        applicationId = "in.proz.apms"
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    //implementation(platform("com.google.firebase:firebase-bom:32.7.4"))
    implementation("androidx.biometric:biometric:1.0.0-rc01")


/*    implementation(platform("com.google.firebase:firebase-bom:32.8.0"))
    implementation("com.google.firebase:firebase-analytics")
    implementation ("com.google.firebase:firebase-ml-vision-face-model:20.0.1")
    implementation ("com.google.firebase:firebase-ml-vision:24.0.3")*/


    implementation("com.google.android.gms:play-services-vision:20.1.3")
    implementation("com.google.android.gms:play-services-vision-common:19.1.3")
    implementation("com.google.android.gms:play-services-vision-face-contour-internal:16.1.0")
    // implementation 'com.google.mlkit:face-detection:16.1.6'
    implementation("com.google.firebase:firebase-ml-vision:24.1.0")


    implementation("com.google.firebase:firebase-core:21.1.1")

    implementation ("com.karumi:dexter:6.2.3")


}