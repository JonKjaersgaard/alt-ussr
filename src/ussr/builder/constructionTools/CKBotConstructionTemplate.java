package ussr.builder.constructionTools;

import ussr.builder.helpers.BuilderHelper;
import ussr.builder.helpers.ModuleMapEntryHelper;
import ussr.builder.helpers.ModuleRotationMapEntryHelper;
import ussr.model.Module;
import ussr.physics.jme.JMEModuleComponent;
import ussr.physics.jme.JMESimulation;
import ussr.samples.ckbot.CKBotStandard;


import com.jme.math.Quaternion;
import com.jme.math.Vector3f;

/**
 * Supports construction of CKBot modular robot morphology in more abstract module oriented way.
 * In general the main responsibility of this class is to create CKBot modules and add default CKBot
 * construction module. 
 * @author Konstantinas
 */
public class CKBotConstructionTemplate extends ModularRobotConstructionTemplate {

	/**
	 * The numbers of connectors on the CKBot module
	 */
	private final static int CONNECTORnr0 = 0,CONNECTORnr1 = 1,CONNECTORnr2 = 2,CONNECTORnr3 = 3;

	/**
	 * The physical lattice distance between two CKBot modules
	 */
	private final static float OFFSET = CKBotStandard.UNIT+0.0001f;// The increase on 0.0001 in needed to avoid stack overflow. The exception usually looks something like that:"java.lang.StackOverflowError: The ODE step function could not allocate enough stack memory to compute the time step!
	                                                                                                                                                                            //This is usually caused by low stack size or by too many applied contacts respectively (number of contacts in this step was 743).
			
	/**
	 * Tolerance used to identify if component (module) already exists in interval of space.
	 */
	private final static float SERACH_TOLERANCE = 0.06f;

	/**
	 * The array of objects containing information about CKBot specific rotations.
	 * The logic is: if rotation is "ROT_0", then the rotation value is CKBotStandard.ROTATION_0 and opposite
	 * to this rotation is CKBotStandard.ROT_0_OPPOS (Look the first entry in array beneath).
	 */
	private final static ModuleRotationMapEntryHelper[] MODULE_ROTATION_MAP =  {
		new ModuleRotationMapEntryHelper("ROT_0",CKBotStandard.ROT_0,CKBotStandard.ROT_0_OPPOS,CKBotStandard.ROT_0_90Z),
		new ModuleRotationMapEntryHelper("ROT_0_OPPOS",CKBotStandard.ROT_0_OPPOS,CKBotStandard.ROT_0,CKBotStandard.ROT_0_OPPOS_90Z),
		new ModuleRotationMapEntryHelper("ROT_0_90Z",CKBotStandard.ROT_0_90Z,CKBotStandard.ROT_0_OPPOS_90Z,CKBotStandard.ROT_0),
		new ModuleRotationMapEntryHelper("ROT_0_OPPOS_90Z",CKBotStandard.ROT_0_OPPOS_90Z,CKBotStandard.ROT_0_90Z,CKBotStandard.ROT_0_OPPOS),
		new ModuleRotationMapEntryHelper("ROT_0_90X",CKBotStandard.ROT_0_90X,CKBotStandard.ROT_0_MIN90X,CKBotStandard.ROT_0_90X_90Y),
		new ModuleRotationMapEntryHelper("ROT_0_MIN90X",CKBotStandard.ROT_0_MIN90X,CKBotStandard.ROT_0_90X,CKBotStandard.ROT_0_270X_90Y),
		new ModuleRotationMapEntryHelper("ROT_0_90X_90Y",CKBotStandard.ROT_0_90X_90Y,CKBotStandard.ROT_0_270X_90Y,CKBotStandard.ROT_0_90X),
		new ModuleRotationMapEntryHelper("ROT_0_270X_90Y",CKBotStandard.ROT_0_270X_90Y,CKBotStandard.ROT_0_90X_90Y,CKBotStandard.ROT_0_MIN90X),
		new ModuleRotationMapEntryHelper("ROT_0_90Y",CKBotStandard.ROT_0_90Y,CKBotStandard.ROT_0_MIN90Y,CKBotStandard.ROT_0_MIN90X_MINUS90Z),
		new ModuleRotationMapEntryHelper("ROT_0_MIN90Y",CKBotStandard.ROT_0_MIN90Y,CKBotStandard.ROT_0_90Y,CKBotStandard.ROT_0_90X_MIN90Z),
		new ModuleRotationMapEntryHelper("ROT_0_90X_MIN90Z",CKBotStandard.ROT_0_90X_MIN90Z,CKBotStandard.ROT_0_MIN90X_MINUS90Z,CKBotStandard.ROT_0_MIN90Y),
		new ModuleRotationMapEntryHelper("ROT_0_MIN90X_MIN90Z",CKBotStandard.ROT_0_MIN90X_MINUS90Z,CKBotStandard.ROT_0_90X_MIN90Z,CKBotStandard.ROT_0_90Y)
	};


	/**
	 * Supports construction of CKBot modular robot's morphology on the level of components.
	 * @param simulation, the physical simulation.
	 */
	public CKBotConstructionTemplate(JMESimulation simulation) {
		super(simulation);		
	}

	/**
	 * Moves newMovableModuleComponent of CKBot module according(respectively) to selected CKBot module preconditions,
	 * like connector number, initial rotation of selected module component, and so on.
	 * This method is so-called "Primitive operation" for TEMPLATE method,called "moveModuleAccording(int connectorNr, Module selectedModule, Module newMovableModule)". 
	 * @param connectorNr, the connector number on selected CKBot module.
	 * @param selectedModule,  the CKBot module selected in simulation environment.
	 * @param movableModuleComponent, the new CKBot module component to move respectively to selected one.
	 * @param rotationQuatComponent, the rotation of current component of selected  CKBot module.	 
	 */		
	@Override
	public void moveComponentAccording(int connectorNr, Module selectedModule,JMEModuleComponent movableModuleComponent,Quaternion rotationQuatComponent, boolean loopFlag) {
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
	 * Rotates selected CKBot module component according to its initial rotation with opposite rotation.
	 * This method is so-called "Primitive operation" for TEMPLATE method, called "rotateModuleOpposite(Module selectedModule)". 	
	 * @param selectedModuleComponent,the module component selected in simulation environment.
	 * @param rotationQComponent,the rotation of selected component.	 
	 */	
	@Override
	public void rotateComponentOpposite(JMEModuleComponent currentModuleComponent,Quaternion rotationQComponent) {
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
	@Override
	public void rotateComponentSpecifically(JMEModuleComponent currentModuleComponent, String rotationName) {
		for (int entry=0;entry<MODULE_ROTATION_MAP.length;entry++){
			if (rotationName.equals(MODULE_ROTATION_MAP[entry].getRotationName())){
				rotateModuleComponent(currentModuleComponent,MODULE_ROTATION_MAP[entry].getRotation().getRotation());
			}
		}	
	}

	/**
	 * Updates the array of objects containing the information about all available initial rotations 
	 * of  CKBot modular robot module and  rotations together with positions of newly added CKBot module with respect to specific 
	 * connector number on selected CKBot module and selected module itself.
	 * This method is so-called "Primitive operation" for TEMPLATE method, called "moveModuleAccording(int connectorNr, Module selectedModule, Module newMovableModule)".	 
	 * @param x, the amount of x coordinate of current position of CKBot component.
	 * @param y, the amount of y coordinate of current position of CKBot component.
	 * @param z, the amount of z coordinate of current position of CKBot component. 
	 */
	@Override
	public void updateModuleMap(float x, float y, float z) {
		/*Apply offset of 0.06(UNIT) for each coordinate, which is a lattice distance between two CKBot modules.
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
		 * number is for example 0 and and selected module rotation is CKBotStandard.ROTATION_0, then new module should
		 * have rotation CKBotStandard.ROTATION_0 and the position of it will be new Vector3f(x,y,zPlusOffset).
		 * (Look the first entry in the array beneath) */ 
		ModuleMapEntryHelper[] moduleMap = {
				/*ConnectorNr0*/
				new ModuleMapEntryHelper(CONNECTORnr0,CKBotStandard.ROT_0,CKBotStandard.ROT_0,new Vector3f(x,y,zPlusOffset)),
				new ModuleMapEntryHelper(CONNECTORnr0,CKBotStandard.ROT_0_OPPOS,CKBotStandard.ROT_0_OPPOS,new Vector3f(x,y,zMinusOffset)),
				new ModuleMapEntryHelper(CONNECTORnr0,CKBotStandard.ROT_0_90Z,CKBotStandard.ROT_0_90Z,new Vector3f(x,y,zPlusOffset)),
				new ModuleMapEntryHelper(CONNECTORnr0,CKBotStandard.ROT_0_OPPOS_90Z,CKBotStandard.ROT_0_OPPOS_90Z,new Vector3f(x,y,zMinusOffset)),

				new ModuleMapEntryHelper(CONNECTORnr0,CKBotStandard.ROT_0_90X,CKBotStandard.ROT_0_90X,new Vector3f(x,yPlusOffset,z)),
				new ModuleMapEntryHelper(CONNECTORnr0,CKBotStandard.ROT_0_MIN90X,CKBotStandard.ROT_0_MIN90X,new Vector3f(x,yPlusOffset,z)),
				new ModuleMapEntryHelper(CONNECTORnr0,CKBotStandard.ROT_0_90X_90Y,CKBotStandard.ROT_0_90X_90Y,new Vector3f(x,yMinusOffset,z)),
				new ModuleMapEntryHelper(CONNECTORnr0,CKBotStandard.ROT_0_270X_90Y,CKBotStandard.ROT_0_270X_90Y,new Vector3f(x,yPlusOffset,z)),

				new ModuleMapEntryHelper(CONNECTORnr0,CKBotStandard.ROT_0_90Y,CKBotStandard.ROT_0_90Y,new Vector3f(xPlusOffset,y,z)),
				new ModuleMapEntryHelper(CONNECTORnr0,CKBotStandard.ROT_0_MIN90Y,CKBotStandard.ROT_0_MIN90Y,new Vector3f(xMinusOffset,y,z)),
				new ModuleMapEntryHelper(CONNECTORnr0,CKBotStandard.ROT_0_90X_MIN90Z,CKBotStandard.ROT_0_90X_MIN90Z,new Vector3f(xMinusOffset,y,z)),
				new ModuleMapEntryHelper(CONNECTORnr0,CKBotStandard.ROT_0_MIN90X_MINUS90Z,CKBotStandard.ROT_0_MIN90X_MINUS90Z,new Vector3f(xPlusOffset,y,z)),

				/*ConnectorNr1*/
				new ModuleMapEntryHelper(CONNECTORnr1,CKBotStandard.ROT_0,CKBotStandard.ROT_0_MIN90X,new Vector3f(x,yPlusOffset,z)),
				new ModuleMapEntryHelper(CONNECTORnr1,CKBotStandard.ROT_0_OPPOS,CKBotStandard.ROT_0_90X,new Vector3f(x,yMinusOffset,z)),
				new ModuleMapEntryHelper(CONNECTORnr1,CKBotStandard.ROT_0_90Z,CKBotStandard.ROT_0_90X_MIN90Z,new Vector3f(xMinusOffset,y,z)),
				new ModuleMapEntryHelper(CONNECTORnr1,CKBotStandard.ROT_0_OPPOS_90Z,CKBotStandard.ROT_0_MIN90X_MINUS90Z,new Vector3f(xPlusOffset,y,z)),

				new ModuleMapEntryHelper(CONNECTORnr1,CKBotStandard.ROT_0_90X,CKBotStandard.ROT_0,new Vector3f(x,y,zPlusOffset)),
				new ModuleMapEntryHelper(CONNECTORnr1,CKBotStandard.ROT_0_MIN90X,CKBotStandard.ROT_0_OPPOS,new Vector3f(x,y,zMinusOffset)),
				new ModuleMapEntryHelper(CONNECTORnr1,CKBotStandard.ROT_0_90X_90Y,CKBotStandard.ROT_0_MIN90X_MINUS90Z,new Vector3f(xPlusOffset,y,z)),
				new ModuleMapEntryHelper(CONNECTORnr1,CKBotStandard.ROT_0_270X_90Y,CKBotStandard.ROT_0_MIN90Y,new Vector3f(xMinusOffset,y,z)),

				new ModuleMapEntryHelper(CONNECTORnr1,CKBotStandard.ROT_0_90Y,CKBotStandard.ROT_0_270X_90Y,new Vector3f(x,yPlusOffset,z)),
				new ModuleMapEntryHelper(CONNECTORnr1,CKBotStandard.ROT_0_MIN90Y,CKBotStandard.ROT_0_270X_90Y,new Vector3f(x,yPlusOffset,z)),
				new ModuleMapEntryHelper(CONNECTORnr1,CKBotStandard.ROT_0_90X_MIN90Z,CKBotStandard.ROT_0_90Z,new Vector3f(x,y,zPlusOffset)),
				new ModuleMapEntryHelper(CONNECTORnr1,CKBotStandard.ROT_0_MIN90X_MINUS90Z,CKBotStandard.ROT_0_OPPOS_90Z,new Vector3f(x,y,zMinusOffset)),

				/*ConnectorNr2*/
				new ModuleMapEntryHelper(CONNECTORnr2,CKBotStandard.ROT_0,CKBotStandard.ROT_0_90X,new Vector3f(x,yMinusOffset,z)),
				new ModuleMapEntryHelper(CONNECTORnr2,CKBotStandard.ROT_0_OPPOS,CKBotStandard.ROT_0_MIN90X,new Vector3f(x,yPlusOffset,z)),
				new ModuleMapEntryHelper(CONNECTORnr2,CKBotStandard.ROT_0_90Z,CKBotStandard.ROT_0_MIN90X_MINUS90Z,new Vector3f(xPlusOffset,y,z)),
				new ModuleMapEntryHelper(CONNECTORnr2,CKBotStandard.ROT_0_OPPOS_90Z,CKBotStandard.ROT_0_90X_MIN90Z,new Vector3f(xMinusOffset,y,z)),

				new ModuleMapEntryHelper(CONNECTORnr2,CKBotStandard.ROT_0_90X,CKBotStandard.ROT_0_OPPOS,new Vector3f(x,y,zMinusOffset)),
				new ModuleMapEntryHelper(CONNECTORnr2,CKBotStandard.ROT_0_MIN90X,CKBotStandard.ROT_0,new Vector3f(x,y,zPlusOffset)),
				new ModuleMapEntryHelper(CONNECTORnr2,CKBotStandard.ROT_0_90X_90Y,CKBotStandard.ROT_0_90X_MIN90Z,new Vector3f(xMinusOffset,y,z)),
				new ModuleMapEntryHelper(CONNECTORnr2,CKBotStandard.ROT_0_270X_90Y,CKBotStandard.ROT_0_90Y,new Vector3f(xPlusOffset,y,z)),

				new ModuleMapEntryHelper(CONNECTORnr2,CKBotStandard.ROT_0_90Y,CKBotStandard.ROT_0_90X_90Y,new Vector3f(x,yMinusOffset,z)),
				new ModuleMapEntryHelper(CONNECTORnr2,CKBotStandard.ROT_0_MIN90Y,CKBotStandard.ROT_0_90X_90Y,new Vector3f(x,yMinusOffset,z)),
				new ModuleMapEntryHelper(CONNECTORnr2,CKBotStandard.ROT_0_90X_MIN90Z,CKBotStandard.ROT_0_OPPOS_90Z,new Vector3f(x,y,zMinusOffset)),
				new ModuleMapEntryHelper(CONNECTORnr2,CKBotStandard.ROT_0_MIN90X_MINUS90Z,CKBotStandard.ROT_0_90Z,new Vector3f(x,y,zPlusOffset)),

				/*ConnectorNr3*/
				new ModuleMapEntryHelper(CONNECTORnr3,CKBotStandard.ROT_0,CKBotStandard.ROT_0,new Vector3f(x,y,zMinusOffset)),
				new ModuleMapEntryHelper(CONNECTORnr3,CKBotStandard.ROT_0_OPPOS,CKBotStandard.ROT_0_OPPOS,new Vector3f(x,y,zPlusOffset)),
				new ModuleMapEntryHelper(CONNECTORnr3,CKBotStandard.ROT_0_90Z,CKBotStandard.ROT_0_90Z,new Vector3f(x,y,zMinusOffset)),
				new ModuleMapEntryHelper(CONNECTORnr3,CKBotStandard.ROT_0_OPPOS_90Z,CKBotStandard.ROT_0_OPPOS_90Z,new Vector3f(x,y,zPlusOffset)),

				new ModuleMapEntryHelper(CONNECTORnr3,CKBotStandard.ROT_0_90X,CKBotStandard.ROT_0_90X,new Vector3f(x,yPlusOffset,z)),
				new ModuleMapEntryHelper(CONNECTORnr3,CKBotStandard.ROT_0_MIN90X,CKBotStandard.ROT_0_MIN90X,new Vector3f(x,yMinusOffset,z)),
				new ModuleMapEntryHelper(CONNECTORnr3,CKBotStandard.ROT_0_90X_90Y,CKBotStandard.ROT_0_90X_90Y,new Vector3f(x,yPlusOffset,z)),
				new ModuleMapEntryHelper(CONNECTORnr3,CKBotStandard.ROT_0_270X_90Y,CKBotStandard.ROT_0_270X_90Y,new Vector3f(x,yMinusOffset,z)),

				new ModuleMapEntryHelper(CONNECTORnr3,CKBotStandard.ROT_0_90Y,CKBotStandard.ROT_0_90Y,new Vector3f(xMinusOffset,y,z)),
				new ModuleMapEntryHelper(CONNECTORnr3,CKBotStandard.ROT_0_MIN90Y,CKBotStandard.ROT_0_MIN90Y,new Vector3f(xPlusOffset,y,z)),
				new ModuleMapEntryHelper(CONNECTORnr3,CKBotStandard.ROT_0_90X_MIN90Z,CKBotStandard.ROT_0_90X_MIN90Z,new Vector3f(xPlusOffset,y,z)),
				new ModuleMapEntryHelper(CONNECTORnr3,CKBotStandard.ROT_0_MIN90X_MINUS90Z,CKBotStandard.ROT_0_MIN90X_MINUS90Z,new Vector3f(xMinusOffset,y,z))

		};		
		this.moduleMap = moduleMap;	
	}

	/**
	 * Additional method for implementing unique properties of modular robots  components of the modules. 
	 * Here rotates the module 90 degrees around its axis (the axis is going along the module).
	 * This method is so-called "Primitive operation" for above TEMPLATE method, called "variateModuleProperties(Module selectedModule)". 	
	 * @param selectedModuleComponent,the module component selected in simulation environment.
	 * @param rotationQComponent,the rotation of selected component.	 
	 */
	@Override
	public void variateComponentProperties(JMEModuleComponent selectedModuleComponent,Quaternion rotationQComponent) {
		for (int entry=0;entry<MODULE_ROTATION_MAP.length;entry++){
			if (rotationQComponent.equals(MODULE_ROTATION_MAP[entry].getRotation().getRotation())){
				rotateModuleComponent(selectedModuleComponent,MODULE_ROTATION_MAP[entry].getRotationAroundAxisValue().getRotation());
			}
		}
	}
	}
