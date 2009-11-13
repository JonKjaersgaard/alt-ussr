package ussr.remote.facade;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import ussr.physics.jme.JMESimulation;

public class JMEWorldDescriptionControlWrapper extends UnicastRemoteObject implements WorldDescriptionControlInter {

	
	private JMESimulation jmeSimulation;	

	public JMEWorldDescriptionControlWrapper(JMESimulation jmeSimulation)throws RemoteException {
		this.jmeSimulation = jmeSimulation;
	}
	
	public int getPlaneSize()throws RemoteException{
		return jmeSimulation.getWorldDescription().getPlaneSize();
	}
	
	public void setPlaneSize(int size)throws RemoteException{
	 jmeSimulation.getWorldDescription().setPlaneSize(size);
	}
	
}
