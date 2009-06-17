package ussr.builder;

import java.util.HashMap;
import java.util.Map;
import ussr.builder.labels.Labels;
import ussr.builder.labels.ModuleLabels;
import ussr.builder.labels.SensorLabels;
import ussr.builder.labels.abstractLabels.ATRONWheelLabeledModule;
import ussr.builder.labels.abstractLabels.Axle;
import ussr.builder.labels.abstractLabels.ATRONAxleLabeledModule;
import ussr.builder.labels.abstractLabels.Wheel;
import ussr.comm.Packet;
import ussr.model.Module;
import ussr.model.Sensor;
import ussr.samples.atron.ATRONController;

/**
 * Default controller assigned to ATRON modules in simulation environment.
 * Also includes experimentation with labels.
 * @author Konstantinas
 *
 */
public class ATRONControllerDefault extends ATRONController{

	private final static float distanceSensitivity = 1.0f; // for 2  4 and 6 wheelers
	private final static float  //initialSpeed = 0.3f;// for 6 wheeler
	initialSpeed = 0.8f; // for 2 and 4 wheeler
	private final static float maximumSpeed = 1 ;// for 2  4 and 6 wheelers

	/**
	 * Initiates the controller in separate thread
	 */
	@Override
	public void activate() {
		/*In order for ATRON car wheels to drive hack the diameter of the north hemisphere
		 *from 0.935 to 0.9352 in package "ussr.samples.atron" class ATRON, line of code 69*/
		//avoidObstacles();//with direct use of labels.
		avoidObstacles1();// with more abstract use of labels
		//communicate();
	}

	/**
	 * Exhibits obstacle avoidance behavior. It is implemented for using labels on 2,4,6 and so on
	 * wheelers (meets obstacles and avoids them by turning to the left).Don't forget to uncomment 
	 * the source code for obstacles in BuilderMultiRobotSimulation.java LOCs 151-152.  
	 */
	private void avoidObstacles(){		
		yield();
		sleep(1000);
		/*Get labels of the module*/
		Labels moduleLabels = new ModuleLabels(module);
		while (true){
			rotateWheels(moduleLabels, initialSpeed, 0); // rotate wheels slowly
			for(Sensor sensor: module.getSensors()) {
				/*Get labels of the sensor*/
				Labels sesorLabels = new SensorLabels(sensor);
				if(sesorLabels.has("front")) {
					float sensorValue = sensor.readValue();
					/*if the modules are close to the obstacles*/
					System.out.println("SensorValue:"+ sensorValue);
					if (sensorValue<distanceSensitivity &&sensorValue!=0 ) {
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
		if(time>0) sleep(time) ;
	}	
	
	/**
	 * 
	 */
	private void avoidObstacles1(){		
		yield();
		/*Define objects of entities to manipulate, which where previously labeled*/
		//Wheel allWheels = new AllWheels(module);
		//Wheel leftWheels = new LeftWheels(module);
		Wheel leftWheels =  new ATRONWheelLabeledModule(module);
		leftWheels.addLabel("wheel");
		leftWheels.addLabel("left");
		
		Wheel rightWheels = new ATRONWheelLabeledModule(module);
		rightWheels.addLabel("wheel");
		rightWheels.addLabel("right");
		
		Axle frontAxle = new ATRONAxleLabeledModule(module);
		frontAxle.addLabel("axle");
		frontAxle.addLabel("front");
		
		//Wheel rightWheels = new RightWheels(module);
		//Axle frontAxle = new FrontAxle(module);
		//Axle rearAxle = new RearAxle(module);		
		//ProximitySensor leftFrontSensor = new LeftFrontSensor(module); 
		//ProximitySensor rightFrontSensor = new LeftFrontSensor(module);

		while (true){			

			leftWheels.rotateContinuously(-maximumSpeed);			
			rightWheels.rotateContinuously(maximumSpeed);
			sleep(100000);
			leftWheels.stop();
			rightWheels.stop();
			sleep(100000);
			frontAxle.turnAngle(-10);
			//rearAxle.turnAngle(10);
			sleep(100000);			
			leftWheels.rotateContinuously(maximumSpeed);			
			rightWheels.rotateContinuously(-maximumSpeed);
			sleep(100000);
			leftWheels.stop();			
		    rightWheels.stop();
			sleep(100000);
			frontAxle.turnAngle(10);
			//rearAxle.turnAngle(-10);
			sleep(100000);
			//float leftSensorValue =leftFrontSensor.getValue();
			//float rightSensorValue = rightFrontSensor.getValue();

			/*		if (leftSensorValue < distanceSensitivity1|| rightSensorValue < distanceSensitivity1){
				System.out.println("LeftSensor Value" + leftSensorValue);
				System.out.println("RightSensor Value" + rightSensorValue);
				leftWheels.stop();
				rightWheels.stop();
				sleep(100000);


				//leftWheels.rotateContinuously(maximumSpeed);
				//rightWheels.rotateContinuously(-maximumSpeed);
				//sleep(1000);
				//rearAxle.turnAngle(-10);
				//drive (false,maximumSpeed);

				//rearAxle.turnAngle(10);
				//frontAxle.turnAngle(20);
			}else if (leftSensorValue==0f||leftSensorValue==1f||rightSensorValue==0f||rightSensorValue==1f){

			}*/		
		}
	}	

	private void sleep(int time){		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	} 

	Map<Packet,Module> registry = new HashMap<Packet,Module>();

	private void communicate(){
		yield();

		while (true){
			int amountConnectors = module.getConnectors().size();
			for (int connector=0; connector<amountConnectors; connector++){
				if (module.getConnectors().get(connector).isConnected()){
					byte[] message = new byte[] {(byte)module.getID()};
					sendMessage(message, (byte)message.length, (byte)connector);					
					module.getReceivers().size();
					
				}				
			}
			for (int connector=0; connector<amountConnectors; connector++){
				if (module.getConnectors().get(connector).isConnected()){
			packetReceived(module.getReceivers().get(connector));
				}
			}
			
		}

	}

	public void handleMessage(byte[] incoming, int messageSize, int channel) {
		System.out.println("Message:"+ incoming[0]);

	}

}
