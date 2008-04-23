/**
 * Unified Simulator for Self-Reconfigurable Robots (USSR)
 * (C) University of Southern Denmark 2008
 * This software is distributed under the BSD open-source license.
 * For licensing see the file LICENCE.txt included in the root of the USSR distribution.
 */
package ussr.description.setup;

import java.util.HashMap;
import java.util.Map;

import ussr.description.geometry.RotationDescription;
import ussr.description.geometry.VectorDescription;

import com.jme.math.Matrix4f;
import com.jme.math.Quaternion;
import com.jme.math.Vector3f;
import com.jmex.model.collada.schema.faceType;

/**
 * Global starting position of a module and annotations for the module
 * 
 * @author Modular Robots @ MMMI
 *
 */
public class ModulePosition {
    private String name;
    private String type;
    private VectorDescription position;
    private RotationDescription rotation;
    private Map<String, String> properties;

    /**
     * @param name
     * @param position
     * @param rotation_EW
     */
    public ModulePosition(String name, VectorDescription position, RotationDescription rotation) {
        this(name,"default",position,rotation);
    }
    /**
     * 
     * @param name name of module
     * @param type type of module like "atron", "odin", etc
     * @param position position of module in the world
     * @param rotation rotation of module in the world
     */
    public ModulePosition(String name, String type, VectorDescription position, RotationDescription rotation) {
        this(name,type,position,rotation,null);
    }

    public ModulePosition(String name, String type, VectorDescription position, RotationDescription rotation, Map<String,String> properties) {
        this.name = name;
        this.position = position;
        this.rotation = rotation;
        if(properties!=null)
            this.properties = properties;
        else
            this.properties = new HashMap<String,String>();
        if(type.indexOf(':')>=0) {
            int index = type.indexOf(':');
            this.type = type.substring(0,index);
            while(index<type.length()) {
                int end = type.indexOf(':',index);
                end = end==-1 ? type.length() : end;
                String property = type.substring(index,end);
                int pos = property.indexOf('=');
                if(pos==-1||pos==0||pos==property.length()-1) throw new Error("Illegal module property: "+property);
                properties.put(property.substring(0,pos), property.substring(pos+1));
            }
        } else
            this.type = type;
    }

    /**
     * @return the position
     */
    public VectorDescription getPosition() {
        return position;
    }

    /**
     * @param position the position to set
     */
    public void setPosition(VectorDescription position) {
        this.position = position;
    }

    /**
     * @return the rotation
     */
    public RotationDescription getRotation() {
        return rotation;
    }

    /**
     * @param rotation the rotation to set
     */
    public void setRotation(RotationDescription rotation) {
        this.rotation = rotation;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }
    /**
     * @return the type
     */
    public String getType() {
        return type;
    }
    /**
     * @return the properties
     */
    public Map<String, String> getProperties() {
        return properties;
    }
    /**
     * @param properties the properties to set
     */
    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }
    private Matrix4f toMatrix(Vector3f pos, Quaternion q) {
    	Matrix4f m = new Matrix4f();
		m.setRotationQuaternion(q);
		m.setTranslation(pos);
		return m;
    }
	public void rotate(Vector3f origin, Quaternion rot) {
		Quaternion q = rotation.getRotation();
		Vector3f pos = new Vector3f(position.getX(),position.getY(),position.getZ()); 
		
		Matrix4f m1 = toMatrix(origin,rot);
		Matrix4f m2 = toMatrix(pos,q);
		Matrix4f mRes = m1.mult(m2);
		
		rotation.setRotation(mRes.toRotationQuat());
		position.set(mRes.toTranslationVector());
	}
	public void translate(Vector3f translation) {
		Vector3f pos = new Vector3f(position.getX(),position.getY(),position.getZ());
		pos.addLocal(translation);
		position.set(pos);
	}
}