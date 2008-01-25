/*
 * To run from commandline: java -classpath /Users/ups/eclipse_workspace/ussr/zoo/jME/lib/lwjgl.jar:. -Djava.library.path=/Users/ups/eclipse_workspace/ussr/zoo/jME/lib  mousetest.Main
 */

package mousetest;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

public class Main extends JFrame implements MouseWheelListener {

    public static final boolean SCROLL_WHEEL = false;
    
    public static void main(String arg[]) {
        new Main();
    }
    
    public Main() {
        super("Mouseometer");
        this.add(new JLabel("Place mouse here for scroll wheel"));
        this.pack();
        this.setVisible(true);
        this.setSize(300,50);
        if(SCROLL_WHEEL) 
            this.addMouseWheelListener(this);
        else
            pollMouseDXY();
    }
    
    public void pollMouseDXY() {
        try {
            DisplayMode mode = new DisplayMode(100,100);
            Display.create();
            Display.setDisplayMode(mode);
            Mouse.create();
            Mouse.setGrabbed(true);
            while(true) {
                Mouse.poll();
                int dx = Mouse.getDX(), dy = Mouse.getDY();
                System.out.println("Mouse dx="+dx+" dy="+dy+" speed="+Math.sqrt(dx*dx+dy*dy));
                Thread.sleep(100);
            }
        } catch (LWJGLException e) {
            throw new Error(e);
        } catch (InterruptedException e) {
            throw new Error(e);
        }
    }

    public void mouseWheelMoved(MouseWheelEvent e) {
        System.out.println("Mouse wheel moved at speed: "+e.getWheelRotation());
    }
    
}
