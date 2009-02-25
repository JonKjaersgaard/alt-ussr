package ussr.builder;

public class BuilderUtilities {
//SHOULD NOT BE STATIC OR WHAT
	public static final String builderPrefix = "ussr.builder.";
	
	public static final String moduleStem = "module.";
	
	public static final String moduleDeletionSuffix = "deletionKey";
	
	/*ussr.builder.module.deletionKey*/
	public static final String moduleDeletionKey = builderPrefix +moduleStem + moduleDeletionSuffix;
	
	public static final String moduleDeletionValue = "deleted";
	
//MAYBE CAN BE RECEIVED FROM USSR INTERNALLY
	public static final String ussrPrefix = "ussr.";
	
	public static final String moduleTypeSuffix ="type";
	
	// The property called "ussr.module.type" was implemented by Ulrik.
	public static final String moduleTypeKey = ussrPrefix + moduleStem+ moduleTypeSuffix; 
	
	public static final String connectorStem = "connector_";
	
	public static final String connectorSuffix = "number";

	// The property called "ussr.connector_number" was implemented by Ulrik.	
	public static final String moduleConnectorNrKey = ussrPrefix +connectorStem + connectorSuffix;
	
	public static final String moduleNameKey = "name"; 

	public static String getModuleDeletionKey() {
		return moduleDeletionKey;
	}

	public static String getModuleDeletionValue() {
		return moduleDeletionValue;
	}

	public static String getModuleTypeKey() {
		return moduleTypeKey;
	}

	public static String getModuleConnectorNrKey() {
		return moduleConnectorNrKey;
	}

	public static String getModuleNameKey() {
		return moduleNameKey;
	}
	
	


	
}
