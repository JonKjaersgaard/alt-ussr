package onlineLearning.skills;

import onlineLearning.Role;
import onlineLearning.SkillController;

public class SkillSystematic implements Skill {
	int nRoles;
	int myID;
	int iterations = 0;
	int counter  = 0;
	public SkillSystematic(float periodeTime, SkillController controller) {
		myID = controller.getDebugID();
	}
	public Role getRole() {
		Role r = new Role(1);
		if(counter%2==0) {
			/*WALKER 1*/
			if(myID==0) r.setRole((iterations/1)%3, 0); //leg1
			if(myID==1) r.setRole((iterations/3)%3, 0); //leg2
			if(myID==2) r.setRole((iterations/3/3)%3, 0); //leg3
			if(myID==3) r.setRole((iterations/3/3/3)%3, 0); //leg4
			
			if(myID==4) r.setRole(0, 0); //foot
			if(myID==5) r.setRole(0, 0); //foot
			if(myID==6) r.setRole(0, 0); //foot
			if(myID==7) r.setRole(0, 0); //foot
			iterations++;
		} else {
			r.setRole(0, 0);
		}
		counter++;
		return r;
	}
	public void update(float reward, float evalTime, Role myRole) {
		
	}
	public void fromFloats(float[] skillData) {
		throw new RuntimeException("Unimplemented method");
	}
	public Role getBestRole() {
		throw new RuntimeException("Unimplemented method");	
	}
	public boolean isContinuous() {
		return false;
	}
}
