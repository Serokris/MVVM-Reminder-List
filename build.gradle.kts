buildscript {
    extra.apply {
        set("kotlinVersion", "1.5.20")
        set("roomVersion", "2.4.0-rc01")
        set("navigationVersion", "2.3.5")
        set("retrofitVersion", "2.9.0")
        set("daggerVersion", "2.39.1")
        set("gsonVersion", "2.8.6")
    }

    val kotlinVersion: String by extra
    val navigationVersion: String by extra

    repositories {
        mavenCentral()
        google()
    }

    dependencies {
        classpath("com.android.tools.build:gradle:7.0.3")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:$navigationVersion")
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

tasks.register<Delete>("clean") {
    delete(rootProject.buildDir)
}