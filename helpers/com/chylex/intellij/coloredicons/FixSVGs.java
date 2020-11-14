package com.chylex.intellij.coloredicons;
import com.google.common.collect.ImmutableMap;
import org.apache.commons.io.FileUtils;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

final class FixSVGs{
	public static void main(final String[] args) throws IOException{
		final Charset charset = StandardCharsets.UTF_8;
		final String encoding = charset.name();
		
		final Map<String, String> svgRemapping = ImmutableMap.<String, String>builder()
			.put("<svg width=\"100%\" height=\"100%\" viewBox=\"0 0 13 13\"", "<svg xmlns=\"http://www.w3.org/2000/svg\" width=\"13\" height=\"13\" viewBox=\"0 0 13 13\">")
			.put("<svg width=\"100%\" height=\"100%\" viewBox=\"0 0 16 16\"", "<svg xmlns=\"http://www.w3.org/2000/svg\" width=\"16\" height=\"16\" viewBox=\"0 0 16 16\">")
			.put("<svg width=\"100%\" height=\"100%\" viewBox=\"0 0 12 12\"", "<svg xmlns=\"http://www.w3.org/2000/svg\" width=\"12\" height=\"12\" viewBox=\"0 0 12 12\">")
			.build();
		
		final Set<String> svgValidTags = new HashSet<>(svgRemapping.values());
		
		int changedFiles = 0;
		
		for(final File file : FileUtils.listFiles(new File("./resources/icons"), new String[]{ "svg" }, true)){
			boolean hasChanged = false;
			
			final List<String> lines = FileUtils.readLines(file, charset);
			
			if (lines.get(0).startsWith("<?xml")){
				lines.remove(0);
				hasChanged = true;
			}
			
			if (lines.get(0).startsWith("<!DOCTYPE")){
				lines.remove(0);
				hasChanged = true;
			}
			
			final String svg = lines.get(0);
			
			if (!svgValidTags.contains(svg)){
				final String correctedTag = svgRemapping.keySet().stream().filter(svg::contains).findAny().map(svgRemapping::get).orElse(null);
				
				if (correctedTag == null){
					System.out.println("Error processing <svg> tag in file: " + file);
					System.out.println("  " + svg);
					continue;
				}
				else{
					lines.set(0, correctedTag);
					hasChanged = true;
				}
			}
			
			if (hasChanged){
				++changedFiles;
				System.out.println("Updated file: " + file);
			}
			
			FileUtils.writeLines(file, encoding, lines);
		}
		
		System.out.println("Total updated files: " + changedFiles);
	}
}
