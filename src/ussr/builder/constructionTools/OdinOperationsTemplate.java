package ussr.builder.constructionTools;

import java.awt.Color;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import ussr.builder.enumerations.OdinTypesModules;
import ussr.builder.helpers.BuilderHelper;
import ussr.description.geometry.RotationDescription;
import ussr.description.geometry.VectorDescription;
import ussr.description.setup.ModulePosition;
import ussr.model.Module;
import ussr.physics.jme.JMESimulation;
import ussr.remote.facade.RemotePhysicsSimulationImpl;

/**
 * Supports construction of Odin modular robot morphology in more abstract module oriented way.
 * In general the main responsibility of this class is to create Odin modules, add default Odin
 * construction module and variate the type of modules. 
 * @author Konstantinas
 *
 */
public class OdinOperationsTemplate extends CommonOperationsTemplate{

	/**
	 * The default construction module is OdinBall.
	 */
	private final static String DEFAULT_MODULE = "OdinBall";	
	
	/**
	 * The amount of connectors on default module.
	 */
	private final static int AMOUNT_CONNECTORS = 12;
	
	/**
	 * Other Odin modules
	 */
	private final static String ODIN_MUSCLE = "OdinMuscle",ODIN_HINGE = "OdinHinge",ODIN_BATTERY = "OdinBattery",
	                            ODIN_SPRING = "OdinSpring",ODIN_WHEEL = "OdinWheel";
		
	/**
	 * Supports construction of Odin modular robot morphology in more abstract module oriented way.
	 * @param simulation, the physical simulation.
	 */
	public OdinOperationsTemplate(JMESimulation simulation) {
		super(simulation);		
	}
	
	/**
	 * Adds default (the first) OdinBall construction module at specified position.
	 * This method is so-called "Primitive operation" for  TEMPLATE method, called "addDefaultConstructionModule(String type, VectorDescription modulePosition)".	 
	 * @param type, the type of modular robot. In this case it is OdinBall.
	 * @param modulePosition, the position of the OdinBall module in simulation environment.
	 * @param moduleRotation, the rotation of the OdinBall module.
	 * @param colorsComponents, the colors of components constituting the OdinBall module.
	 * @param colorsConectors, the colors of connectors on the OdinBall module.
	 */
	@Override
	public void addDefaultModule(String type, VectorDescription modulePosition,	RotationDescription moduleRotation, List<Color> colorsComponents, ArrayList<Color> colorsConectors) {
		colorsComponents.add(Color.RED);			
		for (int connector=0;connector<AMOUNT_CONNECTORS;connector++){
			colorsConectors.add(Color.WHITE);
		}
		moduleRotation = OdinConstructionTemplate.ROTATION000;
		addNewModule(new ModulePosition(DEFAULT_MODULE+ BuilderHelper.getRandomInt(),DEFAULT_MODULE,modulePosition,moduleRotation),colorsComponents,colorsConectors);
	}	
	
	/**
	 * Creates and returns new default construction Odin modules, depending on which type of the module is passed.
	 * The assumption is that during initial construction are used only OdinBall and OdinBall.
	 * This method is so-called "Primitive operation" for above TEMPLATE method, called "addNewModuleOnConnector(ConstructionToolSpecification toolSpecification)".	 
	 * @param selectedModule,the module selected in simulation environment.
	 * @return module, newly created Odin module.
	 * @throws new Error("Something is wrong with the type of the module").  
	 */	
	@Override
	public Module createNewModule(Module selectedModule) {		
		String selectedModuleType = selectedModule.getProperty(BuilderHelper.getModuleTypeKey());
		Module odinModule = null;
		if (selectedModuleType.contains("Ball")){
			String userFriendlyModuleType ="";
			try {
				userFriendlyModuleType = RemotePhysicsSimulationImpl.getGUICallbackControl().getDefaultConstructionModuleType();
			} catch (RemoteException e) {
				throw new Error("Failed  to receive module type, due to remote exception.");
			}
			String undelyingLogicModuleType = OdinTypesModules.getUnderlyingLogicNameFromUserFriendly(userFriendlyModuleType);
			odinModule = createNewOdinModule(undelyingLogicModuleType);
		}else /*if (selectedModuleType.equalsIgnoreCase(ODIN_MUSCLE))*/{
			odinModule = createNewOdinModule(DEFAULT_MODULE); 			
		}//else throw new Error("Something is wrong with the type of the module");		
		return odinModule;
	}
	
	/**
	 * Creates new Odin modules, according to the type passed as a String.
	 * @param type, the type of Odin module.
	 * @return odinModule, the specific Odin module, like for example "OdinMuscle" or "OdinBall".
	 * @throws Error, if something is wrong with the type of the module.This method supports creation of OdinMuscle and OdinBall only.
	 */
	private Module createNewOdinModule(String type){		
		Module odinModule = new Module(null);
		List<Color> colorsComponents = new LinkedList<Color>();
		ArrayList<Color> colorsConectors = new ArrayList<Color>();
		VectorDescription modulePosition = new VectorDescription(0,0,0);
		RotationDescription moduleRotation = new RotationDescription(0,0,0);
		if (type.equalsIgnoreCase(ODIN_MUSCLE)){			
			colorsComponents.add(Color.RED); colorsComponents.add(Color.BLUE);
			colorsConectors.add(Color.WHITE); colorsConectors.add(Color.WHITE);			
			odinModule  = addNewModule(new ModulePosition(ODIN_MUSCLE+ BuilderHelper.getRandomInt(),ODIN_MUSCLE,modulePosition,moduleRotation),colorsComponents,colorsConectors);
		}else if(type.equalsIgnoreCase(DEFAULT_MODULE)){		
			colorsComponents.add(Color.RED);			
			for (int connector=0;connector<AMOUNT_CONNECTORS;connector++){
				colorsConectors.add(Color.WHITE);
			}			
			odinModule = addNewModule(new ModulePosition(DEFAULT_MODULE+BuilderHelper.getRandomInt(),DEFAULT_MODULE,modulePosition,moduleRotation),colorsComponents,colorsConectors);
		}else if (type.equalsIgnoreCase(ODIN_HINGE)){
			colorsComponents.add(Color.WHITE); colorsComponents.add(Color.WHITE);colorsComponents.add(Color.RED); colorsComponents.add(Color.WHITE); colorsComponents.add(Color.WHITE);			
			colorsConectors.add(Color.WHITE); colorsConectors.add(Color.WHITE);
			odinModule = addNewModule(new ModulePosition(ODIN_HINGE+BuilderHelper.getRandomInt(),ODIN_HINGE,modulePosition,moduleRotation),colorsComponents,colorsConectors);			
		}else if (type.equalsIgnoreCase(ODIN_BATTERY)){
			colorsComponents.add(Color.WHITE); colorsComponents.add(Color.WHITE);colorsComponents.add(Color.WHITE);			
			colorsConectors.add(Color.WHITE); colorsConectors.add(Color.WHITE);
			odinModule = addNewModule(new ModulePosition(ODIN_BATTERY+BuilderHelper.getRandomInt(),ODIN_BATTERY,modulePosition,moduleRotation),colorsComponents,colorsConectors);
		}else if (type.equalsIgnoreCase(ODIN_SPRING)){
			colorsComponents.add(Color.BLACK); colorsComponents.add(Color.WHITE);colorsComponents.add(Color.WHITE);			
			colorsConectors.add(Color.WHITE); colorsConectors.add(Color.WHITE);
			odinModule = addNewModule(new ModulePosition(ODIN_SPRING+BuilderHelper.getRandomInt(),ODIN_SPRING,modulePosition,moduleRotation),colorsComponents,colorsConectors);			
		}else if (type.equalsIgnoreCase(ODIN_WHEEL)){
			colorsComponents.add(Color.WHITE); colorsComponents.add(Color.BLUE);colorsComponents.add(Color.WHITE); 	colorsComponents.add(Color.WHITE);			
			colorsConectors.add(Color.WHITE); colorsConectors.add(Color.WHITE);
			odinModule = addNewModule(new ModulePosition(ODIN_WHEEL+BuilderHelper.getRandomInt(),ODIN_WHEEL,modulePosition,moduleRotation),colorsComponents,colorsConectors);
		}else throw new Error("Something is wrong with the type of the module.This method supports creation of OdinMuscle and OdinBall only.");
		return odinModule;
	}

	/**
	 * Replaces OdinModule selected in simulation environment with another type of Odin module. The particular sequence 
	 * is "OdinMuscle"-->"OdinHinge"-->"OdinBattery"-->"OdinSpring"-->"OdinWheel"-->"OdinMuscle". For example
	 * if "OdinBattery" is selected once then it will be replaced with "OdinSpring".
	 * This method is so-called "Primitive operation" for TEMPLATE method, called "variateModule(ConstructionToolSpecification toolSpecification)".	    
	 * @param selectedModule, the Odin module selected in simulation environment. These can be any of them,
	 * except the OdinBall. 
	 */	
	@Override
	public void variateSpecificModule(Module selectedModule) {
		String selectedModuleType =	selectedModule.getProperty(BuilderHelper.getModuleTypeKey());		
		VectorDescription modulePosition = selectedModule.getPhysics().get(0).getPosition();
		RotationDescription moduleRotation = selectedModule.getPhysics().get(0).getRotation();
		List<Color> colorsComponents = new LinkedList<Color>();
		ArrayList<Color> colorsConectors = new ArrayList<Color>();		
		if (selectedModuleType.equalsIgnoreCase (DEFAULT_MODULE)){
			//do nothing
		}else if (selectedModuleType.equalsIgnoreCase(ODIN_MUSCLE)){
			BuilderHelper.deleteModule(selectedModule,true);
			colorsComponents.add(Color.WHITE); colorsComponents.add(Color.WHITE);colorsComponents.add(Color.RED); colorsComponents.add(Color.WHITE); colorsComponents.add(Color.WHITE);			
			colorsConectors.add(Color.WHITE); colorsConectors.add(Color.WHITE);
			addNewModule(new ModulePosition(ODIN_HINGE+BuilderHelper.getRandomInt(),ODIN_HINGE,modulePosition,moduleRotation),colorsComponents,colorsConectors);			
		}else if (selectedModuleType.equalsIgnoreCase(ODIN_HINGE)){
			BuilderHelper.deleteModule(selectedModule,true);
			colorsComponents.add(Color.WHITE); colorsComponents.add(Color.WHITE);colorsComponents.add(Color.WHITE);			
			colorsConectors.add(Color.WHITE); colorsConectors.add(Color.WHITE);
			addNewModule(new ModulePosition(ODIN_BATTERY+BuilderHelper.getRandomInt(),ODIN_BATTERY,modulePosition,moduleRotation),colorsComponents,colorsConectors);
		}else if (selectedModuleType.equalsIgnoreCase(ODIN_BATTERY)){
			BuilderHelper.deleteModule(selectedModule,true);
			colorsComponents.add(Color.BLACK); colorsComponents.add(Color.WHITE);colorsComponents.add(Color.WHITE);			
			colorsConectors.add(Color.WHITE); colorsConectors.add(Color.WHITE);
			addNewModule(new ModulePosition(ODIN_SPRING+BuilderHelper.getRandomInt(),ODIN_SPRING,modulePosition,moduleRotation),colorsComponents,colorsConectors);			
		}else if (selectedModuleType.equalsIgnoreCase(ODIN_SPRING)){
			BuilderHelper.deleteModule(selectedModule,true);
			colorsComponents.add(Color.WHITE); colorsComponents.add(Color.BLUE);colorsComponents.add(Color.WHITE); 	colorsComponents.add(Color.WHITE);			
			colorsConectors.add(Color.WHITE); colorsConectors.add(Color.WHITE);
			addNewModule(new ModulePosition(ODIN_WHEEL+BuilderHelper.getRandomInt(),ODIN_WHEEL,modulePosition,moduleRotation),colorsComponents,colorsConectors);
		}else if (selectedModuleType.equalsIgnoreCase(ODIN_WHEEL)){
			BuilderHelper.deleteModule(selectedModule,true);
			colorsComponents.add(Color.RED); colorsComponents.add(Color.BLUE);colorsComponents.add(Color.RED); 	colorsComponents.add(Color.BLUE);			
			colorsConectors.add(Color.WHITE); colorsConectors.add(Color.WHITE);
			addNewModule(new ModulePosition(ODIN_MUSCLE + BuilderHelper.getRandomInt(),ODIN_MUSCLE,modulePosition,moduleRotation),colorsComponents,colorsConectors);
		}		
	}

	/**
	 * Returns the lower level construction object for Odin modular robot morphology.
	 * The construction is on the level of components.
	 */
	@Override
	public ConstructionTemplate getConstruction() {		
		return construction = new OdinConstructionTemplate(simulation);
	}	
}