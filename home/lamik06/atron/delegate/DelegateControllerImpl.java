package atron.delegate;

public abstract class DelegateControllerImpl implements IDelegateController {

	protected ATRONDelegateAPI atronDelegateAPI;
	DelegateControllerImpl(ATRONDelegateAPI atronDelegateAPI){
		this.atronDelegateAPI = atronDelegateAPI;
	}
	public void setATRONDelegateAPI(ATRONDelegateAPI atronDelegateAPI) {
		this.atronDelegateAPI = atronDelegateAPI;
	}

}
