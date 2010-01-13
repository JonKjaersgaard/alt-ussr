package ussr.builder.enumerations;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Vector;

import ussr.builder.simulationLoader.DefaultAtronController;
import ussr.model.Controller;
import ussr.physics.PhysicsSimulation;
import ussr.samples.atron.ATRON;

/**
 * Contains types of modules supported for ATRON modular robot and methods specific to
 * processing of them.
 * @author Konstantinas
 *
 */
public enum ATRONTypesModules {

	ATRON_GENTLE("ATRON gentle","setGentle", "gentle"),
	ATRON_SUPER("ATRON super","setSuper", "super"),
	ATRON_REALISTIC("ATRON realistic","setRealistic", "realistic"),
	ATRON_SMOOTH("ATRON smooth","setSmooth", "smooth"),
	ATRON_RUBBER_RING("ATRON rubberRing","setRubberRing", "rubberRing"),	
	ATRON_RADIO("ATRON radio","setRadio", "radio"),
	ATRON_HALF_DUPLEX("ATRON halfDuplex","setHalfDuplex", "halfDuplex")
	;
	
	/**
	 * The type of module as it is defined in underlying logic. 
	 */
	private String moduleType;
	
	/**
	 * The name of the method(setter method) to set specific type of ATRON module located in the class ussr.samples.ATRON
	 */
	private String methodNameForSetting;

	/**
	 * The name of module type as it is presented to the user in GUI.
	 */
	private String userFriendlyName;

	/**
	 * Contains types of modules supported for ATRON modular robot and methods specific to
	 * processing of them.
	 * @param moduleType,the type of module as it is defined in underlying logic. 
	 * @param methodNameForSetting, the name of the method(setter method) to set specific type of ATRON module located in the class ussr.samples.ATRON
	 * @param userFriendlyName, the name of module type as it is presented to the user in GUI.
	 */
	ATRONTypesModules(String moduleType,String methodNameForSetting, String userFriendlyName){
		this.moduleType = moduleType;
		this.methodNameForSetting = methodNameForSetting;
		this.userFriendlyName = userFriendlyName;
	}


	/**
	 * Returns the type of module as it is defined in underlying logic. 
	 * @return the type of module as it is defined in underlying logic. 
	 */
	public String getModuleType() {
		return moduleType;
	}
	
	/**
	 * Returns the name of the method(setter method) to set specific type of ATRON module located in the class ussr.samples.ATRON
	 * @return the name of the method(setter method) to set specific type of ATRON module located in the class ussr.samples.ATRON
	 */
	public String getMethodNameForSetting() {
		return methodNameForSetting;
	}

	/**
	 * Returns the name of module type as it is presented to the user in GUI.
	 * @return the name of module type as it is presented to the user in GUI.
	 */
	public String getUserFriendlyName() {
		return userFriendlyName;
	}

	/**
	 * Returns the array of objects representing the names of enumerations in users friendly format.
	 * @return the array of objects representing the names of enumerations in users friendly format.
	 */
	public static Object[] getAllInUserFriendlyFormat(){
		Vector <String> namesTypesModules = new Vector<String>();
		for (int nameNr=0;nameNr<values().length;nameNr++){
			namesTypesModules.add(values()[nameNr].getUserFriendlyName()) ;
		} 		
		return namesTypesModules.toArray();
	}


	/**
	 * Returns the name of module type in underlying logic format according to user friendly format.
	 * @param userFriedlyName, the type of module in the form as presented to the user in GUI.
	 * @return  the name of module type in underlying logic format according to user friendly format.
	 */
	public static String getModuleTypeFromUserFriendly(String userFriedlyName ){
		String underlyingLogicName ="";
		for (int type=0; type<values().length;type++){
			if (values()[type].getUserFriendlyName().equals(userFriedlyName)){
				underlyingLogicName = values()[type].getModuleType();  
			}
		}
		if (underlyingLogicName ==""){
			throw new Error("There is no such underlyging logic name matching  the following user friendly name: "+userFriedlyName);
		}
		return underlyingLogicName;
	} 
	
	/**
	 * Instance of ATRON robot;
	 */
	private static ATRON atronRobot;

	/**
	 * Sets all supported  ATRON module types on physical simulation.
	 * @param physicsSimulation, the physical simulation.
	 */
	public static void setAllModuleTypesOnSimulation(PhysicsSimulation physicsSimulation){
		for (int moduleTypeNr=0;moduleTypeNr<values().length; moduleTypeNr++){
			String moduleType = values()[moduleTypeNr].getModuleType();
			atronRobot = new ATRON() {
				public Controller createController() {
					return new DefaultAtronController();
				}
			};
			try {
                String methodNameForSetting = values()[moduleTypeNr].getMethodNameForSetting();
				Method method = ATRON.class.getMethod(methodNameForSetting);
				method.invoke(atronRobot);	
				
			} catch (SecurityException e) {
	            throw new Error("Failed to locate method name: "+ values()[moduleTypeNr].getMethodNameForSetting()+ " , due to following  exception "+ e.getClass().getName());	           
			} catch (NoSuchMethodException e) {
				throw new Error("Failed to locate method name: "+ values()[moduleTypeNr].getMethodNameForSetting()+ " , due to following  exception "+ e.getClass().getName());
			}catch (IllegalArgumentException e) {
				throw new Error("Failed to locate method name: "+ values()[moduleTypeNr].getMethodNameForSetting()+ " , due to following  exception "+ e.getClass().getName());
			} catch (IllegalAccessException e) {
				throw new Error("Failed to locate method name: "+ values()[moduleTypeNr].getMethodNameForSetting()+ " , due to following  exception "+ e.getClass().getName());
			} catch (InvocationTargetException e) {
				throw new Error("Failed to locate method name: "+ values()[moduleTypeNr].getMethodNameForSetting()+ " , due to following  exception "+ e.getClass().getName());
			}
			
			physicsSimulation.setRobot(atronRobot, moduleType);
			
		}
	}
}
