plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("maven-publish")
}

android {
    namespace = "com.akaun.kt.mobile"
    compileSdk = 34

    defaultConfig {
        minSdk = 21
        targetSdk = 34

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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
}

dependencies {
    implementation(libs.material3)
    implementation(libs.hilt.navigation.compose)
    debugImplementation(libs.ui.tooling)
    implementation(libs.ui.tooling.preview)

    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    api (libs.akn.client.sdk)

}

publishing {
    publications {
        register<MavenPublication>("release") {
            groupId = "com.github.bigledger"
            artifactId = "akn-kotlin-sdk-mobile-lib"
            version = "0.0.9"

            afterEvaluate {
                from(components["release"])
            }
        }
    }
}