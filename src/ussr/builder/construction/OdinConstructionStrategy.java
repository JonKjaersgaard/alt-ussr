package ussr.builder.construction;

import com.jme.math.Quaternion;
import ussr.description.geometry.RotationDescription;
import ussr.description.geometry.VectorDescription;
import ussr.model.Connector;
import ussr.model.Module;
import ussr.physics.jme.JMEModuleComponent;

/**
 * The main responsibility of this class  is to support children classes with common methods
 *  and  variables for construction of  morphology of Odin modular robot. Moreover define methods 
 *  specific for Odin modular robot. 
 * @author Konstantinas 
 */
//FIXME 1) UPDATE COMMENTS
//FIXME 2) FIX EXISTING IMPROVEMENTS
public class OdinConstructionStrategy extends ModularRobotConstructionStrategy {

	/**
	 * A number of different rotations of Structure link, Telescoping link(also called OdinMuscle),OdinBattery,
	 * OdinHinge,OdinSpring,OdinTube and OdinWhell with respect to connector of CCP joint (also called OdinBall).
	 * Here:rotation0 is rotation of Structure link or Telescoping link (also called OdinMuscle) with respect to connector number 0 of CCP joint (also called OdinBall).
	 *      rotation1 - connector number 1 
	 *      rotation2 - connector number 2 
	 *      rotation3 - connector number 3 
	 *      rotation4 - connector number 4 
	 *      rotation5 - connector number 5 
	 *      rotation6 - connector number 6 
	 *      rotation7 - connector number 7 
	 *      rotation8 - connector number 8
	 *      rotation9 - connector number 9
	 *      rotation10 - connector number 10
	 *      rotation11 - connector number 11
	 */
	public final static RotationDescription rotation0 = new RotationDescription(0,0,pi/4);
	public final static RotationDescription rotation1 = new RotationDescription(0,-pi/4,0);
	public final static RotationDescription rotation2 = new RotationDescription(0,pi/4,0);										  
	public final static RotationDescription rotation3 = new RotationDescription(0,0,-pi/4);										  
	public final static RotationDescription rotation4 = new RotationDescription(0,5*pi/4,-5*pi/2);
	public final static RotationDescription rotation5 = new RotationDescription(0,-5*pi/4,-5*pi/2);
	public final static RotationDescription rotation6 = new RotationDescription(0,-pi/4,-pi/2);// rotation5 +pi for y and +2*pi for z
	public final static RotationDescription rotation7 = new RotationDescription(0,pi/4,-pi/2);// rotation4 -pi for y and +2*pi for z
	public final static RotationDescription rotation8 = new RotationDescription(0,0,-5*pi/4);// rotation3 +pi/2 for z	
	public final static RotationDescription rotation9 = new RotationDescription(0,5*pi/4,0);//rotation2 +pi for y
	public final static RotationDescription rotation10 = new RotationDescription(0,-5*pi/4,0);//rotation1 -pi for y
	public final static RotationDescription rotation11= new RotationDescription(0,0,5*pi/4);//rotation0 + pi for z
	
	/**
	 * Global rotation of OdinBall (also called CCP joint)
	 */
	public final static RotationDescription rotation00 = new RotationDescription(0,0,0);	
//	FIXME ABOVE ROTATIONS, MAYBE CAN BE MOVED TO ODIN.java or somewhere else
	
	/**
	 * The array of objects containing information about OdinMuscle,OdinBattery,OdinHinge,OdinSpring,OdinTube,OdinWheel modules specific rotations.
	 */
	private final static ModuleRotationMapEntry[] moduleRotationMap =  {
		new ModuleRotationMapEntry("rotation0",rotation0,rotation11),
		new ModuleRotationMapEntry("rotation1",rotation1,rotation10),		
		new ModuleRotationMapEntry("rotation2",rotation2,rotation9),
		new ModuleRotationMapEntry("rotation3",rotation3,rotation8),		
		new ModuleRotationMapEntry("rotation4",rotation4,rotation7),
		new ModuleRotationMapEntry("rotation5",rotation5,rotation6),
		new ModuleRotationMapEntry("rotation6",rotation6,rotation5),
		new ModuleRotationMapEntry("rotation7",rotation7,rotation4),
		new ModuleRotationMapEntry("rotation8",rotation8,rotation3),
		new ModuleRotationMapEntry("rotation9",rotation9,rotation2),
		new ModuleRotationMapEntry("rotation10",rotation10,rotation1),
		new ModuleRotationMapEntry("rotation11",rotation11,rotation0)
	};

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
			odinBallConst.moveOdinBallAccording(connectorNr, selectedModule, newMovableModule);
		}
	}	

	@Override
	public void rotateOpposite(Module selectedModule) {
		String selectedModuleType =selectedModule.getProperty("ussr.module.type");
		if(selectedModuleType.equalsIgnoreCase("OdinBall")){//Do nothing					
		} else{
			/*Amount of components constituting selectedModule*/
			int amountComponents =selectedModule.getNumberOfComponents();		

			/*Loop through each component of selected module and rotate it with opposite rotation*/
			for (int component=0; component<amountComponents;component++){

				/* The current component of selected module*/
				JMEModuleComponent selectedModuleComponent= (JMEModuleComponent)selectedModule.getComponent(component);			
				Quaternion  rotationQselectedModuleComponent = selectedModule.getComponent(component).getRotation().getRotation();
	      	
				/*Locate matching rotation Quaternion and rotate with opposite Quaternion*/		
				for (int entry=0;entry<moduleRotationMap.length;entry++){
					if (rotationQselectedModuleComponent.equals(moduleRotationMap[entry].getRotation().getRotation())){
						rotateModuleComponent(selectedModuleComponent,moduleRotationMap[entry].getRotationOppositeValue().getRotation());						
					}
				}				
			}		
		}
	}

	@Override
	public void rotateSpecifically(Module selectedModule, String rotationName) {
		// NOT RELEVANT IN ODIN CASE
	}

	@Override
	public void variate(Module selectedModule) {		
	}
	
	/**
	 * Returns global position of connector on selected Odin module. 
	 * @param connectorNr,the connector number on Odin module. 
	 * @param selectedModule, the Odin module selected in simulation environment.
	 * @return VectorDescription, description of a vector. 
	 */
	public VectorDescription getConnectorPosition(int connectorNr, Module selectedModule){
		Connector connector = selectedModule.getConnectors().get(connectorNr);		
		VectorDescription connectorPosition = connector.getPhysics().get(0).getPosition();		
		return connectorPosition;		
	}
}
