package ussr.builder.genericSelectionTools;

import java.awt.Font;

import jmetest.renderer.state.TestTextureState;

import com.jme.math.Ray;
import com.jme.math.Vector3f;
import com.jme.renderer.ColorRGBA;
import com.jme.renderer.Renderer;
import com.jme.scene.Spatial;
import com.jme.scene.TriMesh;
import com.jme.scene.shape.Sphere;
import com.jmex.font3d.Font3D;
import com.jmex.font3d.Text3D;
import com.jmex.font3d.effects.Font3DTexture;
import com.jmex.physics.DynamicPhysicsNode;

import ussr.model.Connector;
import ussr.model.Module;
import ussr.physics.jme.JMEModuleComponent;
import ussr.physics.jme.JMESimulation;
import ussr.physics.jme.connectors.JMEConnector;
import ussr.physics.jme.pickers.CustomizedPicker;

public class NumberConnectors extends CustomizedPicker {

	/**
	 * The physical simulation
	 */
	private JMESimulation simulation;
	/**
	 * The connector number on module, selected with the mouse in simulation environment
	 */	
	private int selectedConnectorNr = 1000;//just to avoid having default 0 value, which is also number of connector. 

	/**
	 * Constructor called in Quick Prototyping.java main window 
	 * @param simulation, the physical simulation	 
	 */
	public NumberConnectors(JMESimulation simulation) {
		this.simulation = simulation;		
	}



	@Override
	protected void pickModuleComponent(JMEModuleComponent component) {
		int moduleID = component.getModel().getID();
		Module selectedModule =simulation.getModules().get(moduleID);
		
		
		
		for(DynamicPhysicsNode part: component.getNodes()){	
			 
			
			
			

		}
		
		
		
		
		//selectedModule.setRotation(rotation);
		
/*	for(DynamicPhysicsNode part: component.getNodes()){	
		 int amountChildren = part.getQuantity();
		 System.out.println("Amount of children:"+amountChildren);//For debugging
		 for (int child=0;child<amountChildren;child++){
             String childName = part.getChild(child).getName();
             System.out.println("Child nr "+child+" name is:"+childName);//For debugging
             
             if (childName.contains("Connector")){            	 
            	 childName.charAt(childName.length()-1);            	 
             }
             
			}

	}*/
	
		
		//int amountComponents = selectedModule.getNumberOfComponents();
		//int amountConnectors = selectedModule.getConnectors().size();

		
		
	/*	for (int moduleComponent=0;moduleComponent<amountComponents;moduleComponent++){

			JMEModuleComponent componenct =(JMEModuleComponent)simulation.getModules().get(moduleID).getComponent(moduleComponent);			
			System.out.println("geometries:"+componenct.getComponentGeometries().size());//For debugging
			
		}*/
		
		
		

		/*for(DynamicPhysicsNode part: component.getNodes()){	
			String parentName = part.getParent().getName();
			System.out.println("Parent name:"+parentName);//For debugging
			int amountChildren = part.getParent().getChildren().size();
			System.out.println("Amount of children:"+amountChildren);//For debugging

			for (int child=0;child<amountChildren;child++){
              String childName = part.getChild(child).getName();
              System.out.println("Child nr "+child+" name is:"+childName);//For debugging
			}
			
			

		}*/









		//component.getNodes().size();
		//System.out.println("Amount of nodes"+component.getNodes().size());//For debugging




	


		/*		for (int connector =0;connector<4;connector++){

		 


        Connector connect = simulation.getModules().get(moduleID).getConnectors().get(connector);
        connect.getPhysics().get(0).getPosition();
        connect.getPhysics().get(0).getRotation();

        component.getConnector(connector).getNode().attachChild(mytext);



		}*/

		/*    for(DynamicPhysicsNode part: component.getNodes()){       	
       	 mytext.setLocalTranslation(part.getLocalTranslation());
       	 mytext.setLocalRotation(part.getLocalRotation());
           // System.out.println("Child name"+part.getChildren().get(1).getName());//For debugging
       	 part.attachChild(mytext);
        }
		 */




	}

	private void labelConnector(){		


	}

	@Override
	protected void pickTarget(Spatial target) {
		if(target instanceof TriMesh) {			
			String name = simulation.getGeometryName((TriMesh)target);
			if(name!=null && name.contains("Connector")){ 
				//System.out.println("Connector: "+name);//For debugging				
				String [] temp = null;	         
				temp = name.split("#");// Split by #, into two parts, line describing the connector. For example "Connector 1 #1"
				this.selectedConnectorNr= Integer.parseInt(temp[1].toString());// Take only the connector number, in above example "1" (at the end)							
			}
		}	

	}


}
