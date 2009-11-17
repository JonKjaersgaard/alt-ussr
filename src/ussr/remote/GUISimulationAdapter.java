package ussr.remote;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.rmi.RemoteException;


import ussr.aGui.GeneralController;
import ussr.aGui.MainFrameSeparate;
import ussr.aGui.MainFrameSeparateController;
import ussr.aGui.MainFramesInter;
import ussr.aGui.tabs.controllers.ConsoleTabController;
import ussr.aGui.tabs.controllers.ConstructRobotTabController;
import ussr.aGui.tabs.controllers.SimulationTabController;
import ussr.aGui.tabs.controllers.TabsControllers;
import ussr.builder.BuilderMultiRobotPreSimulation;

import ussr.remote.facade.ActiveSimulation;
import ussr.remote.facade.GUICallbackControlImpl;

import ussr.remote.facade.RemotePhysicsSimulation;
import ussr.samples.atron.simulations.ATRONRoleSimulation;
import ussr.samples.atron.simulations.CommunicationDemo;

/**
 * Frontend example: a main application that starts a single simulation using the remote facility 
 * provided by this package
 * @author ups
 */
public class GUISimulationAdapter {

    public static final int SERVER_PORT = 54323;

	/**
     * @param args
     * @throws IOException 
     */
    public static void main(String[] args) {
        try {
        	String simulationXMLFile = "samples/simulations/atron/simpleVehicleSim.xml";
            consoleSimulationExample(simulationXMLFile);
        } catch(IOException exn) {
            System.err.println("Program terminated with "+exn.getClass().getName()+" exception");
            // Explicitly stop program (RMI server still running)
            System.exit(0);
        }
    }
    
    public static void consoleSimulationExample(final String simulationXMLFile) throws IOException { 
        // Start a simulation server (one that manages a number of running simulation processes)
        SimulationLauncherServer server = new SimulationLauncherServer(GUISimulationAdapter.SERVER_PORT);
        // Start a simulation server process
        final ActiveSimulation simulation = server.launchSimulation();
        // Discard standard out (avoid buffers running full)
        //simulation.discardStandardOut();
        // Get standard err, pass it to method that prints it in separate thread
        dumpStream("err", simulation.getStandardErr());
        // Wait for simulation process to be ready to start a new simulation
        if(!simulation.isReady()) {
            System.out.println("Waiting for simulation");
             simulation.waitForReady();
        }
        // Start a simulation in the remote process
        new Thread() {
            public void run() {
                try {
                    // Start using an xml file for a robot and a controller (both loaded by simulator process)
                   //simulation.start("samples/atron/car.xml", ussr.samples.atron.simulations.ATRONCarController1.class);
                	simulation.start(ATRONRoleSimulation.class);
                	//simulation.start("samples/atron/car.xml");
                	//simulation.start(simulationXMLFile);

                } catch (RemoteException e) {
                    // Normal or abnormal termination, inspection of remote exception currently needed to determine...
                    System.err.println("Simulation stopped");
                }
            }
        }.start();
        System.out.println("Simulation started as background thread");
        // Obtain a reference to remote PhysicsSimulation object (must wait for it to be instantiated remotely)
        
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
        //ArrayList<Object> 
        
        
        sim.setGUICallbackControl(new GUICallbackControlImpl() );
        
        GeneralController.setRemotePhysicsSimulation(sim);
        MainFrameSeparateController.setRendererControl(sim.getRendererControl());
        //MainFrameSeparateController.setBuilderControl(sim.getBuilderControl());
       //TabsControllers.setBuilderController(sim.getBuilderControl());
        GeneralController.setBuilderController(sim.getBuilderControl());
        MainFrameSeparate.setMainFrameSeparateEnabled(true);
        SimulationTabController.updateTable();
       
        ConsoleTabController.setInputStream(simulation.getStandardOut());
        
/*        while(true) {
            System.out.println(" remote simulation isPaused()="+sim.isPaused());
            try {
                Thread.sleep(1000);
            } catch(InterruptedException exn) {
                throw new Error("Unexpected interruption");
            }
        }*/
    }

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
