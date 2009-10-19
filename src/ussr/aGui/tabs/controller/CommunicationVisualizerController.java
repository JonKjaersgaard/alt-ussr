package ussr.aGui.tabs.controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import ussr.aGui.tabs.additionalResources.CanvasMouseListener;
import ussr.aGui.tabs.view.AssignBehaviorsTab;
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
		CommunicationVisualizer.getJButtonRun().setEnabled(false);
		CommunicationVisualizer.getJButtonReset().setEnabled(true);
		modules = jmeSimulation.getModules();	 
		numberOfModules = modules.size();
		
	/*	
        if(jmeSimulation.isPaused()==false){
        	jLabel1000.setVisible(true);
			jLabel1000.setText("Simulation is running ");
			jScrollPane.setViewportView(jLabel1000);
			
		}else */if (numberOfModules == 0||numberOfModules <= 2){
			// WOULD BE GOOD TO CHECK: 1) IF THERE IS TWO THE SAME MODULE TYPES AND 2)COMMUNICATION IS IN PROGRESS
			CommunicationVisualizer.getJLabel1000().setVisible(true);
			CommunicationVisualizer.getJLabel1000().setText("In simulation environment should be at lest two modules. Now there are "+ numberOfModules + " modules.");
			jScrollPane.setViewportView(CommunicationVisualizer.getJLabel1000());
		}else{
			CommunicationVisualizer.getJLabel1001().setVisible(false);
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

	}
	
	
	public static void jButtonResetActionPerformed(JMESimulation jmeSimulation,JScrollPane jScrollPane) {
		modules = jmeSimulation.getModules();	 
		numberOfModules = modules.size();
		
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
