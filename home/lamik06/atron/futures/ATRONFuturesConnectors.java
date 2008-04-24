package atron.futures;

import ussr.samples.atron.IATRONAPI;

public class ATRONFuturesConnectors extends ATRONFutures{
	int connectorNo;
	public ATRONFuturesConnectors(int i,IATRONAPI atronDelegateAPI) {
		this.atronAPI = atronDelegateAPI;
		connectorNo = i;
	}


	@Override
	public void waitForCompletion() {
		// TODO Auto-generated method stub
		for(int i = 0; i < 100; i++){
			atronAPI.yield();
			System.out.println("Connector " + i);
		}
		
	}



}
