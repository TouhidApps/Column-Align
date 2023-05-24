plugins {
    id("java")
    id("org.jetbrains.kotlin.jvm") version "1.7.10"
    id("org.jetbrains.intellij") version "1.8.0"
}

group "com.touhidapps.align"
version "1.6.0"

repositories {
    mavenCentral()
}

dependencies {
//    implementation "org.jetbrains.kotlin:kotlin-stdlib-jdk8"
}


//publishPlugin {
//    channels = "Stable"
//}

// See https://github.com/JetBrains/gradle-intellij-plugin/
intellij {
     //   updateSinceUntilBuild = false
//    version '2019.1'   

}




//// See https://github.com/JetBrains/gradle-intellij-plugin/
//intellij {
//    //    updateSinceUntilBuild false
//    // Define IntelliJ Platform against which to build the plugin project.
//    version '192.7142.36'  // Same IntelliJ IDEA version (2019.1.4) as target 3.5 Android Studio   
//    type 'IC'              // Use IntelliJ IDEA CE because it's the basis of the IntelliJ Platform   
//    // Require the Android plugin, Gradle will match the plugin version to intellij.version 
//    plugins 'android'
//      updateSinceUntilBuild = false
//}
//
//runIde {
//    // Absolute path to installed target 3.5 Android Studio to use as IDE Development Instance
//    // The "Contents" directory is macOS specific.
//    ideDirectory '/Applications/Android Studio.app/Contents'
//}
intellij {
    version.set("2021.3.3")
    type.set("IC") // Target IDE Platform

    plugins.set(listOf(/* Plugin Dependencies */))
}

//compileKotlin {
//    kotlinOptions.jvmTarget = "11"
//}
//compileTestKotlin {
//    kotlinOptions.jvmTarget = "11"
//}


//publishPlugin {
//    token = intellijPublishToken
//}