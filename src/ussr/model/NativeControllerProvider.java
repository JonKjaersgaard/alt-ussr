/**
 * Unified Simulator for Self-Reconfigurable Robots (USSR)
 * (C) University of Southern Denmark 2008
 * This software is distributed under the BSD open-source license.
 * For licensing see the file LICENCE.txt included in the root of the USSR distribution.
 */
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
