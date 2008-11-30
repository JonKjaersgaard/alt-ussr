package ussr.builder.pickers.atron;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import com.jme.scene.Spatial;
import ussr.builder.pickers.utilities.Utilities;
import ussr.physics.jme.JMEModuleComponent;
import ussr.physics.jme.JMESimulation;

//TODO 1)THINK ABOUT THE CASE WHEN MODULES ARE OVERLAPING, SHOULD AVOID THAT
//2)CHANGE SOLUTION TO SUPPORT DYNAMIC ADDING OF MODULES INSTEAD OF TAKING THE MODULES FROM THE ONES IN SIMULATION ENVIRONMENT 
/**
 * Handles adding of randomly chosen ATRON module from existing ones in simulation environment to the picked module.
 * Here it is assumed that user picks(selects) the ATRON module, later uses the right arrow in GUI to loop through all possible connections of one ATRON module on another.
 * Next selects the same picker and repeats above steps.
 * @author Konstantinas
 *
 */
public class AssembleATRONPicker4 extends AssembleATRONPicker {

	/**
	 * The connector number on ATRON module 
	 */
	private int connectorNr;

	/**
	 * The ID of selected module
	 */
	private int idModule;

	/**
	 * Constructor called in Quick Prototyping.java main window 
	 * @param simulation, the physical simulation
	 * @param connectorNr, the connector number on ATRON module
	 */
	public AssembleATRONPicker4(JMESimulation simulation,int connectorNr) {
		super(simulation);
		this.connectorNr = connectorNr;
	}

	/* Not used
	 * @see ussr.QPSS.pickers.ATRON.AssembleATRONPicker#pickTarget(com.jme.scene.Spatial)
	 */
	@Override
	protected void pickTarget(Spatial target) {	}

	Utilities utilities = new Utilities();
	int randomID = utilities.randomIntFromInterval(5,24);


	/* Handles identification of selected ATRON module and subsequent calls to add new ATRON module to it
	 * @see ussr.QPSS.pickers.ATRON.AssembleATRONPicker#pickModuleComponent(ussr.physics.jme.JMEModuleComponent)
	 */
	@Override
	protected void pickModuleComponent(JMEModuleComponent component) {	
		int moduleID = component.getModel().getID(); 

		if (isAtron(moduleID)){    		
			setIdModule(moduleID);
			Utilities utilities = new Utilities();
			//int randomID = utilities.randomIntFromInterval(1,2);//(Take the same) For debugging
			moveModuleAccording(connectorNr, moduleID, randomID);
		}else{
			JOptionPane.showMessageDialog(null, "This module is not an ATRON module. The chosen picker is for ATRON modules!");// Handle wrong user input
		}
	}

	/**
	 * Adds random ATRON module to picked ATRON module 
	 * @param connectorNr, the connector number on ATRON module 
	 */
	public void addNewModule(int connectorNr){    	   	
		moveModuleAccording(connectorNr, this.idModule, randomID);
	}

	public int getIdModule() {
		return idModule;
	}

	public void setIdModule(int idModule) {
		this.idModule = idModule;
	}    

}
