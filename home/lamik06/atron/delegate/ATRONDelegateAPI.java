package atron.delegate;

import java.util.List;

import atron.spot.ISunTronAPI;
import atron.spot.IUSSRSunTRONSAPI;
import ussr.model.Module;
import ussr.model.Sensor;
import ussr.samples.atron.ATRONController;
import atron.futures.Future;
import atron.futures.FuturesExtend;
import atron.futures.FutureCenterMotor;


public class ATRONDelegateAPI extends ATRONController implements ISunTronAPI{

	public ATRONDelegateAPI(Module module){
		setModule(module);
		setBlocking(false);
	}
	
	public void activate() {
		// TODO Auto-generated method stub
		
	}

	public byte sendRadioMessage(byte[] message, int destination) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public List<Sensor> getSensors() {
		// TODO Auto-generated method stub
		return module.getSensors();
	}
	public FuturesExtend connectFuture(int i) {
		FuturesExtend f = new FuturesExtend(i, this);
		super.connect(i);
		return f;
		
	}
	public FutureCenterMotor rotateToDegreeInDegreesFutures(int i) {
		FutureCenterMotor f = new FutureCenterMotor(i, this);
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
	public FuturesExtend extendConnector(int connectNo) {
		return null;
		// TODO Auto-generated method stub
		
	}

	@Override
	public void retractConnector(int connectNo) {
		// TODO Auto-generated method stub
		
	}
}
