package ussr.aGui.tabs.simulation.enumerations;


import ussr.physics.PhysicsParameters;

/**
 * @author Konstantinas
 * TODO DECIDE IF NEEDED
 */
public enum PhysicsParametersDefault {
	
	
	WORLD_DAMPING_LINEAR_VELOCITY(""+PhysicsParameters.get().getWorldDampingLinearVelocity(),Type.FLOAT),
	WORLD_DAMPING_ANGULAR_VELOCITY(""+PhysicsParameters.get().getWorldDampingAngularVelocity(),Type.FLOAT),
	REALISTIC_COLLISION(""+PhysicsParameters.get().getRealisticCollision(),Type.BOOLEAN),
	GRAVITY(""+PhysicsParameters.get().getGravity(),Type.FLOAT),
	PLANE_MATERIAL(""+PhysicsParameters.get().getPlaneMaterial(),Type.MATERIAL),
	MAINTAIN_ROTATIONAL_JOINT_POSITIONS(""+PhysicsParameters.get().getMaintainRotationalJointPositions(),Type.BOOLEAN),
	CONSTRAINT_FORCE_MIXING(""+PhysicsParameters.get().getConstraintForceMix(),Type.FLOAT),
	ERROR_REDUCTION_PARAMETER(""+PhysicsParameters.get().getErrorReductionParameter(),Type.FLOAT),
	USE_MODULE_EVENT_QUEUE(""+PhysicsParameters.get().useModuleEventQueue(),Type.BOOLEAN),
	SYNCHRONIZE_WITH_CONTROLLERS(""+PhysicsParameters.get().syncWithControllers(),Type.BOOLEAN),
	PHYSICS_SIMULATION_CONTROLLER_STEP_FACTOR(""+PhysicsParameters.get().getPhysicsSimulationControllerStepFactor(),Type.FLOAT),
	;
	
	private enum Type{FLOAT,BOOLEAN,MATERIAL};
	
	
	
	
	private String defaultValue;
	
	
	private Type type;
	
	

	public Type getType() {
		return type;
	}


	PhysicsParametersDefault(String defaultValue,Type type){
		this.defaultValue = defaultValue;
		this.type =type;
		
		
	} 
	
	
	public String getDefaultValue() {
		return defaultValue;
	}
	
	
	public Object covert(boolean defaultValue){
		String valueToConvert ="";
		if (defaultValue){
			valueToConvert = this.getDefaultValue();
		}
		//Object object = null;
		float object = 0.0f;
		switch (this.type){
		case FLOAT:
			object = Float.parseFloat(valueToConvert);
			break;
		}
		
		return object;
	}
		
	
	public void setDefaultPhysicsParaemeters(){

		PhysicsParameters.get().setWorldDampingLinearVelocity(Float.parseFloat(WORLD_DAMPING_LINEAR_VELOCITY.getDefaultValue()));
		PhysicsParameters.get().setWorldDampingAngularVelocity(Float.parseFloat(WORLD_DAMPING_ANGULAR_VELOCITY.getDefaultValue()));
		PhysicsParameters.get().setRealisticCollision(Boolean.parseBoolean(REALISTIC_COLLISION.getDefaultValue()));
		PhysicsParameters.get().setGravity(Float.parseFloat(GRAVITY.getDefaultValue()));
		PhysicsParameters.get().setPlaneMaterial(PhysicsParameters.Material.valueOf(PLANE_MATERIAL.getDefaultValue()));
		PhysicsParameters.get().setMaintainRotationalJointPositions(Boolean.parseBoolean(MAINTAIN_ROTATIONAL_JOINT_POSITIONS.getDefaultValue()));
		PhysicsParameters.get().setConstraintForceMix(Float.parseFloat(CONSTRAINT_FORCE_MIXING.getDefaultValue()));
		PhysicsParameters.get().setErrorReductionParameter(Float.parseFloat(ERROR_REDUCTION_PARAMETER.getDefaultValue()));
		PhysicsParameters.get().setUseModuleEventQueue(Boolean.parseBoolean(USE_MODULE_EVENT_QUEUE.getDefaultValue()));
		PhysicsParameters.get().setSyncWithControllers(Boolean.parseBoolean(SYNCHRONIZE_WITH_CONTROLLERS.getDefaultValue()));
		PhysicsParameters.get().setPhysicsSimulationControllerStepFactor(Float.parseFloat(PHYSICS_SIMULATION_CONTROLLER_STEP_FACTOR.getDefaultValue()));
	}
	

}


