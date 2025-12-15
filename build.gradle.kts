plugins {
	id("net.fabricmc.fabric-loom-remap")
	id("maven-publish")
    kotlin("jvm")
    kotlin("plugin.serialization")
}

version = "0.1+1.21.11"
group = "dev.runefox.blocktower"

base {
	archivesName = "blocktower"
}

sourceSets {
    create("mixin")
}

java {
    withSourcesJar()

    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

kotlin {
    jvmToolchain(21)
}

loom {
    mods {
        create("blocktower") {
            sourceSet("main")
            sourceSet("mixin")
        }
    }
}

fabricApi {
    configureDataGeneration {
        client = true
    }
}

repositories {
    mavenCentral()
}

dependencies {
	minecraft("com.mojang:minecraft:1.21.11")
	mappings(loom.officialMojangMappings())

    implementation(sourceSets["mixin"].output)

	modImplementation("net.fabricmc:fabric-loader:0.18.2")
	modImplementation("net.fabricmc.fabric-api:fabric-api:0.139.4+1.21.11")
    modImplementation("net.fabricmc:fabric-language-kotlin:1.13.7+kotlin.2.2.21")
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            artifactId = project.base.archivesName.get()
            from(components["java"])
        }
    }

    repositories {
    }
}

tasks.withType<JavaCompile> {
    options.release = 21
}

tasks.withType<ProcessResources> {
	inputs.property("version", project.version)

	filesMatching("fabric.mod.json") {
		expand(mapOf("version" to inputs.properties["version"]))
	}
}

tasks.withType<Jar> {
	inputs.property("archivesName", project.base.archivesName)

    from(sourceSets["mixin"].output.dirs)
    from(sourceSets["mixin"].output.resourcesDir)

	from("LICENSE") {
		rename { "${it}_${inputs.properties["archivesName"]}"}
	}
}
