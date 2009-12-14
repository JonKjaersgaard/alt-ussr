package ussr.aGui.tabs.simulation;

public class RobotSpecification {

   private String morphologyLocation;
	
	private  String idsModules;
	
	
	
	public RobotSpecification(String morphologyLocation,String idsModules){
		this.morphologyLocation = morphologyLocation;
		this.idsModules = idsModules;
		
	}
	
	public  String getIdsModules() {
		return idsModules;
	}


	public  String getMorphologyLocation() {
		return morphologyLocation;
	}

	
	
	
}
