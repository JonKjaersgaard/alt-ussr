/**
 * 
 */
package ussr.model;

/**
 * @author ups
 *
 * TODO Write a nice and user-friendly comment here
 * 
 */
public abstract class ControllerImpl implements Controller {

    protected Module module;
    
    /* (non-Javadoc)
     * @see ussr.model.Controller#activate()
     */
    public abstract void activate();

    /* (non-Javadoc)
     * @see ussr.model.Controller#setModule(ussr.model.Module)
     */
    public void setModule(Module module) {
        this.module = module;
    }

    public void waitForEvent() {
        synchronized(module) {
            try {
                module.wait();
            } catch (InterruptedException e) {
                throw new Error("Unexpected interrupt of waiting module");
            }
        }
    }
}
