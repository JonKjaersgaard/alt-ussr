package ussr.builder;

import java.util.ArrayList;

import ussr.model.Module;
import ussr.samples.odin.OdinController;

/**
 * Default controller for Odin modular robot, which is expanding and contracting 
 * OdinMuscles in the morphology of the modular robot. Solution is dependent on the time,
 * but not on the physical dimensions of the OdinMuscle.
 * @author Konstantinas *
 */
public class OdinControllerDefault extends OdinController {

	ArrayList<Float> timeValues = new  ArrayList<Float>(); 
	int counter = -1;
	/*Following values were found empirically*/
	float actuationSpeed = 0.3f;//for about 100% contraction-expansion
	float timeDiffrence = 1;//between contraction and expansion(for about 100% contraction-expansion)
	//float actuationSpeed = 0.1f;//for about 50% contraction-expansion	
	//float timeDiffrence = 0.0000001f;//for about 50% contraction-expansion
	
	@Override
	public void activate() {
		//expand();
	 //expandContract();
	}
	
	private void expandContract() {
		yield();
		String moduleType = module.getProperty(BuilderHelper.getModuleTypeKey());
		while (true){
		counter++;		
		this.delay(1000); 
		if (moduleType.contains("Muscle")){
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
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
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
