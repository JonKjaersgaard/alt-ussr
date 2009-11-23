package ussr.builder.enumerations;

public enum XMLTagsUsed {
   /*For description of modular robot morphology(shape)*/
   MODULES,          /*First tag(by hierarchy), indicating that file keeps information about all modules in the morphology of modular robot*/
   MODULE,           /*Second tag, separating each module description*/
   ID,               /*Third tag, global-unique ID of the module in simulation environment*/
   TYPE,             /* Type of module, for instance ATRON, OdinBall and so on.*/
   NAME,             /*Random name of module. Format: "Type of module" + "random integer"*/
   ROTATION,          /*Module rotation in simulation environment. As x,y and z values. However is not precise enough to use.*/
   ROTATION_QUATERNION, /*Rotation in the form of quaternion. Is precise enough and heavily used in builder.*/
   POSITION,            /*Module position as x,y,z values in 3D space*/
   POSITION_VECTOR,
   COMPONENTS,
   COLORS_COMPONENTS,
   CONNECTORS,
   COLORS_CONNECTORS,
   LABELS_MODULE,
   LABELS_CONNECTORS,
   
   
   
   /*For description of simulation*/
   SIMULATION,
   
   ROBOT,
   NUMBER_OF_MODULES,
   CONTROLLER_LOCATION,
   MORPHOLOGY_LOCATION,
   
   WORLD_DESCRIPTION,
   PLANE_SIZE,
   PLANE_TEXTURE,
   CAMERA_POSITION,   
   THE_WORLD_IS_FLAT,
   HAS_BACKGROUND_SCENERY,
   HAS_HEAVY_OBSTACLES,
   IS_FRAME_GRABBING_ACTIVE,
   BIG_OBSTACLES,
   
   PHYSICS_PARAMETERS,
   WORLD_DAMPING_LINEAR_VELOCITY,
   WORLD_DAMPING_ANGULAR_VELOCITY,
   PHYSICS_SIMULATION_STEP_SIZE,
   REALISTIC_COLLISION,
   GRAVITY,
   PLANE_MATERIAL,
   MAINTAIN_ROTATIONAL_JOINT_POSITIONS,
  /*Not supported yet*/ HAS_MECHANICAL_CONNECTOR_SPRINGINESS,
  /*Not supported yet*/ MECHANICAL_CONNECTOR_CONSTANT,
  /*Not supported yet*/MECHANICAL_CONNECTOR_DAMPING,
   CONSTRAINT_FORCE_MIX,
   ERROR_REDUCTION_PARAMETER,
   RESOLUTION_FACTOR,
   USE_MOUSE_EVENT_QUEUE,
   SYNC_WITH_CONTROLLERS,
   PHYSICS_SIMULATION_CONTROLLER_STEP_FACTOR;
   
}
