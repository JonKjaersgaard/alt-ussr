/**
 * Unified Simulator for Self-Reconfigurable Robots (USSR)
 * (C) University of Southern Denmark 2008
 * This software is distributed under the BSD open-source license.
 * For licensing see the file LICENCE.txt included in the root of the USSR distribution.
 */
package sunTron.samples.xmlController;


import sunTron.API.SunTronAPIImpl;
import sunTron.API.SunTronDelegateAPIImpl;
import sunTron.mobileXmlControllers.XmlControllers;
import ussr.samples.atron.ATRONController;


/**
 * A controller for a two-wheeler ATRON robot
 * 
 * @author Modular Robots @ MMMI
 *
 */
public class ATRONSimpleXmlController extends SunTronAPIImpl {
	
	int i = 0;
	private boolean justStarted = true;
//	ControllerLoopXML testController;
	/**
     * @see ussr.model.ControllerImpl#activate()
     */
    public void activate() {
        yield();
        SunTronDelegateAPIImpl atronDelegateAPI = new SunTronDelegateAPIImpl();
        ControllerLoopXML controllerLoop = new ControllerLoopXML();
        controllerLoop.setAPI(atronDelegateAPI);
            
        while(true){
        	
        	yield();
        	if (getName().equals("RearLeftWheel")){
        		if (justStarted  == true){
	        		try {
						controllerLoop = (ControllerLoopXML)XmlControllers.readXmlController("RearRightWheel.xml").readObject();
						System.out.println(getName() + ": controller restored from xml");
					} catch (Exception e) {
						System.out.println("No xml file");
					}
					justStarted = false;
					controllerLoop.setDir(-1);
					controllerLoop.setAPI(atronDelegateAPI);
        		}
        		controllerLoop.controllerLoop();
        		
        		
        	}
        	
        	
        	if (getName().equals("RearRightWheel")){
        		i++;
        		controllerLoop.setDir(1);
        		controllerLoop.controllerLoop();
        		
        		// demo serialize
        		if (i ==3500){ 
	    			try {
	    				XmlControllers.writeXmlController(controllerLoop, getName() + ".xml");
    				} catch (Exception e) {
    					e.printStackTrace();
    				}
            		
        	}
        	
        	}
        }
        
    }
}
