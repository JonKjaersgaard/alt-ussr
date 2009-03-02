package ussr.builder.construction;

import java.awt.Color;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import ussr.builder.BuilderHelper;
import ussr.description.geometry.RotationDescription;
import ussr.description.geometry.VectorDescription;
import ussr.description.setup.ModulePosition;
import ussr.model.Module;
import ussr.physics.jme.JMESimulation;
import ussr.samples.atron.ATRON;
import ussr.samples.mtran.MTRANSimulation;
import ussr.samples.odin.modules.OdinHinge;

/**
 * The main responsibility of this class is to act as Context class in Strategy pattern.
 * Moreover define the methods common for construction of modular robots morphologies in more 
 * abstract-module oriented way, rather than component oriented way. It is more abstract due to the 
 * fact that modules are created and moved with respect to each other here. 
 * @author Konstantinas
 *
 */
//TODO 1) UPDATE COMMENTS
//2) REFACTOR method called moveModuleOnNextConnector
//3) ASK ABOUT ODINTUBE
public class CommonOperationsStrategy implements  SelectOperationsStrategy{

	/** 
	 * The physical simulation
	 */
	private JMESimulation simulation;	

	/**
	 * The interface to construction of modular robot morphology. This one is on the level of components of the module.  
	 */
	private ConstructionStrategy construction;

	/**
	 * The name of modular robot,like for example "ATRON", "MTRAN" and "Odin"
	 */
	private String modularRobotName;

	/**
	 * The module selected in simulation environment. 
	 */
	private Module selectedModule;

	/**
	 *  Newly created movable module.
	 */
	private Module newMovableModule;

	/**
	 * Construction of modular robot morphologies in more abstract module oriented way.
	 * @param simulation,the physical simulation
	 */
	public  CommonOperationsStrategy(JMESimulation simulation){
		this.simulation = simulation;
	}

	/**
	 * Adds the new module on connector. This is for both: selected connector on the module in simulation environment
	 * and connector passed as a variable and later selected module in simulation environment.
	 * @param toolSpecification,object containing information about modular robot, selected or chosen connector
	 * number,selected module, simulation and so on.  
	 */
	public void addNewModuleOnConnector(ConstructionToolSpecification toolSpecification){	    
		/*Define the information, which will be manipulated*/
		this.construction = toolSpecification.getConstruction();		
		this.selectedModule = toolSpecification.getSelectedModule();			
		this.modularRobotName = toolSpecification.getModularRobotName();
		/*For each modular robot create new movable module and move it with respect to selected module */
		if (this.modularRobotName.equalsIgnoreCase("ATRON")|| this.modularRobotName.equalsIgnoreCase("MTRAN")){
			//HERE THE PROBLEM EXISTS WITH "default" thing of ATRON 
			this.newMovableModule = addNewCopyModule(this.selectedModule,toolSpecification.getModularRobotName());			
			this.construction.moveModuleAccording(toolSpecification.getSelectedConnectorNr(), this.selectedModule, this.newMovableModule);
		}else if (this.modularRobotName.equalsIgnoreCase("Odin")){
			this.selectedModule = toolSpecification.getSelectedModule();
			String selectedModuleType =this.selectedModule.getProperty(BuilderHelper.getModuleTypeKey());
			if (selectedModuleType.equalsIgnoreCase("OdinBall")){
				this.newMovableModule = createNewOdinModule("OdinMuscle");
				this.construction.moveModuleAccording(toolSpecification.getSelectedConnectorNr(), this.selectedModule, this.newMovableModule);
				/*SOLUTION NR2. FORGET FOR NOW (OVERLAPING OF MODULES IS TOO BIG PROBLEM)
				Module newMovableModule1 = createNewOdinModule(simulation,"OdinMuscle");
				construction.moveModuleAccording(specification.getSelectedConnectorNr(), selectedModule, newMovableModule1);
				Module newMovableModule2 = createNewOdinModule(simulation,"OdinBall");
				construction.moveModuleAccording(0, newMovableModule1, newMovableModule2);*/				
			}else if (selectedModuleType.equalsIgnoreCase("OdinMuscle")){
				this.newMovableModule = createNewOdinModule("OdinBall"); 
				this.construction.moveModuleAccording(toolSpecification.getSelectedConnectorNr(), this.selectedModule, this.newMovableModule);
			}			
		}else throw new Error("This type of modular robot is not supported yet or the name is misspelled");		
	}

	/**
	 * Adds the new modules on all connectors of the module selected in simulation environment
	 * @param toolSpecification,object containing information about modular robot, selected module, simulation
	 * and so on.
	 */
	public void addModulesOnAllConnectors(ConstructionToolSpecification toolSpecification) {
		/*Define the information, which will be manipulated*/
		this.selectedModule = toolSpecification.getSelectedModule();
		int amountConnectors = this.selectedModule.getConnectors().size();
		/*For each connector of selected module set it as selected connector and place new module on it*/
		for (int connectorNr=0; connectorNr<amountConnectors;connectorNr++){			
			toolSpecification.setSelectedConnectorNr(connectorNr);			
			addNewModuleOnConnector(toolSpecification);				
		}	
	}

	/**
	 * Rotates the module selected in simulation environment with opposite rotation
	 * @param toolSpecification, object containing information about modular robot, selected module, simulation
	 * and so on.
	 */
	public void rotateModuleWithOppositeRotation(ConstructionToolSpecification toolSpecification){
		this.selectedModule = toolSpecification.getSelectedModule();
		this.construction = toolSpecification.getConstruction();
		this.construction.rotateOpposite(this.selectedModule);	
	}

	/**
	 * Rotates the module selected in simulation environment with standard rotation passed as a String
	 * @param toolSpecification, object containing information about modular robot, selected module, simulation
	 * and so on.
	 */
	public void rotateModuleStandardRotation(ConstructionToolSpecification toolSpecification, String standardRotationName){
		this.selectedModule = toolSpecification.getSelectedModule();
		this.construction = toolSpecification.getConstruction();
		this.construction.rotateSpecifically(this.selectedModule, standardRotationName);	
	}	

	/**
	 * Rotates the module selected in simulation environment with opposite rotation
	 * @param toolSpecification,object containing information about modular robot, selected module, simulation
	 * and so on.
	 */
	public void variateModule(ConstructionToolSpecification toolSpecification){		
		this.modularRobotName = toolSpecification.getModularRobotName();
		this.selectedModule = toolSpecification.getSelectedModule();
		if (this.modularRobotName.equalsIgnoreCase("ATRON")|| this.modularRobotName.equalsIgnoreCase("MTRAN")){		
			this.construction = toolSpecification.getConstruction();
			this.construction.variate(this.selectedModule);
		}else if (this.modularRobotName.equalsIgnoreCase("Odin")){
			replaceOdinModules(this.selectedModule);
		}	
	}	

	//TODO MOVE THE FUNCTIONALITY
	public void moveModuleOnNextConnector(ConstructionToolSpecification specification){

	}


	/**
	 * Adds default (the first) construction module at specified position.
	 * @param type, the type of modular robot. For example: ATRON,MTRAN or OdinBall.
	 * @param modulePosition, the position of module in simulation environment.
	 */
	public void addDefaultConstructionModule(String type, VectorDescription modulePosition ){			

		List<Color> colorsComponents = new LinkedList<Color>();
		ArrayList<Color> colorsConectors = new ArrayList<Color>();
		RotationDescription moduleRotation;		
		if (type.equalsIgnoreCase("ATRON")){
			colorsComponents.add(Color.BLUE);colorsComponents.add(Color.RED);
			colorsConectors.add(Color.BLACK);colorsConectors.add(Color.WHITE);colorsConectors.add(Color.BLACK);colorsConectors.add(Color.WHITE);colorsConectors.add(Color.BLACK);colorsConectors.add(Color.WHITE);colorsConectors.add(Color.BLACK);colorsConectors.add(Color.WHITE);
			moduleRotation = ATRON.ROTATION_EW;
			addNewModule(new ModulePosition(type+ BuilderHelper.getRandomInt(),type,modulePosition,moduleRotation),colorsComponents,colorsConectors);
		}else if (type.equalsIgnoreCase("MTRAN")){
			colorsComponents.add(Color.BLUE);colorsComponents.add(Color.BLACK);colorsComponents.add(Color.RED);
			colorsConectors.add(Color.BLACK);colorsConectors.add(Color.BLACK);colorsConectors.add(Color.BLACK);colorsConectors.add(Color.WHITE);colorsConectors.add(Color.WHITE);colorsConectors.add(Color.WHITE);
			moduleRotation = MTRANSimulation.ORI2;
			addNewModule(new ModulePosition(type+ BuilderHelper.getRandomInt(),type,modulePosition,moduleRotation),colorsComponents,colorsConectors);
		}else if (type.equalsIgnoreCase("Odin")){
			colorsComponents.add(Color.RED);			
			for (int connector=0;connector<12;connector++){
				colorsConectors.add(Color.WHITE);
			}
			moduleRotation = OdinConstructionStrategy.rotation00;
			addNewModule(new ModulePosition("OdinBall"+ BuilderHelper.getRandomInt(),"OdinBall",modulePosition,moduleRotation),colorsComponents,colorsConectors);
		}		
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
		if (type.equalsIgnoreCase("OdinMuscle")){			
			colorsComponents.add(Color.RED); colorsComponents.add(Color.BLUE);
			colorsConectors.add(Color.WHITE); colorsConectors.add(Color.WHITE);			
			odinModule  = addNewModule(new ModulePosition("Muscle"+ BuilderHelper.getRandomInt(),"OdinMuscle",modulePosition,moduleRotation),colorsComponents,colorsConectors);
		}else if(type.equalsIgnoreCase("OdinBall")){		
			colorsComponents.add(Color.RED);			
			for (int connector=0;connector<12;connector++){
				colorsConectors.add(Color.WHITE);
			}			
			odinModule = addNewModule(new ModulePosition("Ball"+BuilderHelper.getRandomInt(),"OdinBall",modulePosition,moduleRotation),colorsComponents,colorsConectors);
		}
		return odinModule;
	}

	/**
	 * Replaces OdinModule selected in simulation environment with another kind of Odin module. The particular sequence 
	 * is "OdinMuscle"-->"OdinHinge"-->"OdinBattery"-->"OdinSpring"-->"OdinWheel"-->"OdinMuscle". For example
	 * if "OdinBattery" is selected once then it will be replaced with "OdinSpring".
	 * @param selectedModule, the Odin module selected in simulation environment. These can be any of them,
	 * except the OdinBall.
	 */
	private void replaceOdinModules(Module selectedModule){	
		String selectedModuleType =	selectedModule.getProperty(BuilderHelper.getModuleTypeKey());
		BuilderHelper builderHelper= new BuilderHelper();
		VectorDescription modulePosition = selectedModule.getPhysics().get(0).getPosition();
		RotationDescription moduleRotation = selectedModule.getPhysics().get(0).getRotation();
		List<Color> colorsComponents = new LinkedList<Color>();
		ArrayList<Color> colorsConectors = new ArrayList<Color>();		
		if (selectedModuleType.equalsIgnoreCase("OdinMuscle")){			
			builderHelper.deleteModule(selectedModule);			
			colorsComponents.add(Color.WHITE); colorsComponents.add(Color.WHITE);colorsComponents.add(Color.RED); colorsComponents.add(Color.WHITE); colorsComponents.add(Color.WHITE);			
			colorsConectors.add(Color.WHITE); colorsConectors.add(Color.WHITE);
			addNewModule(new ModulePosition("newHinge","OdinHinge",modulePosition,moduleRotation),colorsComponents,colorsConectors);			
		}else if (selectedModuleType.equalsIgnoreCase("OdinHinge")){
			builderHelper.deleteModule(selectedModule);			
			colorsComponents.add(Color.WHITE); colorsComponents.add(Color.WHITE);colorsComponents.add(Color.WHITE);			
			colorsConectors.add(Color.WHITE); colorsConectors.add(Color.WHITE);
			addNewModule(new ModulePosition("newBattery","OdinBattery",modulePosition,moduleRotation),colorsComponents,colorsConectors);
		}else if (selectedModuleType.equalsIgnoreCase("OdinBattery")){
			builderHelper.deleteModule(selectedModule);			
			colorsComponents.add(Color.BLACK); colorsComponents.add(Color.WHITE);colorsComponents.add(Color.WHITE);			
			colorsConectors.add(Color.WHITE); colorsConectors.add(Color.WHITE);
			addNewModule(new ModulePosition("newSpring","OdinSpring",modulePosition,moduleRotation),colorsComponents,colorsConectors);			
		}
//		PROBLEM java.lang.Error: Illegal module type: OdinTubeRobotType:OdinTube
		/*else if (selectedModuleType.equalsIgnoreCase("OdinSpring")){
			builderHelper.deleteModule(selectedModule);			
			colorsComponents.add(Color.WHITE); colorsComponents.add(Color.WHITE);colorsComponents.add(Color.WHITE);			
			colorsConectors.add(Color.WHITE); colorsConectors.add(Color.WHITE);
			addNewModuleOneMore(new ModulePosition("newTube","OdinTube",modulePosition,moduleRotation),colorsComponents,colorsConectors);
		}*/else if (selectedModuleType.equalsIgnoreCase("OdinSpring")){
			builderHelper.deleteModule(selectedModule);
			colorsComponents.add(Color.WHITE); colorsComponents.add(Color.BLUE);colorsComponents.add(Color.WHITE); 	colorsComponents.add(Color.WHITE);			
			colorsConectors.add(Color.WHITE); colorsConectors.add(Color.WHITE);
			addNewModule(new ModulePosition("newWheel","OdinWheel",modulePosition,moduleRotation),colorsComponents,colorsConectors);
		}else if (selectedModuleType.equalsIgnoreCase("OdinWheel")){
			builderHelper.deleteModule(selectedModule);			
			colorsComponents.add(Color.RED); colorsComponents.add(Color.BLUE);colorsComponents.add(Color.RED); 	colorsComponents.add(Color.BLUE);			
			colorsConectors.add(Color.WHITE); colorsConectors.add(Color.WHITE);
			addNewModule(new ModulePosition("newMuscle","OdinMuscle",modulePosition,moduleRotation),colorsComponents,colorsConectors);
		}
	}

	/**
	 * Adds and returns newModule in simulation environment, which is the copy of selected module.
	 * Meaning that the module will be added at the same position, with the same rotation  and with the same colours, as selected module.	 
	 * @param selectedModule,the module selected in simulation environment.
	 * @param moduleType, the type of the module, like for example: ATRON, MTRAN and so on.
	 * @return newModule,the module which is the copy of selected module
	 */
	private Module addNewCopyModule(Module selectedModule, String moduleType){		
		VectorDescription position = selectedModule.getPhysics().get(0).getPosition();       
		RotationDescription rotation = selectedModule.getPhysics().get(0).getRotation();
		List<Color> colorsComponents = selectedModule.getColorList();			
		ArrayList<Color> colorsConnectors = getColorsConnectors(selectedModule);
		String selectedModuleName = selectedModule.getProperty("name");
		ModulePosition modulePosition;
		if (moduleType.equalsIgnoreCase("ATRON")){

			modulePosition = new ModulePosition(selectedModuleName+ BuilderHelper.getRandomInt(),"ATRON",position,rotation);
		}else {
			modulePosition = new ModulePosition(selectedModuleName+ BuilderHelper.getRandomInt(),moduleType,position,rotation);
		}
		Module newModule = this.simulation.createModule(modulePosition,true);				
		newModule.setColorList(colorsComponents);		
		setColorsConnectors(newModule,colorsConnectors);
		return newModule;
	}

	/**
	 * Adds and returns newModule at specific position and with specific rotation. 
	 * @param modulePosition, global starting position of the module and annotations.
	 * @param colorsComponents, the colours of module components.
	 * @param colorsConnectors, the colours of module connectors.
	 * @return newModule,newly added module with specific position and rotation. 
	 */
	private Module addNewModule(ModulePosition modulePosition, List<Color> colorsComponents,ArrayList<Color> colorsConnectors){
		Module newModule = this.simulation.createModule(modulePosition,true);			
		newModule.setColorList(colorsComponents);		
		setColorsConnectors(newModule,colorsConnectors);
		return newModule;
	}

	/**
	 * Returns the ArrayList, containing the colours of connectors of selected module.
	 * @param selectedModule, the module selected in simulation environment.
	 * @return the ArrayList of colours of connectors on the module
	 */
	private ArrayList<Color> getColorsConnectors(Module selectedModule){
		ArrayList<Color> colorsConnectors = new ArrayList<Color>();
		int nrConnectors = selectedModule.getConnectors().size();	        
		for (int connector=0;connector<nrConnectors; connector++){        	
			Color connectorColor = selectedModule.getConnectors().get(connector).getColor();
			colorsConnectors.add(connectorColor);
		}
		return colorsConnectors; 
	}

	/**
	 * Colours the connectors of the module, according to the colours in ArrayList. Precondition should be that the number of connectors equals to the number of colours in ArrayList.  
	 * @param newModule, the module in simulation environment.
	 * @param colorsConnectors, the colours to assign to connectors. Each index in ArrayList is colour and at the same time equals to connector number 
	 */
	private void setColorsConnectors(Module newModule, ArrayList<Color> colorsConnectors){
		int nrConnectors = newModule.getConnectors().size();
		if (nrConnectors!=colorsConnectors.size()){	
			throw new Error("The number of connnectors on module is not matching the number of colors in ArrayList!");			
		}else{
			for (int connector=0;connector<nrConnectors; connector++){
				newModule.getConnectors().get(connector).setColor(colorsConnectors.get(connector));	        	
			}
		}
	}	
}
