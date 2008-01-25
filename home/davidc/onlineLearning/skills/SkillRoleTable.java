package onlineLearning.skills;

import java.util.ArrayList;

import onlineLearning.Role;
import onlineLearning.SkillController;

public class SkillRoleTable implements Skill {

	ArrayList<SkillQ> skills;
	SkillController controller;
	public SkillRoleTable(float periodeTime, SkillController controller) {
		//atron specific implementation
		this.controller= controller;
		skills = new ArrayList<SkillQ>();
		skills.add(new SkillQ(3)); //center
		skills.add(new SkillQ(4)); //homePos
		skills.add(new SkillQ(2)); //connector 0
		skills.add(new SkillQ(2)); //connector 2
		skills.add(new SkillQ(2)); //connector 4
		skills.add(new SkillQ(2)); //connector 6
	}
	public void fromFloats(float[] skillData) {
		throw new RuntimeException("Not supported for role divided skill learning");
	}

	public Role getBestRole() {
		return getRole();
	}

	public void update(float reward, float evalTime, Role myRole) {
		for(int i=0;i<skills.size();i++) {
			Role role = new Role(1);
			role.setRole(myRole.getRole(i), 0);
			skills.get(i).update(reward, evalTime, role);
		}
	}
	
	public Role getRole() {
		Role role = new Role(6);
		role.setRole(skills.get(0).getRole().getRole(0), 0); //center
		role.setRole(skills.get(1).getRole().getRole(0), 1); //homePos
		role.setRole(skills.get(2).getRole().getRole(0), 2); //connector 0
		role.setRole(skills.get(3).getRole().getRole(0), 3); //connector 2
		role.setRole(skills.get(4).getRole().getRole(0), 4); //connector 4
		role.setRole(skills.get(5).getRole().getRole(0), 5); //connector 6
		return role;
	}
	
	public boolean isContinuous() {
		return false;
	}

}
