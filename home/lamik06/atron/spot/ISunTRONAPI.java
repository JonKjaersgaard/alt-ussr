package atron.spot;

import atron.futures.ATRONFutures;
import atron.futures.ATRONFuturesConnectors;
import ussr.samples.atron.IATRONAPI;



/**
 * Temporary interface wrt. Sun SPOT extensions to the IATRONController interface
 * @author lamik06@student.sdu.dk
 *
 */
public interface ISunTRONAPI extends IATRONAPI{
	public byte sendRadioMessage(byte[] message, int destination);
	public void sleep(long delay);
	public void activate();
	
	public void addActiveFuturesTable(String tmpKey,ATRONFutures f);
	public void removeActiveFuturesTable(String tmpKey);
	public boolean activeFutures();
	public void waitForAllActiveFutures();
	
	public ATRONFuturesConnectors extendConnector(int connectNo);
	public void retractConnector(int connectNo);
}
