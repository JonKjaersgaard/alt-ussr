package sunTron.futures;

import sunTron.API.ISunTronAPI;

public class FutureCenterMotor extends Future {
	int target;

	public FutureCenterMotor(int target,ISunTronAPI atronDelegateAPI) {
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
