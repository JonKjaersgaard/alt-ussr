package ussr.builder.construction;

import com.jme.math.Quaternion;
import com.jme.math.Vector3f;
import ussr.description.geometry.VectorDescription;
import ussr.model.Connector;
import ussr.model.Module;
import ussr.physics.jme.JMEModuleComponent;

/**
 * @author Konstantinas
 *
 */
//TODO CONSIDER TO MOVE THE SAME CODE FROM moveOdinBallAccording() METHOD TO PARENT CLASS
public class OdinBallConstructionStrategy extends OdinConstructionStrategy  {
	
	/**
	 * Offset to move Odin's CCP joint (OdinBall) with respect to Structure Link and Telescoping link connectors(also called OdinMuscle)
	 * The value is calculated from equation: 0.035/2+-0.0002(ODINHinge-->ConeCap-->height/2)+-tolerance
	 */
	private final static float offset =0.0177f;	
	
	/**
	 * @param connectorNr
	 * @param selectedModule
	 * @param newMovableModule
	 */
	public void moveOdinMusleAccording(int connectorNr, Module selectedModule,	Module newMovableModule){
		
		/*Get the information about  selected module and connector. Usually it is OdinMuscle*/
		Connector connector = selectedModule.getConnectors().get(connectorNr);				
		VectorDescription position = connector.getPhysics().get(0).getPosition();
		Quaternion rotationQauterion = selectedModule.getPhysics().get(0).getRotation().getRotation(); 

		/*Get x,y,z of selected module connector position. In USSR x axis is horizontal,
		 * y axis is perpendicular to horizontal axis, z is facing the user*/
		float x = position.getX();
		float y = position.getY();
		float z = position.getZ();
		
		/*Update the array of objects storing information about all available rotations and positions 
		 * of OdinBall module with respect to specific connector of OdinMuscle*/
		ModuleMapEntry[] currentModuleMap = updateModuleMap(x,y,z);		
		
		/*Amount of components constituting new movable module*/
		int amountComponents= newMovableModule.getNumberOfComponents();
		
		/*Loop through each component of new movable module and move it with respect to selected 
		 * module component. Here selected module component is Structure link or Telescoping link(also called OdinMuscle)
		 * and movable module is OdinBall(CCP joint)*/
		for (int component=0;component<amountComponents;component++){
			JMEModuleComponent moduleComponent = (JMEModuleComponent)newMovableModule.getComponent(component);					
			
			for (int i=0; i<currentModuleMap.length;i++){
				if (currentModuleMap[i].getConnectorNr()==connectorNr && currentModuleMap[i].getInitialRotation().getRotation().equals(rotationQauterion)){
					moveModuleComponent(moduleComponent,currentModuleMap[i].getNewRotation(),currentModuleMap[i].getNewPosition());
				}				
			}			
		}
	}
	
	
	/**
	 * @param x
	 * @param y
	 * @param z
	 * @return
	 */
	private ModuleMapEntry[] updateModuleMap(float x, float y, float z){

		/*Different offsets along each of coordinate axes.
		 *This is done to get the position of newly added component of the module (movable module) with respect to selected one*/
		float xMinusOffset = x- offset;
		float yMinusOffset = y- offset;
		float zMinusOffset = z- offset;
		float xPlusOffset = x+ offset;
		float yPlusOffset = y+ offset;
		float zPlusOffset = z+ offset;	

		/*Array containing the data for adding the new movable component(module) with respect to selected module.
		 * The format of the object is: (connector number on selected module, the rotation of selected module, the rotation of new movable module, the position of new movable module*/ 
		ModuleMapEntry[] moduleMap = {
				//Connector Nr:0
				new ModuleMapEntry(0,rotation0,rotation00, new Vector3f(xMinusOffset,yMinusOffset,z)),				
				new ModuleMapEntry(0,rotation1,rotation00, new Vector3f(xMinusOffset,y,zMinusOffset)),				
				new ModuleMapEntry(0,rotation2,rotation00, new Vector3f(xMinusOffset,y,zPlusOffset)),				
				new ModuleMapEntry(0,rotation3,rotation00, new Vector3f(xMinusOffset,yPlusOffset,z)),				
				new ModuleMapEntry(0,rotation4,rotation00, new Vector3f(x,yPlusOffset,zPlusOffset)),				
				new ModuleMapEntry(0,rotation5,rotation00, new Vector3f(x,yPlusOffset,zMinusOffset)),
				//Connector Nr:1
//TODO EVERYTHING FOR CONNECTOR 1 CAN BE REMOVED IF IT IS ASSUMED THAT THE INITIALLY THERE IS ODIN BALL IN SIMULATION ENVIRONMENT
//IN THIS WAY THE OVERLAPING OF MODULES CAN BE AVOIDED
				new ModuleMapEntry(1,rotation0,rotation00, new Vector3f(xPlusOffset,yPlusOffset,z)),				
				new ModuleMapEntry(1,rotation1,rotation00, new Vector3f(xPlusOffset,y,zPlusOffset)),				
				new ModuleMapEntry(1,rotation2,rotation00, new Vector3f(xPlusOffset,y,zMinusOffset)),				
				new ModuleMapEntry(1,rotation3,rotation00, new Vector3f(xPlusOffset,yMinusOffset,z)),			
				new ModuleMapEntry(1,rotation4,rotation00, new Vector3f(x,yMinusOffset,zMinusOffset)),				
				new ModuleMapEntry(1,rotation5,rotation00, new Vector3f(x,yMinusOffset,zPlusOffset))				
		};		
		return moduleMap;
	}
}
