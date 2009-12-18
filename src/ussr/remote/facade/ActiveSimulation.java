package ussr.remote.facade;

import java.io.InputStream;
import java.rmi.RemoteException;

import ussr.builder.simulationLoader.SimulationSpecificationConverter;
import ussr.model.Controller;
import ussr.physics.PhysicsFactory;
import ussr.physics.PhysicsParameters;
import ussr.physics.PhysicsSimulation;
import ussr.samples.atron.simulations.ATRONCarController1;

/**
 * Proxy for a simulation running in a different process.
 * 
 * Note that exceeding the buffer size on stdout and stderr can cause the running simulation to pause
 * until buffers are emptied
 * 
 * (Proxy object and handle for the simulation process, used on the frontend side.)
 * @author ups
 *
 */
public interface ActiveSimulation extends RemoteActiveSimulation {
    /**
     * Obtain standard out from running simulation.  Not reading this stream can cause the
     * simulation to stop due to a full output buffer
     * @return stream representing standard out
     */
    public InputStream getStandardOut();
    /**
     * Obtain standard error from running simulation.  Not reading this stream can cause the
     * simulation to stop due to a full output buffer
     * @return stream representing standard error
     */
    public InputStream getStandardErr();
    /**
     * Convenience method for starting a simulation with a single controller
     * @see ussr.remote.RemoteActivativeSimulation.start
     * @param simulationXML XML file describing the robot to put in the simulation
     * @param controller controller class to assign to the modules
     * @throws RemoteException
     */
    public void start(String simulationXML, Class<? extends Controller> controller) throws RemoteException;
    /**
     * Indicates whether the remote simulation is ready, that is, available for remote commands
     * @return true if remote simulation is ready, false otherwise
     */
    public boolean isReady();
    /**
     * Wait for isReady() to return true
     */
    public void waitForReady();
    /**
     * Discard the output generated to standard out (avoid buffers running full)
     */
    public void discardStandardOut();
    /**
     * Discard the output generated to standard err (avoid buffers running full)
     */
    public void discardStandardErr();
    /**
     * Obtain the latest version of the parameters object (if passed from subprocess)
     * @return parameters object
     */
    public ParameterHolder getParameters();
    
}
