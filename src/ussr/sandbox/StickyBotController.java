/**
 * 
 */
package ussr.sandbox;

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
                Connector proximate = connector.getProximateConnector();
                if(proximate==null||connector.isConnected()) continue;
                connector.connectTo(proximate);
            }
        }
    }

}
