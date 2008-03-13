package onlineLearning;

import onlineLearning.skills.Skill;
import onlineLearning.skills.SkillPSO;
import onlineLearning.skills.SkillQ;
import onlineLearning.skills.SkillRandom;
import onlineLearning.skills.SkillRoleTable;
import onlineLearning.skills.SkillTimeTable;
import onlineLearning.utils.DataLogger;

public class SkillLearner {
	
	static enum LearningStrategy {PSO, Q, RANDOM, TIMETABLE,ROLETABLE};
    LearningStrategy learningStrategy = LearningStrategy.Q;
    
    static int nParticles = 5;
    static boolean parallelLearning = false;
    static float[][] skillData = null;

    Skill[] skills;
    int nSkills; 
    int nRoles;
    RewardSystem rewardSystem;
    RolePlayer rolePlayer;

    public SkillLearner(RolePlayer rolePlayer, RewardSystem rewardSystem, int nSkills) 
	{
		this.rolePlayer = rolePlayer;
		this.rewardSystem = rewardSystem;
		this.nRoles = rolePlayer.getNumberOfRoles();
		this.nSkills = nSkills;
	}

	public static void setSkillsData(float[][] sd) {
		skillData = sd;
	}
	public static boolean loadSkillsQ() {
		return skillData!=null;
	}
	public static float[] getSkillData(int id, int skill) {
		float[] sData = new float[skillData[0].length-3];
		if(skillData!=null) {
			for(int i=skillData.length-1;i>=0;i--) { //backward to take better last
				if(skillData[i][1]==id && skillData[i][2]==skill) {
					for(int j=0;j<sData.length;j++) {
						sData[j] = skillData[i][j+3];
					}
					return sData;
				}
			}
		}
		System.out.println("Unable to find data for skill "+skill+" at module "+id);
		return null;
	}
    private void initSkills(int nSkills, int nRoles, float evalPeriode, SkillController controller) {
    	skills = new Skill[nSkills];
		 for(int i=0;i<nSkills;i++) {
			 if(learningStrategy==LearningStrategy.PSO) skills[i] = new SkillPSO(nParticles,nRoles);
			 if(learningStrategy==LearningStrategy.Q) skills[i] = new SkillQ(nRoles);
			 if(learningStrategy==LearningStrategy.TIMETABLE) skills[i] = new SkillTimeTable(nRoles,evalPeriode,8,controller); 
			 if(learningStrategy==LearningStrategy.ROLETABLE) skills[i] = new SkillRoleTable(nRoles,controller);
			 if(learningStrategy==LearningStrategy.RANDOM) skills[i] = new SkillRandom(nRoles);
			 if(loadSkillsQ()) {
				 skills[i].fromFloats(getSkillData(controller.getDebugID(),i));
			}
		 }
	}

    private float updateSkills(int evalSkill, Role myRole, float evalTime) {
		float[] rewards = rewardSystem.collectAllRewards();
		float reward = rewards[evalSkill]; 
		if(!parallelLearning) {
    		skills[evalSkill].update(reward, evalTime, myRole);
		}
		else {
			for(int i=0;i<nSkills;i++) {
				skills[i].update(rewards[i], evalTime, myRole);
			}
		}
		return reward;
    }
    public void init(SkillController controller, float evalPeriode) {
    	rewardSystem.reset();
    	initSkills(nSkills, nRoles, evalPeriode, controller);
    }
	public void startLearning(SkillController controller, float evalPeriode) {
    	while(true) {
    		float startTime = controller.getTime();
    		int currentSkill = controller.getRobotSkill();
    		Role myRole = skills[currentSkill].getRole();
    		//System.out.println(myID+": Playing role = "+rolePlayer.roleToString(myRole));
    		while((startTime+evalPeriode)>controller.getTime() && currentSkill==controller.getRobotSkill()) {
    			if(skills[currentSkill].isContinuous()) myRole = skills[currentSkill].getRole();
    			controller.controlHook();
    			rolePlayer.playRole(myRole);
    			controller.yield();
    			if(controller.isStopped()) return;
    		}
    		//System.out.println(controller.getDebugID()+" is alive at "+controller.getTime()+"!");
    		float reward = updateSkills(currentSkill, myRole, controller.getTime()-startTime);
    		logReward(reward, currentSkill, controller);
    		logSkills(controller);
    		logRole(reward, currentSkill, myRole, controller);
    	}
	}
	private void logReward(float reward, int currentSkill, SkillController controller) {
		if(controller.getDebugID()==0) {
			String str = controller.getTime()+"\t"+ formatFloat(reward)+"\t"+controller.getRobotSkill();
			DataLogger.logln(str, "rewards.txt", true);
		}
	}
	private void logSkills(SkillController controller) {
		StringBuffer buffer = new StringBuffer();
		for(int i=0;i<nSkills;i++) {
			buffer.append(controller.getTime()+"\t"+controller.getDebugID()+"\t"+i+"\t"+skills[i].toString()+System.getProperty("line.separator"));
		}
		DataLogger.logln(buffer.toString(), "Q-Values.txt", false);
		
	}
	private void logRole(float reward, int currentSkill, Role myRole, SkillController controller) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(controller.getTime()); buffer.append("\t");
		buffer.append(controller.getRobotID());buffer.append("\t");
		buffer.append(reward);buffer.append("\t");
		buffer.append(controller.getDebugID());buffer.append("\t");
		buffer.append(currentSkill);buffer.append("\t");
		buffer.append(myRole.getRole(0));
		DataLogger.logln(buffer.toString(), "Role-Rewards.txt", false);
	}
	private String formatFloat(float x) {
		String s = Float.toString(x);
		if(s.contains("E")) return "0.0";
		else return s;
	}
}
