/**
 * 
 */
package ussr.physics;

/**
 * @author ups
 *
 * TODO Write a nice and user-friendly comment here
 * 
 */
public class PhysicsParameters {
    
    public static enum Material { RUBBER };
    
    // NOTE: Initialization here are DEFAULT values, do NOT edit for your own simulation
    // Use e.g. PhysicsParameters.get().setPhysicsSimulationStepSize(0.0001f);
    // This can be called from the main method of your simulation 
    private float physicsSimulationStepSize = 0.005f;
    private boolean realisticCollision = false;
    private float gravity =-9.82f;
    private Material planeMaterial = Material.RUBBER;
    private boolean maintainRotationalJointPositions = false;
    
    /**
     * @return the physicsSimulationStepSize
     */
    public float getPhysicsSimulationStepSize() {
        return physicsSimulationStepSize;
    }
    /**
     * @param physicsSimulationStepSize the physicsSimulationStepSize to set
     */
    public void setPhysicsSimulationStepSize(float physicsSimulationStepSize) {
        this.physicsSimulationStepSize = physicsSimulationStepSize;
    }
    /**
     * @return the realisticCollision
     */
    public boolean getRealisticCollision() {
        return realisticCollision;
    }
    /**
     * @param realisticCollision the realisticCollision to set
     */
    public void setRealisticCollision(boolean realisticCollision) {
        this.realisticCollision = realisticCollision;
    }
    /**
     * @return the gravity
     */
    public float getGravity() {
        return gravity;
    }
    /**
     * @param gravity the gravity to set
     */
    public void setGravity(float gravity) {
        this.gravity = gravity;
    }
    /**
     * @return the planeMaterial
     */
    public Material getPlaneMaterial() {
        return planeMaterial;
    }
    /**
     * @param planeMaterial the planeMaterial to set
     */
    public void setPlaneMaterial(Material planeMaterial) {
        this.planeMaterial = planeMaterial;
    }

    private static PhysicsParameters defaultParameters;
    public static synchronized PhysicsParameters get() {
        if(defaultParameters==null) defaultParameters = new PhysicsParameters();
        return defaultParameters;
    }
    /**
     * @return the maintainRotationalJointPositions
     */
    public boolean getMaintainRotationalJointPositions() {
        return maintainRotationalJointPositions;
    }
    /**
     * @param maintainRotationalJointPositions the maintainRotationalJointPositions to set
     */
    public void setMaintainRotationalJointPositions(
            boolean maintainRotationalJointPositions) {
        this.maintainRotationalJointPositions = maintainRotationalJointPositions;
    }
    
}
