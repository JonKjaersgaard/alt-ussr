package morphingProductionLine.simpleExample;

import ussr.description.geometry.RotationDescription;
import ussr.description.geometry.VectorDescription;

public class Configuration {

    // Size and position of the box being transported
    public static final VectorDescription BOX_SIZE = new VectorDescription(0.25f,0.06f,0.15f);
    public static final float BOX_MASS = 10f;
    private static final VectorDescription BOX_INITIAL_POSITION = new VectorDescription(0.1f-0.4f,-0.25f,0.4f);
    private static final RotationDescription BOX_INITIAL_ROTATION = new RotationDescription(0,0,0);
    
    public static VectorDescription boxInitialPosition() {
        return BOX_INITIAL_POSITION;
    }

    public static RotationDescription boxInitialRotation() {
        return BOX_INITIAL_ROTATION;
    }
}
