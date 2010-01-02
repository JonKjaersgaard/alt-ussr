package ussr.remote;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.rmi.RemoteException;


import ussr.aGui.GeneralController;
import ussr.aGui.MainFrameSeparate;
import ussr.aGui.MainFrameSeparateController;
import ussr.aGui.MainFrames;
import ussr.aGui.tabs.controllers.ConsoleTabController;
import ussr.aGui.tabs.controllers.ConstructRobotTabController;
import ussr.aGui.tabs.controllers.SimulationTabController;
import ussr.aGui.tabs.simulation.SimulationTab;
import ussr.aGui.tabs.simulation.SimulationTreeEditors;
import ussr.builder.simulationLoader.SimulationSpecification;
import ussr.remote.facade.ActiveSimulation;
import ussr.remote.facade.GUICallbackControlImpl;


import ussr.remote.facade.RemotePhysicsSimulation;

/**
 * Is responsible for connecting GUI with remote simulation.
 * @author Konstantinas
 */
public class GUIRemoteSimulationAdapter {

	public static final int SERVER_PORT = 54323;
	

	/**
	 * The server to run simulation on.
	 */
	private static SimulationLauncherServer server;

	public GUIRemoteSimulationAdapter(){
		// Start a simulation server (one that manages a number of running simulation processes)
		try {
			server = new SimulationLauncherServer(AbstractSimulationBatch.SERVER_PORT);
		} catch (RemoteException e) {
			throw new Error("Unable to create server: "+e);
		}      
	} 
	
/*	private static RemoteSimulationThread threadRemoteSimulation;
	
	public static RemoteSimulationThread getThreadRemoteSimulation() {
		return threadRemoteSimulation;
	}*/


	/**
	 * Starts simulation from specified xml file.
	 * @param simulationXMLFile
	 */
	public static void runSimulation(final String simulationXMLFile) throws IOException { 

		// Start a simulation server process
		final ActiveSimulation simulation;
		try {
			simulation = server.launchSimulation();
		} catch (IOException e) {
			throw new Error("Unable to start simulation subprocess: "+e);
		}
		
		// Get standard output and error streams and direct them to GUI console tab, so that the buffer is not full, which will cause simulation to stop.
		ConsoleTabController.appendStreamToConsole("StandardOut", simulation.getStandardOut());
		ConsoleTabController.appendStreamToConsole("Error/Info/Warning", simulation.getStandardErr());
		
		
		// FIXME USED TEMPORARY
		// Discard standard out (avoid buffers running full)
        simulation.discardStandardOut();
        // Get standard err, pass it to method that prints it in separate thread
        dumpStream("err", simulation.getStandardErr());


		// Wait for simulation process to be ready to start a new simulation
		if(!simulation.isReady()) {
			System.out.println("Waiting for simulation");
			simulation.waitForReady();
		}
		
		/*Set new position of simulation window relative to GUI*/
		simulation.setWindowPosition(MainFrameSeparate.simWindowX, MainFrameSeparate.simWindowY);
	
		
		// Start a simulation in the remote process
		//threadRemoteSimulation = new RemoteSimulationThread(simulation,simulationXMLFile);
		//threadRemoteSimulation.start();
		
	
		
		new Thread() {
			
			public void run() {
			
				try {
					// Start using an xml file for a robot and a controller (both loaded by simulator process)
					//simulation.start("samples/atron/car.xml", ussr.samples.atron.simulations.ATRONCarController1.class);
					//simulation.start(SnakeCarDemo.class);
					simulation.start(simulationXMLFile);
					//ATRONSimulation1.class,ATRONCarSimulation
					//NO ATRONRoleSimulation.class(broken), CommunicationDemo(null),ATRONTestSimulation.class(null),
					//ConveyorSimulation(null),CrawlerSimulation(null),EightToCarSimulation(broken),SnakeCarDemo.class(null);
				} catch (RemoteException e) {
			
					
					// Normal or abnormal termination, inspection of remote exception currently needed to determine...
					System.err.println("Simulation stopped");
				}
		}
			
		}.start();
		//threadRemoteSimulation.start();
		
		
		// Obtain a reference to remote PhysicsSimulation object (must wait for it to be instantiated remotely)
	/*	if (threadRemoteSimulation.isUbNormalTemination()){
			
		}else{*/
		RemotePhysicsSimulation sim = null;
		while(sim==null) {
			System.out.println("Simulation still null");
			sim = simulation.getSimulation();
			try {
				Thread.sleep(1000);
			} catch(InterruptedException exn) {
				throw new Error("Unexpected interruption");
			}
		}
	
		callBackGUI(simulation,sim);
		//}
	}
	

	/**
	 * Sets remote objects of simulation in GUI and adapts it to simulation.
	 * @param simulation
	 * @param remotePhysicsSimulation
	 */
	private static void callBackGUI(ActiveSimulation simulation,RemotePhysicsSimulation remotePhysicsSimulation)throws IOException {
		remotePhysicsSimulation.setGUICallbackControl(new GUICallbackControlImpl());
		GeneralController.setRemotePhysicsSimulation(remotePhysicsSimulation);
		MainFrameSeparateController.setRendererControl(remotePhysicsSimulation.getRendererControl());
		
		GeneralController.setBuilderControl(remotePhysicsSimulation.getBuilderControl());		
		MainFrameSeparate.setMainFrameSeparateEnabled(true,true);
		MainFrameSeparate.setMainFrameSeparateEnabled(false,true);		
		 
         SimulationSpecification simulationSpecification = simulation.getXmlSimulationProvider().getSimulationSpecification();
         SimulationTabController.setSimulationSpecification(simulationSpecification);
         SimulationTab.addRobotNode(simulationSpecification);
         
         SimulationTreeEditors.update();
         SimulationTab.resizeComponents();
         SimulationTab.setTabVisible(true);       
	}
	
	// FIXME USED TEMPORARY
	 /**
     * Dump an input stream to standard out, prefixing all lines with a fixed text 
     * @param prefix the prefix to use
     * @param stream the stream to dump
     */
    private static void dumpStream(final String prefix, final InputStream stream) {
        new Thread() {
            public void run() {
                BufferedReader input = new BufferedReader(new InputStreamReader(stream));
                while(true) {
                    String line;
                    try {
                        line = input.readLine();
                        if(line==null) break;
                        System.out.println(prefix+": "+line);
                    } catch (IOException e) {
                        throw new Error("Unable to dump stream: "+e); 
                    }
                }
            }
        }.start();
    }
    
   
    
    
}

