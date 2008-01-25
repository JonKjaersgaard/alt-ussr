package meta2d;

import java.awt.Color;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ussr.model.Module;
import ussr.physics.PhysicsSimulation;
import ussr.physics.SimulationGadget;

public class MetaGadget implements SimulationGadget.Textual {

    private PhysicsSimulation simulation;
    private SimulationGadget.Host host;
    
    public String getKey() {
        return "meta";
    }

    private static boolean match(String[] parts, int index, String value) {
        if(parts.length<=index) return false;
        return value.equals(parts[index]);
    }

    private static boolean has(String[] parts, int index) {
        return parts.length>index;
    }
    
    private abstract class ForEachGroup {
        ForEachGroup(String group) { 
            for(Module module: host.getModules()) {
                String moduleGroup = module.getProperty(MetaController.META_GROUP);
                if(group.equals(moduleGroup)) process(module);
            }
            
        }
        abstract void process(Module module);
    }
    
    public String process(String command) {
        String[] parts = command.split(" ");
        if(match(parts,1,"compass") && has(parts,2))
            new ForEachGroup(parts[2]) {
                void process(Module module) { compassColor(module); }
            };
        else if(match(parts,1,"recolor") && has(parts,2))
            new ForEachGroup(parts[2]) {
                void process(Module module) { recolorColor(module); }
            };
        else
            return "*** Meta command not understood: "+command;
        return "done";
}

    private Map<Module,List<Color>> colorRegistry = new HashMap<Module,List<Color>>();
    
    private void compassColor(Module module) {
        List<Color> colors = module.getColorList();
        colorRegistry.put(module,colors);
        int moduleRole = Integer.parseInt(module.getProperty(MetaController.META_ROLE));
        Color newColor;
        switch(moduleRole) {
        case 1: newColor = Color.WHITE; break;
        case 2: newColor = Color.YELLOW; break;
        case 4: newColor = Color.RED; break;
        case 8: newColor = Color.GREEN; break;
        default: throw new Error("Internal error: illegal role "+moduleRole);
        }
        module.setColor(newColor);
    }
    
    private void recolorColor(Module module) {
        List<Color> colors = colorRegistry.get(module);
        if(colors==null) throw new Error("Internal error: colors not found for module "+module);
        module.setColorList(colors);
    }

    public void install(PhysicsSimulation simulation, SimulationGadget.Host host) {
        this.simulation = simulation;
        this.host = host;
    }
    
    public MetaGadget clone() {
        return new MetaGadget();
    }

}
