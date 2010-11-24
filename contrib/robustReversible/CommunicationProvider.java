package robustReversible;

public interface CommunicationProvider {
    /**
     * Broadcast message to all modules
     */
    public void broadcastMessage(byte[] message);
    
    /**
     * Pause for designated simulated time 
     */
    public void delay(int time);

    /** 
     * Get the current simulated time
     */
    public float getTime();
    
    /**
     * Check if the module myID is responsible for executing the pending state p 
     */
    public boolean isResponsible(int myID, int p);
}
