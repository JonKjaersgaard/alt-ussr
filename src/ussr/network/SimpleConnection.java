package ussr.network;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class SimpleConnection extends AbstractNetworkConnection {

	public SimpleConnection(int port) {
		super(port);
	}

	@Override
	public boolean activationHook(InputStream input, OutputStream output, Socket connection) {
		// TODO Auto-generated method stub
		return false;
	}

}
