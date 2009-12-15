package ussr.aGui;

import java.util.ArrayList;

import javax.swing.JTabbedPane;
import javax.swing.SwingWorker;

import ussr.aGui.tabs.TabsInter;

public class SomeTabs extends SwingWorker<Void,Void> {

	private ArrayList<TabsInter> tabs;
	private JTabbedPane jTabbedPaneFirst;

	public SomeTabs (ArrayList<TabsInter> tabs,JTabbedPane jTabbedPaneFirst   ){
		this.tabs =tabs;
		this.jTabbedPaneFirst =jTabbedPaneFirst;
	}

	@Override
	protected Void doInBackground() throws Exception {
		/*Check if tabs are defined*/
		TabsInter constructRobotTab = getTabByTitle(MainFramesInter.CONSTRUCT_ROBOT_TAB_TITLE,tabs);
		TabsInter assignBehaviorsTab = getTabByTitle(MainFramesInter.ASSIGN_BEHAVIORS_TAB_TITLE,tabs);
		TabsInter assignLabels = getTabByTitle(MainFramesInter.ASSIGN_LABELS_TAB_TITLE,tabs);

		/*Add tabs for construction of modular robot*/
		jTabbedPaneFirst.addTab(constructRobotTab.getTabTitle(),new javax.swing.ImageIcon(constructRobotTab.getImageIconDirectory()),constructRobotTab.getJComponent());
		jTabbedPaneFirst.addTab(assignBehaviorsTab.getTabTitle(),new javax.swing.ImageIcon(assignBehaviorsTab.getImageIconDirectory()),assignBehaviorsTab.getJComponent());
		jTabbedPaneFirst.addTab(assignLabels.getTabTitle(),new javax.swing.ImageIcon(assignLabels.getImageIconDirectory()),assignLabels.getJComponent());

		/*Update look and feel for newly added tabs*/		
		//MainFrames.changeToLookAndFeel(constructRobotTab.getJComponent());
		//MainFrames.changeToLookAndFeel(assignBehaviorsTab.getJComponent());
		//MainFrames.changeToLookAndFeel(assignLabels.getJComponent());
		return null;
	}
	
	/**
	 * Checks if the tab with specific title is in the container for tabs.
	 * @param tabTitle, the title of the tab to look for.
	 * @param tabs, container of all tabs in main frame.
	 * @return foundTab, the tab with the title to look for.
	 */
	private static TabsInter getTabByTitle( String tabTitle,ArrayList<TabsInter> tabs){
		TabsInter foundTab = null;
		for (int tabNr=0;tabNr<tabs.size();tabNr++){
			String currentTabTitle = tabs.get(tabNr).getTabTitle();
			if (currentTabTitle.equals(tabTitle)){
				foundTab = tabs.get(tabNr);
			}
		}
		if (foundTab == null){
			throw new Error("The tab with title ("+ tabTitle + ") was not found");
		}
		return foundTab;
	}

}
