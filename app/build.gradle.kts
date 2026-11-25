plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.compose.screenshot)
}

android {
    namespace = "com.apexfission.android.ui.common.sample"
    compileSdk { version = release(37) }
    defaultConfig {
        applicationId = namespace
        minSdk = 28
        targetSdk = 37
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildFeatures { compose = true }
    experimentalProperties["android.experimental.enableScreenshotTest"] = true
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}
kotlin { jvmToolchain(17) }

dependencies {
    implementation(project(":ui-common"))
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.ui.tooling.preview)
    debugImplementation(libs.androidx.compose.ui.tooling)
    screenshotTestImplementation(libs.androidx.compose.ui.tooling)
    screenshotTestImplementation(libs.screenshot.validation.api)
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    androidTestImplementation(libs.androidx.test.runner)
    androidTestImplementation("androidx.test.ext:junit:1.3.0")
    androidTestImplementation("androidx.test.espresso:espresso-intents:3.7.0")
    androidTestImplementation("androidx.test.uiautomator:uiautomator:2.3.0")
}
