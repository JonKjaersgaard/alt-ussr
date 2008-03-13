/**
 * Unified Simulator for Self-Reconfigurable Robots (USSR)
 * (C) University of Southern Denmark 2008
 * This software is distributed under the BSD open-source license.
 * For licensing see the file LICENCE.txt included in the root of the USSR distribution.
 */
package ussr.model;

/**
 * Interface that a controller can implement to be executed by a scheduler thread instead
 * of having its own thread.
 * 
 * @author ups
 */
public interface ActBasedController extends Controller {
    /**
     * Method called once when the controller starts
     */
    public void initializationActStep();
    /**
     * Method called regularly by the scheduler to perform a single controller step
     * @return true if the controller should be rescheduled, false to stop the controller 
     */
    public boolean singleActStep();
}
