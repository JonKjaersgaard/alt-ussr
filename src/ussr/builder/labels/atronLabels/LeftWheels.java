package ussr.builder.labels.atronLabels;

import ussr.builder.labels.Labels;
import ussr.builder.labels.ModuleLabels;
import ussr.model.Controller;
import ussr.model.Module;
import ussr.samples.atron.ATRONController;

public class LeftWheels extends ModulesLabeledAsWheels {	
	
	public LeftWheels( Module module){		
		super.module= module;
		}
	
	@Override
	public void rotateContinuously(float speed) {
		/*Get labels of the module*/
		Labels moduleLabels = new ModuleLabels(module);
		if(moduleLabels.has("wheel")&&moduleLabels.has("left")){
			ATRONController atronController   = (ATRONController)module.getController();
			atronController.rotateContinuous(speed);			
		}		
	}

	@Override
	public void stop() {
		/*Get labels of the module*/
		Labels moduleLabels = new ModuleLabels(module);
		if(moduleLabels.has("wheel")&&moduleLabels.has("left")){
			ATRONController atronController   = (ATRONController)module.getController();
			atronController.rotateDegrees(1);			
		}
		
	}
}
