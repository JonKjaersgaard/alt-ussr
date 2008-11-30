package ussr.builder.pickers.mtran;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JOptionPane;

import ussr.builder.pickers.utilities.Utilities;
import ussr.description.geometry.RotationDescription;
import ussr.description.geometry.VectorDescription;
import ussr.physics.jme.JMEModuleComponent;
import ussr.physics.jme.JMESimulation;
import ussr.physics.jme.pickers.CustomizedPicker;
import ussr.samples.atron.ATRON;

import com.jme.input.InputHandler;
import com.jme.math.Quaternion;
import com.jme.math.Vector3f;
import com.jme.scene.Node;
import com.jme.scene.Spatial;
import com.jme.scene.TriMesh;
import com.jmex.physics.DynamicPhysicsNode;
import com.jmex.physics.PhysicsSpace;
//TODO 1)THINK ABOUT THE CASE WHEN MODULES ARE OVERLAPING, SHOULD AVOID THAT
//2)CHANGE SOLUTION TO SUPPORT DYNAMIC ADDING OF MODULES INSTEAD OF TAKING THE MODULES FROM THE ONES IN SIMULATION ENVIRONMENT 
/**
 * Handles adding of randomly chosen MTRAN module from existing ones in simulation environment to the picked module.
 * Here it is assumed that user picks(selects) the MTRAN module, later selects the number of connector in GUI where he/she wants new module and later again clicks the same module.
 * @author Konstantinas
 *
 */
public class AssembleMTRANPicker2 extends AssembleMTRANPicker {

	/**
	 * The connector number on MTRAN module 
	 */
	private int connectorNr;


	/**
	 * Constructor called in Quick Prototyping.java main window 
	 * @param simulation, the physical simulation
	 */
	public AssembleMTRANPicker2(JMESimulation simulation,int connectorNr) {
		super(simulation);
		this.connectorNr = connectorNr;
	}


	/* Not used
	 * @see ussr.QPSS.pickers.MTRAN.AssembleMTRANPicker#pickTarget(com.jme.scene.Spatial)
	 */
	@Override
	protected void pickTarget(Spatial target) {	}

	/* Handles identification of selected MTRAN module and subsequent calls to add new MTRAN module to it
	 * @see ussr.QPSS.pickers.MTRAN.AssembleMTRANPicker#pickModuleComponent(ussr.physics.jme.JMEModuleComponent)
	 */
	@Override
	protected void pickModuleComponent(JMEModuleComponent component) {            
		int moduleID = component.getModel().getID();
		if (isMtran(moduleID)){    	
			Utilities u = new Utilities();
			int random = u.randomIntFromInterval(20,35);
			moveModuleAccording(this.connectorNr, moduleID, random);
		}else{
			JOptionPane.showMessageDialog(null, "This module is not an MTRAN module. The chosen picker is for MTRAN modules!");// Handle wrong user input
		}
	}

}
