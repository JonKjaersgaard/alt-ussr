package ussr.builder.labels.abstractLabels;

import ussr.builder.labels.Labels;
import ussr.builder.labels.ModuleLabels;
import ussr.model.Module;
import ussr.samples.atron.ATRONController;

public class ATRONAxleLabeledModule extends AxleLabeledModule  {

	
	public ATRONAxleLabeledModule( Module module){	
		super.module= module;
		}
	
	@Override
	public void turnAngle(int angle) {
		/*Get labels of the module*/
		currentModuleLabels = new ModuleLabels(module);
		if(checkExistanceLabels(currentModuleLabels,moduleLabels)){			
			ATRONController atronController   = (ATRONController)module.getController();
			atronController.rotateDegrees(angle);	
		}
		
	}
}
