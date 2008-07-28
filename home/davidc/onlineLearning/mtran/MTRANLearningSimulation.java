/**
 * Unified Simulator for Self-Reconfigurable Robots (USSR)
 * (C) University of Southern Denmark 2008
 * This software is distributed under the BSD open-source license.
 * For licensing see the file LICENCE.txt included in the root of the USSR distribution.
 */
package onlineLearning.mtran;

import com.jme.math.Quaternion;
import com.jme.math.Vector3f;

import onlineLearning.SkillFileManager;
import onlineLearning.SkillLearner;
import onlineLearning.skills.SkillQ;
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
		if(args.length>2) {
			for(int i=2;i<args.length;i++) {
				if(args[i].contains("TrialID=")) {
					trialID = args[i].subSequence(args[i].indexOf('=')+1, args[i].length()).toString();
				}
				if(args[i].contains("RoleSelectionStrategy=")) {
					String strategy = args[i].subSequence(args[i].indexOf('=')+1, args[i].length()).toString();
					SkillQ.setRoleSelectionStrategy(strategy);
				}
			}
		}
		new MTRANLearningSimulation().runSimulation(null,false);
	}
	public Controller getController(String type) {
		return new MTRANLearningController();
	}

	@Override
	public void changeWorldHook(WorldDescription world) {
	    world.setPlaneTexture(WorldDescription.GRID_TEXTURE);
	    world.setHasBackgroundScenery(false);
	    world.setPlaneSize(500);
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
	public Vector3f getModulePos(int index) {
    	Vector3f pos = (Vector3f) simulation.getHelper().getModulePos(simulation.getModules().get(index)).get();
        return pos;
    }
	public float[] getModuleOri(int index) {
    	return ((Quaternion)simulation.getHelper().getModuleOri(simulation.getModules().get(index)).get()).toAngles(null);
    }
	int counter=0;
	int fallenCounter=0;
	public void physicsTimeStepHook(PhysicsSimulation simulation) {
		//simulation.setGravity(0.0f);
		if(robot.equals("WALKER")) {
			counter++;
			if(counter%100==0) {
				System.out.println("{"+simulation.getTime()+","+getModulePos(6).y+"},");
				if(getModulePos(6).y<-0.45) {
					System.out.println("Fallen "+fallenCounter);
					fallenCounter++;
					if(fallenCounter>25) System.exit(0);
					System.out.println();
				}
			}
		}
		if(robot.equals("MINI")) {
			counter++;
			if(counter%100==0) {
				System.out.println("{"+simulation.getTime()+","+getModuleOri(0)[0]+","+getModuleOri(0)[1]+","+getModuleOri(0)[2]+"},");
				if(getModuleOri(0)[0]>0) 
				{ 
					System.out.println("Fallen "+fallenCounter);
					fallenCounter++;
					if(fallenCounter>25) System.exit(0);
					System.out.println();
				}
			}
		}
		/*if(simulation.getTime()<20) {
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
		if(simulation.getTime()>2*(3600+100)) {
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