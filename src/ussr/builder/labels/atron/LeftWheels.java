package ussr.builder.labels.atron;

import ussr.builder.labels.Labels;
import ussr.builder.labels.ModuleLabels;
import ussr.model.Module;
import ussr.samples.atron.ATRONController;

public class LeftWheels extends ModulesLabeledAsWheels {	

	
	
	public LeftWheels(ATRONController atronController, Module module){
		super.atronController = atronController;
		super.module= module;
		}

	
	@Override
	public void rotateContinuously(float speed) {
		/*Get labels of the module*/
		Labels moduleLabels = new ModuleLabels(module);
		if(moduleLabels.has("wheel")&&moduleLabels.has("left")){
			atronController.rotateContinuous(speed);			
		}		
	}
}
