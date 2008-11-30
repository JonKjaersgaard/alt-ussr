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

//TODO// IMPLEMENT THIS CLASS, SOLUTION SHOULD BE SIMILAR TO  AssembleATRONPicker4. java
//2)THINK ABOUT THE CASE WHEN MODULES ARE OVERLAPING, SHOULD AVOID THAT
//3)CHANGE SOLUTION TO SUPPORT DYNAMIC ADDING OF MODULES INSTEAD OF TAKING THE MODULES FROM THE ONES IN SIMULATION ENVIRONMENT 
/**
 * Handles adding of randomly chosen MTRAN module from existing ones in simulation environment to the picked module.
 * Here it is assumed that user picks(selects) the MTRAN module, later uses the right arrow in GUI to loop through all possible connections of one MTRAN module on another.
 * Next selects the same picker and repeats above steps.
 * @author Konstantinas
 *
 */
public class AssembleMTRANPicker4 extends AssembleMTRANPicker {

	/**
	 * Constructor called in Quick Prototyping.java main window 
	 * @param simulation, the physical simulation
	 */
	public AssembleMTRANPicker4(JMESimulation simulation) {
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
//			FIXME		// CODE IT
		}else{
			JOptionPane.showMessageDialog(null, "This module is not an MTRAN module. The chosen picker is for MTRAN modules!");// Handle wrong user input
		}		
	}



}
