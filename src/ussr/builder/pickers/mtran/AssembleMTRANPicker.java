package ussr.builder.pickers.mtran;

import com.jme.math.Vector3f;
import com.jme.scene.Spatial;
import com.jmex.physics.DynamicPhysicsNode;
import ussr.description.geometry.RotationDescription;
import ussr.description.geometry.VectorDescription;
import ussr.physics.jme.JMEModuleComponent;
import ussr.physics.jme.JMESimulation;
import ussr.physics.jme.pickers.CustomizedPicker;

//TODO 1) WORK MORE WITH ROTATIONS
//2)ENCHANCE CHECKING OF WHAT KIND OF MODULE IT IS (IS IT ATRON OR OTHER MODULE, method isMtran()). THIS SHOULD COVER THE CASE WHEN USER SELECTS WRONG PICKER FOR  A WRONG MODULE TYPE
/**
 * Parent class, defining methods to handle the picking of the MTRAN module in simulation environment
 * @author Konstantinas
 *
 */
public class AssembleMTRANPicker extends CustomizedPicker {

	/**
	 * The physical simulation
	 */
	public JMESimulation simulation;

	/**
	 * Constructor 
	 * @param simulation, the physical simulation
	 */
	public AssembleMTRANPicker (JMESimulation simulation) {
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
	 * Moves the MTRAN module according to picked MTRAN module preconditions, like rotation, connector and so on
	 * @param connectorNr, the connector number on MTRAN module
	 * @param pickedModuleID, the ID of the module picked in simulation environment
	 * @param moduleToMoveID, the ID of the module existing in simulation and should be moved with respect to picked one
	 */
	public void moveModuleAccording(int connectorNr, int pickedModuleID, int moduleToMoveID ){

		int amountComponents =simulation.getModules().get(pickedModuleID).getNumberOfComponents();

		for (int c=0; c<amountComponents;c++){
			JMEModuleComponent moduleComponent1 = (JMEModuleComponent)simulation.getModules().get(pickedModuleID).getComponent(c);
			VectorDescription position1 = moduleComponent1.getPosition();

			float x = position1.getX();
			float y = position1.getY();
			float z = position1.getZ();


			float xMinusOffset = x - 0.14f;
			final float pi = (float)Math.PI;
			RotationDescription ORI1 = new RotationDescription(-pi/2,0,pi/2);
			RotationDescription ORI1X = new RotationDescription(pi,0,pi/2);

			RotationDescription ORI2 = new RotationDescription(-pi/2,0,0);
			RotationDescription ORI2X = new RotationDescription(0,0,0);//NEW
			//RotationDescription ORI2X = new RotationDescription(pi/2,0,pi/2);
			//RotationDescription ORI2X = new RotationDescription(0,pi/2,0);
			RotationDescription ORI2Y = new RotationDescription(0,pi/2,0);
			RotationDescription ORI2Y1 = new RotationDescription(0,-pi/2,0);

			//RotationDescription ORI3 = new RotationDescription(-pi/2,pi/2,0);
			//RotationDescription ORI3X = new RotationDescription(pi/2,pi/2,0);

			//RotationDescription ORI1Y = new RotationDescription(-pi/2,pi,pi/2);
			//RotationDescription ORI1XY = new RotationDescription(pi,pi,pi/2);

			//RotationDescription ORI2Y = new RotationDescription(-pi/2,pi,0);
			//RotationDescription ORI2XY = new RotationDescription(pi,pi,0);

			//RotationDescription ORI3Y = new RotationDescription(-pi/2,pi/2+pi,0);
			//RotationDescription ORI3XY = new RotationDescription(pi,pi/2+pi,0);
			/*		RotationDescription initial =new RotationDescription(0,0,0);
			RotationDescription initial1 =new RotationDescription(pi,0,0);
			if (c==2){//
				for(DynamicPhysicsNode part: moduleComponent1.getNodes()){ 
				part.setLocalRotation(initial.getRotation());
				}
			}else if (c==1){
				for(DynamicPhysicsNode part: moduleComponent1.getNodes()){ 
				part.setLocalRotation(initial1.getRotation());
				}
			}else if (c==0){
				for(DynamicPhysicsNode part: moduleComponent1.getNodes()){ 
				part.setLocalRotation(initial.getRotation());
				}
			}*/



			JMEModuleComponent movableModuleComponent = (JMEModuleComponent)simulation.getModules().get(moduleToMoveID).getComponent(c);

//			TODO WORK WITH DIFFERENT ROTATIONS
			switch (connectorNr){
			case 0:
				moveModuleComponent(movableModuleComponent,ORI2,new Vector3f(xMinusOffset,y,z));				
				break;
			case 1:
				if (c==2){//
					moveModuleComponent(movableModuleComponent,ORI2Y,new Vector3f(x-0.075f,y,z+0.065f));
				}else if (c==1){
					moveModuleComponent(movableModuleComponent,ORI2Y,new Vector3f(x-0.0375f,y,z+0.1f));
				}else if (c==0){
					moveModuleComponent(movableModuleComponent,ORI2Y,new Vector3f(x,y,z+0.14f));
				}
				//moveModuleComponent(moduleComponent2,ORI2X,new Vector3f(x,y,z+0.05f));
				break;
			case 2:
				if (c==2){
					moveModuleComponent(movableModuleComponent,ORI2Y1,new Vector3f(x-0.075f,y,z-0.065f));
				}else if (c==1){
					moveModuleComponent(movableModuleComponent,ORI2Y1,new Vector3f(x-0.0375f,y,z-0.1f));
				}else if (c==0){
					moveModuleComponent(movableModuleComponent,ORI2Y1,new Vector3f(x,y,z-0.14f));
				}
				break;
			case 3:
				moveModuleComponent(movableModuleComponent,ORI2,new Vector3f(x+0.14f,y,z));
				break;
			case 4:
				if (c==2){
					moveModuleComponent(movableModuleComponent,ORI2Y1,new Vector3f(x,y,z+0.14f));
				}else if (c==1){
					moveModuleComponent(movableModuleComponent,ORI2Y1,new Vector3f(x+0.0375f,y,z+0.1f));
				}else if (c==0){

					moveModuleComponent(movableModuleComponent,ORI2Y1,new Vector3f(x+0.075f,y,z+0.065f));
				}
				break;
			case 5:
				if (c==2){
					moveModuleComponent(movableModuleComponent,ORI2Y,new Vector3f(x,y,z-0.14f));
				}else if (c==1){
					moveModuleComponent(movableModuleComponent,ORI2Y,new Vector3f(x+0.0375f,y,z-0.1f));
				}else if (c==0){

					moveModuleComponent(movableModuleComponent,ORI2Y,new Vector3f(x+0.075f,y,z-0.065f));
				}
				break;
			default:
				System.out.println("M-Tran has  6 connectors" );
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
	 * Checks if picked module is MTRAN module (SHOULD BE MODIFIED, BECAUSE NOW IT IS RELYING ON NUMBER OF CONNECTORS, SHOULD RELY ON DIFRENT PROPERTY) NUMBER OF CONNECTORS IS NOT AN UNIQUE PROPERTY
	 * @param pickedModuleID, the ID of the module picked in simulation environment
	 * @return true if picked module is an MTRAN module
	 */
	public boolean isMtran(int pickedModuleID){
		int amountConnectors = simulation.getModules().get(pickedModuleID).getConnectors().size();
		if (amountConnectors ==6){
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
