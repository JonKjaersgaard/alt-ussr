package ussr.model;

/**
 * Controller implementation for NativeController
 * 
 * @see NativeController
 * 
 * @author Modular Robots @ MMMI
 */
public interface NativeControllerProvider extends Controller {
    /**
     * Return an integer defining the role played by the module, can be used by the controller
     * implementation to differentiate the behavior of different modules
     * @return the role identifier
     */
	public int getRole();
}
