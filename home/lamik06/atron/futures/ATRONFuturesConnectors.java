package atron.futures;

import atron.spot.ISunTRONAPI;

public class ATRONFuturesConnectors extends ATRONFutures{
	int connectorNo;
	public ATRONFuturesConnectors(int i,ISunTRONAPI atronDelegateAPI) {
		this.atronAPI = atronDelegateAPI;
		connectorNo = i;
	}



	public void waitForCompletion() {
		// TODO Auto-generated method stub
		for(int i = 0; i < 100; i++){
			atronAPI.yield();
			System.out.println("Connector " + i);
		}
		
	}



	public String getKey() {
		// TODO Auto-generated method stub
		return null;
	}



}
