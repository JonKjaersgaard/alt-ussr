package ussr.builder.labels.atron;

import ussr.builder.labels.Labels;
import ussr.builder.labels.ModuleLabels;
import ussr.model.Module;
import ussr.samples.atron.ATRONController;

public abstract class ModulesLabeledAsWheels implements Wheel {

	public float  speedOfRotation;

	public ATRONController atronController;

	public Module module;
	
	public  abstract void rotateContinuously(float speed);
	
}
