package ussr.samples.atron;

import java.util.ArrayList;

import ussr.model.Controller;
import ussr.robotbuildingblocks.ModulePosition;
import ussr.robotbuildingblocks.Robot;
import ussr.robotbuildingblocks.VectorDescription;

/**
 * A simulation of an ATRON snake robot
 * 
 * @author Modular Robots @ MMMI
 *
 */
public class ATRONSnakeSimulation extends GenericATRONSimulation {
	
	
	public static void main( String[] args ) {
        new ATRONSnakeSimulation().main();
    }
	
	protected Robot getRobot() {
        return new ATRON() {
            public Controller createController() {
                //return new ATRONSnakeController1();
            	return new ATRONSnakeController2();
            }
        };
    }
	private static ArrayList<ModulePosition> buildSnake(int length) {
    	float Yoffset = 0.4f;
    	ArrayList<ModulePosition> mPos = new ArrayList<ModulePosition>();
    	int x=0,y=0,z=0;
    	for(int i=0;i<length;i++) {
    		if(i%2==0) {
    			mPos.add(new ModulePosition("snake "+i, new VectorDescription(x*unit,y*unit-Yoffset,z*unit), rotation_EW));
    		}
    		else {
    			mPos.add(new ModulePosition("snake "+i, new VectorDescription(x*unit,y*unit-Yoffset,z*unit), rotation_NS));
    		}
    		x++;z++;
    	}
		return mPos;
	}

	protected ArrayList<ModulePosition> buildRobot() {
		return buildSnake(4);
	}
}
