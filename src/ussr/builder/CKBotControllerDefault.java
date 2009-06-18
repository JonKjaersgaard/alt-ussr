package ussr.builder;

import ussr.samples.odin.OdinController;

/**
 * Default controller assigned to CKBot modules in simulation environment.
 * So far does nothing. 
 * @author Konstantinas
 */
public class CKBotControllerDefault extends OdinController {

	
	@Override
	public void activate() {
	yield();
	}
	
	
	
	
}
