package onlineLearning.odin;

import onlineLearning.Role;
import onlineLearning.RolePlayer;

public class OdinRolePlayer implements RolePlayer  {
	OdinSkillController controller;
	enum OdinWheelRoles  {wheel_stop,wheel_forward,wheel_backward,wheel_passive};
	enum OdinMuscleRoles  {muscle_oscillate,muscle_contract,muscle_expand};
	enum OdinBatteryRoles  {battery_passive};

	public OdinRolePlayer(OdinSkillController controller) {
		this.controller = controller;
	}
	public int getNumberOfRoles() {
		if(controller.getType()=="OdinMuscle") 	return OdinMuscleRoles.values().length;
		else if(controller.getType()=="OdinWheel") 	return OdinWheelRoles.values().length;
		else if(controller.getType()=="OdinBattery") return OdinBatteryRoles.values().length;
		else return 0;
	}
	public void playRole(Role roles) {
		String type = controller.getType();
		int role = roles.getRole(0);
    	if(type=="OdinMuscle") {
    		//if(controller.getDebugID()==((int)(controller.getTime())%13)) {role=2;System.out.println("Jubii "+controller.getDebugID());}
    		//else 
    		//role =0;
    		switch(role) {
    			case 0: muscle_oscillate(); break;
    			case 1: muscle_contract(); break;
    			case 2: muscle_expand(); break;
    			default: break;
    		}
    	}
    	if(type=="OdinWheel") {
    		switch(role) {
	    		case 0: wheel_stop(); break;
				case 1: wheel_forward(); break;
				case 2: wheel_backward(); break;
				case 3: wheel_passive(); break;
				default: break;
    		}
		}
	 	if(type=="OdinBattery") {
    		switch(role) {
				case 0: battery_passive(); break;
				default: break;
    		}
		}
	}
	private void battery_passive() {/*controller.setColor(0, 0, 0);*/}
	private void wheel_forward() {controller.actuate(1);}
	private void wheel_stop() {controller.actuate(0);}
	private void wheel_passive() {controller.disactivate();}
	private void wheel_backward() {controller.actuate(-1);}
	private void muscle_expand() {controller.actuate(1);}
	private void muscle_contract() {controller.actuate(0);}
	private void muscle_oscillate() {
		float time = controller.getTime();
		float goal = (float)(Math.sin(time)+1)/2f;
		controller.actuate(goal);
	}
	public String roleToString(int role) {
		if(controller.getType()=="OdinMuscle") return OdinMuscleRoles.values()[role].toString();
		else if(controller.getType()=="OdinWheel") 	return OdinWheelRoles.values()[role].toString();
		else if(controller.getType()=="OdinBattery") return OdinBatteryRoles.values()[role].toString();
		return "NaN";
	}
}
