package onlineLearning.atron;

import java.awt.Color;

import onlineLearning.Role;
import onlineLearning.RolePlayer;
import onlineLearning.SkillLearner;
import onlineLearning.SkillLearner.LearningStrategy;
import ussr.physics.PhysicsLogger;

public class AtronRolePlayerPrimitive implements RolePlayer  {
	//float periodeTime = AtronSkillSimulation.periodeTime;
	private int homePos = 180;
	private int safeHomePos = 180;
	AtronSkillController controller;
	enum AtronRoles  {stop,clockwise,counter_clockwise};
	public AtronRolePlayerPrimitive(AtronSkillController controller) {
		this.controller = controller;
	}
	public int getNumberOfRoles() {
		return AtronRoles.values().length;
	} 
	public void playRole(Role roles, float timePercent) {
		//TWO Wheeler
		/*if(controller.getDebugID()==0) roles.setRole(0, 0);
		if(controller.getDebugID()==1) roles.setRole(2, 0);
		if(controller.getDebugID()==2) roles.setRole(1, 0);*/
		
		//CRAWLER 1
		/*if(controller.getDebugID()==0) roles.setRole(1, 0);
		if(controller.getDebugID()==1) roles.setRole(2, 0);
		if(controller.getDebugID()==3) roles.setRole(2, 0);
		
		if(controller.getDebugID()==2) roles.setRole(1, 0);
		if(controller.getDebugID()==4) roles.setRole(1, 0);*/
		
		//WALKER 1
		/*if(controller.getDebugID()==0) roles.setRole(1, 0);
		if(controller.getDebugID()==1) roles.setRole(2, 0);
		if(controller.getDebugID()==2) roles.setRole(1, 0);
		if(controller.getDebugID()==3) roles.setRole(2, 0);
		
		if(controller.getDebugID()==4) roles.setRole(1, 0);
		if(controller.getDebugID()==5) roles.setRole(2, 0);
		if(controller.getDebugID()==6) roles.setRole(1, 0);
		if(controller.getDebugID()==7) roles.setRole(2, 0);*/
		
		
		//Systematic WALKER 1
	/*	if(controller.getDebugID()==4) roles.setRole(0, 0);
		if(controller.getDebugID()==5) roles.setRole(0, 0);
		if(controller.getDebugID()==6) roles.setRole(0, 0);
		if(controller.getDebugID()==7) roles.setRole(0, 0);
		*/

		//System.out.println("if(controller.getDebugID()=="+controller.getDebugID()+") role="+role+";");
		//System.out.println("Role count = "+roles.getRoleCount());
		if(roles.getRoleCount()>0) {
			playCenterRole(roles.getRole(0), timePercent);
		//	playCenterRole(role);
			//TODO somethings wrong => deadlock? also try to count messages
		}
		if(roles.getRoleCount()>1) {
			playHomePosRole(roles.getRole(1));
			//if(controller.getDebugID()<4) playHomePosRole(1);
			//else playHomePosRole(3);
		}
		if(roles.getRoleCount()>5) {
			for(int connector=0;connector<8;connector+=2) {
				//playConnectorRole(connector, roles.getRole(2+connector/2));
			}
		}
	}
	float[] lastDisconnectTryTime = new float[8];
	float[] lastConnectTryTime = new float[8];
	static int disconnectcounter=0,connectcounter=0;
	private void playConnectorRole(int connector, int connectorRole) {
		if(connectorRole==0) { //connect
			if(!controller.isConnected(connector)) {
				//stop();
				if(controller.canConnect(connector)) {
					//stop();
					if(controller.isRotating()) return;
					controller.getConnectorProxy().safeConnect(connector);
					lastConnectTryTime[connector] = controller.getTime();
					connectcounter++;
					System.out.println("Connect trials "+connectcounter);
				}
			}
		}
		else { //disconnect
			
			if(controller.isConnected(connector)) {
				if((lastDisconnectTryTime[connector]+3.0f)<controller.getTime()) {
					//stop();
					controller.getConnectorProxy().safeDisconnect(connector);
					lastDisconnectTryTime[connector] = controller.getTime();
					disconnectcounter++;
					//System.out.println("Disconnect trials "+disconnectcounter);
				}
			}
		}
	}
	
	private int getNearestHomePos(float angle) {
		int[] homePoss = new int[]{180, 270, 0, 90};
		int nIndex = 0;
		for(int i =0;i<homePoss.length;i++) {
			if(Math.abs(angle-homePoss[i])<Math.abs(angle-homePoss[nIndex])) {
				nIndex = i;
			}
		}
		return homePoss[nIndex];
	}
	
	private int toHomePosRole(int homePosAngle) {
		if(homePosAngle == 180) return 0;
		if(homePosAngle == 270) return 1;
		if(homePosAngle == 0) return 2;
		if(homePosAngle == 90) return 3;
		return -1;
	}
	public void stopHere() {
		float angle = controller.getAngularPositionDegrees();
		int homePos = getNearestHomePos(angle);
		int role = toHomePosRole(homePos);
		System.out.println("Stopping here: "+role+" "+homePos);
		playHomePosRole(role);
	}
	private void playHomePosRole(int homePosRole) {
		int previousHomePos = homePos;
		if(homePosRole==0) homePos = 180;
		if(homePosRole==1) homePos = 270;
		if(homePosRole==2) homePos = 0;
		if(homePosRole==3) homePos = 90;
		if(homePos!=previousHomePos) safeHomePos = previousHomePos;
		//stop();
	}
	
	private void playCenterRole(int centerRole, float timePercent) {
		//if("center is stuck") centerRole=0;
		//centerRole=0;
	
		/*switch(centerRole) {
			case 0: stop(); break;
			case 1: rotate(1); break;
			case 2: rotate(-1); break;
			default: break;
		}*/
		
		/*if(timePercent>0.5&&centerRole==1) centerRole=2;
		else if(timePercent>0.5&&centerRole==2) centerRole=1;*/
		
		//if(timePercent>0.25&&timePercent<0.75) centerRole = (centerRole==1)?2:(centerRole==2)?1:0;
		if(SkillLearner.learningStrategy == LearningStrategy.TIMETABLE) {
			/*if(!homeObserver(timePercent)) {
				switch(centerRole) {
					case 0: stop(); break;
					case 1: rotate(1); break;
					case 2: rotate(-1); break;
					default: break;
				}
			}*/
			/*switch(centerRole) {
				case 0: rotateTo(0); break;
				case 1: rotateTo(120); break;
				case 2: rotateTo(240); break;
				default: break;
			}*/
			/*switch(centerRole) {
				case 0: rotateTo(0*72); break;
				case 1: rotateTo(1*72); break;
				case 2: rotateTo(2*72); break;
				case 3: rotateTo(3*72); break;
				case 4: rotateTo(4*72); break;
				default: break;
			}*/
			//rotateTo((int)(centerRole*360.0/3.0));
			switch(centerRole) {
				case 0: rotateToNotUnlimited(0); break;
				case 1: rotateToNotUnlimited(-45); break;
				case 2: rotateToNotUnlimited(45); break;
				case 3: rotateToNotUnlimited(90); break;
				case 4: rotateToNotUnlimited(-90); break;
				default: break;
			}
			/*switch(centerRole) {
			case 0: rotateToNotUnlimited(0); break;
			case 1: rotateToNotUnlimited(-25); break;
			case 2: rotateToNotUnlimited(25); break;
			case 3: rotateToNotUnlimited(-50); break;
			case 4: rotateToNotUnlimited(50); break;
			default: break;
			}*/
			//rotateToNotUnlimited((int)(centerRole*360.0/3.0)-120);
		}
		else {
			switch(centerRole) {
				case 0: stop(); break;
				case 1: rotateSync(1,timePercent); break;
				case 2: rotateSync(-1,timePercent); break;
				default: break;
			}
		}
		
		//colorFromRole(centerRole);
	}
	private void rotateTo(int angle) {
		int goal = (angle+homePos)%360;
		int current = controller.getAngularPositionDegrees();
		int error = Math.abs(goal-current);
		if(current<goal&&error<=180) controller.rotateContinuous(1);
		if(current<goal&&error>180) controller.rotateContinuous(-1);
		if(current>goal&&error<=180) controller.rotateContinuous(-1);
		if(current>goal&&error>180) controller.rotateContinuous(1);
		//if(controller.getDebugID()==1) System.out.println("Degree "+current+" "+goal);
		//controller.rotateToDegreeInDegrees(goal);
	}
	int oldError = 0;
	private void rotateToNotUnlimited(int angle) {
		int goal = (angle+homePos)%360;
		int current = controller.getAngularPositionDegrees();
		int error = goal-current;
		if(error>0) controller.rotateContinuous(1);
		else controller.rotateContinuous(-1);
		
		if(oldError<=error) {
			//stalled++;
		}
		oldError = error;
	}
	private boolean homeObserver(float timePercent) {
		int angle = controller.getAngularPositionDegrees();
		double percentFromHome = Math.abs(angle-180)/360.0f; 
		if(percentFromHome>(1-timePercent)) {
			//System.out.println(controller.getDebugID()+": Apply force "+timePercent+" vs "+percentFromHome);
			stop();
			return true;
		}
		else {
			//System.out.println(controller.getDebugID()+": not "+timePercent);
		}
		return false;
	}
	private void rotateSync(int dir, float timePercent) {
		int angle = controller.getAngularPositionDegrees();
		/*int joint =0;
		if(angle>=0&&angle<90) 		joint = 2;
		if(angle>90&&angle<=180) 	joint = 0;
		if(angle>180&&angle<=270) 	joint = 1;
		if(angle>270&&angle<=360) 	joint = 3;*/
		if(dir==-1) angle = 360-angle;
		float anglePercent = ((angle+homePos)%360)/360.0f;
		float error = anglePercent-timePercent; //minus = actuator is behind
		
		if(error>0.5) error -=1;
		else if(error<-0.5) error +=1;
		
		/*if(controller.getDebugID()==0) {
			if(Math.abs(error)<0.05f) System.out.println("On time: "+error);
			else if(error<0) System.out.println("Behind: "+error);
			else System.out.println("Ahead: "+error);
		}*/
		if(error>0.25/2f) {
			//System.out.println("Ahead stopping");
			controller.centerStop(); //ahead stop
		}
		else if(error<-0.25) {
			//System.out.println("Behing home()");
			stop(); //way behind go shortest path towards home 
		}
		else rotate(dir);
	}
	private void testFullRotation(int dir) {
    	float startTime = controller.getTime();
    	controller.setBlocking(true);
    	controller.rotate(dir);
    	controller.rotate(dir);
    	controller.rotate(dir);
    	controller.rotate(dir);
    	controller.setBlocking(false);
    	System.out.println("One full rotation took "+(controller.getTime()-startTime)+" sec");
	}
	private void colorFromRole(int role) {
		switch(role) 
		{
			case 0: controller.getModule().setColor(new Color(100,100,100)); break;
			case 1: controller.getModule().setColor(new Color(255,0,0)); break;
			case 2: controller.getModule().setColor(new Color(0,255,0)); break;
			default: break;
		}		
	}
	private void stop() {
		//controller.getCenterProxy().safeToDegreeRotate((float)(homePos*2*Math.PI/360.0),(float)(safeHomePos*2*Math.PI/360.0));
		controller.rotateToDegree((float)(homePos*2*Math.PI/360.0));
	}
	float startTime=0;
	private void rotate(int dir) {
		//testFullRotation(dir);
		controller.rotateContinuous(dir);
		/*float time = controller.getTime();
		float angle,error;
		if(dir>0) {
			angle = (float)(2*Math.PI*((2*time/(0.9*periodeTime))%1));
			error =  angle-controller.getAngularPosition();
			if(error>Math.PI) error-=2*Math.PI; if(error<-Math.PI) error+=2*Math.PI;
			if(error>-Math.PI/2)
				controller.rotateContinuous(dir);
			else
				controller.centerStop();
		}
		else {
			angle = (float)(2*Math.PI-2*Math.PI*((2*time/(0.9*periodeTime))%1));
			error =  angle-controller.getAngularPosition();
			if(error>Math.PI) error-=2*Math.PI; if(error<-Math.PI) error+=2*Math.PI;
			if(error<Math.PI/2)
				controller.rotateContinuous(dir);
			else
				controller.centerStop();
		}*/
		//controller.rotateToDegree(angle);
	}
	public String roleToString(int role) {
		return AtronRoles.values()[role].toString();
	}
}
