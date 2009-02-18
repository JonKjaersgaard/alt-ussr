package ussr.builder.construction;

import java.awt.Color;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JOptionPane;

import com.jmex.physics.DynamicPhysicsNode;

import ussr.description.geometry.RotationDescription;
import ussr.description.geometry.VectorDescription;
import ussr.description.setup.ModulePosition;
import ussr.model.Module;
import ussr.physics.jme.JMEModuleComponent;
import ussr.physics.jme.JMESimulation;

/**
 * The main responsibility of this class is to act as Context class in Strategy pattern.
 * Moreover define the methods common for construction of modular robots morphologies in more module oriented way. 
 * @author Konstantinas
 *
 */
public class CommonOperationsStrategy implements  SelectOperationsStrategy{

	/** 
	 * The physical simulation
	 */
	private JMESimulation simulation;	

	private ConstructionStrategy construction;

	/**
	 * Constructor
	 * @param simulation,the physical simulation
	 */
	public  CommonOperationsStrategy(JMESimulation simulation){
		this.simulation = simulation;
	}

/*	public void addNewModuleOnConnector(ModuleHandler handler, int connectorNr, int selectedModuleID) {
		Module selectedModule = simulation.getModules().get(selectedModuleID);
		handler.preAddModuleOnConnectorAction();
			Create the copy of selected module and define it as new movable module
			Module newMovableModule = addNewCopyModule(simulation,selectedModuleID,handler.getName());		
			construction =  handler.newConstruction();
			construction.moveModuleAccording(connectorNr, selectedModule, newMovableModule);		

	}*/
	
	@Override
	public void addNewModuleOnConnector(String modularRobotName, int connectorNr, int selectedModuleID){

		/*Get selected module in simulation environment*/ 
		Module selectedModule = simulation.getModules().get(selectedModuleID);
		if (modularRobotName.equalsIgnoreCase("ATRON")){
			/*Create the copy of selected module and define it as new movable module*/
			Module newMovableModule = addNewCopyModule(simulation,selectedModuleID,"default");		
			construction =  new ATRONConstructionStrategy();		
			construction.moveModuleAccording(connectorNr, selectedModule, newMovableModule);
			
		}else if (modularRobotName.equalsIgnoreCase("MTRAN")){
			Module newMovableModule = addNewCopyModule(simulation,selectedModuleID,"MTRAN");
			construction =  new MTRANConstructionStrategy();		
			construction.moveModuleAccording(connectorNr, selectedModule, newMovableModule);
		}else if(modularRobotName.equalsIgnoreCase("Odin")){

			String selectedModuleType =selectedModule.getProperty("ussr.module.type");
			if (selectedModuleType.equalsIgnoreCase("OdinBall")){
				
				//SOLUTION NR1
				List<Color> colorsComponents = new LinkedList<Color>();
				colorsComponents.add(Color.RED); colorsComponents.add(Color.BLUE);				
				ArrayList<Color> colorsConectors = new ArrayList<Color>();
				colorsConectors.add(Color.WHITE); colorsConectors.add(Color.WHITE);				
				Module newMovableModule = addNewModule(simulation,"newMuscle","OdinMuscle",colorsComponents,colorsConectors);
				construction =  new OdinConstructionStrategy();		
				construction.moveModuleAccording(connectorNr, selectedModule, newMovableModule);
				
				//SOLUTION NR2
				
				/*List<Color> colorsComponents = new LinkedList<Color>();
				colorsComponents.add(Color.RED); colorsComponents.add(Color.BLUE);				
				ArrayList<Color> colorsConectors = new ArrayList<Color>();
				colorsConectors.add(Color.WHITE); colorsConectors.add(Color.WHITE);				
				Module newMovableModule = addNewModule(simulation,"newMuscle","OdinMuscle",colorsComponents,colorsConectors);
				construction =  new OdinConstructionStrategy();		
				construction.moveModuleAccording(connectorNr, selectedModule, newMovableModule);
				
				
				int amountModules = simulation.getModules().size();
				Module selectedModule1 = simulation.getModules().get(amountModules-1);//Last added module;
				List<Color> colorsComponents1 = new LinkedList<Color>();
				colorsComponents1.add(Color.RED); 				
				ArrayList<Color> colorsConectors2 = new ArrayList<Color>();
				for (int connector=0;connector<12;connector++){
					colorsConectors2.add(Color.WHITE);
				}
				Module newMovableModule1 = addNewModule(simulation,"newBall","OdinBall",colorsComponents1,colorsConectors2);
				construction =  new OdinConstructionStrategy();		
				construction.moveModuleAccording(0, selectedModule1, newMovableModule1);*/
				
				
				
			}else if(selectedModuleType.equalsIgnoreCase("OdinMuscle")){
				List<Color> colorsComponents = new LinkedList<Color>();
				colorsComponents.add(Color.RED); 				
				ArrayList<Color> colorsConectors = new ArrayList<Color>();
				for (int connector=0;connector<12;connector++){
					colorsConectors.add(Color.WHITE);
				}
				Module newMovableModule = addNewModule(simulation,"newBall","OdinBall",colorsComponents,colorsConectors);
				construction =  new OdinConstructionStrategy();		
				construction.moveModuleAccording(connectorNr, selectedModule, newMovableModule);				
			}
		}else throw new Error("This type of robot is not supported yet");
	}

	@Override
	public void addModulesOnAllConnectors(String modularRobotName,int selectedModuleID) {
		Module selectedModule = simulation.getModules().get(selectedModuleID);
		int amountConnectors = selectedModule.getConnectors().size();
		for (int connectorNr=0; connectorNr<amountConnectors;connectorNr++){	
			if (modularRobotName.equalsIgnoreCase("ATRON")){
				Module newMovableModule = addNewCopyModule(simulation,selectedModuleID,"default");
				construction =  new ATRONConstructionStrategy();
				construction.moveModuleAccording(connectorNr, selectedModule,newMovableModule);
			}else if (modularRobotName.equalsIgnoreCase("MTRAN")){
				Module newMovableModule = addNewCopyModule(simulation,selectedModuleID,"MTRAN");
				construction =  new MTRANConstructionStrategy();
				construction.moveModuleAccording(connectorNr, selectedModule,newMovableModule);
			}else if(modularRobotName.equalsIgnoreCase("Odin")){

				String selectedModuleType =selectedModule.getProperty("ussr.module.type");
				if (selectedModuleType.equalsIgnoreCase("OdinBall")){
					// SOLUTION NR1
					List<Color> colorsComponents = new LinkedList<Color>();
					colorsComponents.add(Color.RED); colorsComponents.add(Color.BLUE);				
					ArrayList<Color> colorsConectors = new ArrayList<Color>();
					colorsConectors.add(Color.WHITE); colorsConectors.add(Color.WHITE);				
					Module newMovableModule = addNewModule(simulation,"newMuscle","OdinMuscle",colorsComponents,colorsConectors);
					construction =  new OdinConstructionStrategy();		
					construction.moveModuleAccording(connectorNr, selectedModule, newMovableModule);
					
					// SOLUTION NR2 (Repeats)
					/*int amountModules = simulation.getModules().size();
					Module selectedModule1 = simulation.getModules().get(amountModules-1);//Last added module;
					List<Color> colorsComponents1 = new LinkedList<Color>();
					colorsComponents1.add(Color.RED); 				
					ArrayList<Color> colorsConectors2 = new ArrayList<Color>();
					for (int connector=0;connector<12;connector++){
						colorsConectors2.add(Color.WHITE);
					}
					Module newMovableModule1 = addNewModule(simulation,"newBall","OdinBall",colorsComponents1,colorsConectors2);
					construction =  new OdinConstructionStrategy();		
					construction.moveModuleAccording(0, selectedModule1, newMovableModule1);*/
					
				}else if(selectedModuleType.equalsIgnoreCase("OdinMuscle")){
					List<Color> colorsComponents = new LinkedList<Color>();
					colorsComponents.add(Color.RED); 				
					ArrayList<Color> colorsConectors = new ArrayList<Color>();
					for (int connector=0;connector<12;connector++){
						colorsConectors.add(Color.WHITE);
					}
					Module newMovableModule = addNewModule(simulation,"newMuscle","OdinBall",colorsComponents,colorsConectors);
					construction =  new OdinConstructionStrategy();		
					construction.moveModuleAccording(connectorNr, selectedModule, newMovableModule);				
				}
			}else throw new Error("This type of robot is not supported yet");
		}	
	}


	public void moveModuleOnNextConnector(String modularRobotName,int connectorNr,int selectedModuleID){

	}
	
// IS IT THE BEST PLACE FOR YOU?
	public void replaceOdinModules(int selectedModuleID){
		Module selectedModule = simulation.getModules().get(selectedModuleID);
		String selectedModuleType =	selectedModule.getProperty("ussr.module.type");
		if (selectedModuleType.equalsIgnoreCase("OdinMuscle")){
			VectorDescription modulePosition = selectedModule.getPhysics().get(0).getPosition();
			RotationDescription moduleRotation = selectedModule.getPhysics().get(0).getRotation();
			int amountComponents = selectedModule.getNumberOfComponents();
			for (int component=0; component<amountComponents;component++){
				JMEModuleComponent moduleComponent =(JMEModuleComponent) selectedModule.getComponent(component);
				for(DynamicPhysicsNode part: moduleComponent.getNodes()){
					part.detachAllChildren();				
				}
			}
			selectedModule.setProperty("ussr.module.deletionFlag", "deleted");//Flag to indicate that the information about module should not be saved in XML (Hack)
			
			List<Color> colorsComponents = new LinkedList<Color>();
			colorsComponents.add(Color.WHITE); colorsComponents.add(Color.WHITE);colorsComponents.add(Color.RED); colorsComponents.add(Color.WHITE); colorsComponents.add(Color.WHITE);  			
			ArrayList<Color> colorsConectors = new ArrayList<Color>();
			colorsConectors.add(Color.WHITE); colorsConectors.add(Color.WHITE);
			addNewModuleOneMore(simulation,new ModulePosition("newHinge","OdinHinge",modulePosition,moduleRotation),colorsComponents,colorsConectors);			
		}else if (selectedModuleType.equalsIgnoreCase("OdinHinge")){
			VectorDescription modulePosition = selectedModule.getPhysics().get(0).getPosition();
			RotationDescription moduleRotation = selectedModule.getPhysics().get(0).getRotation();
			int amountComponents = selectedModule.getNumberOfComponents();
			for (int component=0; component<amountComponents;component++){
				JMEModuleComponent moduleComponent =(JMEModuleComponent) selectedModule.getComponent(component);
				for(DynamicPhysicsNode part: moduleComponent.getNodes()){
					part.detachAllChildren();				
				}
			}
			selectedModule.setProperty("ussr.module.deletionFlag", "deleted");//Flag to indicate that the information about module should not be saved in XML (Hack)		
			
			List<Color> colorsComponents = new LinkedList<Color>();
			colorsComponents.add(Color.WHITE); colorsComponents.add(Color.WHITE);colorsComponents.add(Color.WHITE); 			
			ArrayList<Color> colorsConectors = new ArrayList<Color>();
			colorsConectors.add(Color.WHITE); colorsConectors.add(Color.WHITE);
			addNewModuleOneMore(simulation,new ModulePosition("newBattery","OdinBattery",modulePosition,moduleRotation),colorsComponents,colorsConectors);			
			
		}else if (selectedModuleType.equalsIgnoreCase("OdinBattery")){
			VectorDescription modulePosition = selectedModule.getPhysics().get(0).getPosition();
			RotationDescription moduleRotation = selectedModule.getPhysics().get(0).getRotation();
			int amountComponents = selectedModule.getNumberOfComponents();
			for (int component=0; component<amountComponents;component++){
				JMEModuleComponent moduleComponent =(JMEModuleComponent) selectedModule.getComponent(component);
				for(DynamicPhysicsNode part: moduleComponent.getNodes()){
					part.detachAllChildren();				
				}
			}
			selectedModule.setProperty("ussr.module.deletionFlag", "deleted");//Flag to indicate that the information about module should not be saved in XML (Hack)		
			List<Color> colorsComponents = new LinkedList<Color>();
			colorsComponents.add(Color.BLACK); colorsComponents.add(Color.WHITE);colorsComponents.add(Color.WHITE); 	 			
			ArrayList<Color> colorsConectors = new ArrayList<Color>();
			colorsConectors.add(Color.WHITE); colorsConectors.add(Color.WHITE);
			addNewModuleOneMore(simulation,new ModulePosition("newSpring","OdinSpring",modulePosition,moduleRotation),colorsComponents,colorsConectors);			
			
		}
		//PROBLEM java.lang.Error: Illegal module type: OdinTubeRobotType:OdinTube
		/*else if (selectedModuleType.equalsIgnoreCase("OdinSpring")){
			VectorDescription modulePosition = selectedModule.getPhysics().get(0).getPosition();
			RotationDescription moduleRotation = selectedModule.getPhysics().get(0).getRotation();
			int amountComponents = selectedModule.getNumberOfComponents();
			for (int component=0; component<amountComponents;component++){
				JMEModuleComponent moduleComponent =(JMEModuleComponent) selectedModule.getComponent(component);
				for(DynamicPhysicsNode part: moduleComponent.getNodes()){
					part.detachAllChildren();				
				}
			}
			List<Color> colorsComponents = new LinkedList<Color>();
			colorsComponents.add(Color.WHITE); colorsComponents.add(Color.WHITE);colorsComponents.add(Color.WHITE); 	 			
			ArrayList<Color> colorsConectors = new ArrayList<Color>();
			colorsConectors.add(Color.WHITE); colorsConectors.add(Color.WHITE);
			addNewModuleOneMore(simulation,new ModulePosition("newTube","OdinTube",modulePosition,moduleRotation),colorsComponents,colorsConectors);			
			
		}*/
	
		else if (selectedModuleType.equalsIgnoreCase("OdinSpring")){
			VectorDescription modulePosition = selectedModule.getPhysics().get(0).getPosition();
			RotationDescription moduleRotation = selectedModule.getPhysics().get(0).getRotation();
			int amountComponents = selectedModule.getNumberOfComponents();
			for (int component=0; component<amountComponents;component++){
				JMEModuleComponent moduleComponent =(JMEModuleComponent) selectedModule.getComponent(component);
				for(DynamicPhysicsNode part: moduleComponent.getNodes()){
					part.detachAllChildren();				
				}
			}
			selectedModule.setProperty("ussr.module.deletionFlag", "deleted");//Flag to indicate that the information about module should not be saved in XML (Hack)		
			List<Color> colorsComponents = new LinkedList<Color>();
			colorsComponents.add(Color.WHITE); colorsComponents.add(Color.BLUE);colorsComponents.add(Color.WHITE); 	colorsComponents.add(Color.WHITE); 	 			
			ArrayList<Color> colorsConectors = new ArrayList<Color>();
			colorsConectors.add(Color.WHITE); colorsConectors.add(Color.WHITE);
			addNewModuleOneMore(simulation,new ModulePosition("newWheel","OdinWheel",modulePosition,moduleRotation),colorsComponents,colorsConectors);			
			
		}else if (selectedModuleType.equalsIgnoreCase("OdinWheel")){
			VectorDescription modulePosition = selectedModule.getPhysics().get(0).getPosition();
			RotationDescription moduleRotation = selectedModule.getPhysics().get(0).getRotation();
			int amountComponents = selectedModule.getNumberOfComponents();
			for (int component=0; component<amountComponents;component++){
				JMEModuleComponent moduleComponent =(JMEModuleComponent) selectedModule.getComponent(component);
				for(DynamicPhysicsNode part: moduleComponent.getNodes()){
					part.detachAllChildren();				
				}
			}
			selectedModule.setProperty("ussr.module.deletionFlag", "deleted");//Flag to indicate that the information about module should not be saved in XML (Hack)		
			List<Color> colorsComponents = new LinkedList<Color>();
			colorsComponents.add(Color.RED); colorsComponents.add(Color.BLUE);colorsComponents.add(Color.RED); 	colorsComponents.add(Color.BLUE); 	 			
			ArrayList<Color> colorsConectors = new ArrayList<Color>();
			colorsConectors.add(Color.WHITE); colorsConectors.add(Color.WHITE);
			addNewModuleOneMore(simulation,new ModulePosition("newMuscle","OdinMuscle",modulePosition,moduleRotation),colorsComponents,colorsConectors);			
			
		}

	}

	public void rotateModuleWithOppositeRotation(String modularRobotName,int selectedModuleID){
		Module selectedModule = simulation.getModules().get(selectedModuleID);
		if (modularRobotName.equalsIgnoreCase("ATRON")){
			ConstructionStrategy construction =  new ATRONConstructionStrategy();		
			construction.rotateOpposite(selectedModule);
		}else if (modularRobotName.equalsIgnoreCase("MTRAN")){
			ConstructionStrategy construction =  new MTRANConstructionStrategy();		
			construction.rotateOpposite(selectedModule);
		}else if (modularRobotName.equalsIgnoreCase("Odin")){
			ConstructionStrategy construction =  new OdinConstructionStrategy();		
			construction.rotateOpposite(selectedModule);
		}
	}

	public void rotateModuleStandardRotation(String modularRobotName,int selectedModuleID, String rotationName){
		Module selectedModule = simulation.getModules().get(selectedModuleID);
		if (modularRobotName.equalsIgnoreCase("ATRON")){
			ConstructionStrategy construction =  new ATRONConstructionStrategy();		
			construction.rotateSpecifically(selectedModule, rotationName);
		}else if(modularRobotName.equalsIgnoreCase("MTRAN")){
			ConstructionStrategy construction =  new MTRANConstructionStrategy();		
			construction.rotateSpecifically(selectedModule, rotationName);
		}
	}
	
	public void variateModule(String modularRobotName,int selectedModuleID){
		Module selectedModule = simulation.getModules().get(selectedModuleID);
		if (modularRobotName.equalsIgnoreCase("ATRON")){
			ConstructionStrategy construction =  new ATRONConstructionStrategy();		
			construction.variate(selectedModule);
		}else if(modularRobotName.equalsIgnoreCase("MTRAN")){			
			ConstructionStrategy construction =  new MTRANConstructionStrategy();		
			construction.variate(selectedModule);
		}else if(modularRobotName.equalsIgnoreCase("Odin")){
			replaceOdinModules(selectedModuleID);
		}
	}


	/**
	 * Adds and returns newModule in simulation environment, which is the copy of selected module.
	 * Meaning that the module will be added at the same position, with the same rotation  and with the same colors,as selected module
	 * @param simulation, the physical simulation.
	 * @param selectedModuleID,the global ID of the selected module in simulation environment
	 * @return newModule,which is the copy of selected module
	 */
	private Module addNewCopyModule(JMESimulation simulation, int selectedModuleID, String moduleType){

		Module selectedModule = simulation.getModules().get(selectedModuleID);
		VectorDescription position = selectedModule.getPhysics().get(0).getPosition();       
		RotationDescription rotation = selectedModule.getPhysics().get(0).getRotation();
		List<Color> colorsComponents = selectedModule.getColorList();
		//System.out.println("Colors of components:"+ colorsComponents.size());//For debugging		
		ArrayList<Color> colorsConnectors = getColorsConnectors(simulation,selectedModuleID);
		String selectedModuleName = selectedModule.getProperty("name");
		//System.out.println("Name of module selected in simulation environment:"+ selectedModuleName);//For debugging		

		ModulePosition modulePosition = new ModulePosition(selectedModuleName,moduleType,position,rotation);
		Module newModule = simulation.createModule(modulePosition,true);
		int newModuleID = newModule.getID();
		//FIXME WHEN ODIN IS CONSIDERED THEN THERE IS THE PROBLEM WITH COLORS
		simulation.getModules().get(newModuleID).setColorList(colorsComponents);        
		setColorsConnectors(simulation,newModuleID,colorsConnectors);
		return newModule;
	}


	private Module addNewModule(JMESimulation simulation, String moduleName, String moduleType, List<Color> colorsComponents,ArrayList<Color> colorsConnectors){

		VectorDescription position = new VectorDescription(0,0,0);
		RotationDescription rotation = new RotationDescription(0,0,0);
		ModulePosition modulePosition= new ModulePosition(moduleName,moduleType,position,rotation);
		Module newModule = simulation.createModule(modulePosition,true);			
		newModule.setColorList(colorsComponents);		
		setColorsConnectors(simulation,newModule.getID(),colorsConnectors);
		return newModule;
	}
	private Module addNewModuleOneMore(JMESimulation simulation, ModulePosition modulePosition, List<Color> colorsComponents,ArrayList<Color> colorsConnectors){
		Module newModule = simulation.createModule(modulePosition,true);			
		newModule.setColorList(colorsComponents);        
		setColorsConnectors(simulation,newModule.getID(),colorsConnectors);
		return newModule;
	}

	/**
	 * Gives the ArrayList, containing the colours of connectors of selected module
	 * @param simulation, the physical simulation.
	 * @param selectedModuleID, the global ID of the selected module in simulation
	 * @return the ArrayList of colours of connectors on the module
	 */
	private ArrayList<Color> getColorsConnectors(JMESimulation simulation, int selectedModuleID){

		ArrayList<Color> colorsConnectors = new ArrayList<Color>();
		int nrConnectors = simulation.getModules().get(selectedModuleID).getConnectors().size();	        
		for (int connector=0;connector<nrConnectors; connector++){        	
			Color connectorColor = simulation.getModules().get(selectedModuleID).getConnectors().get(connector).getColor();
			colorsConnectors.add(connectorColor);
		}
		return colorsConnectors; 
	}

	/**
	 * Colours the connectors of the module, according to the coulors in ArrayList. Precondition should be that the number of connectors equals to the number of colors in ArrayList  
	 * @param simulation, the physical simulation.
	 * @param moduleID, the global ID of the module in simulation.
	 * @param colorsConnectors, the colours to assign to connectors. Each index in ArrayList is color and at the same time equals to connector number 
	 */
	private void setColorsConnectors(JMESimulation simulation, int moduleID, ArrayList<Color> colorsConnectors){

		int nrConnectors = simulation.getModules().get(moduleID).getConnectors().size();
		if (nrConnectors!=colorsConnectors.size()){
			System.out.println("Warning: The number of connnectors on module is not matching the number of colors in ArrayList");//For Debugging
			JOptionPane.showMessageDialog(null, "The number of connnectors on module is not matching the number of colors in ArrayList!","Warning", JOptionPane.WARNING_MESSAGE);//For Debugging
		}else{
			for (int connector=0;connector<nrConnectors; connector++){
				simulation.getModules().get(moduleID).getConnectors().get(connector).setColor(colorsConnectors.get(connector));	        	
			}
		}
	}	

}
