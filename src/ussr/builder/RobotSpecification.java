package ussr.builder;

import java.util.Map;

import ussr.description.geometry.VectorDescription;
import ussr.description.setup.ModulePosition;

public class RobotSpecification {

	private static String morphologyLocation;
	
	private static Map<Integer, ModulePosition> robotModules;
	
	private static String idsModules;

	public static String getIdsModules() {
		return idsModules;
	}

	public static void setIdsModules(String idsModules) {
		RobotSpecification.idsModules = idsModules;
	}

	public static Map<Integer, ModulePosition> getRobotModules() {
		return robotModules;
	}

	public static void setRobotModules(Map<Integer, ModulePosition> robotModules) {
		RobotSpecification.robotModules = robotModules;
	}

	public static String getMorphologyLocation() {
		return morphologyLocation;
	}

	public static void setMorphologyLocation(String morphologyLocation) {
		RobotSpecification.morphologyLocation = morphologyLocation;
	}	
	
}
