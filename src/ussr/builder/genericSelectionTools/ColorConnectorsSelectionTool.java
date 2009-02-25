package ussr.builder.genericSelectionTools;

import ussr.description.geometry.VectorDescription;
import ussr.physics.jme.JMEModuleComponent;
import ussr.physics.jme.JMESimulation;
import ussr.physics.jme.pickers.CustomizedPicker;
import java.awt.Color;
import java.awt.Font;
import jmetest.renderer.state.TestTextureState;
import com.jme.math.FastMath;
import com.jme.math.Quaternion;
import com.jme.math.Vector3f;
import com.jme.renderer.ColorRGBA;
import com.jme.scene.Spatial;
import com.jme.scene.state.LightState;
import com.jmex.font3d.Font3D;
import com.jmex.font3d.Text3D;
import com.jmex.font3d.effects.Font3DTexture;
import com.jmex.physics.DynamicPhysicsNode;


/**
 *  Helping tool. Colours the connectors of the module selected with the left side of the mouse in simulation environment. 
 *  Currently supports up to 12 connectors coloured with different colours.
 *  @author Konstantinas
 *
 */
//TODO CONSIDER USING LABELING INSTEAD OF COLOURS.
public class ColorConnectorsSelectionTool extends CustomizedPicker {

	/**
	 * The physical simulation
	 */
	private JMESimulation simulation;


	/**
	 * Constructor called in Quick Prototyping.java main window 
	 * @param simulation, the physical simulation	 
	 */
	public ColorConnectorsSelectionTool(JMESimulation simulation) {
		this.simulation = simulation;		
	}

	/* Method executed when the module is selected with the left side of the mouse in simulation environment.
	 * Here not used, because it is enough of pickModuleComponent(JMEModuleComponent component) method (look beneath).
	 * @see ussr.physics.jme.pickers.CustomizedPicker#pickTarget(com.jme.scene.Spatial)
	 */
	@Override
	protected void pickTarget(Spatial target) {	}

	/*  Method executed when the module is selected with the left side of the mouse in simulation environment. 
	 *  Handles actual colouring of connectors.
	 * @see ussr.physics.jme.pickers.CustomizedPicker#pickModuleComponent(ussr.physics.jme.JMEModuleComponent)
	 */
	@Override
	protected void pickModuleComponent(JMEModuleComponent component) {

		int moduleID = component.getModel().getID();
		int nrConnectors = simulation.getModules().get(moduleID).getConnectors().size();		
		
		/*VectorDescription vd = simulation.getModules().get(moduleID).getConnectors().get(0).getPhysics().get(0).getPosition();
		
		 Font3D myfont;
		 myfont = new Font3D(new Font("Arial", Font.PLAIN, 2), 0.1, true, true, true);
		 Font3DTexture fonttexture = new Font3DTexture(TestTextureState.class.getClassLoader().getResource("jmetest/data/model/marble.bmp"));
	     fonttexture.applyEffect(myfont);
	     Text3D mytext = myfont.createText(
                 "ATRON", 0.05f, 0);
         ColorRGBA fontcolor = new ColorRGBA(1, (float) Math.random(), (float) Math.random(), 1);
         mytext.setFontColor(fontcolor);
         //mytext.setLocalTranslation(new Vector3f(2, 1*2, 0));
         
         //rootNode.attachChild(mytext);          
         
         
         for(DynamicPhysicsNode part: component.getNodes()){
        	
        	 mytext.setLocalTranslation(new Vector3f(vd.getX(),vd.getY(),vd.getZ()));
        	 mytext.setLocalRotation(part.getLocalRotation());
            // System.out.println("Child name"+part.getChildren().get(1).getName());//For debugging
        	 part.attachChild(mytext);
         }*/
         
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
