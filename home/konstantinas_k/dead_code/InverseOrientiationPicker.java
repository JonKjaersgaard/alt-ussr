package dead_code;

import com.jme.math.Quaternion;
import com.jmex.physics.DynamicPhysicsNode;

import ussr.description.geometry.VectorDescription;
import ussr.physics.jme.JMEModuleComponent;
import ussr.physics.jme.JMESimulation;
import ussr.samples.atron.ATRON;

public class InverseOrientiationPicker extends CustomizedPicker {

	//DOESN'T WORK RIGHT
	private JMESimulation simulation;
	
	
	public InverseOrientiationPicker(JMESimulation simulation) {
		 this.simulation = simulation;
	 }
	
	
	@Override
	protected void pickModuleComponent(JMEModuleComponent component) {
				
		int moduleID = component.getModel().getID();
		int nrComponents= simulation.getModules().get(moduleID).getColorList().size();//HACK number of colors of components equals the number of components
		//System.out.println("nrComponents: "+nrComponents);//for debugging
		
		for (int c=0; c<nrComponents;c++){
			JMEModuleComponent moduleComponent= (JMEModuleComponent)simulation.getModules().get(moduleID).getComponent(c);			
			Quaternion  rotationQuaternion = simulation.getModules().get(moduleID).getComponent(c).getRotation().getRotation();
			//rotationQuaternion = rotationQuaternion.inverse().add(rotationQuaternion.inverse());
	
			rotateComponent(moduleComponent,rotationQuaternion.inverse());			
		}		
	}
	private void rotateComponent(JMEModuleComponent moduleComponent, Quaternion quaternion){
		for(DynamicPhysicsNode part: moduleComponent.getNodes()){
			part.setLocalRotation(quaternion);
		}
	}

}

