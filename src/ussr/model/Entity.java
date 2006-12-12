/**
 * 
 */
package ussr.model;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ups
 *
 * Abstract class defining common behavior for entities in the simulator
 * 
 */
public abstract class Entity {

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
    }
}
