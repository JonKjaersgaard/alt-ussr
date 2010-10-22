package morphingProductionLine.abstractConveyor;

import ussr.description.geometry.VectorDescription;

public class Configuration {

    // Use smooth ATRONs or plain ATRONs for passive modules?
    public static final boolean PASSIVE_MODULE_SMOOTH_ATRON = true;
    
    // Size and position of 2D ATRON plane
    public static final int PLANE_MAX_MODULES = 200;
    public static final int PLANE_MAX_X = 8;
    public static final int PLANE_MAX_Z = 8;
    private static final VectorDescription PLANE_POSITION = new VectorDescription(-0.5f,-0.54f,0);

    // Simulation time step
    public static final float TIME_STEP_SIZE = 0.01f;
    
    // compute 3D position of robot
    public static VectorDescription getPlanePosition() {
        return PLANE_POSITION;
    }

}
