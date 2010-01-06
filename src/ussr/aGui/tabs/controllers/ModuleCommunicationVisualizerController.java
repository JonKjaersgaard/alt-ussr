package ussr.aGui.tabs.controllers;


import java.rmi.RemoteException;
import java.util.List;
import javax.swing.JCheckBox;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;

import ussr.aGui.MainFramesInter;
import ussr.aGui.tabs.TabsInter;
import ussr.aGui.tabs.visualizer.CanvasMouseListener;
import ussr.aGui.tabs.visualizer.DrawingCanvas;
import ussr.aGui.tabs.visualizer.ModuleCommunicationVisualizer;


public class ModuleCommunicationVisualizerController extends TabsControllers {


	private static int numberOfModules;
	//private static List<Module> modules ;
	private static List<Integer> idsModules; 

	public static void setIdsModules() {
		try {
			idsModules = builderControl.getIDsModules();
			numberOfModules = idsModules.size();
		} catch (RemoteException e) {
			throw new Error ("Failed to receive the list of modules, due to remote exception.");
		}
	}

	private static DrawingCanvas drawingCanvas;

	private final static int CANVAS_ROWS = 55;

	public static void jButtonRunActionPerformed() {
		ModuleCommunicationVisualizer.getJButtonRun().setEnabled(false);
		ModuleCommunicationVisualizer.getJButtonReset().setEnabled(true);

		/*	
        if(jmeSimulation.isPaused()==false){
        	jLabel1000.setVisible(true);
			jLabel1000.setText("Simulation is running ");
			jScrollPane.setViewportView(jLabel1000);

		}else */if (numberOfModules == 0||numberOfModules < 2){
			// WOULD BE GOOD TO CHECK: 1) IF THERE IS TWO THE SAME MODULE TYPES AND 2)COMMUNICATION IS IN PROGRESS
			ModuleCommunicationVisualizer.getJLabel1000().setVisible(true);
			ModuleCommunicationVisualizer.getJLabel1000().setText("In simulation environment should be at lest two modules. Now there are "+ numberOfModules + " modules.");
			ModuleCommunicationVisualizer.getJScrollPane().setViewportView(ModuleCommunicationVisualizer.getJLabel1000());
		}else{
			jButtonResetActionPerformed(ModuleCommunicationVisualizer.getJScrollPane());
		}
	}	

	public static void jButtonResetActionPerformed(JScrollPane jScrollPane) {		
		if (drawingCanvas == null){
			drawingCanvas = getDrawingCanvas();
			drawingCanvas.start();
		}else{
			drawingCanvas.stop();
			drawingCanvas.removeAll();
			drawingCanvas.validate();
			drawingCanvas = getDrawingCanvas();
			drawingCanvas.start();
			//FIXME HEAP SPACE PROBLEM IF USED FREQUENTLY
		}
		jScrollPane.setViewportView(drawingCanvas); //add canvas to scroll pane		

	}

	private static DrawingCanvas getDrawingCanvas(){
		//jmeSimulation.setPause(false);	

		DrawingCanvas drawingCanvasNew = new DrawingCanvas( idsModules,/*jmeSimulation,*/ CANVAS_ROWS, numberOfModules);
		drawingCanvasNew.addMouseListener(new CanvasMouseListener(drawingCanvas));
		//jScrollPane.setViewportView(drawingCanvasNew); //add canvas to scroll pane

		for(Integer moduleID: idsModules){
			drawingCanvasNew.getModuleToDraw().add(moduleID);
			drawingCanvasNew.setDrawDecimalPackets(true);
		}
		return drawingCanvasNew;
	}
	

	/**
	 * Controls visibility of the legend for display of module communication. 
	 * @param checkBoxShowLabelControl, the GUI component.
	 */
	public static void jCheckBoxDisplayLegend(JCheckBox checkBoxShowLabelControl) {
		if (checkBoxShowLabelControl.isSelected()){
			ModuleCommunicationVisualizer.getJPanelLabeling().setVisible(true);
			/*For each module ID add new row in the table of modules */
			for (int module =0;module < numberOfModules; module++){
				javax.swing.table.DefaultTableModel model = (DefaultTableModel) ModuleCommunicationVisualizer.getJTableModules().getModel();
				model.addRow(new Object[]{"#"+module,true});
			}
			}else{
			ModuleCommunicationVisualizer.getJPanelLabeling().setVisible(false);
		}
	}

	public static void jListPacketFormatsMouseReleased() {
		// TODO Auto-generated method stub

	}



}
