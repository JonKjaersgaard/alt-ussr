package ussr.builder.labels.abstractLabels;


public abstract class WheelLabeledModule extends LabeledEntities implements Wheel {

	/*@Override
	public void addLabel(String label) {
		
	}*/
	
	public abstract void rotateContinuously(float speed);

	public abstract void stop();

}
