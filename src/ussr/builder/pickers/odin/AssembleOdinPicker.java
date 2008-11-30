package ussr.builder.pickers.odin;

import java.awt.Color;
import java.util.Random;

import javax.swing.JOptionPane;

import com.jme.math.Quaternion;
import com.jme.math.Vector3f;
import com.jme.scene.Spatial;
import com.jme.scene.TriMesh;
import com.jmex.physics.DynamicPhysicsNode;

import ussr.builder.pickers.utilities.Utilities;
import ussr.description.geometry.RotationDescription;
import ussr.description.geometry.VectorDescription;
import ussr.model.Connector;
import ussr.model.Module;
import ussr.physics.jme.JMEModuleComponent;
import ussr.physics.jme.JMESimulation;
import ussr.physics.jme.pickers.CustomizedPicker;
import ussr.samples.atron.ATRON;

//TODO 1) REFACTOR MORE
//2) CHANGE CHECKING OF WHAT KIND OF MODULE IT IS (IS IT ATRON OR OTHER MODULE, method isOdin()). THIS SHOULD COVER THE CASE WHEN USER SELECTS WRONG PICKER FOR  A WRONG MODULE TYPE
//3) CONSIDER USING POSITION OF MODULE COMPONENT AS REFERENCE INSTEAD OF CONNECTOR POSITION, LOOK AT ATRON
/**
 * 
 * Parent class, defining methods to handle the picking of the Odin module in simulation environment
 * @author Konstantinas
 *
 */
public class AssembleOdinPicker extends CustomizedPicker {

	/** 
	 * The physical simulation
	 */
	public JMESimulation simulation;

	/**
	 * Mathematical constant pi
	 */
	public final float pi = (float)Math.PI;

	/**
	 * A number of different rotations of Structure link or Telescoping link with respect to connector of CCP joint .
	 * Here:rotation0 is rotation of Structure link with respect to connector number 0 and 11 of CCP joint
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
	 * Global rotation of Odin  ball
	 */
	public final RotationDescription rotation00= new RotationDescription(0,0,0);	
	// ABOVE ROTATIONS, MAYBE CAN BE MOVED TO ODIN.java or somwhere else

	/**
	 * Constructor 
	 * @param simulation, the physical simulation
	 */
	public AssembleOdinPicker (JMESimulation simulation) {
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
	 * Moves the Odin module according to picked Odin module preconditions, like rotation, connector and so on
	 * @param connectorNr, the connector number on Odin module
	 * @param pickedModuleID, the ID of the module picked in simulation environment
	 * @param moduleToMoveID, the ID of the module existing in simulation and should be moved with respect to picked one
	 */
	public void moveModuleAccording(int amountConnectors,int connectorNr, int pickedModuleID, int moduleToMoveID ){
		if (amountConnectors==12) {// Odins module callled CCP joint
			Connector connector = simulation.getModules().get(pickedModuleID).getConnectors().get(connectorNr);
			//connector.setColor(Color.CYAN);// FOR Debugging
			VectorDescription position = connector.getPhysics().get(0).getPosition();
			//RotationDescription rotation = connector.getPhysics().get(0).getRotation(); //FOR TESTING

			float x = position.getX();
			float y = position.getY();
			float z = position.getZ();

			// Offsets to move Structure Link and Telescoping  link with respect to CCP joint connectors
			final float Offset =0.046f;//0.03+0.035/2 +-0.015, means (OdinMuscle-->CylinderShape-->height/2)+(ODINMuscle-->coneCap1-->radius/2)+-tolerance
			float xMinusOffset = position.getX()-Offset;
			float yMinusOffset = position.getY()-Offset;
			float zMinusOffset = position.getZ()-Offset;
			float xPlusOffset = position.getX()+Offset;
			float yPlusOffset = position.getY()+Offset;
			float zPlusOffset = position.getZ()+Offset;			

			int amountComponents= simulation.getModules().get(moduleToMoveID).getNumberOfComponents();			

			for (int com=0;com<amountComponents;com++){
				JMEModuleComponent moduleComponent = (JMEModuleComponent)simulation.getModules().get(moduleToMoveID).getComponent(com);

				switch(connectorNr){
				case 0:					
					moveModuleComponent(moduleComponent, rotation0, new Vector3f(xMinusOffset,yMinusOffset,z));				
					break;
				case 1:					
					moveModuleComponent(moduleComponent, rotation1, new Vector3f(xMinusOffset,y,zMinusOffset));			
					break;
				case 2:					
					moveModuleComponent(moduleComponent, rotation2, new Vector3f(xMinusOffset,y,zPlusOffset));			
					break;
				case 3:					
					moveModuleComponent(moduleComponent, rotation3, new Vector3f(xMinusOffset,yPlusOffset,z));			
					break;
				case 4:					
					moveModuleComponent(moduleComponent, rotation4, new Vector3f(x,yMinusOffset,zMinusOffset));			
					break;
				case 5:					
					moveModuleComponent(moduleComponent, rotation5, new Vector3f(x,yMinusOffset,zPlusOffset));					
					break;
				case 6:					
					moveModuleComponent(moduleComponent, rotation5, new Vector3f(x,yPlusOffset,zMinusOffset));
					break;
				case 7:					
					moveModuleComponent(moduleComponent, rotation4, new Vector3f(x,yPlusOffset,zPlusOffset));					
					break;
				case 8:					
					moveModuleComponent(moduleComponent, rotation3, new Vector3f(xPlusOffset,yMinusOffset,z));				
					break;
				case 9:					
					moveModuleComponent(moduleComponent, rotation2, new Vector3f(xPlusOffset,y,zMinusOffset));				
					break;
				case 10:					
					moveModuleComponent(moduleComponent, rotation1, new Vector3f(xPlusOffset,y,zPlusOffset));				
					break;
				case 11:
					moveModuleComponent(moduleComponent, rotation0, new Vector3f(xPlusOffset,yPlusOffset,z));		
					break;
				default: System.out.println("Odin's CCP joint has 12 female connectors");
				}
			}
		} else  if (amountConnectors == 2){ // Odins module callled Structure link or Telescoping link
			if (connectorNr>1){
				JOptionPane.showMessageDialog(null, "This module has only 2 connectors (0 and 1)");// Handle wrong user input
			}else{
				Connector connector = simulation.getModules().get(pickedModuleID).getConnectors().get(connectorNr);
				//connector.setColor(Color.CYAN);// FOR Debugging
				VectorDescription position = connector.getPhysics().get(0).getPosition();
				Quaternion rotationQauterion = simulation.getModules().get(pickedModuleID).getPhysics().get(0).getRotation().getRotation(); 

				float x = position.getX();
				float y = position.getY();
				float z = position.getZ();

				// Offsets to move Odins CCP joint with respect to Structure Link and Telescoping link connectors 
				final float Offset =0.0177f;//0.035/2+-0.0002(ODINHinge-->ConeCap-->height/2)+-tolerance
				float xMinusOffset = position.getX()- Offset;
				float yMinusOffset = position.getY()- Offset;
				float zMinusOffset = position.getZ()- Offset;
				float xPlusOffset = position.getX()+ Offset;
				float yPlusOffset = position.getY()+ Offset;
				float zPlusOffset = position.getZ()+ Offset;

				int amountComponents= simulation.getModules().get(moduleToMoveID).getNumberOfComponents();
				for (int com=0;com<amountComponents;com++){

					JMEModuleComponent moduleComponent = (JMEModuleComponent)simulation.getModules().get(moduleToMoveID).getComponent(com);					

					switch(connectorNr){
					case 0:
						if (rotationQauterion.equals(rotation0.getRotation())){
							moveModuleComponent(moduleComponent, rotation00, new Vector3f(xMinusOffset,yMinusOffset,z));
						}else if (rotationQauterion.equals(rotation1.getRotation())){
							moveModuleComponent(moduleComponent, rotation00, new Vector3f(xMinusOffset,y,zMinusOffset));
						}else if (rotationQauterion.equals(rotation2.getRotation())){
							moveModuleComponent(moduleComponent, rotation00, new Vector3f(xMinusOffset,y,zPlusOffset));
						}else if (rotationQauterion.equals(rotation3.getRotation())){
							moveModuleComponent(moduleComponent, rotation00, new Vector3f(xMinusOffset,yPlusOffset,z));
						}else if (rotationQauterion.equals(rotation4.getRotation())){
							moveModuleComponent(moduleComponent, rotation00, new Vector3f(x,yPlusOffset,zPlusOffset));
						}else if (rotationQauterion.equals(rotation5.getRotation())){
							moveModuleComponent(moduleComponent, rotation00, new Vector3f(x,yPlusOffset,zMinusOffset));
						}
						break;
					case 1: // EVERYTHING FOR CONNECTOR 1 CAN BE REMOVED IF IT IS ASSUMED THAT THE INITIALLY THERE IS ODIN BALL IN SIMULATION ENVIRONMENT
						// IN THIS WAY THE OVERLAPING OF MODULES CAN BE AVOIDED
						if (rotationQauterion.equals(rotation0.getRotation())){
							moveModuleComponent(moduleComponent, rotation00, new Vector3f(xPlusOffset,yPlusOffset,z));
						}else if (rotationQauterion.equals(rotation1.getRotation())){
							moveModuleComponent(moduleComponent, rotation00, new Vector3f(xPlusOffset,y,zPlusOffset));
						}else if (rotationQauterion.equals(rotation2.getRotation())){
							moveModuleComponent(moduleComponent, rotation00, new Vector3f(xPlusOffset,y,zMinusOffset));
						}else if (rotationQauterion.equals(rotation3.getRotation())){
							moveModuleComponent(moduleComponent, rotation00, new Vector3f(xPlusOffset,yMinusOffset,z));
						}else if (rotationQauterion.equals(rotation4.getRotation())){
							moveModuleComponent(moduleComponent, rotation00, new Vector3f(x,yMinusOffset,zMinusOffset));
						}else if (rotationQauterion.equals(rotation5.getRotation())){
							moveModuleComponent(moduleComponent, rotation00, new Vector3f(x,yMinusOffset,zPlusOffset));
						}				
						break;
					default: System.out.println("Odin's Structure and Telescoping links have 2 male connectors");

					}
				}
			}

		}

	}

	/**
	 * Moves current component of a module to new local position and assigns new rotation to the same component
	 * @param moduleComponent, the current component of the module 
	 * @param newRotation, the new rotation to assign to the component
	 * @param newPosition,the new position to translate the component to
	 */
	public void moveModuleComponent(JMEModuleComponent moduleComponent,RotationDescription  newRotation, Vector3f newPosition){
		for(DynamicPhysicsNode part: moduleComponent.getNodes()){ 
			part.setLocalRotation(newRotation.getRotation());											
			part.setLocalTranslation(newPosition);
		}
	}

	/**
	 * Checks if picked module is Odin module (SHOULD BE MODIFIED, BECAUSE NOW IT IS RELYING ON NUMBER OF CONNECTORS, SHOULD RELY ON DIFRENT PROPERTY) NUMBER OF CONNECTORS IS NOT AN UNIQUE PROPERTY
	 * @param pickedModuleID, the ID of the module picked in simulation environment
	 * @return true if picked module is an Odin module
	 */
	public boolean isOdin(int pickedModuleID){
		int amountConnectors = simulation.getModules().get(pickedModuleID).getConnectors().size();
		if (amountConnectors ==2||amountConnectors==12 ){
			return true;
		}
		return false;
	}

	/**
	 * Checks if the selected spatial is Odin module
	 * @param target, spatial parent geometry of picked component (module)
	 * @return true if the target is Odin module, false in opposite case
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
	/**
	 * Returns the random module from simulation environment matching given number of connectors (for example 12 connectors gives Odin joint, 2 - Structure link )  
	 * @param nrConnectors, the number of connectors the module has, Odin CCP has 12, Structure and Telescoping links have 2
	 * @return module,random module from simulation environment matching the number of connectors (for example 12 connectors gives Odin joint, 2 - Structure link )
	 */
	public Module findRandomModuleToMove(int nrConnectors){			
		Module moduleToMove=null;			
		while(moduleToMove ==null){
			int randomInt = randomIntFromInterval(2,(simulation.getModules().size()-2));
			int nrCon =simulation.getModules().get(randomInt).getConnectors().size();

			if(nrCon == nrConnectors){
				moduleToMove =simulation.getModules().get(randomInt);
			}
		}	
		System.out.println("Module:"+moduleToMove.getID());

		return moduleToMove;
	}
	/**
	 * Gives the random integer from the assigned interval
	 * @param min, lowest limit
	 * @param max, highest limit
	 * @return, random integer
	 */
	public int randomIntFromInterval(int min, int max) {	
		return min + (new Random()).nextInt(max-min);
	}


}
