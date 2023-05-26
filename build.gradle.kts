fun properties(key: String) = project.findProperty(key).toString()

plugins {
    id("java")
    id("org.jetbrains.kotlin.jvm") version "1.7.10"
    id("org.jetbrains.intellij") version "1.8.0"
}

group = "com.touhidapps.align"
version = "1.6.3"

repositories {
    mavenCentral()
}

dependencies {
//    implementation "org.jetbrains.kotlin:kotlin-stdlib-jdk8"
}

intellij {
//    version.set("2019.1.4")
    version.set("2021.3.3")
//    type.set("IC") // Target IDE Platform

    plugins.set(listOf(/* Plugin Dependencies */))
}

tasks {
    buildSearchableOptions {
        enabled = false
    }

    patchPluginXml {
        version.set("${project.version}")
//        sinceBuild.set("213")
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

}


