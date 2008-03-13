/**
 * Unified Simulator for Self-Reconfigurable Robots (USSR)
 * (C) University of Southern Denmark 2008
 * This software is distributed under the BSD open-source license.
 * For licensing see the file LICENCE.txt included in the root of the USSR distribution.
 */
package ussr.physics;

import ussr.model.Connector;

/**
 * For connectors that support it, this interface allows details of the connector behavior
 * to be specified independently of the controller, such as whether to automatically connect
 * when another connector is within connection proximity. 
 * 
 * @author ups
 */
public interface ConnectorBehaviorHandler {
    /**
     * Specify the behavior of a connector in terms of whether it should immediately 
     * connector to some other connector that has become visible in its proximity
     * @param target The connector for which the behavior is specified
     * @param proximate The connector that has become visible within the proximity of the connector
     * @return true if the target connector should connect to the proximate connector, false otherwise
     */
    public boolean connectToProximateConnector(Connector target, Connector proximate);
}
