plugins {
    id(Plugins.androidApplication)
    id(Plugins.kotlinAndroid)
    id(Plugins.safeArgs)
    id(Plugins.hiltAndroid)
    kotlin(Plugins.kapt)
}

android {
    namespace = "com.ralphevmanzano.movies"
    compileSdk = Config.compileSdkVersion

    defaultConfig {
        applicationId = Config.applicationId
        minSdk = Config.minSdkVersion
        targetSdk = Config.targetSdkVersion
        versionCode = Config.versionCode
        versionName = Config.versionName

        testInstrumentationRunner = Config.androidTestInstrumentation
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
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
        viewBinding = true
    }
}

dependencies {
    implementation(project(Modules.shared))
    implementation(project(Modules.data))
    implementation(project(Modules.featureHome))
    implementation(project(Modules.featureDetails))
    implementation(project(Modules.featureFavourites))
    implementation(project(Modules.featureSearch))

    implementation(Deps.androidxCore)
    implementation(Deps.appCompat)
    implementation(Deps.material)
    implementation(Deps.constraintLayout)

    // navigation
    implementation(Deps.navigationFragment)
    implementation(Deps.navigationUI)

    // hilt
    implementation(Deps.hilt)
    kapt(Deps.hiltCompiler)

    // chucker
    debugImplementation(Deps.chucker)
    releaseImplementation(Deps.chuckerNoOp)

    implementation(Deps.timber)
}