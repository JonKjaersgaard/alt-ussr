package ussr.home.davidc.onlineLearning.skills;

import java.util.Random;


public class SkillRandom implements Skill {
	int nRoles;
	int randomRole;
	static Random rand = new Random(); 
	public SkillRandom(int nRoles) {
		this.nRoles = nRoles;
		randomRole = rand.nextInt(nRoles);
	}
	public int getRole() {
		return randomRole;
	}
	public int getBestRole() {
		return getRole();
	}
	public void update(float reward, float evalTime, int role) {
		randomRole = rand.nextInt(nRoles);
	}
	public void fromFloats(float[] skillData) {
		throw new RuntimeException("Unimplemented method");
	}
}
