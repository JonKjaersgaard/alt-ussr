/**
 * Unified Simulator for Self-Reconfigurable Robots (USSR)
 * (C) University of Southern Denmark 2008
 * This software is distributed under the BSD open-source license.
 * For licensing see the file LICENCE.txt included in the root of the USSR distribution.
 */
package communication.local;

import java.awt.Color;
import java.util.List;
import java.util.Random;

import ussr.model.Module;

/**
 * A sample controller for the ATRON
 * 
 * @author Modular Robots @ MMMI
 *
 */
public class ATRONController extends ussr.samples.atron.ATRONController {
	
	static Random rand = new Random(System.currentTimeMillis());

    /*BEGIN TO BE SET*/
    static float pe = 0.1f; //0 to 1, probability of modules sending information out.
    static float pne = 1.0f; //0 to 1, proportion of modules the information is transmitted to.
    static float pp = 1.0f; //0 to 1, probability of Imods modules sending information out.
    //For simulation approach 1, set pp to pe and for simulation approach 2 set it
    //to 1 or high number (e.g., 0.9).
    /*END TO BE SET*/
    static int ne = 0;
    static int nt = 0;
    static int e = (int)((pe*100)-1);
    static int p = (int)((pp*100)-1);
    static int id = -1;
    static boolean idDone = false;
    static boolean txDone = false;
    static int Imod = 0;
    
    static int time = 0;
    static int activityCounter = 0; //How many modules are done before next time.
    static float lastTime = 0;
    static float commInterval = 0.1f;
    static float blinkInterval = 0.5f*commInterval;
    
    public byte[] msg = {'n'};//non-informed module (default)
    public int color = 0;
    public byte[] channels;
    public int[] counters;
    public List<Color> lastColors;
    public boolean done = false;
    //We can also access modules, which is a protected attribute of a parent class.

    
    /**
     * This method differs from the Odin Controller's one, because we have
     * just one type of module in ATRON. Therefore, the infinite loop begins
     * here and not at another methods (muscleControl and so on...).
     * 
     * @see ussr.model.ControllerImpl#activate()
     */
    public void activate(){
    	while(module.getSimulation().isPaused()) yield();
    	delay(1000);
    	/***************************************/
    	
    	List<Module> modules = module.getSimulation().getModules();
    	ATRONController controller;
    	
    	//This process is done until I have selected the one propagating module.
    	//Only in the activation of the first module. Watch out, many modules are
    	//activated at the same time...
    	if(!idDone){//I randomly select the propagating module
        	int pos = 0;
        	int counter = 0;
        	while(!idDone){
        		//List<Module> modules = module.getSimulation().getModules();
                pos = rand.nextInt(modules.size());
                controller = (ATRONController)(modules.get(pos)).getController();
                if(ATRONController.Imod == 0){
                	ATRONController.Imod++;
                	controller.color = 1;//purple
                	controller.setColor(0.5f,0.5f,1);//paint purple
                	controller.msg[0] = 'i';//become informed module
                	ATRONController.id = controller.getModule().getID();
                	ATRONController.idDone = true;
                	ATRONController.lastTime = module.getSimulation().getTime();
                }
                yield();
            }
        	counter = modules.size();
        	ATRONController.ne = ((int)(pne*counter));
        	ATRONController.nt = counter;
        	System.out.println("ne = "+ne);
        	System.out.println("simulation");
    	}
    	
    	channels = new byte[this.getModule().getConnectors().size()];
    	counters = new int[this.getModule().getConnectors().size()];
    	for(int x=0; x<channels.length;x++){
    		channels[x] = 'n';
    		counters[x] = 0;
    	}
    	
    	lastColors = module.getColorList();

    	/***************************************/
    	
    	//float lastTime = module.getSimulation().getTime();
    	int number = 0;
    	while(true) {
    		
			module.getSimulation().waitForPhysicsStep(false);
			
			if(!done && (ATRONController.activityCounter < nt)){

				if(color==1){
					number = p;
				}
				else{
					number = e;
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
			if(ATRONController.activityCounter >= ATRONController.nt){
				if((ATRONController.lastTime+commInterval)<module.getSimulation().getTime()){
					if(module.getID()==ATRONController.id){
						
						if(!ATRONController.txDone){//Check if we transmitted to "ne" modules already...
							//System.out.print("{"+ATRONController.time+","+ATRONController.Imod+","+((float)ATRONController.Imod/(float)ATRONController.nt)+"},");
							System.out.print("{"+ATRONController.time+","+((float)ATRONController.Imod/(float)ATRONController.nt)+"},");
							//System.out.println("\n(counter,channel) = ");
							//for(int j=0; j<counters.length; j++){
							//	System.out.print("("+counters[j]+","+(char)channels[j]+")"+",");
							//}
							ATRONController.time++;
							if(ATRONController.Imod>=ne){
								System.out.println("\nInformation Transmitted");
								ATRONController.txDone = true;
							}
						}
						
						for(int i=0; i<modules.size();i++){//Check if information was received by a non-informed module
							controller = (ATRONController)(modules.get(i)).getController();
							//if(controller.color != 1){
								for(int x=0; x<controller.channels.length; x++){
									if(controller.color != 1){
									if(controller.counters[x]==1 && controller.channels[x]=='i'){
										//information received by a non-informed module
										controller.color = 1;
										controller.setColor(0.5f,0.5f,1);//paint purple
										controller.lastColors = module.getColorList();
										controller.msg[0] = 'i';//become informed module
										ATRONController.Imod++;
									}
									}
									controller.channels[x] = 'n';
									controller.counters[x] = 0;
								}
							//}
							controller.done = false;
						}
						
						ATRONController.lastTime = module.getSimulation().getTime();
						ATRONController.activityCounter = 0;
					}
				}
			}
			
			if((lastTime+blinkInterval)<module.getSimulation().getTime()){
			//if(activityCounter >= (0.5*nt)){
				module.setColorList(lastColors);
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
    
    /**
     * 
     * This method should be on the ATRONController class, which I will not
     * modify.
     * 
     * @param r
     * @param g
     * @param b
     */
    public void setColor(float r, float g, float b) {
    	module.setColor(new Color(r,g,b));
    }

}
	
