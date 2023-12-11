package com.chylex.intellij.coloredicons.sets;

import java.util.HashMap;
import java.util.Map;

public final class OldUI {
	
	public static Map<String, String> getIconPaths() {
		return new OldUI().getMap();
	}
	
	private final Map<String, String> iconPaths = new HashMap<>();
	
	private OldUI() {
		addPathWithDark("actions/addList");
		addPathWithDark("actions/AddMulticaret");
		addPathWithDark("actions/back");
		addPathWithDark("actions/buildAutoReloadChanges");
		addPathWithDark("actions/cancel");
		addPathWithDark("actions/CheckMulticaret");
		addPathWithDark("actions/collapseall");
		addPathWithDark("actions/download");
		addPathWithDark("actions/dump");
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
		addPathWithDark("general/autoscrollFromSource");
		addPathWithDark("general/autoscrollToSource");
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
		
		addPathWithDark("icons/artifactSmall");
		addPathWithDark("icons/cherryPick");
		addPathWithDark("icons/CMakeToolWindow");
		addPathWithDark("icons/ConcurrencyDiagramToolwindow");
		addPathWithDark("icons/debug_listen_on");
		addPathWithDark("icons/ejbToolWindow");
		addPathWithDark("icons/gradleNavigate");
		addPathWithDark("icons/IntelliSort");
		addPathWithDark("icons/interTypes");
		addPathWithDark("icons/javaeeToolWindow");
		addPathWithDark("icons/jpaConsoleToolWindow");
		addPathWithDark("icons/jpaToolWindow");
		addPathWithDark("icons/makefileToolWindow");
		addPathWithDark("icons/OpenTerminal_13x13");
		addPathWithDark("icons/springToolWindow");
		addPathWithDark("icons/toolWindowConsole");
		addPathWithDark("icons/toolWindowDatabase");
		addPathWithDark("icons/toolwindowDatabaseChanges");
		addPathWithDark("icons/toolWindowDsm");
		addPathWithDark("icons/toolWindowEndpoints");
		addPathWithDark("icons/toolWindowGradle");
		addPathWithDark("icons/toolWindowSQLGenerator");
		addPathWithDark("icons/youTrack");
		addPathWithDark("icons/buildTools/gulp_toolwindow");
		addPathWithDark("icons/buildTools/grunt/grunt_toolwindow");
		addPathWithDark("icons/buildTools/npm/npm_13");
		addPathWithDark("icons/com/jetbrains/python/DataView");
		addPathWithDark("icons/com/jetbrains/python/pythonConsoleToolWindow");
		addPathWithDark("icons/com/jetbrains/python/pythonPackages");
		
		addPathWithDark("images/toolWindowMaven");
		addPathWithDark("images/transferToolWindow");
		addPathWithDark("images/updateFolders");
		addPathWithDark("images/webServerToolWindow");
		
		addPathWithDark("img/featureTrainerToolWindow");
		
		addPathWithDark("modules/addExcludedRoot");
		
		addPathWithDark("objectBrowser/sortByType");
		addPathWithDark("objectBrowser/sorted");
		addPathWithDark("objectBrowser/sortedByUsage");
		addPathWithDark("objectBrowser/visibilitySort");
		
		addPathWithDark("org/jetbrains/plugins/github/pullRequestsToolWindow");
		addPathWithDark("org/jetbrains/sbt/images/sbtShellToolwin");
		addPathWithDark("org/jetbrains/sbt/images/sbtToolwin");
		
		addPathWithDark("resources/icons/bvToolWindow");
		addPathWithDark("resources/icons/hibConsoleToolWindow");
		
		addPathWithDark("rider/toolwindows/DotMemoryProfilingMonoTone");
		addPathWithDark("rider/toolwindows/toolWindowDPA");
		addPathWithDark("rider/toolwindows/toolWindowDotTrace");
		addPathWithDark("rider/toolwindows/toolWindowNuGet");
		addPathWithDark("rider/toolwindows/toolWindowSolutionWideAnalysis");
		addPathWithDark("rider/toolwindows/toolWindowUnitTesting");
		addPathWithDark("rider/toolwindows/toolWindowWPFPreview");
		addPathWithDark("rider/toolwindows/toolwindowStacktrace");
		addPathWithDark("rider/toolwindows/toolwindowUnitTestCoverage");
		
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
		addPathWithDark("toolwindows/notifications");
		addPathWithDark("toolwindows/notificationsNew");
		addPathWithDark("toolwindows/notificationsNewImportant");
		addPathWithDark("toolwindows/toolWindowAnalyzeDataflow");
		addPathWithDark("toolwindows/toolWindowAnt");
		addPathWithDark("toolwindows/toolWindowBookmarks");
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
		addPathWithDark("toolwindows/toolWindowProfilerAndroid");
		addPathWithDark("toolwindows/toolWindowProject");
		addPathWithDark("toolwindows/toolWindowRun");
		addPathWithDark("toolwindows/toolWindowRunWithCoverage");
		addPathWithDark("toolwindows/toolWindowServices");
		addPathWithDark("toolwindows/toolWindowStructure");
		addPathWithDark("toolwindows/toolWindowTodo");
		addPathWithDark("toolwindows/webToolWindow");
		
		addPathWithDark("vcs/changelist");
		addPathWithDark("vcs/history");
		addPathWithDark("vcs/merge");
	}
	
	private Map<String, String> getMap() {
		return iconPaths;
	}
	
	private void addPathWithDark(final String path) {
		iconPaths.put('/' + path + ".svg", "icons/" + path + ".svg");
		iconPaths.put('/' + path + "_dark.svg", "icons/" + path + "_dark.svg");
	}
}
