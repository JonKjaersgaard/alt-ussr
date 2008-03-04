package atron.delegate;

import java.util.List;
import ussr.model.Module;
import ussr.model.Sensor;
import ussr.samples.atron.ATRONController;
import ussr.samples.atron.spot.IATRONSPOTAPI;


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
}
