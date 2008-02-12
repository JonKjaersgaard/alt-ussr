package onlineLearning.realAtron;

import java.io.IOException;
import java.util.Random;
import java.util.StringTokenizer;

import onlineLearning.realAtron.comm.PC2ATRONSender;
import onlineLearning.realAtron.odometer.MouseVelocityHandler;
import onlineLearning.realAtron.tracking.ATRONTracker;
import onlineLearning.realAtron.tracking.ATRONTrackerGUI;

import com.jme.math.Vector2f;

public class FitnessManager {
	PC2ATRONSender pc2atron = new PC2ATRONSender(5322);
	boolean odometer = false;
	boolean communication = true;
	MouseVelocityHandler mouse;
	ATRONTrackerGUI trackerGUI;
	ATRONTracker tracker;
	
	
	int fitness = 1;
	int timeStep = 1;
	float ticksSum = 0;
	
	long lastSendTime = System.currentTimeMillis();
	long lastRecieveTime = System.currentTimeMillis();
	long lastMouseTime = System.currentTimeMillis();
	long lastVisionTime = System.currentTimeMillis();
	
	public FitnessManager() {
		if(odometer) {
			mouse = new MouseVelocityHandler();
		}
		else {
			tracker = new ATRONTracker();
			trackerGUI = ATRONTrackerGUI.startGUI(tracker);
		}
		if(communication) pc2atron.connect();
		waitForReady();
		int count=0;
		while(true) {
			if(communication && (System.currentTimeMillis()-lastRecieveTime)>100) {
				String s = pc2atron.receive();
				if(s!=null&&s.length()!=0) {
					String str = parseString(s);
					if(!str.equals("nop")) {
						System.out.println("Modtaget: "+str);
					}
				}
				lastRecieveTime = System.currentTimeMillis();
			}
			if(odometer) mouseUpdate();
			else visionUpdate();
			
			if((System.currentTimeMillis()-lastSendTime)>6000) {
				update();
				String message = fitness+":"+timeStep+"\n";
				//System.out.println("{"+fitness+", "+timeStep+"}, ");
				//System.out.println("Sender: "+message);
				if(communication) pc2atron.send(message);
				lastSendTime = System.currentTimeMillis();
			}
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	private void waitForReady() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
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
	
	private void update() {
		if(odometer) {
			fitness = (int) (ticksSum*255f/18316f); //9158 = ticks per 60 cm (max distance in 6 seconds), 18316 =>max 20cm/sec
		}
		else {
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
					fitness = (int) (dist*255/60);
				}
				oldPos = pos;
			}
			else {
				System.out.println("ATRON out of view");
				fitness = 0;
			} 
		}
		ticksSum=0;
		
		timeStep++; 
		
		//System.out.println("Mouse moved "+fitness+" in timestep "+timeStep);
		
		fitness =(fitness==0)?1:fitness;
		fitness =(fitness==85)?86:fitness;
		fitness =(fitness>=255)?254:fitness;
		timeStep =(timeStep==0)?1:timeStep;
		timeStep =(timeStep==85)?86:timeStep;
		timeStep =(timeStep==255)?1:timeStep;
	}

	private String parseString(String s) {
		StringTokenizer tokens = new StringTokenizer(s,":");
		StringBuffer str = new StringBuffer();
		while(tokens.hasMoreTokens()) {
			str.append((char)Integer.decode(tokens.nextToken()).intValue());
		}
		return str.toString();
	}
	public static void main(String[] args) {
		new FitnessManager();
	}
}
