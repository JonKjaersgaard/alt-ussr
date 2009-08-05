package ussr.builder.constructionTools;

import ussr.builder.BuilderHelper;
import ussr.model.Module;
import ussr.physics.jme.JMEModuleComponent;
import ussr.physics.jme.JMESimulation;
import ussr.samples.atron.ATRON;
import com.jme.math.Quaternion;
import com.jme.math.Vector3f;

/**
 * The main responsibility of this class is to take care of construction functions for
 * construction of ATRON modular robot's morphology. Here module consists of components. To be more precise
 * ATRON module consists of two components (two hemispheres). Connectors are considered to be as parts
 * of components. ATRON module has 8 connectors, numbered as: 0,1,2,3,4,5,6 and 7. All methods are 
 * specific to ATRON module design.
 * @author Konstantinas
 */
public class ATRONConstructionTemplate extends ModularRobotConstructionTemplate  {	

	/**
	 * The numbers of connectors on the ATRON module
	 */
	private final static int CONNECTORnr0 = 0,CONNECTORnr1 = 1,CONNECTORnr2 = 2,CONNECTORnr3 = 3,CONNECTORnr4 = 4, CONNECTORnr5 = 5,CONNECTORnr6 = 6, CONNECTORnr7 = 7;

	/**
	 * Supported rotations of ATRON module."EW" means east-west and so on.
	 */
	private final static String EW = "EW", WE= "WE", SN = "SN", NS = "NS", UD = "UD", DU = "DU";
	
	/**
	 * The physical lattice distance between two ATRON modules
	 */
	private final static float OFFSET = ATRON.UNIT;
	
	/**
	 * The array of objects containing information about ATRON specific rotations.
	 * The logic is: if rotation is "EW"(means east-west), then the rotation value is ATRON.ROTATION_EW and opposite
	 * to this rotation is ATRON.ROTATION_WE (Look the first entry in array beneath).
	 */
	private final static ModuleRotationMapEntryHelper[] MODULE_ROTATION_MAP =  {
		new ModuleRotationMapEntryHelper(EW,ATRON.ROTATION_EW,ATRON.ROTATION_WE),
		new ModuleRotationMapEntryHelper(WE,ATRON.ROTATION_WE,ATRON.ROTATION_EW),
		new ModuleRotationMapEntryHelper(SN,ATRON.ROTATION_SN,ATRON.ROTATION_NS),
		new ModuleRotationMapEntryHelper(NS,ATRON.ROTATION_NS,ATRON.ROTATION_SN),
		new ModuleRotationMapEntryHelper(UD,ATRON.ROTATION_UD,ATRON.ROTATION_DU),
		new ModuleRotationMapEntryHelper(DU,ATRON.ROTATION_DU,ATRON.ROTATION_UD),
	};
	
	/**
	 * Tolerance used to identify if component (module) already exists in interval of space.
	 */
	private final static float SERACH_TOLERANCE = 0.0000001f;
	
	/**
	 * Supports construction of ATRON modular robot's morphology on the level of components.
	 * @param simulation, the physical simulation.
	 */
	public ATRONConstructionTemplate(JMESimulation simulation) {
		super(simulation);		
	}

	/**
	 * Updates the array of objects containing the information about all available initial rotations 
	 * of  ATRON modular robot module and  rotations together with positions of newly added ATRON module with respect to specific 
	 * connector number on selected ATRON module and selected module itself.
	 * This method is so-called "Primitive operation" for TEMPLATE method, called "moveModuleAccording(int connectorNr, Module selectedModule, Module newMovableModule)".	 
	 * @param x, the amount of x coordinate of current position of ATRON component.
	 * @param y, the amount of y coordinate of current position of ATRON component.
	 * @param z, the amount of z coordinate of current position of ATRON component. 
	 */
	public void updateModuleMap(float x, float y, float z){		

		/*Apply offset of 0.08(UNIT) for each coordinate, which is a lattice distance between two ATRON modules.
		 * This is done to get the position of newly added component of the module (movable module) with respect 
		 * to selected one*/ 
		float xPlusOffset = x + OFFSET;
		float xMinusOffset = x - OFFSET;
		float yPlusOffset = y + OFFSET;
		float yMinusOffset = y - OFFSET;
		float zPlusOffset = z + OFFSET;
		float zMinusOffset = z - OFFSET;

		/*Array containing the data for adding the new movable component(module) with respect to selected module.
		 * The format of the object is: (connector number on selected module, the rotation of selected module, 
		 * the rotation of new movable module, the position of new movable module. So the logic is, if connector
		 * number is for example 0 and and selected module rotation is ATRON.ROTATION_EW, then new module should
		 * have rotation ATRON.ROTATION_DU and the position of it will be new Vector3f(xMinusOffset,yPlusOffset,z).
		 * (Look the first entry in the array beneath) */ 
		ModuleMapEntryHelper[] moduleMap = {
				/*ConnectorNr0*/
				new ModuleMapEntryHelper(CONNECTORnr0,ATRON.ROTATION_EW,ATRON.ROTATION_DU,new Vector3f(xMinusOffset,yPlusOffset,z)),
				new ModuleMapEntryHelper(CONNECTORnr0,ATRON.ROTATION_WE,ATRON.ROTATION_DU,new Vector3f(xPlusOffset,yPlusOffset,z)),
				new ModuleMapEntryHelper(CONNECTORnr0,ATRON.ROTATION_DU,ATRON.ROTATION_SN,new Vector3f(x,yMinusOffset,zMinusOffset)),
				new ModuleMapEntryHelper(CONNECTORnr0,ATRON.ROTATION_UD,ATRON.ROTATION_SN,new Vector3f(x,yPlusOffset,zMinusOffset)),
				new ModuleMapEntryHelper(CONNECTORnr0,ATRON.ROTATION_SN,ATRON.ROTATION_EW,new Vector3f(xPlusOffset,y,zPlusOffset)),
				new ModuleMapEntryHelper(CONNECTORnr0,ATRON.ROTATION_NS,ATRON.ROTATION_WE,new Vector3f(xMinusOffset,y,zMinusOffset)),
				/*ConnectorNr1*/
				new ModuleMapEntryHelper(CONNECTORnr1,ATRON.ROTATION_EW,ATRON.ROTATION_NS,new Vector3f(xMinusOffset,y,zPlusOffset)),
				new ModuleMapEntryHelper(CONNECTORnr1,ATRON.ROTATION_WE,ATRON.ROTATION_SN,new Vector3f(xPlusOffset,y,zMinusOffset)),
				new ModuleMapEntryHelper(CONNECTORnr1,ATRON.ROTATION_DU,ATRON.ROTATION_WE,new Vector3f(xMinusOffset,yMinusOffset,z)),
				new ModuleMapEntryHelper(CONNECTORnr1,ATRON.ROTATION_UD,ATRON.ROTATION_EW,new Vector3f(xPlusOffset,yPlusOffset,z)),
				new ModuleMapEntryHelper(CONNECTORnr1,ATRON.ROTATION_SN,ATRON.ROTATION_UD,new Vector3f(x,yMinusOffset,zPlusOffset)),
				new ModuleMapEntryHelper(CONNECTORnr1,ATRON.ROTATION_NS,ATRON.ROTATION_UD,new Vector3f(x,yMinusOffset,zMinusOffset)),
				/*ConnectorNr2*/
				new ModuleMapEntryHelper(CONNECTORnr2,ATRON.ROTATION_EW,ATRON.ROTATION_UD,new Vector3f(xMinusOffset,yMinusOffset,z)),
				new ModuleMapEntryHelper(CONNECTORnr2,ATRON.ROTATION_WE,ATRON.ROTATION_UD,new Vector3f(xPlusOffset,yMinusOffset,z)),
				new ModuleMapEntryHelper(CONNECTORnr2,ATRON.ROTATION_DU,ATRON.ROTATION_NS,new Vector3f(x,yMinusOffset,zPlusOffset)),
				new ModuleMapEntryHelper(CONNECTORnr2,ATRON.ROTATION_UD,ATRON.ROTATION_NS,new Vector3f(x,yPlusOffset,zPlusOffset)),
				new ModuleMapEntryHelper(CONNECTORnr2,ATRON.ROTATION_SN,ATRON.ROTATION_WE,new Vector3f(xMinusOffset,y,zPlusOffset)),
				new ModuleMapEntryHelper(CONNECTORnr2,ATRON.ROTATION_NS,ATRON.ROTATION_EW,new Vector3f(xPlusOffset,y,zMinusOffset)),		
				/*ConnectorNr3*/
				new ModuleMapEntryHelper(CONNECTORnr3,ATRON.ROTATION_EW,ATRON.ROTATION_SN,new Vector3f(xMinusOffset,y,zMinusOffset)),
				new ModuleMapEntryHelper(CONNECTORnr3,ATRON.ROTATION_WE,ATRON.ROTATION_NS,new Vector3f(xPlusOffset,y,zPlusOffset)),
				new ModuleMapEntryHelper(CONNECTORnr3,ATRON.ROTATION_DU,ATRON.ROTATION_EW,new Vector3f(xPlusOffset,yMinusOffset,z)),
				new ModuleMapEntryHelper(CONNECTORnr3,ATRON.ROTATION_UD,ATRON.ROTATION_WE,new Vector3f(xMinusOffset,yPlusOffset,z)),
				new ModuleMapEntryHelper(CONNECTORnr3,ATRON.ROTATION_SN,ATRON.ROTATION_DU,new Vector3f(x,yPlusOffset,zPlusOffset)),
				new ModuleMapEntryHelper(CONNECTORnr3,ATRON.ROTATION_NS,ATRON.ROTATION_DU,new Vector3f(x,yPlusOffset,zMinusOffset)),
				/*ConnectorNr4*/
				new ModuleMapEntryHelper(CONNECTORnr4,ATRON.ROTATION_EW,ATRON.ROTATION_UD,new Vector3f(xPlusOffset,yPlusOffset,z)),
				new ModuleMapEntryHelper(CONNECTORnr4,ATRON.ROTATION_WE,ATRON.ROTATION_UD,new Vector3f(xMinusOffset,yPlusOffset,z)),
				new ModuleMapEntryHelper(CONNECTORnr4,ATRON.ROTATION_DU,ATRON.ROTATION_NS,new Vector3f(x,yPlusOffset,zMinusOffset)),
				new ModuleMapEntryHelper(CONNECTORnr4,ATRON.ROTATION_UD,ATRON.ROTATION_NS,new Vector3f(x,yMinusOffset,zMinusOffset)),
				new ModuleMapEntryHelper(CONNECTORnr4,ATRON.ROTATION_SN,ATRON.ROTATION_WE,new Vector3f(xPlusOffset,y,zMinusOffset)),
				new ModuleMapEntryHelper(CONNECTORnr4,ATRON.ROTATION_NS,ATRON.ROTATION_EW,new Vector3f(xMinusOffset,y,zPlusOffset)),
				/*ConnectorNr5*/
				new ModuleMapEntryHelper(CONNECTORnr5,ATRON.ROTATION_EW,ATRON.ROTATION_SN,new Vector3f(xPlusOffset,y,zPlusOffset)),
				new ModuleMapEntryHelper(CONNECTORnr5,ATRON.ROTATION_WE,ATRON.ROTATION_NS,new Vector3f(xMinusOffset,y,zMinusOffset)),
				new ModuleMapEntryHelper(CONNECTORnr5,ATRON.ROTATION_DU,ATRON.ROTATION_EW,new Vector3f(xMinusOffset,yPlusOffset,z)),
				new ModuleMapEntryHelper(CONNECTORnr5,ATRON.ROTATION_UD,ATRON.ROTATION_WE,new Vector3f(xPlusOffset,yMinusOffset,z)),
				new ModuleMapEntryHelper(CONNECTORnr5,ATRON.ROTATION_SN,ATRON.ROTATION_DU,new Vector3f(x,yMinusOffset,zMinusOffset)),
				new ModuleMapEntryHelper(CONNECTORnr5,ATRON.ROTATION_NS,ATRON.ROTATION_DU,new Vector3f(x,yMinusOffset,zPlusOffset)),
				/*ConnectorNr6*/
				new ModuleMapEntryHelper(CONNECTORnr6,ATRON.ROTATION_EW,ATRON.ROTATION_DU,new Vector3f(xPlusOffset,yMinusOffset,z)),
				new ModuleMapEntryHelper(CONNECTORnr6,ATRON.ROTATION_WE,ATRON.ROTATION_DU,new Vector3f(xMinusOffset,yMinusOffset,z)),
				new ModuleMapEntryHelper(CONNECTORnr6,ATRON.ROTATION_DU,ATRON.ROTATION_SN,new Vector3f(x,yPlusOffset,zPlusOffset)),
				new ModuleMapEntryHelper(CONNECTORnr6,ATRON.ROTATION_UD,ATRON.ROTATION_SN,new Vector3f(x,yMinusOffset,zPlusOffset)),
				new ModuleMapEntryHelper(CONNECTORnr6,ATRON.ROTATION_SN,ATRON.ROTATION_EW,new Vector3f(xMinusOffset,y,zMinusOffset)),
				new ModuleMapEntryHelper(CONNECTORnr6,ATRON.ROTATION_NS,ATRON.ROTATION_WE,new Vector3f(xPlusOffset,y,zPlusOffset)),
				/*ConnectorNr7*/
				new ModuleMapEntryHelper(CONNECTORnr7,ATRON.ROTATION_EW,ATRON.ROTATION_NS,new Vector3f(xPlusOffset,y,zMinusOffset)),
				new ModuleMapEntryHelper(CONNECTORnr7,ATRON.ROTATION_WE,ATRON.ROTATION_SN,new Vector3f(xMinusOffset,y,zPlusOffset)),
				new ModuleMapEntryHelper(CONNECTORnr7,ATRON.ROTATION_DU,ATRON.ROTATION_WE,new Vector3f(xPlusOffset,yPlusOffset,z)),
				new ModuleMapEntryHelper(CONNECTORnr7,ATRON.ROTATION_UD,ATRON.ROTATION_EW,new Vector3f(xMinusOffset,yMinusOffset,z)),
				new ModuleMapEntryHelper(CONNECTORnr7,ATRON.ROTATION_SN,ATRON.ROTATION_UD,new Vector3f(x,yPlusOffset,zMinusOffset)),
				new ModuleMapEntryHelper(CONNECTORnr7,ATRON.ROTATION_NS,ATRON.ROTATION_UD,new Vector3f(x,yPlusOffset,zPlusOffset))
		};		
		this.moduleMap = moduleMap;		
	}		
	
	/**
	 * Moves newMovableModuleComponent of ATRON module according(respectively) to selected ATRON module preconditions,
	 * like connector number, initial rotation of selected module component, and so on.
	 * This method is so-called "Primitive operation" for TEMPLATE method,called "moveModuleAccording(int connectorNr, Module selectedModule, Module newMovableModule)". 
	 * @param connectorNr, the connector number on selected ATRON module.
	 * @param selectedModule,  the ATRON module selected in simulation environment.
	 * @param movableModuleComponent, the new ATRON module component to move respectively to selected one.
	 * @param rotationQuatComponent, the rotation of current component of selected  ATRON module.	 
	 */		
	public void moveComponentAccording(int connectorNr,Module selectedModule, JMEModuleComponent movableModuleComponent,Quaternion rotationQuatComponent, boolean loopFlag){
			/*Loop through and locate the object matching the description of current component(also selected module).*/
			for (int i=0; i<moduleMap.length;i++){				
				if (moduleMap[i].getConnectorNr()==connectorNr && moduleMap[i].getInitialRotation().getRotation().equals(rotationQuatComponent)){
					/*If component(module) already exists at current position, delete movableModuleComponent and newly added module.*/
					if (componentExitst(moduleMap[i].getNewPosition(), SERACH_TOLERANCE)&& loopFlag== false){						
						BuilderHelper.deleteModule(movableModuleComponent.getModel());											
					}else {/*move the component to new position with new rotation*/
						moveModuleComponent(movableModuleComponent,moduleMap[i].getNewRotation(),moduleMap[i].getNewPosition());
					}
				}
			}		 		
	}

	/**
	 * Rotates selected ATRON module component according to its initial rotation with opposite rotation.
	 * This method is so-called "Primitive operation" for TEMPLATE method, called "rotateModuleOpposite(Module selectedModule)". 	
	 * @param selectedModuleComponent,the module component selected in simulation environment.
	 * @param rotationQComponent,the rotation of selected component.	 
	 */	
	public void rotateComponentOpposite(JMEModuleComponent currentModuleComponent,Quaternion  rotationQComponent){
		/*Locate matching rotation Quaternion in moduleRotationMap (initial) and rotate with opposite rotation Quaternion
		 * from the same entry in  moduleRotationMap*/
		for (int entry=0;entry<MODULE_ROTATION_MAP.length;entry++){		
			if (rotationQComponent.equals(MODULE_ROTATION_MAP[entry].getRotation().getRotation())){
				rotateModuleComponent(currentModuleComponent,MODULE_ROTATION_MAP[entry].getRotationOppositeValue().getRotation());
			}
		}		
	}

	/**
	 * Rotates selected module component with standard rotations, passed as a string.
	 * This method is so-called "Primitive Operation" for TEMPLATE method, called "rotateModuleSpecifically(Module selectedModule,String rotationName)". 
	 * @param selectedModuleComponent,the module component selected in simulation environment.	 
	 * @param rotationName,the name of standard(specific) rotation of the module.	 
	 */	
	public void rotateComponentSpecifically(JMEModuleComponent currentModuleComponent, String rotationName){
		for (int entry=0;entry<MODULE_ROTATION_MAP.length;entry++){
			if (rotationName.equals(MODULE_ROTATION_MAP[entry].getRotationName())){
				rotateModuleComponent(currentModuleComponent,MODULE_ROTATION_MAP[entry].getRotation().getRotation());
			}
		}		
	}

	/**
	 * Additional method for implementing unique properties of modular robots  components of the modules. Like for example
	 * MTRAN has several more specific rotations, which are implemented to make the construction
	 * more flexible.
	 * This method is so-called "Primitive operation" for above TEMPLATE method, called "variateModuleProperties(Module selectedModule)". 	
	 * @param selectedModuleComponent,the module component selected in simulation environment.
	 * @param rotationQComponent,the rotation of selected component.	 
	 */
	public void variateComponentProperties(JMEModuleComponent selectedModuleComponent,Quaternion  rotationQComponent){
		/*Repeats functionality of opposite rotation, because there are several special rotations for ATRON like rotation
		 * with 90 degrees step with respect to each hemisphere, however there is no enough time
		 * for that. This support will require much more additional rotations in updateModuleMap method, as a
		 * result a lot of time*/
		rotateComponentOpposite(selectedModuleComponent,rotationQComponent);
	}
}
