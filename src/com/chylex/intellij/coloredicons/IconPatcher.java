package com.chylex.intellij.coloredicons;
import com.chylex.intellij.coloredicons.sets.OldUI;
import com.intellij.openapi.util.IconLoader;
import com.intellij.openapi.util.IconPathPatcher;
import com.intellij.ui.NewUI;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.util.Collections;
import java.util.Map;

public class IconPatcher extends IconPathPatcher {
	private final ClassLoader classLoader = getClass().getClassLoader();
	private final Map<String, String> iconPaths;
	
	public IconPatcher() {
		iconPaths = NewUI.isEnabled() ? Collections.emptyMap() : OldUI.getIconPaths();
		IconLoader.installPathPatcher(this);
	}
	
	@Nullable
	@Override
	public String patchPath(@NotNull final String path, final ClassLoader classLoaderIgnore) {
		return iconPaths.get(path);
	}
	
	@Nullable
	@Override
	public ClassLoader getContextClassLoader(@NotNull final String path, final ClassLoader originalClassLoader) {
		return classLoader;
	}
}
