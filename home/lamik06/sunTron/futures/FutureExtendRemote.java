package sunTron.futures;

import sunTron.API.ISunTronAPI;
import sunTron.API.SunTronAPI;

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

	@Override
	public boolean isCompleted() {
		((SunTronAPI) sunTronAPI).connectorList[connector] = false;
		sunTronAPI.sendMessage(new byte[]{SunTronAPI.EXTEND}, (byte) 1,(byte) connector);
		while(((SunTronAPI) sunTronAPI).connectorList[connector] == false){
			yield();
		}
		// TODO Auto-generated method stub
		return true;
	}

}
