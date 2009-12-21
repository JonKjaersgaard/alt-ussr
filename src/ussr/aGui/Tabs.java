package ussr.aGui;

import ussr.aGui.tabs.TabsInter;

public enum Tabs {

	CONSTRUCT_ROBOT_TAB(MainFramesInter.CONSTRUCT_ROBOT_TAB),
	ASSIGN_BEHAVIORS_TAB(MainFramesInter.ASSIGN_BEHAVIORS_TAB),
	ASSIGN_LABELS_TAB(MainFramesInter.ASSIGN_LABELS_TAB),
	MODULE_COMMUNICATION_VISUALIZER_TAB(MainFramesInter.MODULE_COMMUNICATION_VISUALIZER_TAB),
	SIMULATION_CONFIGURATION_TAB (MainFramesInter.SIMULATION_CONFIGURATION_TAB),
	CONSOLE_TAB(MainFramesInter.CONSOLE_TAB),
	YOUR_TAB(MainFramesInter.YOUR_TAB),
	;
	private TabsInter tab;
	


	Tabs(TabsInter tab){
		this.tab = tab;
	}


	
	public TabsInter getTab() {
		return tab;
	}
	
	public TabsInter[] getTabs(){
		TabsInter[] tabs = {};
		
		for (int index=0;index<Tabs.values().length;index++){
			tabs[index] = this.getTab();
			
		}
		return tabs; 
	}
	
	
	
	
	
	
	
	
}
