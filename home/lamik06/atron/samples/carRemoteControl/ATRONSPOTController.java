package atron.samples.carRemoteControl;


import atron.spot.IATRONSPOTAPI;


import ussr.samples.atron.ATRONController;

public abstract class ATRONSPOTController extends ATRONController implements IATRONSPOTAPI {
    
	public byte sendRadioMessage(byte[] message, int destination) {
		// TODO Auto-generated method stub
		return 0;
	}   


    
}