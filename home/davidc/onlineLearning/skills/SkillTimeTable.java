package onlineLearning.skills;

import java.util.ArrayList;

import onlineLearning.Role;
import onlineLearning.SkillController;

public class SkillTimeTable implements Skill {

	ArrayList<SkillQ> skills;
	Role[] roles;
	float periodeTime;
	float lastUpdateTime = 0;
	boolean updated = false;
	SkillController controller;
	int lastSkillIndex=-1;
	public SkillTimeTable(int nRoles, float periodeTime, int subperiods, SkillController controller) {
		
		this.periodeTime = periodeTime;
		this.controller= controller;
		roles = new Role[subperiods];
		skills = new ArrayList<SkillQ>();
		for(int i =0;i<subperiods;i++) {
			skills.add(new SkillQ(nRoles));
		}
	}
	public void fromFloats(float[] skillData) {
		throw new RuntimeException("Not supported for time divided skill learning");
	}

	public Role getBestRole() {
		return getRole();
	}

	public void update(float reward, float evalTime, Role myRole) {
		lastUpdateTime = controller.getTime();
		for(int i=0;i<=lastSkillIndex;i++) {
			skills.get(i).update(reward, evalTime, roles[lastSkillIndex]);
		}
		updated = true;
	}
	public Role getRole() {
		float timeOfPeriode = (controller.getTime()-lastUpdateTime)/periodeTime;
		float timeOfSubPeriode = 1.0f/skills.size();
		int newSkillIndex = (int)(timeOfPeriode/timeOfSubPeriode);
		if(newSkillIndex>=skills.size()) newSkillIndex=skills.size()-1;
		if(lastSkillIndex!=newSkillIndex||updated) {
			Role newRole = skills.get(newSkillIndex).getRole();
			roles[newSkillIndex] = newRole;
			lastSkillIndex = newSkillIndex;
			updated = false;
			return newRole;
		}
		else {
			return roles[lastSkillIndex];
		}
	}
	public boolean isContinuous() {
		return true;
	}

}
