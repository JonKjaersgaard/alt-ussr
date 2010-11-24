package robustReversible;

public interface CommunicationManager {

    /**
     * Factory for creating CommunicationManager objects, accessed through Factory singleton class
     */
    interface CommunicationManagerFactory {
        CommunicationManager create();
    }
    
    /**
     * Singleton for accessing communication manager factory
     * To override default factory: CommunicationManager.Factory.set(...) 
     */
    static class Factory {
        private static CommunicationManagerFactory factory;
        public synchronized static CommunicationManagerFactory get() {
            if(factory==null) factory = new CommunicationManagerFactory() {
                @Override public CommunicationManager create() {
                    return new DistributedStateManager();
                }
            };
            return factory;
        }
        public synchronized static void set(CommunicationManagerFactory f) {
            factory = f;
        }
    }

    /**
     * Callback: set the API that is used to interact with the hardware
     * @param atronStateMachineAPI
     */
    void setCommunicationProvider(CommunicationProvider provider);
    
    /**
     * Callback: receive a message over the network
     * @param message
     */
    void receive(byte[] message);

    /**
     * Callback: optionally perform an operation at regular intervals
     */
    void senderAct();

    /**
     * Limit pending states to only propagate with main state 
     */
    void setLimitPendingOneWay(boolean b);

    /**
     * Return global state that has arrived for this module, 255 if no state is newly arrived
     */
    int getMyNewState();

    /**
     * Get the current global state, independently of module ID
     */
    int getGlobalState();

    /**
     * Add p to the global set of pending states 
     */
    void addPendingState(int p);

    /**
     * Remove p from the global set of pending states
     */
    void removePendingState(int p);

    /**
     * Test if there are any modules present in the global set of pending states
     */
    boolean pendingStatesPresent();

    /**
     * Increment the global state to 'state' to be performed by module with ID 'module'
     */
    void sendState(int state, int module);

    /**
     * Initialize the communication manager to ID 'myID' and with first module ID firstModuleID
     * @param myID
     * @param firstModuleID
     */
    void init(int myID, int firstModuleID);

    /**
     * Reset the sequence, starting again from the beginning
     */
    void reset_sequence();

    /**
     * Perform simulated reset of module state
     */
    void reset_state();

}
