package ussr.home.davidc.onlineLearning.atron;

import java.awt.Color;

import ussr.home.davidc.onlineLearning.RolePlayer;

public class AtronRolePlayer implements RolePlayer  {
	AtronSkillController controller;
	//enum AtronRoles  {stop,oscillate0,oscillate90,oscillate180,oscillate270,clockwise,counter_clockwise};
	enum AtronRoles  {stop,oscillate0small,oscillate90small,oscillate180small,oscillate270small,oscillate0big,oscillate90big,oscillate180big,oscillate270big,clockwise,counter_clockwise};
	public AtronRolePlayer(AtronSkillController controller) {
		this.controller = controller;
	}
	public int getNumberOfRoles() {
		return AtronRoles.values().length;
	}
	public void playRole(int role) {
		//System.out.println("Playing:"+roleToString(role));
		switch(role) {
			case 0: stop(); break;
			case 1: oscillate(0,25); break;
			case 2: oscillate(90,25); break;
			case 3: oscillate(180,25); break;
			case 4: oscillate(270,25); break;
			case 5: oscillate(0,55); break;
			case 6: oscillate(90,55); break;
			case 7: oscillate(180,55); break;
			case 8: oscillate(270,55); break;
			case 9: rotate(1); break;
			case 10: rotate(-1); break;
			default: break;
		}
		colorFromRole(role);
	}
	
	private void colorFromRole(int role) {
		switch(role) 
		{
			case 0: controller.getModule().setColor(new Color(0,0,0)); break;
			case 1: controller.getModule().setColor(new Color(255,0,0)); break;
			case 2: controller.getModule().setColor(new Color(255-1*50,0,0)); break;
			case 3: controller.getModule().setColor(new Color(255-2*50,0,0)); break;
			case 4: controller.getModule().setColor(new Color(255-3*50,0,0)); break;
			case 5: controller.getModule().setColor(new Color(0,255,0)); break;
			case 6: controller.getModule().setColor(new Color(0,255-1*50,0)); break;
			case 7: controller.getModule().setColor(new Color(0,255-2*50,0)); break;
			case 8: controller.getModule().setColor(new Color(0,255-3*50,0)); break;
			case 9: controller.getModule().setColor(new Color(0,0,255)); break;
			case 10: controller.getModule().setColor(new Color(0,0,255)); break;
			default: break;
		}		
	}
	private void stop() {
		//System.out.println("getAngularPositionDegrees() "+controller.getAngularPositionDegrees());
		controller.rotateToDegree((float)(180.0*2*Math.PI/360.0));
	}
	private void rotate(int dir) {
		//float time = controller.getTime();
		controller.rotateContinuous(dir); //make periodic?
	}
	private void oscillate(float offset, float angleWidth) {
		float time = controller.getTime();
		float speedUp = 1;
		float x = (float)Math.sin(speedUp*time*(2*Math.PI)+offset*(2*Math.PI/360));
		float currentAngle = controller.getAngularPositionDegrees()-180; 
		if(currentAngle>180) currentAngle = -(360 - currentAngle);
		if(x>0) {
			if(currentAngle>angleWidth) {
				controller.rotateToDegree(controller.getAngularPosition()-1);
			}
			else {
				controller.rotateContinuous(1);
			}
		}
		else {
			if(currentAngle<-angleWidth) {
				controller.rotateToDegree(controller.getAngularPosition()+1);
			}
			else {
				controller.rotateContinuous(-1);
			}
		}
	}
	public String roleToString(int role) {
		return AtronRoles.values()[role].toString();
	}
}
