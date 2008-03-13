/**
 * Unified Simulator for Self-Reconfigurable Robots (USSR)
 * (C) University of Southern Denmark 2008
 * This software is distributed under the BSD open-source license.
 * For licensing see the file LICENCE.txt included in the root of the USSR distribution.
 */
package ussr.samples.odin.simulations;

import java.util.Random;

import onlineLearning.tools.RNN;
import onlineLearning.tools.RNNPSOTrainer;
import ussr.samples.odin.OdinController;

import com.jme.math.Vector3f;

/**
 * A simple controller for the ODIN robot, uses tilt sensor to stretch upwards
 * 
 * @author david
 *
 */
public class OdinSampleController3 extends OdinController {
	static Random rand = new Random(System.currentTimeMillis());
	
    float timeOffset=0;
    int myID = 0;
    int role =0;
    int nInput = 3;
    int nHidden = 1;
    int nOutput = 4;
    int nConnection = 40;
    RNNPSOTrainer trainer;
    public OdinSampleController3(String type, float[] gene) {
    	this.type =type;
    	timeOffset = 100*gene[0];
    	myID = rand.nextInt(1000);
    }
	/**
     * @see ussr.model.ControllerImpl#activate()
     */
    public void activate() {
    	while(module.getSimulation().isPaused()) Thread.yield();
    	if(type=="OdinBall") while(true) ballControl();
    	if(type=="OdinHinge") while(true) hingeTestControl();
    	delay(2000);
    	while(true) basicControl();
	}
    static int[] roleCount = new int[4];
    static float roboReward = 0;
    public void basicControl() {
    	trainer = new RNNPSOTrainer(nInput,nHidden,nOutput,nConnection);
    	float[] output;
    	float fitness = 0;
    	rewards(); //initialize reward system
    	while(true) {
    		//System.out.println(getTime()+": *************NEW ITERATION*************");
    		float startTime = getTime();
    		while(startTime+5>getTime()) {// every 2 sec (?)
    			RNN rnn = trainer.getRNN();
    			output = rnn.iterate(getInput());
    	    	role = maxIndexOf(output);
    	    	playRole(role);
    	    	roleCount[role]++;
    	    	float reward = rewards(); 
    	    	fitness += reward;
    	    	roboReward += reward; 
    	    	module.getSimulation().waitForPhysicsStep(false);
    		}
    		//System.out.println("My fitness = "+fitness);
    		trainer.setFitness(fitness);
    		sendParticle(trainer.getPRNN()); //Send particle + fitness to neighbors
    		delay(100); //wait to recieve 
    		trainer.train(getLocalBestRNN(), 0); //Update particle (RNN) based on neighbor particles
        	fitness = 0;
    	}
    }
    private float[] getInput() {
		float[] inputs = new float[3];
		inputs[0] = 1; //bias
		inputs[1] = (type=="OdinMuscle")?1:0; //?
		inputs[2] = (type=="OdinWheel")?1:0; //?
		return inputs;
	}
	
	
	float oldDist = 0;
	private float rewards() {
		Vector3f pos = (Vector3f)module.getSimulationHelper().getModulePos(module).get();
		float dist = pos.distance(new Vector3f(10,0,0));
		float reward = oldDist-dist;
		oldDist = dist;
		return 100*reward;
		/*if(type=="OdinMuscle") {
			if(role==1) return 0.01f;
		}
		else if(type=="OdinBattery") {
			if(role==2) return 0.01f;
		}
		return 0.0f;*/
	}
    private void sendParticle(RNN rnn) {
    	byte[] data = rnn.toByteArray();
    	//System.out.println("Sending RNN with fitness = "+rnn.getFitness());
    	sendMessage(data, (byte)data.length,(byte)0);
    	sendMessage(data, (byte)data.length,(byte)1);
	}
	private void playRole(int role) {
    	if(type=="OdinMuscle") {
    		switch(role) {
    			case 0: passiveControl(); break;
    			case 1: oscillateControl(); break;
    			case 2: contractControl(); break;
    			case 3: expandControl(); break;
    			default: break;
    		}
    	}
    	if(type=="OdinWheel") {
    		switch(role) {
	    		case 0: passiveControl(); break;
				case 1: oscillateControl(); break;
				case 2: contractControl(); break;
				case 3: expandControl(); break;
				default: break;
    		}
		}
	 	if(type=="OdinBattery") {
    		switch(role) {
				case 0: passiveControl(); break;
				case 1: passiveControl(); break;
				case 2: passiveControl(); break;
				case 3: passiveControl(); break;
				default: break;
    		}
		}
	}
	private int maxIndexOf(float[] out) {
    	int maxIndex = 0;
    	float max = out[maxIndex];
		for(int i=0;i<out.length;i++) {
			if(out[i]>max) {
				maxIndex = i;
				max = out[i];
			}
		}
		return maxIndex;
	}
	public void passiveControl() {
		setColor(0, 0, 0);
		disactivate();
    	/*while(true) {
    		delay(1000);  		
    	}*/
    }
	public void contractControl() {
		setColor(1, 0, 0);
		if(type=="OdinMuscle") actuate(0);
		if(type=="OdinWheel") actuate(-1);
	}
	public void expandControl() {
		setColor(0, 1, 0);
		if(type=="OdinMuscle") actuate(1);
		if(type=="OdinWheel") actuate(1);
	}
	public void oscillateControl() {
		setColor(0, 0, 1);
   		float time = getTime()+timeOffset;
		float goal=0;
		if(type=="OdinMuscle") goal = (float)(Math.sin(time)+1)/2f;
		if(type=="OdinWheel") goal = (float)Math.sin(time);
		if(type=="OdinHinge") goal = (float)(Math.sin(5*time)+1)/2f;
		actuate(goal);
		/*byte lastMsg = 0;
		byte[] message = new byte[1];
		if(goal>0.5) {
			message[0] = 'g';
		}
		else { 
			message[0] = 'r';
		}
		if(lastMsg !=message[0]) {
			lastMsg = message[0];
			sendMessage(message, (byte)message.length,(byte)0);
			sendMessage(message, (byte)message.length,(byte)1);	
		}*/
    }
    public void ballControl() {
    	while(true) {
    		try {
                Thread.sleep(100000);
            } catch (InterruptedException e) {
                throw new Error("unexpected");
            }
    	}
    }
    public void hingeTestControl() {
    	while(true) {
    		//actuate(0.9f);
    		while(getTime()<5) {
    			actuate(0.5f);
    			module.getSimulation().waitForPhysicsStep(false);
    		}
    		while(getTime()<30) {
    			oscillateControl();
    			module.getSimulation().waitForPhysicsStep(false);
    		}
    		while(true) {
    			actuate(0.5f);
    			module.getSimulation().waitForPhysicsStep(false);
    		}
    	}
    }
    
    public float getTime() {
		//TODO cpg implementation of this
		return module.getSimulation().getTime();
	}
    float maxFitness = -10000; 
    RNN localBest;
    public void handleMessage(byte[] message, int messageSize, int channel) {
    	/*if(type=="OdinBattery") {
    		if(message[0]=='r') setColor(1, 0, 0);
    		if(message[0]=='g') setColor(0, 1, 0);
    		if(message[0]=='b') setColor(0, 0, 1);
        }
    	System.out.println(type+" ("+myID+"): Package ("+message[0]+") recieved size = "+messageSize+ " on channel "+channel);*/
    	RNN rnn = new RNN(nInput,nHidden,nOutput,nConnection);
    	rnn.fromByteArray(message);
    	//System.out.println("Fitness of recieved = "+rnn.getFitness());
    	if(rnn.getFitness()>maxFitness) {
    		//System.out.println("Local best updated");
    		localBest = rnn;
    		maxFitness = rnn.getFitness();
    	}
    	if(trainer.getPFitness()>maxFitness) {
    		//System.out.println("I have my local best");
    		localBest = trainer.getPRNN();
    		maxFitness = trainer.getPFitness();
    	}
    }
    protected RNN getLocalBestRNN() {
    	if(localBest!=null) {
    		return localBest;
    	}
    	else  {
    		System.out.println("No local best returning random");
    		return new RNN(nInput,nHidden,nOutput,nConnection);
    	}
    }
    public static void setRoboReward(float v) {
        roboReward = v;
    }
}
