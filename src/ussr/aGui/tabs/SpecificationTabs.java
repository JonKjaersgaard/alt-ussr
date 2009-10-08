package ussr.aGui.tabs;

import java.util.ArrayList;

public class SpecificationTabs {

	private ArrayList<String> tabTitles;
	



	private ArrayList<TabsInter> tabs;
	
	
	public SpecificationTabs(ArrayList<String> tabTitles, ArrayList<TabsInter> tabs){
		this.tabTitles = tabTitles;
		this.tabs = tabs;
	}
	
	public ArrayList<String> getTabTitles() {
		return tabTitles;
	}


	public ArrayList<TabsInter> getTabs() {
		return tabs;
	}
}
