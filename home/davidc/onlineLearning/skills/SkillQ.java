package onlineLearning.skills;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import onlineLearning.Role;

public class SkillQ implements Skill {
	
	enum RoleSelectionStragegy  {HARDMAX, EPSILON_GREEDY, EPSILON_FIRST, EPSILON_DECREASING, SOFTMAX, RANDOM};
	static RoleSelectionStragegy selectionStrategy =  RoleSelectionStragegy.EPSILON_GREEDY;
	
	enum RewardEstimationStragegy  {LAST, MAX, EMA, MEDIAN};
	static RewardEstimationStragegy estimationStrategy =  RewardEstimationStragegy.EMA;
	
	/*Learning parameters*/
	static float coolingTime = 250;
	static float epsilon = 0.80f;
	static float T = 0.01f;//epsilon;//10*epsilon+1/20f;
	
	static boolean accelerated = true;
	boolean learning = true;
	float[] Q;
	ArrayList<ArrayList<Float>> roleRewards;
	int nRoles;
	
	int currentRole = 0;
	float currentReward =0; 
	static Random rand = new Random(System.currentTimeMillis());
	public static float alpha = 0.1f;
	
	int iterations = 0;
	
		
	public SkillQ(int nRoles) {
		this.nRoles = nRoles;
		roleRewards = new ArrayList<ArrayList<Float>>();
		Q = new float[nRoles];
		for(int i=0;i<nRoles;i++) {
			//Q[i] = 0.2f;
			Q[i] = 99;
			//Q[i] = 0;
			roleRewards.add(new ArrayList<Float>());
		}
		//Q[0] =0.2f/6f;
	}
	private int hardMax() {
		int selectedRole = -1;
		for(int i=0;i<nRoles;i++) {
			return getBestRoleInt();
		}
		return selectedRole;
	}	
	private int epsilonGreedy() {
		if(epsilon>rand.nextFloat()) {
			return getBestRoleInt();
		}
		return rand.nextInt(nRoles);
	}
	private int epsilonFirst() {
		if(iterations<coolingTime) {
			return rand.nextInt(nRoles);
		}
		else {
			return getBestRoleInt();
		}
	}
	private int epsilonDecreasing() {
		epsilon = iterations/coolingTime;
		if(epsilon>1.0f) epsilon=1.0f;
		return epsilonGreedy();
	}
	
	private int softMax() {
		int selectedRole = -1;
		float Qsum = 0;
		float[] QNorm = new float[Q.length];
		for(int i=0;i<Q.length;i++) Qsum+=Math.abs(Q[i]);
		for(int i=0;i<Q.length;i++) QNorm[i] = Q[i]/Qsum;
		float sumBoltz =0;
		for(int i=0;i<nRoles;i++) {
			sumBoltz += Math.exp(1f/T*QNorm[i]);
			//System.out.println("Math.exp(1f/T*QNorm[i])="+Math.exp(1f/T*QNorm[i]));
		}
		int loop = 0;
		while(selectedRole==-1) {
			int testRole = rand.nextInt(nRoles);
			//System.out.println("prob to select role "+testRole+" "+(Math.exp(1/T*Q[testRole])/sumBoltz+" "+sumBoltz));
			if((Math.exp(1f/T*QNorm[testRole])/sumBoltz)>rand.nextFloat()||Q[testRole]==99) {
				selectedRole = testRole;
			}
			if(loop++>100) {
				System.out.println(" sumBoltz = "+sumBoltz+" T = "+T+" nRoles="+nRoles);
				System.out.println("Infinite loop detected in softmax...");
				System.out.println(" printing Q"); printQ();
				selectedRole = rand.nextInt(nRoles);
			}
		}
		if(selectedRole!=getBestRoleInt()) System.out.println("Module Explores Role"+selectedRole);
		return selectedRole;
	}
	public Role getRole() {
		int selectedRole = -1;
		if(!repeatPreviousRole()) {
			if(selectionStrategy == RoleSelectionStragegy.HARDMAX) 				selectedRole = hardMax();
			if(selectionStrategy == RoleSelectionStragegy.EPSILON_GREEDY) 		selectedRole = epsilonGreedy();
			if(selectionStrategy == RoleSelectionStragegy.EPSILON_FIRST) 		selectedRole = epsilonFirst();
			if(selectionStrategy == RoleSelectionStragegy.EPSILON_DECREASING) 	selectedRole = epsilonDecreasing();
			if(selectionStrategy == RoleSelectionStragegy.SOFTMAX) 				selectedRole = softMax();
			if(selectionStrategy == RoleSelectionStragegy.RANDOM) 				selectedRole = rand.nextInt(nRoles);
			if(!learning) selectedRole = getBestRoleInt();
		}
		else {
			//System.out.println("Repeating role");
			selectedRole = currentRole;
		}
		
		currentRole = selectedRole;
		
		Role role = new Role(1);
		role.setRole(currentRole, 0);
		return role;
	}
	public Role getRole(boolean exploit) {
		int selectedRole = -1;
		if(!repeatPreviousRole()) {
			if(exploit) {
				selectedRole = getBestRoleInt();
			}else {
				selectedRole = rand.nextInt(nRoles);	
			}
		}
		else {
			selectedRole = currentRole;
		}
		
		currentRole = selectedRole;
		
		Role role = new Role(1);
		role.setRole(currentRole, 0);
		return role;
	}
	private boolean repeatPreviousRole() {
		if(accelerated) {
			//boolean redo = maxQ()<currentReward && getBestRole()!=currentRole;
			boolean redo = maxQ()<currentReward;
			if(redo) {
				//System.out.println("Redoing role "+currentRole+" with fitness "+currentReward+" (estimated "+Q[currentRole]+") best role "+getBestRole()+" has fitness "+maxQ());
			}
			return redo; 
		}
		return false;
	}
	private float maxQ() {
		float maxQ = -1000;
		for(int i=0;i<nRoles;i++) {
			if(maxQ<=Q[i]) {
				maxQ = Q[i];
			}
		}
		return maxQ;
	}
	public Role getBestRole() {
		Role role = new Role(1);
		role.setRole(getBestRoleInt(), 0);
		if(maxQ()==99) role.setRole(99, 0);
		return role;
	}
	public int getBestRoleInt() {
		float maxQ = maxQ();
		int bestRole = -1;
		int loop=0;
		while(bestRole==-1) {
			int testRole = rand.nextInt(nRoles);
			if(Q[testRole]==maxQ) { 
				bestRole = testRole;
			}
			if(loop++>100) System.out.println("Infinite loop detected in getBestRole...");
		}
		return bestRole;
	} 
	
	public void update(float reward, float evalTime, Role roles) {
		int role = roles.getRole(0);
		if(!learning) return; 
		if(evalTime>=0.1f) { 
			float r = reward/evalTime;
			roleRewards.get(role).add(reward); //will this overload memory?
			if(estimationStrategy == RewardEstimationStragegy.LAST) lastReward(role, r);
			if(estimationStrategy == RewardEstimationStragegy.MAX) maxReward(role, r);
			if(estimationStrategy == RewardEstimationStragegy.EMA) exponentialMovingAverageReward(role, r);
			if(estimationStrategy == RewardEstimationStragegy.MEDIAN) medianReward(role, r);
			
			currentReward = r;
			iterations++;
		}
	}
	private void maxReward(int role, float r) {
		if(Q[role]<r)  Q[role] = r;		
	}
	private void lastReward(int role, float r) {
		Q[role] = r;		
	}
	private void exponentialMovingAverageReward(int role, float r) {
		if(Q[role]==99) Q[role] = r;
		else Q[role] += alpha*(r-Q[role]);
	}
	private void medianReward(int role, float r) {
		ArrayList<Float> roleHistory = roleRewards.get(role);
		int start = (roleHistory.size()-10)<0?0:roleHistory.size()-10; 	int end = roleHistory.size();
		List<Float> latestHistory = (List<Float>) roleHistory.subList(start, end);
		//TODO
		for(Float f: latestHistory ) {
			Q[role] = f.floatValue();
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
		iterations = (int)coolingTime; //hack
		epsilon = 0;
		learning = false;
	}
	
	public synchronized void printQ() {
		System.out.print("Q = [");
		for(int i=0;i<nRoles-1;i++) {
			System.out.print(Q[i]+", ");
		}
		System.out.println(Q[nRoles-1]+"]");
	}
	public static void setRoleSelectionStrategy(String strategy) {
		String[] str = strategy.split(":", 4);
		selectionStrategy = RoleSelectionStragegy.valueOf(str[0]);
		for(int i=1;i<str.length;i++) {
			String[] param = str[i].split("=", 2);
			
			if(param[0].contains("coolingTime")) {
				coolingTime =  Integer.parseInt(param[1]);
				System.out.println("Cooling Time set to "+coolingTime);
			}
			else if(param[0].contains("epsilon")) { 
				epsilon =  Float.parseFloat(param[1]);
				System.out.println("Epsilon set to "+epsilon);
			}
			else if(param[0].contains("T")) {
				T =  Float.parseFloat(param[1]);
				System.out.println("Temperature set to "+T);
			}
			else {
				System.out.println("parameter not recognized in roles selection strategy: "+str[i]);
			}
		}
		System.out.println("RoleSelectionStragegy set to "+selectionStrategy.name());
	}
	
	public static void setAccelerated(boolean acc) {
		accelerated =acc;
		System.out.println("Learning accelerated "+accelerated);
	}
	public static void setRewardEstimationStragegy(String strategy) {
		estimationStrategy = RewardEstimationStragegy.valueOf(strategy);
		System.out.println("RewardEstimationStragegy set to "+estimationStrategy.name());
	}
	public boolean isContinuous() {
		return false;
	}

}
