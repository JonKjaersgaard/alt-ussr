package ussr.samples.atron.framework.comm;

public interface MessageListener {

    public static interface Raw {
        public void messageReceived(int connector, int[] packet);
    }
    
    public static interface AddressBased {
        public void messageReceived(int connector, int address, int[] packet);
    }
    
}
