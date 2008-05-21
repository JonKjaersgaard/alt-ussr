package sunTron.API;

import java.util.List;

import sunTron.futures.Future;
import sunTron.futures.FutureRotate;
import sunTron.futures.FutureExtend;
import ussr.model.Sensor;



public class SunTronDelegateAPI implements ISunTronAPI{

	SunTronAPI sunTronAPIImpl = new SunTronAPI();
	public void activate() {
		// TODO Auto-generated method stub
		
	}

	public byte sendRadioMessage(byte[] message, int destination) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public List<Sensor> getSensors() {
		// TODO Auto-generated method stub
		return null;
	}
	public FutureExtend connectFuture(int i) {
		return sunTronAPIImpl.connectFuture(i);
		
	}
	public FutureRotate rotateToDegreeInDegreesFutures(int i) {
		FutureRotate f = new FutureRotate(i, this);
		setBlocking(false);
		rotateToDegreeInDegrees(i);
		return f;
		
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

	@Override
	public void removeActiveFuturesTable(String tmpKey) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sleep(long delay) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void waitForAllActiveFutures() {
		// TODO Auto-generated method stub
		
	}



	@Override
	public Future retract(int connectNo) {
		return null;
		// TODO Auto-generated method stub
		
	}

	@Override
	public FutureExtend extend(int connectNo) {
		// TODO Auto-generated method stub
		return null;
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
		// TODO Auto-generated method stub
		return null;
		
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
	public void handleMessage(byte[] message, int messageSize, int channel) {
		// TODO Auto-generated method stub
		
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
	public Future rotateToDegreeInDegrees(int degrees) {
		return sunTronAPIImpl.rotateToDegreeInDegrees(degrees);
		
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

	@Override
	public Future rotateTo(int targetInDegrees) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public void addDebugInfo(String debugInfo) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getDebugInfo() {
		// TODO Auto-generated method stub
		return null;
	}
}
