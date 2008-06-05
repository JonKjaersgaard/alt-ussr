package sunTron.futures;

import sunTron.API.ISunTronAPI;
import sunTron.API.SunTronAPIUSSR;

public class FutureExtendRemote extends Future {

	private int connector;

	public FutureExtendRemote(int connector,ISunTronAPI sunTronAPI){
		this.sunTronAPI = sunTronAPI;
		this.connector = connector; 		
	}
	
	public String getKey() {
		// TODO Auto-generated method stub
		return null;
	}


	public boolean isCompleted() {
		((SunTronAPIUSSR) sunTronAPI).connectorList[connector] = false;
		sunTronAPI.sendMessage(new byte[]{SunTronAPIUSSR.EXTEND}, (byte) 1,(byte) connector);
		while(((SunTronAPIUSSR) sunTronAPI).connectorList[connector] == false){
			yield();
		}
		// TODO Auto-generated method stub
		return true;
	}

}
