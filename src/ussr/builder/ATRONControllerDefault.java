package ussr.builder;

import ussr.builder.labels.Labels;
import ussr.builder.labels.ModuleLabel;
import ussr.builder.labels.SensorLabel;
import ussr.model.Sensor;
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

	/**
	 * Exhibits obstacle avoidance behavior. It is implemented for using labels on 2,4,6 and so on
	 * wheelers (meets obstacles and avoids them by turning to the left).Don't forget to uncomment 
	 * the source code for obstacles in BuilderMultiRobotSimulation.java LOCs 151-152.  
	 */
	private void avoidObstacles(){		
		yield();
		/*Get labels of the module*/
		Labels moduleLabels = new ModuleLabel(module);
		while (true){
			rotateWheels(moduleLabels, initialSpeed, 0); // rotate wheels slowly
			for(Sensor sensor: module.getSensors()) {
				/*Get labels of the sensor*/
				Labels sesorLabels = new SensorLabel(sensor);
				if(sesorLabels.has("front")) {
					float sensorValue = sensor.readValue();
					/*if the modules are close to the obstacles*/
					if (sensorValue>distanceSensitivity) {
						/*if it is left wheel module*/
						if(moduleLabels.has("wheel") && moduleLabels.has("left"))
							rotateWheels(moduleLabels,-maximumSpeed,1000);
						/*if it is right wheel module*/
						else if(moduleLabels.has("wheel") && moduleLabels.has("right"))
							rotateWheels(moduleLabels,maximumSpeed,1000);
					}
				}
			}			
		}		
	}

	/**
	 * Rotates wheel modules in appropriate directions for specific amount of time
	 * @param labels, the labels of the module.
	 * @param dir, direction of rotation and speed.
	 * @param time, the time of execution.
	 */
	private void rotateWheels (Labels labels, float dir, int time){
		if(labels.has("wheel")&& labels.has("right")) rotateContinuous(dir);
		if(labels.has("wheel")&& labels.has("left")) rotateContinuous(-dir);
		if(time>0) try { Thread.sleep(time); } catch(InterruptedException 
				exn) { ; }
	}
}
