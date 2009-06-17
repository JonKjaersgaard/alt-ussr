package ussr.builder.labels.abstractLabels;

import java.util.ArrayList;

import ussr.builder.labels.Labels;
import ussr.builder.labels.ModuleLabels;
import ussr.model.Module;
import ussr.samples.atron.ATRONController;

public class ATRONWheelLabeledModule extends WheelLabeledModule {

  
	public ATRONWheelLabeledModule(Module module){
		super.module = module;
	} 

	@Override
	public void rotateContinuously(float speed) {
		/*Get labels of the module*/
		currentModuleLabels = new ModuleLabels(module);		
		if (checkExistanceLabels(currentModuleLabels,moduleLabels)){
			ATRONController atronController   = (ATRONController)module.getController();
			atronController.rotateContinuous(speed);
		}
		
	}

	@Override
	public void stop() {
		/*Get labels of the module*/
		currentModuleLabels = new ModuleLabels(module);
		if(checkExistanceLabels(currentModuleLabels,moduleLabels)){
			ATRONController atronController   = (ATRONController)module.getController();
			atronController.rotateDegrees(1);
			atronController.rotateDegrees(-1);
		}
		
	}
}
