package sunTron.API;

import sunTron.futures.Future;
import sunTron.futures.FutureExtend;
import sunTron.futures.FutureRotate;
import ussr.model.ControllerImpl;


public class SunTronAPI extends ControllerImpl implements ISunTronAPI {
	private static final byte LOCALMESSAGE = 0;
	private static final byte ROUTINGMESSAGE = 1;
	private static final byte REMOTEACTION = 2;
	private static final byte CONNECT = 3;
	private static final byte DISCONNECT = 4;
	private static final byte CONNECTNOINFO = 5;
	private static final byte GETCONNECTNO = 6;
	private static final byte CONNECTED = 7;
	private boolean[] connectorList = new boolean[8];
	
	public ATRONControllerImpl atronAPIImpl = new ATRONControllerImpl(this);
	private String connectNoInfo = "99";
	
//	SunTronAPIImpl(){
//		atronAPIImpl.setModule(this.getModule());
//	}
	
	/*
	 * Filter for IR communication
	 * @see ussr.samples.atron.ATRONController#handleMessage(byte[], int, int)
	 */
	public void handleMessage(byte[] message, int messageSize, int channel) {
		System.out.println(getName() + " Message recieved " + message[0]);
		switch (message[0]) {
		case LOCALMESSAGE:
			handleLocalMessage(message, messageSize, channel);			
			break;
		case DISCONNECT:
			handleDisConnectRequest(message, messageSize, channel);
			break;
		case CONNECT:
			handleConnectRequest(message, messageSize, channel);
			break;
		case CONNECTED:
			handleConnectedRequest(message, messageSize, channel);
			break;
		case GETCONNECTNO:
			atronAPIImpl.sendMessage(new byte[]{CONNECTNOINFO,(byte)channel}, (byte) 2, (byte) channel);
			break;
		case CONNECTNOINFO:
			setConnectorNoInfo(message[1]);
			break;
			
			
			
		case ROUTINGMESSAGE:
			handleRoutingMessage(message, messageSize, channel);
			break;
		case REMOTEACTION:
			handleRemoteAction(message, messageSize, channel);			
			break;
		

			
		default:
			System.out.println("Error: handleMessage() -> message type not supported ");
			break;
		}
	}



	private void setConnectorNoInfo(byte b) {
		
		synchronized (connectNoInfo) {
			connectNoInfo = b + "";
		
		}
		
		//connectNoInfo.notifyAll();
		
	}
	private int getConnectorNoInfo() {
//		synchronized (connectNoInfo) {
			while(connectNoInfo == "99") yield();
//				try {
////					connectNoInfo.wait();
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				} 
////				notifyAll();
			int connectNoTmp =Integer.parseInt(connectNoInfo);
			connectNoInfo = "99";
//		}
		return connectNoTmp;
	}



	private void handleConnectedRequest(byte[] message, int messageSize,
			int channel) {
		connectorList[channel]= true;	
		
	}



	private void handleConnectRequest(byte[] message, int messageSize, int channel) {
		if (atronAPIImpl.getModule()!=getModule()){
			atronAPIImpl.setModule(this.getModule());
		}
		connectorList[channel]= true;
		extend(channel);
		atronAPIImpl.sendMessage(new byte[]{CONNECTED}, (byte) 1, (byte) channel);
		System.out.println(getName() + " connectorList handleConnectRequest" + channel);	
	
}



	private void handleDisConnectRequest(byte[] message2, int messageSize, int channel) {
		connectorList[channel]= false;
		if (channel == 0 || channel == 2 || channel == 4 || channel == 6) retract(channel);
		System.out.println(getName() + " connectorList handleDisConnectRequest" + channel);
		
	}



	private void handleRemoteAction(byte[] message2, int messageSize,
			int channel) {
		// TODO Auto-generated method stub
		
	}



	private void handleLocalMessage(byte[] message2, int messageSize,
			int channel) {
		// TODO Auto-generated method stub
		
	}



	private void handleRoutingMessage(byte[] message2, int messageSize,
			int channel) {
		// TODO Auto-generated method stub
		
	}
	public Future retractConnector(byte b) {
		return null;
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean activeFutures() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public void addActiveFuturesTable(String tmpKey, Future f) {
		// TODO Auto-generated method stub
		
	}

	public FutureExtend extend(int connectNo) {
		if (atronAPIImpl.getModule()!=getModule()){
			atronAPIImpl.setModule(this.getModule());
		}
		atronAPIImpl.connect(connectNo);
		System.out.println(getName()+ " extended " + connectNo);
		FutureExtend f = new FutureExtend(connectNo,this);
		return f;
	}
	@Override
	public void removeActiveFuturesTable(String tmpKey) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public Future retract(int connectNo) {
		if (atronAPIImpl.getModule()!=getModule()){
			atronAPIImpl.setModule(this.getModule());
		}
		atronAPIImpl.disconnect(connectNo);
		System.out.println(getName()+ " retracted " + connectNo);
		return null;
		// TODO Auto-generated method stub
		
	}
	@Override
	public byte sendRadioMessage(byte[] message, int destination) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void sleep(long delay) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void waitForAllActiveFutures() {
		// TODO Auto-generated method stub
		
	}
	public FutureExtend connectFuture(int i) {
		FutureExtend f = new FutureExtend(i,(ISunTronAPI)this);
		addActiveFuturesTable(f.getKey(),(Future)f);
		atronAPIImpl.connect(i);
		return f;
	}



	@Override
	public void activate() {
		// TODO Auto-generated method stub
		
	}



	@Override
	public boolean canConnect(int connector) {
		// TODO Auto-generated method stub
		return false;
	}



	@Override
	public boolean canDisconnect(int connector) {
		// TODO Auto-generated method stub
		return false;
	}



	@Override
	public void centerBrake() {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void centerStop() {
		// TODO Auto-generated method stub
		
	}



	@Override
	public FutureExtend connect(int connector) {
		if (atronAPIImpl.getModule()!=getModule()){
			atronAPIImpl.setModule(this.getModule());
		}
		if (connector == 0 || connector == 2 || connector == 4 || connector == 6){
			if (neighbourIsFemal(connector)){
				extend(connector);
				connectorList[connector]= true;
				atronAPIImpl.sendMessage(new byte[]{CONNECTED}, (byte) 1, (byte) connector);
			}else{
				// TODO error handling
			}

			
		}else{
			atronAPIImpl.sendMessage(new byte[]{CONNECT}, (byte) 1, (byte) connector);
			System.out.println("test1");
		}
		
		
		return null;
	
	}

	private boolean neighbourIsFemal(int connector) {
		if (atronAPIImpl.getModule()!=getModule()){
			atronAPIImpl.setModule(this.getModule());
		}
				atronAPIImpl.sendMessage(new byte[]{GETCONNECTNO}, (byte) 1, (byte) connector);			
		int tmp = getConnectorNoInfo();
		if (tmp == 1 || tmp == 3 || tmp == 5 || tmp == 7){
			return true;
		}else{
			return false;
		}
	}



	public void initConnectorList(){
		if (atronAPIImpl.getModule()!=getModule()){
			atronAPIImpl.setModule(this.getModule());
		}
        for(int i = 0;i<8;i++){
        	if (atronAPIImpl.isConnected(i)) connectorList[i] = true;
        }
	}

	@Override
	public void connectAll() {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void disconnect(int connector) {
		if (atronAPIImpl.getModule()!=getModule()){
			atronAPIImpl.setModule(this.getModule());
		}
		connectorList[connector]= false;
		if (connector == 1 || connector == 3 || connector == 5 || connector == 7) retract(connector);
		atronAPIImpl.sendMessage(new byte[]{DISCONNECT}, (byte) 1, (byte) connector);
		System.out.println(getName() + " connectorList false" + connector);		
	}



	@Override
	public void disconnectAll() {
		// TODO Auto-generated method stub
		
	}



	@Override
	public float getAngularPosition() {
		// TODO Auto-generated method stub
		return 0;
	}



	@Override
	public int getAngularPositionDegrees() {
		if (atronAPIImpl.getModule()!=getModule()){
			atronAPIImpl.setModule(this.getModule());
		}
		return atronAPIImpl.getAngularPositionDegrees();
	}



	@Override
	public int getJointPosition() {
		// TODO Auto-generated method stub
		return 0;
	}



	@Override
	public String getName() {
		// TODO Auto-generated method stub
		
//		if(getModule().getProperty("name")==null) getModule().waitForPropertyToExist("name");
		return getModule().getProperty("name");
	}



	@Override
	public byte getTiltX() {
		// TODO Auto-generated method stub
		return 0;
	}



	@Override
	public byte getTiltY() {
		// TODO Auto-generated method stub
		return 0;
	}



	@Override
	public byte getTiltZ() {
		// TODO Auto-generated method stub
		return 0;
	}



	@Override
	public float getTime() {
		// TODO Auto-generated method stub
		return 0;
	}



	@Override
	public void home() {
		// TODO Auto-generated method stub
		
	}



	@Override
	public boolean isConnected(int connector) {
//		if (atronAPIImpl.getModule()!=getModule()){
//			atronAPIImpl.setModule(this.getModule());
//		}
//		return atronAPIImpl.isConnected(connector);
		
		return connectorList[connector];
	}



	@Override
	public boolean isDisconnected(int connector) {
		// TODO Auto-generated method stub
		return false;
	}



	@Override
	public boolean isMale(int connector) {
		// TODO Auto-generated method stub
		return false;
	}



	@Override
	public boolean isObjectNearby(int connector) {
		// TODO Auto-generated method stub
		return false;
	}



	@Override
	public boolean isOtherConnectorNearby(int connector) {
		// TODO Auto-generated method stub
		return false;
	}



	@Override
	public boolean isRotating() {
		// TODO Auto-generated method stub
		return false;
	}



	@Override
	public void rotate(int dir) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void rotateContinuous(float dir) {
		if (atronAPIImpl.getModule()!=getModule()){
			atronAPIImpl.setModule(this.getModule());
		}
		atronAPIImpl.rotateContinuous(dir);
	}



	@Override
	public void rotateDegrees(int degrees) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void rotateToDegree(float rad) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public Future rotateToDegreeInDegrees(int degrees) {
		if (atronAPIImpl.getModule()!=getModule()){
			atronAPIImpl.setModule(this.getModule());
		}
		FutureRotate f = new FutureRotate(degrees, this);
		atronAPIImpl.setBlocking(false);
		atronAPIImpl.rotateToDegreeInDegrees(degrees);
		return f;
		
		
		
		
	}



	@Override
	public byte sendMessage(byte[] message, byte messageSize, byte connector) {
		// TODO Auto-generated method stub
		return 0;
	}



	@Override
	public void setBlocking(boolean blocking) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void setup() {
		// TODO Auto-generated method stub
		
	}



	@Override
	public Future rotateTo(int targetInDegrees) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public void addDebugInfo(String debugInfo) {
		System.out.println("Debug info: " + debugInfo);
		
	}



	@Override
	public String getDebugInfo() {
		// TODO Auto-generated method stub
		return null;
	}



//	@Override
//	public void yield() {
//		// TODO Auto-generated method stub
//		yield();
//	}
}
