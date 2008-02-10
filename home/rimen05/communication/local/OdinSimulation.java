/**
 * Uniform Simulator for Self-reconfigurable (modular) Robots
 * 
 * (C) 2006 University of Southern Denmark
 */
package communication.local;

import java.util.ArrayList;

import ussr.model.Controller;
import ussr.physics.PhysicsFactory;
import ussr.physics.PhysicsLogger;
import ussr.physics.PhysicsParameters;
import ussr.physics.PhysicsSimulation;
import ussr.robotbuildingblocks.ModuleConnection;
import ussr.robotbuildingblocks.ModulePosition;
import ussr.robotbuildingblocks.Robot;
import ussr.robotbuildingblocks.RotationDescription;
import ussr.robotbuildingblocks.VectorDescription;
import ussr.robotbuildingblocks.WorldDescription;
import ussr.samples.GenericSimulation;
import ussr.samples.odin.OdinMuscle;
import ussr.samples.odin.OdinBall;
import ussr.samples.odin.OdinSampleController1;

/**
 * simulation of local communication for Odin
 * 
 * @author david (franco's mods)
 *
 */
public class OdinSimulation extends GenericSimulation {
	private static float unit = (float)Math.sqrt((0.18f*0.18f)/2);
	private static float pi = (float)Math.PI;
	
    public static void main( String[] args ) {
    	//Here we call an overwritten method.
    	new OdinSimulation().runSimulation(null,true);
    }
    
    //Here the abstract method is overwritten.
    public void runSimulation(WorldDescription world, boolean startPaused) {
        PhysicsLogger.setDefaultLoggingLevel();
        final PhysicsSimulation simulation = PhysicsFactory.createSimulator();
        PhysicsParameters.get().setResolutionFactor(2); // Needed for large Odin structures 
        
        simulation.setRobot(new OdinMuscle(){
        	public Controller createController() {
        		return new OdinSampleController1("OdinMuscle");
        	}},"OdinMuscle");
        
        simulation.setRobot(new OdinBall(){
        	public Controller createController() {
        		return new OdinSampleController1("OdinBall");
        	}},"OdinBall");
        
        //Here we call an overwritten method.
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
    //Here the abstract method is overwritten.
    private static WorldDescription createWorld() {
    	WorldDescription world = new WorldDescription();
        world.setPlaneSize(5);
        //Generic's
        ArrayList<ModulePosition> ballPos = new ArrayList<ModulePosition>();
        ArrayList<ModulePosition> modulePos = new ArrayList<ModulePosition>();
        //printConnectorPos();
        int index=0;
        //int nBalls=0,xMax=0, yMax=0,zMax=0; modulePos.add(new WorldDescription.ModulePosition("0","OdinMuscle", new VectorDescription(0,0,0), new RotationDescription(0,0,0)));
        //int nBalls=2, xMax=1, yMax=2,zMax=2;
       // int nBalls=3, xMax=3, yMax=2,zMax=2;
       // int nBalls=4, xMax=3, yMax=2,zMax=2;
       //int nBalls=8, xMax=3, yMax=2,zMax=2;
       //int nBalls=14, xMax=3, yMax=3,zMax=3;
        int nBalls=4, xMax=2, yMax=2,zMax=2; //With this parameters I form a tetahedron.
        //Number of balls and maximum distance between them over each axis - 1?
        //int nBalls=80, xMax=5, yMax=5,zMax=5; // Max on Ulrik's machine
        for(int x=0;x<xMax;x++) {
        	for(int y=0;y<yMax;y++) {
        		for(int z=0;z<zMax;z++) {
        			if((x+y+z)%2==0) { //This should be lattice related.
        				VectorDescription pos = new VectorDescription(x*unit,y*unit,z*unit);
        				if(index<nBalls) {
       						ballPos.add(new ModulePosition(Integer.toString(index),"OdinBall", pos, new RotationDescription(0,0,0)));
        				}
    	        		index++;
        			}
        		}
        	}
        }
        for(int i=0;i<ballPos.size();i++) {
        	for(int j=i+1;j<ballPos.size();j++) {
        		if(isNeighborBalls(ballPos.get(i),ballPos.get(j))) {
        			VectorDescription pos = posFromBalls(ballPos.get(i),ballPos.get(j));//Get position for the link.
        			RotationDescription rot = rotFromBalls(ballPos.get(i),ballPos.get(j));//Get rotation for the link.
        			modulePos.add(new ModulePosition(Integer.toString(index),"OdinMuscle", pos, rot));
        			index++;
        			//System.out.println("Ball "+i+" and ball "+j+" are neighbors");
        		}
        	}
        }
        ArrayList<ModuleConnection> connections = allConnections(ballPos,modulePos);
        world.setModuleConnections(connections);
        System.out.println("#Balls Placed  = "+ballPos.size());
        System.out.println("#Module Placed = "+modulePos.size());
        modulePos.addAll(ballPos);
        world.setModulePositions(modulePos);
        System.out.println("#Total         = "+modulePos.size());
        /*world.setModuleConnections(new WorldDescription.Connection[] {
              //  new WorldDescription.Connection("leftleg",4,"middle",6)
                //,new WorldDescription.Connection("rightleg",2,"middle",4)
        });*/
        return world;
    }
    
    private static ArrayList<ModuleConnection> allConnections(ArrayList<ModulePosition> ballPos, ArrayList<ModulePosition> modulePos) {
    	ArrayList<ModuleConnection> connections = new ArrayList<ModuleConnection>();
    	for(int i=0;i<ballPos.size();i++) {
    		for(int j=0;j<modulePos.size();j++) {
    			if(isConnectable(ballPos.get(i), modulePos.get(j))) {
    				connections.add(new ModuleConnection(ballPos.get(i).getName(),modulePos.get(j).getName()));
    			}
    		}
    	}
		return connections;
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
		if(x1-x2<0&&z1-z2<0) return new RotationDescription(0,-pi/4,0);
		else if(x1-x2<0&&z1-z2>0) return new RotationDescription(0,pi/4,0);
		else if(x1-x2<0&&y1-y2<0) return new RotationDescription(0,0,pi/4);
		else if(x1-x2<0&&y1-y2>0) return new RotationDescription(0,0,-pi/4);
		else if(y1-y2<0&&z1-z2<0) return new RotationDescription(0,pi/4,-pi/2);
		else if(y1-y2<0&&z1-z2>0) return new RotationDescription(0,-pi/4,-pi/2);
		System.out.println("("+(x1-x2)+","+(y1-y2)+","+(z1-z2)+")");
    	return new RotationDescription(0,0,0);
	}
	
	public static boolean isConnectable(ModulePosition ball, ModulePosition module) {
    	float dist = ball.getPosition().distance(module.getPosition());
    	return dist==(float)Math.sqrt(2*unit*unit)/2;
    }
	
	public static boolean isNeighborBalls(ModulePosition ball1, ModulePosition ball2) {
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
