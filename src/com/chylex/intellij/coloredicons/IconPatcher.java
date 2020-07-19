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
		addPathWithDark("actions/AddMulticaret");
		addPathWithDark("actions/back");
		addPathWithDark("actions/buildAutoReloadChanges");
		addPathWithDark("actions/cancel");
		addPathWithDark("actions/collapseall");
		addPathWithDark("actions/download");
		addPathWithDark("actions/edit");
		addPathWithDark("actions/editScheme");
		addPathWithDark("actions/editSource");
		addPathWithDark("actions/expandall");
		addPathWithDark("actions/forward");
		addPathWithDark("actions/inSelection");
		addPathWithDark("actions/intentionBulbGrey");
		addPathWithDark("actions/menu-saveall");
		addPathWithDark("actions/newFolder");
		addPathWithDark("actions/nextOccurence");
		addPathWithDark("actions/prettyPrint");
		addPathWithDark("actions/previousOccurence");
		addPathWithDark("actions/realIntentionBulb");
		addPathWithDark("actions/refactoringBulb"); // TODO not working
		addPathWithDark("actions/refresh");
		addPathWithDark("actions/RemoveMulticaret");
		addPathWithDark("actions/rollback");
		addPathWithDark("actions/splitHorizontally");
		addPathWithDark("actions/splitVertically");
		addPathWithDark("actions/swapPanels");
		addPathWithDark("actions/synchronizeScrolling");
		addPathWithDark("actions/syncPanels");
		addPathWithDark("actions/unselectall");
		addPathWithDark("actions/upload");
		
		addPathWithDark("codeStyle/AddNewSectionRule");
		
		addPathWithDark("diff/magicResolve");
		addPathWithDark("diff/magicResolveToolbar");
		
		addPathWithDark("general/add");
		addPathWithDark("general/addJdk");
		addPathWithDark("general/filter");
		addPathWithDark("general/layout");
		addPathWithDark("general/layoutEditorOnly");
		addPathWithDark("general/layoutEditorPreview");
		addPathWithDark("general/layoutPreviewOnly");
		addPathWithDark("general/pin_tab");
		addPathWithDark("general/print");
		addPathWithDark("general/remove");
		addPathWithDark("general/reset");
		addPathWithDark("general/zoomIn");
		addPathWithDark("general/zoomOut");
		
		addPathWithDark("graph/layout");
		addPathWithDark("graph/zoomIn");
		addPathWithDark("graph/zoomOut");
		
		addPathWithDark("hierarchy/class");
		addPathWithDark("hierarchy/subtypes");
		addPathWithDark("hierarchy/supertypes");
		
		addPathWithDark("icons/toolWindowDatabase");
		
		addPathWithDark("modules/addExcludedRoot");
		
		addPathWithDark("objectBrowser/sortByType");
		addPathWithDark("objectBrowser/sorted");
		addPathWithDark("objectBrowser/sortedByUsage");
		
		addPathWithDark("runConfigurations/sortbyDuration");
		
		addPathWithDark("toolbarDecorator/addBlankLine");
		addPathWithDark("toolbarDecorator/addClass");
		addPathWithDark("toolbarDecorator/addFolder");
		addPathWithDark("toolbarDecorator/addIcon");
		addPathWithDark("toolbarDecorator/addJira");
		addPathWithDark("toolbarDecorator/addLink");
		addPathWithDark("toolbarDecorator/addPattern");
		addPathWithDark("toolbarDecorator/addRemoteDatasource");
		addPathWithDark("toolbarDecorator/addYouTrack");
		addPathWithDark("toolbarDecorator/export");
		addPathWithDark("toolbarDecorator/import");
		
		addPathWithDark("toolwindows/documentation");
		addPathWithDark("toolwindows/toolWindowBuild");
		addPathWithDark("toolwindows/toolWindowChanges");
		addPathWithDark("toolwindows/toolWindowCommit");
		addPathWithDark("toolwindows/toolWindowCoverage");
		addPathWithDark("toolwindows/toolWindowDebugger");
		addPathWithDark("toolwindows/toolWindowFavorites");
		addPathWithDark("toolwindows/toolWindowFind");
		addPathWithDark("toolwindows/toolWindowHierarchy");
		addPathWithDark("toolwindows/toolWindowInspection");
		addPathWithDark("toolwindows/toolWindowMessages");
		addPathWithDark("toolwindows/toolWindowModuleDependencies");
		addPathWithDark("toolwindows/toolWindowProfiler");
		addPathWithDark("toolwindows/toolWindowProject"); // TODO not working
		addPathWithDark("toolwindows/toolWindowRun");
		addPathWithDark("toolwindows/toolWindowServices");
		addPathWithDark("toolwindows/toolWindowStructure");
		addPathWithDark("toolwindows/toolWindowTodo");
		addPathWithDark("toolwindows/webToolWindow");
		
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