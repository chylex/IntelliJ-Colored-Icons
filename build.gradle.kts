plugins {
	java
	idea
	id("org.jetbrains.intellij") version "1.7.0"
}

group = "com.chylex.intellij.coloredicons"
version = "1.3"

repositories {
	mavenCentral()
	maven("https://www.jetbrains.com/intellij-repository/snapshots/")
}

intellij {
	type.set("IU")
	version.set("LATEST-EAP-SNAPSHOT")
	updateSinceUntilBuild.set(false)
}

tasks.buildSearchableOptions {
	enabled = false
}

configurations {
	create("ides")
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
	
	"ides"("com.jetbrains.intellij.idea:ideaIU:LATEST-EAP-SNAPSHOT")
	
	if (System.getProperty("downloadExtraIDEs", "") == "true") {
		"ides"("com.jetbrains.intellij.clion:clion:LATEST-EAP-SNAPSHOT")
		"ides"("com.jetbrains.intellij.goland:goland:LATEST-EAP-SNAPSHOT")
		"ides"("com.jetbrains.intellij.phpstorm:phpstorm:LATEST-EAP-SNAPSHOT")
		"ides"("com.jetbrains.intellij.pycharm:pycharmPY:LATEST-EAP-SNAPSHOT")
		"ides"("com.jetbrains.intellij.rider:riderRD:2022.3-SNAPSHOT")
	}
}

fun createHelperTask(name: String, main: String, configure: ((JavaExec) -> Unit)? = null) {
	tasks.create(name, JavaExec::class.java) {
		group = "helpers"
		mainClass.set("com.chylex.intellij.coloredicons.$main")
		classpath = sourceSets.getByName("helpers").runtimeClasspath
		configure?.invoke(this)
	}
}

fun getClassPathFolders(configuration: Configuration): List<String> {
	return configuration.files.map(File::getParentFile).distinct().map(File::getAbsolutePath)
}

createHelperTask("fixSVGs",                    main = "FixSVGs")
createHelperTask("grabIconsFromInstalledIDEs", main = "GrabIcons\$FromInstalledIDEs")
createHelperTask("grabIconsFromGradle",        main = "GrabIcons\$FromArgumentPaths") {
	val ideLibraries = getClassPathFolders(project.configurations.getByName("ides"))
	val downloadedPlugins = File(buildDir, "idea-sandbox/system/plugins").absolutePath
	it.args = ideLibraries + downloadedPlugins
}
