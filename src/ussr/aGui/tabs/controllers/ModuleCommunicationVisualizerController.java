package ussr.aGui.tabs.controllers;

import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import ussr.aGui.tabs.TabsInter;
import ussr.aGui.tabs.view.visualizer.CanvasMouseListener;
import ussr.aGui.tabs.view.visualizer.ModuleCommunicationVisualizer;
import ussr.aGui.tabs.views.constructionTabs.AssignBehaviorsTab;
import ussr.comm.monitors.visualtracker.DrawingCanvas;
import ussr.comm.monitors.visualtracker.ModuleFilterDialog;
import ussr.model.Module;
import ussr.physics.jme.JMESimulation;

public class ModuleCommunicationVisualizerController {


	private static int numberOfModules;
	private static List<Module> modules ;

	private static DrawingCanvas drawingCanvas;

	private final static int CANVAS_ROWS = 55;

	public static void jButtonRunActionPerformed(JMESimulation jmeSimulation,JScrollPane jScrollPane) {
		ModuleCommunicationVisualizer.getJButtonRun().setEnabled(false);
		ModuleCommunicationVisualizer.getJButtonReset().setEnabled(true);
		modules = jmeSimulation.getModules();	 
		numberOfModules = modules.size();

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
			jButtonResetActionPerformed(jmeSimulation,jScrollPane);
		}
	}	

	public static void jButtonResetActionPerformed(JMESimulation jmeSimulation,JScrollPane jScrollPane) {		
		if (drawingCanvas == null){
			drawingCanvas = getDrawingCanvas( jmeSimulation);
			drawingCanvas.start();
		}else{
			drawingCanvas.stop();			
			drawingCanvas = getDrawingCanvas( jmeSimulation);
			drawingCanvas.start();
		}
		jScrollPane.setViewportView(drawingCanvas); //add canvas to scroll pane		
		
	}

	private static DrawingCanvas getDrawingCanvas(JMESimulation jmeSimulation){

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

	public static void jButtonModulesActionPerformed(JMESimulation jmeSimulation,JScrollPane jScrollPane) {		
		//new ModuleFilterDialog(null, jmeSimulation);
	}

	public static void jCheckBoxShowLabelControlActionPerformed(JCheckBox checkBoxShowLabelControl,JMESimulation jmeSimulation) {
		if (checkBoxShowLabelControl.isSelected()){
			ModuleCommunicationVisualizer.getJPanelLabeling().setVisible(true);
			ModuleCommunicationVisualizer.getJToolBar1().setVisible(false);
			
			int amountRows = ModuleCommunicationVisualizer.getJTableModules().getRowCount() ;
			int amountModules = jmeSimulation.getModules().size();
			//if (amountModules<amountRows){
			for (int module =0;module < amountModules; module++){
				
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
