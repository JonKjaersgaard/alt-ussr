/**
 * Uniform Simulator for Self-reconfigurable (modular) Robots
 * 
 * (C) 2006 University of Southern Denmark
 */
package ussr.robotbuildingblocks;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * The description of a world in which a number of robot modules can be placed for
 * the simulation.  Defines the number of modules, the size of the underlying plane,
 * and the position of each obstacle (note: obstacles currently have a fixed geometry)
 * 
 * @author ups
 *
 */
public class WorldDescription extends Description {

    public static class Connection {

        public Connection(String module1, int connector1, String module2, int connector2) {
            // TODO Auto-generated constructor stub
        }

    }

    public static class ModulePosition {
        private String name;
        private VectorDescription position;
        private RotationDescription rotation;

        /**
         * @param name
         * @param position
         * @param rotation_EW
         */
        public ModulePosition(String name, VectorDescription position, RotationDescription rotation) {
            this.name = name;
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
     * The position of each obstacle (and implicigtly the number of obstacles)
     */
    private List<VectorDescription> obstacles = Collections.emptyList();
    
    private List<ModulePosition> modules = Collections.emptyList(); 
    
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
        return obstacles;
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
        this.obstacles = Arrays.asList(descriptions);        
    }

   /**
    * @return the modules
    */
   public List<ModulePosition> getModulePositions() {
       return modules;
   }

   /**
    * @param modules the modules to set
    */
   public void setModulePositions(ModulePosition[] modules) {
       this.modules = Arrays.asList(modules);
   }

public void setModuleConnections(Connection[] connections) {
    // TODO Auto-generated method stub
    
}

}
