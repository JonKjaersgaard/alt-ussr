/**
 * Unified Simulator for Self-Reconfigurable Robots (USSR)
 * (C) University of Southern Denmark 2008
 * This software is distributed under the BSD open-source license.
 * For licensing see the file LICENCE.txt included in the root of the USSR distribution.
 */
package ussr.description;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Common superclass for descriptions.  Defines ability to tag elements with
 * with textual labels.
 * 
 * @author Modular Robots @ MMMI
 *
 */
public abstract class Description implements Cloneable {

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

    public Set<String> getLabels() {
        return Collections.unmodifiableSet(labels);
    }
}
