package ussr.builder.pickers.atron;

import com.jme.math.Quaternion;
import com.jme.math.Vector3f;
import com.jme.scene.Spatial;
import com.jmex.physics.DynamicPhysicsNode;
import ussr.description.geometry.RotationDescription;
import ussr.description.geometry.VectorDescription;
import ussr.physics.jme.JMEModuleComponent;
import ussr.physics.jme.JMESimulation;
import ussr.physics.jme.pickers.CustomizedPicker;
import ussr.samples.atron.ATRON;

//TODO ENCHANCE CHECKING OF WHAT KIND OF MODULE IT IS (IS IT ATRON OR OTHER MODULE, method isAtron()). THIS SHOULD COVER THE CASE WHEN USER SELECTS WRONG PICKER FOR  A WRONG MODULE TYPE
/**
 * Parent class, defining the methods to handle the picking of the ATRON module in simulation environment
 * @author Konstantinas
 *
 */
public class AssembleATRONPicker extends CustomizedPicker {

	/** 
	 * The physical simulation
	 */
	public JMESimulation simulation;

	/**
	 * Constructor 
	 * @param simulation, the physical simulation
	 */
	public AssembleATRONPicker (JMESimulation simulation) {
		this.simulation = simulation;
	}	

	/* Passed to children of this class
	 * @see ussr.physics.jme.pickers.CustomizedPicker#pickTarget(com.jme.scene.Spatial)
	 */
	@Override
	protected void pickTarget(Spatial target) {	}

	/* Passed to children of this class
	 * @see ussr.physics.jme.pickers.CustomizedPicker#pickModuleComponent(ussr.physics.jme.JMEModuleComponent)
	 */
	@Override
	protected void pickModuleComponent(JMEModuleComponent component) {}

	/**
	 * Moves the ATRON module according to picked ATRON module preconditions, like rotation, connector and so on
	 * @param connectorNr, the connector number on ATRON module
	 * @param pickedModuleID, the ID of the module picked in simulation environment
	 * @param moduleToMoveID, the ID of the module existing in simulation and should be moved with respect to the picked ATRON module
	 */
	public void moveModuleAccording(int connectorNr, int pickedModuleID, int moduleToMoveID ){

		int amountComponents =simulation.getModules().get(pickedModuleID).getNumberOfComponents();

		for (int component=0; component<amountComponents;component++){

			// The component of picked module
			JMEModuleComponent pickedModuleComponent = (JMEModuleComponent)simulation.getModules().get(pickedModuleID).getComponent(component);
			VectorDescription position = pickedModuleComponent.getPosition();

			float x = position.getX();
			float y = position.getY();
			float z = position.getZ();                            				

			// Apply offset of 0,08, which is lattice distance between two ATRON modules 
			float xPlusOffset = x + ATRON.UNIT;
			float xMinusOffset = x - ATRON.UNIT;
			float yPlusOffset = y + ATRON.UNIT;
			float yMinusOffset = y - ATRON.UNIT;
			float zPlusOffset = z + ATRON.UNIT;
			float zMinusOffset = z - ATRON.UNIT;			

			Quaternion  rotationQuaterion = simulation.getModules().get(pickedModuleID).getComponent(component).getRotation().getRotation();
			//The component of  module to move
			JMEModuleComponent movableModuleComponent = (JMEModuleComponent)simulation.getModules().get(moduleToMoveID).getComponent(component);

			/* Check which connector it is and what is the rotation of the picked module, then move the movable module component accordingly.
			 * This is specific to design of ATRON module*/
			switch (connectorNr){
			case 0:
				if(rotationQuaterion.equals(ATRON.ROTATION_EW.getRotation())){
					moveModuleComponent(movableModuleComponent,ATRON.ROTATION_DU,new Vector3f(xMinusOffset,yPlusOffset,z));
				}else if (rotationQuaterion.equals(ATRON.ROTATION_WE.getRotation())){
					moveModuleComponent(movableModuleComponent,ATRON.ROTATION_DU,new Vector3f(xPlusOffset,yPlusOffset,z));                            						 
				}else if (rotationQuaterion.equals(ATRON.ROTATION_DU.getRotation())){
					moveModuleComponent(movableModuleComponent,ATRON.ROTATION_SN,new Vector3f(x,yMinusOffset,zMinusOffset));
				}else if (rotationQuaterion.equals(ATRON.ROTATION_UD.getRotation())){
					moveModuleComponent(movableModuleComponent,ATRON.ROTATION_SN,new Vector3f(x,yPlusOffset,zMinusOffset));
				}else if (rotationQuaterion.equals(ATRON.ROTATION_SN.getRotation())){
					moveModuleComponent(movableModuleComponent,ATRON.ROTATION_WE,new Vector3f(xMinusOffset,y,zPlusOffset));
				}else if (rotationQuaterion.equals(ATRON.ROTATION_NS.getRotation())){
					moveModuleComponent(movableModuleComponent,ATRON.ROTATION_WE,new Vector3f(xMinusOffset,y,zMinusOffset));
				}
				break;
			case 1:
				if(rotationQuaterion.equals(ATRON.ROTATION_EW.getRotation())){
					moveModuleComponent(movableModuleComponent,ATRON.ROTATION_NS,new Vector3f(xMinusOffset,y,zPlusOffset));
				}else if (rotationQuaterion.equals(ATRON.ROTATION_WE.getRotation())){
					moveModuleComponent(movableModuleComponent,ATRON.ROTATION_SN,new Vector3f(xPlusOffset,y,zMinusOffset));
				}else if (rotationQuaterion.equals(ATRON.ROTATION_DU.getRotation())){
					moveModuleComponent(movableModuleComponent,ATRON.ROTATION_WE,new Vector3f(xMinusOffset,yMinusOffset,z));
				}else if (rotationQuaterion.equals(ATRON.ROTATION_UD.getRotation())){
					moveModuleComponent(movableModuleComponent,ATRON.ROTATION_EW,new Vector3f(xPlusOffset,yPlusOffset,z));
				}else if (rotationQuaterion.equals(ATRON.ROTATION_SN.getRotation())){
					moveModuleComponent(movableModuleComponent,ATRON.ROTATION_DU,new Vector3f(x,yPlusOffset,zPlusOffset));
				}else if (rotationQuaterion.equals(ATRON.ROTATION_NS.getRotation())){
					moveModuleComponent(movableModuleComponent,ATRON.ROTATION_UD,new Vector3f(x,yMinusOffset,zMinusOffset));
				}
				break;
			case 2:
				if(rotationQuaterion.equals(ATRON.ROTATION_EW.getRotation())){
					moveModuleComponent(movableModuleComponent,ATRON.ROTATION_UD,new Vector3f(xMinusOffset,yMinusOffset,z));
				}else if (rotationQuaterion.equals(ATRON.ROTATION_WE.getRotation())){
					moveModuleComponent(movableModuleComponent,ATRON.ROTATION_UD,new Vector3f(xPlusOffset,yMinusOffset,z));
				}else if(rotationQuaterion.equals(ATRON.ROTATION_DU.getRotation())){
					moveModuleComponent(movableModuleComponent,ATRON.ROTATION_NS,new Vector3f(x,yMinusOffset,zPlusOffset));
				}else if(rotationQuaterion.equals(ATRON.ROTATION_UD.getRotation())){
					moveModuleComponent(movableModuleComponent,ATRON.ROTATION_NS,new Vector3f(x,yPlusOffset,zPlusOffset));
				}else if (rotationQuaterion.equals(ATRON.ROTATION_SN.getRotation())){
					moveModuleComponent(movableModuleComponent,ATRON.ROTATION_EW,new Vector3f(xPlusOffset,y,zPlusOffset));
				}else if (rotationQuaterion.equals(ATRON.ROTATION_NS.getRotation())){
					moveModuleComponent(movableModuleComponent,ATRON.ROTATION_EW,new Vector3f(xPlusOffset,y,zMinusOffset));
				}
				break;
			case 3:
				if(rotationQuaterion.equals(ATRON.ROTATION_EW.getRotation())){
					moveModuleComponent(movableModuleComponent,ATRON.ROTATION_SN,new Vector3f(xMinusOffset,y,zMinusOffset));
				}else if (rotationQuaterion.equals(ATRON.ROTATION_WE.getRotation())){
					moveModuleComponent(movableModuleComponent,ATRON.ROTATION_NS,new Vector3f(xPlusOffset,y,zPlusOffset));
				}else if(rotationQuaterion.equals(ATRON.ROTATION_DU.getRotation())){
					moveModuleComponent(movableModuleComponent,ATRON.ROTATION_EW,new Vector3f(xPlusOffset,yMinusOffset,z));
				}else if(rotationQuaterion.equals(ATRON.ROTATION_UD.getRotation())){
					moveModuleComponent(movableModuleComponent,ATRON.ROTATION_WE,new Vector3f(xMinusOffset,yPlusOffset,z));
				}else if (rotationQuaterion.equals(ATRON.ROTATION_SN.getRotation())){
					moveModuleComponent(movableModuleComponent,ATRON.ROTATION_UD,new Vector3f(x,yMinusOffset,zPlusOffset));
				}else if (rotationQuaterion.equals(ATRON.ROTATION_NS.getRotation())){
					moveModuleComponent(movableModuleComponent,ATRON.ROTATION_DU,new Vector3f(x,yPlusOffset,zMinusOffset));
				}
				break;
			case 4:
				if(rotationQuaterion.equals(ATRON.ROTATION_EW.getRotation())){
					moveModuleComponent(movableModuleComponent,ATRON.ROTATION_UD,new Vector3f(xPlusOffset,yPlusOffset,z));
				}else if (rotationQuaterion.equals(ATRON.ROTATION_WE.getRotation())){
					moveModuleComponent(movableModuleComponent,ATRON.ROTATION_UD,new Vector3f(xMinusOffset,yPlusOffset,z));
				}else if (rotationQuaterion.equals(ATRON.ROTATION_DU.getRotation())){
					moveModuleComponent(movableModuleComponent,ATRON.ROTATION_NS,new Vector3f(x,yPlusOffset,zMinusOffset));
				}else if (rotationQuaterion.equals(ATRON.ROTATION_UD.getRotation())){
					moveModuleComponent(movableModuleComponent,ATRON.ROTATION_NS,new Vector3f(x,yMinusOffset,zMinusOffset));
				}else if (rotationQuaterion.equals(ATRON.ROTATION_SN.getRotation())){
					moveModuleComponent(movableModuleComponent,ATRON.ROTATION_EW,new Vector3f(xMinusOffset,y,zMinusOffset));
				}else if (rotationQuaterion.equals(ATRON.ROTATION_NS.getRotation())){
					moveModuleComponent(movableModuleComponent,ATRON.ROTATION_EW,new Vector3f(xMinusOffset,y,zPlusOffset));
				}
				break;
			case 5:
				if(rotationQuaterion.equals(ATRON.ROTATION_EW.getRotation())){
					moveModuleComponent(movableModuleComponent,ATRON.ROTATION_SN,new Vector3f(xPlusOffset,y,zPlusOffset));
				}else if (rotationQuaterion.equals(ATRON.ROTATION_WE.getRotation())){
					moveModuleComponent(movableModuleComponent,ATRON.ROTATION_NS,new Vector3f(xMinusOffset,y,zMinusOffset));
				}else if (rotationQuaterion.equals(ATRON.ROTATION_DU.getRotation())){
					moveModuleComponent(movableModuleComponent,ATRON.ROTATION_EW,new Vector3f(xMinusOffset,yPlusOffset,z));
				}else if (rotationQuaterion.equals(ATRON.ROTATION_UD.getRotation())){
					moveModuleComponent(movableModuleComponent,ATRON.ROTATION_WE,new Vector3f(xPlusOffset,yMinusOffset,z));
				}else if (rotationQuaterion.equals(ATRON.ROTATION_SN.getRotation())){
					moveModuleComponent(movableModuleComponent,ATRON.ROTATION_UD,new Vector3f(x,yPlusOffset,zMinusOffset));
				}else if (rotationQuaterion.equals(ATRON.ROTATION_NS.getRotation())){
					moveModuleComponent(movableModuleComponent,ATRON.ROTATION_DU,new Vector3f(x,yMinusOffset,zPlusOffset));
				}
				break;
			case 6:
				if(rotationQuaterion.equals(ATRON.ROTATION_EW.getRotation())){
					moveModuleComponent(movableModuleComponent,ATRON.ROTATION_DU,new Vector3f(xPlusOffset,yMinusOffset,z));
				}else if (rotationQuaterion.equals(ATRON.ROTATION_WE.getRotation())){
					moveModuleComponent(movableModuleComponent,ATRON.ROTATION_DU,new Vector3f(xMinusOffset,yMinusOffset,z));
				}else if(rotationQuaterion.equals(ATRON.ROTATION_DU.getRotation())){
					moveModuleComponent(movableModuleComponent,ATRON.ROTATION_SN,new Vector3f(x,yPlusOffset,zPlusOffset));
				}else if(rotationQuaterion.equals(ATRON.ROTATION_UD.getRotation())){
					moveModuleComponent(movableModuleComponent,ATRON.ROTATION_SN,new Vector3f(x,yMinusOffset,zPlusOffset));
				}else if (rotationQuaterion.equals(ATRON.ROTATION_SN.getRotation())){
					moveModuleComponent(movableModuleComponent,ATRON.ROTATION_WE,new Vector3f(xPlusOffset,y,zMinusOffset));
				}else if (rotationQuaterion.equals(ATRON.ROTATION_NS.getRotation())){
					moveModuleComponent(movableModuleComponent,ATRON.ROTATION_WE,new Vector3f(xPlusOffset,y,zPlusOffset));
				}
				break;
			case 7:
				if(rotationQuaterion.equals(ATRON.ROTATION_EW.getRotation())){
					moveModuleComponent(movableModuleComponent,ATRON.ROTATION_NS,new Vector3f(xPlusOffset,y,zMinusOffset));
				}else if (rotationQuaterion.equals(ATRON.ROTATION_WE.getRotation())){
					moveModuleComponent(movableModuleComponent,ATRON.ROTATION_SN,new Vector3f(xMinusOffset,y,zPlusOffset));
				}else if(rotationQuaterion.equals(ATRON.ROTATION_DU.getRotation())){
					moveModuleComponent(movableModuleComponent,ATRON.ROTATION_WE,new Vector3f(xPlusOffset,yPlusOffset,z));
				}else if(rotationQuaterion.equals(ATRON.ROTATION_UD.getRotation())){
					moveModuleComponent(movableModuleComponent,ATRON.ROTATION_EW,new Vector3f(xMinusOffset,yMinusOffset,z));
				}else if (rotationQuaterion.equals(ATRON.ROTATION_SN.getRotation())){
					moveModuleComponent(movableModuleComponent,ATRON.ROTATION_DU,new Vector3f(x,yMinusOffset,zMinusOffset));
				}else if (rotationQuaterion.equals(ATRON.ROTATION_NS.getRotation())){
					moveModuleComponent(movableModuleComponent,ATRON.ROTATION_UD,new Vector3f(x,yPlusOffset,zPlusOffset));
				}
				break;
			default: System.out.println("ATRON module has only 8 connectors" );
			}  
		}  		
	}

	/**
	 * Moves current component of the module to new local position and assigns new rotation to the same component
	 * @param movableModuleComponent, the current component of the module 
	 * @param newRotation, the new local rotation to assign to the component
	 * @param newPosition,the new local position to translate the component to
	 */
	public void moveModuleComponent(JMEModuleComponent movableModuleComponent,RotationDescription  newRotation, Vector3f newPosition){
		for(DynamicPhysicsNode part: movableModuleComponent.getNodes()){ 
			part.setLocalRotation(newRotation.getRotation());											
			part.setLocalTranslation(newPosition);
		}
	}


	/**
	 * Checks if picked module is ATRON module (SHOULD BE MODIFIED, BECAUSE NOW IT IS RELYING ON NUMBER OF CONNECTORS, SHOULD RELY ON DIFRENT PROPERTY) NUMBER OF CONNECTORS IS NOT AN UNIQUE PROPERTY
	 * @param pickedModuleID, the ID of the module picked in simulation environment
	 * @return true if picked module is an ATRON module
	 */
	public boolean isAtron(int pickedModuleID){
		int amountConnectors = simulation.getModules().get(pickedModuleID).getConnectors().size();
		if (amountConnectors ==8 ){
			return true;
		}
		return false;
	}


	/**
	 * Checks if the selected spatial is ATRON module
	 * @param target, spatial parent geometry of picked component (module)
	 * @return true if the target is ATRON module, false in opposite case
	 */
	/*	public boolean isATRON(Spatial target){
	int nrChildren = target.getParent().getChildren().size();				
	for (int c =0;c<nrChildren; c++){
		String child = target.getParent().getChild(c).getName();
		//System.out.println("Child:"+child);//For debugging
		if (child.contains("ATRON")){
			//System.out.println("robotName: ATRON");//For debugging
			return true;
		}
	}
	return false;	
	}*/


}
