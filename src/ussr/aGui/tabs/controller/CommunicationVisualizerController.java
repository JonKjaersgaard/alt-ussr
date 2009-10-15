package ussr.aGui.tabs.controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import ussr.aGui.tabs.additional.CanvasMouseListener;
import ussr.aGui.tabs.view.CommunicationVisualizer;
import ussr.aGui.tabs.view.TabsInter;
import ussr.comm.monitors.visualtracker.DrawingCanvas;
import ussr.model.Module;
import ussr.physics.jme.JMESimulation;

public class CommunicationVisualizerController {


	private static int numberOfModules;
	private static List<Module> modules ;

	private static DrawingCanvas drawingCanvas;

	private final static int CANVAS_ROWS = 55;

	public static void jButtonRunActionPerformed(JMESimulation jmeSimulation,JScrollPane jScrollPane) {
		modules = jmeSimulation.getModules();	 
		numberOfModules = modules.size();
		if (numberOfModules == 0||numberOfModules <= 2){
			//STOPPED HERE WRITE THW CLASS: "OptionPane.java"
			//JPanel panel = new	JPanel();			
			//jScrollPane.setViewport(new javax.swing.ImageIcon(TabsInter.DIRECTORY_ICONS + TabsInter.ERROR));
		}else{
			if (drawingCanvas == null){
				drawingCanvas = getDrawingCanvas( jmeSimulation, jScrollPane);
				drawingCanvas.start();
			}else{
				drawingCanvas.stop();
				drawingCanvas = getDrawingCanvas( jmeSimulation, jScrollPane);
				drawingCanvas.start();
			}
			jScrollPane.setViewportView(drawingCanvas); //add canvas to scroll pane
		}

		//jComponent.add(jScrollPane);

	}

	private static DrawingCanvas getDrawingCanvas(JMESimulation jmeSimulation,JScrollPane jScrollPane){
		jmeSimulation.setPause(false);	

		DrawingCanvas drawingCanvasNew = new DrawingCanvas(jmeSimulation, CANVAS_ROWS, numberOfModules);
		drawingCanvasNew.addMouseListener(new CanvasMouseListener(drawingCanvas));
		//jScrollPane.setViewportView(drawingCanvasNew); //add canvas to scroll pane

		for(Module module: modules){
			drawingCanvasNew.getModuleToDraw().add(module.getID());
			drawingCanvasNew.setDrawDecimalPackets(true);
		}
		return drawingCanvasNew;
	}

}
