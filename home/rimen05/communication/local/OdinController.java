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
 * state. The modules are links and joints.
 * 
 * @author david (franco's mods)
 *
 */
public class OdinController extends ussr.samples.odin.OdinController {
	
	static Random rand = new Random(System.currentTimeMillis());

    float timeOffset=0;
    public byte[] msg = {0};
    public int color = 0;
    /*BEGIN TO BE SET*/
    static float pe = 0.1f; //0 to 1, modules sending information out.
    static float nep = 0.8f; //0 to 1, modules the information is transmitted to.
    /*END TO BE SET*/
    static int ne = 0;
    static boolean idDone = false;
    static boolean peDone = false;
    static boolean txDone = false;
    int channelOut = -1;
    //We can also access modules, which is a protected attribute of a parent class.
    static int Imod = 0;
        
    /**
     * Constructor.
     * 
     * @param type
     */
    public OdinController(String type) {
    	this.type = type;
    	timeOffset = 100*rand.nextFloat();
    }
    
	/**
	 * In this method I create one muscle module with color=3 (white) which
	 * is suppose to begin the information diffusion process.
	 * Apparently, this method is called when we start the simulation.
	 * 
     * @see ussr.model.ControllerImpl#activate()
     */
    public void activate() {
    	while(module.getSimulation().isPaused()) Thread.yield();
    	delay(1000);
    	
    	//This process is done until I have selected the one propagating module.
    	//Only in the activation of the first module.
    	if(!idDone){
        	List<Module> modules = module.getSimulation().getModules();
        	int pos = 0;
        	while(!idDone){
                pos = rand.nextInt(modules.size());
                OdinController controller = (OdinController)(modules.get(pos)).getController(); 
                if(controller.type=="OdinMuscle"){
                	//id = modules.get(pos).getID();
                	controller.color = 1;//purple
                	controller.setColor(0.5f,0.5f,1);//paint purple
			    	controller.msg[0] = 'p';
			    	Imod++;
                	idDone = true;
                	//System.out.println("id = "+id);
                }
        	}

    	}
    	
    	//This process is done until we have selected X out of Xt modules (so that X/Xt == pe).
    	//Only in the activation of the first module.
    	if(!peDone){
            //Here I have to set one and just one module with colour purple.
        	List<Module> modules = module.getSimulation().getModules();
        	int counter = 0;
        	for(int i=0; i<modules.size(); i++){
        		OdinController controller = (OdinController)(modules.get(i)).getController(); 
                if(controller.type=="OdinMuscle"){
                	counter++;
                }
        	}
        	ne = ((int)(nep*counter));
        	int x = ((int)(pe*(counter)));
        	counter = 0;
        	int pos = 0;
        	while(!peDone){
                pos = rand.nextInt(modules.size());
                OdinController controller = (OdinController)(modules.get(pos)).getController(); 
                if((controller.type=="OdinMuscle") && (color!=1)){
                	controller.color = 2;//green
                	controller.setColor(0,1,0);
                	controller.msg[0] = 'g';
                	counter++;
                	if(counter == x){
                		peDone = true;
                	}
                }
        	}

    	}
		
    	if(type=="OdinMuscle") muscleControl();
    	if(type=="OdinBall") ballControl();
	}
    
    /**
     * This method propagates the information.
     */
    public void muscleControl() {
    	float lastTime = module.getSimulation().getTime();
    	while(true) {
    		//float time = module.getSimulation().getTime()+timeOffset;
    		//actuate((float)(Math.sin(time)+1)/2f);
			module.getSimulation().waitForPhysicsStep(false);
			
			if((lastTime+0.5)<module.getSimulation().getTime()){
				
				if(Imod>=ne && !txDone){
					System.out.println("Information Transmitted!!!");
					System.out.println("Time = XXX");
					txDone = true;
				}
				
				if(channelOut==-1){
					if(color==1){//propagating module
						sendMessage(msg, (byte)msg.length,(byte)0);
						sendMessage(msg, (byte)msg.length,(byte)1);
					}
				}
				else{
					sendMessage(msg, (byte)msg.length,(byte)channelOut);
					//if no collision -> isSent==true;
					//Here I have to embed the probability pe?
					//should I send just with that probability?
				}
					
				lastTime = module.getSimulation().getTime();
			}
        }
    }
    
    public void ballControl() {
    	while(true) {
        	try {
                //Thread.sleep(10000);
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
    	if(message[0]=='p' && color!=1){
    		//Local communication received by a not informed module (Nmod).
    		color = 1;
    		setColor(0.5f,0.5f,1);//paint purple
    		msg[0] = 'p';
    		if(channel == 0) channelOut=1;
    		else channelOut=0;
    		Imod++;
    	}
    }
}
