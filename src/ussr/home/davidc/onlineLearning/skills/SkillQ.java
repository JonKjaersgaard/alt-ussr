package ussr.home.davidc.onlineLearning.skills;

import java.util.Random;

public class SkillQ implements Skill {
	float[] Q; 
	int nRoles;
	int currentRole = 0;
	static Random rand = new Random();
	int iterations=0;
	float epsilon = 1f;
	float maxReward = -1000;
	boolean exploit=true;
	boolean boltzmann=false;
	
	public SkillQ(int nRoles) {
		this.nRoles = nRoles;
		Q = new float[nRoles];
		for(int i=0;i<nRoles;i++) {
			Q[i] = 0;
		}
	}
	
	public int getRole() {
		int selectedRole = -1;
		if(!boltzmann) {
			selectedRole = getBestRole();
			if(rand.nextFloat()<epsilon) {
				selectedRole = rand.nextInt(nRoles);
				exploit=false;
			}
			else {
				exploit=true;
			}
		}
		else { // Boltzmann "soft max" distribution
			float T = 10*epsilon+1/20f;
			float sumBoltz =0;
			for(int i=0;i<nRoles;i++) {
				sumBoltz += Math.exp(1/T*Q[i]);
			}
			int loop=0;
			while(selectedRole==-1) {
				int testRole = rand.nextInt(nRoles);
				loop++;
				if(loop>100) System.out.println("Infinity loop detected");
				//System.out.println("prob "+(Math.exp(1/T*Q[testRole])/sumBoltz));
				if((Math.exp(1/T*Q[testRole])/sumBoltz)>rand.nextFloat()) {
					selectedRole = testRole;
				}
			}
		}
		currentRole = selectedRole;
		return currentRole;
	}
	private float maxQ() {
		float maxQ = Float.MIN_VALUE;
		for(int i=0;i<nRoles;i++) {
			if(maxQ<=Q[i]) {
				maxQ = Q[i];
			}
		}
		return maxQ;
	}
	public int getBestRole() {
		int bestRole = 0;
		float maxQ = Float.MIN_VALUE;
		for(int i=0;i<nRoles;i++) {
			if(maxQ<=Q[i]) {
				maxQ = Q[i];
				bestRole = i;
			}
		}
		return bestRole;
	}
	//float x = 0.1f, y = 0.01f;
	//float x = 0.8f, y = 0.0f;
	//float x = 0.01f, y = 0.0f;
	float x = 1f, y = 0.0f;
	public void update(float reward, float evalTime, int role) {
		if(evalTime>0.5f) { 
			float r = reward*evalTime;
			Q[role] += x*(r+y*maxQ()-Q[role]);
			iterations++;
			if(r>maxReward) maxReward = r;
			
			epsilon = 1-iterations/200.0f;
			//epsilon = 1;
			x = 1-iterations/200.0f;
			if(x<0.01f) x=0.01f; 
		}
	}
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		for(int i=0;i<nRoles-1;i++) {
			buffer.append(Q[i]);
			buffer.append("\t");
		}
		buffer.append(Q[nRoles-1]);
		return buffer.toString();
	}
	
	public void fromFloats(float[] skillData) {
		if(Q.length!=skillData.length) throw new RuntimeException("Uncompadible data for skill");
		for(int i=0;i<Q.length;i++) {
			Q[i] = skillData[i]; 
		}
		iterations = 200; //hack
		epsilon = 0;
	}
	
	public synchronized void printQ() {
		System.out.print("Q = [");
		for(int i=0;i<nRoles-1;i++) {
			System.out.print(Q[i]+", ");
		}
		System.out.println(Q[nRoles-1]+"]");
	}
}
