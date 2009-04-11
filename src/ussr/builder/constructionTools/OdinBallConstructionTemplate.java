package ussr.builder.constructionTools;

import com.jme.math.Quaternion;
import com.jme.math.Vector3f;

import ussr.builder.BuilderHelper;
import ussr.description.geometry.VectorDescription;
import ussr.model.Module;
import ussr.physics.jme.JMEModuleComponent;
import ussr.physics.jme.JMESimulation;

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
public class OdinBallConstructionTemplate extends OdinConstructionTemplate  {	

	/**
	 * Offset to move Odin's CCP joint (OdinBall) with respect to Structure Link and Telescoping link connectors(also called OdinMuscle)
	 * The value is calculated from equation: 0.035/2+-0.0002(ODINHinge-->ConeCap-->height/2)+-tolerance
	 */
	private final static float OFFSET =0.0177f;
	
	/**
	 * Supports OdinBall construction with respect to OdinMuscle.
	 * @param simulation, the physical simulation.
	 */
	public OdinBallConstructionTemplate(JMESimulation simulation) {
		super(simulation);		
	}
	
	/**
	 * Moves newMovableModule of OdinBall according to selected OdinMuscle module preconditions,
	 * like connector, rotation of selected module,  and so on.
	 * @param connectorNr, the connector number on selected OdinMusle module
	 * @param selectedModule,  the OdinMuscle module selected in simulation environment
	 * @param newMovableModule, the new OdinBall module to move
	 */	
	public void moveOdinBallAccording(int connectorNr, Module selectedModule,	Module newMovableModule, boolean loopFlag){
		
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
		updateModuleMap(x,y,z);		
		
		/*Amount of components constituting new movable module*/
		int amountComponents= newMovableModule.getNumberOfComponents();
		
		/*Loop through each component of new movable module and move it with respect to selected 
		 * module component. Here selected module component is Structure link or Telescoping link(also called OdinMuscle)
		 * and movable module is OdinBall(CCP joint)*/
		for (int component=0;component<amountComponents;component++){
			JMEModuleComponent moduleComponent = (JMEModuleComponent)newMovableModule.getComponent(component);
			
			/*Loop through and locate the object matching the description of current component(also selected module).*/
			for (int i=0; i<moduleMap.length;i++){
				if (moduleMap[i].getConnectorNr()==connectorNr && moduleMap[i].getInitialRotation().getRotation().equals(rotationQauterion)){
					/*If component(module) already exists at current position, delete movableModuleComponent and newly added module.*/
					if (componentExitst(moduleMap[i].getNewPosition(), SEARCH_TOLERANCE)&& loopFlag == false ){						
						BuilderHelper.deleteModule(moduleComponent.getModel());											
					}else {/*move the component to new position with new rotation*/
					moveModuleComponent(moduleComponent,moduleMap[i].getNewRotation(),moduleMap[i].getNewPosition());
					}
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
	public void updateModuleMap(float x, float y, float z){

		/*Different offsets along each of coordinate axes.
		 *This is done to get the position of newly added component of the module (movable module) with respect to selected one*/
		float xMinusOffset = x- OFFSET;
		float yMinusOffset = y- OFFSET;
		float zMinusOffset = z- OFFSET;
		float xPlusOffset = x+ OFFSET;
		float yPlusOffset = y+ OFFSET;
		float zPlusOffset = z+ OFFSET;	

		/*Array containing the data for adding the new movable component(module) with respect to selected module.
		 * The format of the object is: (connector number on selected module, the rotation of selected module, the rotation of new movable module, the position of new movable module*/ 
		ModuleMapEntryHelper[] moduleMap = {
				/*ConnectorNr0*/
				new ModuleMapEntryHelper(CONNECTORnr0,ROTATION0,ROTATION000, new Vector3f(xMinusOffset,yMinusOffset,z)),				
				new ModuleMapEntryHelper(CONNECTORnr0,ROTATION1,ROTATION000, new Vector3f(xMinusOffset,y,zMinusOffset)),				
				new ModuleMapEntryHelper(CONNECTORnr0,ROTATION2,ROTATION000, new Vector3f(xMinusOffset,y,zPlusOffset)),				
				new ModuleMapEntryHelper(CONNECTORnr0,ROTATION3,ROTATION000, new Vector3f(xMinusOffset,yPlusOffset,z)),				
				new ModuleMapEntryHelper(CONNECTORnr0,ROTATION4,ROTATION000, new Vector3f(x,yMinusOffset,zMinusOffset)),				
				new ModuleMapEntryHelper(CONNECTORnr0,ROTATION5,ROTATION000, new Vector3f(x,yMinusOffset,zPlusOffset)),
				new ModuleMapEntryHelper(CONNECTORnr0,ROTATION6,ROTATION000, new Vector3f(x,yPlusOffset,zMinusOffset)),
				new ModuleMapEntryHelper(CONNECTORnr0,ROTATION7,ROTATION000, new Vector3f(x,yPlusOffset,zPlusOffset)),
				new ModuleMapEntryHelper(CONNECTORnr0,ROTATION8,ROTATION000, new Vector3f(xPlusOffset,yMinusOffset,z)),
				new ModuleMapEntryHelper(CONNECTORnr0,ROTATION9,ROTATION000, new Vector3f(xPlusOffset,y,zMinusOffset)),
				new ModuleMapEntryHelper(CONNECTORnr0,ROTATION10,ROTATION000, new Vector3f(xPlusOffset,y,zPlusOffset)),
				new ModuleMapEntryHelper(CONNECTORnr0,ROTATION11,ROTATION000, new Vector3f(xPlusOffset,yPlusOffset,z)),				
				/*Connector Nr1*/
				new ModuleMapEntryHelper(CONNECTORnr1,ROTATION0,ROTATION000, new Vector3f(xPlusOffset,yPlusOffset,z)),				
				new ModuleMapEntryHelper(CONNECTORnr1,ROTATION1,ROTATION000, new Vector3f(xPlusOffset,y,zPlusOffset)),				
				new ModuleMapEntryHelper(CONNECTORnr1,ROTATION2,ROTATION000, new Vector3f(xPlusOffset,y,zMinusOffset)),				
				new ModuleMapEntryHelper(CONNECTORnr1,ROTATION3,ROTATION000, new Vector3f(xPlusOffset,yMinusOffset,z)),			
				new ModuleMapEntryHelper(CONNECTORnr1,ROTATION4,ROTATION000, new Vector3f(x,yPlusOffset,zPlusOffset)),				
				new ModuleMapEntryHelper(CONNECTORnr1,ROTATION5,ROTATION000, new Vector3f(x,yPlusOffset,zMinusOffset)),
				new ModuleMapEntryHelper(CONNECTORnr1,ROTATION6,ROTATION000, new Vector3f(x,yMinusOffset,zPlusOffset)),
				new ModuleMapEntryHelper(CONNECTORnr1,ROTATION7,ROTATION000, new Vector3f(x,yMinusOffset,zMinusOffset)),
				new ModuleMapEntryHelper(CONNECTORnr1,ROTATION8,ROTATION000, new Vector3f(xMinusOffset,yPlusOffset,z)),
				new ModuleMapEntryHelper(CONNECTORnr1,ROTATION9,ROTATION000, new Vector3f(xMinusOffset,y,zPlusOffset)),
				new ModuleMapEntryHelper(CONNECTORnr1,ROTATION10,ROTATION000, new Vector3f(xMinusOffset,y,zMinusOffset)),
				new ModuleMapEntryHelper(CONNECTORnr1,ROTATION11,ROTATION000, new Vector3f(xMinusOffset,yMinusOffset,z)),				
		};		
		this.moduleMap = moduleMap;
	}
}
