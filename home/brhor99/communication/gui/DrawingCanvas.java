package communication.gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import java.awt.Point;
import java.awt.Polygon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.swing.JPanel;

import ussr.comm.CommunicationMonitor;
import ussr.comm.GenericReceiver;
import ussr.comm.GenericTransmitter;
import ussr.comm.Packet;
import ussr.model.Module;
import ussr.physics.PhysicsFactory;
import ussr.physics.jme.JMESimulation;
import ussr.util.Pair;

public class DrawingCanvas extends JPanel implements CommunicationMonitor {

	private static final long serialVersionUID = 1L;
	protected JMESimulation simulation;
	protected int rows;
	protected int columns;
	protected int rowHeight;
	protected int columnWidth;
	protected int mouseClickX;
	protected int mouseClickY;

	
	protected int[][] grid;
	public static final int DX = 50;
	public static final int DY = 50;
	public static final int DX_MODULE_OFFSET = 45;
	public static final int DY_MODULE_OFFSET = 25;
	public static final int DX_OFFSET = 180;
	public static final int DY_OFFSET = 40;	
	private Graphics2D graphic;
	private BufferedImage communicationImage;
	private int communicationCount = 0;
	private static final int UPDATE_INTERVAL = 1000;  	// Milliseconds
    private javax.swing.Timer timer;     			 	// Fires to update clock.
    private Calendar now = Calendar.getInstance();  	// Current time.
	
	Map<Packet, Module> registry = new HashMap<Packet, Module>();
	Map<ModuleCanvasPosition, Module> positionMap = new HashMap<ModuleCanvasPosition, Module>();
	Map<Point, Module> positions = new HashMap<Point, Module>();
	Map<Point, Point> transmitterRecieverMap = new HashMap<Point, Point>();
	List<Pair<Point, Point>> transmitterReceiverList = new ArrayList<Pair<Point, Point>>();
	List<Pair<Point, Point>> transmitterReceiverListBackup = new ArrayList<Pair<Point, Point>>();

		
	public DrawingCanvas(JMESimulation simulation, int rows, int columns) {
		this.simulation = simulation;
		this.rows = rows;
		this.columns = columns;
		grid = new int[rows][columns];
		setSize(getPreferredSize());
		DrawingCanvas.this.setBackground(Color.white);
		setOpaque(true);
		PhysicsFactory.getOptions().addCommunicationMonitor(this);
		timer = new javax.swing.Timer(UPDATE_INTERVAL, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateTime();
                repaint();
            }
        });

	}
	
    public void start() {
        timer.start(); 
    }
    
    public void stop() {
        timer.stop(); 
    }
        
    private void updateTime() {
        // Avoid creating new objects.
        now.setTimeInMillis(System.currentTimeMillis());
    }

	
	public int getRows() {
		return rows;
	}
	
	public int getColumns() {
		return columns;
	}
	
	public int getRowHeight() {
		return rowHeight;
	}
	
	public int getColumnWidth() {
		return columnWidth;
	}
	
	public int getMouseClickX() {
		return mouseClickX;
	}
		
	public void setMouseClickX(int mouseClickX) {
		this.mouseClickX = mouseClickX;
	}
	
	public int getMouseClickY() {
		return mouseClickY;
	}
	
	public void setMouseClickY(int mouseClickY) {
		this.mouseClickY = mouseClickY;
	}

	public Dimension getPreferredSize() {
		return new Dimension(800, 800);
	}
	
	public void packetReceived(Module module, GenericReceiver receiver, Packet data) {
        Module from = registry.get(data);
        if(from == null) {
            System.out.println("Unknown source for packet " + data);
    		//textArea.append("Unknown source for packet "  + data + "\n");
        }        
        else {
            String fromName = formatName(from, from.getProperty("name"));
            String toName = formatName(module, module.getProperty("name"));
            int fromID = from.getID();
            int toID = module.getID();
            System.out.println("[" + fromName + "->" + toName +"] "+ data);
            // fromID -> toID
            int xFrom = DX + fromID * getColumnWidth();
            int yFrom = DY + fromID * getRowHeight();
            int xTo = DX + toID * getColumnWidth();
            int yTo = DY + toID * getRowHeight();
            Point transmitterPoint = new Point(xFrom, yFrom);
            Point receiverPoint = new Point(xTo, yTo);            
            transmitterReceiverList.add(new Pair<Point, Point>(transmitterPoint, receiverPoint));
            transmitterReceiverListBackup.add(new Pair<Point, Point>(transmitterPoint, receiverPoint));
            communicationCount++;
            //Pair transmitterReceiverPair = new Pair<new Point(xFrom, yFrom), new Point(xTo, yTo)>();
            
            //transmitterRecieverMap.put(transmitterPoint, receiverPoint);

            //textArea.append(("[" + fromName + "->" + toName +"] "+ data + "\n"));
            //canvas.drawArrow(xCenter, yCenter, x, y, stroke)
            registry.remove(data);
        }
    }

    private String formatName(Module module, String name) {
        return name==null ? ("<Unnamed module "+module+">") : name;
    }
    
    public void packetSent(Module module, GenericTransmitter transmitter, Packet packet) {
        registry.put(packet,module);
    }
	
	 
	
	/*
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		graphic = (Graphics2D) g;

		graphic.drawRect(25, 25, 80, 30);
		graphic.drawString("#0", DX + (0 * DX_OFFSET), DY + (0 * DY_OFFSET));
		graphic.drawString("#3", DX + (1 * DX_OFFSET), DY + (0 * DY_OFFSET));
		graphic.drawString("#6", DX + (2 * DX_OFFSET), DY + (0 * DY_OFFSET));
		graphic.drawString("#7", DX + (3 * DX_OFFSET), DY + (0 * DY_OFFSET));
		graphic.drawString("#9", DX + (4 * DX_OFFSET), DY + (0 * DY_OFFSET));

		graphic.drawLine(DX + (0 * DX_OFFSET), DY + (1 * DY_OFFSET), DX + (1 * DX_OFFSET), DY + (1 * DY_OFFSET));
		graphic.drawLine(DX + (1 * DX_OFFSET), DY + (2 * DY_OFFSET), DX + (2 * DX_OFFSET), DY + (2 * DY_OFFSET));
		graphic.drawLine(DX + (2 * DX_OFFSET), DY + (3 * DY_OFFSET), DX + (3 * DX_OFFSET), DY + (3 * DY_OFFSET));
		graphic.drawLine(DX + (3 * DX_OFFSET), DY + (4 * DY_OFFSET), DX + (4 * DX_OFFSET), DY + (4 * DY_OFFSET));
	}
	*/
	
    public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		Dimension d = getSize();		
		rowHeight = d.height / rows;
		columnWidth = d.width / columns;
		
		if(communicationImage == null) {
			communicationImage = (BufferedImage) (this.createImage(d.width, d.height));
			Graphics2D g2a = communicationImage.createGraphics();
            g2a.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

    		int i, j;		
    		/*
    		for(i = 1; i <  rows; i++) {
    			g.drawLine(0, i * rowHeight, d.width, i * rowHeight);
    		}
    		*/
    		int k = 0;
    		for(Module m : simulation.getModules()) {    			    			
    			drawModuleID(g2a, m, k * columnWidth + DX_MODULE_OFFSET, DY_MODULE_OFFSET);
    			k++;
    		}
    		
    		for(j = 0; j < columns; j++) {
    			g2a.drawLine(j * columnWidth + DX, DY, j * columnWidth + DX, d.height);
    		}
		}
		g2.drawImage(communicationImage, null, 0, 0);
		
		
		// If new packet - drawArrow from transmitter module to receiver module
		// Draw communication at runtime 
		if (!transmitterReceiverList.isEmpty()) {
			for (Pair<Point, Point> transmitterReceiverPair : transmitterReceiverList) {
				int xFrom = (int) transmitterReceiverPair.fst().getX();
				int yFrom = (int) transmitterReceiverPair.fst().getY();
				int xTo = (int) transmitterReceiverPair.snd().getX();
				int yTo = (int) transmitterReceiverPair.snd().getY();
				// g2.drawLine(xFrom, yFrom, xTo, yTo);
				drawArrow(g2, xFrom, yFrom, xTo, yTo, 1.0f);
			}
			transmitterReceiverList.remove(0);
		}
		// Use backup communication list to keep canvas updated after the communication has stopped
		else {
			System.out.println("nu er jeg tom");
		}
		
		/*
		int cnt = 0;
		while(!transmitterReceiverList.isEmpty()) {
			System.out.println(cnt);
			int xFrom = (int) transmitterReceiverList.get(0).fst().getX();
			int yFrom = (int) transmitterReceiverList.get(0).fst().getY();
			int xTo = (int) transmitterReceiverList.get(0).snd().getX();
			int yTo = (int) transmitterReceiverList.get(0).snd().getY();
			transmitterReceiverList.remove(0);
			//drawArrow(g2, xFrom, yFrom, xTo, yTo, 1.0f);
			g2.drawLine(xFrom, yFrom, xTo, yTo);
			cnt++;
		}
		*/
		
		//g2.dispose();
	}
	
	/*
	public void paint(Graphics g) {
		super.paint(g);
		graphic = (Graphics2D) g;
		Dimension d = getSize();
		rowHeight = d.height / rows;
		columnWidth = d.width / columns;		
		int i, j;		
	
		int k = 0;
		for(Module m : simulation.getModules()) {
			int x = k * columnWidth + DX_MODULE_OFFSET;
			int y = DY_MODULE_OFFSET;
			
			
			drawModuleID(m, k * columnWidth + DX_MODULE_OFFSET, DY_MODULE_OFFSET);
			k++;
		}
		
		for(j = 0; j < columns; j++) {
			g.drawLine(j * columnWidth + DX, DY, j * columnWidth + DX, d.height);

		}
		
		//If new packet - drawArrow from transmitter module to receiver module
		int cnt = 0;
		if(!transmitterReceiverList.isEmpty()) {
			System.out.println(cnt);
			int xfrom = (int) transmitterReceiverList.get(0).fst().getX();
			int yfrom = (int) transmitterReceiverList.get(0).fst().getY();
			int xto = (int) transmitterReceiverList.get(0).snd().getX();
			int yto = (int) transmitterReceiverList.get(0).snd().getY();
			transmitterReceiverList.remove(0);
			drawArrow(xfrom, yfrom, xto, yto, 1.0f);
			cnt++;
		}
	}
*/
	
	private static int yCor(int len, double dir) {
		return (int)(len * Math.cos(dir));
	}
	
	private static int xCor(int len, double dir) {
		return (int)(len * Math.sin(dir));
	}
		
	public void drawArrow(int xCenter, int yCenter, int x, int y, float stroke) {
	      double aDir = Math.atan2(xCenter - x, yCenter - y);
	      graphic.drawLine(x, y, xCenter, yCenter);
	      graphic.setStroke(new BasicStroke(1f));			// make the arrow head solid even if dash pattern has been specified
	      Polygon tmpPoly = new Polygon();
	      int i1 = 12 + (int)(stroke * 2);
	      int i2 = 6 + (int)stroke;							// make the arrow head the same size regardless of the length
	      tmpPoly.addPoint(x, y);							// arrow tip
	      tmpPoly.addPoint(x + xCor(i1, aDir +.5), y + yCor(i1, aDir + .5));
	      tmpPoly.addPoint(x + xCor(i2, aDir), y + yCor(i2, aDir));
	      tmpPoly.addPoint(x + xCor(i1, aDir -.5), y +yCor(i1, aDir - .5));
	      tmpPoly.addPoint(x, y);							// arrow tip
	      graphic.drawPolygon(tmpPoly);
	      graphic.fillPolygon(tmpPoly);						// remove this line to leave arrow head unpainted
	      //repaint();
	   }
	
	public void drawArrow(Graphics2D g2, int xCenter, int yCenter, int x, int y, float stroke) {
	      double aDir = Math.atan2(xCenter - x, yCenter - y);
	      g2.drawLine(x, y, xCenter, yCenter);
	      g2.setStroke(new BasicStroke(1f));			// make the arrow head solid even if dash pattern has been specified
	      Polygon tmpPoly = new Polygon();
	      int i1 = 12 + (int)(stroke * 2);
	      int i2 = 6 + (int)stroke;							// make the arrow head the same size regardless of the length
	      tmpPoly.addPoint(x, y);							// arrow tip
	      tmpPoly.addPoint(x + xCor(i1, aDir +.5), y + yCor(i1, aDir + .5));
	      tmpPoly.addPoint(x + xCor(i2, aDir), y + yCor(i2, aDir));
	      tmpPoly.addPoint(x + xCor(i1, aDir -.5), y +yCor(i1, aDir - .5));
	      tmpPoly.addPoint(x, y);							// arrow tip
	      g2.drawPolygon(tmpPoly);
	      g2.fillPolygon(tmpPoly);						// remove this line to leave arrow head unpainted
	      //repaint();
	   }
		
	public void drawModuleID(Module module, int x, int y) {
		String moduleID = "#" + Integer.toString(module.getID());
		graphic.drawString(moduleID, x, y);
		repaint();
	}
	
	public void drawModuleID(Graphics2D g2, Module module, int x, int y) {
		String moduleID = "#" + Integer.toString(module.getID());
		g2.drawString(moduleID, x, y);
		//repaint();
	}
	
	/*
	public void drawLine(int x1, int y1, int x2, int y2) {
		graphic.drawLine(x1, y1, x2, y2);
		repaint();
	}
	*/

	public void drawPacket(String s, int x, int y, int width, int height) {
		graphic.drawRect(x, y, width, height);
		graphic.drawString(s, x, y);
		//repaint();
	}
	
	private void drawSomeArrows() {
		//From module #0 to module #1
		drawArrow(DX + 0 * columnWidth, DY + 0 * rowHeight, DX + 1 * columnWidth, DY + 0 * rowHeight, 1.0f);
		//From module #1 to module #2
		drawArrow(DX + 1 * columnWidth, DY + 1 * rowHeight, DX + 2 * columnWidth, DY + 1 * rowHeight, 1.0f);
		//From module #2 to module #3
		drawArrow(DX + 2 * columnWidth, DY + 2 * rowHeight, DX + 3 * columnWidth, DY + 2 * rowHeight, 1.0f);
		//From module #3 to module #4
		drawArrow(DX + 3 * columnWidth, DY + 3 * rowHeight, DX + 4 * columnWidth, DY + 3 * rowHeight, 1.0f);
		//From module #5 to module 0#
		drawArrow(DX + 5 * columnWidth, DY + 4 * rowHeight, DX + 0 * columnWidth, DY + 4 * rowHeight, 1.0f);		
	}
	
	private class ModuleCanvasPosition {
		private Module module;
		private int modulePositionX;
		private int modulePositionY;
		
		public ModuleCanvasPosition(Module module, int modulePositionX, int modulePositionY) {
			this.module = module;
			this.modulePositionX = modulePositionX;
			this.modulePositionY = modulePositionY;
		}		
	}
}	

