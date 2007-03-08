/**
 * Uniform Simulator for Self-reconfigurable (modular) Robots
 * 
 * (C) 2006 University of Southern Denmark
 */
package ussr.samples.atron;

import java.awt.Color;

import ussr.comm.Packet;
import ussr.comm.Receiver;
import ussr.comm.Transmitter;
import ussr.samples.GenericSimulation;

/**
 * A simple controller for the ATRON, controlling connector stickiness based on
 * user-controlled state stored in the main simulator
 * 
 * @author ups
 *
 */
public class ATRONSnakeController1 extends ATRONController {

	private int[] roleOfConnector = {-1,-1,-1,-1,-1,-1,-1,-1};
    /**
     * @see ussr.model.ControllerImpl#activate()
     */
    public void activate() {
        while(true) {
        	if(!module.getSimulation().isPaused()) {
        		snakeControl();
    			if(!GenericSimulation.getConnectorsAreActive()) {
    				disconnectAll();
    			}
        	}
        	Thread.yield();
        }
    }

	private void snakeControl() {
		//oscillate(300.0f); //ossilate in 10 sek
		//muscleControl();
		//delay(1000);
//		System.out.println(module.getProperty("name")+" start to self-reconfigure");
		snakeToWalker();
		//broadCastMessage();
		//testCommunication();
	//	while(true) delay(1000);
	}
	private void muscleControl() {
		while(true) {
			rotateContinuous(1);
			delay(1500);
			rotateContinuous(-1);
			delay(1500);
			//Thread.yield();
		}
	}
	private int getRole() {
		//quick hack mehtod
		String name = module.getProperty("name");
		if(!name.contains("snake")) throw new RuntimeException("Can not select role for this module: not a snake");
		for(int i=100;i>=0;i--) {
			if(name.contains(Integer.toString(i))) return i;
		}
		throw new RuntimeException("Can not select role for this module: no number in name");
	}
	static int globalState=0;
	private void snakeToWalker() {
		//works for 9-module snake only 
		int role = getRole();
		int tempConnector=-1;
		System.out.println("role = "+role);
		/*Step 1*/
		waitForState(0);
		if(role==0||role==8) {
			rotate(1); 
		}
		if(role==1||role==7) {
			rotate(1); 
			rotate(1);
			if(role==1) globalState++;
		}
		if(role==2||role==6) {
			rotate(-1); 
			rotate(-1);
		}
		waitForState(1);
		/*Step 2*/
		if(role==0||role==8) {
			connectAll(); 
			if(role==0) globalState++;
		}
		waitForState(2);
		/*Step 3*/
		if(role==7) {
			disconnectFromRole(8);
		}
		if(role==0) {
			disconnectFromRole(1);
		}
	}
	private boolean disconnectFromRole(int role) {
	//	refreshRoleOfConnector();
		for(int i=0;i<8;i++) {
			if(roleOfConnector(i)==role) {
				if(canDisconnect(i)) {
					disconnect(i);
					return true;
				}
			}
		}
		return false;
	}
	

	private void broadCastMessage() {
		 Transmitter transmitter = module.getTransmitters().get(0);
		 transmitter.send(new Packet(module.getID()));
		 System.out.println(module.getID()+" Message send");
	     Receiver receiver = module.getReceivers().get(0);
	     while(true) {
	         if(receiver.hasData()) {
	        	 Packet data = receiver.getData();
	             module.setColor(Color.RED);
	             System.out.println(module.getID()+" Message recieved from "+data.get(0));
	         }
	     }
	}
	private int roleOfConnector(int i) {
	/*	roleOfConnector[i];
		module.getConnectors().get(i).*/
		return 0;
	}

	private void waitForState(int i) {
		//System.out.println(module.getID()+": Waiting for state "+i);
		while(globalState!=i)
			Thread.yield();
		if(module.getID()==0) System.out.println(module.getID()+": In state "+globalState);
		
	}

	private void oscillate(float sec) {
		float startTime = module.getSimulation().getTime();
		while(module.getSimulation().getTime()<(sec+startTime)) {
			CPGOssilateIteration();
			delay(50);
			//if(module.getID()==1) {
			//	System.out.println(module.getID()+" at "+module.getSimulation().getTime());
			//}
		}
	}
	float E 		= 1;
	float tau 	= (float)(1/(2*Math.PI*0.01));
	float phaseDiff = (float)(0.3*2*Math.PI);
	float alpha 	= 1;
	float offset	= 0.5f;
	float strengt =0.5f;
	float v=0.1f,x=0,S=0;
	
	private void CPGOssilateIteration() {
		float lastTime = module.getSimulation().getTime();
		receiveCPGupdate();
		updateCPG();
		sendCPGupdate();
		CPGRotate();
		if((module.getSimulation().getTime()-lastTime)>(75f/1000)) 
			System.out.println(module.getID()+"I am beeing starved "+(module.getSimulation().getTime()-lastTime));
	
	}
	private void CPGRotate() {
		float percent = (x)/4+offset;
		float angle = (float)(percent*Math.PI*2);
		rotateToDegree(angle);
		if(module.getID()==1) {
			//System.out.println("Test");
			//System.out.println(module.getID()+"("+module.getSimulation().getTime()+"):  angle = ("+360*getAngularPosition()/(Math.PI*2)+", "+360*angle/(Math.PI*2)+")");
		}
	}

	void updateCPG() {
		float vNext = v+(-alpha*v*((x*x+v*v-E)/E)-x+S)/tau;
		float xNext = x+v/tau;
		v = vNext; x = xNext;
		S=0;
	}
	void receiveCPGupdate() {
		for(int i=0;i<8;i++) {
			Receiver receiver = module.getReceivers().get(i);
			//if(receiver.size()!=0) System.out.println(module.getID()+": receiver.size() = "+receiver.size());
			while(receiver.hasData()) {
				Packet packet = receiver.getData();
				float[] data = toFloatArray(packet.getData());
				float x1= data[0];
				float v1= data[1];
			//	System.out.println(module.getID()+": CPG update("+x1+", "+v1+") reveived from "+i);
				S += strengt*((sin(phaseDiff)*x1+cos(phaseDiff)*v1)/(sqrt(x1*x1+v1*v1))-v/(sqrt(x*x+v*v)));
				//System.out.println(module.getID()+": Message recieved at channel "+i+" from "+data.get(0)+" send through channel "+data.get(1));
			}
		}
		//Thread.yield();
	}
	void sendCPGupdate() {
		byte[] data = toByteArray(new float[]{x,v});
		for(int i=0;i<4;i++) {
			if(isOtherConnectorNearby(i)) {
				sendMessage(data, (byte)2, (byte)i);
				//System.out.println(module.getID()+": CPG update ("+x+", "+v+") send to "+i);
			}
		}
	}
	byte[] toByteArray(float[] floats) {
		byte[] bytes = new byte[floats.length*4];
		for(int i=0;i<floats.length;i++) {
			int bits = Float.floatToIntBits(floats[i]);
			bytes[4*i+0] =(byte)( bits >> 24 );
			bytes[4*i+1] =(byte)( (bits << 8) >> 24 );
			bytes[4*i+2] =(byte)( (bits << 16) >> 24 );
			bytes[4*i+3] =(byte)( (bits << 24) >> 24 );
		} 
		return bytes;
	}
	float[] toFloatArray(byte[] bytes) {
		float[] floats = new float[bytes.length/4];
		for(int i=0;i<floats.length;i++) {
			Integer in = new Integer(0);
			int intfromBytes = (bytes[4*i+0]<<24) + (int)((0xff&bytes[4*i+1])<<16) + (int)((0xff&bytes[4*i+2])<<8)+ (int)((0xff&bytes[4*i+3])<<0);			//int intfromBytes = (((int)bytes[4*i+0])<<24)|(((int)bytes[4*i+1])<<16)|(((int)bytes[4*i+2])<<8);
			floats[i] = Float.intBitsToFloat(intfromBytes);
		}
		return floats;
	}
	float sin(float angle) {
		return (float)Math.sin(angle);
	}
	float cos(float angle) {
		return (float)Math.cos(angle);
	}
	private float sqrt(float val) {
		return (float)Math.sqrt(val);
	}
}
