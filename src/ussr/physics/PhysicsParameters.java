/**
 * Unified Simulator for Self-Reconfigurable Robots (USSR)
 * (C) University of Southern Denmark 2008
 * This software is distributed under the BSD open-source license.
 * For licensing see the file LICENCE.txt included in the root of the USSR distribution.
 */
package ussr.physics;

import ussr.util.TopologyWriter;

/**
 * A collection of parameters that describe the physical properties of the simulation.
 * [Currently] implemented as a singleton.  Some parameters a default value (step
 * size, gravity, etc.) whereas other parameters are optional and only have a value after
 * the have been set.
 * 
 * The parameters are to some extent specific to the JME physics engine (which again is based on ODE). 
 *  
 * @author Modular Robots @ MMMI
 */
public class PhysicsParameters {
    
    /**
     * Standard materials supported by the physics engine of the simulator
     * 
     * @author ups
     */
    public static enum Material { RUBBER, WOOD, ICE, CONCRETE, GLASS, IRON, DEFAULT, GRANITE };
    
    // NOTE: Initialization here are DEFAULT values, do NOT edit for your own simulation
    // Use e.g. PhysicsParameters.get().setPhysicsSimulationStepSize(0.0001f);
    // This can be called from the main method of your simulation 
    private float physicsSimulationStepSize = 0.005f;
    private boolean realisticCollision = true; // true = less likely to cause trouble, more user friendly
    private float gravity =-9.82f;
    private Material planeMaterial = Material.RUBBER;
    private boolean maintainRotationalJointPositions = true; // true = more user friendly
    private boolean hasMechanicalConnectorSpringiness = false;
    private float mechanicalConnectorSpringConstant;
    private float mechanicalConnectorDamping;
    private float constraintForceMix = 10E-5f;
    private float errorReductionParameter = 0.8f;
    private float worldDampingLinearVelocity = 0.0f;
    private float worldDampingAngularVelocity = 0.0f;
    private int resolutionFactor = 5;
    
    
    public float getWorldDampingLinearVelocity() {
        return worldDampingLinearVelocity;
    }
   /**
    * Percentage of linear velocity lost in each physics time-step
    */
    public void setWorldDampingLinearVelocity(float worldDampingLinearVelocity) {
        this.worldDampingLinearVelocity = worldDampingLinearVelocity;
    }
    
    public float getWorldDampingAngularVelocity() {
        return worldDampingAngularVelocity;
    }
    /**
     * Percentage of angular velocity lost in each physics time-step
     */
    public void setWorldDampingAngularVelocity(float worldDampingAngularVelocity) {
        this.worldDampingAngularVelocity = worldDampingAngularVelocity;
    }
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
    
    public boolean hasMechanicalConnectorSpringiness() {
        return this.hasMechanicalConnectorSpringiness;
    }
 
    public float getMechanicalConnectorConstant() {
        if(!this.hasMechanicalConnectorSpringiness()) throw new Error("Mechanical connector springiness not set");
        return this.mechanicalConnectorSpringConstant;
    }
    
    public float getMechanicalConnectorDamping() {
        if(!this.hasMechanicalConnectorSpringiness()) throw new Error("Mechanical connector springiness not set");
        return this.mechanicalConnectorDamping;
    }
    
    public void setMechanicalConnectorSpringiness(float constant, float damping) {
        this.mechanicalConnectorSpringConstant = constant;
        this.mechanicalConnectorDamping = damping;
        this.hasMechanicalConnectorSpringiness = true;
    }
    /**
     * @return the constraintForceMix
     */
    public float getConstraintForceMix() {
        return constraintForceMix;
    }
    /**
     * @param constraintForceMix the constraintForceMix to set
     */
    public void setConstraintForceMix(float constraintForceMix) {
        this.constraintForceMix = constraintForceMix;
    }
    /**
     * @return the errorReductionParameter
     */
    public float getErrorReductionParameter() {
        return errorReductionParameter;
    }
    /**
     * @param errorReductionParameter the errorReductionParameter to set
     */
    public void setErrorReductionParameter(float errorReductionParameter) {
        this.errorReductionParameter = errorReductionParameter;
    }
    public int getResolutionFactor() {
        return resolutionFactor  ;
    }
    public void setResolutionFactor(int resolutionFactor) {
        this.resolutionFactor = resolutionFactor;
    }
}
