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
    
    public NEDTopologyWriter(PrintWriter writer) {
        this.writer = writer;
    }

    private String name(Module key) {
        return key.getProperty("name");
    }

    @Override
    protected void printStart() {
        writer.println("<topology>");
    }
    
    @Override
    protected void printEntry(Module key, Iterator<Module> connections) {
        writer.println(" <node name=\""+name(key)+"\">");
        while(connections.hasNext())
            writer.println("  <connection name=\""+name(connections.next())+"\">");
        writer.println(" </node>");
    }
    
    @Override
    protected void printEnd() {
        writer.println("</topology>");
        writer.flush();
    }


}