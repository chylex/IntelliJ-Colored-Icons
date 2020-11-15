package com.chylex.intellij.coloredicons;
import com.google.common.collect.ImmutableList;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipFile;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import static java.util.Comparator.comparing;

final class GrabIconsFromInstalledIDEs{
	private static final ImmutableList<String> EXPECTED_VIEW_BOXES_LOWERCASE = ImmutableList.of(
		"viewbox=\"0 0 12 12\"",
		"viewbox=\"0 0 16 16\"",
		"viewbox=\"0 0 13 13\""
	);
	
	private static final ImmutableList<String> EXPECTED_COLORS_LOWERCASE = ImmutableList.of(
		"#afb1b3",
		"#6e6e6e"
	);
	
	private static boolean FLATTEN = true;
	
	public static void main(final String[] args) throws IOException{
		@SuppressWarnings("CallToSystemGetenv")
		final String localAppData = System.getenv("LOCALAPPDATA");
		
		if (localAppData == null){
			System.out.println("Missing %LOCALAPPDATA%, only Windows Vista or newer are supported.");
			return;
		}
		
		final Path toolboxAppsFolder = Paths.get(localAppData, "JetBrains", "Toolbox", "apps");
		
		if (!Files.exists(toolboxAppsFolder)){
			System.out.println("Missing JetBrains Toolbox installation folder at: " + toolboxAppsFolder);
			return;
		}
		
		final List<Path> foundJarFiles = list(toolboxAppsFolder)
			.filter(Files::isDirectory)
			.flatMap(GrabIconsFromInstalledIDEs::getAppChannelFolders)
			.flatMap(GrabIconsFromInstalledIDEs::getLibAndPluginsFolders)
			.flatMap(GrabIconsFromInstalledIDEs::getJarFiles)
			.sorted(comparing(GrabIconsFromInstalledIDEs::getName))
			.collect(Collectors.toList());
		
		System.out.println("Total JAR files found: " + foundJarFiles.size());
		
		final Path extractionRootPath = new File("./extracted").toPath();
		
		if (Files.exists(extractionRootPath)){
			FileUtils.cleanDirectory(extractionRootPath.toFile());
		}
		else{
			Files.createDirectory(extractionRootPath);
		}
		
		int totalExtractedFiles = 0;
		
		for(final Path jarFile : foundJarFiles){
			final Path jarFileName = jarFile.getFileName();
			final Path extractionJarPath = extractionRootPath.resolve(jarFileName);
			
			int extractedFiles = 0;
			
			try(final ZipFile zip = new ZipFile(jarFile.toFile())){
				for(final Enumeration<ZipArchiveEntry> entries = zip.getEntries(); entries.hasMoreElements();){
					final ZipArchiveEntry entry = entries.nextElement();
					
					if (!entry.isDirectory()){
						final String entryName = entry.getName();
						
						if (entryName.toLowerCase(Locale.ROOT).endsWith(".svg") && extractFile(zip, entry, extractionJarPath.resolve(FLATTEN ? entryName.replace("/", "__") : entryName))){
							++extractedFiles;
						}
					}
				}
			}
			
			if (extractedFiles > 0){
				totalExtractedFiles += extractedFiles;
				System.out.println("Extracted SVGs from " + jarFileName + ": " + extractedFiles);
			}
		}
		
		System.out.println("Total extracted SVGs: " + totalExtractedFiles);
	}
	
	private static Stream<Path> list(final Path parent){
		try{
			return Files.list(parent);
		}catch(final IOException e){
			e.printStackTrace();
			return Stream.empty();
		}
	}
	
	private static String getName(final Path path){
		return path.getFileName().toString().toLowerCase(Locale.ROOT);
	}
	
	private static Stream<Path> getAppChannelFolders(final Path appFolder){
		return list(appFolder).filter(Files::isDirectory).filter(child -> getName(child).startsWith("ch-"));
	}
	
	private static Stream<Path> getLibAndPluginsFolders(final Path channelFolder){
		return list(channelFolder).filter(Files::isDirectory).flatMap(child -> {
			final String name = getName(child);
			
			if (name.contains(".remove-")){
				return Stream.empty();
			}
			else if (name.endsWith(".plugins")){
				return Stream.of(child);
			}
			else{
				return Stream.of(child.resolve("lib"), child.resolve("plugins"));
			}
		}).filter(Files::isDirectory); // catches non-existent paths too
	}
	
	private static Stream<Path> getJarFiles(final Path parentFolder){
		System.out.println("Collecting JAR files from: " + parentFolder);
		try{
			return Files.find(parentFolder, Integer.MAX_VALUE, (path, attr) -> Files.isRegularFile(path) && getName(path).endsWith(".jar"));
		}catch(final IOException e){
			e.printStackTrace();
			return Stream.empty();
		}
	}
	
	private static boolean extractFile(final ZipFile zip, final ZipArchiveEntry entry, final Path target) throws IOException{
		if (!isValidSVG(zip.getInputStream(entry))){
			return false;
		}
		
		Path svgPath = target;
		
		final Path svgParentFolder = svgPath.getParent();
		final String originalFileName = svgPath.getFileName().toString();
		int duplicateCounter = 0;
		
		while(Files.exists(svgPath)){
			if (Files.size(svgPath) == entry.getSize() && IOUtils.contentEquals(zip.getInputStream(entry), Files.newInputStream(svgPath, StandardOpenOption.READ))){
				return false;
			}
			
			svgPath = svgParentFolder.resolve(FilenameUtils.removeExtension(originalFileName) + '_' + (++duplicateCounter) + '.' + FilenameUtils.getExtension(originalFileName));
		}
		
		Files.createDirectories(svgParentFolder);
		Files.copy(zip.getInputStream(entry), svgPath);
		Files.setLastModifiedTime(svgPath, entry.getLastModifiedTime());
		
		return true;
	}
	
	private static boolean isValidSVG(final InputStream fileStream) throws IOException{
		boolean hasExpectedViewBox = false;
		boolean hasExpectedColor = false;
		
		for(final Iterator<String> lineIterator = IOUtils.lineIterator(fileStream, StandardCharsets.UTF_8); lineIterator.hasNext();){
			final String line = lineIterator.next().toLowerCase(Locale.ROOT);
			
			if (EXPECTED_VIEW_BOXES_LOWERCASE.stream().anyMatch(line::contains)){
				hasExpectedViewBox = true;
			}
			
			if (EXPECTED_COLORS_LOWERCASE.stream().anyMatch(line::contains)){
				hasExpectedColor = true;
			}
			
			if (hasExpectedViewBox && hasExpectedColor){
				return true;
			}
		}
		
		return false;
	}
}
