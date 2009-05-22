package ussr.builder;

import java.util.ArrayList;

import ussr.builder.labelingTools.LabelModuleTemplate;
import ussr.builder.labelingTools.LabelingTemplate;
import ussr.builder.labels.Label;
import ussr.builder.labels.Labels;
import ussr.builder.labels.ModuleLabel;
import ussr.model.Connector;
import ussr.model.Module;
import ussr.model.Sensor;
import ussr.physics.PhysicsSimulation;

import ussr.samples.GenericSimulation;
import ussr.samples.atron.ATRONController;

/**
 * Default controller assigned to ATRON modules in simulation environment.
 * Also includes experimentation with labels.
 * @author Konstantinas
 *
 */
public class ATRONControllerDefault extends ATRONController  {

	
	
	private final static float  //distanceSensitivity = 0.000000001f;// for 6 wheeler
	                           distanceSensitivity = 0.0001f; // for 2 and 4 wheeler
	
	private final static float // initialSpeed = 0.3f;// for 6 wheeler
	                           initialSpeed = 0.8f; // for 2 and 4 wheeler
	
	private final static float maximumSpeed = 1 ;
  
	/**
	 * Initiates the controller in separate thread
	 */
	@Override
	public void activate() {		
		//avoidObstacles();
	}
	
	private void avoidObstacles(){
		/* FOR USING LABELS ON 2,4,6 AND SO ON WHEELERS(MEETS OBASTACLES AND AVOIDS THEM)*/
		//Don't forget to uncomment the source code for obstacles in BuilderMultiRobotSimulation LOCs 151-152 
		yield();
		this.delay(1000); // rotateContinuous seem to fail sometimes if we do not wait at first 
		boolean firstTime = true;
		
		while(true) {

			// Enable stopping the car interactively:
			if(!GenericSimulation.getActuatorsAreActive()) { yield(); firstTime = true; continue; }

			// Basic control: first time we enter the loop start rotating and turn the axle
			Labels labels = new ModuleLabel(module);
			if(firstTime) {
				firstTime = false;
				if (labels ==null){}else{				               
					rotateWheels(labels, initialSpeed);					
				}  
			} 
			// Print out proximity information
			float max_prox = Float.NEGATIVE_INFINITY;
			for(Sensor s: module.getSensors()) {
				//System.out.println("Sensors: " + s.getName().toString());
				if(s.getName().startsWith("Proximity")) {
					float v = s.readValue();
					max_prox = Math.max(max_prox, v);
				}
			}			
		

			if(labels.has("wheel")) {
				//System.out.println("Proximity "+" max = "+max_prox);			
				if (labels.has("left")&& max_prox> distanceSensitivity) {		
					rotateWheels(labels,-maximumSpeed);   //rotates only the front left wheel					
				}else if(labels.has("right")&& max_prox>distanceSensitivity) {
					rotateWheels(labels,maximumSpeed);  //rotates only the front right wheel			
				}else{
					rotateWheels(labels,initialSpeed);
				}
			}	
			// Always call yield sometimes
			yield();
		}
	}

	
	/**
	 * @param labels
	 * @param dir
	 */
	private void rotateWheels (Labels labels, float dir){
		if(labels.has("wheel")&& labels.has("right")) rotateContinuous(dir);
		if(labels.has("wheel")&& labels.has("left")) rotateContinuous(-dir);			
	}
}
