package ussr.builder.labels.atron;

import ussr.model.Module;
import ussr.samples.atron.ATRONController;

public abstract class ModulesLabeledAsAxles implements Axle  {
	
	public float  speedOfRotation;

	public ATRONController atronController;

	public Module module;
	
	public  abstract void turnAngle(int angle);
}
