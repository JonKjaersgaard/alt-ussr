/**
 * Unified Simulator for Self-Reconfigurable Robots (USSR)
 * (C) University of Southern Denmark 2008
 * This software is distributed under the BSD open-source license.
 * For licensing see the file LICENCE.txt included in the root of the USSR distribution.
 */
package ussr.description.geometry;

import java.io.Serializable;

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
public class VectorDescription extends Description implements Serializable {
    
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

    public Vector3f getVector() {
    	return new Vector3f(x,y,z);
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
	public void set(Vector3f p) {
		this.x = p.x; this.y = p.y; this.z = p.z;	
	}

    public VectorDescription add(VectorDescription offset) {
        this.add(offset.x, offset.y, offset.z);
        return this;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Float.floatToIntBits(x);
        result = prime * result + Float.floatToIntBits(y);
        result = prime * result + Float.floatToIntBits(z);
        return result;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final VectorDescription other = (VectorDescription) obj;
        if (Float.floatToIntBits(x) != Float.floatToIntBits(other.x))
            return false;
        if (Float.floatToIntBits(y) != Float.floatToIntBits(other.y))
            return false;
        if (Float.floatToIntBits(z) != Float.floatToIntBits(other.z))
            return false;
        return true;
    }
}
