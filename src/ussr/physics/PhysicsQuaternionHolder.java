package ussr.physics;

public class PhysicsQuaternionHolder {
    private Object physicsQuaternion;
    public PhysicsQuaternionHolder(Object physicsQuaternion) {
        this.physicsQuaternion = physicsQuaternion;
    }
    public Object get() { return physicsQuaternion; }
}
