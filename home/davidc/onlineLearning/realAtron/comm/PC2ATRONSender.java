package onlineLearning.realAtron.comm;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.StringTokenizer;


public class PC2ATRONSender {
   public ServerSocket hostServer = null;
   public Socket socket = null;
   public BufferedReader in = null;
   public PrintWriter out = null;
   public int port = 1234;
	
   public PC2ATRONSender(int port) {
	   this.port=port;
   }
   public boolean connect() {
	   System.out.print("Connecting...");
	   try {
		   hostServer = new ServerSocket(port);
	       socket = hostServer.accept();
	       in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	       out = new PrintWriter(socket.getOutputStream(), true);
	   }
	   catch(Exception e) {System.out.println("failure");return false;}
	   System.out.println("done");
	   return true;
   }
   public void disconnect() {
	   try {
	         if (hostServer != null) {
	            hostServer.close();
	            hostServer = null;
	         }
	      }
	      catch (IOException e) { hostServer = null; }

	      try {
	         if (socket != null) {
	            socket.close();
	            socket = null;
	         }
	      }
	      catch (IOException e) { socket = null; }

	      try {
	         if (in != null) {
	            in.close();
	            in = null;
	         }
	      }
	      catch (IOException e) { in = null; }

	      if (out != null) {
	         out.close();
	         out = null;
	      }
   }
   public String receive() {
	   String s = null;
       try {
		if (in.ready()) {
		      s = in.readLine(); // Receive data
		   }
		} catch (IOException e) {
			e.printStackTrace();
		}
       return s;
       
   }
   public void send(String toSend) {
	   out.print(toSend); out.flush();
   }
   
   
   
   
   public static void main(String[] args) {
	   String fitness = "65:66\n";
	   int count=0;
//	   PC2ATRONSender pc2atron = new PC2ATRONSender(6201);
	   PC2ATRONSender pc2atron = new PC2ATRONSender(5322);
	   pc2atron.connect();
	   long lastSendTime = System.currentTimeMillis();
	   while(true) {
		   try { // Poll every ~1000 ms
			   Thread.sleep(10);
		   }
		   catch (InterruptedException e) {}
		   String s = pc2atron.receive();
		   if(s!=null&&s.length()!=0) {
			   String str = parseString(s);
			   System.out.println("Modtaget: "+str);
		   }
		   if((System.currentTimeMillis()-lastSendTime)>1000) {
			   lastSendTime = System.currentTimeMillis();
			   System.out.println("Sending "+fitness);
			   pc2atron.send(fitness);
			   count++;
		   }
	   }
	}
	private static String parseString(String s) {
		StringTokenizer tokens = new StringTokenizer(s,":");
		StringBuffer str = new StringBuffer();
		while(tokens.hasMoreTokens()) {
			str.append((char)Integer.decode(tokens.nextToken()).intValue());
		}
		return str.toString();
	}
}
