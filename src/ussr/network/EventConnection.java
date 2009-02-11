package ussr.network;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class EventConnection extends AbstractNetworkConnection {
	BufferedReader reader;
	BufferedWriter writer;
	Socket connection;
	int id = 1;
	boolean debug = false; 
	boolean reconnect = false;
	public EventConnection(int port) {
		super(port);
	}

	public boolean activationHook(InputStream input, OutputStream output) {
        reader = new BufferedReader(new InputStreamReader(input));
        writer = new BufferedWriter(new OutputStreamWriter(output));
        this.connection = connection;
        while(!reconnect) {
        	try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
        }
        try {

        	reader.close();
        	writer.close();
			connection.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
        reconnect = false;
        return true;
	}
	public String sendEvent(String eventType, Object[] eventParams) {
		String packet = packEvent(eventType, eventParams);
		if(debug) System.out.println("Packet: "+packet);
		sendEvent(packet);
		String reply = getReply();
		if(reply!=null) {
			if(debug) System.out.println("Reply: "+reply);
			String[] parts = reply.split(" ");
			int replyid = Integer.parseInt(parts[0]);
			if(replyid==id||parts.length>2) {
				if(parts[1].equals("OK")) {
					id++;
					return parts[2];
				}
				else {
					System.err.println("Warning: event error "+parts[1]+"!=OK");
				}
			}
			else {
				System.err.println("Warning: illegal event packet received after sending "+packet);
			}
		}
		else {
			System.err.println("Warning: null event packet received after sending "+packet);
		}
		System.err.println("Event socket closed, reconnecting");
		reconnect = true;
		return null;
	}
	
	private void sendEvent(String packet) {
		if(writer==null) {
			System.err.println("Event Connection not activated (no writer socket connected)");
			return;
		}
		try {
            writer.write(packet);
            writer.newLine();
            writer.flush();
        } catch(IOException exn) {
            throw new Error("Error writing to event socket: "+packet);
        }
		
	}

	private String packEvent(String eventType, Object[] eventParams) {
		StringBuffer eventMsg = new StringBuffer();
		eventMsg.append(id);
		eventMsg.append(" ");
		eventMsg.append(eventType);
		for(int i=0;i<eventParams.length;i++) {
			eventMsg.append(" ");
			eventMsg.append(paramToString(eventParams[i]));
		}
		return eventMsg.toString();
	}

	private String paramToString(Object param) {
		if(param.getClass().equals(Integer.class)) return param.toString();
		else if(param.getClass().equals(Long.class)) return param.toString();
		else if(param==Float.TYPE) return param.toString();
		else if(param==Byte.TYPE) return param.toString();
		else if(param==Character.TYPE) return param.toString();
		else if(param instanceof byte[]) {
			StringBuffer buffer = new StringBuffer();
			for(int i=0;i<((byte[])param).length;i++) {
				buffer.append(((byte[])param)[i]);
				buffer.append(',');
			}
			return buffer.toString();
		}
		else {
			throw new Error("Unsupported parameter of class "+param.getClass()+": "+param);
	    }
	}
	private String getReply() {
		if(reader==null) {
			System.err.println("Event Connection not activated (no reader socket connected)");
			return "0 error";
		}
		try {
	    	return reader.readLine(); 
        } catch (IOException e) {
            throw new Error("Error reading from socket");
        }
	}

	public boolean isReady() {
		return !reconnect&&connection!=null&&!connection.isClosed();
	}
}
