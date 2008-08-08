package ussr.samples.atron.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ussr.model.Controller;
import ussr.samples.AbstractNetworkConnection;

public class ModularCommanderController extends AbstractNetworkConnection {

    private Mapper controller;
    
    public static interface Mapper {
        public void mapPacketToAPI(byte[] packet);
    }

    public ModularCommanderController(String name, int port, Mapper mapper) {
        super(name,port);
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
        XMLEater xe = new XMLEater(input);
        xe.eat("<modular_commander>");
        while(xe.hasTag("b")) storage.add(xe.eatByte());
        xe.eat("</modular_commander>");
        byte[] result = new byte[storage.size()];
        for(int i=0; i<result.length; i++) result[i] = storage.get(i);
        return result;
    }
    
    private static class XMLEater {
        private String string;
        private int index;
        public XMLEater(String string) { this.string = string; }
        public void eat(String sequence) {
            if(!string.startsWith(sequence, index)) throw new XMLEaterMismatch("Expected "+sequence+" got "+string.substring(index));
            index+=sequence.length();
        }
        public boolean hasTag(String tag) {
            return string.startsWith("<"+tag+">", index);
        }
        public byte eatByte() {
            this.eat("<b>");
            int end = string.indexOf('<', index);
            byte result = Byte.parseByte(string.substring(index, end));
            index = end;
            this.eat("</b>");
            return result;
        }
    }

    private static class XMLEaterMismatch extends RuntimeException {
        public XMLEaterMismatch(String string) { super(string); }
    }

}
