/**
 * Unified Simulator for Self-Reconfigurable Robots (USSR)
 * (C) University of Southern Denmark 2008
 * This software is distributed under the BSD open-source license.
 * For licensing see the file LICENCE.txt included in the root of the USSR distribution.
 */
package ussr.samples.atron.simulations;

import java.util.ArrayList;

import ussr.description.Robot;
import ussr.description.geometry.RotationDescription;
import ussr.description.geometry.VectorDescription;
import ussr.description.setup.ModulePosition;
import ussr.description.setup.WorldDescription;
import ussr.model.Controller;
import ussr.physics.PhysicsParameters;
import ussr.physics.PhysicsParameters.Material;
import ussr.samples.atron.ATRON;
import ussr.samples.atron.GenericATRONSimulation;

/**
 * A simulation for the ATRON used to test the physical properties of the module
 * @author Modular Robots @ MMMI
 */
public class ATRONTestSimulation extends GenericATRONSimulation {
	//String testType = "velocityTest";
	String testType = "torqueTest"; //double lift test
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
    			mPos.add(new ModulePosition("test "+i, new VectorDescription(x*ATRON.UNIT,y*ATRON.UNIT-Yoffset,z*ATRON.UNIT), ATRON.ROTATION_EW));
    		}
    		else {
    			mPos.add(new ModulePosition("test "+i, new VectorDescription(x*ATRON.UNIT,y*ATRON.UNIT-Yoffset,z*ATRON.UNIT), ATRON.ROTATION_NS));
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
    			mPos.add(new ModulePosition("test "+i, new VectorDescription(x*ATRON.UNIT,y*ATRON.UNIT-Yoffset,z*ATRON.UNIT), ATRON.ROTATION_EW));
    		}
    		else {
    			mPos.add(new ModulePosition("test "+i, new VectorDescription(x*ATRON.UNIT,y*ATRON.UNIT-Yoffset,z*ATRON.UNIT), ATRON.ROTATION_NS));
    		}
    		x++;z++;
    	}
    	mPos.add(new ModulePosition("base0", new VectorDescription(-1*ATRON.UNIT,-1*ATRON.UNIT-Yoffset,0*ATRON.UNIT), ATRON.ROTATION_UD));
    	int index =0;
    	for(x=-4;x<0;x++) {
        	for(y=-2;y<=-2;y++) {
        		for(z=-2;z<=2;z++) {
        			VectorDescription pos = null;
        			RotationDescription rot = ATRON.ROTATION_NS;
        			if(Math.abs(y)%2==0&&Math.abs(z)%2==0) {
        				pos = new VectorDescription(2*x*ATRON.UNIT,y*ATRON.UNIT-Yoffset,z*ATRON.UNIT);
        				rot = ATRON.ROTATION_EW;
        			}
	        		else if(Math.abs(y)%2==0&&Math.abs(z)%2==1)  {
	        			pos = new VectorDescription(2*x*ATRON.UNIT+ATRON.UNIT,y*ATRON.UNIT-Yoffset,z*ATRON.UNIT);
	        			rot = ATRON.ROTATION_NS;
	        		}
	        		else if(Math.abs(y)%2==1&&Math.abs(z)%2==0) {
	        			pos = new VectorDescription(2*x*ATRON.UNIT+ATRON.UNIT,y*ATRON.UNIT-Yoffset,z*ATRON.UNIT);
	        			rot = ATRON.ROTATION_UD;
	        		}
	        		else if(Math.abs(y)%2==1&&Math.abs(z)%2==1) {
	        			pos = new VectorDescription(2*x*ATRON.UNIT,y*ATRON.UNIT-Yoffset,z*ATRON.UNIT);
	        			rot = ATRON.ROTATION_NS;
	        		}
                    ModulePosition mpos;
                    mpos = new ModulePosition(index+"", pos, rot);
                    mPos.add(mpos);
	        		index++;
        		}
        	}
        }
		return mPos;
	}

	protected ArrayList<ModulePosition> buildRobot() {
		if(testType.equals("velocityTest")) return buildSnake(1);
		else if(testType.equals("torqueTest")) return buildTorqueSetup();
		else return buildTorqueSetup();
	}
}
