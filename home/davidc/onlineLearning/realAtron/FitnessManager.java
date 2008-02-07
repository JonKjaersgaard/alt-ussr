package onlineLearning.realAtron;

import java.util.Random;
import java.util.StringTokenizer;

import com.jme.math.Vector2f;

public class FitnessManager {
	PC2ATRONSender pc2atron = new PC2ATRONSender(5322);
	MouseVelocityHandler mouse = new MouseVelocityHandler();
	
	int fitness = 1;
	int timeStep = 1;
	float ticksSum = 0;
	
	long lastSendTime = System.currentTimeMillis();
	long lastRecieveTime = System.currentTimeMillis();
	long lastMouseTime = System.currentTimeMillis();
	
	public FitnessManager() {
		pc2atron.connect();
		int count=0;
		while(true) {
			if((System.currentTimeMillis()-lastRecieveTime)>100) {
				String s = pc2atron.receive();
				if(s!=null&&s.length()!=0) {
					String str = parseString(s);
					if(!str.equals("nop")) {
						System.out.println("Modtaget: "+str);
					}
				}
				lastRecieveTime = System.currentTimeMillis();
			}
			if((System.currentTimeMillis()-lastMouseTime)>50) {
				Vector2f dPos = mouse.poll();
				ticksSum += (int)Math.sqrt(dPos.x*dPos.x+dPos.y*dPos.y);
				lastMouseTime = System.currentTimeMillis();
			}
			if((System.currentTimeMillis()-lastSendTime)>6000) {
				update();
				String message = fitness+":"+timeStep+"\n";
				System.out.println("{"+fitness+", "+timeStep+"}, ");
				//System.out.println("Sender: "+message);
				pc2atron.send(message);
				lastSendTime = System.currentTimeMillis();
			}
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void update() {
		fitness = (int) (ticksSum*255f/18316f); //9158 = ticks per 60 cm (max distance in 6 seconds), 18316 =>max 20cm/sec
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
