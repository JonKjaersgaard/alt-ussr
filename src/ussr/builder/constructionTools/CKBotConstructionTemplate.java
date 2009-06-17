package ussr.builder.constructionTools;

import ussr.model.Module;
import ussr.physics.jme.JMEModuleComponent;
import ussr.physics.jme.JMESimulation;

import ckbot.CKBotStandard;

import com.jme.math.Quaternion;
import com.jme.math.Vector3f;

public class CKBotConstructionTemplate extends ModularRobotConstructionTemplate {

	/**
	 * The numbers of connectors on the CKBot module
	 */
	private final static int CONNECTORnr0 = 0,CONNECTORnr1 = 1,CONNECTORnr2 = 2,CONNECTORnr3 = 3;


	/**
	 * The physical lattice distance between two CKBot modules
	 */
	private final static float OFFSET = CKBotStandard.UNIT;

	/**
	 * The array of objects containing information about CKBot specific rotations.
	 * The logic is: if rotation is "ROTATION_0", then the rotation value is CKBotStandard.ROTATION_0 and opposite
	 * to this rotation is CKBotStandard.ROTATION_0_OPPOSITE (Look the first entry in array beneath).
	 */
	private final static ModuleRotationMapEntryHelper[] MODULE_ROTATION_MAP =  {
		new ModuleRotationMapEntryHelper("ROTATION_0",CKBotStandard.ROTATION_0,CKBotStandard.ROTATION_0_OPPOSITE),
		new ModuleRotationMapEntryHelper("ROTATION_0_OPPOSITE",CKBotStandard.ROTATION_0_OPPOSITE,CKBotStandard.ROTATION_0),
		new ModuleRotationMapEntryHelper("ROTATION_0_90Z",CKBotStandard.ROTATION_0_90Z,CKBotStandard.ROTATION_0_OPPOSITE_90Z),
		new ModuleRotationMapEntryHelper("ROTATION_0_OPPOSITE_90Z",CKBotStandard.ROTATION_0_OPPOSITE_90Z,CKBotStandard.ROTATION_0_90Z),
		new ModuleRotationMapEntryHelper("ROTATION_0_90X",CKBotStandard.ROTATION_0_90X,CKBotStandard.ROTATION_0_MINUS90X),
		new ModuleRotationMapEntryHelper("ROTATION_0_MINUS90X",CKBotStandard.ROTATION_0_MINUS90X,CKBotStandard.ROTATION_0_90X),
		new ModuleRotationMapEntryHelper("ROTATION_0_90X_90Y",CKBotStandard.ROTATION_0_90X_90Y,CKBotStandard.ROTATION_0_270X_90Y),
		new ModuleRotationMapEntryHelper("ROTATION_0_270X_90Y",CKBotStandard.ROTATION_0_270X_90Y,CKBotStandard.ROTATION_0_90X_90Y),
		new ModuleRotationMapEntryHelper("ROTATION_0_90Y",CKBotStandard.ROTATION_0_90Y,CKBotStandard.ROTATION_0_MINUS90Y),
		new ModuleRotationMapEntryHelper("ROTATION_0_MINUS90Y",CKBotStandard.ROTATION_0_MINUS90Y,CKBotStandard.ROTATION_0_90Y),
		new ModuleRotationMapEntryHelper("ROTATION_0_90X_MINUS90Z",CKBotStandard.ROTATION_0_90X_MINUS90Z,CKBotStandard.ROTATION_0_MINUS90X_MINUS90Z),
		new ModuleRotationMapEntryHelper("ROTATION_0_MINUS90X_MINUS90Z",CKBotStandard.ROTATION_0_MINUS90X_MINUS90Z,CKBotStandard.ROTATION_0_90X_MINUS90Z)
	};


	/**
	 * Supports construction of CKBot modular robot's morphology on the level of components.
	 * @param simulation, the physical simulation.
	 */
	public CKBotConstructionTemplate(JMESimulation simulation) {
		super(simulation);		
	}

	@Override
	public void moveComponentAccording(int connectorNr, Module selectedModule,JMEModuleComponent movableModuleComponent,Quaternion rotationQuatComponent, boolean loopFlag) {
		/*Loop through and locate the object matching the description of current component(also selected module).*/
		for (int i=0; i<moduleMap.length;i++){				
			if (moduleMap[i].getConnectorNr()==connectorNr && moduleMap[i].getInitialRotation().getRotation().equals(rotationQuatComponent)){
				/*If component(module) already exists at current position, delete movableModuleComponent and newly added module.*/
				//if (componentExitst(moduleMap[i].getNewPosition(), SERACH_TOLERANCE)&& loopFlag== false){						
				//BuilderHelper.deleteModule(movableModuleComponent.getModel());											
				//}else {/*move the component to new position with new rotation*/
				moveModuleComponent(movableModuleComponent,moduleMap[i].getNewRotation(),moduleMap[i].getNewPosition());
				//}
			}
		}		 

	}

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

	@Override
	public void rotateComponentSpecifically(
			JMEModuleComponent selectedModuleComponent, String rotationName) {
		// TODO Auto-generated method stub

	}

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
				new ModuleMapEntryHelper(CONNECTORnr0,CKBotStandard.ROTATION_0,CKBotStandard.ROTATION_0,new Vector3f(x,y,zPlusOffset)),
				new ModuleMapEntryHelper(CONNECTORnr0,CKBotStandard.ROTATION_0_OPPOSITE,CKBotStandard.ROTATION_0_OPPOSITE,new Vector3f(x,y,zMinusOffset)),
				new ModuleMapEntryHelper(CONNECTORnr0,CKBotStandard.ROTATION_0_90Z,CKBotStandard.ROTATION_0_90Z,new Vector3f(x,y,zPlusOffset)),
				new ModuleMapEntryHelper(CONNECTORnr0,CKBotStandard.ROTATION_0_OPPOSITE_90Z,CKBotStandard.ROTATION_0_OPPOSITE_90Z,new Vector3f(x,y,zMinusOffset)),

				new ModuleMapEntryHelper(CONNECTORnr0,CKBotStandard.ROTATION_0_90X,CKBotStandard.ROTATION_0_90X,new Vector3f(x,yPlusOffset,z)),
				new ModuleMapEntryHelper(CONNECTORnr0,CKBotStandard.ROTATION_0_MINUS90X,CKBotStandard.ROTATION_0_MINUS90X,new Vector3f(x,yPlusOffset,z)),
				new ModuleMapEntryHelper(CONNECTORnr0,CKBotStandard.ROTATION_0_90X_90Y,CKBotStandard.ROTATION_0_90X_90Y,new Vector3f(x,yMinusOffset,z)),
				new ModuleMapEntryHelper(CONNECTORnr0,CKBotStandard.ROTATION_0_270X_90Y,CKBotStandard.ROTATION_0_270X_90Y,new Vector3f(x,yPlusOffset,z)),

				new ModuleMapEntryHelper(CONNECTORnr0,CKBotStandard.ROTATION_0_90Y,CKBotStandard.ROTATION_0_90Y,new Vector3f(xPlusOffset,y,z)),
				new ModuleMapEntryHelper(CONNECTORnr0,CKBotStandard.ROTATION_0_MINUS90Y,CKBotStandard.ROTATION_0_MINUS90Y,new Vector3f(xMinusOffset,y,z)),
				new ModuleMapEntryHelper(CONNECTORnr0,CKBotStandard.ROTATION_0_90X_MINUS90Z,CKBotStandard.ROTATION_0_90X_MINUS90Z,new Vector3f(xMinusOffset,y,z)),
				new ModuleMapEntryHelper(CONNECTORnr0,CKBotStandard.ROTATION_0_MINUS90X_MINUS90Z,CKBotStandard.ROTATION_0_MINUS90X_MINUS90Z,new Vector3f(xPlusOffset,y,z)),

				/*ConnectorNr1*/
				new ModuleMapEntryHelper(CONNECTORnr1,CKBotStandard.ROTATION_0,CKBotStandard.ROTATION_0_MINUS90X,new Vector3f(x,yPlusOffset,z)),
				new ModuleMapEntryHelper(CONNECTORnr1,CKBotStandard.ROTATION_0_OPPOSITE,CKBotStandard.ROTATION_0_90X,new Vector3f(x,yMinusOffset,z)),
				new ModuleMapEntryHelper(CONNECTORnr1,CKBotStandard.ROTATION_0_90Z,CKBotStandard.ROTATION_0_90X_MINUS90Z,new Vector3f(xMinusOffset,y,z)),
				new ModuleMapEntryHelper(CONNECTORnr1,CKBotStandard.ROTATION_0_OPPOSITE_90Z,CKBotStandard.ROTATION_0_MINUS90X_MINUS90Z,new Vector3f(xPlusOffset,y,z)),

				new ModuleMapEntryHelper(CONNECTORnr1,CKBotStandard.ROTATION_0_90X,CKBotStandard.ROTATION_0,new Vector3f(x,y,zPlusOffset)),
				new ModuleMapEntryHelper(CONNECTORnr1,CKBotStandard.ROTATION_0_MINUS90X,CKBotStandard.ROTATION_0_OPPOSITE,new Vector3f(x,y,zMinusOffset)),
				new ModuleMapEntryHelper(CONNECTORnr1,CKBotStandard.ROTATION_0_90X_90Y,CKBotStandard.ROTATION_0_MINUS90X_MINUS90Z,new Vector3f(xPlusOffset,y,z)),
				new ModuleMapEntryHelper(CONNECTORnr1,CKBotStandard.ROTATION_0_270X_90Y,CKBotStandard.ROTATION_0_MINUS90Y,new Vector3f(xMinusOffset,y,z)),

				new ModuleMapEntryHelper(CONNECTORnr1,CKBotStandard.ROTATION_0_90Y,CKBotStandard.ROTATION_0_270X_90Y,new Vector3f(x,yPlusOffset,z)),
				new ModuleMapEntryHelper(CONNECTORnr1,CKBotStandard.ROTATION_0_MINUS90Y,CKBotStandard.ROTATION_0_270X_90Y,new Vector3f(x,yPlusOffset,z)),
				new ModuleMapEntryHelper(CONNECTORnr1,CKBotStandard.ROTATION_0_90X_MINUS90Z,CKBotStandard.ROTATION_0_90Z,new Vector3f(x,y,zPlusOffset)),
				new ModuleMapEntryHelper(CONNECTORnr1,CKBotStandard.ROTATION_0_MINUS90X_MINUS90Z,CKBotStandard.ROTATION_0_OPPOSITE_90Z,new Vector3f(x,y,zMinusOffset)),

				/*ConnectorNr2*/
				new ModuleMapEntryHelper(CONNECTORnr2,CKBotStandard.ROTATION_0,CKBotStandard.ROTATION_0_90X,new Vector3f(x,yMinusOffset,z)),
				new ModuleMapEntryHelper(CONNECTORnr2,CKBotStandard.ROTATION_0_OPPOSITE,CKBotStandard.ROTATION_0_MINUS90X,new Vector3f(x,yPlusOffset,z)),
				new ModuleMapEntryHelper(CONNECTORnr2,CKBotStandard.ROTATION_0_90Z,CKBotStandard.ROTATION_0_MINUS90X_MINUS90Z,new Vector3f(xPlusOffset,y,z)),
				new ModuleMapEntryHelper(CONNECTORnr2,CKBotStandard.ROTATION_0_OPPOSITE_90Z,CKBotStandard.ROTATION_0_90X_MINUS90Z,new Vector3f(xMinusOffset,y,z)),

				new ModuleMapEntryHelper(CONNECTORnr2,CKBotStandard.ROTATION_0_90X,CKBotStandard.ROTATION_0_OPPOSITE,new Vector3f(x,y,zMinusOffset)),
				new ModuleMapEntryHelper(CONNECTORnr2,CKBotStandard.ROTATION_0_MINUS90X,CKBotStandard.ROTATION_0,new Vector3f(x,y,zPlusOffset)),
				new ModuleMapEntryHelper(CONNECTORnr2,CKBotStandard.ROTATION_0_90X_90Y,CKBotStandard.ROTATION_0_90X_MINUS90Z,new Vector3f(xMinusOffset,y,z)),
				new ModuleMapEntryHelper(CONNECTORnr2,CKBotStandard.ROTATION_0_270X_90Y,CKBotStandard.ROTATION_0_90Y,new Vector3f(xPlusOffset,y,z)),

				new ModuleMapEntryHelper(CONNECTORnr2,CKBotStandard.ROTATION_0_90Y,CKBotStandard.ROTATION_0_90X_90Y,new Vector3f(x,yMinusOffset,z)),
				new ModuleMapEntryHelper(CONNECTORnr2,CKBotStandard.ROTATION_0_MINUS90Y,CKBotStandard.ROTATION_0_90X_90Y,new Vector3f(x,yMinusOffset,z)),
				new ModuleMapEntryHelper(CONNECTORnr2,CKBotStandard.ROTATION_0_90X_MINUS90Z,CKBotStandard.ROTATION_0_OPPOSITE_90Z,new Vector3f(x,y,zMinusOffset)),
				new ModuleMapEntryHelper(CONNECTORnr2,CKBotStandard.ROTATION_0_MINUS90X_MINUS90Z,CKBotStandard.ROTATION_0_90Z,new Vector3f(x,y,zPlusOffset)),

				/*ConnectorNr3*/
				new ModuleMapEntryHelper(CONNECTORnr3,CKBotStandard.ROTATION_0,CKBotStandard.ROTATION_0,new Vector3f(x,y,zMinusOffset)),
				new ModuleMapEntryHelper(CONNECTORnr3,CKBotStandard.ROTATION_0_OPPOSITE,CKBotStandard.ROTATION_0_OPPOSITE,new Vector3f(x,y,zPlusOffset)),
				new ModuleMapEntryHelper(CONNECTORnr3,CKBotStandard.ROTATION_0_90Z,CKBotStandard.ROTATION_0_90Z,new Vector3f(x,y,zMinusOffset)),
				new ModuleMapEntryHelper(CONNECTORnr3,CKBotStandard.ROTATION_0_OPPOSITE_90Z,CKBotStandard.ROTATION_0_OPPOSITE_90Z,new Vector3f(x,y,zPlusOffset)),

				new ModuleMapEntryHelper(CONNECTORnr3,CKBotStandard.ROTATION_0_90X,CKBotStandard.ROTATION_0_90X,new Vector3f(x,yPlusOffset,z)),
				new ModuleMapEntryHelper(CONNECTORnr3,CKBotStandard.ROTATION_0_MINUS90X,CKBotStandard.ROTATION_0_MINUS90X,new Vector3f(x,yMinusOffset,z)),
				new ModuleMapEntryHelper(CONNECTORnr3,CKBotStandard.ROTATION_0_90X_90Y,CKBotStandard.ROTATION_0_90X_90Y,new Vector3f(x,yPlusOffset,z)),
				new ModuleMapEntryHelper(CONNECTORnr3,CKBotStandard.ROTATION_0_270X_90Y,CKBotStandard.ROTATION_0_270X_90Y,new Vector3f(x,yMinusOffset,z)),

				new ModuleMapEntryHelper(CONNECTORnr3,CKBotStandard.ROTATION_0_90Y,CKBotStandard.ROTATION_0_90Y,new Vector3f(xMinusOffset,y,z)),
				new ModuleMapEntryHelper(CONNECTORnr3,CKBotStandard.ROTATION_0_MINUS90Y,CKBotStandard.ROTATION_0_MINUS90Y,new Vector3f(xPlusOffset,y,z)),
				new ModuleMapEntryHelper(CONNECTORnr3,CKBotStandard.ROTATION_0_90X_MINUS90Z,CKBotStandard.ROTATION_0_90X_MINUS90Z,new Vector3f(xPlusOffset,y,z)),
				new ModuleMapEntryHelper(CONNECTORnr3,CKBotStandard.ROTATION_0_MINUS90X_MINUS90Z,CKBotStandard.ROTATION_0_MINUS90X_MINUS90Z,new Vector3f(xMinusOffset,y,z))

		};		
		this.moduleMap = moduleMap;	
	}

	@Override
	public void variateComponentProperties(
			JMEModuleComponent selectedModuleComponent,
			Quaternion rotationQComponent) {
		// TODO Auto-generated method stub

	}

}
