package atron.spot;

import atron.futures.ATRONFutures;
import atron.futures.ATRONFuturesConnectors;
import ussr.samples.atron.ATRONController;

public abstract class SunTronController extends ATRONController implements ISunTRONAPI {
	private static final byte LOCALMESSAGE = 0;
	private static final byte ROUTINGMESSAGE = 1;
	private static final byte REMOTEACTION = 2;
	private static final byte CONNECTOR = 3;
	
	

	/*
	 * IR - filter
	 * @see ussr.samples.atron.ATRONController#handleMessage(byte[], int, int)
	 */
	public void handleMessage(byte[] message, int messageSize, int channel) {
		switch (message[0]) {
		case LOCALMESSAGE:
			handleLocalMessage(message, messageSize, channel);			
			break;
		case ROUTINGMESSAGE:
			handleRoutingMessage(message, messageSize, channel);
			break;
		case REMOTEACTION:
			handleRemoteAction(message, messageSize, channel);			
			break;
		
		case CONNECTOR:
			handleConnector(message, messageSize, channel);
			break;
			
		default:
			System.out.println("Error: handleMessage() -> message type not supported ");
			break;
		}
	}



	private void handleConnector(byte[] message2, int messageSize, int channel) {
		// TODO Auto-generated method stub
		
	}



	private void handleRemoteAction(byte[] message2, int messageSize,
			int channel) {
		// TODO Auto-generated method stub
		
	}



	private void handleLocalMessage(byte[] message2, int messageSize,
			int channel) {
		// TODO Auto-generated method stub
		
	}



	private void handleRoutingMessage(byte[] message2, int messageSize,
			int channel) {
		// TODO Auto-generated method stub
		
	}
	public ATRONFutures retractConnector(byte b) {
		return null;
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean activeFutures() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public void addActiveFuturesTable(String tmpKey, ATRONFutures f) {
		// TODO Auto-generated method stub
		
	}

	public ATRONFuturesConnectors extendConnector(int connectNo) {
		// TODO Auto-generated method stub
		ATRONFuturesConnectors f = new ATRONFuturesConnectors(connectNo,this);
		return f;
	}
	@Override
	public void removeActiveFuturesTable(String tmpKey) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void retractConnector(int connectNo) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public byte sendRadioMessage(byte[] message, int destination) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void sleep(long delay) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void waitForAllActiveFutures() {
		// TODO Auto-generated method stub
		
	}
}
