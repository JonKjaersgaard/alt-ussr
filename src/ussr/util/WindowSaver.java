package ussr.util;

import java.awt.AWTEvent;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import javax.swing.JFrame;

public class WindowSaver implements AWTEventListener {

    private static WindowSaver saver;
    private Map framemap;
    private final static String fileName = "guisettings.cfg";
    
	private WindowSaver( ) {
        framemap = new HashMap( );
    }

    public static WindowSaver getInstance( ) {
        if(saver == null) {
            saver = new WindowSaver( );
        }
        return saver;
    }

	public void eventDispatched(AWTEvent evt) {
		try {
            if(evt.getID( ) == WindowEvent.WINDOW_OPENED) {
            	//System.out.println("Got open frame event...! ");
                ComponentEvent cev = (ComponentEvent)evt;
                if(cev.getComponent( ) instanceof JFrame) {
                    JFrame frame = (JFrame)cev.getComponent( );
                    loadSettings(frame);
                }        
            }
            else  if(evt.getID()== WindowEvent.WINDOW_CLOSING) {
            	//System.out.println("Got closing frame event...! ");
            	saveSettings();   
            }
            else {
            	//System.out.println("Got another event...! "+evt);
            }
        }catch(Exception ex) {
            System.out.println(ex.toString( ));
        }
	}
	
	public static void loadSettings(JFrame frame) throws IOException {
        Properties settings = new Properties( );
        File file = new File(fileName);
        file.createNewFile();
        settings.load(new FileInputStream(file));
        
        String name = frame.getName( );
        int x = getInt(settings,name+".x",frame.getX());
        int y = getInt(settings,name+".y",frame.getY());
        int w = getInt(settings,name+".w",frame.getWidth());
        int h = getInt(settings,name+".h",frame.getHeight());
        frame.setLocation(x,y);
        frame.setSize(new Dimension(w,h));
        saver.framemap.put(name,frame);
        frame.validate( );
    }
	public static int getInt(Properties props, String name, int value) { 
		String v = props.getProperty(name); 
		if(v == null) {
			return value;
		}
		return Integer.parseInt(v);

	}
	public static void saveSettings( ) throws IOException {
        Properties settings = new Properties( );
        settings.load(new FileInputStream(fileName));

        Iterator it = saver.framemap.keySet( ).iterator( );
        while(it.hasNext( )) {    
            String name = (String)it.next( ); 
            JFrame frame = (JFrame)saver.framemap.get(name);    
            settings.setProperty(name+".x",""+frame.getX( ));    
            settings.setProperty(name+".y",""+frame.getY( ));    
            settings.setProperty(name+".w",""+frame.getWidth( ));    
            settings.setProperty(name+".h",""+frame.getHeight( ));
        } 
        settings.store(new FileOutputStream(fileName),null); 
    }

	public static void init() {
		Toolkit tk = Toolkit.getDefaultToolkit( );
        tk.addAWTEventListener(WindowSaver.getInstance(), AWTEvent.WINDOW_EVENT_MASK);
	}
}
