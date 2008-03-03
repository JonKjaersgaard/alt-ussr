/**
 * Uniform Simulator for Self-reconfigurable (modular) Robots
 * 
 * (C) 2006 University of Southern Denmark
 */
package onlineLearning.odin;

import onlineLearning.SkillController;
import onlineLearning.SkillLearner;
import onlineLearning.locomoton.MotionController;
import onlineLearning.locomoton.MotionRewardSystem;
import onlineLearning.locomoton.MotionSkills;
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
    	rewardSystem = new MotionRewardSystem(this,OdinSkillSimulation.simulation);
    	rolePlayer = new OdinRolePlayer(this);
    	int nSkills = MotionSkills.getNumberOfSkills();
    	motionController = new MotionController();
    	learner = new SkillLearner(rolePlayer, rewardSystem, nSkills);
    }
    
    public void activate() {
    	controlYield();
    	delay(2000);
    	learner.init(this,evalPeriode);
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

    public boolean isStopped() {
    	return module.getSimulation().isStopped();
    }

	public int getRobotID() {
		throw new RuntimeException();
	}
}
