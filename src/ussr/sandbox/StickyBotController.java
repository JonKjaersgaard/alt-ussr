/**
 * 
 */
package ussr.sandbox;

import java.util.List;

import ussr.model.Connector;
import ussr.model.ControllerImpl;

/**
 * @author ups
 *
 * TODO Write a nice and user-friendly comment here
 * 
 */
public class StickyBotController extends ControllerImpl {

    /* (non-Javadoc)
     * @see ussr.model.ControllerImpl#activate()
     */
    @Override
    public void activate() {
        while(true) {
            this.waitForEvent();
            if(!StickyBotSimulation.getConnectorsAreActive()) continue; 
            for(Connector connector: module.getConnectors()) {
                List<Connector> proximates = connector.getAvailableConnectors();
                for(Connector proximate: proximates) {
                    if(proximate==null||connector.isConnected()) continue;
                    connector.connectTo(proximate);
                }
            }
        }
    }

}
