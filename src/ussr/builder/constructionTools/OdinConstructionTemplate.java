package ussr.builder.constructionTools;

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
 *  specific for Odin modular robot construction. Here the assumption is that construction is based
 *  on assembly of Odin morphology from OdinBalls and OdinMuscles as default construction modules.
 *  Later the OdinMuscles can be substituted with other OdinModules, see class named OdinOperationsTemplate.java. 
 * @author Konstantinas 
 */
//FIXME 1) UPDATE COMMENTS
//FIXME 2) FIX EXISTING IMPROVEMENTS
public class OdinConstructionTemplate extends ModularRobotConstructionTemplate {	
	
	/**
	 * The numbers of connectors on the OdinBall module (12 of them) and two of them(the first) for OdinMuscle
	 */
	public final static int CONNECTORnr0 = 0,CONNECTORnr1 = 1,CONNECTORnr2 = 2,CONNECTORnr3 = 3,
	                        CONNECTORnr4 = 4, CONNECTORnr5 = 5,CONNECTORnr6 = 6, CONNECTORnr7 = 7,
	                        CONNECTORnr8 = 8, CONNECTORnr9 = 9, CONNECTORnr10 = 10,CONNECTORnr11 = 11;
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
	public final static RotationDescription ROTATION0 = new RotationDescription(0,0,pi/4);
	public final static RotationDescription ROTATION1 = new RotationDescription(0,-pi/4,0);
	public final static RotationDescription ROTATION2 = new RotationDescription(0,pi/4,0);										  
	public final static RotationDescription ROTATION3 = new RotationDescription(0,0,-pi/4);										  
	public final static RotationDescription ROTATION4 = new RotationDescription(0,5*pi/4,-5*pi/2);
	public final static RotationDescription ROTATION5 = new RotationDescription(0,-5*pi/4,-5*pi/2);
	public final static RotationDescription ROTATION6 = new RotationDescription(0,-pi/4,-pi/2);// rotation5 +pi for y and +2*pi for z
	public final static RotationDescription ROTATION7 = new RotationDescription(0,pi/4,-pi/2);// rotation4 -pi for y and +2*pi for z
	public final static RotationDescription ROTATION8 = new RotationDescription(0,0,-5*pi/4);// rotation3 +pi/2 for z	
	public final static RotationDescription ROTATION9 = new RotationDescription(0,5*pi/4,0);//rotation2 +pi for y
	public final static RotationDescription ROTATION10 = new RotationDescription(0,-5*pi/4,0);//rotation1 -pi for y
	public final static RotationDescription ROTATION11= new RotationDescription(0,0,5*pi/4);//rotation0 + pi for z
	
	/**
	 * Global rotation of OdinBall (also called CCP joint)
	 */
	public final static RotationDescription ROTATION000 = new RotationDescription(0,0,0);
	
	/**
	 * The default construction modules used to construct Odin modular robot morphology.
	 * Default means that the morphology is initially build from OdinBall and OdinMuscle.
	 * Later they can be swapped with other modules.  
	 */
	private final static String odinBall ="OdinBall",odinMuscle = "OdinMuscle";
		
	/**
	 * The array of objects containing information about OdinMuscle,OdinBattery,OdinHinge,OdinSpring,OdinTube,OdinWheel modules specific rotations.
	 */
	private final static ModuleRotationMapEntryHelper[] MODULE_ROTATION_MAP = {
		new ModuleRotationMapEntryHelper("rotation0",ROTATION0,ROTATION11),
		new ModuleRotationMapEntryHelper("rotation1",ROTATION1,ROTATION10),		
		new ModuleRotationMapEntryHelper("rotation2",ROTATION2,ROTATION9),
		new ModuleRotationMapEntryHelper("rotation3",ROTATION3,ROTATION8),		
		new ModuleRotationMapEntryHelper("rotation4",ROTATION4,ROTATION7),
		new ModuleRotationMapEntryHelper("rotation5",ROTATION5,ROTATION6),
		new ModuleRotationMapEntryHelper("rotation6",ROTATION6,ROTATION5),
		new ModuleRotationMapEntryHelper("rotation7",ROTATION7,ROTATION4),
		new ModuleRotationMapEntryHelper("rotation8",ROTATION8,ROTATION3),
		new ModuleRotationMapEntryHelper("rotation9",ROTATION9,ROTATION2),
		new ModuleRotationMapEntryHelper("rotation10",ROTATION10,ROTATION1),
		new ModuleRotationMapEntryHelper("rotation11",ROTATION11,ROTATION0)
	};
	
	/**
	 * Tolerance used to identify if component (module) already exists in interval of space.
	 */
	public final static float SEARCH_TOLERANCE = 0.0001f;
	
	/**
	 * Supports construction of Odin modular robot's morphology on the level of components.
	 * @param simulation, the physical simulation.
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
	public void moveModuleComponentAccording(int connectorNr,Module selectedModule, JMEModuleComponent movableModuleComponent,Quaternion rotationQuatComponent,boolean loopFlag) {
		String selectedModuleType =selectedModule.getProperty(BuilderHelper.getModuleTypeKey());
		if(selectedModuleType.equalsIgnoreCase(odinBall)){			
			OdinMuscleConstructionTemplate  odinMuscleConst = new OdinMuscleConstructionTemplate(simulation);
			odinMuscleConst.moveOdinMusleAccording(connectorNr, selectedModule,movableModuleComponent.getModel(), loopFlag);			
		} else if (selectedModuleType.equalsIgnoreCase(odinMuscle)){
			OdinBallConstructionTemplate odinBallConst = new OdinBallConstructionTemplate(simulation);
			odinBallConst.moveOdinBallAccording(connectorNr, selectedModule, movableModuleComponent.getModel(),loopFlag);
		}
	}	
	
	public void updateModuleMap(float x, float y, float z) {
		/*This method is overridden in children classes */		
	}
	
	/**
	 * Rotates Odin module selected in simulation environment with opposite rotation. This is means all except OdinBall.
	 * @param selectedModule, the Odin module selected in simulation environment.	
	 */
	public void rotateComponentOpposite(JMEModuleComponent selectedModuleComponent,Quaternion  rotationQComponent) {
		String selectedModuleType = selectedModuleComponent.getModel().getProperty(BuilderHelper.getModuleTypeKey());
		if(selectedModuleType.equalsIgnoreCase(odinBall)){//Do nothing					
		} else{	      	
			/*Locate matching rotation Quaternion in moduleRotationMap and rotate with opposite rotation Quaternion
			 * from the same entry in  moduleRotationMap*/		
				for (int entry=0;entry<MODULE_ROTATION_MAP.length;entry++){
					if (rotationQComponent.equals(MODULE_ROTATION_MAP[entry].getRotation().getRotation())){
						rotateModuleComponent(selectedModuleComponent,MODULE_ROTATION_MAP[entry].getRotationOppositeValue().getRotation());						
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
