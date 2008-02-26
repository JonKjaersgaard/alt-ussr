package ussr.samples.atron;

import java.util.ArrayList;
import java.util.Random;

import ussr.model.Controller;
import ussr.robotbuildingblocks.ModulePosition;
import ussr.robotbuildingblocks.Robot;
import ussr.robotbuildingblocks.RotationDescription;
import ussr.robotbuildingblocks.VectorDescription;

/**
 * A sample simulation based on the ATRON robot
 * 
 * @author Modular Robots @ MMMI
 */
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
			RotationDescription rot = ATRON.ROTATION_NS;
			if(y%2==0&&z%2==0) {
				pos = new VectorDescription(2*x*ATRON.UNIT,y*ATRON.UNIT,z*ATRON.UNIT);
				rot = ATRON.ROTATION_EW;
			}
    		else if(y%2==0&&z%2==1)  {
    			pos = new VectorDescription(2*x*ATRON.UNIT+ATRON.UNIT,y*ATRON.UNIT,z*ATRON.UNIT);
    			rot = ATRON.ROTATION_NS;
    		}
    		else if(y%2==1&&z%2==0) {
    			pos = new VectorDescription(2*x*ATRON.UNIT+ATRON.UNIT,y*ATRON.UNIT,z*ATRON.UNIT);
    			rot = ATRON.ROTATION_UD;
    		}
    		else if(y%2==1&&z%2==1) {
    			pos = new VectorDescription(2*x*ATRON.UNIT,y*ATRON.UNIT,z*ATRON.UNIT);
    			rot = ATRON.ROTATION_NS;
    		}
			if(pos!=null&&(index==0||isConnectable(new ModulePosition("",pos,rot),mPos)&&empty(pos,mPos))) {
	        	mPos.add(new ModulePosition(Integer.toString(index), pos, rot));
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
