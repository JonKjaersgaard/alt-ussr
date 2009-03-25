package onlineLearning.skills;

import onlineLearning.Role;
import onlineLearning.SkillController;

public class SkillSystematic implements Skill {
	int nRoles;
	int myID;
	int iterations = 0;
	int counter  = 0;
	public SkillSystematic(float periodeTime, SkillController controller) {
		myID = controller.getDebugID();
	}
	public Role r;
	public Role getRole() {
		r = new Role(1);
		
		/*if(counter%3==0||counter%3==1) 
		{
			//WALKER 1\\
			if(myID==0) r.setRole((iterations/1)%3, 0); //leg1
			if(myID==1) r.setRole((iterations/3)%3, 0); //leg2
			if(myID==2) r.setRole((iterations/3/3)%3, 0); //leg3
			if(myID==3) r.setRole((iterations/3/3/3)%3, 0); //leg4
			
			if(myID==4) r.setRole(0, 0); //foot
			if(myID==5) r.setRole(0, 0); //foot
			if(myID==6) r.setRole(0, 0); //foot
			if(myID==7) r.setRole(0, 0); //foot
			if(counter%3==1)iterations++;
		} else {
			r.setRole(0, 0);
		}*/
		if(myID%5==1) r.setRole(1, 0);
		if(myID%5==2) r.setRole(2, 0);
		/*if(counter>3*10&&myID==0*5+1) r.setRole(2, 0);
		if(counter>3*20&&myID==1*5+2) r.setRole(1, 0);
		if(counter>3*30&&myID==2*5+1) r.setRole(2, 0);
		if(counter>3*40+5) System.exit(0);*/
		
		if(counter>3*10&&myID==0*5+1) r.setRole(2, 0);
		if(counter>3*20&&myID==1*5+2) r.setRole(1, 0);
		if(counter>3*30&&myID==2*5+1) r.setRole(2, 0);
		if(counter>3*40&&myID==3*5+2) r.setRole(1, 0);
		if(counter>3*50&&myID==4*5+1) r.setRole(2, 0);
		if(counter>3*60&&myID==5*5+2) r.setRole(1, 0);
		if(counter>3*70&&myID==6*5+1) r.setRole(2, 0);
		if(counter>3*80&&myID==7*5+2) r.setRole(1, 0);
		if(counter>3*90&&myID==8*5+1) r.setRole(2, 0);
		if(counter>3*100&&myID==9*5+2) r.setRole(1, 0);
		if(counter>3*110+5) System.exit(0);
		System.out.println("Counter "+counter );
		
		counter++;
		return r;
	}
	public void update(float reward, float evalTime, Role myRole) {
		
	}
	public void fromFloats(float[] skillData) {
		throw new RuntimeException("Unimplemented method");
	}
	public Role getBestRole() {
		return r;
	}
	public boolean isContinuous() {
		return false;
	}
	public int getBestRoleInt() {
		// TODO Auto-generated method stub
		return 0;
	}
}
