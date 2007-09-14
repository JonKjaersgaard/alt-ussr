package ussr.home.davidc.onlineLearning.skills;


public interface Skill {
	public int getRole();
	public int getBestRole();
	public void update(float reward, float evalTime, int myRole);
	public void fromFloats(float[] skillData);
}
