package ussr.remote;

import java.rmi.RemoteException;

import ussr.remote.facade.ActiveSimulation;

public class RemoteSimulationThread extends Thread {

	private boolean  stopFlag = false;
	private boolean  ubNormalTemination = false;
	   
	   public void setUbNormalTemination(boolean ubNormalTemination) {
		this.ubNormalTemination = ubNormalTemination;
	}




	public boolean isUbNormalTemination() {
		return ubNormalTemination;
	}

	private ActiveSimulation activeSimulation;
	   private String simulationXMLFile;
	   
	   
	   public RemoteSimulationThread(ActiveSimulation activeSimulation,final String simulationXMLFile ){
		   this.activeSimulation = activeSimulation;
		   this.simulationXMLFile = simulationXMLFile; 
	   }
	   
		
	  
	   
		public void done(){
			stopFlag = true;
		}
		
		public void run() {
			while(!stopFlag){
			try {
				// Start using an xml file for a robot and a controller (both loaded by simulator process)
				//simulation.start("samples/atron/car.xml", ussr.samples.atron.simulations.ATRONCarController1.class);
				//simulation.start(SnakeCarDemo.class);
				activeSimulation.start(simulationXMLFile);
				//ATRONSimulation1.class,ATRONCarSimulation
				//NO ATRONRoleSimulation.class(broken), CommunicationDemo(null),ATRONTestSimulation.class(null),
				//ConveyorSimulation(null),CrawlerSimulation(null),EightToCarSimulation(broken),SnakeCarDemo.class(null);
			} catch (RemoteException e) {
				ubNormalTemination =true;
				// Normal or abnormal termination, inspection of remote exception currently needed to determine...
				System.err.println("Simulation stopped");
			}
			}
		}
}
