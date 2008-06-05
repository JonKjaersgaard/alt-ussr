package sunTron.futures;

import sunTron.API.ISunTronAPI;
import sunTron.API.SunTronAPIUSSR;

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
		int neighborConnector = ((SunTronAPIUSSR)sunTronAPI).getNeighborConnetorType(connector);
		if(neighborConnector==-1){
			return false;
		}else{
			if (connector == 0 || connector == 2 || connector == 4 || connector == 6){ 
				if (neighborConnector == 1 || neighborConnector == 3 || neighborConnector == 5 || neighborConnector == 7){
					(new FutureExtend(connector,sunTronAPI)).block();
					sunTronAPI.sendMessage(new byte[]{SunTronAPIUSSR.EXTENDED},(byte) 1, (byte)connector);
				}else{
					return false;
				}
			}else{
				if (neighborConnector == 0 || neighborConnector == 2 || neighborConnector == 4 || neighborConnector == 6){
					(new FutureExtendRemote(connector,sunTronAPI)).block();
				}else{
					return false;
				}
			}
			((SunTronAPIUSSR)sunTronAPI).connectorList[connector]=true;
			return true;
		}
		
		
		
	}

}
