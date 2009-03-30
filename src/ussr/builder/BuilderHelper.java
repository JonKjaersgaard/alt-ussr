package ussr.builder;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ussr.description.geometry.RotationDescription;
import ussr.description.geometry.VectorDescription;
import ussr.description.setup.ModuleConnection;
import ussr.description.setup.ModulePosition;
import ussr.model.Module;
import ussr.physics.jme.JMEModuleComponent;
import ussr.physics.jme.JMESimulation;
import ussr.samples.atron.ATRONBuilder;
import ussr.samples.mtran.MTRANSimulation;
import ussr.samples.odin.OdinBuilder;

import com.jme.math.Matrix3f;
import com.jme.scene.Spatial;
import com.jme.scene.TriMesh;
import com.jmex.physics.DynamicPhysicsNode;

/**
 * The main responsibility of this class is to house common methods and constants used frequently
 * by other classes all over the builder package.
 * @author Konstantinas
 */
public class BuilderHelper {

	/**
	 * Mathematical constant pi
	 */
	private static final float pi = (float)Math.PI;
	/**
	 * The USSR specific prefix
	 */
	public static final String ussrPrefix = "ussr.";
	
	/**
	 * The builder specific prefix, which is unique for builder package.
	 */
	public static final String builderPrefix = ussrPrefix+ "builder.";
	
	/**
	 * The module stem used in both builder and all over ussr.
	 */
	public static final String moduleStem = "module.";	
	
	/**
	 * The labels suffix used to create the key for storing labels of the module.
	 * Unique for builder package.
	 */
	public static final String moduleLabelsSuffix = "labels";
	
	/**
	 * The key for storing the labels of the module as property. The format is: "ussr.builder.module.labels".
	 * Unique for builder package.
	 */
	public static final String labelsKey = builderPrefix +moduleStem + moduleLabelsSuffix;
	
	/**
	 * Temporary value of labels used to indicate that the module do not have any labels.
	 * Used for saving in XML and loading from it. 
	 */
	private static final String tempLabel = "none";	

	/**
	 * The key used to receive the name of the module.
	 */
	public static final String moduleNameKey = "name";	
	
	/**
	 * The type suffix used to create the key for storing the type of the module.
	 */
	public static final String moduleTypeSuffix ="type";
		
	/**
	 *  The key used to receive the type of the module. The format is: "ussr.module.type".
	 *  Was implemented by Ulrik. 
	 */
	public static final String moduleTypeKey = ussrPrefix + moduleStem+ moduleTypeSuffix; 
	
	/**
	 *  The connector stem, used to create the key for receiving the connector number of the module. 
	 */
	public static final String connectorStem = "connector_";
	
	/**
	 * The number suffix, used to create the key for receiving the connector number of the module. 
	 */
	public static final String connectorSuffix = "number";
	
	/**
	 * The key used to receive the connector number of the module. The format is: "ussr.connector_number".
	 * Was implemented by Ulrik.
	 */
	public static final String moduleConnectorNrKey = ussrPrefix +connectorStem + connectorSuffix;
	
	/**
	 * The generator used to generate random integer. 
	 */
	private static final Random generator = new Random();
	
	/**
	 * The identifier, used to locate the string.
	 */
	private static final String CONNECTOR ="Connector";
	
	/**
	 * Symbol used to extract the connector number from the string.  
	 */
	private static final String SPLIT_SYMBOL = "#";

	/**
	 * Returns the module type key.
	 * @return moduleTypeKey, the module type key in format:"ussr.module.type".
	 */
	public static String getModuleTypeKey() {
		return moduleTypeKey;
	}

	/**
	 * Returns the module connector number key.
	 * @return moduleConnectorNrKey, the module connector number key in format:"ussr.connector_number".
	 */
	public static String getModuleConnectorNrKey() {
		return moduleConnectorNrKey;
	}

	/**
	 * Returns the module name key.
	 * @return moduleNameKey,the module name key in format:"name".
	 */
	public static String getModuleNameKey() {
		return moduleNameKey;
	}
	
	/**
	 * Returns the random integer from min to max value of the system.
	 * @return randomInt, the random integer from min to max value of the system.
	 */
	public static int getRandomInt(){
		return generator.nextInt();
		
	}
	
	/**
	 * Returns the module labels key.
	 * @return labelsKey, the module labels key in format: "ussr.builder.module.labels".
	 */
	public static String getLabelsKey() {
		return labelsKey;
	}
	
	/**
	 *  Returns temporary label for indicating that module do not have any label.
	 * @return tempLabel, temporary label in format "none";
	 */
	public static String getTempLabel() {
		return tempLabel;
	}
	
	/**
	 * Removes (deletes) the module from simulation environment
	 * @param selectedModule, module to remove (delete)
	 */
	public static void deleteModule(Module selectedModule){
		/*Remove the module from the internal list of the modules in USSR*/
		selectedModule.getSimulation().getModules().remove(selectedModule);
		
		/* Identify each component of the module and remove the visual of it*/
		int amountComponents= selectedModule.getNumberOfComponents();		
		for (int compon=0; compon<amountComponents;compon++){			
			JMEModuleComponent moduleComponent= (JMEModuleComponent)selectedModule.getComponent(compon);			
			for(DynamicPhysicsNode part: moduleComponent.getNodes()){
				int amountNodes = moduleComponent.getNodes().size();
				for (int node=0; node<amountNodes; node++ ){ //removes bounds and physics
					moduleComponent.getNodes().get(node).removeFromParent();
				}						
				part.setIsCollidable(false);
				part.setActive(false);							
				part.detachAllChildren();//removes visual						
			}        
		}		
	}
	/**
	 * Rotates the component of the module with specified angle around specific coordinate.
	 * @param matrix, initial rotation matrix of component.
	 * @param coordinate, one of the Cartesian coordinates, like X, Y or Z. 
	 * @param angle, the rotation angle in degrees
	 * @return rotationMatrix, the resulting rotation matrix.
	 */
	public static Matrix3f rotateAround(Matrix3f matrix,String coordinate, float angle){

		float cos =(float)Math.cos(angle/(180/pi));// division on 180/pi means transformation into radians 
		float sin =(float) Math.sin(angle/(180/pi));

		Matrix3f rotationMatrix = null;
		if (coordinate.equalsIgnoreCase("X")){			
			rotationMatrix = new Matrix3f(1,0,0,0,cos,-sin,0,sin,cos);
		}else if(coordinate.equalsIgnoreCase("Y")){			
			rotationMatrix = new Matrix3f(cos,0,sin,0,1,0,-sin,0,cos);
		}else if(coordinate.equalsIgnoreCase("Z")){			
			rotationMatrix = new Matrix3f(cos,-sin,0,sin,cos,0,0,0,1);					
		}	
		return rotationMatrix.mult(matrix) ;
	}
	
	/**
	 * Here the connector number is extracted from the string of TriMesh(target). Initial format of string is for example: "Connector 1 #1"
	 * @param simulation, the physical simulation.
	 * @param target, spatial target selected in simulation environment.
	 * @return selected connector number if connector was selected and 1000 if something else. 
	 */
	public static int extractConnectorNr(JMESimulation simulation, Spatial target){
		if(target instanceof TriMesh) {			
			String name = simulation.getGeometryName((TriMesh)target);
			if(name!=null && name.contains(CONNECTOR)){							
				String [] temp = null;	         
				temp = name.split(SPLIT_SYMBOL);// Split by #, into two parts, line describing the connector. For example "Connector 1 #1"
				return Integer.parseInt(temp[1].toString());// Take only the connector number, in above example "1" (at the end)					
			}
		}
		return 1000 /*means connector extraction failed*/;
	}
	
//FIXME FIND BETTER PLACE FOR ME
	public static void connectAllModules(JMESimulation simulation){
		if (simulation.worldDescription.getModulePositions().size()>=0){

		int amountModules = simulation.getModules().size();
		ArrayList<ModulePosition> atronModulePositions = new ArrayList<ModulePosition>();
		ArrayList<ModulePosition> mtranModulePositions = new ArrayList<ModulePosition>(); 
		ArrayList<ModulePosition> odinAllModulePositions = new ArrayList<ModulePosition>();
		ArrayList<ModulePosition> odinBallModulePositions = new ArrayList<ModulePosition>(); 
		ArrayList<ModulePosition> odinOtherModulesPositions = new ArrayList<ModulePosition>();

		List<Module> atronModules = new ArrayList<Module>();
		List<Module> mtranModules = new ArrayList<Module>();
		List<Module> odinAllModules = new ArrayList<Module>();         	


		for (int i=0; i<amountModules; i++){
			Module currentModule = simulation.getModules().get(i);
			
				String moduleName = currentModule.getProperty(BuilderHelper.getModuleNameKey());
				String moduleType = currentModule.getProperty(BuilderHelper.getModuleTypeKey());

				RotationDescription moduleRotation = currentModule.getPhysics().get(0).getRotation(); 
				if (moduleType.contains("ATRON")){
					VectorDescription modulePosition = currentModule.getPhysics().get(0).getPosition();
					atronModulePositions.add(new ModulePosition(moduleName,moduleType,modulePosition,moduleRotation));
					atronModules.add(currentModule);             			
				}else if (moduleType.contains("MTRAN")){ 
					VectorDescription modulePosition = currentModule.getPhysics().get(1).getPosition();
					mtranModulePositions.add(new ModulePosition(moduleName,moduleType,modulePosition,moduleRotation));             			
					mtranModules.add(currentModule);             			
				}else if (moduleType.contains("Odin")){
					VectorDescription modulePosition = currentModule.getPhysics().get(0).getPosition();
					odinAllModulePositions.add(new ModulePosition(moduleName,moduleType,modulePosition,moduleRotation));
					odinAllModules.add(currentModule);

					if (moduleType.contains("OdinBall")){
						odinBallModulePositions.add(new ModulePosition(moduleName,moduleType,modulePosition,moduleRotation));
					}else {
						odinOtherModulesPositions.add(new ModulePosition(moduleName,moduleType,modulePosition,moduleRotation));
					}
			
			}else{
				// do nothing
			}
		}         	

		ATRONBuilder atronbuilder = new ATRONBuilder();             
		ArrayList<ModuleConnection> atronModuleConnection = atronbuilder.allConnections(atronModulePositions);        	 
		simulation.setModules(atronModules);
		simulation.worldDescription.setModulePositions(atronModulePositions);
		simulation.worldDescription.setModuleConnections(atronModuleConnection);                          
		simulation.placeModules();

		ArrayList<ModuleConnection> mtranModuleConnection =MTRANSimulation.allConnections(mtranModulePositions); 
		simulation.setModules(mtranModules);
		simulation.worldDescription.setModulePositions(mtranModulePositions);
		simulation.worldDescription.setModuleConnections(mtranModuleConnection); 
		simulation.placeModules();              

		OdinBuilder odinBuilder = new OdinBuilder();
		odinBuilder.setBallPos(odinBallModulePositions);
		odinBuilder.setModulePos(odinOtherModulesPositions);             
		ArrayList<ModuleConnection> odinModuleConnection = odinBuilder.allConnections();        	 
		simulation.setModules(odinAllModules);
		simulation.worldDescription.setModulePositions(odinAllModulePositions);
		simulation.worldDescription.setModuleConnections(odinModuleConnection);                          
		simulation.placeModules();
	}       
	}
	
}
