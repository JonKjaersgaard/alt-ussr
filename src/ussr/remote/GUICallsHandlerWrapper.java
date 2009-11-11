package ussr.remote;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class GUICallsHandlerWrapper extends UnicastRemoteObject implements GUICallsHandler{

	protected GUICallsHandlerWrapper() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

}
