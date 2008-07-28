/**
 * Uniform Simulator for Self-reconfigurable (modular) Robots
 * 
 * (C) 2006 University of Southern Denmark
 */
package onlineLearning.mtran;

import java.awt.Color;
import java.util.List;
import java.util.Random;

import onlineLearning.Role;
import onlineLearning.RolePlayer;
import onlineLearning.SkillController;
import onlineLearning.SkillLearner;
import onlineLearning.locomoton.MotionController;
import onlineLearning.locomoton.MotionRewardSystem;
import onlineLearning.locomoton.MotionSkills;
import ussr.samples.atron.ATRONController;
import ussr.samples.mtran.MTRANController;

/**
 * Role based online learning of robot skill for the ATRON robot
 * 
 * @author david
 *
 */
public class MTRANLearningController extends MTRANController implements SkillController {
	
	static float evalPeriode = 1.5f;
	SkillLearner learner;
    RolePlayer rolePlayer;
    MotionRewardSystem rewardSystem; 
    
    static Random rand = new Random();
    
    public MTRANLearningController() {
    	rewardSystem = new MotionRewardSystem(this, MTRANLearningSimulation.getPhysicsSimulation());
    	rolePlayer = new MTRANRolePlayer(this);
    	int nSkills = MotionSkills.getNumberOfSkills();
    	learner = new SkillLearner(rolePlayer, rewardSystem, nSkills);
    	setBlocking(false);
    }
    public void activate() {
    	yield();
    	init();
    	learner.init(this,evalPeriode); 
    	learner.startLearning(this ,evalPeriode);
    	System.out.println("Module "+getDebugID()+" Finished");
	}
    public void init() {
    	float startTime = module.getSimulation().getTime();
    	while(module.getSimulation().getTime()<(10+startTime)) {
    		rolePlayer.playRole(new Role(2),0);
			module.getSimulation().waitForPhysicsStep(module);
    	}
    	System.out.println(getDebugID()+": Module staring "+getTime());
    }
    public float getTime() {
		return module.getSimulation().getTime();
	}
    public int getDebugID() {
    	return getModule().getID();
    }
    public int getRobotID() {
    	return 0;
    }
    
    public void yield() {
    	while(module.getSimulation().isPaused()) module.getSimulation().waitForPhysicsStep(module);
    	if(!isStopped()) module.getSimulation().waitForPhysicsStep(module);
	}
    public boolean isStopped() {
    	return module.getSimulation().isStopped();
    }
    public void delay(int ms) {
    	float startTime = module.getSimulation().getTime();
    	while(module.getSimulation().getTime()<(ms/1000.0+startTime)) {
			module.getSimulation().waitForPhysicsStep(module);
    	}
    }
	public void controlHook() { }
	public int getRobotSkill() {
		return 1;
	}

}
