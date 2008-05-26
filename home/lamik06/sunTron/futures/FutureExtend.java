package sunTron.futures;

import sunTron.API.ISunTronAPI;
import sunTron.API.SunTronAPI;

public class FutureExtend extends Future{
	boolean error = false;
	int connector;
	public FutureExtend(int connector,ISunTronAPI sunTronAPI) {
		this.sunTronAPI = sunTronAPI;
		this.connector = connector; 
		if (connector == 0 || connector == 2 || connector == 4 || connector == 6){ 
			((SunTronAPI) sunTronAPI).atronAPIImpl.connect(connector);
		}else{
			error = true;
			sunTronAPI.addDebugInfo("Error: Can't extend a femal connector no. " + connector);
		}
	}



	public boolean isCompleted() {
		if (error == true){
			return false;
		}else{
			return ((SunTronAPI) sunTronAPI).atronAPIImpl.isConnected(connector);
		}
		
		
	}



	public String getKey() {
		// TODO Auto-generated method stub
		return "Extend("+connector+")";
	}






}
