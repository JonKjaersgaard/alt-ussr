package ussr.builder.genericTools;

import ussr.model.Module;
import ussr.physics.jme.JMEModuleComponent;
import ussr.physics.jme.JMESimulation;
import ussr.physics.jme.pickers.CustomizedPicker;
import java.awt.Color;
import com.jme.scene.Geometry;


/**
 *  Helping tool. Colors the connectors of the module selected with the left side of the mouse in simulation environment. 
 *  Currently supports up to 12 connectors colored with different colors.
 *  @author Konstantinas
 */
public class ColorModuleConnectors extends CustomizedPicker {

	/**
	 * Array containing 12 colours for 12 connectors. 
	 */
	private static final Color colors[] = {Color.BLACK,Color.RED,Color.CYAN,Color.GRAY,Color.GREEN,Color.MAGENTA,
		                                    Color.ORANGE,Color.PINK,Color.BLUE,Color.WHITE,Color.YELLOW,Color.LIGHT_GRAY};
	
	/* Method executed when the module is selected with the left side of the mouse in simulation environment.
	 * Here not used, because it is enough of pickModuleComponent(JMEModuleComponent component) method (look beneath).
	 * @see ussr.physics.jme.pickers.CustomizedPicker#pickTarget(com.jme.scene.Spatial)
	 */
	@Override
	protected void pickTarget(Geometry target,JMESimulation jmeSimulation) {	}
	

	/*  Method executed when the module is selected with the left side of the mouse in simulation environment. 
	 *  In this case handles actual colouring of connectors.
	 * @see ussr.physics.jme.pickers.CustomizedPicker#pickModuleComponent(ussr.physics.jme.JMEModuleComponent)
	 */
	@Override
	protected void pickModuleComponent(JMEModuleComponent component) {
		/*Get selected module and for each connector assign colour*/
		Module selectedModule = component.getModel();
		int nrConnectors = selectedModule.getConnectors().size();		
		for (int connector=0; connector<nrConnectors;connector++){			
			selectedModule.getConnectors().get(connector).setColor(colors[connector]);			
		}            
	}

}
