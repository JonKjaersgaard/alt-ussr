package atron.delegate;

import java.util.List;

import atron.spot.IATRONSPOTAPI;
import ussr.model.Module;
import ussr.model.Sensor;
import ussr.samples.atron.ATRONController;
import atron.futures.ATRONFuturesConnectors;
import atron.futures.ATRONFuturesCenterMotor;


public class ATRONDelegateAPI extends ATRONController implements IATRONSPOTAPI{

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
		ATRONFuturesConnectors f = new ATRONFuturesConnectors(i,this);
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
