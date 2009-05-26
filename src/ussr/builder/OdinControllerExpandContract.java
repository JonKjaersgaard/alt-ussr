package ussr.builder;

import java.util.ArrayList;

import ussr.samples.odin.OdinController;

/**
 * Default controller for Odin modular robot.
 * @author Konstantinas 
 */
public class OdinControllerExpandContract extends OdinController {


	
	ArrayList<Float> timeValues = new  ArrayList<Float>(); 
	int counter = -1;
	float actuationSpeed = 0.3f;
	float timeDiffrence = 1;//between contraction and expansion	
	
	@Override
	public void activate() {
	
		expand();
	}
	
	private void expand(){
		yield();
		
		while (true){
			this.delay(1000); 
		if (module.getProperty(BuilderHelper.getModuleTypeKey()).contains("Muscle")){
	
				actuateContinuous(actuationSpeed);			
		
			}
		yield(); 
		}
		
	}
	
	
	    
}
