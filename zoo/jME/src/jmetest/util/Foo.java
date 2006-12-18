package jmetest.util;

import java.io.IOException;

import com.jme.util.export.JMEExporter;
import com.jme.util.export.JMEImporter;
import com.jme.util.export.Savable;

public class Foo implements Savable {
    int x = 0;
    Bar y = null;
    Bar z = null;
    
    public Foo() {
        
    }
    
    public void write(JMEExporter e) {
        try {
            e.getCapsule(this).write(x, "x", 0);
            e.getCapsule(this).write(y, "y", null);
            e.getCapsule(this).write(z, "z", null);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void read(JMEImporter e) {
        try {
            x = e.getCapsule(this).readInt("x", 0);
            y = (Bar)e.getCapsule(this).readSavable("y", null);
            z = (Bar)e.getCapsule(this).readSavable("z", null);
            
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public Class getClassTag() {
        return this.getClass();
    }
}