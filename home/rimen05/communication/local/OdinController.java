/**
 * Uniform Simulator for Self-reconfigurable (modular) Robots
 * 
 * (C) 2006 University of Southern Denmark
 */
package communication.local;

import java.util.List;
import java.util.Random;

import ussr.model.Module;

/**
 * A simple controller for the ODIN robot, oscillates OdinMuscles with a random start
 * state. 
 * 
 * @author david (franco's mods)
 *
 */
public class OdinController extends ussr.samples.odin.OdinController {
	
	static Random rand = new Random(System.currentTimeMillis());
    static int id = -1; //store the module's id for origin of local comm. 
    float timeOffset=0;
    byte[] msg = {0};
    int color = 0;
    //We can also access modules, which is protected.
    
    //These members control the diffusion process.
    float pe = 0.1f; //pe from 0 to 1;
        
    /**
     * Constructor.
     * @param type
     */
    public OdinController(String type) {
    	this.type = type;
    	timeOffset = 100*rand.nextFloat();
    	//I use it to set a traffic that makes difficult the information diffusion.
    	if(type == "OdinMuscle"){
    		if(rand.nextInt((int)(1/pe))==0){
    			color = 1;
        		//If color == 1 (green) the module is
    			//set to increase communication traffic.
    		}
    	}
    }
    
	/**
     * @see ussr.model.ControllerImpl#activate()
     */
    public void activate() {
    	while(module.getSimulation().isPaused()) Thread.yield();
    	delay(1000);
    	
    	//These should be done once.
    	if(id == -1){
            //Here I have to set one and just one module with color white.
        	List<Module> modules = module.getSimulation().getModules();
        	boolean idNotAssigned = true;
        	int pos = 0;
        	while(idNotAssigned){
                pos = rand.nextInt(modules.size());
                OdinController controller = (OdinController)(modules.get(pos)).getController(); 
                if(controller.type=="OdinMuscle"){
                	id = modules.get(pos).getID();
                	idNotAssigned = false;
                	//System.out.println("id = "+id);
                }
        	}

    	}
    	
		if(module.getID()==id){
			color = 3;
			//If color == 3 (white) the module is
			//the origin of local communication.
		}
		
		//System.out.println("id = "+id);
		//System.out.println("size = "+module.getSimulation().getModules().size());
		
    	if(type=="OdinMuscle") muscleControl();
    	if(type=="OdinBall") ballControl();
	}
    
    /**
     * Right now, this method is sending messages every 5 seconds,
     * but I should send messages just when determined by a reception
     * of message or when I set it on the simulation.
     * 
     * Now, this method just oscillates the modules.
     */
    public void muscleControl() {
    	float lastTime = module.getSimulation().getTime();
    	boolean doDif = false;
    	while(true) {
    		//float time = module.getSimulation().getTime()+timeOffset;
    		//actuate((float)(Math.sin(time)+1)/2f);
			module.getSimulation().waitForPhysicsStep(false);
			if((lastTime+5)<module.getSimulation().getTime()){
				if(doDif){
					//Then I have to do it selective..
					sendMessage(msg, (byte)msg.length,(byte)0);
					sendMessage(msg, (byte)msg.length,(byte)1);
				}
				if(color==1){
					setColor(0, 1, 0); //paint green
					msg[0] = 'g';
					doDif = true;
					color = 0;
				}
				if(color==3){
					setColor(1,1,1); //paint white
					msg[0] = 'w';
					doDif = true;
					color = 0;
				}
				lastTime = module.getSimulation().getTime();
			}
			/*if((lastTime+5)<module.getSimulation().getTime()) {
				if(color==0) msg[0] = 'r';
				if(color==1) msg[0] = 'g';
				if(color==2) msg[0] = 'b';
				color=(color+1)%3;
				//I send the message through connector 0...
				sendMessage(msg, (byte)msg.length,(byte)0);
				//I send the message through connector 1...
		    	//sendMessage(msg, (byte)msg.length,(byte)1);
		    	lastTime = module.getSimulation().getTime();
			}*/
        }
    }
    
    public void ballControl() {
    	while(true) {
        	try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                throw new Error("unexpected");
            }
    	}
    }
    
    /**
     * This handler decide what to do with the messages the modules receive.
     */
    public void handleMessage(byte[] message, int messageSize, int channel) {
    	//Here I have to check the message connector and diffuse the information.
    	//float lastTime = module.getSimulation().getTime();
    	//if((lastTime+5)<module.getSimulation().getTime()){
    		/*if(message[0]=='g' && color!=1){
        		color = 1;
        		setColor(0, 1, 0);
        		//if(channel == 0) sendMessage(message, (byte)messageSize,(byte)1);
        		//if(channel == 1) sendMessage(message, (byte)messageSize,(byte)0);
        	}*/
    		if(message[0]=='w' && color!=3){
        		color = 3;
        		//setColor(1, 1, 1);
        		//if(channel == 0) sendMessage(message, (byte)messageSize,(byte)1);
        		//if(channel == 1) sendMessage(message, (byte)messageSize,(byte)0);
        	}
    	//}
    	
   		//if(message[0]=='r') setColor(1, 0, 0);
   		//if(message[0]=='g') setColor(0, 1, 0);
   		//if(message[0]=='b') setColor(0, 0, 1);
    }
}
