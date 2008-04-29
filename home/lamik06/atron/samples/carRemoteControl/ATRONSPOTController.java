package atron.samples.carRemoteControl;


import java.util.List;

import atron.futures.ATRONFutures;
import atron.spot.IUSSRSunTRONSAPI;


import ussr.model.Sensor;
import ussr.samples.atron.ATRONController;

public abstract class ATRONSPOTController extends ATRONController implements IUSSRSunTRONSAPI {
    
	public byte sendRadioMessage(byte[] message, int destination) {
		// TODO Auto-generated method stub
		return 0;
	}   
	@Override
	public List<Sensor> getSensors() {
		// TODO Auto-generated method stub
		return null;
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