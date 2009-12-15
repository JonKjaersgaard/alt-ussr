package ussr.aGui.tabs.simulation;

import java.io.Serializable;

public class RobotSpecification implements Serializable {

  
	private String controllerLocation;
	



	private String morphologyLocation;
	



	private  String idsModules;
	
	
	
	public RobotSpecification(){
		
	}
	
	
	
	
	
	public String getControllerLocation() {
		return controllerLocation;
	}

	public void setControllerLocation(String controllerLocation) {
		this.controllerLocation = controllerLocation;
	}
	
	
	public  String getIdsModules() {
		return idsModules;
	}


	public  String getMorphologyLocation() {
		return morphologyLocation;
	}
	
	public void setMorphologyLocation(String morphologyLocation) {
		this.morphologyLocation = morphologyLocation;
	}

	
	
	
}
