/**
 * Uniform Simulator for Self-reconfigurable (modular) Robots
 * 
 * (C) 2006 University of Southern Denmark
 */
package communication.local;

import java.util.List;
import java.util.Random;
import java.awt.Color;

import ussr.model.Module;

/**
 * Odin controller for local communication model.
 * 
 * @author franco
 *
 */
public class OdinController extends ussr.samples.odin.OdinController {
	
	static Random rand = new Random(System.currentTimeMillis());

    /*BEGIN TO BE SET*/
    private static float pe = OdinSimulation.pe;
    private static float pne = OdinSimulation.pne;
    private static float pp = OdinSimulation.pp;
    /*END TO BE SET*/
    private static int ne = 0;
    private static int nt = 0;
    private static int e = (int)((OdinController.pe*100)-1);
    private static int p = (int)((OdinController.pp*100)-1);
    private static int id = -1;
    private static boolean idDone = false;
    private static boolean txDone = false;
	private static int Imod = 0;
    
	private static int time = 0;
	private static int activityCounter = 0; //How many modules are done before next time.
	private static float lastTime = 0;
	private static float commInterval = 2.0f;
	private static float blinkInterval = 0.5f*commInterval;

    public byte[] msg = {'n'};//non-informed module (default)
    public int color = 0;
    public byte[] channels;
    public int[] counters;
    public List<Color> lastColors;
    public boolean done = false;
    //We can also access modules, which is a protected attribute of a parent class.
    
    /**
     * Constructor.
     * 
     * @param type
     */
    public OdinController(String type) {
    	this.type = type;
    }
    
	/**
	 * Apparently, this method is called when we start the simulation.
	 * 
     * @see ussr.model.ControllerImpl#activate()
     */
    public void activate() {
    	while(module.getSimulation().isPaused()) yield();
    	delay(1000);
    	
    	List<Module> modules = module.getSimulation().getModules();
    	OdinController controller;
    	
    	//This process is done until I have selected the one propagating module.
    	//Only in the activation of the first module. Watch out, many modules are
    	//activated at the same time...
    	if(!idDone){//I randomly select the propagating module
        	int pos = 0;
        	int counter = 0;
        	while(!idDone){
                pos = rand.nextInt(modules.size());
                controller = (OdinController)(modules.get(pos)).getController(); 
                if(controller.type=="OdinMuscle"){
                	if(OdinController.Imod == 0){
                		OdinController.Imod++;
                    	controller.color = 1;//purple
                    	controller.setColor(0.5f,0.5f,1);//paint purple
    			    	controller.msg[0] = 'i';//become informed module
    			    	OdinController.id = controller.getModule().getID();
    			    	OdinController.idDone = true;
    			    	OdinController.lastTime = module.getSimulation().getTime();
                	}
                }
                yield();
        	}
        	for(int i=0; i<modules.size(); i++){
        		controller = (OdinController)(modules.get(i)).getController(); 
                if(controller.type=="OdinMuscle"){
                	counter++;
                }
        	}
        	OdinController.ne = ((int)(pne*counter));
        	OdinController.nt = counter;
        	System.out.println("ne = "+ne);
        	System.out.println("simulation");
    	}
    	
    	if(type=="OdinMuscle"){
    		channels = new byte[this.getModule().getConnectors().size()];
    		counters = new int[this.getModule().getConnectors().size()];
    		for(int x=0; x<channels.length;x++){
        		channels[x] = 'n';
        		counters[x] = 0;
        	}
    	}
    	
    	lastColors = module.getColorList();
		
    	if(type=="OdinMuscle") muscleControl();
    	if(type=="OdinBall") ballControl();
	}
    
    /**
     * This method propagates the information.
     */
    public void muscleControl() {
    	
    	List<Module> modules = module.getSimulation().getModules();
    	OdinController controller;
    	
    	int number = 0;
    	//float lastTime = module.getSimulation().getTime();
    	while(true) {
    		
			module.getSimulation().waitForPhysicsStep(false);
			
			if(!done && (OdinController.activityCounter < nt)){

				if(color==1){
					number = OdinController.p;
				}
				else{
					number = OdinController.e;
				}
				if(rand.nextInt(100) <= number){//Propagate info with probability pe
					for(int x=0; x<channels.length; x++){
						sendMessage(msg, (byte)msg.length,(byte)x);
						//the module itself may produce collisions...
						(counters[x])++;
						channels[x] = msg[0];
					}
					setColor(0,1,0);//blink green
				}
				activityCounter++;
				done = true;
			}
			
			//if((lastTime+commInterval)<module.getSimulation().getTime()){
			if(OdinController.activityCounter >= OdinController.nt){
				if((OdinController.lastTime+commInterval)<module.getSimulation().getTime()){
					if(module.getID()==OdinController.id){
				

						if(!OdinController.txDone){//Check if we transmitted to "ne" modules already...
							System.out.print("{"+OdinController.time+","+((float)OdinController.Imod/(float)OdinController.nt)+"},");
							OdinController.time++;
							if(OdinController.Imod>=ne){
								System.out.println("\nInformation Transmitted");
								OdinController.txDone = true;
								module.getSimulation().stop();
							}
						}
						
						for(int i=0; i<modules.size();i++){//Check if information was received by non-informed modules
							controller = (OdinController)(modules.get(i)).getController();
							//if(controller.color != 1){
							if(controller.type=="OdinMuscle"){
								for(int x=0; x<controller.channels.length; x++){
									if(controller.color != 1){
									if(controller.counters[x]==1 && controller.channels[x]=='i'){
										//information received by a non-informed module
										controller.color = 1;
										controller.setColor(0.5f,0.5f,1);//paint purple
										controller.lastColors = module.getColorList();
										controller.msg[0] = 'i';//become informed module
										OdinController.Imod++;
									}
									}
									controller.channels[x] = 'n';
									controller.counters[x] = 0;
								}
								controller.done = false;
							}
							//}
						}
						
						OdinController.lastTime = module.getSimulation().getTime();
						OdinController.activityCounter = 0;
					}
				}
			}
			
			if((lastTime+blinkInterval)<module.getSimulation().getTime()){
				module.setColorList(lastColors);
			}
			yield();
        }
    }
    
    public void ballControl() {
    	while(true) {
        	try {
        		Thread.sleep(10000);
            } catch (InterruptedException e) {
                throw new Error("unexpected");
            }
            yield();
    	}
    }
    
    /**
     * This handler decide what to do with the messages the modules receive.
     */
    public void handleMessage(byte[] message, int messageSize, int channel) {
    	
    	(counters[channel])++;
    	channels[channel] = message[0];

    }
}
