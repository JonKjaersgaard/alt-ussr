package atron.delegate;

import java.util.List;

import atron.spot.ISunTRONAPI;
import atron.spot.IUSSRSunTRONSAPI;
import ussr.model.Module;
import ussr.model.Sensor;
import ussr.samples.atron.ATRONController;
import atron.futures.ATRONFutures;
import atron.futures.ATRONFuturesConnectors;
import atron.futures.ATRONFuturesCenterMotor;


public class ATRONDelegateAPI extends ATRONController implements ISunTRONAPI{

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
	public ATRONFuturesConnectors connectFuture(int i) {
		ATRONFuturesConnectors f = new ATRONFuturesConnectors(i, this);
		super.connect(i);
		return f;
		
	}
	public ATRONFuturesCenterMotor rotateToDegreeInDegreesFutures(int i) {
		ATRONFuturesCenterMotor f = new ATRONFuturesCenterMotor(i, this);
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
	public void addActiveFuturesTable(String tmpKey, ATRONFutures f) {
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
}
