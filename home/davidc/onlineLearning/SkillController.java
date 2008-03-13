package onlineLearning;

public interface SkillController {
	public void yield();
	public boolean isStopped();

	public void delay(int i);

	public float getTime();

	public int getRobotSkill();

	public void controlHook();

	public int getDebugID();
	
	public int getRobotID();
	
}
