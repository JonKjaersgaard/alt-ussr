/**
 * Unified Simulator for Self-Reconfigurable Robots (USSR)
 * (C) University of Southern Denmark 2008
 * This software is distributed under the BSD open-source license.
 * For licensing see the file LICENCE.txt included in the root of the USSR distribution.
 */
package ussr.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ussr.description.Description;
import ussr.physics.PhysicsEntity;

/**
 * Abstract class defining common behavior for entities in the simulator, namely
 * the ability to have properties (strings with a key-value structure) defined on
 * the entity.
 * 
 * @author ups
 *
 */
public abstract class Entity {

    public static final String PROPERTY_NAME = "name";
    public static final String PROPERTY_COLOR = "color";
    public static final String PROPERTY_PORTRC = "portRC";
    public static final String PROPERTY_PORTEVENT = "portEvent";
    
    /**
     * Properties associated with this entity, may be null meaning no properties are set
     */
    private Map<String,String> properties;

    /**
     * Lookup a property on this entity  
     * @param key the name of the property to look up
     * @return the value of the property, null if the property is not defined
     */
    public String getProperty(String key) {
        if(properties==null) return null;
        return properties.get(key);
    }

    /**
     * Set a property on this entity
     * @param key the name of the property to look up
     * @param value the value of the property
     */
    public synchronized void setProperty(String key, String value) {
        if(properties==null) properties = new HashMap<String,String>();
        properties.put(key, value);
        synchronized(properties) {
        	properties.notifyAll();
        }
    }
    
    /**
     * Wait (by blocking the thread) for a given property to be set on this entity 
     * @param name the name of the property to wait for
     */
    public void waitForPropertyToExist(String name) {
    	synchronized(this) {
    		if(properties==null) properties = new HashMap<String,String>();
    	}
    	synchronized(properties) {
    		try {
    			while(properties.get(name)==null) properties.wait();
    		} catch(InterruptedException exn) {
    			throw new Error("Unexpected interruption");
    		}
    	}
    }
    
    /**
     * Get the physics entities representing this entity
     * @return the physics entities
     */
    public abstract List<? extends PhysicsEntity> getPhysics();
    
    /**
     * Read the labels stored in a description object, store each label as a property with key
     * label:name and the name as a value
     */
    public void readLabels(Description description) {
        for(String label: description.getLabels())
            this.setProperty("label:"+label, label);
    }

    public void setProperties(Map<String, String> otherProperties) {
        for(Map.Entry<String, String> prop: otherProperties.entrySet()) {
            this.setProperty(prop.getKey(), prop.getValue());
        }
        System.out.println("properties set on "+this);
    }

    public String toString() {
        StringBuffer result = new StringBuffer("{ ");
        if(properties!=null) {
            for(Map.Entry<String, String> entry: properties.entrySet())
                result.append(entry.getKey()+"="+entry.getValue()+" ");
        }
        result.append('}');
        return result.toString();
    }
    
    public static String mkprop(String propertyName, int value) {
        return mkprop(propertyName,Integer.toString(value));
    }

    public static String mkprop(String propertyName, String value) {
        return ";"+propertyName+"="+value;
    }
}
