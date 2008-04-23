/**
 * Unified Simulator for Self-Reconfigurable Robots (USSR)
 * (C) University of Southern Denmark 2008
 * This software is distributed under the BSD open-source license.
 * For licensing see the file LICENCE.txt included in the root of the USSR distribution.
 */
package onlineLearning.mtran;

import com.jme.math.Vector3f;

import onlineLearning.SkillFileManager;
import onlineLearning.SkillLearner;
import ussr.description.setup.WorldDescription;
import ussr.model.Controller;
import ussr.physics.PhysicsParameters;
import ussr.physics.PhysicsSimulation;
import ussr.samples.mtran.MTRANSampleController2;
import ussr.samples.mtran.MTRANSimulation;

/**
 * Simple MTRAN simulation with a snake robot
 * 
 * @author david
 *
 */
public class MTRANLearningSimulation extends MTRANSimulation { 

	boolean loadSkillsFromFile = true;
	static String trialID = System.currentTimeMillis()%1000+"";
	public static void main( String[] args ) {
		if(args.length>0) robot=args[0];
		if(args.length>1) trialID=args[1];
		new MTRANLearningSimulation().runSimulation(null,false);
	}
	public Controller getController(String type) {
		return new MTRANLearningController();
	}

	@Override
	public void changeWorldHook(WorldDescription world) {
	    world.setPlaneTexture(WorldDescription.GRID_TEXTURE);
	    world.setHasBackgroundScenery(false);
	    PhysicsParameters.get().setPhysicsSimulationStepSize(0.005f);
	    PhysicsParameters.get().setResolutionFactor(1);
	    SkillFileManager.initLogFiles("MTRAN",robot,trialID);
		PhysicsParameters.get().setWorldDampingLinearVelocity(0.5f);
		if(loadSkillsFromFile) SkillFileManager.loadSkills();
	}
	public Vector3f getRobotCM() {
    	Vector3f cm = new Vector3f();
    	int nModulesInRobot = 0;
        for(int i=0;i<simulation.getModules().size();i++) {
    		cm = cm.addLocal((Vector3f)simulation.getHelper().getModulePos(simulation.getModules().get(i)).get());
    		nModulesInRobot++;
        }
        cm = cm.multLocal(1.0f/nModulesInRobot);
        return cm;
    }
	int counter=0;
	public void physicsTimeStepHook(PhysicsSimulation simulation) {
		//simulation.setGravity(0.0f);
		counter++;
		/*if(counter%1==0) {
			//System.out.println("{"+simulation.getTime()+","+getRobotCM().y+"},");
		}
		if(simulation.getTime()<20) {
			SkillLearner.evalPeriode = 4.5f;
		}
		else if(simulation.getTime()<40) {
			SkillLearner.evalPeriode = 3.5f;
		}
		else if(simulation.getTime()<60) {
			SkillLearner.evalPeriode = 2.5f;
		}
		else {
			SkillLearner.evalPeriode = 1.5f;
		}*/
		if(simulation.getTime()>(3600+100)) {
       		System.out.println("stopping simulation at time "+simulation.getTime());
   			simulation.stop();
       	}
	}
	static String robot = "CATERPILLAR";
	protected void constructRobot() {
		if(robot.equals("SINGLE")) {
			addModule(0,0,0,ORI2,"X");
		}
		if(robot.equals("DOUBLE")) {
			addModule(0,0,0,ORI2,"S1"+"-"+robot);
			addModule(2,0,0,ORI2,"S2"+"-"+robot);
		}
		//Caterpillar
		if(robot.equals("CATERPILLAR")) {
			addModule(0,0,0,ORI2,"S1"+"-"+robot);
			addModule(2,0,0,ORI2,"S2"+"-"+robot);
			addModule(4,0,0,ORI2,"S3"+"-"+robot);
			addModule(6,0,0,ORI2,"S4"+"-"+robot);
			addModule(8,0,0,ORI2,"S5"+"-"+robot);
			addModule(10,0,0,ORI2,"S6"+"-"+robot);
			//addModule(12,0,0,ORI2,"S7"+"-"+robot);
			//addModule(14,0,0,ORI2,"S8"+"-"+robot);
		}
		//SNAKE
		if(robot.equals("SNAKE")) {
			addModule(0,0,0,ORI2,"S1"+"-"+robot);
			addModule(2,0,0,ORI2X,"S2"+"-"+robot);
			addModule(4,0,0,ORI2,"S3"+"-"+robot);
			addModule(6,0,0,ORI2X,"S4"+"-"+robot);
			addModule(8,0,0,ORI2,"S5"+"-"+robot);
			addModule(10,0,0,ORI2X,"S6"+"-"+robot);
			//addModule(12,0,0,ORI2,"S7"+"-"+robot);
			//addModule(14,0,0,ORI2,"S8"+"-"+robot);
		}
		if(robot.equals("LOOP")) {
			addModule(0,0,0,ORI2,"front"+"-"+robot);
			addModule(2,0,0,ORI2,"S2"+"-"+robot);
			addModule(4,0,0,ORI2,"S3"+"-"+robot);
			addModule(6,0,0,ORI2,"S4"+"-"+robot);
			addModule(8,0,0,ORI2,"S5"+"-"+robot);
			addModule(10,0,0,ORI2,"back"+"-"+robot);
		}
		if(robot.equals("SIDEWIDER")) {
			addModule(0,0,0,ORI2X,"S1"+"-"+robot);
			addModule(2,0,0,ORI2X,"S2"+"-"+robot);
			addModule(4,0,0,ORI2X,"S3"+"-"+robot);
			addModule(6,0,0,ORI2X,"S4"+"-"+robot);
			addModule(8,0,0,ORI2X,"S5"+"-"+robot);
			addModule(10,0,0,ORI2X,"S6"+"-"+robot);
			//addModule(12,0,0,ORI2,"S7"+"-"+robot);
			//addModule(14,0,0,ORI2,"S8"+"-"+robot);
		}
		
		//Mini- 4-legged Walker
		if(robot.equals("MINI")) {
			addModule(0,0,0,ORI3,"x1"+"-"+robot);
			addModule(1,0,2,ORI3Y,"y1"+"-"+robot);
			addModule(-0.5f,0,1.5f,ORI2,"z1"+"-"+robot);
			addModule(1.5f,0,0.5f,ORI2Y,"æ1"+"-"+robot);
		}
		
		//Axisymmetric 4-Leg 
		if(robot.equals("WALKER")) {
			addModule(0,0,0,ORI3,"x1"+"-"+robot);
			addModule(0,0,-2,ORI3X,"x2"+"-"+robot);
			addModule(1,0,2,ORI3Y,"y1"+"-"+robot);
			addModule(1,0,4,ORI3XY,"y2"+"-"+robot);
			addModule(-0.5f,0,1.5f,ORI2,"z1"+"-"+robot);
			addModule(-2.5f,0,1.5f,ORI2X,"z2"+"-"+robot);
			//addModule(-2.5f,0,1.5f,ORI2,"z2"+"-"+robot);
			addModule(1.5f,0,0.5f,ORI2Y,"æ1"+"-"+robot);
			addModule(3.5f,0,0.5f,ORI2XY,"æ2"+"-"+robot);
			//addModule(3.5f,0,0.5f,ORI2Y,"æ2"+"-"+robot);
		}
		//Axisymmetric 4-Leg 
		if(robot.equals("WALKER2")) {
			addModule(0,0,0,ORI3,"x1"+"-"+robot);
			addModule(0,0,-2,ORI3,"x2"+"-"+robot);
			addModule(1,0,2,ORI3Y,"y1"+"-"+robot);
			addModule(1,0,4,ORI3Y,"y2"+"-"+robot);
			addModule(-0.5f,0,1.5f,ORI2,"z1"+"-"+robot);
			addModule(-2.5f,0,1.5f,ORI2,"z2"+"-"+robot);
			addModule(1.5f,0,0.5f,ORI2Y,"æ1"+"-"+robot);
			addModule(3.5f,0,0.5f,ORI2Y,"æ2"+"-"+robot);	
		}
		
		if(robot.equals("WALKER3")) {
			addModule(2.5f,0,1.5f,ORI3X,"center"+"-"+robot);
			addModule(-1,0,0,ORI2,"leftBackFoot"+"-"+robot);
			addModule(1,0,0,ORI2,"leftBackSpline"+"-"+robot);
			addModule(3,0,0,ORI2,"leftFrontSpline"+"-"+robot);
			addModule(5,0,0,ORI2,"leftFrontFoot"+"-"+robot);
			addModule(0,0,3,ORI2,"rightBackFoot"+"-"+robot);
			addModule(2,0,3,ORI2,"rightBackSpline"+"-"+robot);
			addModule(4,0,3,ORI2,"rightFrontSpline"+"-"+robot);
			addModule(6,0,3,ORI2,"rightFrontFoot"+"-"+robot);
		}	
		/*if(robot.equals("WALKER4")) {
			addModule(2.5f,0,1.5f,ORI3X,"center"+"-"+robot);
			addModule(-1,0,0,ORI2,"leftBackFoot"+"-"+robot);
			addModule(1,0,0,ORI2,"leftBackSpline"+"-"+robot);
			addModule(3,0,0,ORI2,"leftFrontSpline"+"-"+robot);
			addModule(5,0,0,ORI2,"leftFrontFoot"+"-"+robot);
			addModule(0,0,3,ORI2,"rightBackFoot"+"-"+robot);
			addModule(2,0,3,ORI2,"rightBackSpline"+"-"+robot);
			addModule(4,0,3,ORI2,"rightFrontSpline"+"-"+robot);
			addModule(6,0,3,ORI2,"rightFrontFoot"+"-"+robot);
		}*/
		if(robot.equals("WALKER4")) {
			addModule(2.5f,0,1.5f,ORI3X,"center"+"-"+robot);
			addModule(-1,0,0,ORI2,"leftBackFoot"+"-"+robot);
			addModule(1,0,0,ORI2,"leftBackSpline"+"-"+robot);
			addModule(3,0,0,ORI2,"leftFrontSpline"+"-"+robot);
			addModule(5,0,0,ORI2,"leftFrontFoot"+"-"+robot);
			
			addModule(-1,0,3,ORI2Y,"rightBackFoot"+"-"+robot);
			addModule(1,0,3,ORI2Y,"rightBackSpline"+"-"+robot);
			addModule(3,0,3,ORI2Y,"rightFrontSpline"+"-"+robot);
			addModule(5,0,3,ORI2Y,"rightFrontFoot"+"-"+robot);
		}	
	}
}