package ussr.builder.labels.atronLabels;

import ussr.model.Module;

public abstract class ModulesLabeledAsWheels implements Wheel {

	public float  speedOfRotation;

	public Module module;
	
	public  abstract void rotateContinuously(float speed);
	
}
