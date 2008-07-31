package onlineLearning;

import java.awt.Color;

import onlineLearning.atron.ATRONSkillRoleTable;
import onlineLearning.atron.AtronSkillController;
import onlineLearning.atron.AtronSkillSimulation;
import onlineLearning.mtran.MTRANSkillRoleTable;
import onlineLearning.skills.Skill;
import onlineLearning.skills.SkillPSO;
import onlineLearning.skills.SkillQ;
import onlineLearning.skills.SkillRandom;
import onlineLearning.skills.SkillSystematic;
import onlineLearning.skills.SkillTimeTable;
import onlineLearning.utils.DataLogger;

public class SkillLearner {
	
	public static enum LearningStrategy {PSO, Q, RANDOM, TIMETABLE, ROLETABLE, SYSTEMATIC,ROLETABLE_MTRAN};
    public static LearningStrategy learningStrategy = LearningStrategy.TIMETABLE;
    
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
						if(id==0&&skill==1) System.out.print(sData[j]+" ");
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
			 if(learningStrategy==LearningStrategy.TIMETABLE) skills[i] = new SkillTimeTable(5,evalPeriode,5,controller); //was 5 
			 if(learningStrategy==LearningStrategy.ROLETABLE) skills[i] = new ATRONSkillRoleTable(evalPeriode,controller);
			 if(learningStrategy==LearningStrategy.ROLETABLE_MTRAN) skills[i] = new MTRANSkillRoleTable(evalPeriode,controller);
			 if(learningStrategy==LearningStrategy.RANDOM) skills[i] = new SkillRandom(nRoles);
			 if(learningStrategy==LearningStrategy.SYSTEMATIC) skills[i] = new SkillSystematic(evalPeriode,controller);
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
		float startTime = controller.getTime();
		System.out.println(controller.getDebugID()+": Start learning at "+startTime );
		float nowTime=controller.getTime();
		while(true) {
    		int currentSkill = controller.getRobotSkill();
    		Role myRole = skills[currentSkill].getRole();
    		Role bestRole = skills[currentSkill].getBestRole();
    		while((startTime+evalPeriode)>controller.getTime() && currentSkill==controller.getRobotSkill()) {
    			if(skills[currentSkill].isContinuous()) {
    				myRole = skills[currentSkill].getRole();
    				bestRole = skills[currentSkill].getBestRole();
    			}
    			controller.controlHook();
    			float timePercent = (controller.getTime()-startTime)/(evalPeriode);
    			rolePlayer.playRole(myRole,timePercent);
    			controller.yield();
    			float endTime = controller.getTime();
    			if((endTime-nowTime)>0.1f) System.out.println("Starvation detected..."+(endTime-nowTime));
    			nowTime =controller.getTime();
    	    	
    	    	
    	    	
    			if(controller.isStopped()) return;
    			if(AtronSkillSimulation.SRFAULT) {
	    			float FaultTime = 7*500+2;
	        		if(controller.getTime()>FaultTime&&controller.getDebugID()==2&&controller.getTime()<(FaultTime+7*250)) {
	       				System.out.println("Module breaks down");
	        			while(controller.getTime()<(FaultTime+7*250)) {
	        				((AtronSkillController) controller).centerStop();
	        				((AtronSkillController) controller).getModule().setColor(Color.BLACK);
	        				controller.yield();
	        			}
	        			System.out.println("Module restarts");
	        			((AtronSkillController) controller).getModule().setColor(Color.WHITE);
	        		}
    			}
    		}
    		//System.out.println(controller.getDebugID()+" is alive at "+controller.getTime()+"!");
    		float reward = updateSkills(currentSkill, myRole, controller.getTime()-startTime);
    		logReward(reward, currentSkill, controller);
    		logSkills(controller);
    		logRole(reward, currentSkill, bestRole, controller);
    		logBestRole(reward, currentSkill, bestRole, controller);
    		startTime = startTime+evalPeriode;
    		if(AtronSkillSimulation.SRFAULT) {
	    		float SRStartTime = 7*250;
	    		if(controller.getTime()>SRStartTime&&controller.getTime()<SRStartTime+20) {
	    			System.out.println("Starting to self-reconfigure");
	    			selfReconfigure(controller);
	    			while(controller.getTime()<SRStartTime+20) controller.yield();
	    			System.out.println("Self-reconfiguration done - continue learning");
	    		}
    		}
    	}
	}
	private void selfReconfigure(SkillController controller) {
		float startTime = controller.getTime();
		AtronSkillController con = (AtronSkillController) controller;
		con.setBlocking(true);
		con.home();		
		if(con.getDebugID()==4) {
			con.rotate(1);
			con.rotate(1);
		}
		if(con.getDebugID()==5) {
			con.rotate(-1);
			con.rotate(-1);
		}
		if(con.getDebugID()==1||con.getDebugID()==2||con.getDebugID()==6||con.getDebugID()==7) {
			con.rotate(1);
		}
			
		while(controller.getTime()<startTime+10.0f) con.yield();
		con.connectAll();
		while(controller.getTime()<startTime+13.0f) con.yield();
		if(con.getDebugID()==4||con.getDebugID()==5) {
			for(int i=0;i<4;i++) {
				if(con.canDisconnect(i)) {
					con.disconnect(i);
				}
			}
		}
		con.setBlocking(false);
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
	private void logBestRole(float reward, int currentSkill, Role myRole, SkillController controller) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(controller.getTime()); buffer.append("\t");
		buffer.append(controller.getRobotID());buffer.append("\t");
		buffer.append(reward);buffer.append("\t");
		buffer.append(controller.getDebugID());buffer.append("\t");
		buffer.append(currentSkill);buffer.append("\t");
		buffer.append(myRole.getRole(0));
		DataLogger.logln(buffer.toString(), "BestRole-Rewards.txt", false);
	}
	private String formatFloat(float x) {
		String s = Float.toString(x);
		if(s.contains("E")) return "0.0";
		else return s;
	}

	public static void setLearningStrategy(String strategy) {
		learningStrategy = LearningStrategy.valueOf(strategy);
		System.out.println("LearningStrategy set to "+learningStrategy);
	}
}
