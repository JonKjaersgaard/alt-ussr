package sunTron.futures;

import sunTron.API.ISunTronAPI;
import sunTron.API.SunTronAPI;

public class FutureConnect extends Future {
	int connector;
	public FutureConnect(int connector,ISunTronAPI sunTronAPI) {
		this.sunTronAPI = sunTronAPI;
		this.connector = connector; 

	}
	
	@Override
	public String getKey() {

		return "FutureConnect("+connector+")";
	}

	@Override
	public boolean isCompleted() {
		
		if (connector == 0 || connector == 2 || connector == 4 || connector == 6){ 
			if (((SunTronAPI)sunTronAPI).neighbourIsFemal(connector)){
			(new FutureExtend(connector,sunTronAPI)).block();
			sunTronAPI.sendMessage(new byte[]{SunTronAPI.EXTENDED},(byte) 1, (byte)connector);
			}
		}else{
			(new FutureExtendRemote(connector,sunTronAPI)).block();
		}
		
		return true;
	}

}
