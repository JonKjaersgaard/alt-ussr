package atron.delegate.controllerLoop;

import atron.spot.IATRONSPOTAPI;

public abstract class DelegateControllerLoopImpl implements IDelegateControllerLoop {
	public IATRONSPOTAPI atronDelegateController;

	public DelegateControllerLoopImpl(IATRONSPOTAPI atronDelegateAPI){
		this.atronDelegateController = atronDelegateAPI;
	}
	public void setATRONDelegateAPI(IATRONSPOTAPI atronDelegateAPI) {
		this.atronDelegateController = atronDelegateAPI;
	}

}
