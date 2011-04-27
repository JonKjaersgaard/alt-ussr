package ussr.samples.atron.framework.comm;

import java.util.BitSet;
import java.util.Map;

import ussr.samples.atron.ATRONController;
import ussr.samples.atron.framework.ATRONFramework;

public class CommunicationManager implements MessageListener.Raw {

    private static byte HEADER = 87;
    private static byte RPC_MODE = 88;
    private MessageListener.AddressBased listener;
    private ATRONFramework framework;
    private BitSet activeMulticastAddresses = new BitSet();
    
    public CommunicationManager(MessageListener.AddressBased _rpcSystem, ATRONFramework _controller) {
        this.listener = _rpcSystem;
        this.framework = _controller;
        _controller.addMessageListener(this);
    }

    public void sendMultiCast(int code, int[] intArguments) {
        byte[] packet = encode(code,intArguments);
        for(byte c=0; c<8; c++)
            framework.sendMessage(packet, (byte)packet.length, c);
    }

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

    public void registerMultiCastAddress(int code) {
        activeMulticastAddresses.set(code);
    }

    public static int b2i(byte b) {
        return (int)b&0xff;
    }

    @Override
    public void messageReceived(int connector, int[] packet) {
        if(packet.length<1) return;
        if(packet[0]!=HEADER) return;
        if(packet.length<4 || packet[1]!=RPC_MODE) throw new Error("Malformed packet");
        int code = packet[2];
        int nargs = packet[3];
        int[] arguments = new int[nargs];
        for(int i=0; i<nargs; i++)
            arguments[i] = packet[i+4];
        listener.messageReceived(connector, code, arguments);
    }

}
