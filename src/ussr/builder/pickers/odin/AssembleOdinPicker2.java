package ussr.builder.pickers.odin;

import javax.swing.JOptionPane;
import ussr.builder.pickers.utilities.Utilities;
import ussr.model.Module;
import ussr.physics.jme.JMEModuleComponent;
import ussr.physics.jme.JMESimulation;
import com.jme.scene.Spatial;

//TODO 1)THINK ABOUT THE CASE WHEN MODULES ARE OVERLAPING, SHOULD AVOID THAT
//2)CHANGE SOLUTION TO SUPPORT DYNAMIC ADDING OF MODULES INSTEAD OF TAKING THE MODULES FROM THE ONES IN SIMULATION ENVIRONMENT 

/**
 * A tool to assemble Odin's morphology (structure) by means of selecting Odin's module in simulation environment.
 * Here the modules are added in sequential fashion by means of clicking the module. The added modules are chosen randomly from existing ones in simulation environment
 * Here it is assumed that user pickes(selects) the ATRON module, later selects the number of connector in GUI where he/she wants new module and later again clicks the same module.
 * @author Konstantinas
 *
 */
//TODO: 
//1)LOOK ALL GREEN TEXT IN CAPITALS

public class AssembleOdinPicker2 extends AssembleOdinPicker {

	/**
	 * The connector number on Odin module 
	 */
	private int connectorNr;

	/**
	 * Constructor called in QPSS main window 
	 * @param simulation, the physical simulation
	 * @param counter, the connector number on Odin module
	 */
	public AssembleOdinPicker2(JMESimulation simulation, int connectorNr ) {
		super(simulation);
		this.connectorNr = connectorNr;
	}

	/* Not used
	 * @see ussr.builder.pickers.odin.AssembleOdinPicker#pickTarget(com.jme.scene.Spatial)
	 */
	@Override
	protected void pickTarget(Spatial target) {
		// TODO Auto-generated method stub

	}

	/* Method executed when the Odin module is selected with the mouse in simulation environment
	 * @see ussr.physics.jme.pickers.CustomizedPicker#pickModuleComponent(ussr.physics.jme.JMEModuleComponent)
	 */
	@Override
	protected void pickModuleComponent(JMEModuleComponent component) {

		int moduleID = component.getModel().getID();
		if (isOdin(moduleID)){  
			int amountConnectors = simulation.getModules().get(moduleID).getConnectors().size();
			if (amountConnectors==12){
				//Module moduleToMove = findModuleToMove(2);//(Debugging)Take the same module all the time
				Module moduleToMove = findRandomModuleToMove(2); //Take new random module from existing ones during each click 
				int moduleToMoveID = moduleToMove.getID();
				moveModuleAccording(12,connectorNr,moduleID,moduleToMoveID);
			}else if (amountConnectors==2){
				Module moduleToMove = findRandomModuleToMove(12); //Take new random module from existing ones during each click 
				int moduleToMoveID = moduleToMove.getID();
				moveModuleAccording(2,connectorNr,moduleID,moduleToMoveID);
			}
		}else{
			JOptionPane.showMessageDialog(null, "This module is not an Odin module. The chosen picker is for Odin modules!");// Handle wrong user input
		}


	}



	/**
	 * Returns the first occuerence of a module in simulation environment matching given number of connectors (for example 12 connectors gives Odin joint, 2 - Structure link )  
	 * @param nrConnectors, the number of connectors the module has, Odin CCP has 12, Structure and Telescoping links have 2
	 * @return module,first module in simulation environment matching the number of connectors (for example 12 connectors gives Odin joint, 2 - Structure link )
	 */
	private Module findModuleToMove(int nrConnectors){
		Module m=null; 
		for (int n=0; n <=simulation.getModules().size();n++){
			int nrCon =simulation.getModules().get(n).getConnectors().size();
			if (nrCon == nrConnectors){
				m =simulation.getModules().get(n);
				break;
			}else if (nrCon ==nrConnectors){
				m =simulation.getModules().get(n);				
				break;
			}    	
		}
		return m;

	}






}
