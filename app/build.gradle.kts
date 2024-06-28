import java.util.Properties


// Apply plugin: 'com.android.application'
plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
}

// Load properties from local.properties
//val localProperties = Properties()
//val localPropertiesFile = rootProject.file("local.properties")
//if (localPropertiesFile.exists()) {
//    localPropertiesFile.inputStream().use { localProperties.load(it) }
//}
//
//val geminiApiKey = localProperties.getProperty("geminiApiKey") ?: ""

val localProperties = Properties()
val localPropertiesFile = rootProject.file("local.properties")
if (localPropertiesFile.exists()) {
    localPropertiesFile.inputStream().use { localProperties.load(it) }
}

val geminiApiKey = localProperties.getProperty("geminiApiKey") ?: ""

// Save the geminiApiKey to be used in the app module
subprojects {
    project.extensions.extraProperties["geminiApiKey"] = geminiApiKey
}

android {
    namespace = "com.example.dishcraft"
    compileSdk = 34


    defaultConfig {
        applicationId = "com.example.dishcraft"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

//        // Inject the API key into the BuildConfig
       buildConfigField("String", "GEMINI_API_KEY", "\"$geminiApiKey\"")
    }

    //resValue("string", "gemini_api_key", geminiApiKey) // Add this line to generate the resource

    buildFeatures {
        buildConfig = true
    }


    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )


        }
        create("customDebugType") {
            isDebuggable = true
        }


        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_1_8
            targetCompatibility = JavaVersion.VERSION_1_8
        }
        kotlinOptions {
            jvmTarget = "1.8"
        }
        buildFeatures {
            compose = true
        }
        composeOptions {
            kotlinCompilerExtensionVersion = "1.5.14"
        }
        packaging {
            resources {
                excludes += "/META-INF/{AL2.0,LGPL2.1}"
            }
        }
    }

    dependencies {

        implementation(libs.androidx.core.ktx)
        implementation(libs.androidx.lifecycle.runtime.ktx)
        implementation(libs.androidx.activity.compose)
        implementation(platform(libs.androidx.compose.bom))
        implementation(libs.androidx.ui)
        implementation(libs.androidx.ui.graphics)
        implementation(libs.androidx.ui.tooling.preview)
        implementation(libs.androidx.material3)
        implementation(libs.androidx.appcompat)
        implementation(libs.material)
        implementation(libs.androidx.activity)
        implementation(libs.androidx.constraintlayout)
        implementation("org.json:json:20211205") //json dependency
        implementation("com.google.ai.client.generativeai:generativeai:0.7.0") //geminiAPI dependency
        testImplementation(libs.junit)
        androidTestImplementation(libs.androidx.junit)
        androidTestImplementation(libs.androidx.espresso.core)
        androidTestImplementation(platform(libs.androidx.compose.bom))
        androidTestImplementation(libs.androidx.ui.test.junit4)
        debugImplementation(libs.androidx.ui.tooling)
        debugImplementation(libs.androidx.ui.test.manifest)
        implementation ("com.google.code.gson:gson:2.10.1")
        implementation ("androidx.recyclerview:recyclerview:1.3.2")
    }


}