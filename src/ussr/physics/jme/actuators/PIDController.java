package ussr.physics.jme.actuators;

import ussr.physics.PhysicsParameters;

public class PIDController {
	float Kp,Ki,Kd;
	float P,I,D;
	float oldError=0;
	
	public PIDController() {
		this.Kp = 1;
		this.Ki = 0;
		this.Kd = 0;
	}
	
	public PIDController(float Kp, float Ki, float Kd) {
		this.Kp = Kp;
		this.Ki = Ki;
		this.Kd = Kd;
	}
	
	public float getOutput(float goal, float pos) {
		float error = goal - pos;
		float dt = PhysicsParameters.get().getPhysicsSimulationStepSize();
		P =  Kp * error;
		I =+ Ki * error * dt;
		D =  Kd * (error - oldError) / dt;
		oldError = error;
		return P+I+D;
	}	  
}
