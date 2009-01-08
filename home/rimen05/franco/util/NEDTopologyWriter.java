/**
 * Unified Simulator for Self-Reconfigurable Robots (USSR)
 * (C) University of Southern Denmark 2008
 * This software is distributed under the BSD open-source license.
 * For licensing see the file LICENCE.txt included in the root of the USSR distribution.
 */
package franco.util;

import java.io.PrintWriter;
import java.util.Iterator;

import ussr.model.Module;
import ussr.util.AbstractTopologyWriter;

public class NEDTopologyWriter extends AbstractTopologyWriter {

    private PrintWriter writer;
    private int numberOfModules = 0;
    
    public NEDTopologyWriter(PrintWriter writer) {
        this.writer = writer;
    }

    @Override
    protected void printStart() {
    	writer.println("//");
    	writer.println("// Copyright (C) 2008 Modular Robotics Lab @ University of Southern Denmark");
    	writer.println("// WebPage http://modular.mmmi.sdu.dk");
    	writer.println("//");
    	writer.println("");
    	writer.println("import \"TopologySimpleModules.ned\";");
    	writer.println("");
    	writer.println("module LatticeModularRobot");
    	writer.println("  parameters: // compound modules parameters...");
    	writer.println("    nModules: numeric const,");
    	writer.println("    txPeriod: numeric const;");
    	writer.println("  submodules:");
    	writer.println("    statisticsCollector: StatisticsCollector;");
    	writer.println("       parameters: // statistics collector parameters...");
    	writer.println("         nModules = nModules,");
    	writer.println("         seedModule = intuniform(0,nModules-1),");
    	writer.println("         collectionPeriod = txPeriod;");
    	writer.println("       display:  \"p=70,56;b=40,24\"; // affects statistics collector...");
    	writer.println("    modules: Module[nModules];");
    	writer.println("      parameters: // sub-modules parameters...");
    	writer.println("        txPeriod = txPeriod;");
    	writer.println("      display: \"b=40,24,oval;o=white\"; // affects modules...");
    	writer.println("");
    	writer.println("  connections nocheck:");
    }
    
    @Override
    protected void printEntry(Module key, Iterator<Module> connections) {
    	numberOfModules++;
    	while(connections.hasNext())
    		writer.println("    modules["+getIndex(name(key))+"].out++ --> modules["+getIndex(name(connections.next()))+"].in++;");
    	writer.println("");
    }
    
    @Override
    protected void printEnd() {
    	writer.println("  display: \"o=white\"; // affects background...");
    	writer.println("endmodule");
    	writer.println("");
        writer.println("network latticeModularRobot : LatticeModularRobot");
        writer.println("  parameters:");
        writer.println("    nModules = "+numberOfModules+";");
        writer.println("endnetwork");
        writer.flush();
    }

    private String name(Module key) {
        return key.getProperty("name");
    }
    
    private String getIndex(String name){
    	return name.substring(6);
    }

}