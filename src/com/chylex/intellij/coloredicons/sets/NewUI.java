package com.chylex.intellij.coloredicons.sets;

import java.util.HashMap;
import java.util.Map;

public final class NewUI {
	
	public static Map<String, String> getIconPaths() {
		return new NewUI().getMap();
	}
	
	private final Map<String, String> iconPaths = new HashMap<>();
	
	private NewUI() {
		addPathWithDark("toolwindows/toolWindowChanges", "expui/toolwindow/vcs");
		addPathWithDark("expui/toolwindow/vcs");
		addPathWithDark("expui/toolwindow/vcs@20x20");
	}
	
	private Map<String, String> getMap() {
		return iconPaths;
	}
	
	private void addPathWithDark(final String oldUiPath, final String newPath) {
		iconPaths.put('/' + oldUiPath + ".svg", "icons/" + newPath + ".svg");
		iconPaths.put('/' + oldUiPath + "_dark.svg", "icons/" + newPath + "_dark.svg");
	}
	
	private void addPathWithDark(final String path) {
		iconPaths.put('/' + path + ".svg", "icons/" + path + ".svg");
		iconPaths.put('/' + path + "_dark.svg", "icons/" + path + "_dark.svg");
	}
}
