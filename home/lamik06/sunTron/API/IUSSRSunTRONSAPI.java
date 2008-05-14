package sunTron.API;
import java.util.List;
//
import ussr.model.Sensor;
import ussr.samples.atron.*;
/**
 * Temporary interface wrt. Sun SPOT extensions to the IATRONController interface
 * @author lamik06@student.sdu.dk
 *
 */
public interface IUSSRSunTRONSAPI extends ISunTronAPI  {
	public List<Sensor> getSensors();
	
}
