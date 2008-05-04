package dcd.highlevel.fapl;

import java.util.HashMap;
import java.util.Map;

import dcd.highlevel.IName;
import dcd.highlevel.Resolver;

public class IncrementalNameResolver implements Resolver {

    private Map<String,Integer> nameTable = new HashMap<String,Integer>();
    private int nextIndex = 0;
    
    public int getMethodIndex(String role, String method) {
        return getGlobalIndex(method);
    }

    public int getGlobalIndex(String name) {
        Integer index = nameTable.get(name);
        if(index==null) {
            index = nextIndex++;
            nameTable.put(name, index);
        }
        return index;
    }

}
