package ussr.samples.atron.framework.comm;

import java.util.BitSet;
import ussr.samples.atron.framework.ATRONFramework;

/**
 * Manager for low-level communication issues: basic addressing and basic formatting of packets
 * into:
 *  - RPC (address plus arguments)
 *  
 * @author ups
 */
public class CommunicationManager implements MessageListener.Raw {

    /**
     * First byte of any packet
     */
    private static byte HEADER = 87;
    /**
     * Second byte indicating that it is an RPC-style packet
     */
    private static byte RPC_MODE = 88;
    /**
     * The listener that receives packets filtered by address
     */
    private MessageListener.AddressBased listener;
    /**
     * The underlying framework that provides communication capabilities
     */
    private ATRONFramework framework;
    /**
     * Bitset holding the addresses that this module should respond to
     */
    private BitSet activeMulticastAddresses = new BitSet();
    
    /**
     * Create a CommunicationManager providing addressed packets to rpcSystem using controller for communication 
     * @param rpcSystem: the rpcSystem that receives the packets
     * @param controller: the controller used for communication
     */
    public CommunicationManager(MessageListener.AddressBased _rpcSystem, ATRONFramework _controller) {
        this.listener = _rpcSystem;
        this.framework = _controller;
        _controller.addMessageListener(this);
    }

    /**
     * Send multicast packet to the corresponding address
     * @param address: address to send packets to
     * @param arguments: arguments to include with the packet
     */
    public void sendMultiCast(int code, int[] intArguments) {
        byte[] packet = encode(code,intArguments);
        for(byte c=0; c<8; c++)
            framework.sendMessage(packet, (byte)packet.length, c);
    }

    /**
     * Helper method for encoding a packet
     */
    private byte[] encode(int code, int[] arguments) {
        byte[] result = new byte[4+arguments.length];
        int index = 0;
        result[index++] = HEADER;
        result[index++] = RPC_MODE;
        result[index++] = (byte)code;
        result[index++] = (byte)arguments.length;
        for(int i=0; i<arguments.length; i++)
            result[index++] = (byte)arguments[i];
        return result;
    }

    /**
     * Indicate that packets with the corresponding address should be received and delegated to corresponding layers
     * @param address the address to accept packets on
     */
    public void registerMultiCastAddress(int address) {
        activeMulticastAddresses.set(address);
    }

    /**
     * Helper method: convert byte as unsigned to integer
     */
    public static int b2i(byte b) {
        return (int)b&0xff;
    }

    /**
     * Handler method for raw packets arriving on the connector: inspect and pass to listener if address matches
     */
    @Override
    public void messageReceived(int connector, int[] packet) {
        if(packet.length<1) return;
        if(packet[0]!=HEADER) return;
        if(packet.length<4 || packet[1]!=RPC_MODE) throw new Error("Malformed packet");
        int code = packet[2];
        if(!activeMulticastAddresses.get(code)) return;
        int nargs = packet[3];
        int[] arguments = new int[nargs];
        for(int i=0; i<nargs; i++)
            arguments[i] = packet[i+4];
        listener.messageReceived(connector, code, arguments);
    }

}
