/**
 * Unified Simulator for Self-Reconfigurable Robots (USSR)
 * (C) University of Southern Denmark 2008
 * This software is distributed under the BSD open-source license.
 * For licensing see the file LICENCE.txt included in the root of the USSR distribution.
 */
package sunTron.samples.connect;

import java.util.ArrayList;
/* Essentially a stripped down car */

import ussr.description.Robot;
import ussr.description.geometry.VectorDescription;
import ussr.description.setup.ModulePosition;
import ussr.description.setup.WorldDescription;
import ussr.model.Controller;
import ussr.samples.ObstacleGenerator;
import ussr.samples.atron.ATRON;
import ussr.samples.atron.ATRONBuilder;
import ussr.samples.atron.GenericATRONSimulation;

/**
 * A simulation for a two-wheeler ATRON robot
 * @author Modular Robots @ MMMI
 *
 */
public class SunTronConnectSimulation1 extends GenericATRONSimulation {
	private ArrayList<ModulePosition> mPos = new ArrayList<ModulePosition>();
    private float connection_acceptance_range = 0.001f;
	public static void main( String[] args ) {
        new SunTronConnectSimulation1().main();
    }
	
	protected Robot getRobot() {
        return new ATRON() {
            public Controller createController() {
                return new SunTronConnectController1();
            }
        };
    }

	protected ArrayList<ModulePosition> buildRobot() {
        float Yoffset = -0.25f;
        mPos.add(new ModulePosition("driver0", new VectorDescription(-2*ATRON.UNIT,-2*ATRON.UNIT+Yoffset,0*ATRON.UNIT), ATRON.ROTATION_EW));
        mPos.add(new ModulePosition("RearRightWheel", new VectorDescription(-1*ATRON.UNIT,-2*ATRON.UNIT+Yoffset,1*ATRON.UNIT), ATRON.ROTATION_SN));
        mPos.add(new ModulePosition("RearLeftWheel", new VectorDescription(-1*ATRON.UNIT,-2*ATRON.UNIT+Yoffset,-1*ATRON.UNIT), ATRON.ROTATION_NS));
        mPos.add(new ModulePosition("driver1", new VectorDescription(-0*ATRON.UNIT,-2*ATRON.UNIT+Yoffset,0*ATRON.UNIT), ATRON.ROTATION_WE));

        return mPos;
		
	}
    
    protected void changeWorldHook(WorldDescription world) {
        ObstacleGenerator generator = new ObstacleGenerator();
        generator.obstacalize(ObstacleGenerator.ObstacleType.LINE, world);
    }
}
