package ussr.samples.atron.framework.comm;

/**
 * Collection of interfaces for variations of message listener interfaces 
 * (corresponding to protocol stacking levels)
 * @author ups
 */
public interface MessageListener {

    /**
     * A listener for raw packets that arrive on the connectors of the module.
     * Used for all packets that arrive.
     * @author ups
     */
    public static interface Raw {
        public void messageReceived(int connector, int[] packet);
    }
    
    /**
     * A listener for addressed packets that arrive on the connectors of the module.
     * Only used for packets that match any address filtering.
     * @author ups
     */
    public static interface AddressBased {
        public void messageReceived(int connector, int address, int[] packet);
    }
    
}
