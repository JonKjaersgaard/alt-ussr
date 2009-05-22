package ussr.builder;

import java.util.ArrayList;

import ussr.samples.odin.OdinController;

/**
 * Default controller for Odin modular robot.
 * @author Konstantinas *
 */
public class OdinControllerExpandContract extends OdinController {


	
	ArrayList<Float> timeValues = new  ArrayList<Float>(); 
	int counter = -1;
	float actuationSpeed = 0.3f;
	float timeDiffrence = 1;//between contraction and expansion	
	
	@Override
	public void activate() {
	
		while (true){
			counter++;		
			this.delay(1000); 
			if (module.getProperty(BuilderHelper.getModuleTypeKey()).contains("Muscle")){
				if (counter==0){
					actuateContinuous(actuationSpeed);
					timeValues.add(getTime());
				}else{
					timeValues.add(getTime());
					if (timeValues.size()==2){
						if ((timeValues.get(counter)-timeValues.get(counter-1)>timeDiffrence)){						
							actuateContinuous(-actuationSpeed);
							timeValues.removeAll(timeValues);//reset
							this.counter =-1;//reset
						}
					}
				}
			}	
		}}
	    
}
