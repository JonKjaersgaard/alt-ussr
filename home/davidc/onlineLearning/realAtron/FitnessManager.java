package onlineLearning.realAtron;

import java.io.IOException;
import java.util.Random;

import onlineLearning.realAtron.comm.AtronSpotComm;
import onlineLearning.realAtron.comm.LearningMessage;
import onlineLearning.realAtron.comm.SpiMessage;
import onlineLearning.realAtron.odometer.MouseVelocityHandler;
import onlineLearning.realAtron.tracking.ATRONTracker;
import onlineLearning.realAtron.tracking.ATRONTrackerGUI;
import com.jme.math.Vector2f;

public class FitnessManager {
	boolean communication = true;
	AtronSpotComm atronComm;
	LearningMessage atronMsg;
	
	enum AtronState {LEARNING, PAUSED};
	AtronState atronState = AtronState.LEARNING;
	
	enum FitnessType {RANDOM, ODOMETER, VISION};
	FitnessType fitnessType = FitnessType.VISION;
	
	MouseVelocityHandler mouse;
	ATRONTrackerGUI trackerGUI;
	ATRONTracker tracker;
	Random rand;
	
	short fitness = 1;
	short timeStep = 1;
	float ticksSum = 0;
	
	int iterationPeriodeMS = 1000;
	
	long lastSendTime = System.currentTimeMillis();
	long lastRecieveTime = System.currentTimeMillis();
	long lastMouseTime = System.currentTimeMillis();
	long lastVisionTime = System.currentTimeMillis();
	
	public FitnessManager() throws IOException {
		init();
		while(true) {
			
			updateFitnessmeter();

			if((System.currentTimeMillis()-lastSendTime)>iterationPeriodeMS) {
				updateFitness();
				if(communication){ 
					if(atronState == AtronState.LEARNING) atronMsg.state = 0;
					if(atronState == AtronState.PAUSED) atronMsg.state = 1;
					atronMsg.timestep = timeStep;
					atronMsg.reward = fitness;
					System.out.println("Sender: "+atronMsg.toString());
					atronComm.send(atronMsg.toByteArray());
				}
				lastSendTime = System.currentTimeMillis();
			}
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	private class AtronReaderThread extends Thread {
		public void run() {
			byte[] data = null;
			SpiMessage spiMsg = new SpiMessage();
			while(true) {
				try {
					data = atronComm.read();
				} catch (IOException e) {e.printStackTrace();}
				spiMsg.fromByteArray(data);
				System.out.println("Modtaget: "+spiMsg.toString());
			}
		}
	}
	private void updateFitnessmeter() {
		if(fitnessType==FitnessType.RANDOM) { 
			//Nothing to do
		}
		else if(fitnessType==FitnessType.ODOMETER) {
			mouseUpdate();
		}
		else if(fitnessType==FitnessType.VISION) {
			visionUpdate();	
		}
	}
	private void init() {
		if(fitnessType==FitnessType.RANDOM) {
			rand = new Random(System.currentTimeMillis());
		}
		else if(fitnessType==FitnessType.ODOMETER) {
			mouse = new MouseVelocityHandler();
		}
		else if(fitnessType==FitnessType.VISION) {
			tracker = new ATRONTracker();
			trackerGUI = ATRONTrackerGUI.startGUI(tracker);
		}
		if(communication) {
			atronComm = new AtronSpotComm();
			atronMsg = new LearningMessage();
			new AtronReaderThread().start();
		}
		waitForReady();
	}
	private void waitForReady() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	
	private void updateFitness() {
		if(fitnessType==FitnessType.RANDOM) {
			fitness = (short)rand.nextInt(255);
		}
		else if(fitnessType==FitnessType.ODOMETER) {
			fitness = (short) (ticksSum*255f/18316f); //9158 = ticks per 60 cm (max distance in 6 seconds), 18316 =>max 20cm/sec	
		}
		else if(fitnessType==FitnessType.VISION) {
			Vector2f pos = trackerGUI.findATRON();
			if(pos!=null) {
				float pixelDist = pos.subtract(oldPos).length();
				float dist = 0.75f*pixelDist;
				if(dist>60) {
					System.out.println("ATRON moves too fast ignore..."+dist);
					fitness = 0;
				}
				else {
					System.out.println("{"+timeStep+", "+pos.x+", "+pos.y+", "+dist+"},");
					fitness = (short) (dist*255/60);
				}
				oldPos = pos;
			}
			else {
				System.out.println("ATRON out of view");
				fitness = 0;
				atronState = AtronState.PAUSED;
			}	
		}
		ticksSum=0;
		timeStep++; 
		/*fitness =(fitness==0)?1:fitness;
		fitness =(fitness==85)?86:fitness;
		fitness =(fitness>=255)?254:fitness;
		timeStep =(timeStep==0)?1:timeStep;
		timeStep =(timeStep==85)?86:timeStep;
		timeStep =(timeStep==255)?1:timeStep;*/
	}
	Vector2f oldPos = new Vector2f();
	//arena size 240x180cm -> 320x240 (1 pixel = 1.333 cm or 1 cm = 0.75 pixel)
	private void visionUpdate() {
		if((System.currentTimeMillis()-lastVisionTime)>500) {
			trackerGUI.findATRON();
			lastVisionTime = System.currentTimeMillis();
		}
	}
	private void mouseUpdate() {
		if((System.currentTimeMillis()-lastMouseTime)>50) {
			Vector2f dPos = mouse.poll();
			ticksSum += (int)Math.sqrt(dPos.x*dPos.x+dPos.y*dPos.y);
			lastMouseTime = System.currentTimeMillis();
		}
	}
	public static void main(String[] args) {
		try {
			new FitnessManager();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
