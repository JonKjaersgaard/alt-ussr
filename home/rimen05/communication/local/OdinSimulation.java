/**
 * Uniform Simulator for Self-reconfigurable (modular) Robots
 * 
 * (C) 2006 University of Southern Denmark
 */
package communication.local;

import java.io.PrintWriter;
import java.util.ArrayList;

import com.jme.math.Quaternion;

import franco.util.OdinNEDTopologyWriter;

import ussr.description.Robot;
import ussr.description.geometry.RotationDescription;
import ussr.description.geometry.VectorDescription;
import ussr.description.setup.ModuleConnection;
import ussr.description.setup.ModulePosition;
import ussr.description.setup.WorldDescription;
import ussr.model.Controller;
import ussr.physics.PhysicsFactory;
import ussr.physics.PhysicsLogger;
import ussr.physics.PhysicsParameters;
import ussr.physics.PhysicsSimulation;
import ussr.samples.GenericSimulation;
import ussr.samples.odin.modules.OdinBall;
import ussr.samples.odin.modules.OdinMuscle;


/**
 * Simulation of local communication for Odin
 * 
 * @author david (franco's mods)
 *
 */
public class OdinSimulation extends GenericSimulation {
	private static float unit = (float)Math.sqrt((0.18f*0.18f)/2);
	private static float pi = (float)Math.PI;
	//nBalls=100, xMax=10, yMax=1,zMax=10; PLANE
	private static int nBalls = 100;
	private static int xMax = 3;
	private static int yMax = 1;
	private static int zMax = 3;
	public static float pe = 0.1f;//0 to 1, modules sending information out.
	public static float pne = 1.0f;//0 to 1, proportion of modules the information is transmitted to.
	public static float pp = 0.1f;
	
    public static void main( String[] args ) {
    	for(int i=0;i<args.length;i++) {
			if(args[i].contains("nBalls=")) {
				OdinSimulation.nBalls = Integer.parseInt(args[i].subSequence(args[i].indexOf('=')+1, args[i].length()).toString());
			}
			else if(args[i].contains("xMax=")) {
				OdinSimulation.xMax = Integer.parseInt(args[i].subSequence(args[i].indexOf('=')+1, args[i].length()).toString());
			}
			else if(args[i].contains("yMax=")) {
				OdinSimulation.yMax = Integer.parseInt(args[i].subSequence(args[i].indexOf('=')+1, args[i].length()).toString());
			}
			else if(args[i].contains("zMax=")) {
				OdinSimulation.zMax = Integer.parseInt(args[i].subSequence(args[i].indexOf('=')+1, args[i].length()).toString());
			}
			else if(args[i].contains("pe=")) {
				OdinSimulation.pe = Float.parseFloat(args[i].subSequence(args[i].indexOf('=')+1, args[i].length()).toString());
			}
			else if(args[i].contains("pne=")) {
				OdinSimulation.pne = Float.parseFloat(args[i].subSequence(args[i].indexOf('=')+1, args[i].length()).toString());
			}
			else if(args[i].contains("pp=")) {
				OdinSimulation.pp = Float.parseFloat(args[i].subSequence(args[i].indexOf('=')+1, args[i].length()).toString());
			}
			else {
				System.out.println("Unrecognized option "+args[i]);
			}
		}
    	//Here we call an overwritten method.
    	new OdinSimulation().runSimulation(null,true);
    	//System.out.println("\nSimulation Stopped");
    	System.exit(0);
    }
    
    //Here the abstract method is overwritten.
    public void runSimulation(WorldDescription world, boolean startPaused) {
        PhysicsLogger.setDefaultLoggingLevel();
        PhysicsParameters.get().setResolutionFactor(2); // Needed for large Odin structures
        //PhysicsFactory.getOptions().setTopologyWriter(new NEDTopologyWriter(new PrintWriter(System.out))); //YES!!!
        PhysicsFactory.getOptions().setTopologyWriter(new OdinNEDTopologyWriter(new PrintWriter(System.out)));
        final PhysicsSimulation simulation = PhysicsFactory.createSimulator();
        
        simulation.setRobot(new OdinMuscle(){
        	public Controller createController() {
        		return new OdinController("OdinMuscle");
        		//Here it is created a constructor and
        		//and defined a function.
        	}},"OdinMuscle");
        
        simulation.setRobot(new OdinBall(){
        	public Controller createController() {
        		return new OdinController("OdinBall");
        	}},"OdinBall");
        
        //Here we call another overwritten method.
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
        //
        world.setPlaneTexture(WorldDescription.WHITE_GRID_TEXTURE);
        //
        ArrayList<ModulePosition> ballPos = new ArrayList<ModulePosition>();
        ArrayList<ModulePosition> modulePos = new ArrayList<ModulePosition>();
        //printConnectorPos();
        int index=0;
        //int nBalls=0,xMax=0, yMax=0,zMax=0; modulePos.add(new WorldDescription.ModulePosition("0","OdinMuscle", new VectorDescription(0,0,0), new RotationDescription(0,0,0)));
        //int nBalls=2, xMax=1, yMax=2,zMax=2;
        //int nBalls=3, xMax=3, yMax=2,zMax=2;
        //int nBalls=5, xMax=5, yMax=5,zMax=5;
        //int nBalls=4, xMax=3, yMax=2,zMax=2;
        //int nBalls=10, xMax=3, yMax=2,zMax=3;
        //int nBalls=7, xMax=4, yMax=2,zMax=8;
        //int nBalls=20, xMax=4, yMax=4,zMax=4;
        
        //int nBalls=10, xMax=5, yMax=1,zMax=2; //Plane
        //int nBalls=100, xMax=10, yMax=1,zMax=10; //Plane
        //int nBalls=40, xMax=4, yMax=3,zMax=4; //Cube
        
        for(int x=0;x<OdinSimulation.xMax;x++) {
        	for(int y=0;y<yMax;y++) {
        		for(int z=0;z<zMax;z++) {
        			if((x+y+z)%2==0) {
        				//VectorDescription pos = new VectorDescription(x*unit,y*unit-0.48f,z*unit);
        				VectorDescription pos = new VectorDescription(x*unit,y*unit-0.47f,z*unit);
        				if(index<nBalls) {
       						ballPos.add(new ModulePosition("ball-"+Integer.toString(index),"OdinBall", pos, new RotationDescription(0,0,0)));
        				}
    	        		index++;
        			}
        		}
        	}
        }
        for(int i=0;i<ballPos.size();i++) {
        	for(int j=i+1;j<ballPos.size();j++) {
        		if(isNeighborBalls(ballPos.get(i),ballPos.get(j))) {
        			VectorDescription pos = posFromBalls(ballPos.get(i),ballPos.get(j));
        			RotationDescription rot = rotFromBalls(ballPos.get(i),ballPos.get(j));
        			if(index%2==0) {
        				Quaternion q = new Quaternion();
        				q.fromAngles(pi/2, 0, 0);
        				rot.setRotation(rot.getRotation().mult(q));
        				
        			}
        			//modulePos.add(new ModulePosition(Integer.toString(index),"OdinHinge", pos, rot));
        			modulePos.add(new ModulePosition("module-"+Integer.toString(index),"OdinMuscle", pos, rot));
        			//if(index%2==0) modulePos.add(new WorldDescription.ModulePosition(Integer.toString(index),"OdinMuscle", pos, rot));
        			//if(index%2==0) modulePos.add(new WorldDescription.ModulePosition(Integer.toString(index),"OdinBattery", pos, rot));
        			//else modulePos.add(new WorldDescription.ModulePosition(Integer.toString(index),"OdinWheel", pos, rot));
        			index++;
        			//System.out.println("Ball "+i+" and ball "+j+" are neighbours");
        		}
        	}
        }
        ArrayList<ModuleConnection> connections = allConnections(ballPos,modulePos);
        System.out.println("nBalls = "+OdinSimulation.nBalls+" xMax = "+OdinSimulation.xMax+" yMax = "+
        		OdinSimulation.yMax+" zMax = "+OdinSimulation.zMax+" pe = "+OdinSimulation.pe+" pne = "+
        		OdinSimulation.pne+" pp = "+OdinSimulation.pp);
        System.out.println("#connection found = "+connections.size());
        world.setModuleConnections(connections);
        System.out.println("#Balls Placed  = "+ballPos.size());
        System.out.println("#Modules Placed = "+modulePos.size());
        
        modulePos.addAll(ballPos);
        world.setModulePositions(modulePos);
        System.out.println("#Total         = "+modulePos.size());
        System.out.println("#Modules per Ball (avg)= "+(((float)connections.size())/((float)ballPos.size())));
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
    	if(dist-Math.abs(Math.sqrt(2*unit*unit))/2<0.0000001) return true;
    	return dist==(float)Math.sqrt(2*unit*unit)/2;
    }
	
	public static boolean isNeighborBalls(ModulePosition ball1, ModulePosition ball2) {
    	float dist = ball1.getPosition().distance(ball2.getPosition());
    	if(dist-Math.abs(Math.sqrt(2*unit*unit))<0.0000001) return true;
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
