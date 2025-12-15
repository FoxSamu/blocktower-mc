pluginManagement {
	repositories {
		maven {
			name = "Fabric"
			url = uri("https://maven.fabricmc.net/")
		}

		mavenCentral()
		gradlePluginPortal()
	}

    plugins {
        id("net.fabricmc.fabric-loom-remap").version("1.14-SNAPSHOT")
        kotlin("jvm").version("2.2.21")
        kotlin("plugin.serialization").version("2.2.21")
    }
}
