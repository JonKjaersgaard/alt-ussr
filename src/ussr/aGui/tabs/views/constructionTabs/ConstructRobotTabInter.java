package ussr.aGui.tabs.views.constructionTabs;


public interface ConstructRobotTabInter extends ConstructionTabsInter {

	/**
	 * Numbers of connectors on ATRON module
	 */
	public String[] ATRON_CONNECTORS = {"0","1", "2", "3","4", "5", "6","7"};

	/**
	 * Numbers of connectors on  MTRAN module
	 */
	public String[] MTRAN_CONNECTORS = {"0","1", "2", "3","4", "5"};

	/**
	 * Numbers of connectors on Odin module(in particular OdinBall)
	 */
	public String[] ODIN_CONNECTORS = {"0","1", "2", "3","4", "5", "6","7", "8","9", "10", "11"};

	/**
	 * Numbers of connectors on CKBotStandard module(
	 */
	public String[] CKBOT_CONNECTORS = {"0","1", "2", "3"};


	public String[] TOOL_TIP_TEXTS = {
			/*0*/ "Start constructing new robot",
			/*1*/ "Save or load robot", //TODO eliminate me 
			/*2*/ "Rotate opposite",
			/*3*/ "Rotate with standard rotation",
			/*4*/ "Move",
			/*5*/ "Delete",
			/*6*/ "Color Connectors",
			/*7*/ "Construction tools",
			/*8*/ "On selected connector (select connector)",
            /*9*/ "On chosen connector number (select module)",
            /*10*/"To all connectors(select module)",
            /*11*/"On previous connector",
            /*12*/"Jump from one connector to the next connector(loop)",
            /*13*/"On next connector",
            /*14*/"Switch the type of module",


	};
}
