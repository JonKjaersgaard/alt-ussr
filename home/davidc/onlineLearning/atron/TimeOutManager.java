package onlineLearning.atron;

import ussr.samples.atron.ATRONController;

public class TimeOutManager {
	float startTime,duration;
	ATRONController controller;
	
	public TimeOutManager(float duration, ATRONController controller) {
		this.controller = controller;
		this.duration = duration;
		reset();
	}
	public void reset() {
		startTime = controller.getTime();
	}
	public boolean isTimeout() {
		return (startTime+duration)<controller.getTime();		
	}
}
