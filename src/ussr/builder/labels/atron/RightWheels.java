package ussr.builder.labels.atron;

import ussr.builder.labels.Labels;
import ussr.builder.labels.ModuleLabels;
import ussr.model.Module;
import ussr.samples.atron.ATRONController;

public class RightWheels extends ModulesLabeledAsWheels  {

	
	public RightWheels(ATRONController atronController, Module module){
		super.atronController = atronController;
		super.module= module;
		}
	
	@Override
	public void rotateContinuously(float speed) {
		/*Get labels of the module*/
		Labels moduleLabels = new ModuleLabels(module);
		if(moduleLabels.has("wheel")&&moduleLabels.has("right")){
			atronController.rotateContinuous(speed);			
		}
	}

}
