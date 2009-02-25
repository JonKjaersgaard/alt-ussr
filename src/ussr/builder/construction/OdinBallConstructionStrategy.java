package ussr.builder.construction;

import com.jme.math.Quaternion;
import com.jme.math.Vector3f;
import ussr.description.geometry.VectorDescription;
import ussr.model.Module;
import ussr.physics.jme.JMEModuleComponent;

/**
 * The main responsibility of this class is to take care of construction functions for
 * construction of OdinBall module. Here module consists of components. To be more precise
 * OdinBall(also called CCP join) consists of one component (sphere). Connectors are considered to be as 
 * parts of components. OdinBall(CCP joint) module has 12 connectors, numbered as: 0,1,2,3,4,5,6,7,8,9,10 and 11.
 * All methods are specific to OdinBall module design.
 * @author Konstantinas
 */
//FIXME 1) UPDATE COMMENTS
//FIXME 2) FIX EXISTING IMPROVEMENTS
public class OdinBallConstructionStrategy extends OdinConstructionStrategy  {
	
	/**
	 * Offset to move Odin's CCP joint (OdinBall) with respect to Structure Link and Telescoping link connectors(also called OdinMuscle)
	 * The value is calculated from equation: 0.035/2+-0.0002(ODINHinge-->ConeCap-->height/2)+-tolerance
	 */
	private final static float offset =0.0177f;	
	
	/**
	 * Moves newMovableModule of OdinBall according to selected OdinMuscle module preconditions,
	 * like connector, rotation of selected module,  and so on.
	 * @param connectorNr, the connector number on selected OdinMusle module
	 * @param selectedModule,  the OdinMuscle module selected in simulation environment
	 * @param newMovableModule, the new OdinBall module to move
	 */	
	public void moveOdinBallAccording(int connectorNr, Module selectedModule,	Module newMovableModule){
		
		/*Get the information about  selected OdinMuscle module and connector */					
		VectorDescription connectorPosition = getConnectorPosition(connectorNr,selectedModule);
		Quaternion rotationQauterion = selectedModule.getPhysics().get(0).getRotation().getRotation(); 

		/*Get x,y,z of selected module connector position. In USSR x axis is horizontal,
		 * y axis is perpendicular to horizontal axis, z is facing the user*/
		float x = connectorPosition.getX();
		float y = connectorPosition.getY();
		float z = connectorPosition.getZ();
		
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
	 * Updates and returns the array of objects containing the information about all available initial rotations 
	 * of OdinMuscle module and  rotations together with positions of newly added OdinBall module with respect to specific 
	 * connector number chosen on OdinMuscle.	 
	 * @param x, the value of x coordinate of current position of component
	 * @param y, the value of y coordinate of current position of component
	 * @param z, the value of z coordinate of current position of component
	 * @return moduleMap, updated array of objects.
	 */
//FIXME IS  IT A GOOD WAY TO DO THAT?
	public ModuleMapEntry[] updateModuleMap(float x, float y, float z){

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
				new ModuleMapEntry(0,rotation4,rotation00, new Vector3f(x,yMinusOffset,zMinusOffset)),				
				new ModuleMapEntry(0,rotation5,rotation00, new Vector3f(x,yMinusOffset,zPlusOffset)),
				new ModuleMapEntry(0,rotation6,rotation00, new Vector3f(x,yPlusOffset,zMinusOffset)),
				new ModuleMapEntry(0,rotation7,rotation00, new Vector3f(x,yPlusOffset,zPlusOffset)),
				new ModuleMapEntry(0,rotation8,rotation00, new Vector3f(xPlusOffset,yMinusOffset,z)),
				new ModuleMapEntry(0,rotation9,rotation00, new Vector3f(xPlusOffset,y,zMinusOffset)),
				new ModuleMapEntry(0,rotation10,rotation00, new Vector3f(xPlusOffset,y,zPlusOffset)),
				new ModuleMapEntry(0,rotation11,rotation00, new Vector3f(xPlusOffset,yPlusOffset,z)),				
				//Connector Nr:1
				new ModuleMapEntry(1,rotation0,rotation00, new Vector3f(xPlusOffset,yPlusOffset,z)),				
				new ModuleMapEntry(1,rotation1,rotation00, new Vector3f(xPlusOffset,y,zPlusOffset)),				
				new ModuleMapEntry(1,rotation2,rotation00, new Vector3f(xPlusOffset,y,zMinusOffset)),				
				new ModuleMapEntry(1,rotation3,rotation00, new Vector3f(xPlusOffset,yMinusOffset,z)),			
				new ModuleMapEntry(1,rotation4,rotation00, new Vector3f(x,yPlusOffset,zPlusOffset)),				
				new ModuleMapEntry(1,rotation5,rotation00, new Vector3f(x,yPlusOffset,zMinusOffset)),
				new ModuleMapEntry(1,rotation6,rotation00, new Vector3f(x,yMinusOffset,zPlusOffset)),
				new ModuleMapEntry(1,rotation7,rotation00, new Vector3f(x,yMinusOffset,zMinusOffset)),
				new ModuleMapEntry(1,rotation8,rotation00, new Vector3f(xMinusOffset,yPlusOffset,z)),
				new ModuleMapEntry(1,rotation9,rotation00, new Vector3f(xMinusOffset,y,zPlusOffset)),
				new ModuleMapEntry(1,rotation10,rotation00, new Vector3f(xMinusOffset,y,zMinusOffset)),
				new ModuleMapEntry(1,rotation11,rotation00, new Vector3f(xMinusOffset,yMinusOffset,z)),				
		};		
		return moduleMap;
	}
}
