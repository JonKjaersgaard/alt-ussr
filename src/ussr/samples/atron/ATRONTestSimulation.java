package ussr.samples.atron;

import java.util.ArrayList;

import ussr.model.Controller;
import ussr.robotbuildingblocks.ModulePosition;
import ussr.physics.PhysicsParameters;
import ussr.physics.PhysicsParameters.Material;
import ussr.robotbuildingblocks.Robot;
import ussr.robotbuildingblocks.RotationDescription;
import ussr.robotbuildingblocks.VectorDescription;
import ussr.robotbuildingblocks.WorldDescription;

/**
 * A sample ATRON simulation
 * @author Modular Robots @ MMMI
 */
public class ATRONTestSimulation extends GenericATRONSimulation {
	String testType = "velocityTest";
	//String testType = "torqueTest"; //double lift test
	//String testType = "torqueHoldTest"; //double lift and hold test
	
	
	public static void main( String[] args ) {
        new ATRONTestSimulation().main();
    }
	
	protected Robot getRobot() {
		ATRON atron = new ATRON() {
            public Controller createController() {
                return new ATRONTestController1(testType); 
            }
        };
        PhysicsParameters.get().setRealisticCollision(true);
        PhysicsParameters.get().setPlaneMaterial(Material.WOOD);
        atron.setRealistic();
        
        return atron;
    }
	protected void changeWorldHook(WorldDescription world) {
		world.setPlaneTexture(WorldDescription.GRID_TEXTURE);
		if(testType.equals("velocityTest")) PhysicsParameters.get().setGravity(0);
		if(testType.equals("torqueTest")) world.setPlaneTexture(WorldDescription.GRID_TEXTURE);
    }
	private static ArrayList<ModulePosition> buildSnake(int length) {
    	float Yoffset = 0.0f; //0.4f;
    	ArrayList<ModulePosition> mPos = new ArrayList<ModulePosition>();
    	int x=0,y=0,z=0;
    	for(int i=0;i<length;i++) {
    		if(i%2==0) {
    			mPos.add(new ModulePosition("test "+i, new VectorDescription(x*unit,y*unit-Yoffset,z*unit), rotation_EW));
    		}
    		else {
    			mPos.add(new ModulePosition("test "+i, new VectorDescription(x*unit,y*unit-Yoffset,z*unit), rotation_NS));
    		}
    		x++;z++;
    	}
		return mPos;
	}
	private static ArrayList<ModulePosition> buildTorqueSetup() {
    	float Yoffset = 0.28f; //0.4f;
    	ArrayList<ModulePosition> mPos = new ArrayList<ModulePosition>();
    	int x=0,y=0,z=0;
    	for(int i=0;i<3;i++) {
    		if(i%2==0) {
    			mPos.add(new ModulePosition("test "+i, new VectorDescription(x*unit,y*unit-Yoffset,z*unit), rotation_EW));
    		}
    		else {
    			mPos.add(new ModulePosition("test "+i, new VectorDescription(x*unit,y*unit-Yoffset,z*unit), rotation_NS));
    		}
    		x++;z++;
    	}
    	mPos.add(new ModulePosition("base0", new VectorDescription(-1*unit,-1*unit-Yoffset,0*unit), rotation_UD));
    	int index =0;
    	for(x=-4;x<0;x++) {
        	for(y=-2;y<=-2;y++) {
        		for(z=-2;z<=2;z++) {
        			VectorDescription pos = null;
        			RotationDescription rot = rotation_NS;
        			if(Math.abs(y)%2==0&&Math.abs(z)%2==0) {
        				pos = new VectorDescription(2*x*unit,y*unit-Yoffset,z*unit);
        				rot = rotation_EW;
        			}
	        		else if(Math.abs(y)%2==0&&Math.abs(z)%2==1)  {
	        			pos = new VectorDescription(2*x*unit+unit,y*unit-Yoffset,z*unit);
	        			rot = rotation_NS;
	        		}
	        		else if(Math.abs(y)%2==1&&Math.abs(z)%2==0) {
	        			pos = new VectorDescription(2*x*unit+unit,y*unit-Yoffset,z*unit);
	        			rot = rotation_UD;
	        		}
	        		else if(Math.abs(y)%2==1&&Math.abs(z)%2==1) {
	        			pos = new VectorDescription(2*x*unit,y*unit-Yoffset,z*unit);
	        			rot = rotation_NS;
	        		}
                    ModulePosition mpos;
                    mpos = new ModulePosition(index+"", pos, rot);
                    mPos.add(mpos);
	        		index++;
        		}
        	}
        }
    	/*mPos.add(new WorldDescription.ModulePosition("base1", new VectorDescription(0*unit,-2*unit-Yoffset,0*unit), rotation_EW));
    	mPos.add(new WorldDescription.ModulePosition("base2", new VectorDescription(-2*unit,-2*unit-Yoffset,0*unit), rotation_EW));
    	mPos.add(new WorldDescription.ModulePosition("base3", new VectorDescription(-1*unit,-2*unit-Yoffset,-1*unit), rotation_NS));
    	mPos.add(new WorldDescription.ModulePosition("base4", new VectorDescription(-1*unit,-2*unit-Yoffset,1*unit), rotation_NS));*/
		return mPos;
	}

	protected ArrayList<ModulePosition> buildRobot() {
		if(testType.equals("velocityTest")) return buildSnake(1);
		else if(testType.equals("torqueTest")) return buildTorqueSetup();
		else return buildTorqueSetup();
	}
}
