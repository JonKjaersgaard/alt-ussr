/**
 * Uniform Simulator for Self-reconfigurable (modular) Robots
 * 
 * (C) 2006 University of Southern Denmark
 */
package communication.local;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.jme.math.Quaternion;

import ussr.model.Controller;
import ussr.model.Module;
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
//import ussr.samples.odin.OdinSampleController1;

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
        world.setPlaneTexture(WorldDescription.GRID_TEXTURE);
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
        
        
        //int nBalls=100, xMax=10, yMax=1,zMax=10; //Plane
        int nBalls=40, xMax=4, yMax=3,zMax=4; //Cube
        
        for(int x=0;x<xMax;x++) {
        	for(int y=0;y<yMax;y++) {
        		for(int z=0;z<zMax;z++) {
        			if((x+y+z)%2==0) {
        				//VectorDescription pos = new VectorDescription(x*unit,y*unit-0.48f,z*unit);
        				VectorDescription pos = new VectorDescription(x*unit,y*unit-0.47f,z*unit);
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
        			VectorDescription pos = posFromBalls(ballPos.get(i),ballPos.get(j));
        			RotationDescription rot = rotFromBalls(ballPos.get(i),ballPos.get(j));
        			if(index%2==0) {
        				Quaternion q = new Quaternion();
        				q.fromAngles(pi/2, 0, 0);
        				rot.setRotation(rot.getRotation().mult(q));
        				
        			}
        			//modulePos.add(new ModulePosition(Integer.toString(index),"OdinHinge", pos, rot));
        			modulePos.add(new ModulePosition(Integer.toString(index),"OdinMuscle", pos, rot));
        			//if(index%2==0) modulePos.add(new WorldDescription.ModulePosition(Integer.toString(index),"OdinMuscle", pos, rot));
        			//if(index%2==0) modulePos.add(new WorldDescription.ModulePosition(Integer.toString(index),"OdinBattery", pos, rot));
        			//else modulePos.add(new WorldDescription.ModulePosition(Integer.toString(index),"OdinWheel", pos, rot));
        			index++;
        			//System.out.println("Ball "+i+" and ball "+j+" are neighbors");
        		}
        	}
        }
       ArrayList<ModuleConnection> connections = allConnections(ballPos,modulePos);
       System.out.println("#connection found = "+connections.size());
       world.setModuleConnections(connections);
        System.out.println("#Balls Placed  = "+ballPos.size());
        System.out.println("#Module Placed = "+modulePos.size());
        
        /*
        float half = (float)(Math.PI);
    	float quart = (float)(0.5*Math.PI);
    	float eigth = (float)(0.25*Math.PI);
    	
    	float unit = 0.08f;//8 cm between two lattice positions on physical atrons
    	RotationDescription rotation_NS = new RotationDescription(0,0,eigth+quart);//(0,0,eigth);
    	RotationDescription rotation_SN = new RotationDescription(0,half,eigth);
    	RotationDescription rotation_UD = new RotationDescription(quart,eigth,0);
    	RotationDescription rotation_EW = new RotationDescription(new VectorDescription(eigth,0,0),new VectorDescription(0,quart,0));
    	
    	float Yoffset = 0.25f-0.5f;
        
        modulePos.add(new WorldDescription.ModulePosition("wheel4", "ATRON", new VectorDescription(1*unit,-2*unit-Yoffset,-1*unit), rotation_NS));*/
        
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
