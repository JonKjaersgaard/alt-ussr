/**
 * Uniform Simulator for Self-reconfigurable (modular) Robots
 * 
 * (C) 2006 University of Southern Denmark
 */
package ussr.home.davidc.onlineLearning.odin;

import ussr.home.davidc.onlineLearning.SkillController;
import ussr.home.davidc.onlineLearning.SkillLearner;
import ussr.home.davidc.onlineLearning.locomoton.MotionController;
import ussr.home.davidc.onlineLearning.locomoton.MotionRewardSystem;
import ussr.home.davidc.onlineLearning.locomoton.MotionSkills;
import ussr.samples.odin.OdinController;

/**
 * Role based online learning of robot skill for the ODIN robot
 * 
 * @author david
 *
 */
public class OdinSkillController extends OdinController implements SkillController {
	static float evalPeriode = 1.0f; //second(s)
	
	SkillLearner learner;
    OdinRolePlayer rolePlayer;
    MotionRewardSystem rewardSystem;
    MotionController motionController;
    
    public OdinSkillController(String type) {
    	this.type =type;
    	rewardSystem = new MotionRewardSystem(OdinSkillSimulation.simulation);
    	rolePlayer = new OdinRolePlayer(this);
    	int nSkills = MotionSkills.getNumberOfSkills();
    	motionController = new MotionController();
    	learner = new SkillLearner(rolePlayer, rewardSystem, nSkills);
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
}
