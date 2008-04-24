/**
 * Unified Simulator for Self-Reconfigurable Robots (USSR)
 * (C) University of Southern Denmark 2008
 * This software is distributed under the BSD open-source license.
 * For licensing see the file LICENCE.txt included in the root of the USSR distribution.
 */
package atron.samples.xmlController;


import atron.delegate.ATRONDelegateAPI;
import atron.mobileXmlControllers.XmlControllers;
import ussr.samples.atron.ATRONController;


/**
 * A controller for a two-wheeler ATRON robot
 * 
 * @author Modular Robots @ MMMI
 *
 */
public class ATRONSimpleXmlController extends ATRONController {
	
	int i = 0;
	private boolean justStarted = true;
//	ControllerLoopXML testController;
	/**
     * @see ussr.model.ControllerImpl#activate()
     */
    public void activate() {
        yield();
        ATRONDelegateAPI atronDelegateAPI = new ATRONDelegateAPI(module);
        ControllerLoopXML controllerLoop = new ControllerLoopXML();
        controllerLoop.setAPI(atronDelegateAPI);
            
        while(true){
        	
        	yield();
        	if (module.getProperty("name").equals("RearLeftWheel")){
        		if (justStarted  == true){
	        		try {
						controllerLoop = (ControllerLoopXML)XmlControllers.readXmlController("RearRightWheel.xml").readObject();
						System.out.println(module.getProperty("name") + ": controller restored from xml");
					} catch (Exception e) {
						System.out.println("No xml file");
					}
					justStarted = false;
					controllerLoop.setDir(-1);
					controllerLoop.setAPI(atronDelegateAPI);
        		}
        		controllerLoop.controllerLoop();
        		
        		
        	}
        	
        	
        	if (module.getProperty("name").equals("RearRightWheel")){
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
