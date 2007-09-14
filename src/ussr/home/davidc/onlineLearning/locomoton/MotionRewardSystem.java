package ussr.home.davidc.onlineLearning.locomoton;

import ussr.home.davidc.onlineLearning.RewardSystem;
import ussr.model.Module;
import ussr.physics.PhysicsSimulation;

import com.jme.math.Quaternion;
import com.jme.math.Vector3f;

public class MotionRewardSystem implements RewardSystem {
	
	private static float collectedReward = 0;
	Vector3f oldCM;
    Quaternion oldOri;
    PhysicsSimulation simulation;
    
	public MotionRewardSystem(PhysicsSimulation simulation) {
		this.simulation = simulation; 
		reset();
		resetReward();
	}
	
	//TODO implementation of this method 
    public float collectReward(int robotSkill) {
    	float[] rewards = collectAllRewards();
    	float reward = rewards[robotSkill]; 
    	collectedReward += reward;
    	return reward;
	}
    public float[] collectAllRewards() {
    	Vector3f cm = getRobotCM();
    	Quaternion ori = getRobotOrientation();
    	float[] angleDiff = getAngleDifference(ori,oldOri);
    	float[] rewards = new float[MotionSkills.getNumberOfSkills()];
    	for(int i=0;i<rewards.length;i++) {
    		MotionSkills skill = MotionSkills.values()[i];
	    	if(MotionSkills.STOP.equals(skill)) { //STOP - skill
	    		rewards[i] = -cm.distance(oldCM);
	    	}
	    	else if(MotionSkills.ESCAPE.equals(skill)) { //ESCAPE - skill(what ever direction)
	    		rewards[i] = 10*cm.distance(oldCM);
	    	}
	    	else if(MotionSkills.FORWARD.equals(skill)) { //Move Straight forward - skill
	    		rewards[i] = cm.distance(oldCM)-0.01f*Math.abs(angleDiff[1]);
	    	}
	    	else if(MotionSkills.BACKWARD.equals(skill)) { //Move Straight backward - skill
	    		rewards[i] = cm.distance(oldCM)-0.1f*Math.abs(angleDiff[1]);
	    	}
	    	else if(MotionSkills.TURN_RIGHT.equals(skill)) { //TURN Right on spot - skill
	    		rewards[i] = angleDiff[1]-cm.distance(oldCM);
	    	}
	    	else if(MotionSkills.TURN_LEFT.equals(skill)) { //TURN Left on spot - skill
	    		rewards[i] = -angleDiff[1]-cm.distance(oldCM);
	    	}
	    	else {
	    		throw new RuntimeException("Skill with index "+skill+" not recognized");
	    	}
    	}
    	
		/*StringBuffer bb = new StringBuffer ();
		bb.append("Assigning reward: \n");
		bb.append(" Moved = "+cm.distance(oldCM)+" meters\n");
		bb.append(" Rotated = "+180*angleDiff[1]/Math.PI+" degree \n");
		//bb.append(" Angle = "+180*a1[1]/Math.PI+" "+a1[1]+" "+a2[1]+"\n");
		bb.append(" Reward = "+reward+"\n");
		System.out.println(bb.toString());*/
    	
    	reset();
    	return rewards;
    }
    
    private float[] getAngleDifference(Quaternion oriTo, Quaternion oldFrom) {
    	float[] angleDiff = new float[3];
    	float[] a1 = oriTo.toAngles(null);
		float[] a2 = oldFrom.toAngles(null);
		for(int i=0;i<3;i++) {
			angleDiff[i] = (a1[i]-a2[i]);
			if(angleDiff[i]<-Math.PI/2) angleDiff[i] = (float)Math.PI*2f+angleDiff[i];
			if(angleDiff[i]>Math.PI/2) angleDiff[i] = angleDiff[i]-(float)Math.PI*2f;
		}
		return angleDiff;
	}

	public void reset() {
		oldCM = getRobotCM();
    	oldOri = getRobotOrientation();
	}
    public static void resetReward() {
    	collectedReward = 0;
	}
    public static float getSumOfReward() {
    	return collectedReward;
	}
    public Quaternion getRobotOrientation() {
    	if(simulation.getModules().size()==0) return new Quaternion();
    	Module m = simulation.getModules().get(0);
    	return new Quaternion((Quaternion)simulation.getHelper().getModuleOri(m).get());
    }
    public Vector3f getRobotCM() {
    	Vector3f cm = new Vector3f();
        for(int i=0;i<simulation.getModules().size();i++) {
        	cm = cm.addLocal((Vector3f)simulation.getHelper().getModulePos(simulation.getModules().get(i)).get());
        }
        cm = cm.multLocal(1.0f/simulation.getModules().size());
        return cm;
    }
}
