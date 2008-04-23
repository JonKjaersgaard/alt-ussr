package onlineLearning.skills;

import java.util.ArrayList;

import onlineLearning.Role;
import onlineLearning.SkillController;

public abstract class SkillRoleTable implements Skill {

	protected ArrayList<Skill> skills = new ArrayList<Skill>();
	protected ArrayList<DelayedRewardCollector> collectors;
	protected SkillController controller;
	public SkillRoleTable(float periodeTime, SkillController controller) {
		//atron specific implementation
		this.controller= controller;
	}
	
	public int getBestRoleInt() {
		return 0;
	}
	public Role getBestRole() {
		Role role = new Role(skills.size());
		for(int i=0;i<skills.size();i++) {
			role.setRole(skills.get(i).getBestRoleInt(), i);
		}
		return role;
	}
	
	public class DelayedRewardCollector {
		int nIterations=1;
		int ittCount=0;
		int role=0;
		float rewardSum = 0;
		float evalTimeSum = 0;
		public DelayedRewardCollector(int nIterations) {
			this.nIterations = nIterations;
			reset();
		}
		public void update(float reward, float evalTime, int role) {
			rewardSum += reward;
			evalTimeSum +=evalTime;
			this.role = role;
			ittCount++;
		}
		public float getRewardSum() {
			return rewardSum;
		}
		public float getEvalTimeSum() {
			return evalTimeSum;
		}
		public void reset() {
			rewardSum = 0;
			evalTimeSum=0;
			ittCount=0;
		}
		public int getRole() {
			return role;
		}
		public boolean timeToUpdateReward() {
			return ittCount==nIterations;
		}
		public boolean timeToUpdateRole() {
			return ittCount==0;
		}
	}
	
	public void update(float reward, float evalTime, Role myRole) {
		for(int i=0;i<skills.size();i++) {
			int role = myRole.getRole(i);
			collectors.get(i).update(reward, evalTime,role);
			if(collectors.get(i).timeToUpdateReward()) {
				Role r = new Role(1); 
				r.setRole(role, 0);
				skills.get(i).update(collectors.get(i).getRewardSum(), collectors.get(i).getEvalTimeSum(),r);
				collectors.get(i).reset();
			}
		}
	}
	public Role getRole() {
		Role role = new Role(skills.size());
		for(int i=0;i<skills.size();i++) {
			if(collectors.get(i).timeToUpdateRole()||skills.get(i).isContinuous()) {
			//	if(controller.getDebugID()==0&&i==1) System.out.println("Change !!!");
				role.setRole(skills.get(i).getRole().getRole(0), i);
			}
			else {
				role.setRole(collectors.get(i).getRole(), i);
			}
		}
		return role;
	}
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		for(Skill s: skills) {
			buffer.append(s.toString());
			buffer.append("\t");
		}
		return buffer.toString();
	}
	public void fromFloats(float[] skillData) {
		int splitSize = skillData.length/skills.size();
		float[] temp = new float[splitSize];
		int offset=0;
		for(int i=0;i<skills.size();i++) {
			for(int j=0;j<splitSize;j++) { 
				temp[j]= skillData[j+offset];
				//System.out.println(controller.getDebugID()+": j+offset"+(j+offset));
			}
			skills.get(i).fromFloats(temp);
			offset+=splitSize;			
		}
	}
	public boolean isContinuous() {
		return false;
	}

}
