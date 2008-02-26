package ussr.samples.atron;

import java.util.ArrayList;

import ussr.model.Controller;
import ussr.robotbuildingblocks.ModulePosition;
import ussr.robotbuildingblocks.Robot;
import ussr.robotbuildingblocks.RotationDescription;
import ussr.robotbuildingblocks.VectorDescription;

/**
 * Simulation that allows an ATRON lattice of any size to be constructed 
 * 
 * @author Modular Robots @ MMMI
 */
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
	public interface Namer {
	    public String name(int number, VectorDescription pos, RotationDescription rot);
    }
    public interface ModuleSelector {
        String select(String name, int index, VectorDescription pos, RotationDescription rot);
    }
    protected ArrayList<ModulePosition> buildAsLattice(int nModules, int xMax, int yMax, int zMax) {
        return this.buildAsNamedLattice(nModules, xMax, yMax, zMax, new Namer() {
            public String name(int number, VectorDescription pos, RotationDescription rot) {
                return "module"+Integer.toString(number);
            }
                
        }, new ModuleSelector() {
            public String select(String name, int index, VectorDescription pos, RotationDescription rot) {
                return null;
            }
        }, ATRON.UNIT);
    }
    
    protected ArrayList<ModulePosition> buildAsNamedLattice(int nModules, int xMax, int yMax, int zMax, Namer namer, ModuleSelector selector, float placement_unit) {
    	ArrayList<ModulePosition> mPos = new ArrayList<ModulePosition>();
        int index=0;
        for(int x=0;x<xMax;x++) {
        	for(int y=0;y<yMax;y++) {
        		for(int z=0;z<zMax;z++) {
        			VectorDescription pos = null;
        			RotationDescription rot = ATRON.ROTATION_NS;
        			if(y%2==0&&z%2==0) {
        				pos = new VectorDescription(2*x*placement_unit,y*placement_unit,z*placement_unit);
        				rot = ATRON.ROTATION_EW;
        			}
	        		else if(y%2==0&&z%2==1)  {
	        			pos = new VectorDescription(2*x*placement_unit+placement_unit,y*placement_unit,z*placement_unit);
	        			rot = ATRON.ROTATION_NS;
	        		}
	        		else if(y%2==1&&z%2==0) {
	        			pos = new VectorDescription(2*x*placement_unit+placement_unit,y*placement_unit,z*placement_unit);
	        			rot = ATRON.ROTATION_UD;
	        		}
	        		else if(y%2==1&&z%2==1) {
	        			pos = new VectorDescription(2*x*placement_unit,y*placement_unit,z*placement_unit);
	        			rot = ATRON.ROTATION_NS;
	        		}
	        		if(index<nModules) {
                        String name = namer.name(index,pos,rot);
                        String robotNameMaybe = selector.select(name,index,pos,rot);
                        ModulePosition mpos;
                        if(robotNameMaybe==null)
                            mpos = new ModulePosition(name, pos, rot);
                        else
                            mpos = new ModulePosition(name, robotNameMaybe, pos, rot);
                        mPos.add(mpos);
                    }
	        		index++;
        		}
        	}
        }
        return mPos;
	}
	protected ArrayList<ModulePosition> buildRobot() {
		return  buildAsLattice(20,20,40,10);
	}
}
