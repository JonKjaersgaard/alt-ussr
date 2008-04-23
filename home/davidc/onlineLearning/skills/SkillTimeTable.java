package onlineLearning.skills;

import java.util.ArrayList;
import java.util.Random;

import onlineLearning.Role;
import onlineLearning.SkillController;

public class SkillTimeTable implements Skill {

	ArrayList<SkillQ> skills;
	Role[] roles;
	float periodeTime;
	float lastUpdateTime = 0;
	boolean updated = false;
	SkillController controller;
	boolean exploit = true;
	boolean moduleRandom=false;
	int lastSkillIndex=-1;
	static Random rand = new Random(System.currentTimeMillis());
	public SkillTimeTable(int nRoles, float periodeTime, int subperiods, SkillController controller) {
		
		this.periodeTime = periodeTime;
		this.controller= controller;
		roles = new Role[subperiods];
		skills = new ArrayList<SkillQ>();
		for(int i =0;i<subperiods;i++) {
			skills.add(new SkillQ(nRoles));
		}
		if(!moduleRandom&&controller.getDebugID()==0) {
			SkillQ.epsilon = 1-(1-0.8f)/subperiods;
			System.out.println("epsilon set to "+SkillQ.epsilon);
		}
	}
	

	public Role getBestRole() {
		return new Role(1);
	}

	public void update(float reward, float evalTime, Role myRole) {
		lastUpdateTime = controller.getTime();
		for(int i=0;i<roles.length;i++) {
			//System.out.println(skills.size()+" "+roles.length);
			if(roles[i]!=null) skills.get(i).update(reward, evalTime, roles[i]);
		}
		if(moduleRandom) exploit = 0.8f>rand.nextFloat();
		updated = true;
	}
	public Role getRole() {
		float timeOfPeriode = (controller.getTime()-lastUpdateTime)/periodeTime;
		float timeOfSubPeriode = 1.0f/skills.size();
		int newSkillIndex = (int)(timeOfPeriode/timeOfSubPeriode);
		if(newSkillIndex>=skills.size()) newSkillIndex=skills.size()-1;
		
		if(lastSkillIndex!=newSkillIndex||updated) {
			Role newRole;
			if(moduleRandom) newRole = skills.get(newSkillIndex).getRole(exploit);
			else newRole= skills.get(newSkillIndex).getRole();
			
			roles[newSkillIndex] = newRole;
			lastSkillIndex = newSkillIndex;
			//if(controller.getDebugID()==0) System.out.println("New Role...");
		//	if(controller.getDebugID()==0) System.out.println(controller.getDebugID()+": Playing role = "+newRole.getRole(0)+" in "+newSkillIndex);
			updated = false;
			return newRole;
		}
		else {
			return roles[lastSkillIndex];
		}
	}
	public boolean isContinuous() {
		return true;
	}
	public int getBestRoleInt() {
		return 0;
	}
	public void fromFloats(float[] skillData) {
		int splitSize = skillData.length/skills.size();
		float[] temp = new float[splitSize];
		int offset=0;
		for(int i=0;i<skills.size();i++) {
			for(int j=0;j<splitSize;j++) { 
				temp[j]= skillData[j+offset];
			}
			skills.get(i).fromFloats(temp);
			offset+=splitSize;			
		}
	}
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		for(Skill s: skills) {
			buffer.append(s.toString());
			buffer.append("\t");
		}
		return buffer.toString();
	}
}
