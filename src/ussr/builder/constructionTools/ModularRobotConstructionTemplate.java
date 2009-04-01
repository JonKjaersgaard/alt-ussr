package ussr.builder.constructionTools;

import ussr.description.geometry.RotationDescription;
import ussr.description.geometry.VectorDescription;
import ussr.model.Module;
import ussr.physics.jme.JMEModuleComponent;
import ussr.physics.jme.JMESimulation;

import com.jme.math.Quaternion;
import com.jme.math.Vector3f;
import com.jmex.physics.DynamicPhysicsNode;
import ussr.builder.BuilderHelper;

/**
 * The main responsibility of this class  is to support children classes with common methods for 
 * construction of the morphology of modular robots. Moreover act as abstract parent class in 
 * TEMPLATE METHOD pattern and define the TEMPLATE methods as well as "Primitive operations" 
 * (algorithms) common to children classes. 
 * @author Konstantinas 
 */
//FIXME 1) UPDATE COMMENTS
public abstract class ModularRobotConstructionTemplate implements ConstructionTemplate {	

	/**
	 * The physical simulation
	 */	 
	public JMESimulation simulation;	

	/**
	 * Mathematical constant PI
	 */
	public final static float pi = (float)Math.PI;

	/**
	 * The array of objects for string static and dynamic information for adding the new movable component(module) with respect to selected module(component).  
	 */
	public ModuleMapEntryHelper[] moduleMap;

	/**
	 * Defines template methods as well as common methods for construction of different modular
	 * robot's morphologies.
	 * @param simulation, the physical simulation.
	 */
	public ModularRobotConstructionTemplate(JMESimulation simulation){
		this.simulation = simulation;
	}

	/**
	 * Moves newMovableModule according(respectively) to selected module preconditions,
	 * like connector number, initial rotation of selected module, and so on.
	 * This operation is TEMPLATE method. Operation means that it should be executed on the object.
	 * @param connectorNr, the connector number on selected module.
	 * @param selectedModule,  the module selected in simulation environment.
	 * @param newMovableModule, the new module to move respectively to selected one. 
	 */	
	public void moveModuleAccording(int connectorNr, Module selectedModule, Module newMovableModule,boolean loopFlag){
		/*Identify what type of module it is*/
		String selectedModuleType =selectedModule.getProperty(BuilderHelper.getModuleTypeKey());

		if (selectedModuleType.contains("Odin")){//if it is heterogeneous like Odin
			moveModuleComponentAccording(connectorNr,selectedModule, (JMEModuleComponent) newMovableModule.getComponent(0), null, loopFlag);
		}else{// else if it is homogeneous like ATRON and MTRAN

			/*Amount of components constituting selectedModule*/
			int amountComponents =selectedModule.getNumberOfComponents();		

			/*Loop through each component of selected module and move the component of new movable module 
			 * with respect to selected component of the module. ATRON module has two components(two hemispheres).
			 * MTRAN has three(blue and red half-cylinder boxes and black link)*/
			for (int component=0; component<amountComponents;component++){

				/* The current component of selected module*/
				JMEModuleComponent currentComponent = (JMEModuleComponent)selectedModule.getComponent(component);
				VectorDescription positionComponent = currentComponent.getPosition();
				Quaternion  rotationQuatComponent = currentComponent.getRotation().getRotation();

				/*Get x,y,z of selected module component position. In USSR x axis is horizontal,
				 * y axis is perpendicular to horizontal axis, z is facing the user*/
				float x = positionComponent.getX();
				float y = positionComponent.getY();
				float z = positionComponent.getZ();                            				

				/*Update the array of objects storing information about all available initial rotations of ATRON or MTRAN modules
				 * and  rotations together with positions of newly added modules with respect to specific connector*/
				updateModuleMap(x,y,z);			

				/*The current component of new movable module */
				JMEModuleComponent movableModuleComponent = (JMEModuleComponent)newMovableModule.getComponent(component);

				moveModuleComponentAccording(connectorNr,selectedModule, movableModuleComponent, rotationQuatComponent,loopFlag);

			}  	
		}
	}

	/**
	 * Updates the array of objects containing the information about all available initial rotations 
	 * of modular robot module and  rotations together with positions of newly added module with respect to specific 
	 * connector number on selected module and selected module itself.
	 * This method is so-called "Primitive operation" for above TEMPLATE method, called "moveModuleAccording(int connectorNr, Module selectedModule, Module newMovableModule)".	 
	 * @param x, the amount of x coordinate of current position of component.
	 * @param y, the amount of y coordinate of current position of component.
	 * @param z, the amount of z coordinate of current position of component. 
	 */
	public abstract void updateModuleMap(float x, float y, float z);

	/**
	 * Moves newMovableModuleComponent according(respectively) to selected module preconditions,
	 * like connector number, initial rotation of selected module component, and so on.
	 * This method is so-called "Primitive operation" for above TEMPLATE method,called "moveModuleAccording(int connectorNr, Module selectedModule, Module newMovableModule)". 
	 * @param connectorNr, the connector number on selected module.
	 * @param selectedModule,  the module selected in simulation environment.
	 * @param movableModuleComponent, the new module component to move respectively to selected one.
	 * @param rotationQuatComponent, the rotation of current component of selected module.	 
	 */	
	public abstract void moveModuleComponentAccording(int connectorNr,Module selectedModule, JMEModuleComponent movableModuleComponent,Quaternion rotationQuatComponent,boolean loopFlag);

	/**
	 * Rotates selected  module according to its initial rotation with opposite rotation.
	 * This operation is TEMPLATE method. Operation means that it should be executed on the object.	
	 * @param selectedModule,the module selected in simulation environment.	 
	 */	
	public void rotateModuleOpposite(Module selectedModule){
		/*Amount of components constituting selectedModule*/
		int amountComponents =selectedModule.getNumberOfComponents();		

		/*Loop through each component of selected module and rotate it with opposite rotation.*/
		for (int component=0; component<amountComponents;component++){
			/* The current component of selected module*/
			JMEModuleComponent selectedModuleComponent= (JMEModuleComponent)selectedModule.getComponent(component);			
			Quaternion  rotationQComponent = selectedModule.getComponent(component).getRotation().getRotation();			
			rotateComponentOpposite(selectedModuleComponent,rotationQComponent);				
		}
	}	

	/**
	 * Rotates selected  module component according to its initial rotation with opposite rotation.
	 * This method is so-called "Primitive operation" for above TEMPLATE method, called "rotateModuleOpposite(Module selectedModule)". 	
	 * @param selectedModuleComponent,the module component selected in simulation environment.
	 * @param rotationQComponent,the rotation of selected component.	 
	 */	
	public abstract void rotateComponentOpposite(JMEModuleComponent selectedModuleComponent,Quaternion  rotationQComponent);

	/**
	 * Rotates selected  module with standard rotations, passed as a string.
	 * This operation is TEMPLATE method. Operation means that it should be executed on the object.
	 * @param selectedModule,the module selected in simulation environment.
	 * @param rotationName,the name of standard(specific) rotation of the module.	 
	 */
	public void rotateModuleSpecifically(Module selectedModule,String rotationName){
		/*Amount of components constituting selectedModule*/
		int amountComponents =selectedModule.getNumberOfComponents();		

		/*Loop through each component of selected module and rotate it with selected standard rotation. 
		 ATRON module has two components(two hemispheres).*/
		for (int component=0; component<amountComponents;component++){
			/* The current component of selected module*/
			JMEModuleComponent selectedModuleComponent= (JMEModuleComponent)selectedModule.getComponent(component);
			rotateComponentSpecifically(selectedModuleComponent, rotationName);				
		}
	}

	/**
	 * Rotates selected module component with standard rotations, passed as a string.
	 * This method is so-called "Primitive Operation" for above TEMPLATE method, called "rotateModuleSpecifically(Module selectedModule,String rotationName)". 
	 * @param selectedModuleComponent,the module component selected in simulation environment.	 
	 * @param rotationName,the name of standard(specific) rotation of the module.	 
	 */	
	public abstract void rotateComponentSpecifically(JMEModuleComponent selectedModuleComponent, String rotationName);	

	/**
	 * Additional method for implementing unique properties of modular robots modules. Like for example
	 * MTRAN has several more specific rotations, which are implemented to make the construction
	 * more flexible.
	 * This operation is TEMPLATE method. Operation means that it should be executed on the object.
	 * @param selectedModule,the module selected in simulation environment.		
	 */	
	public void variateModuleProperties(Module selectedModule){
		/*Amount of components constituting selectedModule*/
		int amountComponents =selectedModule.getNumberOfComponents();
		for (int component=0; component<amountComponents;component++){
			/* The current component of selected module*/
			JMEModuleComponent selectedModuleComponent= (JMEModuleComponent)selectedModule.getComponent(component);			
			Quaternion  rotationQComponent = selectedModule.getComponent(component).getRotation().getRotation();

			variateComponentProperties( selectedModuleComponent,  rotationQComponent);
		}		
	}

	/**
	 * Additional method for implementing unique properties of modular robots  components of the modules. Like for example
	 * MTRAN has several more specific rotations, which are implemented to make the construction
	 * more flexible.
	 * This method is so-called "Primitive operation" for above TEMPLATE method, called "variateModuleProperties(Module selectedModule)". 	
	 * @param selectedModuleComponent,the module component selected in simulation environment.
	 * @param rotationQComponent,the rotation of selected component.	 
	 */
	public abstract void variateComponentProperties(JMEModuleComponent selectedModuleComponent,Quaternion  rotationQComponent);	

	/**
	 * Moves movable component of the module to new local  position and assigns new local rotation to the same component.
	 * This method is common to children classes.
	 * @param movableModuleComponent, the current component of the module to move. 
	 * @param newRotation, the new local rotation to assign to the component.
	 * @param newPosition,the new local position to translate the component to.
	 */
	public void moveModuleComponent(JMEModuleComponent movableModuleComponent,RotationDescription  newRotation, Vector3f newPosition){
		for(DynamicPhysicsNode part: movableModuleComponent.getNodes()){ 
			part.setLocalRotation(newRotation.getRotation());											
			part.setLocalTranslation(newPosition);
		}
	}

	/**
	 * Rotates the component of a module with specific rotation quaternion.
	 * This method is common to children classes.
	 * @param moduleComponent, the component of the module.
	 * @param quaternion, the rotation quaternion to rotate the component with.
	 */
	public void rotateModuleComponent(JMEModuleComponent moduleComponent, Quaternion quaternion){
		for(DynamicPhysicsNode part: moduleComponent.getNodes()){
			part.setLocalRotation(quaternion);
		}
	}

	/**
	 * Checks is component(module) already exists at specified position. Also checks in the interval of tolerance around this position for
	 * x, y and z coordinates.
	 * This method is common to children classes.   
	 * @param componentPosition, the position of the component to check if there is the module(component) already.
	 * @param tolerance, the tolerance for interval to search in (for x,y,z coordinates).  
	 * @return true, if the module(component)already exists at this position.
	 */
	public boolean componentExitst(Vector3f componentPosition, float tolerance){		
		int amountModules = simulation.getModules().size();
		/*For each module in simulation get its components and check if it is already at the modulePosition*/
		for (int module = 0; module<amountModules; module++ ){
			Module currentModule = simulation.getModules().get(module);
			int amountComponents = currentModule.getNumberOfComponents(); 
			Vector3f curretComponentPosition = null;
			/*Get the position of the last component of the module*/
//FIXME PROBLEM WITH MTRAN EXISTS (SOMETIMES ONE OVERLAPS)
			for (int component = 0; component<amountComponents; component++ ){
				curretComponentPosition= currentModule.getComponent(component).getPosition().getVector();
			}            
			/*Check exact position and in interval */
			if (curretComponentPosition.x == componentPosition.x ||curretComponentPosition.x < componentPosition.x+tolerance && curretComponentPosition.x > componentPosition.x-tolerance){								
				if (curretComponentPosition.y ==componentPosition.y||curretComponentPosition.y < componentPosition.y+tolerance && curretComponentPosition.y > componentPosition.y-tolerance){					//System.out.println("IN2");
					if (curretComponentPosition.z ==componentPosition.z||curretComponentPosition.z < componentPosition.z+tolerance && curretComponentPosition.z > componentPosition.z-tolerance){					
						return true;
					}					
				}
			}
		}
		return false;		
	}

	/**
	 * Moves the component of module to new local position.
	 * This method is common to children classes, however currently is used only for MTRAN.
	 * @param moduleComponent, the component of the module.
	 * @param newPosition, the new local position to assign the component to.
	 */
//	TODO SOFAR IS USED ONLY IN MTRAN CASE
	public void translateModuleComponent(JMEModuleComponent moduleComponent, Vector3f newPosition){
		for(DynamicPhysicsNode part: moduleComponent.getNodes()){
			part.setLocalTranslation(newPosition);
		}		
	}
	/**
	 * Swaps the positions of two components.
	 * This method is common to children classes, however currently is used only for MTRAN.
	 * @param selectedModule, the module selected in simulation environment.
	 * @param firstComponentIndex, the index of first component in the module components array.
	 * @param secondComponentIndex, the index of second component in the module components array.
	 */
//	TODO SOFAR IS USED ONLY IN MTRAN CASE
	public void swapComponentsPositions(Module selectedModule,int firstComponentIndex, int secondComponentIndex){
		/*Get the components of the module to swap*/
		JMEModuleComponent firstComponent =  (JMEModuleComponent)selectedModule.getComponent(firstComponentIndex);
		JMEModuleComponent secondComponent =  (JMEModuleComponent)selectedModule.getComponent(secondComponentIndex);

		/*Get positions of above components*/
		VectorDescription positionFirstComponent = firstComponent.getPosition();//red half-cylinder box		
		VectorDescription positionSecondComponent = secondComponent.getPosition();// blue half-cylinder box

		/*Swap the positioning of components*/
		translateModuleComponent(firstComponent, new Vector3f(positionSecondComponent.getX(),positionSecondComponent.getY(),positionSecondComponent.getZ()));
		translateModuleComponent(secondComponent, new Vector3f(positionFirstComponent.getX(),positionFirstComponent.getY(),positionFirstComponent.getZ()));	
	}	
}
