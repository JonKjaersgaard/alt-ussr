package ussr.builder.labels.atronLabels;

import ussr.builder.labels.Labels;
import ussr.builder.labels.ModuleLabels;
import ussr.model.Module;
import ussr.samples.atron.ATRONController;

public class FrontAxle extends ModulesLabeledAsAxles  {

	
	public FrontAxle( Module module){	
		super.module= module;
		}
	
	@Override
	public void turnAngle(int angle) {
		/*Get labels of the module*/
		Labels moduleLabels = new ModuleLabels(module);
		if(moduleLabels.has("axle")&& moduleLabels.has("front")){			
			ATRONController atronController   = (ATRONController)module.getController();
			atronController.rotateDegrees(angle);	
		}
		
	}

}
