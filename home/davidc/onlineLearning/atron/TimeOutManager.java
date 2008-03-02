package onlineLearning.atron;

import ussr.samples.atron.IATRONAPI;

public class TimeOutManager {
	float startTime,duration;
	IATRONAPI controller;
	
	public TimeOutManager(float duration, IATRONAPI controller) {
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
