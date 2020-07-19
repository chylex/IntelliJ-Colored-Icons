package com.chylex.intellij.coloredicons;
import com.intellij.openapi.util.IconLoader;
import com.intellij.openapi.util.IconPathPatcher;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.util.HashMap;
import java.util.Map;

public class IconPatcher extends IconPathPatcher{
	private final ClassLoader classLoader = getClass().getClassLoader();
	private final Map<String, String> iconPaths = new HashMap<>();
	
	public IconPatcher(){
		IconLoader.installPathPatcher(this);
	}
	
	private void addPathWithDark(String path){
		iconPaths.put("/" + path + ".svg", "/icons/" + path + ".svg");
		iconPaths.put("/" + path + "_dark.svg", "/icons/" + path + "_dark.svg");
	}
	
	@Nullable
	@Override
	public String patchPath(@NotNull String path, ClassLoader classLoader){
		return iconPaths.get(path);
	}
	
	@Nullable
	@Override
	public ClassLoader getContextClassLoader(@NotNull String path, ClassLoader originalClassLoader){
		return classLoader;
	}
}
