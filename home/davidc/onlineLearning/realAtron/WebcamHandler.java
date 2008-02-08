package onlineLearning.realAtron;

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

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
public class WebcamHandler extends Panel implements ActionListener 
{
  public static Player player = null;
  public CaptureDeviceInfo di = null;
  public MediaLocator ml = null;
  public JButton capture = null;
  public JButton findATRON = null;
  public Buffer buf = null;
  public Image img = null;
  public VideoFormat vf = null;
  public BufferToImage btoi = null;
  public ImagePanel imgpanel = null;
  static int dim0 = 320;
  static int dim1 = 240;
  static int dim2 = 550;
  
  
  public WebcamHandler() 
  {
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
 
 
  
  public static void main(String[] args) 
  {
    Frame f = new Frame("SwingCapture");
    WebcamHandler cf = new WebcamHandler();
    
    f.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
      playerclose();
      System.exit(0);}});
    
    f.add("Center",cf);
    f.pack();
    f.setSize(new Dimension(dim0,dim2));
    f.setVisible(true);
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
	  fc.setFormat(formats[6]);//bad res	  
	  //fc.setFormat(formats[8]);//default res
	  //fc.setFormat(formats[10]); //best res
	  System.out.println("Video format: "+fc.getFormat());
  }
  public Image grapFrame() {
	// Grab a frame
      FrameGrabbingControl fgc = (FrameGrabbingControl)
      player.getControl("javax.media.control.FrameGrabbingControl");
      buf = fgc.grabFrame();
      
      // Convert it to an image
      btoi = new BufferToImage((VideoFormat)buf.getFormat());
      img = btoi.createImage(buf);
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
  public void findATRON() {
	  img = grapFrame();
	  System.out.println("Image size "+img.getHeight(null)+" "+img.getWidth(null));
	  BufferedImage bImage = toBufferedImage(img);
	  threasHold(bImage);
	  ArrayList<Blob> blobs = BlobDetector.detectBlobs(bImage);
	  int index = findATRONBlob(bImage,blobs);
	  drawDotOnBlob(blobs.get(index),bImage);
	  Pixel p = blobs.get(index).getCenterOfMassPixel();
	  System.out.println("ATRON at ("+p.x+", "+p.y+") with size "+blobs.get(index).getPixelCount());
	  imgpanel.setImage(bImage.getScaledInstance(dim0, dim1, 24));
  }
  private void drawDotOnBlob(Blob blob,BufferedImage bImage) {
	  Pixel p = blob.getCenterOfMassPixel();
	  Graphics2D graphics = bImage.createGraphics();
	  graphics.setColor(Color.RED);
	  graphics.fillOval(p.x, p.y, 3, 3);
  }
  private int findATRONBlob(BufferedImage bImage, ArrayList<Blob> blobs) {
	  int biggestBlobIndex = 0;
	  int biggestBlobSize = 0;
	  for(int i=0;i<blobs.size();i++) {
		  if(blobs.get(i).getPixelCount()>biggestBlobSize) {
			  biggestBlobIndex=i;
			  biggestBlobSize=blobs.get(i).getPixelCount();
		  }
	  }
	  if(biggestBlobSize<100||biggestBlobSize>1000) 
		  System.out.println("ATRON blob has streange size "+biggestBlobSize);
	  return biggestBlobIndex;
  
  }

  private void threasHold(BufferedImage bImage) {
	  for(int x=0;x<bImage.getWidth();x++) {
		  for(int y=0;y<bImage.getHeight();y++) {
			  int pixel = bImage.getRGB(x, y);
			  int red = (pixel >> 16) & 0xff;
			  int green = (pixel >> 8) & 0xff;
			  int blue = pixel & 0xff;
			  double brightness = Math.sqrt(red*red+green*green+blue*blue);
			  //if(blue<180) bImage.setRGB(x, y, 0x0fffffff);
			  //System.out.println(brightness);
			  if(brightness<200)bImage.setRGB(x, y, 0x0fffffff);
			  else {
				  //System.out.println(brightness);
				  bImage.setRGB(x, y, 0);
			  }
		}
	}
}



//This method returns a buffered image with the contents of an image
  public static BufferedImage toBufferedImage(Image image) {
      if (image instanceof BufferedImage) {
          return (BufferedImage)image;
      }
  
      // This code ensures that all the pixels in the image are loaded
      image = new ImageIcon(image).getImage();
  
      // Determine if the image has transparent pixels; for this method's
      // implementation, see e661 Determining If an Image Has Transparent Pixels
      boolean hasAlpha = false;//hasAlpha(image);
  
      // Create a buffered image with a format that's compatible with the screen
      BufferedImage bimage = null;
      GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
      try {
          // Determine the type of transparency of the new buffered image
          int transparency = Transparency.OPAQUE;
          if (hasAlpha) {
              transparency = Transparency.BITMASK;
          }
  
          // Create the buffered image
          GraphicsDevice gs = ge.getDefaultScreenDevice();
          GraphicsConfiguration gc = gs.getDefaultConfiguration();
          bimage = gc.createCompatibleImage(
              image.getWidth(null), image.getHeight(null), transparency);
      } catch (HeadlessException e) {
          // The system does not have a screen
      }
  
      if (bimage == null) {
          // Create a buffered image using the default color model
          int type = BufferedImage.TYPE_INT_RGB;
          if (hasAlpha) {
              type = BufferedImage.TYPE_INT_ARGB;
          }
          bimage = new BufferedImage(image.getWidth(null), image.getHeight(null), type);
      }
  
      // Copy image to buffered image
      Graphics g = bimage.createGraphics();
  
      // Paint the image onto the buffered image
      g.drawImage(image, 0, 0, null);
      g.dispose();
  
      return bimage;
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
