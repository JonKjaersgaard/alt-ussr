package ussr.builder;

import java.util.Hashtable;
import java.util.Map;

import ussr.builder.saveLoadXML.TagsUsed;
import ussr.description.setup.WorldDescription;
import ussr.description.setup.WorldDescription.CameraPosition;
import ussr.description.setup.WorldDescription.TextureDescription;
import ussr.physics.PhysicsParameters.Material;

public class SimulationDescriptionConverter {

	private Map<TagsUsed,String> simulationWorldDescription;


	private Map<TagsUsed,String> simulationPhysicsParameters;

   private Map<String,TextureDescription> containerPlaneTextureDesc = new Hashtable<String, TextureDescription>(); 


	public SimulationDescriptionConverter (Map<TagsUsed,String> simulationWorldDescription, Map<TagsUsed,String> simulationPhysicsParameters){
		this.simulationWorldDescription= simulationWorldDescription;
		this.simulationPhysicsParameters = simulationPhysicsParameters;
		populateContainerTextureDesc ();
	}


	public void populateContainerTextureDesc (){	     
	    containerPlaneTextureDesc.put(WorldDescription.GRASS_TEXTURE.getFileName(), WorldDescription.GRASS_TEXTURE);
	    containerPlaneTextureDesc.put(WorldDescription.GREY_GRID_TEXTURE.getFileName(), WorldDescription.GREY_GRID_TEXTURE);
	    containerPlaneTextureDesc.put(WorldDescription.MARS_TEXTURE.getFileName(), WorldDescription.MARS_TEXTURE);
	    containerPlaneTextureDesc.put(WorldDescription.WHITE_GRID_TEXTURE.getFileName(), WorldDescription.WHITE_GRID_TEXTURE);
	    containerPlaneTextureDesc.put(WorldDescription.WHITE_TEXTURE.getFileName(), WorldDescription.WHITE_TEXTURE);		
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
	
	public int convertPlaneSize(){
		return Integer.parseInt(simulationWorldDescription.get(TagsUsed.PLANE_SIZE));
	}
	
	public TextureDescription covertPlaneTexture(){
		return containerPlaneTextureDesc.get(simulationWorldDescription.get(TagsUsed.PLANE_TEXTURE));
	}
	
	public CameraPosition convertCameraPosition(){
		return CameraPosition.valueOf(simulationWorldDescription.get(TagsUsed.CAMERA_POSITION));
	}
	
	public boolean convertTheWorldIsFlat(){
		return Boolean.parseBoolean(simulationWorldDescription.get(TagsUsed.THE_WORLD_IS_FLAT));
	}
	
	public boolean convertHasClouds(){
		return Boolean.parseBoolean(simulationWorldDescription.get(TagsUsed.HAS_BACKGROUND_SCENERY));
	}
}
