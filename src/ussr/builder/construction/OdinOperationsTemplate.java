package ussr.builder.construction;

import java.awt.Color;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import ussr.builder.BuilderHelper;
import ussr.description.geometry.RotationDescription;
import ussr.description.geometry.VectorDescription;
import ussr.description.setup.ModuleConnection;
import ussr.description.setup.ModulePosition;
import ussr.model.Module;
import ussr.physics.jme.JMESimulation;
import ussr.samples.odin.OdinBuilder;

public class OdinOperationsTemplate extends CommonOperationsTemplate{

	/**
	 * @param simulation
	 */
	public OdinOperationsTemplate(JMESimulation simulation) {
		super(simulation);		
	}

	/**
	 * The default Odin module is OdinBall.
	 */
	private final static String defaultModule = "OdinBall";	
	
	/**
	 * The amount of connectors on default module.
	 */
	private final static int amountConnectors = 12;
	
	/**
	 * Other Odin modules
	 */
	private final static String odinMuscle = "OdinMuscle",odinHinge = "OdinHinge",odinBattery = "OdinBattery",
	                            odinSpring = "OdinSpring",odinWheel = "OdinWheel";
	/**
	 * Adds default (the first) OdinBall construction module at specified position.
	 * This method is so-called "Primitive operation" for  TEMPLATE method, called "addDefaultConstructionModule(String type, VectorDescription modulePosition)".	 
	 * @param type, the type of modular robot. In this case it is OdinBall.
	 * @param modulePosition, the position of the OdinBall module in simulation environment.
	 * @param moduleRotation, the rotation of the OdinBall module.
	 * @param colorsComponents, the colours of components constituting the OdinBall module.
	 * @param colorsConectors, the colours of connectors on the OdinBall module.
	 */
	@Override
	public void addDefaultModule(String type, VectorDescription modulePosition,	RotationDescription moduleRotation, List<Color> colorsComponents, ArrayList<Color> colorsConectors) {
		colorsComponents.add(Color.RED);			
		for (int connector=0;connector<amountConnectors;connector++){
			colorsConectors.add(Color.WHITE);
		}
		moduleRotation = OdinConstructionTemplate.rotation00;
		addNewModule(new ModulePosition(defaultModule+ BuilderHelper.getRandomInt(),defaultModule,modulePosition,moduleRotation),colorsComponents,colorsConectors);
	}	
	
	/**
	 * Creates and returns new default construction Odin modules, depending on which type of the module is passed.
	 * The assumption is that during initial construction are used only OdinBall and OdinBall.
	 * This method is so-called "Primitive operation" for above TEMPLATE method, called "addNewModuleOnConnector(ConstructionToolSpecification toolSpecification)".	 
	 * @param selectedModule,the module selected in simulation environment.
	 * @return module, newly created Odin module.  
	 */	
	@Override
	public Module createNewModule(Module selectedModule) {		
		String selectedModuleType = selectedModule.getProperty(BuilderHelper.getModuleTypeKey());
		Module odinModule = null;
		if (selectedModuleType.equalsIgnoreCase(defaultModule)){
			odinModule = createNewOdinModule(odinMuscle);							
		}else if (selectedModuleType.equalsIgnoreCase(odinMuscle)){
			odinModule = createNewOdinModule(defaultModule); 			
		}else throw new Error("Something is wrong with the type of the module");		
		return odinModule;
	}
	
	/**
	 * Creates new Odin modules, according to the type passed as a String.
	 * @param type, the type of Odin module.
	 * @return odinModule, the specific Odin module, like for example "OdinMuscle" or "OdinBall".
	 */
	private Module createNewOdinModule(String type){		
		Module odinModule = new Module();
		List<Color> colorsComponents = new LinkedList<Color>();
		ArrayList<Color> colorsConectors = new ArrayList<Color>();
		VectorDescription modulePosition = new VectorDescription(0,0,0);
		RotationDescription moduleRotation = new RotationDescription(0,0,0);		
		if (type.equalsIgnoreCase(odinMuscle)){			
			colorsComponents.add(Color.RED); colorsComponents.add(Color.BLUE);
			colorsConectors.add(Color.WHITE); colorsConectors.add(Color.WHITE);			
			odinModule  = addNewModule(new ModulePosition(odinMuscle+ BuilderHelper.getRandomInt(),odinMuscle,modulePosition,moduleRotation),colorsComponents,colorsConectors);
		}else if(type.equalsIgnoreCase(defaultModule)){		
			colorsComponents.add(Color.RED);			
			for (int connector=0;connector<amountConnectors;connector++){
				colorsConectors.add(Color.WHITE);
			}			
			odinModule = addNewModule(new ModulePosition(defaultModule+BuilderHelper.getRandomInt(),defaultModule,modulePosition,moduleRotation),colorsComponents,colorsConectors);
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
		BuilderHelper.deleteModule(selectedModule);
		if (selectedModuleType.equalsIgnoreCase(odinMuscle)){						
			colorsComponents.add(Color.WHITE); colorsComponents.add(Color.WHITE);colorsComponents.add(Color.RED); colorsComponents.add(Color.WHITE); colorsComponents.add(Color.WHITE);			
			colorsConectors.add(Color.WHITE); colorsConectors.add(Color.WHITE);
			addNewModule(new ModulePosition(odinHinge+BuilderHelper.getRandomInt(),odinHinge,modulePosition,moduleRotation),colorsComponents,colorsConectors);			
		}else if (selectedModuleType.equalsIgnoreCase(odinHinge)){			
			colorsComponents.add(Color.WHITE); colorsComponents.add(Color.WHITE);colorsComponents.add(Color.WHITE);			
			colorsConectors.add(Color.WHITE); colorsConectors.add(Color.WHITE);
			addNewModule(new ModulePosition(odinBattery+BuilderHelper.getRandomInt(),odinBattery,modulePosition,moduleRotation),colorsComponents,colorsConectors);
		}else if (selectedModuleType.equalsIgnoreCase(odinBattery)){					
			colorsComponents.add(Color.BLACK); colorsComponents.add(Color.WHITE);colorsComponents.add(Color.WHITE);			
			colorsConectors.add(Color.WHITE); colorsConectors.add(Color.WHITE);
			addNewModule(new ModulePosition(odinSpring+BuilderHelper.getRandomInt(),odinSpring,modulePosition,moduleRotation),colorsComponents,colorsConectors);			
		}
//FIXME	 (SOFAR IS NOT NEEDED) PROBLEM java.lang.Error: Illegal module type: OdinTubeRobotType:OdinTube
		/*else if (selectedModuleType.equalsIgnoreCase("OdinSpring")){						
			colorsComponents.add(Color.WHITE); colorsComponents.add(Color.WHITE);colorsComponents.add(Color.WHITE);			
			colorsConectors.add(Color.WHITE); colorsConectors.add(Color.WHITE);
			addNewModuleOneMore(new ModulePosition("newTube","OdinTube",modulePosition,moduleRotation),colorsComponents,colorsConectors);
		}*/else if (selectedModuleType.equalsIgnoreCase(odinSpring)){			
			colorsComponents.add(Color.WHITE); colorsComponents.add(Color.BLUE);colorsComponents.add(Color.WHITE); 	colorsComponents.add(Color.WHITE);			
			colorsConectors.add(Color.WHITE); colorsConectors.add(Color.WHITE);
			addNewModule(new ModulePosition(odinWheel+BuilderHelper.getRandomInt(),odinWheel,modulePosition,moduleRotation),colorsComponents,colorsConectors);
		}else if (selectedModuleType.equalsIgnoreCase(odinWheel)){					
			colorsComponents.add(Color.RED); colorsComponents.add(Color.BLUE);colorsComponents.add(Color.RED); 	colorsComponents.add(Color.BLUE);			
			colorsConectors.add(Color.WHITE); colorsConectors.add(Color.WHITE);
			addNewModule(new ModulePosition(odinMuscle + BuilderHelper.getRandomInt(),odinMuscle,modulePosition,moduleRotation),colorsComponents,colorsConectors);
		}		
	}	
}