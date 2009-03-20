package ussr.builder.genericSelectionTools;

import java.util.ArrayList;
import com.jme.math.Vector3f;
import com.jme.scene.Spatial;
import com.jme.scene.TriMesh;
import ussr.description.geometry.VectorDescription;
import ussr.model.Module;
import ussr.physics.jme.JMEModuleComponent;
import ussr.physics.jme.JMESimulation;
import ussr.physics.jme.pickers.CustomizedPicker;

public class New extends CustomizedPicker {

    private JMESimulation simulation;
	private Module selectedModule;
	private int selectedConnectorNr = 1000;

	private int counter;
	public New (JMESimulation simulation, int counter){
		this.simulation = simulation;
		this.counter = counter;
		
	}

	
	@Override
	protected void pickModuleComponent(JMEModuleComponent component) {
	
		
		selectedModule = component.getModel();
		//VectorDescription positionModule  = selectedModule.getComponent(0).getPosition(); //FOR ATRON
		VectorDescription positionModule  = selectedModule.getComponent(1).getPosition();
		float xModule = positionModule.getX();
		float yModule  = positionModule.getY();
		float zModule  = positionModule.getZ();
		
		VectorDescription positionConnector = selectedModule.getConnectors().get(selectedConnectorNr).getPhysics().get(0).getPosition();
		
		float xConnector = positionConnector.getX();
		float yConnector = positionConnector.getY();
		float zConnector  = positionConnector.getZ();
		
		simulation.getModules().get(counter).setPosition(new VectorDescription(xConnector+(xConnector-xModule),yConnector+(yConnector-yModule),zConnector+(zConnector-zModule)));
		
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
	
	public boolean componentExitst(Vector3f componentPosition, float tolerance){		
		int amountModules = simulation.getModules().size();
		/*For each module in simulation get its components and check if it is already at the modulePosition*/
		for (int module = 0; module<amountModules; module++ ){
			Module currentModule = simulation.getModules().get(module);
			int amountComponents = currentModule.getNumberOfComponents(); 
			Vector3f curretComponentPosition = null;
			/*Get the position of the last component of the module*/
//FIXME PROBLEM WITH MTRAN EXISTS (SOMETIMES ONE OVERLAPS)
			for (int component = 0; component<amountComponents; component++ ){
				curretComponentPosition= currentModule.getComponent(component).getPosition().getVector();
			}            
			/*Check exact position and in interval */
			if (curretComponentPosition.x == componentPosition.x ||curretComponentPosition.x < componentPosition.x+tolerance && curretComponentPosition.x > componentPosition.x-tolerance){								
				if (curretComponentPosition.y ==componentPosition.y||curretComponentPosition.y < componentPosition.y+tolerance && curretComponentPosition.y > componentPosition.y-tolerance){					//System.out.println("IN2");
					if (curretComponentPosition.z ==componentPosition.z||curretComponentPosition.z < componentPosition.z+tolerance && curretComponentPosition.z > componentPosition.z-tolerance){					
						return true;
					}					
				}
			}
		}
		return false;		
	}
	
	

}
