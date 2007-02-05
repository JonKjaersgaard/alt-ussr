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
import ussr.robotbuildingblocks.WorldDescription.ModulePosition;

/**
 * Simple Odin simulation test setup
 * 
 * @author david
 *
 */
public class OdinSimulation1 extends GenericSimulation {
	private static float unit = (float)Math.sqrt((0.18f*0.18f)/2);
	private static float pi = (float)Math.PI;
	
    public static void main( String[] args ) {
    	new OdinSimulation1().runSimulation(null,true);
    }
    public void runSimulation(WorldDescription world, boolean startPaused) {
        PhysicsLogger.setDefaultLoggingLevel();
        final PhysicsSimulation simulation = PhysicsFactory.createSimulator();
        simulation.setRobot(new OdinMuscle(),"OdinMuscle");
        simulation.setRobot(new OdinBall(),"OdinBall");
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
        ArrayList<WorldDescription.ModulePosition> ballPos = new ArrayList<WorldDescription.ModulePosition>();
        ArrayList<WorldDescription.ModulePosition> modulePos = new ArrayList<WorldDescription.ModulePosition>();
        //printConnectorPos();
        int index=0;
        int nBalls = 5;
        for(int x=-2;x<2;x++) {
        	for(int y=-0;y<1;y++) {
        		for(int z=-2;z<2;z++) {
        			if((x+y+z)%2==0) {
        				VectorDescription pos = new VectorDescription(x*unit,y*unit,z*unit);
        				if(index<nBalls) {
       						ballPos.add(new WorldDescription.ModulePosition(Integer.toString(index),"OdinBall", pos, new RotationDescription(0,0,0)));
        				}
    	        		index++;
        			}
        		}
        	}
        }
        for(int i=0;i<ballPos.size();i++) {
        	for(int j=i+1;j<ballPos.size();j++) {
        		if(isNeighorBalls(ballPos.get(i),ballPos.get(j))) {
        			VectorDescription pos = posFromBalls(ballPos.get(i),ballPos.get(j));
        			RotationDescription rot = rotFromBalls(ballPos.get(i),ballPos.get(j));
        			modulePos.add(new WorldDescription.ModulePosition(Integer.toString(index),"OdinMuscle", pos, rot));
        			//System.out.println("Ball "+i+" and ball "+j+" are neighbors");
        		}
        	}
        }
        

        modulePos.addAll(ballPos);
        world.setModulePositions(modulePos);
        System.out.println("#Modules Placed = "+modulePos.size());
        world.setModuleConnections(new WorldDescription.Connection[] {
              //  new WorldDescription.Connection("leftleg",4,"middle",6)
                //,new WorldDescription.Connection("rightleg",2,"middle",4)
        });
        return world;
    }
    private static VectorDescription posFromBalls(ModulePosition p1, ModulePosition p2) {
    	VectorDescription pos = new VectorDescription((p1.getPosition().getX()+p2.getPosition().getX())/2,(p1.getPosition().getY()+p2.getPosition().getY())/2,(p1.getPosition().getZ()+p2.getPosition().getZ())/2);
		return pos;
	}
	private static RotationDescription rotFromBalls(ModulePosition p1, ModulePosition p2) {
		float x1 = p1.getPosition().getX();
		float y1 = p1.getPosition().getY();
		float z1 = p1.getPosition().getZ();
		float x2 = p2.getPosition().getX();
		float y2 = p2.getPosition().getY();
		float z2 = p2.getPosition().getZ();
		if(x1-x2<0&&z1-z2<0) {
			return new RotationDescription(0,-pi/4,0);
		}
		if(x1-x2<0&&z1-z2>0) {
			return new RotationDescription(0,pi/4,0);
		}
		System.out.println("("+(x1-x2)+","+(y1-y2)+","+(z1-z2)+")");
    	return new RotationDescription(0,0,0);
	}
	public static boolean isNeighorBalls(ModulePosition ball1, ModulePosition ball2) {
    	float dist = ball1.getPosition().distance(ball2.getPosition());
    	return dist==(float)Math.sqrt(2*unit*unit);
    }
    public static void printConnectorPos() {
    	for(int x=-2;x<2;x++) {
        	for(int y=-2;y<2;y++) {
        		for(int z=-2;z<2;z++) {
        			if((x+y+z)%2==0&&(x*x+y*y+z*z)<3&&!(x==0&&y==0&&z==0)) {
        				System.out.println("new VectorDescription("+x+"*unit, "+y+"*unit, "+z+"*unit),");
        			}
        		}
        	}
        }
    }
   // protected Robot getRobot() {
   //     return new OdinMuscle();
   // }
	@Override
	protected Robot getRobot() {
		// TODO Auto-generated method stub
		return null;
	}
}
