package atron.delegate;

public abstract class DelegateControllerLoopImpl implements IDelegateControllerLoop {
	public ATRONDelegateAPI atronDelegateController;

	public DelegateControllerLoopImpl(ATRONDelegateAPI atronDelegateAPI){
		this.atronDelegateController = atronDelegateAPI;
	}
	public void setATRONDelegateAPI(ATRONDelegateAPI atronDelegateAPI) {
		this.atronDelegateController = atronDelegateAPI;
	}

}
