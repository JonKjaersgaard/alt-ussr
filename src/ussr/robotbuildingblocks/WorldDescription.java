/**
 * Uniform Simulator for Self-reconfigurable (modular) Robots
 * 
 * (C) 2006 University of Southern Denmark
 */
package ussr.robotbuildingblocks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.jme.math.Quaternion;

/**
 * The description of a world in which a number of robot modules can be placed for
 * the simulation.  Defines the number of modules, the size of the underlying plane,
 * and the position of each obstacle (note: obstacles currently have a fixed geometry)
 * 
 * @author ups
 *
 */
public class WorldDescription extends Description {

    public static enum CameraPosition {
        DEFAULT, FAROUT;
    }
    
    public interface TextureDescription {
        public String getFileName();
        public VectorDescription getScale(int size);
    }
    
    public static final TextureDescription GRASS_TEXTURE = new TextureDescription() {
        public String getFileName() { return "resources/myGrass2.jpg"; }
        public VectorDescription getScale(int size) { return new VectorDescription(100f,100f,0f); }
    };
    
    public static final TextureDescription GRID_TEXTURE = new TextureDescription() {
        public String getFileName() { return "resources/grid2.jpg"; }
        public VectorDescription getScale(int size) { return new VectorDescription(50f*size,50f*size,0f); }
    };
    
    public static class Connection {
        private String module1, module2;
        private int connector1, connector2;
        
        public Connection(String module1, int connector1, String module2, int connector2) {
            this.module1 = module1; this.module2 = module2;
            this.connector1 = connector1; this.connector2 = connector2;
        }

        /**
         * Connection constructur - search for a possible connection 
         */
        public Connection(String module1, String module2) {
        	this.module1 = module1; this.module2 = module2;
        	this.connector1 = -1; this.connector2 = -1;
		}

		/**
         * @return the connector1
         */
        public int getConnector1() {
            return connector1;
        }

        /**
         * @return the connector2
         */
        public int getConnector2() {
            return connector2;
        }

        /**
         * @return the module1
         */
        public String getModule1() {
            return module1;
        }

        /**
         * @return the module2
         */
        public String getModule2() {
            return module2;
        }

    }

    public static class ModulePosition {
        private String name;
        private String type;
        private VectorDescription position;
        private RotationDescription rotation;

        /**
         * @param name
         * @param position
         * @param rotation_EW
         */
        public ModulePosition(String name, VectorDescription position, RotationDescription rotation) {
            this.name = name;
            this.type = "default";
            this.position = position;
            this.rotation = rotation;
        }
        /**
         * 
         * @param name name of module
         * @param type type of module linke "atron", "odin", etc
         * @param position position of module in the world
         * @param rotation rotation of module in the world
         */
        public ModulePosition(String name, String type, VectorDescription position, RotationDescription rotation) {
            this.name = name;
            this.type = type;
            this.position = position;
            this.rotation = rotation;
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
    }
    
    public static class BoxDescription {
        private VectorDescription position, size;
        private RotationDescription rotation;
        private boolean isStatic, isHeavy;
        private float mass;
        public BoxDescription(VectorDescription position, VectorDescription size, RotationDescription rotation, float mass, boolean isStatic, boolean isHeavy) {
        	this.position = position; this.size = size; this.isStatic = isStatic;
            this.rotation = rotation; this.isHeavy = isHeavy; this.mass = mass;
        }
        public BoxDescription(VectorDescription position, VectorDescription size, RotationDescription rotation, boolean isStatic) {
        	this.position = position; this.size = size; this.isStatic = isStatic;
            this.rotation = rotation; this.isHeavy = false; this.mass = 1;
        }
        public BoxDescription(VectorDescription position, VectorDescription size, RotationDescription rotation, float mass) {
        	this.position = position; this.size = size; this.isStatic = false;
            this.rotation = rotation; this.isHeavy = false; this.mass = mass;
        }
        public BoxDescription(VectorDescription position, VectorDescription size, boolean isHeavy) {
            this.position = position; this.size = size; this.isHeavy = isHeavy;
            this.rotation = new RotationDescription(new Quaternion()); this.isStatic = false;
        }
        public VectorDescription getPosition() { return position; }
        public VectorDescription getSize() { return size; }
        public RotationDescription getRotation() { return rotation; }
        public float getMass() { return mass; }
        public boolean getIsStatic() { return isStatic; }
        public boolean getIsHeavy() { return isHeavy; }
    }
    
    /**
     * Number of modules in the simulation
     */
    private int numberOfModules = 0;
    
    /**
     * The size of one edge of the underlying plane
     */
    private int planeSize = 0;
    
    /**
     * The texture of the plane
     */
    private TextureDescription planeTexture = GRASS_TEXTURE;
    
    /**
     * The position of each obstacle (and implicigtly the number of obstacles)
     */
    private List<VectorDescription> smallObstacles = Collections.emptyList();
    
    private List<ModulePosition> modules = Collections.emptyList(); 
    
    private List<Connection> connections = Collections.emptyList();
    
    private List<BoxDescription> bigObstacles = Collections.emptyList();
    
    /**
     * The initial position of the camera
     */
    private CameraPosition cameraPosition = CameraPosition.DEFAULT;
    
    /**
     * Get the number of modules initially placed in the simulation
     * @return number of initial modules
     */
    public int getNumberOfModules() {
        if(modules.size()>0)
            return modules.size();
        else
            return numberOfModules;
    }
    
    /**
     * Get the size (e.g., length of one of the edges) of the underlying plane placed in the simulation 
     * @return the underlying plane size
     */
    public int getPlaneSize() {
        return planeSize;
    }

    /**
     * Get the positions of obstacles initially placed in the world
     * @return the obstacle positions
     */
    public List<VectorDescription> getObstacles() {
        return smallObstacles;
    }
    
    /**
     * Set the number of modules initially placed in the simulation
     * @param nModules number of initial modules
     */
    public void setNumberOfModules(int nModules) {
        this.numberOfModules = nModules;
    }

   /**
    * Set the size (e.g., length of one of the edges) of the underlying plane placed in the simulation 
    * @param size the underlying plane size
    */
   public void setPlaneSize(int size) {
        this.planeSize = size;
    }

   /**
    * Set the positions of obstacles initially placed in the world
    * @param descriptions the obstacle positions
    */
   public void setObstacles(VectorDescription[] descriptions) {
        this.smallObstacles = Arrays.asList(descriptions);  
    }

   /**
    * @return the modules
    */
   public List<ModulePosition> getModulePositions() {
       return modules;
   }
   public void setModulePositions(List<ModulePosition> pos) {
	   this.modules = pos;
   }
   
   /**
    * @param modules the modules to set
    */
   public void setModulePositions(ModulePosition[] modules) {
       this.modules = Arrays.asList(modules);
   }

   public void setModuleConnections(Connection[] connections) {
       this.connections = Arrays.asList(connections);
   }
   public void setModuleConnections(List<Connection> connections) {
       this.connections = connections;
   }
   
   public List<Connection> getConnections() {
       return connections;
   }
   public void setCameraPosition(CameraPosition position) {
       cameraPosition = position;
   }
   
   public CameraPosition getCameraPosition() {
       return cameraPosition;
   }

   /**
    * @return the bigObstacles
    */
   public List<BoxDescription> getBigObstacles() {
       return bigObstacles;
   }

   /**
    * @param bigObstacles the bigObstacles to set
    */
   public void setBigObstacles(BoxDescription[] bigObstacles) {
       this.bigObstacles = Arrays.asList(bigObstacles);
   }

/**
 * @return the planeTexture
 */
public TextureDescription getPlaneTexture() {
    return planeTexture;
}

/**
 * @param planeTexture the planeTexture to set
 */
public void setPlaneTexture(TextureDescription planeTexture) {
    this.planeTexture = planeTexture;
}

}
