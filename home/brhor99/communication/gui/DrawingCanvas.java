package communication.gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;

import javax.swing.JPanel;

import ussr.model.Module;
import ussr.physics.jme.JMESimulation;

public class DrawingCanvas extends JPanel {

	private static final long serialVersionUID = 1L;
	protected JMESimulation simulation;
	protected int rows;
	protected int columns;
	protected int rowHeight;
	protected int columnWidth;
	protected int[][] grid;
	public static final int DX = 50;
	public static final int DY = 50;
	public static final int DX_MODULE_OFFSET = 45;
	public static final int DY_MODULE_OFFSET = 25;
	public static final int DX_OFFSET = 180;
	public static final int DY_OFFSET = 40;
	private Graphics2D graphic;
		
	public DrawingCanvas(JMESimulation simulation, int rows, int columns) {
		this.simulation = simulation;
		this.rows = rows;
		this.columns = columns;
		grid = new int[rows][columns];
		setSize(getPreferredSize());
		DrawingCanvas.this.setBackground(Color.white);
	}
	
	public int getRows() {
		return rows;
	}
	
	public int getColumn() {
		return columns;
	}

	public Dimension getPreferredSize() {
		return new Dimension(800, 800);
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
	
	public void paint(Graphics g) {
		super.paint(g);
		graphic = (Graphics2D) g;
		Dimension d = getSize();
		//System.out.println("Dimension height " + d.height);
		//System.out.println("Dimension width " + d.width);
		rowHeight = d.height / rows;
		columnWidth = d.width / columns;
		//System.out.println("Row height : " + rowHeight);
		//System.out.println("Column width : " + columnWidth);
		
		drawArrow(10, 10, 70, 10, 2.0f);
		drawArrow(80, 10, 150, 10, 1.0f);
		
		int i, j;		
		/*
		for(i = 1; i <  rows; i++) {
			g.drawLine(0, i * rowHeight, d.width, i * rowHeight);
		}
		*/
		int k = 0;
		for(Module m : simulation.getModules()) {
			drawModuleID(m, k * columnWidth + DX_MODULE_OFFSET, DY_MODULE_OFFSET);
			k++;
		}
		
		for(j = 0; j < columns; j++) {
			g.drawLine(j * columnWidth + DX, DY, j * columnWidth + DX, d.height);
			/*
			if(j == 0) {
				//g.drawLine(columnWidth, 0, j * columnWidth, d.height);
				g.drawLine(DX, DY, DX, d.height);
			}
			
			else {
				//g.drawLine(j * columnWidth, 0, j * columnWidth, d.height);
				g.drawLine(j * (columnWidth + DX), DY, j * (columnWidth + DX), d.height);
			}
			*/
		}
	}
	
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
	      repaint();
	   }
		
	public void drawModuleID(Module module, int x, int y) {
		String moduleID = "#" + Integer.toString(module.getID());
		graphic.drawString(moduleID, x, y);
		repaint();
	}

	public void drawLine(int x1, int y1, int x2, int y2) {
		graphic.drawLine(x1, y1, x2, y2);
		repaint();
	}

	public void drawPacket(String s, int x, int y, int width, int height) {
		graphic.drawRect(x, y, width, height);
		graphic.drawString(s, x, y);
		repaint();
	}
}	

