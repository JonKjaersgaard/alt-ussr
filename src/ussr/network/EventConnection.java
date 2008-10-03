package ussr.network;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class EventConnection extends AbstractNetworkConnection {
	BufferedReader reader;
	BufferedWriter writer;
	int id = 1;
	public EventConnection(int port) {
		super(port);
	}

	public boolean activationHook(InputStream input, OutputStream output) {
        reader = new BufferedReader(new InputStreamReader(input));
        writer = new BufferedWriter(new OutputStreamWriter(output));
        return false;
	}
	
	public String sendEvent(String eventType, Object[] eventParams) {
		String packet = packEvent(eventType, eventParams);
		sendEvent(packet);
		String reply = getReply();
		String[] parts = reply.split(" ");
		int replyid = Integer.parseInt(parts[0]);
		if(replyid==id||parts.length>2) {
			if(parts[2]=="OK") {
				id++;
				return parts[3];
			}
			else {
				System.err.println("Warning: event error");
			}
		}
		else {
			System.err.println("Warning: illegal packet received");
		}
		return null;
	}
	
	private void sendEvent(String packet) {
		try {
            writer.write(packet.toString());
            writer.flush();
        } catch(IOException exn) {
            throw new Error("Error writing to socket");
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
		if(param==Integer.TYPE) return param.toString();
		else if(param==Float.TYPE) return param.toString();
		else if(param==Byte.TYPE) return param.toString();
		else if(param==Character.TYPE) return param.toString();
		else if(param instanceof byte[]) {
			StringBuffer buffer = new StringBuffer();
			for(int i=0;i<((byte[])param).length;i++) {
				buffer.append(((byte[])param)[i]);
			}
			return buffer.toString();
		}
		else {
			throw new Error("Unsupported parameter of class "+param.getClass()+": "+param);
	    }
	}
	private String getReply() {
	    try {
	    	return reader.readLine(); 
        } catch (IOException e) {
            throw new Error("Error reading from socket");
        }
	}
}
