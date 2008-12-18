package ussr.util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import ussr.model.Module;

public abstract class AbstractTopologyWriter implements ToplogyWriter {
    private Map<Module,Set<Module>> connections = new HashMap<Module,Set<Module>>();
    
    public void addConnection(Module m1, Module m2) {
        Set<Module> s1 = getSet(m1);
        Set<Module> s2 = getSet(m2);
        s1.add(m2);
        s2.add(m1);
    }

    private Set<Module> getSet(Module module) {
        Set<Module> set = connections.get(module);
        if(set==null) {
            set = new HashSet<Module>();
            connections.put(module,set);
        }
        return set;
    }

    public void finish() {
        printStart();
        for(Map.Entry<Module, Set<Module>> entry: connections.entrySet()) {
            printEntry(entry.getKey(),entry.getValue().iterator());
        }
        printEnd();
    }

    protected abstract void printStart();

    protected abstract void printEntry(Module key, Iterator<Module> iterator);

    protected abstract void printEnd();

}
