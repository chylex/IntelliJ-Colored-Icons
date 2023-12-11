import java.io.FileFilter

plugins {
	java
	id("org.jetbrains.intellij") version "1.15.0"
}

group = "com.chylex.intellij.coloredicons"
version = "1.4"

repositories {
	mavenCentral()
	maven("https://www.jetbrains.com/intellij-repository/releases/")
	maven("https://www.jetbrains.com/intellij-repository/snapshots/")
}

intellij {
	type.set("IU")
	version.set("2023.3")
	updateSinceUntilBuild.set(false)
	
	plugins.set(listOf(
		"Pythonid:233.11799.241",                   // https://plugins.jetbrains.com/plugin/631-python/versions
		"com.jetbrains.php:233.11799.241",          // https://plugins.jetbrains.com/plugin/6610-php/versions
		"com.jetbrains.rust:233.20527.212",         // https://plugins.jetbrains.com/plugin/22407-rust/versions/stable/
		"org.intellij.scala:2023.3.17",             // https://plugins.jetbrains.com/plugin/1347-scala/versions
		"org.jetbrains.plugins.go:233.11799.196",   // https://plugins.jetbrains.com/plugin/9568-go/versions
		"org.jetbrains.plugins.ruby:233.11799.241", // https://plugins.jetbrains.com/plugin/1293-ruby/versions
	))
}

tasks.patchPluginXml {
	sinceBuild.set("233")
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
