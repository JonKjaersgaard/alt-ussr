package sunTron.API;

import sunTron.futures.Future;
import sunTron.futures.FutureExtend;


public class SunTronAPIImpl implements ISunTronAPI {
	private static final byte LOCALMESSAGE = 0;
	private static final byte ROUTINGMESSAGE = 1;
	private static final byte REMOTEACTION = 2;
	private static final byte CONNECTOR = 3;
	
	public ATRONAPIImpl atronAPIImpl = new ATRONAPIImpl();

	/*
	 * Filter for IR communication
	 * @see ussr.samples.atron.ATRONController#handleMessage(byte[], int, int)
	 */
	public void handleMessage(byte[] message, int messageSize, int channel) {
		switch (message[0]) {
		case LOCALMESSAGE:
			handleLocalMessage(message, messageSize, channel);			
			break;
		case ROUTINGMESSAGE:
			handleRoutingMessage(message, messageSize, channel);
			break;
		case REMOTEACTION:
			handleRemoteAction(message, messageSize, channel);			
			break;
		
		case CONNECTOR:
			handleConnectorRequest(message, messageSize, channel);
			break;
			
		default:
			System.out.println("Error: handleMessage() -> message type not supported ");
			break;
		}
	}



	private void handleConnectorRequest(byte[] message2, int messageSize, int channel) {
		// TODO Auto-generated method stub
		
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

	public FutureExtend extendConnector(int connectNo) {
		// TODO Auto-generated method stub
		FutureExtend f = new FutureExtend(connectNo,this);
		return f;
	}
	@Override
	public void removeActiveFuturesTable(String tmpKey) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void retractConnector(int connectNo) {
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
		return null;
		// TODO Auto-generated method stub
		
	}



	@Override
	public void connectAll() {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void disconnect(int connector) {
		// TODO Auto-generated method stub
		
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
		// TODO Auto-generated method stub
		return 0;
	}



	@Override
	public int getJointPosition() {
		// TODO Auto-generated method stub
		return 0;
	}



	@Override
	public String getName() {
		// TODO Auto-generated method stub
		
		if(atronAPIImpl.getModule().getProperty("name")==null) atronAPIImpl.getModule().waitForPropertyToExist("name");
		return null;
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
		// TODO Auto-generated method stub
		return false;
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
		// TODO Auto-generated method stub
		
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
	public void rotateToDegreeInDegrees(int degrees) {
		// TODO Auto-generated method stub
		
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
	public void yield() {
		// TODO Auto-generated method stub
		
	}
}
