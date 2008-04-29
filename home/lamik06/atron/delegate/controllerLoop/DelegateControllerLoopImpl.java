package atron.delegate.controllerLoop;

import atron.spot.IUSSRSunTRONSAPI;

public abstract class DelegateControllerLoopImpl implements IDelegateControllerLoop {
	public IUSSRSunTRONSAPI atronDelegateController;

	public DelegateControllerLoopImpl(IUSSRSunTRONSAPI atronDelegateAPI){
		this.atronDelegateController = atronDelegateAPI;
	}
	public void setATRONDelegateAPI(IUSSRSunTRONSAPI atronDelegateAPI) {
		this.atronDelegateController = atronDelegateAPI;
	}

}
