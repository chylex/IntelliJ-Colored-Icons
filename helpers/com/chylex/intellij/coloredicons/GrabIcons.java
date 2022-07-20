package com.chylex.intellij.coloredicons;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

abstract class GrabIcons {
	private static final List<String> EXPECTED_VIEW_BOXES_LOWERCASE = List.of(
		"viewbox=\"0 0 12 12\"",
		"viewbox=\"0 0 16 16\"",
		"viewbox=\"0 0 13 13\""
	);
	
	private static final List<String> EXPECTED_COLORS_LOWERCASE = List.of(
		"#afb1b3",
		"#6e6e6e"
	);
	
	private static final boolean FLATTEN_ARCHIVE_PATHS = true;
	private static final boolean FLATTEN_SVG_PATHS = false;
	
	protected final void run() throws IOException {
		final Stream<Path> libAndPluginFolders = findLibAndPluginFolders();
		if (libAndPluginFolders == null) {
			return;
		}
		
		final Path[] foundArchives = libAndPluginFolders
			.flatMap(GrabIcons::getJarAndZipFiles)
			.sorted()
			.toArray(Path[]::new);
		
		System.out.println("Total JAR and ZIP files found: " + foundArchives.length);
		
		if (foundArchives.length == 0) {
			return;
		}
		
		final Path extractionRootPath = new File("./extracted").toPath();
		
		if (Files.exists(extractionRootPath)) {
			FileUtils.cleanDirectory(extractionRootPath.toFile());
		}
		else {
			Files.createDirectory(extractionRootPath);
		}
		
		extractSVGsFromArchives(foundArchives, extractionRootPath);
		System.out.println("Done!");
	}
	
	protected abstract Stream<Path> findLibAndPluginFolders();
	
	private static Stream<Path> getJarAndZipFiles(final Path parentFolder) {
		System.out.println("Collecting JAR and ZIP files from: " + parentFolder);
		try {
			return Files.find(parentFolder, Integer.MAX_VALUE, GrabIcons::isJarOrZipFile);
		} catch (final IOException e) {
			e.printStackTrace();
			return Stream.empty();
		}
	}
	
	private static boolean isJarOrZipFile(final Path path, final BasicFileAttributes attr) {
		if (!Files.isRegularFile(path)) {
			return false;
		}
		
		final String name = getFileNameLowerCase(path);
		return name.endsWith(".jar") || name.endsWith(".zip");
	}
	
	private static String getFileNameLowerCase(final Path path) {
		return path.getFileName().toString().toLowerCase(Locale.ROOT);
	}
	
	private static void extractSVGsFromArchives(final Path[] foundArchives, final Path extractionRootPath) throws IOException {
		final Path commonParent = findCommonParent(foundArchives);
		
		for (final Path archiveFile : foundArchives) {
			final Path extractionArchiveBasePath = FLATTEN_ARCHIVE_PATHS ? extractionRootPath : extractionRootPath.resolve(archiveFile.getFileName());
			
			try (final ZipInputStream inputStream = new ZipInputStream(new FileInputStream(archiveFile.toFile()))) {
				final Path relativeArchivePath = commonParent == null ? archiveFile : commonParent.relativize(archiveFile);
				
				try {
					extractSVGsFromArchiveStream(extractionArchiveBasePath, relativeArchivePath.toString().replace('\\', '/'), inputStream);
				} catch (final Exception e) {
					System.out.println("Failed to extract SVGs from archive: " + archiveFile);
				}
			}
		}
	}
	
	private static Path findCommonParent(final Path[] paths) {
		return Arrays.stream(paths)
			.map(Optional::of)
			.reduce(GrabIcons::findCommonParent)
			.orElseGet(Optional::empty)
			.orElse(null);
	}
	
	@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
	private static Optional<Path> findCommonParent(final Optional<Path> a, final Optional<Path> b) {
		if (a.isEmpty() || b.isEmpty()) {
			return Optional.empty();
		}
		
		final Path other = b.get().normalize();
		
		return Stream.iterate(a.get().normalize(), Objects::nonNull, Path::getParent)
			.filter(parent -> Stream.iterate(other, Objects::nonNull, Path::getParent).anyMatch(Predicate.isEqual(parent)))
			.findFirst();
	}
	
	@SuppressWarnings({ "NestedAssignment", "AutoBoxing" })
	private static void extractSVGsFromArchiveStream(final Path extractionBasePath, final String archiveName, final ZipInputStream inputStream) throws IOException {
		int extractedFilesDirectly = 0;
		
		ZipEntry entry;
		while ((entry = inputStream.getNextEntry()) != null) {
			extractedFilesDirectly += extractSVGsFromArchiveEntry(extractionBasePath, archiveName, inputStream, entry);
		}
		
		if (extractedFilesDirectly > 0) {
			System.out.println("Extracted " + String.format("%3d", extractedFilesDirectly) + " SVG(s) from: " + archiveName);
		}
	}
	
	private static int extractSVGsFromArchiveEntry(final Path basePath, final String archiveName, final ZipInputStream parentInputStream, final ZipEntry entry) throws IOException {
		if (entry.isDirectory()) {
			return 0;
		}
		
		int extractedFilesDirectly = 0;
		
		final String entryName = entry.getName();
		final String entryNameLowerCase = entryName.toLowerCase(Locale.ROOT);
		
		if (entryNameLowerCase.endsWith(".jar") || entryNameLowerCase.endsWith(".zip")) {
			final String nestedArchiveName = archiveName + '/' + entryName;
			
			try {
				extractSVGsFromArchiveStream(FLATTEN_ARCHIVE_PATHS ? basePath : getRelativePathForExtraction(basePath, entry), nestedArchiveName, new ZipInputStream(parentInputStream));
			} catch (final Exception e) {
				System.out.println("Failed to extract SVGs from nested archive: " + nestedArchiveName);
			}
		}
		else if (entryNameLowerCase.endsWith(".svg")) {
			if (extractSVG(parentInputStream, entry, getRelativePathForExtraction(basePath, entry))) {
				++extractedFilesDirectly;
			}
		}
		
		return extractedFilesDirectly;
	}
	
	private static Path getRelativePathForExtraction(final Path basePath, final ZipEntry entry) {
		return basePath.resolve(FLATTEN_SVG_PATHS ? entry.getName().replace("/", "__") : entry.getName());
	}
	
	private static boolean extractSVG(final ZipInputStream inputStream, final ZipEntry entry, final Path basePath) throws IOException {
		final byte[] svgBytes = inputStream.readAllBytes();
		final String svgText = new String(svgBytes, StandardCharsets.UTF_8);
		
		if (!isValidSVG(svgText)) {
			return false;
		}
		
		Path svgPath = basePath;
		
		final Path svgParentFolder = svgPath.getParent();
		final String originalFileName = svgPath.getFileName().toString();
		int duplicateCounter = 0;
		
		while (Files.exists(svgPath)) {
			if (Files.size(svgPath) == entry.getSize() && IOUtils.contentEquals(new ByteArrayInputStream(svgBytes), Files.newInputStream(svgPath, StandardOpenOption.READ))) {
				return false;
			}
			
			svgPath = svgParentFolder.resolve(FilenameUtils.removeExtension(originalFileName) + '_' + (++duplicateCounter) + '.' + FilenameUtils.getExtension(originalFileName));
		}
		
		Files.createDirectories(svgParentFolder);
		Files.write(svgPath, svgBytes);
		Files.setLastModifiedTime(svgPath, entry.getLastModifiedTime());
		
		return true;
	}
	
	private static boolean isValidSVG(final String svg) {
		boolean hasExpectedViewBox = false;
		boolean hasExpectedColor = false;
		
		for (final String line : svg.lines().map(line -> line.toLowerCase(Locale.ROOT)).toArray(String[]::new)) {
			if (EXPECTED_VIEW_BOXES_LOWERCASE.stream().anyMatch(line::contains)) {
				hasExpectedViewBox = true;
			}
			
			if (EXPECTED_COLORS_LOWERCASE.stream().anyMatch(line::contains)) {
				hasExpectedColor = true;
			}
			
			if (hasExpectedViewBox && hasExpectedColor) {
				return true;
			}
		}
		
		return false;
	}
	
	public static final class FromArgumentPaths extends GrabIcons {
		public static void main(final String[] args) throws IOException {
			new FromArgumentPaths(args).run();
		}
		
		private final Path[] paths;
		
		public FromArgumentPaths(final String[] paths) {
			this.paths = Arrays.stream(paths).map(Path::of).toArray(Path[]::new);
		}
		
		@Override
		protected Stream<Path> findLibAndPluginFolders() {
			return Arrays.stream(paths);
		}
	}
	
	public static final class FromInstalledIDEs extends GrabIcons {
		public static void main(final String[] args) throws IOException {
			new FromInstalledIDEs().run();
		}
		
		@SuppressWarnings("CallToSystemGetenv")
		@Override
		protected Stream<Path> findLibAndPluginFolders() {
			final String localAppData = System.getenv("LOCALAPPDATA");
			
			if (localAppData == null) {
				System.out.println("Missing %LOCALAPPDATA%, only Windows Vista or newer are supported.");
				return null;
			}
			
			final Path toolboxAppsFolder = Paths.get(localAppData, "JetBrains", "Toolbox", "apps");
			
			if (!Files.exists(toolboxAppsFolder)) {
				System.out.println("Missing JetBrains Toolbox installation folder at: " + toolboxAppsFolder);
				return null;
			}
			
			return list(toolboxAppsFolder)
				.filter(Files::isDirectory)
				.flatMap(FromInstalledIDEs::getAppChannelFolders)
				.flatMap(FromInstalledIDEs::getLibAndPluginsFolders);
		}
		
		private static Stream<Path> getAppChannelFolders(final Path appFolder) {
			return list(appFolder).filter(Files::isDirectory).filter(child -> getFileNameLowerCase(child).startsWith("ch-"));
		}
		
		private static Stream<Path> getLibAndPluginsFolders(final Path channelFolder) {
			return list(channelFolder).filter(Files::isDirectory).flatMap(child -> {
				final String name = getFileNameLowerCase(child);
				
				if (name.contains(".remove-")) {
					return Stream.empty();
				}
				else if (name.endsWith(".plugins")) {
					return Stream.of(child);
				}
				else {
					return Stream.of(child.resolve("lib"), child.resolve("plugins"));
				}
			}).filter(Files::isDirectory); // catches non-existent paths too
		}
		
		private static Stream<Path> list(final Path parent) {
			try {
				return Files.list(parent);
			} catch (final IOException e) {
				e.printStackTrace();
				return Stream.empty();
			}
		}
	}
}
