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
            } catch(Throwable t) {
                System.err.println("Warning: exception while processing request");
                t.printStackTrace(System.err);
                response = id+" ERROR "+t;
            }
            try {
                writer.write(response.toString());
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
                arguments = new Object[parameters.length];
                for(int i=0; i<parameters.length; i++) {
                    if(parameters[i]==Integer.TYPE)
                        arguments[i] = Integer.parseInt(parts[2+i]);
                    else if(parameters[i]==Float.TYPE)
                        arguments[i] = Float.parseFloat(parts[2+i]);
                    else if(parameters[i]==Byte.TYPE) 
                    	arguments[i] = Byte.parseByte(parts[2+i]);
                    else if(parameters[i]==Character.TYPE)
                    	arguments[i] = parts[2+i].toCharArray()[0];
                    else if(parameters[i].getCanonicalName().equals("byte[]"))
                    	arguments[i] = ((String)parts[2+i]).getBytes();
                    else {
                    	throw new Error("Illegal arguments type @"+i+": "+parameters[i]);
                    }
                }
                Object result = method.invoke(target, arguments);
                return result;
            }
        }
        throw new Error("Method not found: "+command);
    }
}
