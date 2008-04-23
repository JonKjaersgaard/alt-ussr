/**
 * Uniform Simulator for Self-reconfigurable (modular) Robots
 * 
 * (C) 2006 University of Southern Denmark
 */
package onlineLearning.atron;

import java.awt.Color;
import java.util.List;
import java.util.Random;

import onlineLearning.RolePlayer;
import onlineLearning.SkillController;
import onlineLearning.SkillLearner;
import onlineLearning.locomoton.MotionController;
import onlineLearning.locomoton.MotionRewardSystem;
import onlineLearning.locomoton.MotionSkills;
import ussr.samples.atron.ATRONController;

/**
 * Role based online learning of robot skill for the ATRON robot
 * 
 * @author david
 *
 */
public class AtronSkillController extends ATRONController implements SkillController {
	
	float evalPeriode = AtronSkillSimulation.evalPeriode;
	static boolean syncronized = AtronSkillSimulation.syncronized;
	static boolean primitiveRoles = AtronSkillSimulation.primitiveRoles;
	SkillLearner learner;
    RolePlayer rolePlayer;
    MotionRewardSystem rewardSystem; 
    MotionController motionController;
    private AtronConnectorProxy connectorProxy;
    private AtronCenterProxy centerProxy;
    static Random rand = new Random();
    
    public AtronSkillController() {
    	rewardSystem = new MotionRewardSystem(this, AtronSkillSimulation.getPhysicsSimulation());
    	if(primitiveRoles) rolePlayer = new AtronRolePlayerPrimitive(this);
    	else rolePlayer = new AtronRolePlayer(this);
    	int nSkills = MotionSkills.getNumberOfSkills();
    	//motionController = new MotionController(); //for all learning
    	motionController = new MotionController(MotionSkills.ESCAPE);
    	learner = new SkillLearner(rolePlayer, rewardSystem, nSkills);
    	connectorProxy = new AtronConnectorProxy(this);
    	centerProxy = new AtronCenterProxy(this);
    	setBlocking(false);
    }
    public void activate() {
    	yield();
    	init();
    	learner.init(this,evalPeriode); 
    	learner.startLearning(this ,evalPeriode);
    	System.out.println("Module "+getDebugID()+" Finished");
	}
    public void init() {
    	getModule().setProperty("robotID", getRobotID()+"");
    	if(syncronized) {
    		delay(2000);
    		/*float startTime = module.getSimulation().getTime();
    		float ms = 200000;
    		//System.out.println("Rotating to angle "+angle);
    		int angle = 180;
    		float updateTime=0;
        	while(module.getSimulation().getTime()<(ms/1000.0+startTime)) {
        		if(module.getSimulation().getTime()>(1+updateTime)) {
        			updateTime = module.getSimulation().getTime();
        			angle = rand.nextInt(360);//+180-10;
        		}
        		rotateToDegreeInDegrees(angle);
    			module.getSimulation().waitForPhysicsStep(false);
        	}*/
    	}
    	else delay(2000+rand.nextInt(2000));
    	System.out.println(getDebugID()+": Module staring "+getTime());
    }
	public void controlHook() {
		motionController.control(this);
	}
	
	public int getRobotSkill() {
		return motionController.getRobotSkill();
	}
	
	//TODO CPG implementation of this method
    public float getTime() {
		return module.getSimulation().getTime();
	}
   
    
    public int getDebugID() {
    	return getModule().getID();
    }
    
    public int getRobotID() {
    	String name = getModule().getProperty("name");
    	CharSequence id = name.subSequence(name.indexOf("[")+1, name.indexOf("]"));
    	return Integer.parseInt(id.toString());
    }
    public void yield() {
    	while(module.getSimulation().isPaused()) Thread.yield();
    	if(!isStopped()) module.getSimulation().waitForPhysicsStep(false);
	}
    public boolean isStopped() {
    	return module.getSimulation().isStopped();
    }
    public void delay(int ms) {
    	float startTime = module.getSimulation().getTime();
    	while(module.getSimulation().getTime()<(ms/1000.0+startTime)) {
			module.getSimulation().waitForPhysicsStep(false);
    	}
    }
    public AtronConnectorProxy getConnectorProxy() {
		return connectorProxy;
	}
    public AtronCenterProxy getCenterProxy() {
		return centerProxy;
	}

    static int msgCount = 0;
    public void handleMessage(byte[] message, int messageSize, int channel) {
    	msgCount++;
    	if(message[0]=='d') {
    		connectorProxy.recieveDisconnectMsg(message[1], message[2], message[3], (byte)channel);
    	}
    	//if(msgCount%10==0) System.out.println("We have now handled "+msgCount+" messages");
    	//if(getDebugID()==0) System.out.println("Message "+printMessage(message, messageSize, channel));
    }
	private String printMessage(byte[] message, int messageSize, int channel) {
		String msg = channel+" <- [ ";
		for(int i=0;i<message.length;i++) {
			msg = msg+message[i]+" ";
		}
		msg = msg + "]";
		return msg;
	}
	
}
