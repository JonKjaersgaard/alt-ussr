package ussr.aGui.tabs.simulation;

import java.util.Map;

import ussr.description.geometry.VectorDescription;
import ussr.description.setup.ModulePosition;

public class TemporaryRobotSpecification {

	private static String morphologyLocation;
	
	private static String idsModules;
	
	
	
	
	public static String getIdsModules() {
		return idsModules;
	}

	public static void setIdsModules(String idsModules) {
		TemporaryRobotSpecification.idsModules = idsModules;
	}

	public static String getMorphologyLocation() {
		return morphologyLocation;
	}

	public static void setMorphologyLocation(String morphologyLocation) {
		TemporaryRobotSpecification.morphologyLocation = morphologyLocation;
	}
	
}
