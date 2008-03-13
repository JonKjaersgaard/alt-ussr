package ussr.physics.jme;

import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.swing.*;

import ussr.model.Controller;
import ussr.model.Module;
import ussr.physics.PhysicsLogger;
import ussr.physics.SimulationGadget;

/**
 * Simulation gadget host for JME-based simulations.  Supports textual gadgets only.
 * 
 * @author ups
 */
public class DebugShell extends JPanel implements ActionListener, SimulationGadget.Host {
    protected JTextField textField;
    protected JTextArea textArea;
    private final static String newline = "\n";
    private final static String separator = "---------------------------------"+newline;
    private JMESimulation simulation;
    private static List<SimulationGadget.Textual> gadget_registry = new ArrayList<SimulationGadget.Textual>();
    private List<SimulationGadget.Textual> gadgets = new ArrayList<SimulationGadget.Textual>();

    public DebugShell(JMEBasicGraphicalSimulation simulation) {
        super(new GridBagLayout());
        this.simulation = (JMESimulation)simulation;

        textField = new JTextField(40);
        textField.addActionListener(this);

        textArea = new JTextArea(10, 40);
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);

        //Add Components to this panel.
        GridBagConstraints c = new GridBagConstraints();
        c.gridwidth = GridBagConstraints.REMAINDER;

        c.fill = GridBagConstraints.HORIZONTAL;
        add(textField, c);

        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1.0;
        c.weighty = 1.0;
        add(scrollPane, c);
        
        textArea.append("Debugging simulation "+simulation.toString()+newline);
        
        for(SimulationGadget.Textual gadget: gadget_registry) {
            SimulationGadget.Textual newGadget = gadget.clone();
            newGadget.install(this.simulation,this);
            gadgets.add(newGadget);
            
        }
    }

    public void actionPerformed(ActionEvent evt) {
        String text = textField.getText();
        textArea.append(process(text)+separator);
        textField.selectAll();

        //Make sure the new text is visible, even if there
        //was a selection in the text area.
        textArea.setCaretPosition(textArea.getDocument().getLength());
    }
    
    private String process(String command) {
        StringBuffer result = new StringBuffer();
        if(command.equals("ls")) {
            for(Module module: simulation.getModules()) {
                result.append(module.getProperty("name")+"("+module.getController()+")"+newline);
            }
            return result.toString();
        } else if(command.equals("eval") || command.startsWith("eval ")) {
            String[] args = command.split(" ");
            if(args.length==1) return "Usage: eval <module name> [<parameter>*], omit parameters for module usage"+newline;
            else if(args.length==2) return showUsage(args[1]);
            else return callCommand(args);
        } else if(command.startsWith("color ")) {
            String[] args = command.split(" ");
            if(!(args.length==3)) return "Usage: color <module name> <color>";
            return colorModule(args[1],args[2]);
        } else {
            for(SimulationGadget.Textual gadget: gadgets) {
                if(command.startsWith(gadget.getKey()+" "))
                    return gadget.process(command)+newline;
            }
            StringBuffer unknown = new StringBuffer("Unknown command: "+command+newline+"Available commands: ls eval color ");
            for(SimulationGadget.Textual gadget: gadgets)
                unknown.append(gadget.getKey()+" ");
            unknown.append(newline);
            return unknown.toString();
        }
    }
    
    private static final int OFFSET = 3;
    private String callCommand(String[] all) {
        String moduleNameExp = all[1];
        String methodName = all[2];
        if(!moduleNameExp.endsWith(("*")))
            return doCallCommand(moduleNameExp,methodName,all);
        else {
            String modulePrefix = moduleNameExp.substring(0,moduleNameExp.length()-1);
            StringBuffer result = new StringBuffer("[");
            for(Module module: simulation.getModules()) {
                String moduleName = module.getProperty("name");
                if(moduleName.startsWith(modulePrefix)) {
                    Object value = doCallCommand(moduleName,methodName,all);
                    result.append(value==null ? "(null)" : value.toString());
                    result.append(",");
                }
            }
            result.append("]");
            return result.toString();
        }
    }
    
    private String doCallCommand(String moduleName, String methodName, String[] all) {
        System.out.println("Invoking method "+methodName+" on module "+moduleName);
        Class<Controller> controllerClass = getControllerClass(moduleName);
        if(controllerClass==null) return "Module not found: "+moduleName+newline;
        for(Method method: controllerClass.getMethods()) {
            if(methodName.equals(method.getName())) {
                Object[] parameters = new Object[all.length-OFFSET];
                int position = 0;
                for(Class<?> type: method.getParameterTypes()) {
                    try {
                        parameters[position] = Integer.parseInt(all[position+OFFSET]);
                    } catch(NumberFormatException e) {
                        return "Error: illegal integer argument: "+all[position+OFFSET]+" "+newline;
                    }
                    position++;
                }
                try {
                    Controller controller = getController(moduleName);
                    if(controller==null) return "Internal error: controller not found, "+moduleName;
                    Object result = method.invoke(controller, parameters);
                    return (result==null ? "(null)" : result.toString())+newline;
                } catch (IllegalArgumentException e) {
                    return "Internal error: illegal argument type: "+e+newline; 
                } catch (IllegalAccessException e) {
                    return "Internal error: method cannot be accessed"+e+newline;
                } catch (InvocationTargetException e) {
                    return "Error: exception thrown by method: "+e+newline;
                }
            }
        }
        return "Error: method not found: "+methodName;

    }

    private String showUsage(String moduleName) {
        Class<Controller> controllerClass = getControllerClass(moduleName);
        if(controllerClass==null) return "Module not found: "+moduleName+newline;
        StringBuffer result = new StringBuffer();
        methodLoop: for(Method method: controllerClass.getMethods()) {
            for(Class<?> type: method.getParameterTypes()) {
                if(!(type==Integer.class || type==Integer.TYPE)) continue methodLoop;
            }
            result.append("  "+method+newline);
        }
        return result.toString();
    }

    private Class<Controller> getControllerClass(String moduleName) {
        Controller controller = getController(moduleName);
        if(controller==null) return null;
        Class<Controller> controllerClass = (Class<Controller>) controller.getClass();
        return controllerClass;
    }

    private Controller getController(String moduleName) {
        Module module = findModule(moduleName);
        if(module==null) return null;
        Controller controller = module.getController();
        return controller;
    }

    public Module findModule(String moduleName) {
        for(Module module: simulation.getModules()) {
            if(moduleName.equals(module.getProperty("name"))) return module;
        }
        return null;
    }

    private String colorModule(String moduleName, String colorName) {
        Module module = findModule(moduleName);
        if(module==null) return "Error: module "+moduleName+" not found";
        java.awt.Color color;
        if(colorName.equals("red")) color = Color.red;
        else if(colorName.equals("green")) color = Color.green;
        else return "Error: color "+colorName+" unknown";
        module.setColor(color);
        return "Module "+moduleName+" set to "+color;
    }
    
    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event dispatch thread.
     */
    private static void createAndShowGUI(JMEBasicGraphicalSimulation simulation) {
        //Create and set up the window.
        JFrame frame = new JFrame("USSR debug shell");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        //Add contents to the window.
        frame.add(new DebugShell(simulation));

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public static void activate(final JMEBasicGraphicalSimulation simulation) {
        //Schedule a job for the event dispatch thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI(simulation);
            }
        });
    }

    public synchronized static void addGadget(SimulationGadget gadget) {
        if(!(gadget instanceof SimulationGadget.Textual))
            PhysicsLogger.log("Warning: gadget not installed: "+gadget);
        gadget_registry.add((SimulationGadget.Textual)gadget);
    }

    public List<Module> getModules() {
        return Collections.unmodifiableList(simulation.getModules());
    }
}