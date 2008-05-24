package sunTron.API;

import java.io.DataInputStream;
import java.io.DataOutputStream;

import sunTron.API.ISunTronAPI;

public interface IControllerLoop {

//	public void setSunTronAPI(ISunTronAPI sunTronAPI);
	public void loop();
	public boolean setDataFromDis(DataInputStream dis);
	public DataOutputStream getDos(DataOutputStream dos);

}