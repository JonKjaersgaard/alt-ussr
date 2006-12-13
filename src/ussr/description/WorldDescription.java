/**
 * 
 */
package ussr.description;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author ups
 *
 * TODO Write a nice and user-friendly comment here
 * 
 */
public class WorldDescription {

    private int numberOfModules = 0;
    private int planeSize = 0;
    private List<VectorDescription> obstacles = Collections.emptyList();
    
    public int getNumberOfModules() {
        return numberOfModules;
    }
    
    public int getPlaneSize() {
        return planeSize;
    }
    
    public List<VectorDescription> getObstacles() {
        return obstacles;
    }
    
    public void setNumberOfModules(int nModules) {
        this.numberOfModules = nModules;
        
    }

    public void setPlaneSize(int size) {
        this.planeSize = size;
    }

    public void setObstacles(VectorDescription[] descriptions) {
        this.obstacles = Arrays.asList(descriptions);        
    }

}
