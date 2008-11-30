package ussr.builder.pickers.atron;

import javax.swing.JOptionPane;
import ussr.builder.pickers.utilities.Utilities;
import ussr.physics.jme.JMEModuleComponent;
import ussr.physics.jme.JMESimulation;
import com.jme.scene.Spatial;

//TODO 1)THINK ABOUT THE CASE WHEN MODULES ARE OVERLAPING, SHOULD AVOID THAT
//2)CHANGE SOLUTION TO SUPPORT DYNAMIC ADDING OF MODULES INSTEAD OF TAKING THE MODULES FROM THE ONES IN SIMULATION ENVIRONMNET 

/**
 * Handles adding of randomly chosen ATRON modules from existing ones in simulation environment to the picked module.
 * Here it is assumed that all possible ATRON modules (8 of them) are added to the picked ATRON module.
 * @author Konstantinas
 *
 */
public class AssembleATRONPicker3 extends AssembleATRONPicker {

	/**
	 * Constructor called in Quick Prototyping.java main window 
	 * @param simulation, the physical simulation
	 */
	public AssembleATRONPicker3(JMESimulation simulation) {
		super(simulation);		
	}

	/* Not used
	 * @see ussr.QPSS.pickers.ATRON.AssembleATRONPicker#pickTarget(com.jme.scene.Spatial)
	 */
	@Override
	protected void pickTarget(Spatial target) {	}	

	/* Handles identification of selected ATRON module and subsequent calls to add new ATRON modules to it
	 * @see ussr.QPSS.pickers.ATRON.AssembleATRONPicker#pickModuleComponent(ussr.physics.jme.JMEModuleComponent)
	 */
	@Override
	protected void pickModuleComponent(JMEModuleComponent component) {
		int moduleID = component.getModel().getID();
		if (isAtron(moduleID)){		
			int amountConnectors = simulation.getModules().get(moduleID).getConnectors().size();
			for (int connector=0; connector<amountConnectors;connector++){		
				Utilities utilities = new Utilities();
				int random = utilities.randomIntFromInterval(5,24);
				moveModuleAccording(connector, moduleID, random);
			}}else{
				JOptionPane.showMessageDialog(null, "This module is not an ATRON module. The chosen picker is for ATRON modules!");// Handle wrong user input
			}		
	}
}
