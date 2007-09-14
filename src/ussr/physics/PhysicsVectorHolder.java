package ussr.physics;

public class PhysicsVectorHolder {
    private Object physicsVector;
    public PhysicsVectorHolder(Object physicsVector) {
        this.physicsVector = physicsVector;
    }
    public Object get() { return physicsVector; }
}
