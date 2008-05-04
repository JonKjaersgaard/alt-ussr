package dcd.highlevel.rdcd;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import dcd.highlevel.IName;
import dcd.highlevel.Resolver;
import dcd.highlevel.ast.*;

public class VTableResolutionPhase implements Resolver {
    private Program program;

    private Map<String,Map<String,Integer>> vtableMap = new HashMap<String,Map<String,Integer>>();
    private Set<String> resolved = new HashSet<String>();
    
    public VTableResolutionPhase(Program program) {
        this.program = program;
        resolved.add("Module");
    }

    public void resolve() {
        for(Role role: program.getRoles()) 
            resolveRole(role);
    }

    private void resolveRole(Role role) {
        if(resolved.contains(role.getName().getName())) return;
        resolved.add(role.getName().getName());
        if(!resolved.contains(role.getZuper().getName())) resolveRole(program.getRole(role.getZuper())); 
        for(Method method: role.getMethods()) {
            if(method.getModifier()==Modifier.COMMAND || method.getModifier()==Modifier.BEHAVIOR) {
                addMethod(role,method.getName());
            }
        }
    }
    
    private void addMethod(Role role, Name methodName) {
        if(vtableMap.get(role.getName().getName())==null) vtableMap.put(role.getName().getName(),new HashMap<String,Integer>());
        Map<String,Integer> selfMap = vtableMap.get(role.getName().getName());
        Map<String,Integer> superMap = vtableMap.get(role.getZuper().getName());
        Integer result;
        if(superMap!=null && superMap.get(methodName.getName())!=null) {
            result = superMap.get(methodName.getName());
        } else {
            result = selfMap.size();
        }
        selfMap.put(methodName.getName(), result);
        System.out.println("/* Resolved method "+role.getName().getName()+"."+methodName.getName()+" to "+result+" */");
    }
    
    public int getMethodIndex(String role, String method) {
        if(vtableMap.get(role)==null || vtableMap.get(role).get(method)==null) throw new Error("Undefined method name: "+role+"."+method);
        return vtableMap.get(role).get(method).intValue();
        
    }

    public int getGlobalIndex(String name) {
        throw new Error("Not implemented yet, should compute max index in vtables and return +1 (but also somehow increment)");
    }
}
