package sunTron.api;

import java.io.DataInputStream;
import java.io.DataOutputStream;

public interface IControllerLoop {

	public void loop();
	public boolean setDataFromDis(DataInputStream dis);
	public DataOutputStream getDos(DataOutputStream dos);

}