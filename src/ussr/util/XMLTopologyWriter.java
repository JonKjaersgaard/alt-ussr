package ussr.util;

import java.io.PrintWriter;
import java.util.Iterator;

import ussr.model.Module;
import ussr.samples.tests.AbstractTopologyWriter;

public class XMLTopologyWriter extends AbstractTopologyWriter {

    private PrintWriter writer;
    
    public XMLTopologyWriter(PrintWriter writer) {
        this.writer = writer;
    }

    @Override
    protected void printEnd() {
        writer.println("</topology>");
        writer.flush();
    }

    @Override
    protected void printEntry(Module key, Iterator<Module> connections) {
        writer.println(" <node name=\""+name(key)+"\">");
        while(connections.hasNext())
            writer.println("  <connection name=\""+name(connections.next())+"\">");
        writer.println(" </node>");
    }

    private String name(Module key) {
        return key.getProperty("name");
    }

    @Override
    protected void printStart() {
        writer.println("<topology>");
    }

}
