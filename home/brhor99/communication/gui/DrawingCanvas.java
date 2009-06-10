package communication.gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;

import communication.filter.CommunicationContainer;

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
	protected int rowCount = 0;
	public static final int DX = 50;
	public static final int DY = 50;
	public static final int DX_MODULE_OFFSET = 45;
	public static final int DY_MODULE_OFFSET = 25;
	public static final int DX_OFFSET = 180;
	public static final int DY_OFFSET = 40;	
	private BufferedImage communicationImage;
	private static final int UPDATE_INTERVAL = 1000;  	// Milliseconds
    private javax.swing.Timer timer;     			 	// Fires to update canvas.
    private Calendar now = Calendar.getInstance();  	// Current time.
    
    private boolean drawPackets = false;
	private boolean drawNormalPackets = false;
	private boolean drawDecimalPackets = false;
	private boolean drawHexaDecimalPackets = false;	
    
	Map<Packet, Module> registry = new HashMap<Packet, Module>();
    private Map<Integer, Integer> modulesToDrawMap = new HashMap<Integer, Integer>();    	
	private CommunicationContainer transmitterReceiverContainer = new CommunicationContainer();
	private CommunicationContainer transmitterReceiverContainerBackup = new CommunicationContainer();
			
	public DrawingCanvas(JMESimulation simulation, int rows, int columns) {
		this.simulation = simulation;
		this.rows = rows;
		this.columns = columns;
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
		return new Dimension(800, 2000);
	}
	
	public boolean getDrawPackes() {
		return drawPackets;
	}
	
	public void setDrawPackes(boolean draw) {
		drawPackets = draw;
	}
		
	public boolean getDrawNormalPackets() {
		return drawNormalPackets;
	}
	
	public void setDrawNormalPackets(boolean draw) {
		drawNormalPackets = draw;
	}
	
	public boolean getDrawDecimalPackets() {
		return drawDecimalPackets;
	}
	
	public void setDrawDecimalPackets(boolean draw) {
		drawDecimalPackets = draw;
	}
	
	public boolean getDrawHexaDecimalPackets() {
		return drawHexaDecimalPackets;
	}
	
	public void setDrawHexaDecimalPackets(boolean draw) {
		drawHexaDecimalPackets = draw;
	}
	
	public Map<Integer, Integer> getModuleToDraw() {
		return modulesToDrawMap;
	}
		
	public void addModuleToDraw(Integer moduleIndex) {
		modulesToDrawMap.put(moduleIndex, moduleIndex);
	}
	
	public void packetReceived(Module module, GenericReceiver receiver, Packet data) {		
        Module from = registry.get(data);
        if(from == null) {
            System.out.println("Unknown source for packet " + data);
        }        
        else {
            String fromName = formatName(from, from.getProperty("name"));
            String toName = formatName(module, module.getProperty("name"));
            int fromID = from.getID();
            int toID = module.getID();
            
            if (modulesToDrawMap.containsKey(new Integer(fromID)) || modulesToDrawMap.containsKey(new Integer(toID))) {
            	System.out.println("Drawing from module #" + fromID + " to #" + toID);
            
            
            
            int xFrom = DX + fromID * columnWidth;
            int yFrom = DY + rowCount * rowHeight;
            int xTo = DX + toID * columnWidth;
            int yTo = DY + rowCount * rowHeight;
            rowCount++;
            
            System.out.println("[" + fromName + "->" + toName +"] "+ data);            
            System.out.println("From " + "(x, y)  = (" + xFrom + ", " + yFrom + ") to (x, y) = (" + xTo + ", " + yTo + ")");
            Point transmitterPoint = new Point(xFrom, yFrom);
            Point receiverPoint = new Point(xTo, yTo);            
                        
            transmitterReceiverContainer.addCommunicationPair(from, module, transmitterPoint, receiverPoint);
            transmitterReceiverContainerBackup.addCommunicationPair(from, module, transmitterPoint, receiverPoint);
            transmitterReceiverContainer.addPacket(data, transmitterPoint, receiverPoint);
            transmitterReceiverContainerBackup.addPacket(data, transmitterPoint, receiverPoint);
            }
            registry.remove(data);
        }
    }

    private String formatName(Module module, String name) {
        return name==null ? ("<Unnamed module "+module+">") : name;
    }
    
    public void packetSent(Module module, GenericTransmitter transmitter, Packet packet) {
        registry.put(packet,module);
    }
		 	
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
            g2a.setStroke(new BasicStroke(2f));
    		int i;
    		int j;    		
    		i = 0;
    		for(Module m : simulation.getModules()) {    			    			
    			drawModuleID(g2a, m, i * columnWidth + DX_MODULE_OFFSET, DY_MODULE_OFFSET);
    			i++;
    		}
    		
    		for(j = 0; j < columns; j++) {
    			g2a.drawLine(j * columnWidth + DX, DY, j * columnWidth + DX, d.height);
    		}
		}
		g2.drawImage(communicationImage, null, 0, 0);
		
		//drawReferenceCommunication(g2);
				
		// Container draw
		if(!transmitterReceiverContainer.getCommunicationMap().isEmpty()) {
			Pair<Point, Point> key = null;
			for(Map.Entry<Pair<Point, Point>, Pair<Module, Module>> entry : transmitterReceiverContainer.getCommunicationMap().entrySet()) {
				key = entry.getKey();
				int xFrom = (int) entry.getKey().fst().getX();
				int yFrom = (int) entry.getKey().fst().getY();
				int xTo = (int) entry.getKey().snd().getX();
				int yTo = (int) entry.getKey().snd().getY();					
				drawArrow(g2, xFrom, yFrom, xTo, yTo, 1.0f);				
			}	
			if (transmitterReceiverContainerBackup.getCommunicationMap().containsKey(key)) {
				transmitterReceiverContainer.getCommunicationMap().remove(key);
			}
			Pair<Point, Point> packetKey = null;
			for (Map.Entry<Pair<Point, Point>, Packet> entry : transmitterReceiverContainer.getPacketMap().entrySet()) {
				packetKey = entry.getKey();
				int xFrom = (int) entry.getKey().fst().getX();
				int yFrom = (int) entry.getKey().fst().getY();
				int xTo = (int) entry.getKey().snd().getX();
				int yTo = (int) entry.getKey().snd().getY();
				int xPacket = 0;
				int yPacket = 0;
				if (xFrom < xTo) {
					xPacket = xFrom + Math.abs(xFrom - xTo) / 2;
					yPacket = yFrom + Math.abs(yFrom - yTo) / 2;
				}
				else {
					xPacket = xTo + Math.abs(xFrom - xTo) / 2;
					yPacket = yTo + Math.abs(yFrom - yTo) / 2;
				}				
				Packet p = entry.getValue();
				drawPacket(g2, p, xPacket, yPacket);
			}
			if (transmitterReceiverContainerBackup.getPacketMap().containsKey(packetKey)) {
				transmitterReceiverContainer.getPacketMap().remove(packetKey);
			}
			
			
		}
		// Container backup draw
		else {
			for(Map.Entry<Pair<Point, Point>, Pair<Module, Module>> entry : transmitterReceiverContainerBackup.getCommunicationMap().entrySet()) {
				int xFrom = (int) entry.getKey().fst().getX();
				int yFrom = (int) entry.getKey().fst().getY();
				int xTo = (int) entry.getKey().snd().getX();
				int yTo = (int) entry.getKey().snd().getY();
				drawArrow(g2, xFrom, yFrom, xTo, yTo, 1.0f);
			}
		}		
	}
       
    private static String convertToHex(Packet packet) {
		StringBuffer buf = new StringBuffer();
		byte[] data = packet.getData();
		for (int i = 0; i < data.length; i++) {
			int halfbyte = (data[i] >>> 4) & 0x0F;
			int two_halfs = 0;
			do {
				if ((0 <= halfbyte) && (halfbyte <= 9))
					buf.append((char) ('0' + halfbyte));
				else
					buf.append((char) ('a' + (halfbyte - 10)));
				halfbyte = data[i] & 0x0F;
			} while (two_halfs++ < 1);
		}
		return buf.toString();
	} 
		
	private static int yCor(int len, double dir) {
		return (int)(len * Math.cos(dir));
	}
	
	private static int xCor(int len, double dir) {
		return (int)(len * Math.sin(dir));
	}
		
	public void drawArrow(Graphics2D g2, int x1, int y1, int x2, int y2, float stroke) {
	      double aDir = Math.atan2(x1 - x2, y1 - y2);
	      g2.drawLine(x2, y2, x1, y1);
	      g2.setStroke(new BasicStroke(1f));			
	      Polygon tmpPoly = new Polygon();
	      int i1 = 12 + (int)(stroke * 2);
	      int i2 = 6 + (int)stroke;			
	      tmpPoly.addPoint(x2, y2);			
	      tmpPoly.addPoint(x2 + xCor(i1, aDir +.5), y2 + yCor(i1, aDir + .5));
	      tmpPoly.addPoint(x2 + xCor(i2, aDir), y2 + yCor(i2, aDir));
	      tmpPoly.addPoint(x2 + xCor(i1, aDir -.5), y2 + yCor(i1, aDir - .5));
	      tmpPoly.addPoint(x2, y2);			
	      g2.drawPolygon(tmpPoly);
	      g2.fillPolygon(tmpPoly);			
	   }
	
	public void drawModuleID(Graphics2D g2, Module module, int x, int y) {
		String moduleID = "#" + Integer.toString(module.getID());
		g2.drawString(moduleID, x, y);
	}
	
	public void drawPacket(Graphics2D g2, Packet packet, int x, int y) {
		if (drawPackets) {
			return;
		}
		else if (drawNormalPackets) {
			String rawString = packet.getData().toString();
			g2.drawString(rawString, x, y);
		}
		else if (drawDecimalPackets) {
			String decimalString = packet.toString();
			g2.drawString(decimalString, x, y);
		}
		else if (drawHexaDecimalPackets) {
			String hexaDecimalString = convertToHex(packet);
			g2.drawString(hexaDecimalString, x, y);
		}
	}

	public void packetLost(Module module, GenericTransmitter transmitter, Packet packet) {
		// TODO Auto-generated method stub
		
	}
	
	/*
	private void drawReferenceCommunication(Graphics2D g2) {
		//From module #0 to module #1 (Message 0)
		drawArrow(g2, DX + 0 * columnWidth, DY + 0 * rowHeight, DX + 1 * columnWidth, DY + 0 * rowHeight, 1.0f);
		//From module #1 to module #3 (Message 1)
		drawArrow(g2, DX + 1 * columnWidth, DY + 1 * rowHeight, DX + 3 * columnWidth, DY + 1 * rowHeight, 1.0f);
		//From module #3 to module #5 (Message 2)
		drawArrow(g2, DX + 3 * columnWidth, DY + 2 * rowHeight, DX + 5 * columnWidth, DY + 2 * rowHeight, 1.0f);
		//From module #5 to module #6 (Message 3)
		drawArrow(g2, DX + 5 * columnWidth, DY + 3 * rowHeight, DX + 6 * columnWidth, DY + 3 * rowHeight, 1.0f);
		//From module #6 to module #4 (Message 4)
		drawArrow(g2, DX + 6 * columnWidth, DY + 4 * rowHeight, DX + 4 * columnWidth, DY + 4 * rowHeight, 1.0f);		
		//From module #4 to module #3 (Message 5)
		drawArrow(g2, DX + 4 * columnWidth, DY + 5 * rowHeight, DX + 3 * columnWidth, DY + 5 * rowHeight, 1.0f);		
		//From module #3 to module #1 (Message 6)
		drawArrow(g2, DX + 3 * columnWidth, DY + 6 * rowHeight, DX + 1 * columnWidth, DY + 6 * rowHeight, 1.0f);		
		//From module #1 to module #3 (Message 7)
		drawArrow(g2, DX + 1 * columnWidth, DY + 7 * rowHeight, DX + 3 * columnWidth, DY + 7 * rowHeight, 1.0f);		
		//From module #3 to module #5 (Message 8)
		drawArrow(g2, DX + 3 * columnWidth, DY + 8 * rowHeight, DX + 5 * columnWidth, DY + 8 * rowHeight, 1.0f);		
		//From module #5 to module #6 (Message 9)
		drawArrow(g2, DX + 5 * columnWidth, DY + 9 * rowHeight, DX + 6 * columnWidth, DY + 9 * rowHeight, 1.0f);		
		//From module #6 to module #4 (Message 10)
		drawArrow(g2, DX + 6 * columnWidth, DY + 10 * rowHeight, DX + 4 * columnWidth, DY + 10 * rowHeight, 1.0f);		
		//From module #4 to module #6 (Message 11)
		drawArrow(g2, DX + 4 * columnWidth, DY + 11 * rowHeight, DX + 6 * columnWidth, DY + 11 * rowHeight, 1.0f);		
		//From module #6 to module #4 (Message 12)
		drawArrow(g2, DX + 6 * columnWidth, DY + 12 * rowHeight, DX + 4 * columnWidth, DY + 12 * rowHeight, 1.0f);		
		//From module #4 to module #3 (Message 13)
		drawArrow(g2, DX + 4 * columnWidth, DY + 13 * rowHeight, DX + 3 * columnWidth, DY + 13 * rowHeight, 1.0f);		
		//From module #4 to module #3 (Message 14)
		drawArrow(g2, DX + 3 * columnWidth, DY + 14 * rowHeight, DX + 1 * columnWidth, DY + 14 * rowHeight, 1.0f);		
		//From module #1 to module #0 (Message 15)
		drawArrow(g2, DX + 1 * columnWidth, DY + 15 * rowHeight, DX + 0 * columnWidth, DY + 15 * rowHeight, 1.0f);		
		//From module #0 to module #6 (Message 16)
		drawArrow(g2, DX + 0 * columnWidth, DY + 16 * rowHeight, DX + 6 * columnWidth, DY + 16 * rowHeight, 1.0f);		
		//From module #6 to module #0 (Message 17)
		drawArrow(g2, DX + 6 * columnWidth, DY + 17 * rowHeight, DX + 0 * columnWidth, DY + 17 * rowHeight, 1.0f);		
		//From module #0 to module #1 (Message 18)
		drawArrow(g2, DX + 0 * columnWidth, DY + 18 * rowHeight, DX + 1 * columnWidth, DY + 18 * rowHeight, 1.0f);		
		//From module #1 to module #0 (Message 19)
		drawArrow(g2, DX + 1 * columnWidth, DY + 19 * rowHeight, DX + 0 * columnWidth, DY + 19 * rowHeight, 1.0f);		
		//From module #0 to module #1 (Message 20)
		drawArrow(g2, DX + 0 * columnWidth, DY + 20 * rowHeight, DX + 1 * columnWidth, DY + 20 * rowHeight, 1.0f);		
		//From module #1 to module #3 (Message 21)
		drawArrow(g2, DX + 1 * columnWidth, DY + 21 * rowHeight, DX + 3 * columnWidth, DY + 21 * rowHeight, 1.0f);		
		//From module #3 to module #5 (Message 22)
		drawArrow(g2, DX + 3 * columnWidth, DY + 22 * rowHeight, DX + 5 * columnWidth, DY + 22 * rowHeight, 1.0f);		
		//From module #5 to module #3 (Message 23)
		drawArrow(g2, DX + 5 * columnWidth, DY + 23 * rowHeight, DX + 3 * columnWidth, DY + 23 * rowHeight, 1.0f);		
		//From module #3 to module #2 (Message 24)
		drawArrow(g2, DX + 3 * columnWidth, DY + 24 * rowHeight, DX + 2 * columnWidth, DY + 24 * rowHeight, 1.0f);		
		//From module #2 to module #3 (Message 25)
		drawArrow(g2, DX + 2 * columnWidth, DY + 25 * rowHeight, DX + 3 * columnWidth, DY + 25 * rowHeight, 1.0f);		
		//From module #3 to module #1 (Message 26)
		drawArrow(g2, DX + 3 * columnWidth, DY + 26 * rowHeight, DX + 1 * columnWidth, DY + 26 * rowHeight, 1.0f);		
		//From module #1 to module #4 (Message 27)
		drawArrow(g2, DX + 1 * columnWidth, DY + 27 * rowHeight, DX + 4 * columnWidth, DY + 27 * rowHeight, 1.0f);		
		//From module #4 to module #1 (Message 28)
		drawArrow(g2, DX + 4 * columnWidth, DY + 28 * rowHeight, DX + 1 * columnWidth, DY + 28 * rowHeight, 1.0f);		
		//From module #1 to module #3 (Message 39)
		drawArrow(g2, DX + 1 * columnWidth, DY + 29 * rowHeight, DX + 3 * columnWidth, DY + 29 * rowHeight, 1.0f);		
		//From module #3 to module #5 (Message 30)
		drawArrow(g2, DX + 3 * columnWidth, DY + 30 * rowHeight, DX + 5 * columnWidth, DY + 30 * rowHeight, 1.0f);		
		//From module #5 to module #6 (Message 31)
		drawArrow(g2, DX + 5 * columnWidth, DY + 31 * rowHeight, DX + 6 * columnWidth, DY + 31 * rowHeight, 1.0f);		
		//From module #6 to module #0 (Message 32)
		drawArrow(g2, DX + 6 * columnWidth, DY + 32 * rowHeight, DX + 0 * columnWidth, DY + 32 * rowHeight, 1.0f);		
		//From module #0 to module #1 (Message 33)
		drawArrow(g2, DX + 0 * columnWidth, DY + 33 * rowHeight, DX + 1 * columnWidth, DY + 33 * rowHeight, 1.0f);		
		//From module #1 to module #0 (Message 34)
		drawArrow(g2, DX + 1 * columnWidth, DY + 34 * rowHeight, DX + 0 * columnWidth, DY + 34 * rowHeight, 1.0f);		
		//From module #0 to module #6 (Message 35)
		drawArrow(g2, DX + 0 * columnWidth, DY + 35 * rowHeight, DX + 6 * columnWidth, DY + 35 * rowHeight, 1.0f);		
		//From module #6 to module #5 (Message 36)
		drawArrow(g2, DX + 6 * columnWidth, DY + 36 * rowHeight, DX + 5 * columnWidth, DY + 36 * rowHeight, 1.0f);		
		//From module #5 to module #3 (Message 37)
		drawArrow(g2, DX + 5 * columnWidth, DY + 37 * rowHeight, DX + 3 * columnWidth, DY + 37 * rowHeight, 1.0f);		
		//From module #3 to module #5 (Message 38)
		drawArrow(g2, DX + 3 * columnWidth, DY + 38 * rowHeight, DX + 5 * columnWidth, DY + 38 * rowHeight, 1.0f);		
		//From module #5 to module #6 (Message 39)
		drawArrow(g2, DX + 5 * columnWidth, DY + 39 * rowHeight, DX + 6 * columnWidth, DY + 39 * rowHeight, 1.0f);		
		//From module #6 to module #0 (Message 40)
		drawArrow(g2, DX + 6 * columnWidth, DY + 40 * rowHeight, DX + 0 * columnWidth, DY + 40 * rowHeight, 1.0f);		
		//From module #0 to module #1 (Message 41)
		drawArrow(g2, DX + 0 * columnWidth, DY + 41 * rowHeight, DX + 1 * columnWidth, DY + 41 * rowHeight, 1.0f);		
		//From module #1 to module #3 (Message 42)
		drawArrow(g2, DX + 1 * columnWidth, DY + 42 * rowHeight, DX + 3 * columnWidth, DY + 42 * rowHeight, 1.0f);		
		//From module #3 to module #1 (Message 43)
		drawArrow(g2, DX + 3 * columnWidth, DY + 43 * rowHeight, DX + 1 * columnWidth, DY + 43 * rowHeight, 1.0f);		
		//From module #1 to module #0 (Message 44)
		drawArrow(g2, DX + 1 * columnWidth, DY + 44 * rowHeight, DX + 0 * columnWidth, DY + 44 * rowHeight, 1.0f);		
		//From module #1 to module #4 (Message 45)
		drawArrow(g2, DX + 1 * columnWidth, DY + 45 * rowHeight, DX + 4 * columnWidth, DY + 45 * rowHeight, 1.0f);		
		//From module #1 to module #3 (Message 46)
		drawArrow(g2, DX + 1 * columnWidth, DY + 46 * rowHeight, DX + 3 * columnWidth, DY + 46 * rowHeight, 1.0f);		
		//From module #0 to module #6 (Message 47)
		drawArrow(g2, DX + 0 * columnWidth, DY + 47 * rowHeight, DX + 6 * columnWidth, DY + 47 * rowHeight, 1.0f);		
		//From module #6 to module #5 (Message 48)
		drawArrow(g2, DX + 6 * columnWidth, DY + 48 * rowHeight, DX + 5 * columnWidth, DY + 48 * rowHeight, 1.0f);		
		//From module #6 to module #2 (Message 49)
		drawArrow(g2, DX + 6 * columnWidth, DY + 49 * rowHeight, DX + 2 * columnWidth, DY + 49 * rowHeight, 1.0f);		
	}
	*/
}	

