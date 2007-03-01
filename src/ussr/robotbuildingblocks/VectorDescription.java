/**
 * Uniform Simulator for Self-reconfigurable (modular) Robots
 * 
 * (C) 2006 University of Southern Denmark
 */
package ussr.robotbuildingblocks;

/**
 * A description of a vector.  Can be used for describing relative positions within
 * a robot.
 * 
 * @author ups
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
}
