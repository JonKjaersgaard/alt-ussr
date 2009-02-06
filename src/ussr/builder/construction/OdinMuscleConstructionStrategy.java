package ussr.builder.construction;

import ussr.description.geometry.VectorDescription;
import ussr.model.Connector;
import ussr.model.Module;
import ussr.physics.jme.JMEModuleComponent;
import com.jme.math.Quaternion;
import com.jme.math.Vector3f;

/**
 * @author Konstantinas
 *
 */
//TODO CONSIDER TO MOVE THE SAME CODE FROM moveOdinMusleAccording() METHOD TO PARENT CLASS
public class OdinMuscleConstructionStrategy extends OdinConstructionStrategy  {	

	/**
	 *  Offset to move Structure Link and Telescoping(also called OdinMuscle) link with respect to CCP joint connectors(also called OdinBall).
		0.03+0.035/2 +-0.015, means (OdinMuscle-->CylinderShape-->height/2)+(ODINMuscle-->coneCap1-->radius/2)+-tolerance
	 */
	private final static float offset =0.046f;	

	/**
	 * @param connectorNr
	 * @param selectedModule
	 * @param newMovableModule
	 */
	public void moveOdinMusleAccording(int connectorNr, Module selectedModule,	Module newMovableModule){		

		/*Get information about  selected module and connector. Usually it is OdinBall*/
		Connector connector = selectedModule.getConnectors().get(connectorNr);		
		VectorDescription connectorPosition = connector.getPhysics().get(0).getPosition();			
		Quaternion rotationQuatSelectedModule = selectedModule.getComponent(0).getRotation().getRotation();
		
		/*Get x,y,z of selected module connector position. In USSR x axis is horizontal,
		 * y axis is perpendicular to horizontal axis, z is facing the user*/
		float x = connectorPosition.getX();
		float y = connectorPosition.getY();
		float z = connectorPosition.getZ();
		
		/*Update the array of objects storing information about all available rotations and positions 
		 * of OdinMuscle module with respect to specific connector of OdinBall*/
		ModuleMapEntry[] currentModuleMap = updateModuleMap(x,y,z);						

		/*Amount of components constituting new movable module*/
		int amountComponents= newMovableModule.getNumberOfComponents();		
		
		/*Loop through each component of new movable module and move it with respect to selected 
		 * module component. Here selected module component is OdinBall(CCP joint) and movable module
		 * is Structure link or Telescoping link(also called OdinMuscle).*/
		for (int component=0;component<amountComponents;component++){
			JMEModuleComponent moduleComponent = (JMEModuleComponent)newMovableModule.getComponent(component);
			for (int i=0; i<currentModuleMap.length;i++){
				if (currentModuleMap[i].getConnectorNr()==connectorNr && currentModuleMap[i].getInitialRotation().getRotation().equals(rotationQuatSelectedModule)){
					moveModuleComponent(moduleComponent,currentModuleMap[i].getNewRotation(),currentModuleMap[i].getNewPosition());
				}				
			}			
		}
	}


	/**
	 * Updates and returns the array containing the information about all available rotations and positions
	 * of OdinMuscle module with respect to specific connector	
	 * @param x, the value of x coordinate of current position of component
	 * @param y, the value of y coordinate of current position of component
	 * @param z, the value of z coordinate of current position of component
	 * @return moduleMap, updated array of objects.
	 */
	private ModuleMapEntry[] updateModuleMap(float x, float y, float z){

		/*Different offsets along each of coordinate axes.
		 *This is done to get the position of newly added component of the module (movable module) with respect to selected one*/
		float xPlusOffset = x+offset;
		float xMinusOffset = x-offset;
		float yPlusOffset = y+offset;
		float yMinusOffset = y-offset;
		float zPlusOffset = z+offset;
		float zMinusOffset = z-offset;	

		/*Array containing the data for adding the new movable component(module) with respect to selected module.
		 * The format of the object is: (connector number on selected module, the rotation of selected module, the rotation of new movable module, the position of new movable module*/ 
		ModuleMapEntry[] moduleMap = {
				//Connectors Nr:0-11
				new ModuleMapEntry(0,rotation00,rotation0, new Vector3f(xMinusOffset,yMinusOffset,z)),				
				new ModuleMapEntry(1,rotation00,rotation1, new Vector3f(xMinusOffset,y,zMinusOffset)),				
				new ModuleMapEntry(2,rotation00,rotation2, new Vector3f(xMinusOffset,y,zPlusOffset)),				
				new ModuleMapEntry(3,rotation00,rotation3, new Vector3f(xMinusOffset,yPlusOffset,z)),				
				new ModuleMapEntry(4,rotation00,rotation4, new Vector3f(x,yMinusOffset,zMinusOffset)),				
				new ModuleMapEntry(5,rotation00,rotation5, new Vector3f(x,yMinusOffset,zPlusOffset)),				
				new ModuleMapEntry(6,rotation00,rotation5, new Vector3f(x,yPlusOffset,zMinusOffset)),				
				new ModuleMapEntry(7,rotation00,rotation4, new Vector3f(x,yPlusOffset,zPlusOffset)),				
				new ModuleMapEntry(8,rotation00,rotation3, new Vector3f(xPlusOffset,yMinusOffset,z)),				
				new ModuleMapEntry(9,rotation00,rotation2, new Vector3f(xPlusOffset,y,zMinusOffset)),			
				new ModuleMapEntry(10,rotation00,rotation1, new Vector3f(xPlusOffset,y,zPlusOffset)),				
				new ModuleMapEntry(11,rotation00,rotation0, new Vector3f(xPlusOffset,yPlusOffset,z))				
		};		
		return moduleMap;
	}
}
