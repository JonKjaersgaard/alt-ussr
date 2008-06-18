package sunTron.api;

import ussr.model.Module;
import ussr.samples.atron.ATRONController;

public class ATRONControllerImpl extends ATRONController {

//	public ATRONAPIImpl(Module module1) {
//		this.setModule(module1);
//		// TODO Auto-generated constructor stub
//	}
	SunTronAPIUSSR sunTronAPI;

	public ATRONControllerImpl(SunTronAPIUSSR sunTronAPI) {
		this.sunTronAPI = sunTronAPI;// TODO Auto-generated constructor stub
	}
	public void activate() {
		// TODO Auto-generated method stub
	}
	@Override
	public void handleMessage(byte[] message, int messageSize, int channel) {
		// TODO Auto-generated method stub
		sunTronAPI.handleMessage(message, messageSize, channel);
	}

}
