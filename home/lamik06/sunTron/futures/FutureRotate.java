package sunTron.futures;

import sunTron.API.ISunTronAPI;

public class FutureRotate extends Future {
	int target;

	public FutureRotate(int target,ISunTronAPI sunTronAPI) {
		this.sunTronAPI = sunTronAPI;
		this.target = target;
	}
	public boolean isCompleted() {
		return sunTronAPI.getAngularPositionDegrees() == target;
	}

	public String getKey() {
		// TODO Auto-generated method stub
		return null;
	}

}
