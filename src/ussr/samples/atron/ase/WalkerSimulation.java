/**
 * Unified Simulator for Self-Reconfigurable Robots (USSR)
 * (C) University of Southern Denmark 2008
 * This software is distributed under the BSD open-source license.
 * For licensing see the file LICENCE.txt included in the root of the USSR distribution.
 */
package ussr.samples.atron.ase;

import java.util.ArrayList;

import ussr.description.geometry.VectorDescription;
import ussr.description.setup.ModulePosition;
import ussr.samples.atron.ATRON;


 

public class WalkerSimulation extends GenericASESimulation {
	
    
	public static void main( String[] args ) {
		initASE();
		new WalkerSimulation().main();
    }
	protected ArrayList<ModulePosition> buildWalker(String id) {
    	float Yoffset = 0.25f;
    	ArrayList<ModulePosition> mPos = new ArrayList<ModulePosition>(); 
    	mPos.add(new ModulePosition("1", ";portRC=9900;portEvent=9901", new VectorDescription(0*ATRON.UNIT,0*ATRON.UNIT-Yoffset,0*ATRON.UNIT), ATRON.ROTATION_EW));
    	mPos.add(new ModulePosition("2", ";portRC=9902;portEvent=9903", new VectorDescription(1*ATRON.UNIT,0*ATRON.UNIT-Yoffset,-1*ATRON.UNIT), ATRON.ROTATION_SN));
    	mPos.add(new ModulePosition("3", ";portRC=9904;portEvent=9905", new VectorDescription(1*ATRON.UNIT,0*ATRON.UNIT-Yoffset,1*ATRON.UNIT), ATRON.ROTATION_NS));
    	mPos.add(new ModulePosition("4", ";portRC=9906;portEvent=9907", new VectorDescription(2*ATRON.UNIT,0*ATRON.UNIT-Yoffset,0*ATRON.UNIT), ATRON.ROTATION_WE));
    	mPos.add(new ModulePosition("5", ";portRC=9908;portEvent=9909", new VectorDescription(-1*ATRON.UNIT,-1*ATRON.UNIT-Yoffset,0*ATRON.UNIT), ATRON.ROTATION_DU));
    	mPos.add(new ModulePosition("6", ";portRC=9910;portEvent=9911", new VectorDescription(1*ATRON.UNIT,-1*ATRON.UNIT-Yoffset,-2*ATRON.UNIT), ATRON.ROTATION_UD));
    	mPos.add(new ModulePosition("7", ";portRC=9912;portEvent=9913", new VectorDescription(1*ATRON.UNIT,-1*ATRON.UNIT-Yoffset,2*ATRON.UNIT), ATRON.ROTATION_UD));
    	mPos.add(new ModulePosition("8", ";portRC=9914;portEvent=9915", new VectorDescription(3*ATRON.UNIT,-1*ATRON.UNIT-Yoffset,0*ATRON.UNIT), ATRON.ROTATION_DU));
        return mPos;
	}
	
	protected ArrayList<ModulePosition> buildRobot() {
		return buildWalker("");
	}
}
