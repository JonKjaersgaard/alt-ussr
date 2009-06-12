package ussr.builder.labels.atron;

import ussr.builder.labels.Labels;
import ussr.builder.labels.ModuleLabels;
import ussr.model.Module;
import ussr.samples.atron.ATRONController;

public class RearAxle extends ModulesLabeledAsAxles  {

	
	public RearAxle(ATRONController atronController, Module module){
		super.atronController = atronController;
		super.module= module;
		}
	
	@Override
	public void turnAngle(int angle) {
		/*Get labels of the module*/
		Labels moduleLabels = new ModuleLabels(module);
		if(moduleLabels.has("axle") && moduleLabels.has("rear")){
			atronController.rotateDegrees(angle);			
		}
		
	}

}
