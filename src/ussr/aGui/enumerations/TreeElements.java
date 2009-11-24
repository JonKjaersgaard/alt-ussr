package ussr.aGui.enumerations;

/**
 * @author Konstantinas
 *
 */
public enum TreeElements {
//First Level Hierarchy	
Simulation,

  //Second Level Hierarchy
  //Often used
  Physics_Simulation_Step_Size,
  Resolution_Factor,

  //Second Level Hierarchy
  Robots,
     //Third Level Hierarchy
     Robot,
        //Fourth Level Hierarchy
        Type,
        Morphology_Location,
        Controller_Location,

  //Second Level Hierarchy
  World_Description, 
      //Third Level Hierarchy
      Plane_Size,
      Plane_Texture,
      Camera_Position,
      The_World_Is_Flat,
      Has_Background_Scenery,
      Has_Heavy_Obstacles,
      Is_Frame_Grabbing_Active,

  //Second Level Hierarchy    
  Physics_Parameters,
      //Third Level Hierarchy
      Damping,
         //Fourth Level Hierarchy
         Linear_Velocity,
         Angular_Velocity,
      //Third Level Hierarchy
      Realistic_Collision,
      Gravity,
      Constraint_Force_Mix,
      Error_Reduction_Parameter,
      Use_Mouse_Event_Queue,
      Synchronize_With_Controllers,
      Physics_Simulation_Controller_Step_Factor,
}
