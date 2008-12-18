package ussr.util;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ussr.model.Connector;
import ussr.model.Module;

public class LiveTopologyDumper {

    private Module start;
    
    public LiveTopologyDumper(Module module) {
        this.start = module;
    }

    public void dump(TopologyWriter writer) {
        Set<Module> visited = new HashSet<Module>();
        List<Module> pending = new ArrayList<Module>();
        pending.add(start);
        while(pending.size()>0) {
            Module current = pending.remove(pending.size()-1);
            if(visited.contains(current)) continue;
            visited.add(current);
            List<Connector> connectors = current.getConnectors();
            for(Connector c: connectors) {
                List<Module> modules = c.getConnectedModules();
                for(Module m: modules)
                    if(m!=current) {
                        writer.addConnection(current, m);
                        pending.add(m);
                    }
            }
        }
        writer.finish();
    }

}
