package ussr.builder;

import java.util.Map;

import ussr.builder.saveLoadXML.TagsUsed;
import ussr.physics.PhysicsParameters.Material;

public class SimulationDescriptionConverter {

	private Map<TagsUsed,String> simulationWorldDescription;


	private Map<TagsUsed,String> simulationPhysicsParameters;




	public SimulationDescriptionConverter (Map<TagsUsed,String> simulationWorldDescription, Map<TagsUsed,String> simulationPhysicsParameters){
		this.simulationWorldDescription= simulationWorldDescription;
		this.simulationPhysicsParameters = simulationPhysicsParameters;
	}


	public float convertWorldDampingLinearVelocity(){
		return Float.parseFloat(simulationPhysicsParameters.get(TagsUsed.WORLD_DAMPING_LINEAR_VELOCITY));
	}


	public float convertWorldDampingAngularVelocity(){
		return Float.parseFloat(simulationPhysicsParameters.get(TagsUsed.WORLD_DAMPING_ANGULAR_VELOCITY));
	}

	public float convertPhysicsSimulationStepSize(){
		return Float.parseFloat(simulationPhysicsParameters.get(TagsUsed.PHYSICS_SIMULATION_STEP_SIZE));
	}

	public boolean covertRealisticCollision(){
		return Boolean.parseBoolean(simulationPhysicsParameters.get(TagsUsed.REALISTIC_COLLISION));
	}

	public float covertGravity(){
		return Float.parseFloat(simulationPhysicsParameters.get(TagsUsed.GRAVITY));
	}

	public Material covertMaterial(){
		return Material.valueOf(simulationPhysicsParameters.get(TagsUsed.PLANE_MATERIAL));
	}

	public boolean convertMaintainRotationalJointPositions (){
		return Boolean.parseBoolean(simulationPhysicsParameters.get(TagsUsed.MAINTAIN_ROTATIONAL_JOINT_POSITIONS));
	}


	public float convertConstraintForceMix(){
		return Float.parseFloat(simulationPhysicsParameters.get(TagsUsed.CONSTRAINT_FORCE_MIX));
	}

	public float convertErrorReductionParameter(){
		return Float.parseFloat(simulationPhysicsParameters.get(TagsUsed.ERROR_REDUCTION_PARAMETER));
	}

	public int convertResolutionFactor(){
		return Integer.parseInt(simulationPhysicsParameters.get(TagsUsed.RESOLUTION_FACTOR));
	}

	public boolean convertUseModuleEventQueue (){
		return Boolean.parseBoolean(simulationPhysicsParameters.get(TagsUsed.USE_MOUSE_EVENT_QUEUE));
	}
	public boolean convertSyncWithControllers (){
		return Boolean.parseBoolean(simulationPhysicsParameters.get(TagsUsed.SYNC_WITH_CONTROLLERS));
	}

	public float convertPhysicsSimulationControllerStepFactor(){
		return Float.parseFloat(simulationPhysicsParameters.get(TagsUsed.PHYSICS_SIMULATION_CONTROLLER_STEP_FACTOR));
	}
}
