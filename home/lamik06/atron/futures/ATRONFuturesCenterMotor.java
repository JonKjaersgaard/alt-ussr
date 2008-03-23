package atron.futures;

import atron.delegate.ATRONDelegateAPI;

public class ATRONFuturesCenterMotor extends ATRONFutures {
	int target;
	ATRONDelegateAPI atronDelegateAPI;
	
	private ICommand command;
	public ATRONFuturesCenterMotor(int i,ATRONDelegateAPI atronDelegateAPI) {
		this.atronDelegateAPI = atronDelegateAPI;
		target = i;// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isAvalible() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void waitForComplition() {
		// TODO Auto-generated method stub
//		System.out.println("wait for completion");
		while(atronDelegateAPI.getAngularPositionDegrees() != target){
        	System.out.println("waitForCompletion() -> getAngularPositionDegrees() -> " + atronDelegateAPI.getAngularPositionDegrees());
			atronDelegateAPI.yield();
		}
//		System.out.println("wait for complition -> done");
	}

	@Override
	public void onCompletion(ICommand command) {
		this.command = command;
		start();
	}
	public void  run() {
		waitForComplition();
		command.execute();
	}
}
