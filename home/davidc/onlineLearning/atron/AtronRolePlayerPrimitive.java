package onlineLearning.atron;

import java.awt.Color;

import onlineLearning.Role;
import onlineLearning.RolePlayer;

public class AtronRolePlayerPrimitive implements RolePlayer  {
	float periodeTime = AtronSkillSimulation.periodeTime;
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
	public void playRole(Role roles) {
		/*if(controller.getDebugID()==0) role=2;
		if(controller.getDebugID()==1) role=2;
		if(controller.getDebugID()==2) role=1;
		if(controller.getDebugID()==3) role=1;
		if(controller.getDebugID()==4) role=2;
		if(controller.getDebugID()==5) role=2;
		if(controller.getDebugID()==6) role=1;
		if(controller.getDebugID()==7) role=0;*/
		//System.out.println("if(controller.getDebugID()=="+controller.getDebugID()+") role="+role+";");
		//System.out.println("Role count = "+roles.getRoleCount());
		
		if(roles.getRoleCount()>0) {
			//playCenterRole(roles.getRole(0));
			//TODO somethings wrong => deadlock? also try to count messages
		}
		if(roles.getRoleCount()>1) {
			//playHomePosRole(roles.getRole(1));
			if(controller.getDebugID()<4) playHomePosRole(1);
			else playHomePosRole(3);
		}
		if(roles.getRoleCount()>5) {
			for(int connector=0;connector<8;connector+=2) {
				playConnectorRole(connector, roles.getRole(2+connector/2));
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
		stop();
	}
	
	private void playCenterRole(int centerRole) {
		//if("center is stuck") centerRole=0;
		//centerRole=0;
		switch(centerRole) {
			case 0: stop(); break;
			case 1: rotate(1); break;
			case 2: rotate(-1); break;
			default: break;
		}
		colorFromRole(centerRole);
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
		controller.getCenterProxy().safeToDegreeRotate((float)(homePos*2*Math.PI/360.0),(float)(safeHomePos*2*Math.PI/360.0));
		//controller.rotateToDegree((float)(homePos*2*Math.PI/360.0));
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
