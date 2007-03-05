package ussr.samples.atron;

import java.util.ArrayList;

import ussr.model.Controller;
import ussr.robotbuildingblocks.Robot;
import ussr.robotbuildingblocks.RotationDescription;
import ussr.robotbuildingblocks.VectorDescription;
import ussr.robotbuildingblocks.WorldDescription;
import ussr.robotbuildingblocks.WorldDescription.ModulePosition;

public class ATRONLatticeSimulation extends GenericATRONSimulation {
	
	
	public static void main( String[] args ) {
        new ATRONLatticeSimulation().main();
    }
	
	protected Robot getRobot() {
        return new ATRON() {
            public Controller createController() {
                return new ATRONSampleController1();
            }
        };
    }
	private static ArrayList<ModulePosition> buildAsLattice(int nModules, int xMax, int yMax, int zMax) {
    	ArrayList<ModulePosition> mPos = new ArrayList<ModulePosition>();
        int index=0;
        for(int x=0;x<xMax;x++) {
        	for(int y=0;y<yMax;y++) {
        		for(int z=0;z<zMax;z++) {
        			VectorDescription pos = null;
        			RotationDescription rot = rotation_NS;
        			if(y%2==0&&z%2==0) {
        				pos = new VectorDescription(2*x*unit,y*unit,z*unit);
        				rot = rotation_EW;
        			}
	        		else if(y%2==0&&z%2==1)  {
	        			pos = new VectorDescription(2*x*unit+unit,y*unit,z*unit);
	        			rot = rotation_NS;
	        		}
	        		else if(y%2==1&&z%2==0) {
	        			pos = new VectorDescription(2*x*unit+unit,y*unit,z*unit);
	        			rot = rotation_UD;
	        		}
	        		else if(y%2==1&&z%2==1) {
	        			pos = new VectorDescription(2*x*unit,y*unit,z*unit);
	        			rot = rotation_NS;
	        		}
	        		if(index<nModules) mPos.add(new WorldDescription.ModulePosition(Integer.toString(index), pos, rot));
	        		index++;
        		}
        	}
        }
        return mPos;
	}
	protected ArrayList<ModulePosition> buildRobot() {
		return  buildAsLattice(5,2,4,1);
	}
}
