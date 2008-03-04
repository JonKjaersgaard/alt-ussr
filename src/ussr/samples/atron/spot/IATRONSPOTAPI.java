package ussr.samples.atron.spot;
import ussr.samples.atron.*;
/**
 * Temporary interface wrt. Sun SPOT extensions to the IATRONController interface
 * @author lamik06@student.sdu.dk
 *
 */
public interface IATRONSPOTAPI extends IATRONAPI  {
	public byte sendRadioMessage(byte[] message, int destination);
	
}
