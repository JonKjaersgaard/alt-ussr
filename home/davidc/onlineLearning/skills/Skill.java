package onlineLearning.skills;

import onlineLearning.Role;


public interface Skill {
	public Role getRole();
	public Role getBestRole();
	public void update(float reward, float evalTime, Role myRole);
	public void fromFloats(float[] skillData);
	public boolean isContinuous();
}
