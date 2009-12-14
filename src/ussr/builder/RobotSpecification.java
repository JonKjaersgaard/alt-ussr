package ussr.builder;

import java.util.Map;

import ussr.description.geometry.VectorDescription;

public class RobotSpecification {

	private static String morphologyLocation;
	
	private static Map<String,VectorDescription> robotModules;

	public static Map<String, VectorDescription> getRobotModules() {
		return robotModules;
	}

	public static void setRobotModules(Map<String, VectorDescription> robotModules) {
		RobotSpecification.robotModules = robotModules;
	}

	public static String getMorphologyLocation() {
		return morphologyLocation;
	}

	public static void setMorphologyLocation(String morphologyLocation) {
		RobotSpecification.morphologyLocation = morphologyLocation;
	}	
	
}
