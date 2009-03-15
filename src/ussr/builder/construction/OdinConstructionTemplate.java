package ussr.builder.construction;

import com.jme.math.Quaternion;

import ussr.builder.BuilderHelper;
import ussr.description.geometry.RotationDescription;
import ussr.description.geometry.VectorDescription;
import ussr.model.Connector;
import ussr.model.Module;
import ussr.physics.jme.JMEModuleComponent;
import ussr.physics.jme.JMESimulation;

/**
 * The main responsibility of this class  is to support children classes with common methods
 *  and  variables for construction of  morphology of Odin modular robot. Moreover define methods 
 *  specific for Odin modular robot. 
 * @author Konstantinas 
 */
//FIXME 1) UPDATE COMMENTS
//FIXME 2) FIX EXISTING IMPROVEMENTS
public class OdinConstructionTemplate extends ModularRobotConstructionTemplate {	
	
	/**
	 * The numbers of connectors on the OdinBall module (12 of them) and two of them(the first) for OdinMuscle
	 */
	public final static int connectorNr0 = 0,connectorNr1 = 1,connectorNr2 = 2,connectorNr3 = 3,
	                        connectorNr4 = 4, connectorNr5 = 5,connectorNr6 = 6, connectorNr7 = 7,
	                        connectorNr8 = 8, connectorNr9 = 9, connectorNr10 = 10,connectorNr11 = 11;
	/**
	 * A number of different rotations of Structure link, Telescoping link(also called OdinMuscle),OdinBattery,
	 * OdinHinge,OdinSpring,OdinTube and OdinWheel with respect to connectors of CCP joint (also called OdinBall).
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
		
	/**
	 * The array of objects containing information about OdinMuscle,OdinBattery,OdinHinge,OdinSpring,OdinTube,OdinWheel modules specific rotations.
	 */
	private final static ModuleRotationMapEntryHelper[] moduleRotationMap = {
		new ModuleRotationMapEntryHelper("rotation0",rotation0,rotation11),
		new ModuleRotationMapEntryHelper("rotation1",rotation1,rotation10),		
		new ModuleRotationMapEntryHelper("rotation2",rotation2,rotation9),
		new ModuleRotationMapEntryHelper("rotation3",rotation3,rotation8),		
		new ModuleRotationMapEntryHelper("rotation4",rotation4,rotation7),
		new ModuleRotationMapEntryHelper("rotation5",rotation5,rotation6),
		new ModuleRotationMapEntryHelper("rotation6",rotation6,rotation5),
		new ModuleRotationMapEntryHelper("rotation7",rotation7,rotation4),
		new ModuleRotationMapEntryHelper("rotation8",rotation8,rotation3),
		new ModuleRotationMapEntryHelper("rotation9",rotation9,rotation2),
		new ModuleRotationMapEntryHelper("rotation10",rotation10,rotation1),
		new ModuleRotationMapEntryHelper("rotation11",rotation11,rotation0)
	};
	
	/**
	 * Tolerance used to identify if component (module) already exists in interval of space.
	 */
	public final static float searchTolerance = 0.0001f;
	
	/**
	 * COMMENT
	 * @param simulation
	 */
	public OdinConstructionTemplate(JMESimulation simulation) {
		super(simulation);		
	}

	/**
	 * Moves newMovableModule of Odin according to selected Odin module preconditions,like connector, rotation of selected module,  and so on.
	 * @param connectorNr, the connector number on selected Odin module
	 * @param selectedModule,  the Odin module selected in simulation environment
	 * @param newMovableModule, the new Odin module to move
	 */	
	public void moveModuleComponentAccording(int connectorNr,Module selectedModule, JMEModuleComponent movableModuleComponent,Quaternion rotationQuatComponent) {
		String selectedModuleType =selectedModule.getProperty(BuilderHelper.getModuleTypeKey());
		if(selectedModuleType.equalsIgnoreCase("OdinBall")){			
			OdinMuscleConstructionTemplate  odinMuscleConst = new OdinMuscleConstructionTemplate(simulation);
			odinMuscleConst.moveOdinMusleAccording(connectorNr, selectedModule,movableModuleComponent.getModel());			
		} else if (selectedModuleType.equalsIgnoreCase("OdinMuscle")){
			OdinBallConstructionTemplate odinBallConst = new OdinBallConstructionTemplate(simulation);
			odinBallConst.moveOdinBallAccording(connectorNr, selectedModule, movableModuleComponent.getModel());
		}
	}	
	
	public void updateModuleMap(float x, float y, float z) {
		/*This method is overridden in children classes */		
	}
	
	/**
	 * Rotates Odin module selected in simulation environment with opposite rotation. This is except OdinBall.
	 * @param selectedModule, the Odin module selected in simulation environment.
	 * COMMNET	
	 */
	public void rotateComponentOpposite(JMEModuleComponent selectedModuleComponent,Quaternion  rotationQComponent) {
		String selectedModuleType = selectedModuleComponent.getModel().getProperty(BuilderHelper.getModuleTypeKey());
		if(selectedModuleType.equalsIgnoreCase("OdinBall")){//Do nothing					
		} else{	      	
			/*Locate matching rotation Quaternion in moduleRotationMap and rotate with opposite rotation Quaternion
			 * from the same entry in  moduleRotationMap*/		
				for (int entry=0;entry<moduleRotationMap.length;entry++){
					if (rotationQComponent.equals(moduleRotationMap[entry].getRotation().getRotation())){
						rotateModuleComponent(selectedModuleComponent,moduleRotationMap[entry].getRotationOppositeValue().getRotation());						
					}
				}					
		}
	}
	
	public void rotateComponentSpecifically(JMEModuleComponent selectedModuleComponent, String rotationName) {
		/*This method is not relevant in Odin case because modules only have two standard(specific)
		 * rotations, which are covered in the method above*/
	}
	
	public void variateComponentProperties(JMEModuleComponent selectedModuleComponent,Quaternion  rotationQComponent) {
		/*This functionality is moved to CommonOperationsStrategy class method called replaceModules(),
		because it is more concerned with creation of new modules, rather than rearranging the components of the module.*/
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
