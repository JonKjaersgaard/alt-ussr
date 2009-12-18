package ussr.builder.simulationLoader;

import java.util.Hashtable;
import java.util.Map;

import ussr.builder.enumerations.XMLTagsUsed;
import ussr.description.setup.WorldDescription;
import ussr.description.setup.WorldDescription.CameraPosition;
import ussr.description.setup.WorldDescription.TextureDescription;
import ussr.physics.PhysicsParameters.Material;

public class SimulationSpecificationConverter {

	private Map<XMLTagsUsed,String> simulationWorldDescription,simulationPhysicsParameters;
	                              

   private Map<String,TextureDescription> containerPlaneTextureDesc = new Hashtable<String, TextureDescription>(); 


	public SimulationSpecificationConverter (Map<XMLTagsUsed,String> simulationWorldDescription, Map<XMLTagsUsed,String> simulationPhysicsParameters){
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
		return Float.parseFloat(simulationPhysicsParameters.get(XMLTagsUsed.WORLD_DAMPING_LINEAR_VELOCITY));
	}


	public float convertWorldDampingAngularVelocity(){
		return Float.parseFloat(simulationPhysicsParameters.get(XMLTagsUsed.WORLD_DAMPING_ANGULAR_VELOCITY));
	}

	public float convertPhysicsSimulationStepSize(){
		return Float.parseFloat(simulationPhysicsParameters.get(XMLTagsUsed.PHYSICS_SIMULATION_STEP_SIZE));
	}

	public boolean covertRealisticCollision(){
		return Boolean.parseBoolean(simulationPhysicsParameters.get(XMLTagsUsed.REALISTIC_COLLISION));
	}

	public float covertGravity(){
		return Float.parseFloat(simulationPhysicsParameters.get(XMLTagsUsed.GRAVITY));
	}

	public Material covertMaterial(){
		return Material.valueOf(simulationPhysicsParameters.get(XMLTagsUsed.PLANE_MATERIAL));
	}

	public boolean convertMaintainRotationalJointPositions (){
		return Boolean.parseBoolean(simulationPhysicsParameters.get(XMLTagsUsed.MAINTAIN_ROTATIONAL_JOINT_POSITIONS));
	}


	public float convertConstraintForceMix(){
		return Float.parseFloat(simulationPhysicsParameters.get(XMLTagsUsed.CONSTRAINT_FORCE_MIX));
	}

	public float convertErrorReductionParameter(){
		return Float.parseFloat(simulationPhysicsParameters.get(XMLTagsUsed.ERROR_REDUCTION_PARAMETER));
	}

	public int convertResolutionFactor(){
		return Integer.parseInt(simulationPhysicsParameters.get(XMLTagsUsed.RESOLUTION_FACTOR));
	}

	public boolean convertUseModuleEventQueue (){
		return Boolean.parseBoolean(simulationPhysicsParameters.get(XMLTagsUsed.USE_MODULE_EVENT_QUEUE));
	}
	public boolean convertSyncWithControllers (){
		return Boolean.parseBoolean(simulationPhysicsParameters.get(XMLTagsUsed.SYNC_WITH_CONTROLLERS));
	}

	public float convertPhysicsSimulationControllerStepFactor(){
		return Float.parseFloat(simulationPhysicsParameters.get(XMLTagsUsed.PHYSICS_SIMULATION_CONTROLLER_STEP_FACTOR));
	}
	
	public int convertPlaneSize(){
		return Integer.parseInt(simulationWorldDescription.get(XMLTagsUsed.PLANE_SIZE));
	}
	
	public TextureDescription covertPlaneTexture(){
		return containerPlaneTextureDesc.get(simulationWorldDescription.get(XMLTagsUsed.PLANE_TEXTURE));
	}
	
	public CameraPosition convertCameraPosition(){
		return CameraPosition.valueOf(simulationWorldDescription.get(XMLTagsUsed.CAMERA_POSITION));
	}
	
	public boolean convertTheWorldIsFlat(){
		return Boolean.parseBoolean(simulationWorldDescription.get(XMLTagsUsed.THE_WORLD_IS_FLAT));
	}
	
	public boolean convertHasClouds(){
		return Boolean.parseBoolean(simulationWorldDescription.get(XMLTagsUsed.HAS_BACKGROUND_SCENERY));
	}
	
	public boolean convertHasHeavyObstacles(){
		return Boolean.parseBoolean(simulationWorldDescription.get(XMLTagsUsed.HAS_HEAVY_OBSTACLES));
	}
	
	public boolean covertIsFrameGrabbingActive(){
		return Boolean.parseBoolean(simulationWorldDescription.get(XMLTagsUsed.IS_FRAME_GRABBING_ACTIVE));
	}
}
