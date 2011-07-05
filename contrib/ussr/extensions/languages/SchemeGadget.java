package ussr.extensions.languages;

import java.io.IOException;

import sisc.data.Value;
import sisc.interpreter.AppContext;
import sisc.interpreter.Context;
import sisc.interpreter.Interpreter;
import sisc.interpreter.SchemeException;
import ussr.physics.PhysicsSimulation;
import ussr.physics.SimulationGadget;

/**
 * USSR gadget that interfaces a scheme interpreter to the simulator, allowing
 * functionality to be scripted in scheme.  Currently only provides a single,
 * global shell (interpreter instance) which simplifies the scheme-Java interface
 * (see below).
 * 
 * <p>Scheme functionality is provided by the SISC R5RS compliant scheme interpreter,
 * which upon startup loads <strong>scheme/atron.scm</strong>.  The Scheme code
 * interfaces to the simulator by invoking methods on the gadget (which provides
 * an interface to the host, the simulator, and hence the entire system).  The 
 * reference to the object is obtained using the <strong>getHost</strong> method,
 * which is why it is implemented as a singleton.
 *
 * <p>Usage: press "U" in the simulation to being up the textual gadget interface, prefix all Scheme forms by "$ "
 * 
 * @author ups
 *
 */
public class SchemeGadget implements SimulationGadget.Textual {
    /**
     * The interpreter associated with this gadget
     */
    private Interpreter interpreter;
    /**
     * Singleton: a single scheme gadget associated with the simulation
     */
    private static SchemeGadget instance = new SchemeGadget();
    /**
     * The gadget host (e.g., the simulation)
     */
    private Host host;

    /**
     * @see SimulationGadget#install(PhysicsSimulation, ussr.physics.SimulationGadget.Host)
     */
    public void install(PhysicsSimulation simulation, Host host) {
        this.host = host;
        AppContext context = new AppContext();
        try {
            context.addDefaultHeap();
        } catch (IOException e) {
            throw new Error("SchemeGadget installation failed: "+e);
        }
        interpreter = Context.enter(context);
        try {
            interpreter.eval("(load \"lib/languages/scheme/atron.scm\")");
        } catch (Exception e) {
            throw new Error("SchemeGadget initialization failed: "+e);
        }
    }
    
    /**
     * @see SimulationGadget#clone()
     */
    public SchemeGadget clone() {
        return this;
    }

    /**
     * @see SimulationGadget.Textual#getKey()
     */
    public String getKey() {
        return "$";
    }

    /**
     * @see SimulationGadget.Textual#process(String)
     */
    public String process(String command) {
        try {
            Value result = interpreter.eval(command.substring(1));
            return result.toString();
        } catch (IOException e) {
            return "IO exception: "+e;
        } catch (SchemeException e) {
            return "Scheme exception: "+e;
        }
    }

    /**
     * Singleton pattern accessor method
     * @return the scheme gadget instance
     */
    public static SimulationGadget getInstance() {
        return instance;
    }

    /**
     * Called from scheme code to obtain a reference to the gadet
     * @return the gadget instance
     */
    public static SimulationGadget.Host getHost() {
        return instance.host;
    }

}
