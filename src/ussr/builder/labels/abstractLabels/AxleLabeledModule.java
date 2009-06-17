package ussr.builder.labels.abstractLabels;

import ussr.model.Module;

public abstract class AxleLabeledModule extends LabeledEntities implements Axle {

	public Module module;
	
	public abstract void turnAngle(int angle);	
}
