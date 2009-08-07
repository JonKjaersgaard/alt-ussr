/**
 * Unified Simulator for Self-Reconfigurable Robots (USSR)
 * (C) University of Southern Denmark 2008
 * This software is distributed under the BSD open-source license.
 * For licensing see the file LICENCE.txt included in the root of the USSR distribution.
 */
package ussr.description.setup;

/**
 * A connection between two modules
 * 
 * @author Modular Robots @ MMMI
 *
 */
public class ModuleConnection {
    private String module1, module2;
    private int connector1, connector2;

    public ModuleConnection(String module1, int connector1, String module2, int connector2) {
        this.module1 = module1; this.module2 = module2;
        this.connector1 = connector1; this.connector2 = connector2;
    }

    /**
     * Connection constructor - search for a possible connection 
     */
    public ModuleConnection(String module1, String module2) {
        this.module1 = module1; this.module2 = module2;
        this.connector1 = -1; this.connector2 = -1;
    }

    /**
     * @return the connector1
     */
    public int getConnector1() {
        return connector1;
    }

    /**
     * @return the connector2
     */
    public int getConnector2() {
        return connector2;
    }

    /**
     * @return the module1
     */
    public String getModule1() {
        return module1;
    }

    /**
     * @return the module2
     */
    public String getModule2() {
        return module2;
    }
    public boolean equals(ModuleConnection other) {
    	if(other.connector1 !=connector1) return false;
    	if(other.connector2 !=connector2) return false;
    	if(!other.module1.equals(module1)) return false;
    	if(!other.module2.equals(module2)) return false;
    	return true;
    }
    
    public String toString() {
        return "ModuleConnection<"+module1+","+module2+">";
    }
}