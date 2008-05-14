package atron.futures;

import atron.spot.ISunTronAPI;

public class FuturesExtend extends Future{
	int connectorNo;
	int i = 0;
	public FuturesExtend(int i,ISunTronAPI atronDelegateAPI) {
		this.atronAPI = atronDelegateAPI;
		connectorNo = i;
	}



	public boolean isCompleted() {
		boolean state = false;
		// TODO Auto-generated method stub
		if(i > 100) state  = true; 
		i++;
		
		return state;
		
	}



	public String getKey() {
		// TODO Auto-generated method stub
		return null;
	}



}
