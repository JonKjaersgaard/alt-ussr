package ussr.builder.labels.atronLabels;

import java.util.ArrayList;

import ussr.builder.labels.Labels;
import ussr.builder.labels.ModuleLabels;
import ussr.model.Module;
import ussr.samples.atron.ATRONController;

public class LeftWheels extends ModulesLabeledAsWheels {	
     	
	
	public LeftWheels(Module module){		
		super.module= module;
		addLabel(wheelLabel);
		addLabel("left");		
		}	
	
	@Override
	public void rotateContinuously(float speed) {	
		
		/*Get labels of the module*/
		Labels currentModuleLabels = new ModuleLabels(module);		
		if (checkExistanceLabels(currentModuleLabels,moduleLabels)){
			rotateWheelContinuously(speed);
		}			
	}

	@Override
	public void stop() {
		/*Get labels of the module*/
		Labels currentModuleLabels = new ModuleLabels(module);
		if(checkExistanceLabels(currentModuleLabels,moduleLabels)){
			stopWheel();
		}
		
	}
	
	
	

	
}
