/**
 * Unified Simulator for Self-Reconfigurable Robots (USSR)
 * (C) University of Southern Denmark 2008
 * This software is distributed under the BSD open-source license.
 * For licensing see the file LICENCE.txt included in the root of the USSR distribution.
 */
package ussr.samples.stickybot;

import java.awt.Color;

import ussr.comm.TransmissionType;
import ussr.description.Robot;
import ussr.description.geometry.GeometryDescription;
import ussr.description.geometry.SphereShape;
import ussr.description.geometry.VectorDescription;
import ussr.description.robot.ConnectorDescription;
import ussr.description.robot.ModuleComponentDescription;
import ussr.description.robot.ReceivingDevice;
import ussr.description.robot.RobotDescription;
import ussr.description.robot.TransmissionDevice;
import ussr.model.Controller;

/**
 * A small round robot with connectors at N/S/E/W/U/D; connectors can stick to each
 * other when close, in this case stickiness is globally controlled by the user.  
 * 
 * @author ups
 */
public class StickyBot implements Robot {
    
    public static final float SCALE = 0.1f; 
    
    /**
     * @see ussr.description.Robot#getDescription()
     */
    public RobotDescription getDescription() {
        RobotDescription description = new RobotDescription("Sticky");
        GeometryDescription[] connectorGeometry = new GeometryDescription[] { new SphereShape(SCALE*0.8f) };
        connectorGeometry[0].setColor(Color.BLUE);
        ConnectorDescription.Common common = new ConnectorDescription.Common();
        common.setGeometry(connectorGeometry);
        common.setType( ConnectorDescription.Type.MAGNETIC_CONNECTOR);
        common.setMaxConnectionDistance(SCALE*0.8f);
        ConnectorDescription[] connectors = new ConnectorDescription[] {
                new ConnectorDescription(common, new VectorDescription(-2.0f*SCALE, 0.0f*SCALE, 0*SCALE)), 
                new ConnectorDescription(common, new VectorDescription(2.0f*SCALE, 0.0f*SCALE, 0*SCALE)),
                new ConnectorDescription(common, new VectorDescription(0.0f*SCALE, 2.0f*SCALE, 0*SCALE)),
                new ConnectorDescription(common, new VectorDescription(0.0f*SCALE, -2.0f*SCALE, 0*SCALE)),
                new ConnectorDescription(common, new VectorDescription(0.0f*SCALE, 0.0f*SCALE, -2.0f*SCALE)),
                new ConnectorDescription(common, new VectorDescription(0.0f*SCALE, 0f*SCALE, 2.0f*SCALE)) };
        description.setModuleComponents(new ModuleComponentDescription[] { new ModuleComponentDescription(new SphereShape(SCALE*2), connectors )}); 
        description.setTransmitters(new TransmissionDevice[] { new TransmissionDevice(TransmissionType.RADIO,20*SCALE) });
        description.setReceivers(new ReceivingDevice[] { new ReceivingDevice(TransmissionType.RADIO,10) });
        return description;
    }

    /**
     * @see ussr.description.Robot#createController()
     */
    public Controller createController() {
        return new StickyBotController1();
    }
    
}
