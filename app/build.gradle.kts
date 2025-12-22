import com.google.firebase.appdistribution.gradle.firebaseAppDistribution
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.jetbrains.kotlin.serialization)
    alias(libs.plugins.dagger.hilt)
    alias(libs.plugins.devtools.ksp)
    alias(libs.plugins.stability.analyzer)
    alias(libs.plugins.firebase.appdistribution)
}

val localPropertiesFile = rootProject.file("local.properties")
val localProperties = Properties()
localProperties.load(FileInputStream(localPropertiesFile))

val _appId = localProperties.getProperty("FIREBASE_APP_ID").replace("\"", "")

android {
    namespace = "com.example.portfolio.exemplos"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "com.example.portfolio.exemplos"
        minSdk = 28
        targetSdk = 36
        versionCode = 2
        versionName = "2.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        multiDexEnabled = true

        buildConfigField("String", "FIREBASE_API_KEY", localProperties.getProperty("FIREBASE_API_KEY", ""))
        buildConfigField("String", "FIREBASE_PROJECT_ID", localProperties.getProperty("FIREBASE_PROJECT_ID", ""))
        buildConfigField("String", "FIREBASE_GCM_SENDER_ID", localProperties.getProperty("FIREBASE_GCM_SENDER_ID", ""))
        buildConfigField("String", "FIREBASE_STORAGE_BUCKET", localProperties.getProperty("FIREBASE_STORAGE_BUCKET", ""))
        buildConfigField("String", "FIREBASE_APP_ID", localProperties.getProperty("FIREBASE_APP_ID", ""))

    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
//            firebaseAppDistribution {
//                serviceCredentialsFile = "$rootDir/keys/my-project-portifolio-481618-544f85c5d63c.json"
//            }
        }
        debug {
            isMinifyEnabled = false
            isTestCoverageEnabled = true
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        buildConfig = true
        compose = true
    }
    flavorDimensions += "version"
    productFlavors {
        create("demo") {
            dimension = "version"
            firebaseAppDistribution {
                appId = _appId
                releaseNotes = "Release notes for demo version"
                groups = "DevTesters"
            }
        }

    }

}
kotlin {
    jvmToolchain(11)
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_11)
        freeCompilerArgs.addAll(
            "-XXLanguage:+PropertyParamAnnotationDefaultTargetMode",
            "-Xjvm-default=all",
            "-Xopt-in=kotlin.RequiresOptIn",
            "-Xcontext-parameters"
        )
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.material.icons.extended)

    implementation(libs.google.accompanist.permissions)
    implementation(libs.coil.compose)

    implementation(libs.androidx.multidex)

//    implementation(libs.androidx.core.splash)

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.auth)

    // navigation3
    implementation(libs.androidx.navigation3.ui)
    implementation(libs.androidx.navigation3.runtime)
    implementation(libs.androidx.lifecycle.viewmodel.navigation3)
    implementation(libs.androidx.material3.adaptive.navigation3)
    implementation(libs.kotlinx.serialization.core)
    implementation(libs.androidx.compose.runtime)

    implementation(libs.kotlinx.collections.immutable)

    // hilt
    implementation(libs.androidx.hilt.navigation.compose)

    //dagger
    implementation(libs.dagger.hilt)
    ksp(libs.dagger.ksp)

    // ktor
    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.android)
    implementation(libs.ktor.client.serialization)
    implementation(libs.ktor.client.content.negotiation)
    implementation(libs.ktor.serialization.kotlinx.json)
    implementation(libs.ktor.client.logging)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}