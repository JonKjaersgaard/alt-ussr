package ussr.builder;

import java.util.ArrayList;

import ussr.samples.odin.OdinController;

/**
 * Default controller for Odin modular robot.
 * @author Konstantinas *
 */
public class OdinControllerExpandContract extends OdinController {


	
	ArrayList<Float> time = new  ArrayList<Float>(); 
	int counter = -1;
	float actuationSpeed = 0.3f;
	float timeDiffrence = 1;//between contraction and expansion	
	
	@Override
	public void activate() {
	
		counter++;
		yield();
		this.delay(1000); 
		if (module.getProperty(BuilderHelper.getModuleTypeKey()).contains("Muscle")){
			if (counter==0){
				actuateContinuous(actuationSpeed);
				time.add(getTime());
			}else{
				time.add(getTime());
				if (time.size()>2){
					if ((time.get(counter)-time.get(counter-1)>timeDiffrence)){						
						actuateContinuous(-actuationSpeed);
						time.removeAll(time);//reset
						this.counter =-1;//reset
					}
				}
			}
		}
		yield();
		this.activate();
		}	 
	    
}
