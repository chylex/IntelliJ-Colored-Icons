package com.chylex.intellij.coloredicons;
import org.apache.commons.io.FileUtils;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import static java.util.Map.entry;

final class FixSVGs {
	
	private static final File ICONS_FOLDER = new File("./resources/icons");
	
	public static void main(final String[] args) throws IOException {
		final Charset charset = StandardCharsets.UTF_8;
		final String encoding = charset.name();
		
		final Map<String, String> svgRemapping = Map.ofEntries(
			entry("<svg width=\"100%\" height=\"100%\" viewBox=\"0 0 13 13\"", "<svg xmlns=\"http://www.w3.org/2000/svg\" width=\"13\" height=\"13\" viewBox=\"0 0 13 13\">"),
			entry("<svg width=\"100%\" height=\"100%\" viewBox=\"0 0 16 16\"", "<svg xmlns=\"http://www.w3.org/2000/svg\" width=\"16\" height=\"16\" viewBox=\"0 0 16 16\">"),
			entry("<svg width=\"100%\" height=\"100%\" viewBox=\"0 0 12 12\"", "<svg xmlns=\"http://www.w3.org/2000/svg\" width=\"12\" height=\"12\" viewBox=\"0 0 12 12\">")
		);
		
		final Set<String> svgValidTags = new HashSet<>(svgRemapping.values());
		
		int changedFiles = 0;
		
		for (final File file : FileUtils.listFiles(ICONS_FOLDER, new String[]{ "svg" }, true)) {
			boolean hasChanged = false;
			
			final List<String> lines = FileUtils.readLines(file, charset);
			
			if (lines.get(0).startsWith("<?xml")) {
				lines.remove(0);
				hasChanged = true;
			}
			
			if (lines.get(0).startsWith("<!DOCTYPE")) {
				lines.remove(0);
				hasChanged = true;
			}
			
			final String svg = lines.get(0);
			
			if (!svgValidTags.contains(svg)) {
				final String correctedTag = svgRemapping.keySet().stream().filter(svg::contains).findAny().map(svgRemapping::get).orElse(null);
				
				if (correctedTag == null) {
					System.out.println("Error processing <svg> tag in file: " + file);
					System.out.println("  " + svg);
					continue;
				}
				else {
					lines.set(0, correctedTag);
					hasChanged = true;
				}
			}
			
			if (hasChanged) {
				++changedFiles;
				System.out.println("Updated file: " + file);
			}
			
			printWarnings(lines, file);
			
			FileUtils.writeLines(file, encoding, lines, "\n");
		}
		
		System.out.println("Total updated files: " + changedFiles);
	}
	
	private static final Pattern invalidColorDefinition = Pattern.compile("\\bfill:[^#]");
	private static final Pattern hexColorDefinition = Pattern.compile("\\bfill:#([0-9a-f]{6});");
	
	private static final Set<String> allowedLightColors = Set.of(
		"b76db7",
		"6e6e6e",
		"59a869",
		"eda200",
		"389fd6",
		"db5860"
	);
	
	private static final Set<String> allowedDarkColors = Set.of(
		"afb1b3",
		"b066b0",
		"499c54",
		"f0a732",
		"3592c4",
		"c75450"
	);
	
	private static final Set<String> skipColorCheck = Set.of(
		"general/addJdk",
		"modules/addExcludedRoot",
		"toolbarDecorator/addClass",
		"toolbarDecorator/addJira"
	);
	
	private static void printWarnings(final List<String> lines, final File file) {
		final boolean isDark = file.getName().endsWith("_dark.svg");
		final String relativeFilePath = ICONS_FOLDER.toPath().relativize(file.toPath()).toString().replace('\\', '/');
		final String suffixlessFilePath = isDark ? relativeFilePath.replace("_dark.svg", "") : relativeFilePath.replace(".svg", "");
		
		if (skipColorCheck.contains(suffixlessFilePath)) {
			return;
		}
		
		final Set<String> validThemeColors = isDark ? allowedDarkColors : allowedLightColors;
		
		for (final String line : lines) {
			if (invalidColorDefinition.matcher(line).find()) {
				System.out.println("Warning: Invalid color definition in file: " + relativeFilePath);
				System.out.println("Line: " + line.trim());
			}
			
			hexColorDefinition.matcher(line).results().forEachOrdered(result -> {
				final String color = result.group(1);
				
				if (!validThemeColors.contains(color)) {
					System.out.println("Warning: Invalid theme color in file: " + relativeFilePath);
					System.out.println("Line: " + line.trim());
				}
			});
		}
	}
}
