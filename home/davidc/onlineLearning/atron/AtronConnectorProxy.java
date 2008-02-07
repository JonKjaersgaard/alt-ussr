package onlineLearning.atron;

import java.awt.Color;
import java.util.List;

import ussr.samples.atron.ATRONController;

public class AtronConnectorProxy {
    private boolean[] disconnectingSemaphore  = new boolean[8];
    private boolean safeDisconnectDebug = false; 
    private ATRONController controller;
    public AtronConnectorProxy(ATRONController controller) {
    	this.controller = controller;
    }
    private float getTime() {
    	return controller.getTime();
    }
    private int getID() {
    	return controller.getModule().getID();
    }
    public boolean safeDisconnect(int connector) {
    	if(controller.canDisconnect(connector)&&!getDisconnectingSemaphore(connector)&&countConnections()>1) {
    		if(safeDisconnectDebug) System.out.println(getID()+"("+getTime() +"): Trying to disconnect "+connector);
    		setDisconnectingSemaphore(connector, true);
    		setSafeToDisconnect(connector, false);
    		sendDisconnectMsg((byte)getID(), (byte) connector, (byte) 8, (byte) connector);
    		List<Color> colors = controller.getModule().getColorList();
    		if(safeDisconnectDebug) controller.getModule().setColor(new Color(1f,1f,0f));
    		//wait for reply or timeout
    		TimeOutManager tom = new TimeOutManager(0.3f, controller);
    		boolean done = false;
    		while(!tom.isTimeout() &&!done) { //timeout after 300 ms
    			if(isSafeToDisconnect(connector)) {
    				if(safeDisconnectDebug) System.out.println(getID()+"("+getTime()+"): It is safe to disconnect");
    				controller.disconnect(connector);
    				TimeOutManager tom2 = new TimeOutManager(5.0f, controller);
    				while(!controller.isDisconnected(connector)) {
    					controller.ussrYield();
    					if(tom2.isTimeout()) {
    						System.out.println(getID()+": deadlock detected while disconnecting");
    					}
    				}
    				done = true;
    			}
    			controller.ussrYield();
    		}
    		if(safeDisconnectDebug) controller.getModule().setColorList(colors);
    		setDisconnectingSemaphore(connector, false);
    		if(controller.isDisconnected(connector)) {
    			if(safeDisconnectDebug) System.out.println(getID()+"("+getTime() +"): Disconnect Performed");
    			return true;
    		}
    		else {
    			if(safeDisconnectDebug) System.out.println(getID()+"("+getTime() +"): Disconnect Stopped");
    			return false;
    		}
    	}
   		return false;
    }
    private int countConnections() {
    	int count =0;
		for(int i=0;i<8;i++) {
			if(controller.isConnected(i)) count++;
		}
		return count;
	}
	public void sendDisconnectMsg(byte ID, byte connector, byte maxHopCount, byte channel) {
    	byte[] msg = new byte[4];
		msg[0] = 'd';
		msg[1] = ID;
		msg[2] = connector;
		msg[3] = maxHopCount;
		controller.sendMessage(msg, (byte)4, (byte)channel);
    }
    public void recieveDisconnectMsg(byte ID, byte connector, byte maxHopCount, byte channel) {
    	if(ID==getID()) {
    		if(channel!=connector) {
    			if(safeDisconnectDebug&&!isSafeToDisconnect(connector)) System.out.println(getID()+"("+getTime()+"): JUBII I can now safely Disconnect "+connector+" maxHopCount = "+maxHopCount);
    			setSafeToDisconnect(connector, true);
    		}
    	}
    	else if(maxHopCount>0) {
    		for(byte i=0;i<8;i++) {
    			if(controller.isConnected(i)&&i!=channel) {
    				if(!disconnectingSemaphore[connector] && !disconnectingSemaphore[i]){
    					sendDisconnectMsg(ID, connector, (byte)(maxHopCount-1), i);
    				}
    			}
    		}
    	}
    }
    private boolean[] safeToDisconnect = new boolean[8]; 
    public synchronized void setSafeToDisconnect(int connector, boolean safe) {
    	safeToDisconnect[connector] = safe;
    }
    
    public synchronized boolean isSafeToDisconnect(int connector) {
    	return safeToDisconnect[connector];
    }
    public synchronized void setDisconnectingSemaphore(int connector, boolean isDisconnecting) {
    	disconnectingSemaphore[connector] = isDisconnecting;
    }
    
    public synchronized boolean getDisconnectingSemaphore(int connector) {
    	return disconnectingSemaphore[connector];
    }

    public void safeConnect(int connector) {
    	//TODO rotate to 90 degree pos
    	//TODO make neighbor do the same - check nothing is rotating?     	
		if(controller.canConnect(connector)) {
			//send msg to other to stop?
			controller.connect(connector);
			TimeOutManager tom = new TimeOutManager(5.0f, controller);
			while(!controller.isConnected(connector)&&!tom.isTimeout()) {
				controller.ussrYield();
			}
		}
	}
}
