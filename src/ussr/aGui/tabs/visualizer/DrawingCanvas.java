package ussr.aGui.tabs.visualizer;

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
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JPanel;


import ussr.comm.CommunicationMonitor;
import ussr.comm.GenericReceiver;
import ussr.comm.GenericTransmitter;
import ussr.comm.Packet;
import ussr.comm.monitors.visualtracker.CommunicationContainer;
import ussr.model.Module;
import ussr.physics.PhysicsFactory;
import ussr.physics.jme.JMESimulation;
import ussr.util.Pair;

public class DrawingCanvas extends JPanel implements CommunicationMonitor {

	private static final long serialVersionUID = 1L;
	/*protected JMESimulation simulation;*/
	//private List<Module> modules;
	private List<Integer> idsModules;
	
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
    private Set<Integer> modulesToDraw = new HashSet<Integer>();    	
	private CommunicationContainer transmitterReceiverContainer = new CommunicationContainer();
	private CommunicationContainer transmitterReceiverContainerBackup = new CommunicationContainer();
			
	public DrawingCanvas(List<Integer> idsModules,/*JMESimulation simulation,*/ int rows, int columns) {
		/*this.simulation = simulation;*/
		this.idsModules = idsModules;
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
	
	public Set<Integer> getModuleToDraw() {
		return modulesToDraw;
	}
		
	public void addModuleToDraw(Integer moduleIndex) {
		modulesToDraw.add(moduleIndex);
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
            
            if (modulesToDraw.contains(fromID) || modulesToDraw.contains(toID)) {
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
    		for(Integer integer: idsModules) {    			    			
    			drawModuleID(g2a, integer, i * columnWidth + DX_MODULE_OFFSET, DY_MODULE_OFFSET);
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
		
	/**
	 * Draws communication arrow from one module to another.
	 * @param g2
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @param stroke
	 */
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
	
	public void drawModuleID(Graphics2D g2, int moduleID, int x, int y) {
		String moduleIDFormat = "#" + Integer.toString(moduleID);
		g2.drawString(moduleIDFormat, x, y);
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

}	

