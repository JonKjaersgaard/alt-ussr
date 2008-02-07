/*
 * To run from commandline: java -classpath /Users/ups/eclipse_workspace/ussr/zoo/jME/lib/lwjgl.jar:. -Djava.library.path=/Users/ups/eclipse_workspace/ussr/zoo/jME/lib  mousetest.Main
 */

package onlineLearning.realAtron;

import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.JFrame;
import javax.swing.JLabel;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import com.jme.math.Vector2f;

public class MouseVelocityHandler extends JFrame implements MouseWheelListener {

    public static final boolean SCROLL_WHEEL = false;
    
    public static void main(String arg[]) {
        new MouseVelocityHandler();
    }
    
    public MouseVelocityHandler() {
        super("Mouseometer");
        this.add(new JLabel("Place mouse here for scroll wheel"));
        this.pack();
        this.setVisible(true);
        this.setSize(300,50);
        if(SCROLL_WHEEL) 
            this.addMouseWheelListener(this);
        else
        	setupMouseDXY();
    }
    
    public void setupMouseDXY() {
        try {
            DisplayMode mode = new DisplayMode(100,100);
            Display.create();
            Display.setDisplayMode(mode);
            Mouse.create();
            Mouse.setGrabbed(true);
            
        } catch (LWJGLException e) {
            throw new Error(e);
        } 
    }
    
    public Vector2f poll() {
    	Mouse.poll();
    	int dx = Mouse.getDX(), dy = Mouse.getDY();
    	return new Vector2f(dx,dy);
    }

    public void mouseWheelMoved(MouseWheelEvent e) {
        System.out.println("Mouse wheel moved at speed: "+e.getWheelRotation());
    }
}
