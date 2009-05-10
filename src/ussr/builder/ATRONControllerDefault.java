package ussr.builder;

import java.util.ArrayList;

import ussr.builder.labelingTools.Label;
import ussr.builder.labelingTools.LabelModule;
import ussr.builder.labelingTools.Labeling;
import ussr.builder.labelingTools.Labels;
import ussr.builder.labelingTools.ModuleLabel;
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

	/**
	 * The physical simulation.
	 */
	private PhysicsSimulation simulation;
	
	private final static float //distanceSensitivity = 0.0000001f;// for 6 wheeler
	                           distanceSensitivity = 0.0001f; // for 2 and 4 wheeler
	
	private final static float //initialSpeed = 0.3f;// for 6 wheeler
	                            initialSpeed = 0.5f; // for 2 and 4 wheeler
	
	private final static float maximumSpeed = 1 ;
	/**
	 * Default controller assigned to ATRON modules in simulation environment.
	 * @param simulation, the physical simulation. 
	 */
	public ATRONControllerDefault(PhysicsSimulation simulation){
		this.simulation = simulation;		
	}

  
	/**
	 * Initiates the controller in separate thread
	 */
	@Override
	public void activate() {
		
		/*1 PART FOR USING LABELS ON 2,4,6 AND SO ON WHEELERS(JUST DRIVES FORWARD)*/
		
		/*First solution*/
	/*	Labels labels =  new ModuleLabel(module);
		driveForward (labels,0.9f);*/
		
		/*Second solution*/
		//float speed = 0.9f;
		//if(labels.has("wheel")&& labels.has("right")) rotateContinuous(speed);
		//if(labels.has("wheel")&& labels.has("left")) rotateContinuous(-speed);
		
		
		
		
		
		
		
		
		/*yield();
		this.delay(1000); // rotateContinuous seem to fail sometimes if we do not wait at first 
		
		float lastProx = Float.NEGATIVE_INFINITY;  for printing out proximity data 
		boolean firstTime = true;
		
		while(true) {

			// Enable stopping the car interactively:
			if(!GenericSimulation.getActuatorsAreActive()) { yield(); firstTime = true; continue; }

			// Basic control: first time we enter the loop start rotating and turn the axle
			String labels = module.getProperty(BuilderHelper.getLabelsKey());
			if(firstTime) {
				firstTime = false;
				if (labels ==null){}else{
					Rotate around to the left
					//rotateAround(labels,dir,  true);                
					Rotate around to the right
					//rotateAround(labels,dir,  false);                
					drive(labels, initialSpeed); //for snake turns it in circles to the left
					//driveForward (labels, -dir); // for snake turns it in circles to the right        
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
		

			if(labels.contains("wheel")&& Math.abs(lastProx-max_prox)>0.01) {
				//System.out.println("Proximity "+" max = "+max_prox);
				lastProx = max_prox;
				//sendMessageOnConnectedConnectors(module,1);
				if (labels.contains("left")&& max_prox> distanceSensitivity) {		
					drive(labels,-maximumSpeed);   //rotates only the front left wheel	
					
				}else if(labels.contains("right")&& max_prox>distanceSensitivity) {
					drive(labels,maximumSpeed);				
				}else{
					drive(labels,initialSpeed);
				}
			}	
			// Always call yield sometimes
			yield();
		}*/

	}

	
	/**
	 * @param labels
	 * @param dir
	 */
	private void driveForward (Labels labels, float dir){
		if(labels.has("wheel")&& labels.has("right")) rotateContinuous(dir);
		if(labels.has("wheel")&& labels.has("left")) rotateContinuous(-dir);			
	}
	
	/**
	 * @param labels
	 * @param dir
	 */
	private void stopWheels(Labels labels, float dir){
		if(labels.has("wheel")) centerStop();		 
	}	

	/**
	 * Rotates the ATRON car morphology to the left or to the right.
	 * @param labels, the labels assigned to the module(s).
	 * @param dir, direction of rotation.
	 * @param toLeft, true if the ATRON car should turn to the left, else it will turn to the right. 
	 */
/*	private void rotateAround(String labels,byte dir, boolean toLeft){
		if (toLeft){
			if(labels.contains("wheel1")) rotateContinuous(dir);
			if(labels.contains("wheel2")) rotateContinuous(-dir);
			if(labels.contains("wheel3")) rotateContinuous(dir);
			if(labels.contains("wheel4")) rotateContinuous(-dir);
			if(labels.contains("wheel5")) rotateContinuous(dir);
			if(labels.contains("wheel6")) rotateContinuous(-dir);            
		}else {
			if(labels.contains("wheel1")) rotateContinuous(-dir);
			if(labels.contains("wheel2")) rotateContinuous(dir);
			if(labels.contains("wheel3")) rotateContinuous(-dir);
			if(labels.contains("wheel4")) rotateContinuous(dir);
			if(labels.contains("wheel5")) rotateContinuous(-dir);
			if(labels.contains("wheel6")) rotateContinuous(dir);             
		}

	}*/

}
