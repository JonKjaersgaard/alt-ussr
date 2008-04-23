package onlineLearning.mtran;

import java.util.Random;

import com.jmex.model.collada.schema.constantType;

import onlineLearning.Role;
import onlineLearning.RolePlayer;
import onlineLearning.atron.TimeOutManager;
import ussr.samples.atron.IATRONAPI;

public class MTRANRolePlayer implements RolePlayer  {
	MTRANLearningController controller;
	
	public MTRANRolePlayer(MTRANLearningController controller) {
		this.controller = controller;
	}
	public int getNumberOfRoles() {
		return 3;
	} 
	static Random rand = new Random();
	public void playRole(Role roles, float timePercent) {
		
		double goal0=0, goal1=0;
		
		
		
		/*switch (roles.getRole(0)) {
			case 0: goal0 = 0;  goal1 = 0; break;
			case 1: goal0 = 0;  goal1 = 1; break;
			case 2: goal0 = 0;  goal1 =-1; break;
			case 3: goal0 = 1;  goal1 = 0; break;
			case 4: goal0 = 1;  goal1 = 1; break;
			case 5: goal0 = 1;  goal1 =-1; break;
			case 6: goal0 =-1;  goal1 = 0; break;
			case 7: goal0 =-1;  goal1 = 1; break;
			case 8: goal0 =-1;  goal1 =-1;  break;
			default: break;
		}*/
		switch (roles.getRole(0)) {
			case 0: goal0 = 0;  break;
			case 1: goal0 = 1;  break;
			case 2: goal0 = -1;  break;
			default: break;
		}	
		switch (roles.getRole(1)) {
			case 0: goal1 = 0;  break;
			case 1: goal1 = 1;  break;
			case 2: goal1 = -1;  break;
			default: break;
		}
		float offset0=0;
		float offset1=0;
		String mName= controller.getModule().getProperty("name");
		if(mName.contains("MINI")) {
			if(mName.contains("x1")) {offset0=-0.5f;offset1=0.5f; }
			else if(mName.contains("y1")) {offset0=-0.5f;offset1=0.5f;}
			else if(mName.contains("z1")) {offset0=-0.5f;	offset1=0.5f;}
			else if(mName.contains("æ1")) {offset0=-0.5f;offset1=0.5f;}
		}
		if(mName.contains("WALKER")) {
			float x=0.33f;
			if(mName.contains("x1")) {offset0=-x;offset1=x; }
			else if(mName.contains("x2")) {offset0=0;offset1=0; }
			else if(mName.contains("y1")) {offset0=-x;offset1=x;}
			else if(mName.contains("y2")) {offset0=0;offset1=0;}
			else if(mName.contains("z1")) {offset0=-x;offset1=x;}
			else if(mName.contains("z2")) {offset0=0;offset1=0;}
			else if(mName.contains("æ1")) {offset0=-x;offset1=x;}
			else if(mName.contains("æ2")) {offset0=0;offset1=0;}
		}
		if(mName.contains("WALKER2")) {
			if(mName.contains("x1")) 		{offset0=-0.25f;offset1=0.25f; }
			else if(mName.contains("x2")) 	{offset0=-0.25f;offset1=0.25f; }
			else if(mName.contains("y1")) 	{offset0=-0.25f;offset1=0.25f; }
			else if(mName.contains("y2")) 	{offset0=-0.25f;offset1=0.25f; }
			else if(mName.contains("z1")) 	{offset0=-0.25f;offset1=0.25f;}
			else if(mName.contains("z2")) 	{offset0=-0.25f;offset1=0.25f; }
			else if(mName.contains("æ1")) 	{offset0=-0.25f;offset1=0.25f;}
			else if(mName.contains("æ2")) 	{offset0=-0.25f;offset1=0.25f; }
		}
		if(mName.contains("WALKER3")) {
			float x=0.5f;
			if(mName.contains("center")) 				{offset0=-1;offset1=-1f;}
			else if(mName.contains("leftBackFoot")) 	{offset0=-0.0f;offset1=0.0f;}
			else if(mName.contains("leftBackSpline")) 	{offset0=-x;offset1=x;}
			else if(mName.contains("leftFrontSpline")) 	{offset0=-x;offset1=x;}
			else if(mName.contains("leftFrontFoot")) 	{offset0=-0.0f;offset1=0.0f;}
			else if(mName.contains("rightBackFoot")) 	{offset0=-0.0f;offset1=0.0f;}
			else if(mName.contains("rightBackSpline")) 	{offset0=-x;offset1=x;}
			else if(mName.contains("rightFrontSpline")) {offset0=-x;offset1=x;}
			else if(mName.contains("rightFrontFoot")) 	{offset0=-0.0f;offset1=0.0f;}
		}
		if(mName.contains("WALKER4")) {
			float x=0.25f; //good
			if(mName.contains("center")) 				{offset0= 0;offset1=0;}
			else if(mName.contains("leftBackFoot")) 	{offset0=-x;offset1=x;}
			else if(mName.contains("leftBackSpline")) 	{offset0=-x;offset1=x;}
			else if(mName.contains("leftFrontSpline")) 	{offset0=-x;offset1=x;}
			else if(mName.contains("leftFrontFoot")) 	{offset0=-x;offset1=x;}
			else if(mName.contains("rightBackFoot")) 	{offset0=-x;offset1=x;}
			else if(mName.contains("rightBackSpline")) 	{offset0=-x;offset1=x;}
			else if(mName.contains("rightFrontSpline")) {offset0=-x;offset1=x;}
			else if(mName.contains("rightFrontFoot")) 	{offset0=-x;offset1=x;}
		}
		if(mName.contains("LOOP")) {
			offset0=1f/3f;
			offset1=-1f/3f;
			
			
			if(mName.contains("front")||mName.contains("back")) {
				for(int i=0;i<6;i++) {
					if(!controller.isConnected(i)&&controller.isOtherConnectorNearby(i)) {
						System.out.println("I see it!!!");
						controller.connect(i);
					}
				}
			}
		}
		//goal0=0;goal1=0;
		goToAngles(0.33*goal0+offset0,0.33*goal1+offset1);
		
		
		/*int time = (int)controller.getTime();
		if(controller.getDebugID()==0 &&time!=oldTime) {
			System.out.println(time+": "+roles.getRole(0));
			oldTime=time;
		}*/
	}
	int oldTime =0;
	int counter=0;
	private void goToAngles(double goal0, double goal1) {
    	double cur0 =  2*(controller.getEncoderPosition(0)-0.5);
		if(cur0>goal0) {
			controller.rotate(-1, 0);
		}else {
			controller.rotate(1, 0);
		}
		if(Math.abs(cur0-goal0)<0.05) controller.centerStop(0);

		double cur1 =  2*(controller.getEncoderPosition(1)-0.5);
		if(cur1>goal1) {
			controller.rotate(-1, 1);
		}else {
			controller.rotate(1, 1);
		}
		counter++;
		//if(counter%50==0)
		//	System.out.println("{"+controller.getDebugID()+", "+controller.getTime()+", "+controller.getEncoderPosition(0)+", "+controller.getEncoderPosition(1)+"},");
		//if(Math.abs(cur1-goal1)<0.05) controller.centerStop(1);
		//if(controller.getDebugID()==0) System.out.println("got to: "+controller.getTime());
		//if(controller.getDebugID()==0) System.out.println(controller.getTime()+": Error = "+(int)(Math.abs(cur0-goal0)*100)+" Error = "+(int)(Math.abs(cur1-goal1)*100));
    }
	
}
