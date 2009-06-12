package ussr.builder;

import java.awt.Color;

import ussr.builder.labels.Labels;
import ussr.builder.labels.ModuleLabels;
import ussr.builder.labels.SensorLabels;
import ussr.builder.labels.atron.AllWheels;
import ussr.builder.labels.atron.Axle;
import ussr.builder.labels.atron.FrontAxle;
import ussr.builder.labels.atron.LeftWheels;
import ussr.builder.labels.atron.ModulesLabeledAsWheels;
import ussr.builder.labels.atron.RearAxle;
import ussr.builder.labels.atron.RightWheels;
import ussr.builder.labels.atron.Wheel;
import ussr.comm.GenericReceiver;
import ussr.comm.Packet;
import ussr.comm.Receiver;
import ussr.comm.Transmitter;
import ussr.model.Connector;
import ussr.model.Module;
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
		/*1*///avoidObstacles1();//uncomment for obstacle avoidance,directly involving labels
		/*2*/avoidObstacles2();//
		
		/*3*///communicate();
	}

	/**
	 * Exhibits obstacle avoidance behavior. It is implemented for using labels on 2,4,6 and so on
	 * wheelers (meets obstacles and avoids them by turning to the left).Don't forget to uncomment 
	 * the source code for obstacles in BuilderMultiRobotSimulation.java LOCs 151-152.  
	 */
	private void avoidObstacles1(){		
		yield();
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
	
	private void avoidObstacles2(){		
		yield();
		
		while (true){
			Wheel leftWheels = new LeftWheels((ATRONController)module.getController(),module);
			Wheel rightWheels = new RightWheels((ATRONController)module.getController(),module);
			Wheel allWheels = new AllWheels((ATRONController)module.getController(),module);
			
			Axle rearAxle = new RearAxle((ATRONController)module.getController(),module);
			Axle frontAxle = new FrontAxle((ATRONController)module.getController(),module);
			
			//all wheels(left and right) rotate in the same direction
			leftWheels.rotateContinuously(maximumSpeed);
			rightWheels.rotateContinuously(-maximumSpeed);
			
			//rotate left and right wheels in opposite directions
			//allWheels.rotateContinuously(1);
			
			//rearAxle.turnAngle(15);
			//frontAxle.turnAngle(15);
			
			
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

	private void communicate(){
		yield();
		while(true) {
			
			/*Labels moduleLabels = new ModuleLabels(module);
			if (moduleLabels.has("wheel")){
				int amountConnectors =  module.getConnectors().size();
				for (int connector=0;connector<amountConnectors;connector++){
					Connector  currentConnector = module.getConnectors().get(connector);
					if (currentConnector.isConnected()){						
					byte[] data= {(byte)module.getID(),(byte)connector};				     
					sendMessage(data, (byte)data.length, (byte)connector);
					System.out.println("SendMessage: " + data[0]+","+data[1]);
					}}}
			if (moduleLabels.has("axle")){
				
			};*/
			
			Labels moduleLabels = new ModuleLabels(module);
			if (moduleLabels.has("wheel")){			
				int amountTrasmitters =  module.getTransmitters().size();
				//System.out.println("Amount of transmitters:" + amountTrasmitters);
				for (int t=0;t<amountTrasmitters;t++){
					Transmitter transmitter = module.getTransmitters().get(t);
					transmitter.send(new Packet(module.getID()));
					//System.out.println(module.getID()+" Message send");
				}
			}
			
			if (moduleLabels.has("axle")){
				int amountReceivers =  module.getReceivers().size();
				//System.out.println("Amount of receivers:" + amountReceivers);
				for (int i=0; i<amountReceivers;i++){
					Receiver receiver = module.getReceivers().get(i);					
					if(receiver.hasData()) {						
						Packet data = receiver.getData();
						module.setColor(Color.RED);
						System.out.println(module.getID()+" Message recieved from "+data.get(0));
					}
				}
			}
		}
	}
	  public void handleMessage(byte[] message, int messageSize, int channel) {
		  System.out.println("Message:"+ message);
	    }



}
