package ussr.network;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import ussr.model.Controller;

public class ReflectionConnection extends AbstractNetworkConnection {

    private Object target;
    
    public ReflectionConnection(int port, Object target) {
        super(port);
        this.target = target;
    }

    @Override
    public boolean activationHook(InputStream input, OutputStream output) {
        System.out.println("Reflection connection activated for "+target);
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(output));
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
            if(packet.length()==0) { 
                System.err.println("Warning: empty packet received");
                continue;
            }
            String[] parts = packet.split(" ");
            if(parts.length<2) {
                System.err.println("Warning: illegal packet received");
                continue;
            }
            int id = Integer.parseInt(parts[0]);
            Object response;
            try {
                response = id+" OK "+handleMessage(parts);
            } catch(Exception exn) {
                response = id+" ERROR "+exn;
            }
            try {
                writer.write(response.toString());
                writer.newLine();
                writer.flush();
            } catch(IOException exn) {
                throw new Error("Error writing to socket");
            }
        }
        System.out.println("Socket closed, waiting for new connection");
        return true;
    }

    private Object handleMessage(String[] parts) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
        String command = parts[1];
        Object[] arguments;
        for(Method method: target.getClass().getMethods()) {
            if(method.getName().equals(command)) {
                Class<?>[] parameters = method.getParameterTypes();
                if(parameters.length==0) arguments = new Object[0];
                if(parameters[0]==Integer.class)
                    arguments = new Object[] { Integer.parseInt(parts[2]) };
                else if(parameters[0]==Float.class)
                    arguments = new Object[] { Float.parseFloat(parts[2]) };
                else
                    throw new Error("Illegal arguments type: parameters[0]");
                Object result = method.invoke(target, arguments);
                return result;
            }
        }
        throw new Error("Method not found: "+command);
    }

}
