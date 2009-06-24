/**
 * Unified Simulator for Self-Reconfigurable Robots (USSR)
 * (C) University of Southern Denmark 2008
 * This software is distributed under the BSD open-source license.
 * For licensing see the file LICENCE.txt included in the root of the USSR distribution.
 */
package ussr.samples.atron.ase;

import java.util.ArrayList;

import mc.ModularCommander;

import ussr.description.Robot;
import ussr.description.geometry.VectorDescription;
import ussr.description.setup.ModulePosition;
import ussr.description.setup.WorldDescription;
import ussr.model.Controller;
import ussr.physics.PhysicsFactory;
import ussr.physics.PhysicsParameters;
import ussr.physics.PhysicsSimulation;
import ussr.physics.PhysicsParameters.Material;
import ussr.samples.ObstacleGenerator;
import ussr.samples.atron.ATRON;
import ussr.samples.atron.GenericATRONSimulation;
import ussr.samples.atron.network.ATRONReflectionEventController;
import ussr.util.supervision.CMTracker;
import ussr.util.supervision.CommunicationLoadMonitor;
import ussr.util.supervision.RadioConnection;
import ussr.util.supervision.WifiCMBroadcaster;




public class Walker2Simulation extends GenericASESimulation {
	
    
	public static void main( String[] args ) {
		initASE();
		new Walker2Simulation().main();
    }
	protected ArrayList<ModulePosition> buildWalker3(String id) {
		float Yoffset = 0.25f;
    	ArrayList<ModulePosition> mPos = new ArrayList<ModulePosition>();
    	mPos.add(new ModulePosition("1", ";portRC=9900;portEvent=9901", new VectorDescription(0*ATRON.UNIT,0*ATRON.UNIT-Yoffset,0*ATRON.UNIT), ATRON.ROTATION_EW)); //spline
    	
    	mPos.add(new ModulePosition("2", ";portRC=9902;portEvent=9903", new VectorDescription(1*ATRON.UNIT,0*ATRON.UNIT-Yoffset,-1*ATRON.UNIT), ATRON.ROTATION_NS)); //fa1
    	mPos.add(new ModulePosition("3", ";portRC=9904;portEvent=9905", new VectorDescription(1*ATRON.UNIT,0*ATRON.UNIT-Yoffset,1*ATRON.UNIT), ATRON.ROTATION_SN)); //fa2

    	mPos.add(new ModulePosition("4", ";portRC=9906;portEvent=9907", new VectorDescription(1*ATRON.UNIT,-1*ATRON.UNIT-Yoffset,-2*ATRON.UNIT), ATRON.ROTATION_DU)); //fl1
    	mPos.add(new ModulePosition("5", ";portRC=9908;portEvent=9909", new VectorDescription(1*ATRON.UNIT,-1*ATRON.UNIT-Yoffset,2*ATRON.UNIT), ATRON.ROTATION_DU)); //fl2

    	mPos.add(new ModulePosition("6", ";portRC=9910;portEvent=9911", new VectorDescription(-1*ATRON.UNIT,0*ATRON.UNIT-Yoffset,-1*ATRON.UNIT), ATRON.ROTATION_NS)); //ba1
    	mPos.add(new ModulePosition("7", ";portRC=9912;portEvent=9913", new VectorDescription(-1*ATRON.UNIT,0*ATRON.UNIT-Yoffset,1*ATRON.UNIT), ATRON.ROTATION_SN)); //ba2

    	mPos.add(new ModulePosition("8", ";portRC=9914;portEvent=9915", new VectorDescription(-1*ATRON.UNIT,-1*ATRON.UNIT-Yoffset,-2*ATRON.UNIT), ATRON.ROTATION_DU)); //bl1
    	mPos.add(new ModulePosition("9", ";portRC=9916;portEvent=9917", new VectorDescription(-1*ATRON.UNIT,-1*ATRON.UNIT-Yoffset,2*ATRON.UNIT), ATRON.ROTATION_DU)); //bl2
        return mPos;
	}
	
	protected ArrayList<ModulePosition> buildRobot() {
		return buildWalker3("");
	}
}
