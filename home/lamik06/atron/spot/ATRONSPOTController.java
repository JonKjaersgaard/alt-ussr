package atron.spot;

import java.util.List;

import ussr.model.Sensor;
import ussr.samples.atron.ATRONController;

public abstract class ATRONSPOTController extends ATRONController implements IATRONSPOTAPI{


	@Override
	public List<Sensor> getSensors() {
		// TODO Auto-generated method stub
		return module.getSensors();
	}

	@Override
	public byte sendRadioMessage(byte[] message, int destination) {
		// TODO Auto-generated method stub
		return 0;
	}



}
