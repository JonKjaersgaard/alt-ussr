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
 * Handles adding of randomly chosen MTRAN modules from existing ones in simulation environment to the picked module.
 * Here it is assumed that all possible MTRAN modules are added to the picked MTRAN module.
 * @author Konstantinas
 *
 */
public class AssembleMTRANPicker3 extends AssembleMTRANPicker {

	/**
	 * Constructor called in Quick Prototyping.java main window 
	 * @param simulation, the physical simulation
	 */
	public AssembleMTRANPicker3(JMESimulation simulation) {
		super(simulation);		
	}

	/* Not used
	 * @see ussr.QPSS.pickers.MTRAN.AssembleMTRANPicker#pickTarget(com.jme.scene.Spatial)
	 */
	@Override
	protected void pickTarget(Spatial target) {	}

	/*  Handles identification of selected MTRAN module and subsequent calls to add new MTRAN modules to it
	 * @see ussr.QPSS.pickers.MTRAN.AssembleMTRANPicker#pickModuleComponent(ussr.physics.jme.JMEModuleComponent)
	 */
	@Override
	protected void pickModuleComponent(JMEModuleComponent component) {
		int moduleID = component.getModel().getID();
		if (isMtran(moduleID)==true){			
			int amountConnectors = simulation.getModules().get(moduleID).getConnectors().size();
			for (int connector=0; connector<amountConnectors;connector++){		
				Utilities u = new Utilities();
				int random = u.randomIntFromInterval(5,24);
				moveModuleAccording(connector, moduleID, random);
			}}else{
				JOptionPane.showMessageDialog(null, "This module is not an MTRAN module. The chosen picker is for MTRAN modules!");// Handle wrong user input
			}		
	}



}
