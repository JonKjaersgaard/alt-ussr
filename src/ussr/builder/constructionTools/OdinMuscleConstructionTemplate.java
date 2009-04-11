package ussr.builder.constructionTools;

import ussr.builder.BuilderHelper;
import ussr.description.geometry.VectorDescription;
import ussr.model.Module;
import ussr.physics.jme.JMEModuleComponent;
import ussr.physics.jme.JMESimulation;

import com.jme.math.Quaternion;
import com.jme.math.Vector3f;

/**
 * The main responsibility of this class is to take care of construction functions for
 * construction of Telescoping link(also called OdinMuscle). Here module consists of components. To be more precise
 * OdinMuscle consists of two components(two cylinders with cones on them). Connectors are considered to be as 
 * parts of components. OdinMuscle link module has two connectors, numbered as: 0 and 1. All methods are specific to 
 * to design of OdinMuscle module.
 * @author Konstantinas
 */
//FIXME 1) UPDATE COMMENTS
//FIXME 2) FIX EXISTING IMPROVEMENTS
//FIXME 3) CONSIDER TO MOVE THE SAME CODE FROM moveOdinMusleAccording() METHOD TO PARENT CLASS
public class OdinMuscleConstructionTemplate extends OdinConstructionTemplate  {	

	/**
	 *  Offset to move Structure Link and Telescoping(also called OdinMuscle) link with respect to CCP joint connectors(also called OdinBall).
		0.03+0.035/2 +-0.015, means (OdinMuscle-->CylinderShape-->height/2)+(ODINMuscle-->coneCap1-->radius/2)+-tolerance
	 */
	private final static float OFFSET =0.046f;	
	
	/**
	 * Supports OdinMuscle construction with respect to OdinBall
	 * @param simulation, the physical simulation.
	 */
	public OdinMuscleConstructionTemplate(JMESimulation simulation) {
		super(simulation);		
	}

	/**
	 * Moves newMovableModule of OdinMuscle according to selected OdinBall module preconditions,
	 * like connector, rotation of selected module,  and so on.
	 * @param connectorNr, the connector number on selected OdinBall module
	 * @param selectedModule,  the OdinBall module selected in simulation environment
	 * @param newMovableModule, the new OdinMuscle module to move
	 */	
	public void moveOdinMusleAccording(int connectorNr, Module selectedModule,	Module newMovableModule, boolean loopFlag){		

		/*Get information about  selected module and connector. Usually it is OdinBall*/			
		VectorDescription connectorPosition = getConnectorPosition(connectorNr,selectedModule);			
		Quaternion rotationQuatSelectedModule = selectedModule.getComponent(0).getRotation().getRotation();
		
		/*Get x,y,z of selected module connector position. In USSR x axis is horizontal,
		 * y axis is perpendicular to horizontal axis, z is facing the user*/
		float x = connectorPosition.getX();
		float y = connectorPosition.getY();
		float z = connectorPosition.getZ();
		
		/*Update the array of objects storing information about all available rotations and positions 
		 * of OdinMuscle module with respect to specific connector of OdinBall*/
		 updateModuleMap(x,y,z);						

		/*Amount of components constituting new movable module*/
		int amountComponents= newMovableModule.getNumberOfComponents();		
		
		/*Loop through each component of new movable module and move it with respect to selected 
		 * module component. Here selected module component is OdinBall(CCP joint) and movable module
		 * is Structure link or Telescoping link(also called OdinMuscle).*/
		for (int component=0;component<amountComponents;component++){
			JMEModuleComponent moduleComponent = (JMEModuleComponent)newMovableModule.getComponent(component);
			/*Loop through and locate the object matching the description of current component(also selected module).*/
			for (int i=0; i<moduleMap.length;i++){
				if (moduleMap[i].getConnectorNr()==connectorNr && moduleMap[i].getInitialRotation().getRotation().equals(rotationQuatSelectedModule)){
					if (componentExitst(moduleMap[i].getNewPosition(), SEARCH_TOLERANCE)&& loopFlag == false){						
						BuilderHelper.deleteModule(moduleComponent.getModel());											
					}else {/*move the component to new position with new rotation*/
					moveModuleComponent(moduleComponent,moduleMap[i].getNewRotation(),moduleMap[i].getNewPosition());
					}
				}					
			}			
		}
	}

	/**
	 * Updates and returns the array containing the information about all available rotations and positions
	 * of OdinMuscle module with respect to specific connector of OdinBall	
	 * @param x, the amount of x coordinate of current position of component
	 * @param y, the amount of y coordinate of current position of component
	 * @param z, the amount of z coordinate of current position of component
	 * @return moduleMap, updated array of objects.
	 */
	public void updateModuleMap(float x, float y, float z){

		/*Different offsets along each of coordinate axes.
		 *This is done to get the position of newly added component of the module (movable module) with respect to selected one*/
		float xPlusOffset = x+OFFSET;
		float xMinusOffset = x-OFFSET;
		float yPlusOffset = y+OFFSET;
		float yMinusOffset = y-OFFSET;
		float zPlusOffset = z+OFFSET;
		float zMinusOffset = z-OFFSET;	

		/*Array containing the data for adding the new movable component(module) with respect to selected module.
		 * The format of the object is: (connector number on selected module, the rotation of selected module, the rotation of new movable module, the position of new movable module*/ 
		ModuleMapEntryHelper[] moduleMap = {
				/*Connectors Nr0-11*/
				new ModuleMapEntryHelper(CONNECTORnr0,ROTATION000,ROTATION0, new Vector3f(xMinusOffset,yMinusOffset,z)),				
				new ModuleMapEntryHelper(CONNECTORnr1,ROTATION000,ROTATION1, new Vector3f(xMinusOffset,y,zMinusOffset)),				
				new ModuleMapEntryHelper(CONNECTORnr2,ROTATION000,ROTATION2, new Vector3f(xMinusOffset,y,zPlusOffset)),				
				new ModuleMapEntryHelper(CONNECTORnr3,ROTATION000,ROTATION3, new Vector3f(xMinusOffset,yPlusOffset,z)),				
				new ModuleMapEntryHelper(CONNECTORnr4,ROTATION000,ROTATION4, new Vector3f(x,yMinusOffset,zMinusOffset)),				
				new ModuleMapEntryHelper(CONNECTORnr5,ROTATION000,ROTATION5, new Vector3f(x,yMinusOffset,zPlusOffset)),				
				new ModuleMapEntryHelper(CONNECTORnr6,ROTATION000,ROTATION6, new Vector3f(x,yPlusOffset,zMinusOffset)),				
				new ModuleMapEntryHelper(CONNECTORnr7,ROTATION000,ROTATION7, new Vector3f(x,yPlusOffset,zPlusOffset)),				
				new ModuleMapEntryHelper(CONNECTORnr8,ROTATION000,ROTATION8, new Vector3f(xPlusOffset,yMinusOffset,z)),				
				new ModuleMapEntryHelper(CONNECTORnr9,ROTATION000,ROTATION9, new Vector3f(xPlusOffset,y,zMinusOffset)),			
				new ModuleMapEntryHelper(CONNECTORnr10,ROTATION000,ROTATION10, new Vector3f(xPlusOffset,y,zPlusOffset)),				
				new ModuleMapEntryHelper(CONNECTORnr11,ROTATION000,ROTATION11, new Vector3f(xPlusOffset,yPlusOffset,z))				
		};		
		this.moduleMap = moduleMap;
	}
}
