package onlineLearning.realAtron.tracking;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Panel;
import java.awt.Transparency;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.util.ArrayList;

import javax.media.Buffer;
import javax.media.CaptureDeviceInfo;
import javax.media.CaptureDeviceManager;
import javax.media.Format;
import javax.media.Manager;
import javax.media.MediaLocator;
import javax.media.Player;
import javax.media.control.FormatControl;
import javax.media.control.FrameGrabbingControl;
import javax.media.format.VideoFormat;
import javax.media.util.BufferToImage;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;

import com.jme.math.Vector2f;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
public class ATRONTrackerGUI extends Panel implements ActionListener 
{
  public static Player player = null;
  public CaptureDeviceInfo di = null;
  public MediaLocator ml = null;
  public JButton capture = null;
  public JButton findATRON = null;
  public Buffer buf = null;
  public BufferedImage img = null;
  public VideoFormat vf = null;
  public BufferToImage btoi = null;
  public ImagePanel imgpanel = null;
  public ATRONTracker tracker;
  static int dim0 = 320;
  static int dim1 = 240;
  static int dim2 = 550;
  
  
  public ATRONTrackerGUI(ATRONTracker tracker) 
  {
	  this.tracker = tracker;
    setLayout(new BorderLayout());
    setSize(dim0,dim2);
    
    imgpanel = new ImagePanel();
    capture = new JButton("Capture");
    capture.addActionListener(this);
    
    findATRON = new JButton("Find ATRONs");
    findATRON.addActionListener(this);
    
    String str1 = "vfw:Logitech USB Video Camera:0";
    String str2 = "vfw:Microsoft WDM Image Capture (Win32):0";
    di = CaptureDeviceManager.getDevice(str2);
    ml = di.getLocator();
    try 
    {
      player = Manager.createRealizedPlayer(ml);
      setFormat();
      player.start();
      
      Component comp;
      
      if ((comp = player.getVisualComponent()) != null)
      {
        add(comp,BorderLayout.NORTH);
      }
      add(capture,BorderLayout.WEST);
      add(findATRON,BorderLayout.EAST);
      add(imgpanel,BorderLayout.SOUTH);
    } 
    catch (Exception e) 
    {
      e.printStackTrace();
    }
  }
 
 public static ATRONTrackerGUI startGUI(ATRONTracker tracker) {
	 Frame f = new Frame("SwingCapture");
	    ATRONTrackerGUI cf = new ATRONTrackerGUI(tracker);
	    
	    f.addWindowListener(new WindowAdapter() {
	      public void windowClosing(WindowEvent e) {
	      playerclose();
	      System.exit(0);}});
	    
	    f.add("Center",cf);
	    f.pack();
	    f.setSize(new Dimension(dim0,dim2));
	    f.setVisible(true);
	    return cf;
 }
  
  public static void main(String[] args) 
  {
    ATRONTrackerGUI.startGUI(new ATRONTracker());
  }
  
  
  public static void playerclose() 
  {
    player.close();
    player.deallocate();
  }
  public void setFormat() {
	  FormatControl fc = (FormatControl) player.getControl("javax.media.control.FormatControl");
	  
	  Format[] formats = fc.getSupportedFormats();
	  for(int i=0;i<formats.length;i++) {
		 //System.out.println(i+": "+formats[i]);
	  }
	  //fc.setFormat(formats[6]);//bad res	  
	  fc.setFormat(formats[8]);//default res
	  //fc.setFormat(formats[10]); //best res
	  System.out.println("Video format: "+fc.getFormat());
  }
  public BufferedImage grapFrame() {
	// Grab a frame
      FrameGrabbingControl fgc = (FrameGrabbingControl)
      player.getControl("javax.media.control.FrameGrabbingControl");
      buf = fgc.grabFrame();
      
      // Convert it to an image
      btoi = new BufferToImage((VideoFormat)buf.getFormat());
      img = (BufferedImage) btoi.createImage(buf);
      return img;
  }
 
  public void actionPerformed(ActionEvent e) 
  {
    JComponent c = (JComponent) e.getSource();
    
    if (c == capture) 
    {
    	img = grapFrame();
      // show the image
      imgpanel.setImage(img.getScaledInstance(dim0, dim1, 24));
      
      // save image
      saveJPG(img,"c:\\test.jpg");
    }
    if(c == findATRON) {
    	System.out.println("Finding ATRONS");
    	findATRON();
    	System.out.println("..done");
    }
  }
  public Vector2f findATRON() {
	  img = grapFrame();
	  Vector2f pos = tracker.findATRONPos(img);
	  if(pos!=null) drawDot(pos,img);
	  imgpanel.setImage(img.getScaledInstance(dim0, dim1, 24));
	  return pos;
  }
  
  private void drawDot(Vector2f pos,BufferedImage bImage) {
	  Graphics2D graphics = bImage.createGraphics();
	  graphics.setColor(Color.RED);
	  graphics.fillOval((int)pos.x,(int)pos.y, 3, 3);
  }
  class ImagePanel extends Panel 
  {
    public Image myimg = null;
    
    public ImagePanel() 
    {
      setLayout(null);
      setSize(dim0,dim1);
    }
    
    public void setImage(Image img) 
    {
      this.myimg = img;
      repaint();
    }
    
    public void paint(Graphics g) 
    {
      if (myimg != null) 
      {
        g.drawImage(myimg, 0, 0, this);
      }
    }
  }
  
 
  public static void saveJPG(Image img, String s)
  {
    BufferedImage bi = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_RGB);
    Graphics2D g2 = bi.createGraphics();
    g2.drawImage(img, null, null);
 
    FileOutputStream out = null;
    try
    { 
      out = new FileOutputStream(s); 
    }
    catch (java.io.FileNotFoundException io)
    { 
      System.out.println("File Not Found"); 
    }
    
    JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
    JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(bi);
    param.setQuality(1f,false);
    encoder.setJPEGEncodeParam(param);
    
    try 
    { 
      encoder.encode(bi); 
      out.close(); 
    }
    catch (java.io.IOException io) 
    {
      System.out.println("IOException"); 
    }
  }
  
}
