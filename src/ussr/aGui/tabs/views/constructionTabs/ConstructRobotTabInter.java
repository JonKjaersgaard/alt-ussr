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
   
   
	public enum ATRONStandardRotations   {		
		EW, WE, DU, UD, SN, NS,	
	}
	
	
	public enum MTRANStandardRotations {
		ORI1, ORI1X, ORI1Y, ORI1XY, ORI2, ORI2X, ORI2Y, ORI2XY, ORI3, ORI3X, ORI3Y, ORI3XY;
	}
	
	public enum CKBotStandardRotations {
		
		/*Here: ROT - rotation, OPPOS - opposite, MIN - minus*/
		ROT_0,
		ROT_0_OPPOS, 
		ROT_0_90Z, 
		ROT_0_OPPOS_90Z,
		ROT_0_90X,
		ROT_0_MIN90X,
		ROT_0_90X_90Y,
		ROT_0_270X_90Y,
		ROT_0_90Y,
		ROT_0_MIN90Y,
		ROT_0_90X_MIN90Z,
		ROT_0_MIN90X_MIN90Z;
	}
}