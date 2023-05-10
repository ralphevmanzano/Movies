plugins {
    id(Plugins.androidApplication)
    id(Plugins.kotlinAndroid)
    id(Plugins.safeArgs)
    id(Plugins.hiltAndroid)
    kotlin(Plugins.kapt)
}

android {
    compileSdk = Config.compileSdkVersion

    defaultConfig {
        minSdk = Config.minSdkVersion
        targetSdk = Config.targetSdkVersion

        testInstrumentationRunner = Config.androidTestInstrumentation
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

dependencis {
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

    // viewmodel and livedata
    implementation(Deps.lifecycleViewModel)
    implementation(Deps.lifecycleLiveData)
    kapt(Deps.lifecycleCompiler)

    // coroutines
    implementation(Deps.coroutines)

    testImplementation(Deps.junit)
    androidTestImplementation(Deps.junitTest)
    androidTestImplementation(Deps.espresso)
}

kapt {
    correctErrorTypes = true
}