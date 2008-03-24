package atron.futures;

import ussr.samples.atron.IATRONAPI;
import atron.delegate.ATRONDelegateAPI;

public class ATRONFuturesCenterMotor extends ATRONFutures {
	int target;
//	ATRONDelegateAPI atronDelegateAPI;
	
//	private ICommand command;
	public ATRONFuturesCenterMotor(int i,IATRONAPI atronDelegateAPI) {
		this.atronAPI = atronDelegateAPI;
		target = i;// TODO Auto-generated constructor stub
	}


	@Override
	public void waitForCompletion() {
		// TODO Auto-generated method stub
//		System.out.println("wait for completion");
		while(atronAPI.getAngularPositionDegrees() != target){
        	System.out.println("waitForCompletion() -> getAngularPositionDegrees() -> " + atronAPI.getAngularPositionDegrees());
			atronAPI.yield();
		}
//		System.out.println("wait for complition -> done");
	}


}
