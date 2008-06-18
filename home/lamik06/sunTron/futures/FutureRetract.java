package sunTron.futures;

import sunTron.api.ISunTronAPI;

public class FutureRetract extends Future{
	int connector;
	int i = 0;
	public FutureRetract(int connector,ISunTronAPI sunTronAPI) {
		this.sunTronAPI = sunTronAPI;
		this.connector = connector;
	}



	public boolean isCompleted() {
		return sunTronAPI.isDisconnected(connector);
	}



	public String getKey() {
		return "Retract("+connector+")";
	}



}
