package atron.delegate;

import java.util.List;

import atron.spot.IATRONSPOTAPI;
import ussr.model.Module;
import ussr.model.Sensor;
import ussr.samples.atron.ATRONController;
import atron.futures.ATRONFuturesActuators;
import atron.futures.ATRONFuturesCenterMotor;


public class ATRONDelegateAPI extends ATRONController implements IATRONSPOTAPI{

	public ATRONDelegateAPI(Module module){
		setModule(module);
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
	public ATRONFuturesActuators connectFuture(int i) {
		ATRONFuturesActuators f = new ATRONFuturesActuators(i);
		super.connect(i);
		return f;
		
	}
	public ATRONFuturesCenterMotor rotateToDegreeInDegreesFutures(int i) {
		ATRONFuturesCenterMotor f = new ATRONFuturesCenterMotor(i, this);
		setBlocking(false);
		rotateToDegreeInDegrees(i);
		return f;
		
	}
}
