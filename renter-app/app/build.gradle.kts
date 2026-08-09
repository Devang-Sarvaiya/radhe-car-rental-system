plugins {
    id("com.android.application")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.caronrentrenter"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.caronrentrenter"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    packagingOptions {
        resources.excludes.add("META-INF/*")
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
    implementation("com.google.firebase:firebase-auth:22.3.1")
    implementation("com.google.firebase:firebase-database:20.3.1")
    implementation("com.google.firebase:firebase-inappmessaging-display:20.4.0")
    implementation("com.google.firebase:firebase-storage:20.3.0")
    implementation("androidx.recyclerview:recyclerview:1.3.2")
    implementation("androidx.activity:activity:1.8.2")
    implementation("androidx.biometric:biometric:1.1.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation("com.squareup.picasso:picasso:2.71828")
    implementation("com.github.denzcoskun:ImageSlideshow:0.1.2")
    implementation("com.google.android.gms:play-services-tasks:18.1.0")

    implementation("com.github.bumptech.glide:glide:4.16.0") {
        exclude("androidx.coordinatorlayout")
//        exclude group: "androidx.coordinatorlayout"
    }
    annotationProcessor("com.github.bumptech.glide:compiler:4.12.0") {
        exclude("androidx.coordinatorlayout")

//        exclude group: 'androidx.coordinatorlayout'
    }

    implementation("com.google.android.gms:play-services-location:21.2.0")
    implementation("com.google.android.gms:play-services-maps:18.2.0")

    implementation("org.osmdroid:osmdroid-android:6.1.11")

    implementation("com.facebook.shimmer:shimmer:0.5.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")

    implementation("com.itextpdf:itext7-core:7.1.8")
    // This is for payment
    implementation("com.razorpay:checkout:1.6.33")
    implementation("com.squareup.okhttp3:okhttp:4.10.0")

    implementation("com.github.AtifSayings:Animatoo:1.0.1")
    implementation("de.hdodenhof:circleimageview:3.1.0")
    implementation("com.afollestad.material-dialogs:core:3.3.0")
    implementation("com.makeramen:roundedimageview:2.3.0")


    implementation("com.sun.mail:android-mail:1.6.7")
    implementation("com.sun.mail:android-activation:1.6.7")
}
