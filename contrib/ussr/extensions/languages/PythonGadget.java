package ussr.extensions.languages;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PipedReader;
import java.io.PipedWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.python.core.PyObject;
import org.python.util.PythonInterpreter;

import ussr.model.Module;
import ussr.physics.PhysicsSimulation;
import ussr.physics.SimulationGadget;

/**
 * USSR gadget that interfaces a python interpreter to the simulator, allowing
 * functionality to be scripted in python.  Provides a python interpreter per module
 * with a wildcard functionality to send commands to all interpreters.
 * 
 * <p>Python functionality is provided by jython
 * which upon startup loads <strong>...</strong>.  The python code
 * interfaces to the simulator by invoking methods on the individual module controllers.
 *
 * <p>Usage: press "U" in the simulation to being up the textual gadget interface, prefix all Python commands by "#module "
 * where 'module' is the name of the module to send the command to, or "#*" to send the command to all modules.
 * 
 * @author ups
 *
 */
public class PythonGadget implements SimulationGadget.Textual {
    private static final String INIT_FILE = "lib/languages/python/init_commands.py";
    /**
     * The interpreter associated with this gadget
     */
    private Map<String,PythonInterpreter> interpreters = new HashMap<String,PythonInterpreter>();
    /**
     * Singleton: a single scheme gadget associated with the simulation
     */
    private static PythonGadget instance = new PythonGadget();
    /**
     * The gadget host (e.g., the simulation)
     */
    private Host host;

    /**
     * @see SimulationGadget#install(PhysicsSimulation, ussr.physics.SimulationGadget.Host)
     */
    public void install(PhysicsSimulation simulation, Host _host) {
        this.host = _host;
        List<Module> modules = host.getModules();
        try {
            PipedReader sink = new PipedReader();
            PipedWriter writer = new PipedWriter(sink);
            for(Module module: modules) {
                InputStream startFile = new FileInputStream(INIT_FILE); 
                PythonInterpreter interpreter = new PythonInterpreter();
                final String name = module.getProperty("name");
                interpreters.put(name,interpreter);
                interpreter.set("__module__", module.getController());
                interpreter.setOut(writer);
                interpreter.setErr(writer);
                interpreter.execfile(startFile);
            }
            final BufferedReader input = new BufferedReader(sink);
            new Thread() {
                @Override public void run() {
                    while(true) {
                        try {
                            String line = input.readLine();
                            if(line==null) break;
                            host.println("# "+line);
                        } catch (IOException exn) {
                            throw new Error("Reading input failed");
                        }

                    }
                }
            }.start();
        } catch(IOException exn) {
            System.err.println("Warning: unable to initialize python output");
        }
        //PyObject result = interpreter.eval("print 'hello'");
    }

    /**
     * @see SimulationGadget#clone()
     */
    public PythonGadget clone() {
        return this;
    }

    /**
     * @see SimulationGadget.Textual#getKey()
     */
    public String getKey() {
        return "#";
    }

    /**
     * @see SimulationGadget.Textual#process(String)
     */
    public String process(String commandLine) {
        String command = commandLine.substring(1);
        if(command.startsWith("*")) {
            for(PythonInterpreter interpreter: interpreters.values())
                evaluateCommand(interpreter,command.substring(2));
        } else {
            String module = command.substring(0,command.indexOf(' '));
            PythonInterpreter interpreter = interpreters.get(module);
            if(interpreter==null) return "ERROR: no module "+module+", use command 'ls' to get list of modules";
            evaluateCommand(interpreter,command.substring(module.length()+1));
        }
        return null;
    }

    private void evaluateCommand(PythonInterpreter interpreter, String command) {
        interpreter.exec(command);
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
