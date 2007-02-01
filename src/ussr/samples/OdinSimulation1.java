/**
 * Uniform Simulator for Self-reconfigurable (modular) Robots
 * 
 * (C) 2006 University of Southern Denmark
 */
package ussr.samples;

import java.util.ArrayList;

import ussr.physics.PhysicsFactory;
import ussr.physics.PhysicsLogger;
import ussr.physics.PhysicsSimulation;
import ussr.robotbuildingblocks.Robot;
import ussr.robotbuildingblocks.RotationDescription;
import ussr.robotbuildingblocks.VectorDescription;
import ussr.robotbuildingblocks.WorldDescription;

/**
 * Simple Odin simulation test setup
 * 
 * @author david
 *
 */
public class OdinSimulation1 extends GenericSimulation {
    
    public static void main( String[] args ) {
    	new OdinSimulation1().runSimulation(null,true);
    }
    public void runSimulation(WorldDescription world, boolean startPaused) {
        PhysicsLogger.setDefaultLoggingLevel();
        final PhysicsSimulation simulation = PhysicsFactory.createSimulator();
        simulation.setRobot(getRobot());
        if(world==null) world = createWorld();
        simulation.setWorld(world);
        simulation.setPause(startPaused);

        // Start
        simulation.start();
    }
    /**
     * Create a world description for our simulation
     * @return the world description
     */
    private static WorldDescription createWorld() {
    	WorldDescription world = new WorldDescription();
        world.setPlaneSize(5);
        ArrayList<WorldDescription.ModulePosition> mPos = new ArrayList<WorldDescription.ModulePosition>();
        //printConnectorPos();
        float unit = 0.18f;
        int index=0;
        int nModules = 1;
        for(int x=-2;x<2;x++) {
        	for(int y=-2;y<2;y++) {
        		for(int z=-2;z<2;z++) {
        			if((x+y+z)%2==0) {
        				VectorDescription pos = new VectorDescription(x*unit,y*unit,z*unit);
        				if(index<nModules) mPos.add(new WorldDescription.ModulePosition(Integer.toString(index), pos, new RotationDescription(0,0,0)));
    	        		index++;
        			}
        		}
        	}
        }
        world.setModulePositions(mPos);
        System.out.println("#Modules Placed = "+mPos.size());
        world.setModuleConnections(new WorldDescription.Connection[] {
              //  new WorldDescription.Connection("leftleg",4,"middle",6)
                //,new WorldDescription.Connection("rightleg",2,"middle",4)
        });               
        return world;
    }
    
    public static void printConnectorPos() {
        float unit = (float) (0.045f/Math.sqrt(2)); 
        for(int x=-2;x<2;x++) {
        	for(int y=-2;y<2;y++) {
        		for(int z=-2;z<2;z++) {
        			if((x+y+z)%2==0&&(x*x+y*y+z*z)<3&&!(x==0&&y==0&&z==0)) {
        				System.out.println("new VectorDescription("+x*unit+"f, "+y*unit+"f, "+z*unit+"f),");
        			}
        		}
        	}
        }
    }
    @Override
    protected Robot getRobot() {
        return new OdinMuscle();
    }
}
