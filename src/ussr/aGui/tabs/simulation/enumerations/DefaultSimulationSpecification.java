package ussr.aGui.tabs.simulation.enumerations;


import ussr.builder.saveLoadXML.XMLTagsUsed;
import ussr.builder.simulationLoader.SimulationSpecification;
import ussr.description.setup.WorldDescription;
import ussr.physics.PhysicsParameters;

public enum DefaultSimulationSpecification {

	/*Physics Parameters*/ 
	PHYSICS_SIMULATION_STEP_SIZE(SimulationParameterType.PHYSICS_PARAMETER,XMLTagsUsed.PHYSICS_SIMULATION_STEP_SIZE, PhysicsParameters.get().getPhysicsSimulationStepSize()+""),
	RESOLUTION_FACTOR(SimulationParameterType.PHYSICS_PARAMETER,XMLTagsUsed.RESOLUTION_FACTOR,PhysicsParameters.get().getResolutionFactor()+""),

	/*World Description Values*/
	PLANE_SIZE(SimulationParameterType.WORLD_DECSRIPTION_VALUE,XMLTagsUsed.PLANE_SIZE,getDefaultWorld().getPlaneSize()+""),
	PLANE_TEXTURE(SimulationParameterType.WORLD_DECSRIPTION_VALUE,XMLTagsUsed.PLANE_TEXTURE,getDefaultWorld().getPlaneTexture().getFileName() ),
	CAMERA_POSITION(SimulationParameterType.WORLD_DECSRIPTION_VALUE,XMLTagsUsed.CAMERA_POSITION,getDefaultWorld().getCameraPosition().toString()),
	THE_WORLD_IS_FLAT(SimulationParameterType.WORLD_DECSRIPTION_VALUE,XMLTagsUsed.THE_WORLD_IS_FLAT,getDefaultWorld().theWorldIsFlat()+""),
	HAS_BACKGROUND_SCENERY(SimulationParameterType.WORLD_DECSRIPTION_VALUE,XMLTagsUsed.HAS_BACKGROUND_SCENERY,getDefaultWorld().hasBackgroundScenery()+""),
	HAS_HEAVY_OBSTACLES(SimulationParameterType.WORLD_DECSRIPTION_VALUE,XMLTagsUsed.HAS_HEAVY_OBSTACLES,getDefaultWorld().hasHeavyObstacles()+""),
	IS_FRAME_GRABBING_ACTIVE(SimulationParameterType.WORLD_DECSRIPTION_VALUE,XMLTagsUsed.IS_FRAME_GRABBING_ACTIVE,getDefaultWorld().getIsFrameGrabbingActive()+""),

	/*Physics Parameters*/ 

	DAMPING_LINEAR_VELOCITY(SimulationParameterType.PHYSICS_PARAMETER,XMLTagsUsed.WORLD_DAMPING_LINEAR_VELOCITY,PhysicsParameters.get().getWorldDampingLinearVelocity()+""),
	DAMPING_ANGULAR_VELOCITY(SimulationParameterType.PHYSICS_PARAMETER,XMLTagsUsed.WORLD_DAMPING_ANGULAR_VELOCITY,PhysicsParameters.get().getWorldDampingLinearVelocity()+""),	            
	REALISTIC_COLLISION(SimulationParameterType.PHYSICS_PARAMETER,XMLTagsUsed.REALISTIC_COLLISION,PhysicsParameters.get().getRealisticCollision()+""),
	GRAVITY(SimulationParameterType.PHYSICS_PARAMETER,XMLTagsUsed.GRAVITY,PhysicsParameters.get().getGravity()+""),
	PLANE_MATERIAL(SimulationParameterType.PHYSICS_PARAMETER,XMLTagsUsed.PLANE_MATERIAL,PhysicsParameters.get().getPlaneMaterial().toString()),
	MAINTAIN_ROTATIONAL_JOINT_POSITIONS(SimulationParameterType.PHYSICS_PARAMETER,XMLTagsUsed.MAINTAIN_ROTATIONAL_JOINT_POSITIONS,PhysicsParameters.get().getMaintainRotationalJointPositions()+""),
	CONSTRAINT_FORCE_MIXING(SimulationParameterType.PHYSICS_PARAMETER,XMLTagsUsed.CONSTRAINT_FORCE_MIX,PhysicsParameters.get().getConstraintForceMix()+""),
	ERROR_REDUCTION_PARAMETER(SimulationParameterType.PHYSICS_PARAMETER,XMLTagsUsed.ERROR_REDUCTION_PARAMETER,PhysicsParameters.get().getErrorReductionParameter()+""),
	USE_MODULE_EVENT_QUEUE(SimulationParameterType.PHYSICS_PARAMETER,XMLTagsUsed.USE_MODULE_EVENT_QUEUE,PhysicsParameters.get().useModuleEventQueue()+""),
	SYHCNRONIZE_WITH_CONTROLLERS(SimulationParameterType.PHYSICS_PARAMETER,XMLTagsUsed.SYNC_WITH_CONTROLLERS,PhysicsParameters.get().syncWithControllers()+""),
	PHYSICS_SIMULATION_CONTROLLER_STEP_FACTOR(SimulationParameterType.PHYSICS_PARAMETER,XMLTagsUsed.PHYSICS_SIMULATION_CONTROLLER_STEP_FACTOR,PhysicsParameters.get().getPhysicsSimulationControllerStepFactor()+"")
	;


	public enum SimulationParameterType{
     PHYSICS_PARAMETER,
     WORLD_DECSRIPTION_VALUE;
	}

	private XMLTagsUsed xmlTagUsed;
    private SimulationParameterType simulationParameterType;
    
	


	private String defaultValue;

	

	DefaultSimulationSpecification(SimulationParameterType simulationParameterType,XMLTagsUsed xmlTagUsed,String defaultValue){
		this.simulationParameterType = simulationParameterType;
		this.xmlTagUsed = xmlTagUsed;
		this.defaultValue = defaultValue;
	}

	private static WorldDescription getDefaultWorld(){
		return new WorldDescription();
	} 
	
	public String getDefaultValue() {
		return defaultValue;
	}
	
	public XMLTagsUsed getXmlTagUsed() {
		return xmlTagUsed;
	}
	
	public SimulationParameterType getSimulationParameterType() {
		return simulationParameterType;
	}


	public static SimulationSpecification getDefaultUserSimulationSpecification(){
		SimulationSpecification simulationSpec = new SimulationSpecification();
		//values().length
		for (int enumNr=0;enumNr<values().length;enumNr++){
			if(values()[enumNr].getDefaultValue()!=null){
				//simulationSpec.GET
			}
		}



		return simulationSpec;
	}
}
