package sunTron.samples.xmlController;

import sunTron.API.SunTronDelegateAPI;
import ussr.model.Sensor;

public class ControllerLoopXML {
	private SunTronDelegateAPI atronDelegateAPI;
	private int stepCounter = 0;
	private String demoString;
	private int dir;
	private float lastProx;


 
	public void controllerLoop() {
		stepCounter++;
		String tempString = atronDelegateAPI.getName() + " step counter = "+ stepCounter;
		setDemoString(tempString);
		atronDelegateAPI.rotateContinuous(dir);
        float max_prox = Float.NEGATIVE_INFINITY;
        for(Sensor s: atronDelegateAPI.getSensors()) {
            if(s.getName().startsWith("Proximity")) {
                float v = s.readValue();
                max_prox = Math.max(max_prox, v);
            }
        }
        if(Math.abs(lastProx-max_prox)>0.01) {
            System.out.println("Proximity "+atronDelegateAPI.getName()+" max = "+max_prox);
            lastProx = max_prox; 
        }

	}
	public void setDemoString(String s){
		demoString = s;
		System.out.println(demoString);
	}
	public String getDemoString(){
		return demoString;
	}
	public void setAPI(SunTronDelegateAPI atronDelegateAPI2) {
		// TODO Auto-generated method stub
		atronDelegateAPI = atronDelegateAPI2;
	}
	public int getStepCounter() {
		return stepCounter;
	}
	public void setStepCounter(int stepCount) {
		this.stepCounter = stepCount;
	}
	public int getDir() {
		return dir;
	}
	public void setDir(int dir) {
		this.dir = dir;
	}
	public float getLastProx() {
		return lastProx;
	}
	public void setLastProx(float lastProx) {
		this.lastProx = lastProx;
	}





}
