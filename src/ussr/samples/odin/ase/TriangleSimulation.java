/**
 * Unified Simulator for Self-Reconfigurable Robots (USSR)
 * (C) University of Southern Denmark 2008
 * This software is distributed under the BSD open-source license.
 * For licensing see the file LICENCE.txt included in the root of the USSR distribution.
 */
package ussr.samples.odin.ase;

import java.util.ArrayList;

import ussr.description.Robot;
import ussr.description.geometry.VectorDescription;
import ussr.description.setup.ModulePosition;
import ussr.description.setup.WorldDescription;
import ussr.model.Controller;
import ussr.physics.PhysicsFactory;
import ussr.physics.PhysicsLogger;
import ussr.physics.PhysicsParameters;
import ussr.physics.PhysicsSimulation;
import ussr.samples.GenericSimulation;
import ussr.samples.atron.ATRON;
import ussr.samples.odin.OdinBuilder;
import ussr.samples.odin.modules.OdinBall;
import ussr.samples.odin.modules.OdinMuscle;
import ussr.samples.odin.network.OdinReflectionEventController;

/**
 * Simple Odin simulation test setup
 * 
 * @author david
 *
 */
public class TriangleSimulation extends GenericSimulation {
	
    public static void main( String[] args ) {
    	PhysicsParameters.get().setWorldDampingLinearVelocity(0.5f);
		PhysicsParameters.get().setRealisticCollision(true);
		PhysicsParameters.get().setMaintainRotationalJointPositions(false);
		PhysicsParameters.get().setResolutionFactor(2); // Needed for large Odin structures 
		new TriangleSimulation().runSimulation(null,false);
    }
    
    public void runSimulation(WorldDescription world, boolean startPaused) {
        PhysicsLogger.setDefaultLoggingLevel();
        final PhysicsSimulation simulation = PhysicsFactory.createSimulator();
        
        simulation.setRobot(new OdinMuscle(){
        	public Controller createController() {
        		return new OdinReflectionEventController("OdinMuscle");  
        	}},"OdinMuscle"); 
        
        simulation.setRobot(new OdinBall(){
        	public Controller createController() {
        		return new OdinReflectionEventController("OdinBall");
        	}},"OdinBall");
        
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
        world.setHasBackgroundScenery(false);
        world.setPlaneTexture(WorldDescription.WHITE_GRID_TEXTURE);
        OdinBuilder builder = new OdinBuilder();
        //int nBalls=0,xMax=0, yMax=0,zMax=0; = Arrays.asList(new WorldDescription.ModulePosition[] { new WorldDescription.ModulePosition("0","OdinMuscle", new VectorDescription(0,0,0), new RotationDescription(0,0,0))});
        //int nBalls=2, xMax=1, yMax=2,zMax=2;
        int nBalls=3, xMax=3, yMax=2,zMax=2;
       // int nBalls=4, xMax=3, yMax=2,zMax=2;
       //int nBalls=8, xMax=3, yMax=2,zMax=2;
       //int nBalls=14, xMax=3, yMax=3,zMax=3;
        //int nBalls=20, xMax=4, yMax=4,zMax=4;
        //int nBalls=80, xMax=5, yMax=5,zMax=5; // Max on Ulrik's machine
        
        ArrayList<ModulePosition> modulePos = builder.buildDenseBlob(nBalls,xMax,yMax,zMax);
        int portRC = 9900;
    	int portEvent = 9901;
        for(ModulePosition m:modulePos) {
        	if(m.getType()!="OdinBall") {
        		m.getProperties().put("portRC", ""+portRC);
        		m.getProperties().put("portEvent", ""+portEvent);
        		portRC+=2;
        		portEvent+=2;
        	}
        }
        world.setModulePositions(modulePos);
        world.setModuleConnections(builder.allConnections());
        System.out.println("#Total         = "+modulePos.size());
        return world;
    }
    protected ArrayList<ModulePosition> buildSnake(int length) {
    	float Yoffset = 0.4f;
    	ArrayList<ModulePosition> mPos = new ArrayList<ModulePosition>();
    	int x=0,y=0,z=0;
    	int portRC = 9900;
    	int portEvent = 9901;
    	for(int i=0;i<length;i++) {
    		if(i%2==0) {
    			mPos.add(new ModulePosition(i+"", ";portRC="+portRC+";portEvent="+portEvent, new VectorDescription(x*ATRON.UNIT,y*ATRON.UNIT-Yoffset,z*ATRON.UNIT), ATRON.ROTATION_EW));
    		}
    		else {
    			mPos.add(new ModulePosition(i+"", ";portRC="+portRC+";portEvent="+portEvent, new VectorDescription(x*ATRON.UNIT,y*ATRON.UNIT-Yoffset,z*ATRON.UNIT), ATRON.ROTATION_NS));
    		}
    		portRC+=2;portEvent+=2;
    		x++;z++;
    	}
    	return mPos;
	}

	@Override
	protected Robot getRobot() {
		return Robot.NO_DEFAULT;
	}
}
