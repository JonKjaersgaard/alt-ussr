package sunTron.api;

import java.io.DataInputStream;
import java.io.DataOutputStream;

public abstract class ControllerLoopUSSRImpl implements IControllerLoop{
	public ISunTronAPI sunTronAPI;

	public ControllerLoopUSSRImpl(){
	}
	
	public ControllerLoopUSSRImpl(ISunTronAPI sunTronAPI){
		this.sunTronAPI = sunTronAPI;
	}
	@Override
	public DataOutputStream getDos(DataOutputStream dos) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean setDataFromDis(DataInputStream dis) {
		// TODO Auto-generated method stub
		return false;
	}

	public void setSunTronAPI(ISunTronAPI sunTronAPI) {
		this.sunTronAPI = sunTronAPI;
	}

}
