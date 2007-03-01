package ussr.physics;

import ussr.robotbuildingblocks.RotationDescription;
import ussr.robotbuildingblocks.VectorDescription;

public interface PhysicsEntity {
    public RotationDescription getRotation();
    public VectorDescription getPosition();
}
