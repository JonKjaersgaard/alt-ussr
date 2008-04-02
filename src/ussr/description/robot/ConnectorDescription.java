/**
 * Unified Simulator for Self-Reconfigurable Robots (USSR)
 * (C) University of Southern Denmark 2008
 * This software is distributed under the BSD open-source license.
 * For licensing see the file LICENCE.txt included in the root of the USSR distribution.
 */
package ussr.description.robot;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ussr.description.Description;
import ussr.description.geometry.GeometryDescription;
import ussr.description.geometry.RotationDescription;
import ussr.description.geometry.VectorDescription;

/**
 * Description of a single connector, relative to the module component on which it is to be mounted 
 * 
 * @author ups
 */
public class ConnectorDescription extends Description {

    /**
     * The connector type used on the robot
     * 
     * @author Modular Robots @ MMMI
     */
    public static enum Type {
        MAGNETIC_CONNECTOR,
        MECHANICAL_CONNECTOR_RIGID,
        MECHANICAL_CONNECTOR_HINGE,
        MECHANICAL_CONNECTOR_BALL_SOCKET,
        NONE, VELCRO_CONNECTOR
    }
    private Type type = Type.NONE;

    /**
     * Provide default values for connector properties that are common to several connectors
     * @author ups
     *
     */
    public static class Common extends ConnectorDescription {
        public Common() { }
    }

    private List<GeometryDescription> geometry;
    private VectorDescription position;
    private RotationDescription rotation;
    private String name;

    /**
     * The maximal radius from the center of a connector to the center of another connector
     * where the connectors can reach each other (e.g., connect)
     */
    private float maxConnectionDistance = 0.01f;

    public ConnectorDescription() { }
    
    public ConnectorDescription(Common common, VectorDescription position) {
        this(common,position,new RotationDescription());
    }

    public ConnectorDescription(Common common, VectorDescription position, RotationDescription rotation) {
        this(common, position, rotation, null);
    }
    
    public ConnectorDescription(Common common, VectorDescription position, RotationDescription rotation, Color color) {
        this(common,null,position,rotation,color);
    }
    
    public ConnectorDescription(Common common, String name, VectorDescription position, RotationDescription rotation, Color color) {
        this.geometry = common.getGeometry();
        this.name = name;
        this.position = position;
        this.rotation = rotation;
        this.type = common.getType();
        this.maxConnectionDistance = common.getMaxConnectionDistance();
        if(color!=null) {
            List<GeometryDescription> newGeometry = new ArrayList<GeometryDescription>();
            for(GeometryDescription description: this.geometry) {
                GeometryDescription newDescription = description.copy();
                newDescription.setColor(color);
                newGeometry.add(newDescription);
            }
            this.geometry = newGeometry;
        }
    }

    /**
     * @return the geometry
     */
    public List<GeometryDescription> getGeometry() {
        return geometry;
    }
    /**
     * @param geometry the geometry to set
     */
    public void setGeometry(GeometryDescription[] geometry) {
        this.geometry = Arrays.asList(geometry);
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
     * @return the type
     */
    public Type getType() {
        return type;
    }
    /**
     * @param type the type to set
     */
    public void setType(Type type) {
        this.type = type;
    }

    /**
     * Get the max connection distance: The maximal radius from the center of a connector to the center of another connector
     * where the connectors can reach each other (e.g., connect)
     * @return the max connection distance
     */
    public float getMaxConnectionDistance() {
        return maxConnectionDistance;
    }
    
    /**
     * Set the max connection distance: The maximal radius from the center of a connector to the center of another connector
     * where the connectors can reach each other (e.g., connect)
     * @param the max connection distance
     */
    public void setMaxConnectionDistance(float maxConnectionDistance) {
        this.maxConnectionDistance = maxConnectionDistance;
    }
    
    public String getName() {
        return name;
    }
}
