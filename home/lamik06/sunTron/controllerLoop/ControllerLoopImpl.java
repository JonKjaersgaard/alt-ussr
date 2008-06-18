package sunTron.controllerLoop;

import sunTron.api.IControllerLoop;
import sunTron.api.ISunTronAPI;

public abstract class ControllerLoopImpl implements IControllerLoop {
	public ISunTronAPI sunTronAPI;

	public ControllerLoopImpl(ISunTronAPI sunTronAPI){
		this.sunTronAPI = sunTronAPI;
	}
	public void setSunTronAPI(ISunTronAPI sunTronAPI) {
		this.sunTronAPI = sunTronAPI;
	}

}
