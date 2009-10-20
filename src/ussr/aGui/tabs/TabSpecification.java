package ussr.aGui.tabs;

import ussr.physics.jme.JMESimulation;

public class TabSpecification {

	private SupportedTabs supportedTab;
	
	private boolean firstTabbedPane;
	
	private String imageIconDirectory;
	
	private String tabTitle;
	
	private boolean visible;
	
	private JMESimulation jmeSimulation;	
	
	public TabSpecification(SupportedTabs supportedTab,boolean firstTabbedPane,String imageIconDirectory,String tabTitle, boolean visible,JMESimulation jmeSimulation){
		this.supportedTab = supportedTab;
		this.firstTabbedPane = firstTabbedPane;
		this.imageIconDirectory = imageIconDirectory;
		this.tabTitle = tabTitle;
		this.visible = visible;
		this.jmeSimulation = jmeSimulation;		
	}
	
	
}
