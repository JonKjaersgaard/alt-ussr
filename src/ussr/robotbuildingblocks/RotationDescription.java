/**
 * 
 */
package ussr.robotbuildingblocks;

import com.jme.math.Matrix3f;
import com.jme.math.Quaternion;

/**
 * @author ups
 *
 * TODO Write a nice and user-friendly comment here
 * 
 */
public class RotationDescription {
    private Quaternion rotation = new Quaternion();
    public RotationDescription(float x, float y, float z) {
        rotation.fromAngles(x, y, z);
    }
    public RotationDescription(VectorDescription v1, VectorDescription v2) {
        rotation.fromAngles(v1.getX(),v1.getY(),v1.getZ());
        Matrix3f v1m = rotation.toRotationMatrix();
        Quaternion q2 = new Quaternion();
        q2.fromAngles(v2.getX(),v2.getY(),v2.getZ());
        v1m.multLocal(q2.toRotationMatrix());
        rotation.fromRotationMatrix(v1m);
    }

    /**
     * @return the angle
     */
    public Quaternion getRotation() {
        return rotation;
    }

}
