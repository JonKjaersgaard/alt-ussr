/**
 * Unified Simulator for Self-Reconfigurable Robots (USSR)
 * (C) University of Southern Denmark 2008
 * This software is distributed under the BSD open-source license.
 * For licensing see the file LICENCE.txt included in the root of the USSR distribution.
 */
package franco.util;

//import java.util.HashSet;
import java.io.PrintWriter;
//import java.util.Iterator;
import java.util.Iterator;
//import java.util.Map;
import java.util.Set;

import ussr.model.Module;
//import ussr.util.AbstractTopologyWriter;

public class OdinNEDTopologyWriter extends NEDTopologyWriter {
	
    public OdinNEDTopologyWriter(PrintWriter writer) {
        super(writer);
    }
    
    @Override
    protected void printEntry(Module key, Iterator<Module> connections) {
    	if(getType(name(key)).compareTo("module") == 0){
    		numberOfModules++;
    		while(connections.hasNext()){
    			Module ball = connections.next();
    			Set<Module> set = super.connections.get(ball); // I MADE CONNECTIONS PROTECTED TO RUN ODIN NED TOPOLOGY WRITER.
    			//Iterator<Module> iterator = set.iterator();
    			for(Module module : set){
    				//Module module = iterator.next();
    				if(module != key){
    					//Do something...
    					writer.println("    modules["+getIndex(name(key))+"].out++ --> modules["+getIndex(name(module))+"].in++;");
    				}
    			}
    		}
    		writer.println("");
    	}	
    }

}