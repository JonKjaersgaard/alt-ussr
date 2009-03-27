/**
 * Unified Simulator for Self-Reconfigurable Robots (USSR)
 * (C) University of Southern Denmark 2008
 * This software is distributed under the BSD open-source license.
 * For licensing see the file LICENCE.txt included in the root of the USSR distribution.
 */
package ussr.description.setup;

import ussr.description.geometry.RotationDescription;
import ussr.description.geometry.VectorDescription;

import com.jme.math.Quaternion;

/**
 * A box-shaped obstacle that can be placed in the simulation
 * 
 * @author Modular Robots @ MMMI
 *
 */
public class BoxDescription {
    public static enum Heaviness { NORMAL, KINDOF, VERY };
    private VectorDescription position, size;
    private RotationDescription rotation;
    private boolean isStatic;
    private Heaviness heaviness;
    private float mass;
    /**
     * Create a box obstacle
     * @param position the initial position of the box
     * @param size the size of the box
     * @param rotation the rotation of the box
     * @param mass the mass of the box
     * @param isStatic true if the box has no dynamic behavior (does not move, is not affected by physical forces) during the simulation, false otherwise
     * @param isHeavy true if the mass of the box should be dramatically increased
     */
    public BoxDescription(VectorDescription position, VectorDescription size, RotationDescription rotation, float mass, boolean isStatic, Heaviness isHeavy) {
        this.position = position; this.size = size; this.isStatic = isStatic;
        this.rotation = rotation; this.heaviness = isHeavy; this.mass = mass;
    }
    public BoxDescription(VectorDescription position, VectorDescription size, RotationDescription rotation, boolean isStatic) {
        this.position = position; this.size = size; this.isStatic = isStatic;
        this.rotation = rotation; this.heaviness = Heaviness.NORMAL; this.mass = 1;
    }
    public BoxDescription(VectorDescription position, VectorDescription size, RotationDescription rotation, float mass) {
        this.position = position; this.size = size; this.isStatic = false;
        this.rotation = rotation; this.heaviness = Heaviness.NORMAL; this.mass = mass;
    }
    public BoxDescription(VectorDescription position, VectorDescription size, Heaviness isHeavy) {
        this.position = position; this.size = size; this.heaviness = isHeavy;
        this.rotation = new RotationDescription(new Quaternion()); this.isStatic = false;
    }
    /**
     * Get the position of the box
     * @return the position
     */
    public VectorDescription getPosition() { return position; }
    /**
     * Get the size of the box
     * @return the size
     */
    public VectorDescription getSize() { return size; }
    /**
     * Get the rotation of the box
     * @return the rotation
     */
    public RotationDescription getRotation() { return rotation; }
    /**
     * Get the mass of the box
     * @return the mass
     */
    public float getMass() { return mass; }
    /**
     * Get whether the box is static (does not move during simulation) or dynamic (affected by the physical forces in the simulation)
     * @return true if the box is static, false otherwise
     */
    public boolean getIsStatic() { return isStatic; }
    /**
     * Get whether the box is made extra heavy by massively increasing the mass
     * @return true if the box is made extra heavy, false otherwise
     */
    public Heaviness getHeaviness() { return heaviness; }
}