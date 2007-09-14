/**
 * Uniform Simulator for Self-reconfigurable (modular) Robots
 * 
 * (C) 2006 University of Southern Denmark
 */
package ussr.home.davidc.onlineLearning.atron;

import ussr.home.davidc.onlineLearning.SkillController;
import ussr.home.davidc.onlineLearning.SkillLearner;
import ussr.home.davidc.onlineLearning.locomoton.MotionController;
import ussr.home.davidc.onlineLearning.locomoton.MotionRewardSystem;
import ussr.home.davidc.onlineLearning.locomoton.MotionSkills;
import ussr.samples.atron.ATRONController;

/**
 * Role based online learning of robot skill for the ODIN robot
 * 
 * @author david
 *
 */
public class AtronSkillController extends ATRONController implements SkillController {
	//static float evalPeriode = 3f; //second(s)
	static float evalPeriode = 6f; //second(s)
	
	SkillLearner learner;
    AtronRolePlayer rolePlayer;
    MotionRewardSystem rewardSystem;
    MotionController motionController;
    
    public AtronSkillController() {
    	rewardSystem = new MotionRewardSystem(AtronSkillSimulation.getPhysicsSimulation());
    	rolePlayer = new AtronRolePlayer(this);
    	int nSkills = MotionSkills.getNumberOfSkills();
    	//motionController = new MotionController(); //for all learning
    	motionController = new MotionController(MotionSkills.FORWARD);
    	learner = new SkillLearner(rolePlayer, rewardSystem, nSkills);
    	setBlocking(false);
    }
    
    public void activate() {
    	ussrYield();
    	delay(2000);
    	learner.init(this);
    	learner.startLearning(this ,evalPeriode);
	}
	public void controlHook() {
		motionController.control(this);
	}
	
	public int getRobotSkill() {
		return motionController.getRobotSkill();
	}
	
	//TODO CPG implementation of this method
    public float getTime() {
		return module.getSimulation().getTime();
	}
   
    public void handleMessage(byte[] message, int messageSize, int channel) {

    }

    public int getDebugID() {
    	return getModule().getID();
    }
    public void ussrYield() {
    	while(module.getSimulation().isPaused()) Thread.yield();
    	module.getSimulation().waitForPhysicsStep(false);	
	}
    public void delay(int ms) {
    	float startTime = module.getSimulation().getTime();
    	while(module.getSimulation().getTime()<(ms/1000.0+startTime)) {
			module.getSimulation().waitForPhysicsStep(false);
    	}
    }

}
