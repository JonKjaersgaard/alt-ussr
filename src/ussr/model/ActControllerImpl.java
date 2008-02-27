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
        while(this.singleActStep()) ussrYield();
    }

}
