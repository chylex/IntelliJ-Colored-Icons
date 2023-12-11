package com.chylex.intellij.coloredicons;

import org.apache.commons.io.FileUtils;
import java.io.File;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Set;
import static java.util.stream.Collectors.toSet;

public final class DeleteFinishedIcons {
	@SuppressWarnings("ResultOfMethodCallIgnored")
	public static void main(final String[] args) {
		final Path extractedRootPath = Path.of("./extracted");
		final Path finishedRootPath = Path.of("./resources/icons");
		
		final Collection<File> extractedFiles = FileUtils.listFiles(extractedRootPath.toFile(), null, true);
		final Collection<File> finishedFiles = FileUtils.listFiles(finishedRootPath.toFile(), null, true);
		
		System.out.println("Extracted files: " + extractedFiles.size());
		System.out.println("Finished files: " + finishedFiles.size());
		
		final Set<String> finishedRelativePaths = finishedFiles.stream()
			.map(file -> relativize(finishedRootPath, file))
			.collect(toSet());
		
		int deleted = 0;
		
		for (final File extractedFile : extractedFiles) {
			if (finishedRelativePaths.remove(relativize(extractedRootPath, extractedFile))) {
				++deleted;
				extractedFile.delete();
			}
		}
		
		System.out.println("Deleted files: " + deleted);
		
		if (!finishedRelativePaths.isEmpty()) {
			System.out.println("Undeleted files: " + finishedRelativePaths.size());
			System.out.println();
			
			finishedRelativePaths.stream()
				.sorted()
				.forEachOrdered(undeletedPath -> System.out.println("Undeleted file: " + undeletedPath));
		}
	}
	
	private static String relativize(final Path basePath, final File file) {
		return basePath.relativize(file.toPath()).toString();
	}
}
