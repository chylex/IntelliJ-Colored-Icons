import java.io.FileFilter

plugins {
	java
	id("org.jetbrains.intellij") version "1.15.0"
}

group = "com.chylex.intellij.coloredicons"
version = "1.6.0"

repositories {
	mavenCentral()
	maven("https://www.jetbrains.com/intellij-repository/releases/")
	maven("https://www.jetbrains.com/intellij-repository/snapshots/")
}

intellij {
	type.set("IU")
	version.set("242.20224-EAP-CANDIDATE-SNAPSHOT")
	updateSinceUntilBuild.set(false)
	
	plugins.set(listOf(
		"com.intellij.classic.ui:242.20224.22",    // https://plugins.jetbrains.com/plugin/24468-classic-ui/versions
		"com.jetbrains.php:242.20224.44",          // https://plugins.jetbrains.com/plugin/6610-php/versions
		"com.jetbrains.rust:242.1",                // https://plugins.jetbrains.com/plugin/22407-rust/versions
		"org.intellij.scala:2024.2.11",            // https://plugins.jetbrains.com/plugin/1347-scala/versions
		"org.jetbrains.plugins.go:242.20224.38",   // https://plugins.jetbrains.com/plugin/9568-go/versions
		"org.jetbrains.plugins.ruby:242.20224.38", // https://plugins.jetbrains.com/plugin/1293-ruby/versions
		"PythonCore:242.20224.38",                 // https://plugins.jetbrains.com/plugin/7322-python-community-edition/versions
		"Pythonid:242.20224.38",                   // https://plugins.jetbrains.com/plugin/631-python/versions
	))
}

tasks.patchPluginXml {
	sinceBuild.set("242")
}

tasks.buildSearchableOptions {
	enabled = false
}

configurations {
	create("ides")
}

java {
	toolchain.languageVersion.set(JavaLanguageVersion.of(17))
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
	
	if (System.getProperty("downloadExtraIDEs", "") == "true") {
		"ides"("com.jetbrains.intellij.idea:ideaIU:2023.3")
		"ides"("com.jetbrains.intellij.clion:clion:2023.3")
		"ides"("com.jetbrains.intellij.rider:riderRD:2023.3")
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
createHelperTask("deleteFinishedIcons",        main = "DeleteFinishedIcons")
createHelperTask("grabIconsFromInstalledIDEs", main = "GrabIcons\$FromInstalledIDEs")
createHelperTask("grabIconsFromGradle",        main = "GrabIcons\$FromArgumentPaths") { task ->
	val ideLibraries = getClassPathFolders(project.configurations.getByName("ides"))
	val downloadedPlugins = File(buildDir, "idea-sandbox/plugins").listFiles(FileFilter { it.isDirectory && it.name != rootProject.name })
	
	if (downloadedPlugins != null) {
		task.args = ideLibraries + downloadedPlugins.map(File::getAbsolutePath)
	}
	else {
		task.args = ideLibraries
	}
}
