package ussr.builder.labels.atronLabels;

import ussr.model.Module;
import ussr.samples.atron.ATRONController;

public abstract class ModulesLabeledAsAxles implements Axle  {
	
	public float  speedOfRotation;

	public ATRONController atronController;

	public Module module;
	
	public  abstract void turnAngle(int angle);
}
