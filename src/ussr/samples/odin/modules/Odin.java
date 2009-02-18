/**
 * Unified Simulator for Self-Reconfigurable Robots (USSR)
 * (C) University of Southern Denmark 2008
 * This software is distributed under the BSD open-source license.
 * For licensing see the file LICENCE.txt included in the root of the USSR distribution.
 */
package ussr.samples.odin.modules;

import java.awt.Color;

import ussr.description.Robot;
import ussr.description.geometry.GeometryDescription;
import ussr.description.geometry.SphereShape;
import ussr.description.robot.RobotDescription;
import ussr.model.Controller;

/**
 * Base abstract class for the heterogeneous reconfigurable modular robot Odin   
 * 
 * @author david
 */
public abstract class Odin implements Robot {
    
    private static float defaultConnectorSize = 0.001f; 
    
    public static void setDefaultConnectorSize(float size) {
        defaultConnectorSize = size;
    }
    
    protected GeometryDescription makeConnectorShape() { 
        SphereShape connector = new SphereShape(defaultConnectorSize);
        connector.setColor(Color.WHITE);
        return connector;
    }
    
}
