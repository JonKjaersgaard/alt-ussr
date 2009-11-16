package ussr.aGui.tabs.controllers;


import java.rmi.RemoteException;
import java.util.List;

import javax.swing.JCheckBox;

import javax.swing.JScrollPane;

import javax.swing.table.DefaultTableModel;


import ussr.aGui.tabs.view.visualizer.CanvasMouseListener;
import ussr.aGui.tabs.view.visualizer.DrawingCanvas;
import ussr.aGui.tabs.view.visualizer.ModuleCommunicationVisualizer;

public class ModuleCommunicationVisualizerController extends TabsControllers {


	private static int numberOfModules;
	//private static List<Module> modules ;
	private static List<Integer> idsModules; 

	private static DrawingCanvas drawingCanvas;

	private final static int CANVAS_ROWS = 55;

	public static void jButtonRunActionPerformed(JScrollPane jScrollPane) {
		ModuleCommunicationVisualizer.getJButtonRun().setEnabled(false);
		ModuleCommunicationVisualizer.getJButtonReset().setEnabled(true);
		
		try {
			idsModules = builderControl.getIDsModules() ;
		} catch (RemoteException e) {
			throw new Error ("Failed to receive the list of modules, due to remote exception.");
		}
		
	//	modules = jmeSimulation.getModules();	 
		numberOfModules = idsModules.size();

		/*	
        if(jmeSimulation.isPaused()==false){
        	jLabel1000.setVisible(true);
			jLabel1000.setText("Simulation is running ");
			jScrollPane.setViewportView(jLabel1000);

		}else */if (numberOfModules == 0||numberOfModules <= 2){
			// WOULD BE GOOD TO CHECK: 1) IF THERE IS TWO THE SAME MODULE TYPES AND 2)COMMUNICATION IS IN PROGRESS
			ModuleCommunicationVisualizer.getJLabel1000().setVisible(true);
			ModuleCommunicationVisualizer.getJLabel1000().setText("In simulation environment should be at lest two modules. Now there are "+ numberOfModules + " modules.");
			jScrollPane.setViewportView(ModuleCommunicationVisualizer.getJLabel1000());
		}else{
			jButtonResetActionPerformed(jScrollPane);
		}
	}	

	public static void jButtonResetActionPerformed(JScrollPane jScrollPane) {		
		if (drawingCanvas == null){
			drawingCanvas = getDrawingCanvas();
			drawingCanvas.start();
		}else{
			drawingCanvas.stop();			
			drawingCanvas = getDrawingCanvas();
			drawingCanvas.start();
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

	public static void jCheckBoxShowLabelControlActionPerformed(JCheckBox checkBoxShowLabelControl) {
		if (checkBoxShowLabelControl.isSelected()){
			ModuleCommunicationVisualizer.getJPanelLabeling().setVisible(true);
			ModuleCommunicationVisualizer.getJToolBar1().setVisible(false);
			
			int amountRows = ModuleCommunicationVisualizer.getJTableModules().getRowCount() ;
			//if (amountModules<amountRows){
			for (int module =0;module < numberOfModules; module++){
				
				//ModuleCommunicationVisualizer.getJTableModules().setValueAt("#"+module, module, 0);
				//ModuleCommunicationVisualizer.getJTableModules().setValueAt(true, module, 1);
				javax.swing.table.DefaultTableModel model = (DefaultTableModel) ModuleCommunicationVisualizer.getJTableModules().getModel();
				model.addRow(new Object[]{"#"+module,true});
			}
/*			}else{
				//TODO add addition of new rows if there are too many modules 
				throw new Error ("Too many modules");
			}*/
			
			
		}else{
			ModuleCommunicationVisualizer.getJPanelLabeling().setVisible(false);
			ModuleCommunicationVisualizer.getJToolBar1().setVisible(true);
		}
		
	}

}
