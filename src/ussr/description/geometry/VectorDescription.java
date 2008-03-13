/**
 * Unified Simulator for Self-Reconfigurable Robots (USSR)
 * (C) University of Southern Denmark 2008
 * This software is distributed under the BSD open-source license.
 * For licensing see the file LICENCE.txt included in the root of the USSR distribution.
 */
package ussr.description.geometry;

import ussr.description.Description;

import com.jme.math.Vector3f;

/**
 * A physics-engine independent description of a vector.  Can be used for describing relative positions within
 * a robot.
 * 
 * TODO: make it physics engine independent :-)
 * 
 * @author Modular Robots @ MMMI
 *
 */
public class VectorDescription extends Description {
    
    /**
     * The x,y,z values of the vector
     */
    private float x,y,z;
    
    /**
     * Create a vector setting the individual values
     * @param x x-coordinate
     * @param y y-coordinate
     * @param z z-coordinate
     */
    public VectorDescription(float x, float y, float z) {
        this.x = x; this.y = y; this.z = z;
    }
    
    public VectorDescription(Vector3f pos) {
    	this.x = pos.x; this.y = pos.y; this.z = pos.z;
    }
    
    public VectorDescription() {
        this.x = this.y = this.z = 0;
    }
    
    /**
     * Get the x-coordinate
     * @return x-coordinate
     */
    public float getX() {
        return x;
    }

    /**
     * Get the y-coordinate
     * @return y-coordinate
     */
    public float getY() {
        return y;
    }
    
    /**
     * Get the z-coordinate
     * @return z-coordinate
     */
    public float getZ() {
        return z;
    }

	public float distance(VectorDescription p) {
		return (float)Math.sqrt((p.x-x)*(p.x-x)+(p.y-y)*(p.y-y)+(p.z-z)*(p.z-z));
	}

	public void add(float dx, float dy, float dz) {
		this.x += dx; this.y += dy; this.z += dz;
	}
	public void sub(float dx, float dy, float dz) {
		this.x -= dx; this.y -= dy; this.z -= dz;
	}
	public void mult(float dx, float dy, float dz) {
		this.x *= dx; this.y *= dy; this.z *= dz;
	}
	public String toString() {
		return "("+x+", "+y+", "+z+")";
	}

	public void set(float x, float y, float z) {
		this.x = x; this.y = y; this.z = z;	
	}

    public VectorDescription add(VectorDescription offset) {
        this.add(offset.x, offset.y, offset.z);
        return this;
    }
}
