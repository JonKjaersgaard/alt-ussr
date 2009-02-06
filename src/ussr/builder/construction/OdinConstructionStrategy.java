package ussr.builder.construction;

import ussr.description.geometry.RotationDescription;
import ussr.model.Module;

/**
 * The main responsibility of this class is to take care of low level construction functions for
 * construction of Odin modular robot's morphology. Here module consists of components. To be more precise
 * Odin module called OdinBall(also called CCP join) consists of one component (sphere).The module called
 * Telescoping link(also called OdinMuscle) consists of two components(two cylinders with cones on them).
 * Connectors are considered to be as parts of components. OdinBall(CCP joint) module has 12 connectors, 
 * numbered as: 0,1,2,3,4,5,6,7,8,9,10 and 11. Telescoping link module (also called OdinMuscle) and 
 * Structure link modules have two connectors, numbered as: 0 and 1. All methods are specific to 
 * to design of Odin modules.
 * @author Konstantinas
 *
 */
public class OdinConstructionStrategy extends ModularRobotConstructionStrategy {



	/**
	 * A number of different rotations of Structure link or Telescoping link(also called OdinMuscle) with respect to connector of CCP joint (also called OdinBall).
	 * Here:rotation0 is rotation of Structure link or Telescoping link (also called OdinMuscle) with respect to connector number 0 and 11 of CCP joint (also called OdinBall).
	 *      rotation1 - connector number 1 and 10
	 *      rotation2 - connector number 2 and 9 
	 *      rotation3 - connector number 3 and 8  
	 *      rotation4 - connector number 4 and 7
	 *      rotation5 - connector number 5 and 6     
	 */
	public final RotationDescription rotation0= new RotationDescription(0,0,pi/4);
	public final RotationDescription rotation1 = new RotationDescription(0,-pi/4,0);
	public final RotationDescription rotation2= new RotationDescription(0,pi/4,0);										  
	public final RotationDescription rotation3= new RotationDescription(0,0,-pi/4);										  
	public final RotationDescription rotation4 = new RotationDescription(0,pi/4,-pi/2);
	public final RotationDescription rotation5= new RotationDescription(0,-pi/4,-pi/2);

	/**
	 * Global rotation of OdinBall (also called CCP joint)
	 */
	public final RotationDescription rotation00= new RotationDescription(0,0,0);	
//	FIXME ABOVE ROTATIONS, MAYBE CAN BE MOVED TO ODIN.java or somewhere else

	/**
	 * Moves newMovableModule of Odin according to selected Odin module preconditions,like connector, rotation of selected module,  and so on.
	 * @param connectorNr, the connector number on selected Odin module
	 * @param selectedModule,  the Odin module selected in simulation environment
	 * @param newMovableModule, the new Odin module to move
	 */	
	@Override
	public void moveModuleAccording(int connectorNr, Module selectedModule,	Module newMovableModule) {
		String selectedModuleType =selectedModule.getProperty("ussr.module.type");
		if(selectedModuleType.equalsIgnoreCase("OdinBall")){			
			OdinMuscleConstructionStrategy  odinMuscleConst = new OdinMuscleConstructionStrategy();
			odinMuscleConst.moveOdinMusleAccording(connectorNr, selectedModule,newMovableModule);			
		} else if (selectedModuleType.equalsIgnoreCase("OdinMuscle")){
			OdinBallConstructionStrategy odinBallConst = new OdinBallConstructionStrategy();
			odinBallConst.moveOdinMusleAccording(connectorNr, selectedModule, newMovableModule);
		}

	}




	@Override
	public void rotateOpposite(Module selectedModule) {
		// TODO Auto-generated method stub

	}

	@Override
	public void rotateSpecifically(Module selectedModule, String rotationName) {
		// TODO Auto-generated method stub

	}


}
