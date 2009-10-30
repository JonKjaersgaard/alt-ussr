package ussr.remote;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.rmi.RemoteException;

import ussr.remote.facade.RemotePhysicsSimulation;

/**
 * Example of how a main application can start a single simulation using the remote facility provided by this package
 * @author ups
 */
public class ConsoleSimulationServer {

    /**
     * @param args
     * @throws IOException 
     */
    public static void main(String[] args) throws IOException {
        SimulationLauncherServer server = new SimulationLauncherServer(ConsoleSimulationServer.SERVER_PORT);
        final ActiveSimulation simulation = server.launchSimulation();
        dumpStream("out", simulation.getStandardOut());
        eatStream("err", simulation.getStandardErr());
        if(!simulation.isReady()) {
            System.out.println("Waiting for simulation");
             simulation.waitForReady();
        }
        new Thread() {
            public void run() {
                try {
                    simulation.start("samples/atron/car.xml", ussr.samples.atron.simulations.ATRONCarController1.class);
                } catch (RemoteException e) {
                    System.err.println("Simulation stopped");
                }
            }
        }.start();
        System.out.println("Simulation started as background thread");
        while(true) {
            RemotePhysicsSimulation sim = simulation.getSimulation();
            if(sim==null)
                System.out.println("Simulation still null");
            else
                System.out.println("...isPaused()="+sim.isPaused());
            try {
                Thread.sleep(1000);
            } catch(InterruptedException exn) {
                throw new Error("Unexpected interruption");
            }
        }
    }

    private static void eatStream(String string, final InputStream stream) {
        new Thread() {
            public void run() {
                try {
                    while(stream.read()!=-1);
                    } catch (IOException e) {
                        throw new Error("Unable to dump stream: "+e); 
                    }
                }
        }.start();
    }

    private static void dumpStream(final String name, final InputStream stream) {
        new Thread() {
            public void run() {
                BufferedReader input = new BufferedReader(new InputStreamReader(stream));
                while(true) {
                    String line;
                    try {
                        line = input.readLine();
                        if(line==null) break;
                        System.out.println(name+": "+line);
                    } catch (IOException e) {
                        throw new Error("Unable to dump stream: "+e); 
                    }
                }
            }
        }.start();
    }

    public static final int SERVER_PORT = 54323;

}
