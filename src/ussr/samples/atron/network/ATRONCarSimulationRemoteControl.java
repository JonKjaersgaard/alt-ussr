/**
 * Unified Simulator for Self-Reconfigurable Robots (USSR)
 * (C) University of Southern Denmark 2008
 * This software is distributed under the BSD open-source license.
 * For licensing see the file LICENCE.txt included in the root of the USSR distribution.
 */
package ussr.samples.atron.network;

import java.util.ArrayList;

import ussr.description.Robot;
import ussr.description.geometry.VectorDescription;
import ussr.description.setup.ModulePosition;
import ussr.description.setup.WorldDescription;
import ussr.model.Controller;
import ussr.physics.PhysicsParameters;
import ussr.samples.atron.ATRON;
import ussr.samples.atron.GenericATRONSimulation;
import ussr.samples.atron.simulations.ATRONCarSimulation;




public class ATRONCarSimulationRemoteControl extends GenericATRONSimulation {
	
	public static enum ObstacleType { NONE, LINE, CIRCLE }
    private ObstacleType obstacle = ObstacleType.LINE;
    
    public void setObstableType(ObstacleType obstacle) { this.obstacle = obstacle; }
    
	public static void main( String[] args ) {
		PhysicsParameters.get().setWorldDampingLinearVelocity(0.5f);
		PhysicsParameters.get().setRealisticCollision(true);
		new ATRONCarSimulationRemoteControl().main();
    }
	
	protected Robot getRobot() {

        ATRON robot = new ATRON() {
            public Controller createController() {
                return new ATRONCarControllerRemoteControl();
            }
        };
        robot.setGentle();
        return robot;
    }
	
	protected ArrayList<ModulePosition> buildCar() {
    	float Yoffset = 0.25f;
    	ArrayList<ModulePosition> mPos = new ArrayList<ModulePosition>();
    	mPos.add(new ModulePosition("driver0:10000", new VectorDescription(2*0*ATRON.UNIT,0*ATRON.UNIT-Yoffset,0*ATRON.UNIT), ATRON.ROTATION_EW));
    	mPos.add(new ModulePosition("axleOne5", new VectorDescription(1*ATRON.UNIT,-1*ATRON.UNIT-Yoffset,0*ATRON.UNIT), ATRON.ROTATION_UD));
    	mPos.add(new ModulePosition("axleTwo6", new VectorDescription(-1*ATRON.UNIT,-1*ATRON.UNIT-Yoffset,0*ATRON.UNIT), ATRON.ROTATION_UD));
    	mPos.add(new ModulePosition("wheel1:10001", new VectorDescription(-1*ATRON.UNIT,-2*ATRON.UNIT-Yoffset,1*ATRON.UNIT), ATRON.ROTATION_SN));
    	mPos.add(new ModulePosition("wheel2", new VectorDescription(-1*ATRON.UNIT,-2*ATRON.UNIT-Yoffset,-1*ATRON.UNIT), ATRON.ROTATION_NS));
    	mPos.add(new ModulePosition("wheel3", new VectorDescription(1*ATRON.UNIT,-2*ATRON.UNIT-Yoffset,1*ATRON.UNIT), ATRON.ROTATION_SN));
    	mPos.add(new ModulePosition("wheel4", new VectorDescription(1*ATRON.UNIT,-2*ATRON.UNIT-Yoffset,-1*ATRON.UNIT), ATRON.ROTATION_NS));
        return mPos;
	}

	protected ArrayList<ModulePosition> buildRobot() {
		return buildCar();
	}
    
    private static final int N_LINE_OBSTACLES = 20;
    private static final float OBSTACLE_DIST = 0.2f;
    private static final float LINE_OBSTACLE_CENTER = 0;
    private static final int N_CIRCLE_OBSTACLES = 33;
    private static final float CIRCLE_OBSTACLE_RADIUS = 1.5f;
    private static final float OBSTACLE_Y = 0f;
    protected void changeWorldHook(WorldDescription world) {
        if(obstacle==ObstacleType.LINE) {
            System.out.println("Generating line obstacles");
            VectorDescription[] obstacles = new VectorDescription[N_LINE_OBSTACLES];
            for(int i=0; i<N_LINE_OBSTACLES; i++) {
                obstacles[i] = new VectorDescription(1.0f, OBSTACLE_Y, LINE_OBSTACLE_CENTER-(N_LINE_OBSTACLES/2*OBSTACLE_DIST)+i*OBSTACLE_DIST);
            }
            world.setObstacles(obstacles);
        } else if(obstacle==ObstacleType.CIRCLE) {
            System.out.println("Generating circle obstacles");
            VectorDescription[] obstacles = new VectorDescription[N_CIRCLE_OBSTACLES];
            for(int i=0; i<N_CIRCLE_OBSTACLES; i++) {
                obstacles[i] = new VectorDescription(
                        ((float)(CIRCLE_OBSTACLE_RADIUS*Math.cos(((double)i)/N_CIRCLE_OBSTACLES*Math.PI*2))),
                        OBSTACLE_Y,
                        ((float)(CIRCLE_OBSTACLE_RADIUS*Math.sin(((double)i)/N_CIRCLE_OBSTACLES*Math.PI*2)))
                );
            }
            world.setObstacles(obstacles);
        }
    }
}
