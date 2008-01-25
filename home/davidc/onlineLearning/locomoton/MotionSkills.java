package onlineLearning.locomoton;

public enum MotionSkills {
	STOP, ESCAPE, FORWARD, BACKWARD, TURN_RIGHT, TURN_LEFT;
	public static String skillToString(int robotSkill) {
		return MotionSkills.values()[robotSkill].toString();
	}
	
	public static int getNumberOfSkills() {
		return MotionSkills.values().length;
	}
	 
}
