@file:Suppress("ConvertLambdaToReference")

plugins {
	java
	idea
	id("org.jetbrains.intellij") version "1.7.0"
}

group = "com.chylex.intellij.coloredicons"
version = "1.3"

repositories {
	mavenCentral()
}

intellij {
	version.set("2022.1")
	updateSinceUntilBuild.set(false)
}

tasks.buildSearchableOptions {
	enabled = false
}

java {
	toolchain.languageVersion.set(JavaLanguageVersion.of(11))
}

sourceSets {
	main {
		java.setSrcDirs(setOf(file("src")))
		resources.setSrcDirs(setOf(file("resources")))
	}
	
	create("helpers") {
		java.setSrcDirs(setOf(file("helpers")))
		resources.setSrcDirs(sourceSets.main.get().resources.srcDirs)
	}
}

dependencies {
	"helpersImplementation"("commons-io:commons-io:2.11.0")
	"helpersImplementation"("org.apache.commons:commons-compress:1.21")
}
