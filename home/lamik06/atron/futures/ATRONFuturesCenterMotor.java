package atron.futures;

import atron.spot.ISunTRONAPI;

public class ATRONFuturesCenterMotor extends ATRONFutures {
	int target;

	public ATRONFuturesCenterMotor(int target,ISunTRONAPI atronDelegateAPI) {
		this.atronAPI = atronDelegateAPI;
		this.target = target;// TODO Auto-generated constructor stub
	}
	public boolean isCompleted() {
		boolean state = false;
		if(atronAPI.getAngularPositionDegrees() == target)state = true;
		return state;
	}

	public String getKey() {
		// TODO Auto-generated method stub
		return null;
	}
	

}
