package sunTron.delegate.controllerLoop;

import sunTron.API.IUSSRSunTRONSAPI;

public abstract class DelegateControllerLoopImpl implements IDelegateControllerLoop {
	public IUSSRSunTRONSAPI atronDelegateController;

	public DelegateControllerLoopImpl(IUSSRSunTRONSAPI atronDelegateAPI){
		this.atronDelegateController = atronDelegateAPI;
	}
	public void setATRONDelegateAPI(IUSSRSunTRONSAPI atronDelegateAPI) {
		this.atronDelegateController = atronDelegateAPI;
	}

}
