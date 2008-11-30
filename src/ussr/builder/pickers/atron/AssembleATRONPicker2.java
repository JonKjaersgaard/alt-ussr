package ussr.builder.pickers.atron;

import javax.swing.JOptionPane;
import com.jme.scene.Spatial;
import ussr.builder.pickers.utilities.Utilities;
import ussr.physics.jme.JMEModuleComponent;
import ussr.physics.jme.JMESimulation;

//TODO 1)THINK ABOUT THE CASE WHEN MODULES ARE OVERLAPING, SHOULD AVOID THAT
//2)CHANGE SOLUTION TO SUPPORT DYNAMIC ADDING OF MODULES INSTEAD OF TAKING THE MODULES FROM THE ONES IN SIMULATION ENVIRONMNET 
/**
 * Handles adding of randomly chosen ATRON module from existing ones in simulation environment to the picked module.
 * Here it is assumed that user pickes(selects) the ATRON module, later selects the number of connector in GUI where he/she wants new module and later again clicks the same module.
 * @author Konstantinas
 *
 */
public class AssembleATRONPicker2 extends AssembleATRONPicker {

	/**
	 * The connector number on ATRON module 
	 */
	private int connectorNr;

	/**
	 * Constructor called in Quick Prototyping.java main window 
	 * @param simulation, the physical simulation
	 * @param connectorNr, the connector number on ATRON module
	 */
	public AssembleATRONPicker2(JMESimulation simulation,int connectorNr) {
		super(simulation);
		this.connectorNr = connectorNr;
	}

	/* Not used
	 * @see ussr.QPSS.pickers.ATRON.AssembleATRONPicker#pickTarget(com.jme.scene.Spatial)
	 */
	@Override
	protected void pickTarget(Spatial target) {	}

	/* Handles identification of selected ATRON module and subsequent calls to add new ATRON module to it
	 * @see ussr.QPSS.pickers.ATRON.AssembleATRONPicker#pickModuleComponent(ussr.physics.jme.JMEModuleComponent)
	 */
	@Override
	protected void pickModuleComponent(JMEModuleComponent component) {	
		int moduleID = component.getModel().getID();
		if (isAtron(moduleID)){    	
			Utilities utilities = new Utilities();
			int randomID = utilities.randomIntFromInterval(5,24);
			moveModuleAccording(this.connectorNr, moduleID, randomID);
		}else{
			JOptionPane.showMessageDialog(null, "This module is not an ATRON module. The chosen picker is for ATRON modules!");// Handle wrong user input
		}
	}
}
