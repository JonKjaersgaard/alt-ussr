package atron.futures;

import atron.spot.ISunTRONAPI;

public class ATRONFuturesConnectors extends ATRONFutures{
	int connectorNo;
	int i = 0;
	public ATRONFuturesConnectors(int i,ISunTRONAPI atronDelegateAPI) {
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
