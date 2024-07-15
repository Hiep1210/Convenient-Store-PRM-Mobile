plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.convenientstoremobile"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.convenientstoremobile"
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

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation("com.github.bumptech.glide:glide:4.16.0")
    implementation("de.hdodenhof:circleimageview:3.1.0")
    implementation("com.google.code.gson:gson:2.11.0")
    implementation ("com.squareup.retrofit2:retrofit:2.1.0")
    implementation("com.squareup.retrofit2:converter-gson:2.1.0")
    implementation ("com.intuit.sdp:sdp-android:1.1.0")
    implementation ("com.squareup.picasso:picasso:2.71828")
    implementation ("com.github.bumptech.glide:glide:4.14.2")
    implementation ("com.intuit.sdp:sdp-android:1.1.0")
    implementation("com.github.ybq:Android-SpinKit:1.4.0")
    implementation ("com.google.android.gms:play-services-auth:20.2.0")
    implementation ("com.airbnb.android:lottie:3.4.1")

    //load image form url
    implementation ("com.github.bumptech.glide:glide:4.13.2")
    implementation(files("D:\\Ki_8\\PRM\\ConvenientStoreMobile\\app\\libs\\mail.jar"))
    implementation(files("D:\\Ki_8\\PRM\\ConvenientStoreMobile\\app\\libs\\activation.jar"))
    implementation(files("D:\\Ki_8\\PRM\\ConvenientStoreMobile\\app\\libs\\additionnal.jar"))

    //gg sign in
//    implementation(libs.credentials)
//    implementation(libs.credentials.play.services.auth)
//    implementation(libs.googleid)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}
