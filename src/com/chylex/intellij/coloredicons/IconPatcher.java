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
		addPathWithDark("actions/openNewTab");
		addPathWithDark("actions/prettyPrint");
		addPathWithDark("actions/previousOccurence");
		addPathWithDark("actions/realIntentionBulb");
		addPathWithDark("actions/refactoringBulb"); // TODO not working
		addPathWithDark("actions/refresh");
		addPathWithDark("actions/RemoveMulticaret");
		addPathWithDark("actions/rollback");
		addPathWithDark("actions/show");
		addPathWithDark("actions/showCode");
		addPathWithDark("actions/showHiddens");
		addPathWithDark("actions/splitHorizontally");
		addPathWithDark("actions/splitVertically");
		addPathWithDark("actions/swapPanels");
		addPathWithDark("actions/synchronizeScrolling");
		addPathWithDark("actions/syncPanels");
		addPathWithDark("actions/toggleSoftWrap");
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
		
		addPathWithDark("icons/cherryPick");
		addPathWithDark("icons/CMakeToolWindow");
		addPathWithDark("icons/ConcurrencyDiagramToolwindow");
		addPathWithDark("icons/ejbToolWindow");
		addPathWithDark("icons/IntelliSort");
		addPathWithDark("icons/javaeeToolWindow");
		addPathWithDark("icons/jpaConsoleToolWindow");
		addPathWithDark("icons/jpaToolWindow");
		addPathWithDark("icons/makefileToolWindow");
		addPathWithDark("icons/OpenTerminal_13x13");
		addPathWithDark("icons/springToolWindow");
		addPathWithDark("icons/toolWindowConsole");
		addPathWithDark("icons/toolWindowDatabase");
		addPathWithDark("icons/toolwindowDatabaseChanges");
		addPathWithDark("icons/toolWindowGradle");
		addPathWithDark("icons/toolWindowSQLGenerator");
		addPathWithDark("icons/buildTools/gulp_toolwindow");
		addPathWithDark("icons/buildTools/grunt/grunt_toolwindow");
		addPathWithDark("icons/buildTools/npm/npm_13");
		addPathWithDark("icons/com/jetbrains/python/DataView");
		addPathWithDark("icons/com/jetbrains/python/pythonConsoleToolWindow");
		
		addPathWithDark("images/toolWindowMaven");
		addPathWithDark("images/transferToolWindow");
		addPathWithDark("images/webServerToolWindow");
		
		addPathWithDark("modules/addExcludedRoot");
		
		addPathWithDark("objectBrowser/sortByType");
		addPathWithDark("objectBrowser/sorted");
		addPathWithDark("objectBrowser/sortedByUsage");
		
		addPathWithDark("org/jetbrains/plugins/github/pullRequestsToolWindow");
		
		addPathWithDark("resources/icons/bvToolWindow");
		addPathWithDark("resources/icons/hibConsoleToolWindow");
		
		addPathWithDark("rider/toolWindows/toolWindowNuGet");
		addPathWithDark("rider/toolWindows/toolWindowSolutionWideAnalysis");
		addPathWithDark("rider/toolWindows/toolwindowStacktrace");
		addPathWithDark("rider/toolWindows/toolWindowUnitTesting");
		addPathWithDark("rider/toolWindows/toolWindowWPFPreview");
		
		addPathWithDark("runConfigurations/scroll_down");
		addPathWithDark("runConfigurations/showIgnored");
		addPathWithDark("runConfigurations/showPassed");
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
		addPathWithDark("toolwindows/toolWindowAnt");
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
		
		addPathWithDark("vcs/history");
		addPathWithDark("vcs/merge");
		
		addPathWithDark("toolWindowDotTrace");
		addPathWithDark("toolWindowDPA");
		addPathWithDark("toolwindowUnitTestCoverage");
		
		IconLoader.installPathPatcher(this);
	}
	
	private void addPathWithDark(final String path){
		iconPaths.put('/' + path + ".svg", "/icons/" + path + ".svg");
		iconPaths.put('/' + path + "_dark.svg", "/icons/" + path + "_dark.svg");
	}
	
	@Nullable
	@Override
	public String patchPath(@NotNull final String path, final ClassLoader classLoaderIgnore){
		return iconPaths.get(path);
	}
	
	@Nullable
	@Override
	public ClassLoader getContextClassLoader(@NotNull final String path, final ClassLoader originalClassLoader){
		return classLoader;
	}
}
