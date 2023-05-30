fun properties(key: String) = project.findProperty(key).toString()

plugins {
    id("java")
    id("org.jetbrains.kotlin.jvm") version "1.7.10"
    id("org.jetbrains.intellij") version "1.8.0"
}

group = "com.touhidapps.align"
version = "1.6.4"

repositories {
    mavenCentral()
}

dependencies {
//    implementation "org.jetbrains.kotlin:kotlin-stdlib-jdk8"
}

intellij {
//    version.set("LATEST-EAP-SNAPSHOT")
    version.set("2022.1.1")
//    type.set("IC") // Target IDE Platform

    plugins.set(listOf(/* Plugin Dependencies */))
}

tasks {
    buildSearchableOptions {
        enabled = false
    }

    patchPluginXml {
        version.set("${project.version}")
        sinceBuild.set("213")
//        untilBuild.set("231.*")
    }

    compileKotlin {
        kotlinOptions.jvmTarget = "11"
    }

    compileTestKotlin {
        kotlinOptions.jvmTarget = "11"
    }

    publishPlugin {
        token.set(properties("intellijPublishToken"))
    }

    runIde {
        // Absolute path to the installed targetIDE to use as IDE Development
        // Instance (the "Contents" directory is macOS specific):
        ideDir.set(file("/Applications/Android Studio.app/Contents")) ///Applications/Android\ Studio.app/Contents
    }

    runPluginVerifier {
        ideVersions.addAll("IC-222.4167.29", "AI-222.4167.29")
        downloadDir.set(System.getProperty("user.home") + "/.pluginVerifier/ides")
    }

}


