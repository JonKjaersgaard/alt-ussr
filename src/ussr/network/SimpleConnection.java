package ussr.network;

import java.io.InputStream;
import java.io.OutputStream;

public class SimpleConnection extends AbstractNetworkConnection {

	public SimpleConnection(int port) {
		super(port);
	}

	@Override
	public boolean activationHook(InputStream input, OutputStream output) {
		// TODO Auto-generated method stub
		return false;
	}

}
