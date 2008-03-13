/**
 * Unified Simulator for Self-Reconfigurable Robots (USSR)
 * (C) University of Southern Denmark 2008
 * This software is distributed under the BSD open-source license.
 * For licensing see the file LICENCE.txt included in the root of the USSR distribution.
 */
package ussr.model;

/**
 * Controller implementation that provides the <tt>activate</tt> method expressed in terms
 * of the methods from <tt>ActBasedController</tt>.
 * 
 * @author ups
 *
 */
public abstract class ActControllerImpl extends ControllerImpl implements ActBasedController {

    /**
     * Default implementation of activate that uses methods from <tt>ActBasedController</tt>
     * to provide an activate method
     */
    @Override
    public void activate() {
        this.initializationActStep();
        while(this.singleActStep()) yield();
    }

}
