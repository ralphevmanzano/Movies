plugins {
    id(Plugins.androidLibrary)
    id(Plugins.kotlinAndroid)
    id(Plugins.kotlinxSerialization)
    kotlin(Plugins.kapt)
}

android {
    namespace = "com.ralphevmanzano.movies.data"
    compileSdk = Config.compileSdkVersion

    defaultConfig {
        minSdk = Config.minSdkVersion
        targetSdk = Config.targetSdkVersion

        testInstrumentationRunner = Config.androidTestInstrumentation
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
}

dependencies {
    implementation(project(Modules.domain))
    implementation(project(Modules.shared))

    implementation(Deps.roomRuntime)
    implementation(Deps.roomKtx)
    kapt(Deps.roomCompiler)

    implementation(Deps.sqlcipher)
    implementation(Deps.sqlite)

    implementation(Deps.hilt)
    kapt(Deps.hiltCompiler)

    implementation(Deps.retrofit)
    implementation(Deps.retrofitKotlinxSerialization)
    implementation(Deps.paging)
    implementation(Deps.pagingKtx)
    implementation(Deps.sandwich)

    debugImplementation(Deps.chucker)
    releaseImplementation(Deps.chuckerNoOp)

    implementation(Deps.coroutinesAndroid)
    implementation(Deps.coroutinesCore)
    implementation(Deps.kotlinxSerialization)
    implementation(Deps.timber)
}