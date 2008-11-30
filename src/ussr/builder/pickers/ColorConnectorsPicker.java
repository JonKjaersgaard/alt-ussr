package ussr.builder.pickers;

import ussr.physics.jme.JMEModuleComponent;
import ussr.physics.jme.JMESimulation;
import ussr.physics.jme.pickers.CustomizedPicker;

import java.awt.Color;

import com.jme.scene.Spatial;

//TODO CONSIDER USING LABELING INSTEAD OF COLOURS.
/**
 *  Helping tool.Colours the connectors of selected module. Currently supports up to 12 connectors coloured with different colours.
 * @author Konstantinas
 *
 */
public class ColorConnectorsPicker extends CustomizedPicker {

	/**
	 * The physical simulation
	 */
	private JMESimulation simulation;


	/**
	 * Constructor called in Quick Prototyping.java main window 
	 * @param simulation, the physical simulation	 
	 */
	public ColorConnectorsPicker(JMESimulation simulation) {
		this.simulation = simulation;		
	}

	/* Not used
	 * @see ussr.physics.jme.pickers.CustomizedPicker#pickTarget(com.jme.scene.Spatial)
	 */
	@Override
	protected void pickTarget(Spatial target) {	}

	/*  Method executed when the module is selected with the mouse in simulation environment. Handles actual colouring of connectors.
	 * @see ussr.physics.jme.pickers.CustomizedPicker#pickModuleComponent(ussr.physics.jme.JMEModuleComponent)
	 */
	@Override
	protected void pickModuleComponent(JMEModuleComponent component) {

		int moduleID = component.getModel().getID();
		int nrConnectors = simulation.getModules().get(moduleID).getConnectors().size();

		for (int connector=0; connector<nrConnectors;connector++){
			switch(connector){
			case 0:
				simulation.getModules().get(moduleID).getConnectors().get(0).setColor(Color.BLACK);
				break;
			case 1:
				simulation.getModules().get(moduleID).getConnectors().get(1).setColor(Color.BLUE);
				break;
			case 2:
				simulation.getModules().get(moduleID).getConnectors().get(2).setColor(Color.CYAN);
				break;
			case 3:
				simulation.getModules().get(moduleID).getConnectors().get(3).setColor(Color.GRAY);
				break;
			case 4:
				simulation.getModules().get(moduleID).getConnectors().get(4).setColor(Color.GREEN);
				break;
			case 5:
				simulation.getModules().get(moduleID).getConnectors().get(5).setColor(Color.MAGENTA);
				break;
			case 6:
				simulation.getModules().get(moduleID).getConnectors().get(6).setColor(Color.ORANGE);
				break;
			case 7:
				simulation.getModules().get(moduleID).getConnectors().get(7).setColor(Color.PINK);
				break;
			case 8:
				simulation.getModules().get(moduleID).getConnectors().get(8).setColor(Color.RED);
				break;
			case 9:
				simulation.getModules().get(moduleID).getConnectors().get(9).setColor(Color.WHITE);
				break;
			case 10:
				simulation.getModules().get(moduleID).getConnectors().get(10).setColor(Color.YELLOW);
				break;
			case 11:
				simulation.getModules().get(moduleID).getConnectors().get(11).setColor(Color.LIGHT_GRAY);
				break;    		
			}    		
		}            
	}

}
