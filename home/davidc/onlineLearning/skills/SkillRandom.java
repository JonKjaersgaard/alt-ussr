package onlineLearning.skills;

import java.util.Random;

import onlineLearning.Role;


public class SkillRandom implements Skill {
	int nRoles;
	int randomRole;
	static Random rand = new Random(); 
	public SkillRandom(int nRoles) {
		this.nRoles = nRoles;
		randomRole = rand.nextInt(nRoles);
	}
	public Role getRole() {
		Role role = new Role(1);
		role.setRole(randomRole, 0);
		return role;
	}
	public Role getBestRole() {
		return getRole();
	}
	public void update(float reward, float evalTime, Role role) {
		randomRole = rand.nextInt(nRoles);
	}
	public void fromFloats(float[] skillData) {
		throw new RuntimeException("Unimplemented method");
	}
	public boolean isContinuous() {
		return false;
	}
	@Override
	public int getBestRoleInt() {
		// TODO Auto-generated method stub
		return 0;
	}

}
