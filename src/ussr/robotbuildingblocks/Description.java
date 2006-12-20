/**
 * Uniform Simulator for Self-reconfigurable (modular) Robots
 * 
 * (C) 2006 University of Southern Denmark
 */
package ussr.robotbuildingblocks;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Common superclass for descriptions.  Defines ability to tag elements with
 * with textual labels.
 * 
 * @author ups
 *
 */
public abstract class Description {

    /**
     * Labels associated with this element
     */
    private Set<String> labels = new HashSet<String>();
    
    /**
     * Default constructor: no labels
     */
    public Description() { }
    
    /**
     * A geometry created with a set of labels
     * @param _labels the set of labels to associate with this element
     */
    public Description(String[] _labels) {
        List<String> strings = Arrays.asList(_labels);
        labels.addAll(strings);
    }

    /**
     * Associate additional labels with this element
     * @param _labels the set of labels to associate with this element
     */
    public void addLabels(String[] _labels) {
        List<String> strings = Arrays.asList(_labels);
        labels.addAll(strings);
    }
}
