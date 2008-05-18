package sunTron.controllerLoop;

import sunTron.API.ISunTronAPI;

public interface IControllerLoop {

	public void setSunTronAPI(ISunTronAPI sunTronAPI);
	public void controllerLoop();

}
