/**
 * Uniform Simulator for Self-reconfigurable (modular) Robots
 * 
 * (C) 2006 University of Southern Denmark
 */
package ussr.samples.atron.simulations;

import java.awt.Color;

import com.sun.org.apache.bcel.internal.generic.GETSTATIC;

import ussr.comm.Packet;
import ussr.comm.Receiver;
import ussr.comm.Transmitter;
import ussr.samples.GenericSimulation;
import ussr.samples.atron.ATRONController;

/**
 * A sample controller for the ATRON
 * 
 * @author Modular Robots @ MMMI
 *
 */
public class ATRONRoleController1 extends ATRONController {

	private int[] roleOfConnector = {-1,-1,-1,-1,-1,-1,-1,-1};
    /**
     * @see ussr.model.ControllerImpl#activate()
     */
    public void activate() {
        while(true) {
        	if(!module.getSimulation().isPaused()) {
        		/*roleControl();
    			if(!GenericSimulation.getConnectorsAreActive()) {
    				disconnectAll();
    			}*/
        	}
        	Thread.yield();
        }
    }
    public void handleMessage(byte[] message, int messageSize, int channel) {
		if((char)message[0]=='s') {
			roleOfConnector[channel] = message[1];
			if(getRole()>roleOfConnector[channel]) {
				float[] cpgData = toFloatArray(new byte[]{message[3],message[4],message[5],message[6],message[7],message[8],message[9],message[10]});
				addCPGCoupling(cpgData[0],cpgData[1]);
			}
			
			//System.out.println("Message recieved from "+message[1]+" in state "+message[2]);
		}
		else {
			System.out.println("Wrong message type recieved in ATRON controller "+message[0]);
		}
    }
    private int getRole() {
		//return role based on local sensing and NN
    	if(countNeighbors()>3) return 0;
    	else if(countNeighbors()>2) return 1;
    	else return 2;
	}
	private int countNeighbors() {
		int nNeighbors=0;
		for(int i=0;i<8;i++) {
			if(isOtherConnectorNearby(1))
				nNeighbors++;
		}
		return nNeighbors;
	}
	private void roleControl() {
		while(true) {
			if(getRole()==0) {  //bone
				centerStop();
			}
			else if(getRole()==1) { //oscillator
				oscillate(3.0f);
			}
			else if(getRole()==2) { //muscle
				muscleControl();
			}
		}
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
	private int roleOfConnector(int connector) {
		if(!isOtherConnectorNearby(connector)) roleOfConnector[connector]=-1;
		return  roleOfConnector[connector];
	}
	private void oscillate(float sec) {
		float startTime = module.getSimulation().getTime();
		while(module.getSimulation().getTime()<(sec+startTime)) {
			CPGOssilateIteration();
			delay(50);
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
		//receiveCPGupdate();
		updateCPG();
		//sendCPGupdate();
		sendState();
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
	void addCPGCoupling(float x1, float v1) {
		S += strengt*((sin(phaseDiff)*x1+cos(phaseDiff)*v1)/(sqrt(x1*x1+v1*v1))-v/(sqrt(x*x+v*v)));
		//System.out.println(module.getID()+": CPG update("+x1+", "+v1+") reveived");
	}
	void updateCPG() {
		float vNext = v+(-alpha*v*((x*x+v*v-E)/E)-x+S)/tau;
		float xNext = x+v/tau;
		v = vNext; x = xNext;
		S=0;
	}
	void sendState() {
		byte[] msgData = new byte[1+1+1+8]; //type,role,state,cpg1,spg2,cpg3
		byte[] cpgData = toByteArray(new float[]{x,v});
		msgData[0]=(byte) 's';
		msgData[1]=(byte) getRole();
		msgData[2]=(byte) getState();
		for(int i=0;i<cpgData.length;i++)
			msgData[i+3]=(byte) cpgData[i];

		for(int i=0;i<8;i++) {
			if(isOtherConnectorNearby(i)) {
				sendMessage(msgData, (byte)msgData.length, (byte)i);
				//System.out.println(module.getID()+": CPG update ("+x+", "+v+") send to "+i);
			}
		}
	}
	int getState() {
		return 1;
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
