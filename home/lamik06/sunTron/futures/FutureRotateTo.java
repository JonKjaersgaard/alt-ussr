package sunTron.futures;

import sunTron.api.ISunTronAPI;

public class FutureRotateTo extends Future {
	int target;

	public FutureRotateTo(int target,ISunTronAPI sunTronAPI) {
		this.sunTronAPI = sunTronAPI;
		this.target = target;
	}
	public boolean isCompleted() {
		return sunTronAPI.getAngularPositionDegrees() == target;
	}
	public String getKey() {
		return "RotateTo("+target+")";
	}

}
