package onlineLearning.locomoton;

import onlineLearning.SkillController;

public class MotionController {
	static int robotSkill = 1; //remove 'static' at some point
	boolean learnOneSkill = false;
	public MotionController() { }
	public MotionController(MotionSkills motionSkill) {
		learnOneSkill = true;
		robotSkill = motionSkill.ordinal();
	}
	public void control(SkillController controller) {
		if(!learnOneSkill) {
			if(controller.getDebugID()==0) {
				if(controller.getTime()<200) startRole(MotionSkills.FORWARD);
				else if(controller.getTime()<400) startRole(MotionSkills.TURN_LEFT);
				else if(controller.getTime()<600) startRole(MotionSkills.TURN_RIGHT);
				else {
					if(((int)(controller.getTime()/10.0f))%3==0) startRole(MotionSkills.FORWARD);
					if(((int)(controller.getTime()/10.0f))%3==1) startRole(MotionSkills.TURN_LEFT);
					if(((int)(controller.getTime()/10.0f))%3==2) startRole(MotionSkills.TURN_RIGHT);
				}
			}
		}
		if(controller.getTime()>90*2*5*6) System.exit(0);
	}
	//TODO Distribute skill to other modules
	private void startRole(MotionSkills skill) {
		robotSkill = skill.ordinal();
	}
	public int getRobotSkill() {
		return robotSkill;
	}

}
