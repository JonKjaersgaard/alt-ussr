package atron.futures;

import com.sun.org.apache.bcel.internal.generic.RETURN;

import ussr.samples.atron.IATRONAPI;
import atron.delegate.ATRONDelegateAPI;
import atron.spot.ISunTRONAPI;

public class ATRONFuturesCenterMotor extends ATRONFutures {
	int target;
//	ATRONDelegateAPI atronDelegateAPI;
	
//	private ICommand command;
	public ATRONFuturesCenterMotor(int i,ISunTRONAPI atronDelegateAPI) {
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


	@Override
	public String getKey() {
		// TODO Auto-generated method stub
		return null;
	}
	

}
