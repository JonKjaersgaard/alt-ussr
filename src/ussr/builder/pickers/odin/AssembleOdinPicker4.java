package ussr.builder.pickers.odin;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import com.jme.scene.Spatial;
import ussr.builder.pickers.utilities.Utilities;
import ussr.model.Module;
import ussr.physics.jme.JMEModuleComponent;
import ussr.physics.jme.JMESimulation;

//TODO 1) MORE REFACTORING
//2)THINK ABOUT THE CASE WHEN MODULES ARE OVERLAPING, SHOULD AVOID THAT
//3)CHANGE SOLUTION TO SUPPORT DYNAMIC ADDING OF MODULES INSTEAD OF TAKING THE MODULES FROM THE ONES IN SIMULATION ENVIRONMENT 
/**
 * Handles adding of randomly chosen Odin module from existing ones in simulation environment to the picked module.
 * Here it is assumed that user picks(selects) the Odin module, later uses the right arrow in GUI to loop through all possible connections of one Odin module on another.
 * Next selects the same picker and repeats above steps.
 * @author Konstantinas
 *
 */
public class AssembleOdinPicker4 extends AssembleOdinPicker {

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
	public AssembleOdinPicker4(JMESimulation simulation,int connectorNr) {
		super(simulation);
		this.connectorNr = connectorNr;
	}

	/* Not used
	 * @see ussr.QPSS.pickers.ATRON.AssembleATRONPicker#pickTarget(com.jme.scene.Spatial)
	 */
	@Override
	protected void pickTarget(Spatial target) {	}

	int moduleToMoveLinkID; 
	int moduleToMoveJointID;
	int amountConnectors;	

	/* Handles identification of selected ATRON module and subsequent calls to add new ATRON module to it
	 * @see ussr.QPSS.pickers.ATRON.AssembleATRONPicker#pickModuleComponent(ussr.physics.jme.JMEModuleComponent)
	 */
	@Override
	protected void pickModuleComponent(JMEModuleComponent component) {	

		int moduleID = component.getModel().getID();
		if (isOdin(moduleID)){ 
			setIdModule(moduleID);
			int amountConnectors = simulation.getModules().get(moduleID).getConnectors().size();
			this.amountConnectors = amountConnectors;
			if (amountConnectors==12){
				this.moduleToMoveLinkID = findRandomModuleToMove(2).getID();


				//Module moduleToMove = findModuleToMove(2);//(Debugging)Take the same module all the time
				moveModuleAccording(12,connectorNr,moduleID,moduleToMoveLinkID);
			}else if (amountConnectors==2){	
				this.moduleToMoveJointID = findRandomModuleToMove(12).getID();			
				moveModuleAccording(2,connectorNr,moduleID,moduleToMoveJointID);
			}
		}else{
			JOptionPane.showMessageDialog(null, "This module is not an Odin module. The chosen picker is for Odin modules!");// Handle wrong user input
		}    

		/*int moduleID = component.getModel().getID(); 

    	if (isOdin(moduleID)){    		
    		setIdModule(moduleID);
    		Utilities utilities = new Utilities();
        	//int randomID = utilities.randomIntFromInterval(1,2);//(Take the same) For debugging
        	moveModuleAccording(connectorNr, moduleID, randomID);
    	}else{
			JOptionPane.showMessageDialog(null, "This module is not an ATRON module. The chosen picker is for ATRON modules!");// Handle wrong user input
		}*/
	}

	/**
	 * Adds random Odin module to picked Odin module 
	 * @param connectorNr, the connector number on ATRON module 
	 */
	public void addNewModule(int connectorNr){ 
		if (amountConnectors==2){
			moveModuleAccording(2,connectorNr, this.idModule, this.moduleToMoveJointID);
		}else if (amountConnectors==12){
			moveModuleAccording(12,connectorNr, this.idModule, this.moduleToMoveLinkID);
		}
	}


	public int getIdModule() {
		return idModule;
	}

	public void setIdModule(int idModule) {
		this.idModule = idModule;
	}

	public int getAmountConnectors() {
		return amountConnectors;
	}



}
