package ussr.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class ModularCommanderConnection extends AbstractNetworkConnection {

    private Mapper controller;
    
    public static interface Mapper {
        public void mapPacketToAPI(byte[] packet);
    }

    public ModularCommanderConnection(int port, Mapper mapper) {
        super(port);
        this.controller = mapper;
    }

    @Override
    public boolean activationHook(InputStream input, OutputStream output) {
        System.out.println("Modular commander controller activated for "+controller);
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        String packet;
        while(true) {
            try {
                System.out.println("Waiting to read line...");
                packet = reader.readLine();
                System.out.println("Read line...");
            } catch (IOException e) {
                throw new Error("Error reading from socket");
            }
            System.out.println("Read: "+packet);
            if(packet==null) break;
            byte[] bytes = ussrUnpackXML(packet);
            controller.mapPacketToAPI(bytes);
        }
        System.out.println("Socket closed, waiting for new connection");
        return true;
    }

    private byte[] ussrUnpackXML(String input) {
        List<Byte> storage = new ArrayList<Byte>();
        XMLTokenizer xe = new XMLTokenizer(input);
        xe.eat("<modular_commander>");
        while(xe.hasTag("b")) storage.add(xe.eatByte());
        xe.eat("</modular_commander>");
        byte[] result = new byte[storage.size()];
        for(int i=0; i<result.length; i++) result[i] = storage.get(i);
        return result;
    }

}
