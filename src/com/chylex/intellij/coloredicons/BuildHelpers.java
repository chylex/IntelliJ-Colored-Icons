package com.chylex.intellij.coloredicons;
import org.apache.commons.io.FileUtils;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

final class BuildHelpers{
	static final class FixSVG{
		public static void main(String[] args) throws IOException{
			Charset charset = StandardCharsets.UTF_8;
			String encoding = charset.name();
			
			String svg12Tag = "<svg xmlns=\"http://www.w3.org/2000/svg\" width=\"12\" height=\"12\" viewBox=\"0 0 12 12\">";
			String svg13Tag = "<svg xmlns=\"http://www.w3.org/2000/svg\" width=\"13\" height=\"13\" viewBox=\"0 0 13 13\">";
			String svg16Tag = "<svg xmlns=\"http://www.w3.org/2000/svg\" width=\"16\" height=\"16\" viewBox=\"0 0 16 16\">";
			
			Set<String> svgTags = new HashSet<>(Arrays.asList(
				svg12Tag, svg13Tag, svg16Tag
			));
			
			for(File file : FileUtils.listFiles(new File("./resources/icons"), new String[]{ "svg" }, true)){
				List<String> lines = FileUtils.readLines(file, charset);
				
				if (lines.get(0).startsWith("<?xml")){
					lines.remove(0);
				}
				
				if (lines.get(0).startsWith("<!DOCTYPE")){
					lines.remove(0);
				}
				
				String svg = lines.get(0);
				
				if (svg.startsWith("<svg width=\"100%\" height=\"100%\" viewBox=\"0 0 13 13\"")){
					lines.set(0, svg13Tag);
				}
				else if (svg.startsWith("<svg width=\"100%\" height=\"100%\" viewBox=\"0 0 16 16\"")){
					lines.set(0, svg16Tag);
				}
				else if (svg.startsWith("<svg width=\"100%\" height=\"100%\" viewBox=\"0 0 12 12\"")){
					lines.set(0, svg12Tag);
				}
				else if (!svgTags.contains(svg)){
					System.out.println("Error processing <svg> tag in file " + file + " --- " + svg);
					continue;
				}
				
				FileUtils.writeLines(file, encoding, lines);
			}
		}
	}
}
