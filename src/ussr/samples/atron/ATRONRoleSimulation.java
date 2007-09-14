package ussr.samples.atron;

import java.util.ArrayList;
import java.util.Random;

import ussr.model.Controller;
import ussr.robotbuildingblocks.Robot;
import ussr.robotbuildingblocks.RotationDescription;
import ussr.robotbuildingblocks.VectorDescription;
import ussr.robotbuildingblocks.WorldDescription;
import ussr.robotbuildingblocks.WorldDescription.ModulePosition;

public class ATRONRoleSimulation extends GenericATRONSimulation {
	
	
	public static void main( String[] args ) {
        new ATRONRoleSimulation().main();
    }
	
	protected Robot getRobot() {
        return new ATRON() {
            public Controller createController() {
                return new ATRONRoleController1();
            }
        };
    }
	private static ArrayList<ModulePosition> buildStructure(int nModules) {
    	ArrayList<ModulePosition> mPos = new ArrayList<ModulePosition>();
        int index=0;
        Random rand = new Random(1234); 
        while(index<nModules) {
        	int x = (index==0)?0:rand.nextInt()%10;
        	int y = (index==0)?0:rand.nextInt()%10;
        	int z = (index==0)?0:rand.nextInt()%10;
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
			if(pos!=null&&(index==0||isConnectable(new ModulePosition("",pos,rot),mPos)&&empty(pos,mPos))) {
	        	mPos.add(new WorldDescription.ModulePosition(Integer.toString(index), pos, rot));
	    		index++;
			}
        }
		return mPos;
	}
	private static boolean empty(VectorDescription pos, ArrayList<ModulePosition> allPos) {
		for(ModulePosition p: allPos) {
			if(pos.distance(p.getPosition())<0.001)
				return false;
		}
		return true;
	}

	private static boolean isConnectable(ModulePosition pos, ArrayList<ModulePosition> allPos) {
		/*for(ModulePosition p: allPos) {
			if(isConnectable(pos, p))
				return true;
		}*/
		return false;
	}

	protected ArrayList<ModulePosition> buildRobot() {
		return buildStructure(4);
	}
}
