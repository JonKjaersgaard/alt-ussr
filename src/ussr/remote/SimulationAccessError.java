package ussr.remote;

/**
 * Used to signal access to a simulation that is not ready
 * @author ups
 */
public class SimulationAccessError extends RuntimeException {
    public SimulationAccessError(String message) {
        super(message);
    }
}
