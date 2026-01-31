import xyz.jpenilla.resourcefactory.paper.PaperPluginYaml

plugins {
    id("java")
    id("com.gradleup.shadow") version "9.3.1"
    id("xyz.jpenilla.run-paper") version "3.0.2"
    id("xyz.jpenilla.resource-factory-paper-convention") version "1.3.1"
}

group = "com.bindglam.libertasshop"
version = "1.0-SNAPSHOT"

val groupString = group.toString()

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://maven.devs.beer/")
    maven("https://repo.codemc.org/repository/maven-public/")
    maven("https://jitpack.io")
    maven("https://maven.citizensnpcs.co/repo")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.20.1-R0.1-SNAPSHOT")
    compileOnly("dev.lone:api-itemsadder:4.0.10")
    implementation("dev.jorel:commandapi-bukkit-shade:10.1.2")
    compileOnly("com.github.bindglam.GoldEngine:api:0.0.3")
    compileOnly("net.citizensnpcs:citizensapi:2.0.41-SNAPSHOT")
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
}

paperPluginYaml {
    main = "$groupString.LibertasShopPlugin"
    authors.add("bindglam")
    apiVersion = "1.20"
    dependencies {
        server(name = "GoldEngine", load = PaperPluginYaml.Load.BEFORE, required = false, joinClasspath = true)
        server(name = "ItemsAdder", load = PaperPluginYaml.Load.BEFORE, required = false, joinClasspath = true)
        server(name = "Citizens", load = PaperPluginYaml.Load.BEFORE, required = false, joinClasspath = true)
    }
}

tasks {
    runServer {
        minecraftVersion("1.20.1")

        downloadPlugins {
            github("bindglam", "GoldEngine", "0.0.3", "GoldEngine-0.0.3.jar")
        }
    }

    jar {
        finalizedBy(shadowJar)
    }

    shadowJar {
        archiveClassifier = ""

        dependencies {
            exclude(dependency("org.jetbrains:annotations:13.0")); exclude(dependency("org.jetbrains:annotations:23.0.0")); exclude(dependency("org.jetbrains:annotations:26.0.2"))
        }

        fun prefix(pattern: String) {
            relocate(pattern, "$groupString.shaded.$pattern")
        }
        prefix("kotlin")
        prefix("dev.jorel.commandapi")
    }
}