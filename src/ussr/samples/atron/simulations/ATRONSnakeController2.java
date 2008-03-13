/**
 * Unified Simulator for Self-Reconfigurable Robots (USSR)
 * (C) University of Southern Denmark 2008
 * This software is distributed under the BSD open-source license.
 * For licensing see the file LICENCE.txt included in the root of the USSR distribution.
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
 * A controller for an ATRON snake robot
 * 
 * @author Modular Robots @ MMMI
 *
 */
public class ATRONSnakeController2 extends ATRONController {

	private int[] roleOfConnector = {-1,-1,-1,-1,-1,-1,-1,-1};
    /**
     * @see ussr.model.ControllerImpl#activate()
     */
    public void activate() {
        while(true) {
        	if(!module.getSimulation().isPaused()) {
        		snakeSelfReconfigure();
        	}
        	Thread.yield();
        }
    }
    private int getRole() {
		String name = module.getProperty("name");
		if(!name.contains("snake")) throw new RuntimeException("Can not select role for this module: not a snake");
		for(int i=100;i>=0;i--) {
			if(name.contains(Integer.toString(i))) return i;
		}
		throw new RuntimeException("Can not select role for this module: no number in name");
	}
    public void handleMessage(byte[] message, int messageSize, int channel) {
		if((char)message[0]=='s') {
			roleOfConnector[channel] = message[1];
			if(message[2]>globalState) {
				globalState =message[2];
				spreadState(globalState, role);
			}
		}
		else {
			//System.out.println("Wrong message type recieved in ATRON controller "+message[0]);
		}
    }
    byte[] msgData = new byte[3]; //type,role,state
	void spreadState(int state, int role) {
		msgData[0]=(byte) 's';
		msgData[1]=(byte) role;
		msgData[2]=(byte) state;
		for(int i=0;i<8;i++) {
			if(isOtherConnectorNearby(i)) {
				sendMessage(msgData, (byte)msgData.length, (byte)i);
			}
		}
	}
	int globalState=0;
	int role = -1;
	int first = 1;
	int itWasI = 0;
	private void snakeSelfReconfigure() {
		role = getRole(); //0-3
		//System.out.println("role = "+role);
		while(true) {
			/*Step 1*/
			waitForState(0);
			if(role==2||role==1) {
				if(first!=1) {
					rotate(-1);
					rotate(-1);
				}
				rotate(-1);
				rotate(-1);
				globalState = 1;
			}
			first = 0;
			spreadState(globalState,role);
			waitForState(1);
			
			/*Step 2*/
			if(globalState==1 && (role==0||role==3)) {
				//while(countNeighbors()==1) Thread.yield();
				//spreadState(globalState,role);
				char seenOtherRole=0;
				while(seenOtherRole==0) {
					int i=0;
					for(i=0;i<8;i++) {
						if(roleOfConnector(i)==3||roleOfConnector(i)==0) {
							seenOtherRole=1;
							if(canConnect(i)) {
								//System.out.println(role+": trying to connect");
								connect(i);
								globalState = 2;
								System.out.println(role+": connect succeded - jubii ");
							}
						}
					}
				}
			}
			role=(role+1)%4;
			spreadState(globalState,role);
			waitForState(2);
			
			/*Step 3*/
			if(globalState==2 && (role==0||role==3)) {
				System.out.println("Prøver "+role);
				while(countNeighbors()==1) Thread.yield();
				spreadState(globalState,role);
				char seenOtherRole=0;
				while(seenOtherRole==0) {
					for(int i=0;i<8;i++) {
						if(canDisconnect(i)&&(roleOfConnector(i)==3||roleOfConnector(i)==0)) {
							//System.out.println(role+": trying to disconnect");
							disconnect(i);
							while(isConnected(i)) Thread.yield();
							globalState = 3;
							System.out.println(role+": disconnect succeded - jubii ");
						}
					}
				}
			}
			spreadState(globalState,role);
			waitForState(3);
			
			/*Step 1*/
			//System.out.println(role+": loop completed jubii!!!");
			
			delay(2000);
			globalState = 0;
		}
	}
	private int countNeighbors() {
		int count=0;
		for(int i=0;i<8;i++) {
			if(isOtherConnectorNearby(i)) {
				count++;
			}
		}
		return count;
	}
		
	private int roleOfConnector(int connector) {
		if(!isOtherConnectorNearby(connector)) roleOfConnector[connector]=-1;
		return  roleOfConnector[connector];
	}

	private void waitForState(int i) {
		while(globalState<i) {
			delay(50);
		}
		//System.out.println(role+": In state "+globalState);
	}
}