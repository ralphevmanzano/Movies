// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id(Plugins.androidApplication) version Versions.pluginAndroid apply false
    id(Plugins.androidLibrary) version Versions.pluginAndroid apply false
    id(Plugins.kotlinAndroid) version Versions.pluginKotlin apply false
    id(Plugins.hiltAndroid) version Versions.pluginHilt apply false
    kotlin(Plugins.jvm) version Versions.pluginJvm
    kotlin(Plugins.serialization) version Versions.pluginSerialization
}

buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        val kotlinVersion = "1.8.10"
        classpath(Deps.safeArgsGradlePlugin)
        classpath(kotlin("gradle-plugin", version = kotlinVersion))
        classpath(kotlin("serialization", version = kotlinVersion))
    }
}