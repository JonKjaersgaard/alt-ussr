package ussr.aGui.tabs.controllers;

import java.rmi.RemoteException;

import javax.swing.DefaultCellEditor;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import ussr.aGui.GeneralController;
import ussr.aGui.enumerations.TextureDescriptions;
import ussr.aGui.tabs.additionalResources.SpinnerEditor;
import ussr.aGui.tabs.view.visualizer.ModuleCommunicationVisualizer;
import ussr.aGui.tabs.views.SimulationTab;
import ussr.builder.SimulationDescriptionConverter;
import ussr.builder.enumerations.XMLTagsUsed;
import ussr.description.setup.WorldDescription.CameraPosition;


public class SimulationTabController extends GeneralController {

	
	//private static SimulationDescriptionConverter descriptionConverter;
	
/*	public static void setDescriptionConverter(SimulationDescriptionConverter descriptionConverter) {
		SimulationTabController.descriptionConverter = descriptionConverter;
	}*/

	public static void updateTable(){
		
		javax.swing.table.DefaultTableModel model = (DefaultTableModel) SimulationTab.getJTable1().getModel();
		//descriptionConverter.
		int planeSize=0;
		boolean theWorldIsFlat = false;
		CameraPosition camear = null;
		boolean hasBackgroundScenery = false;
		boolean hasHeavyObstacles = false;
		try {
			planeSize = remotePhysicsSimulation.getWorldDescriptionControl().getPlaneSize();
			theWorldIsFlat = remotePhysicsSimulation.getWorldDescriptionControl().theWorldIsFlat();
			hasBackgroundScenery = 	remotePhysicsSimulation.getWorldDescriptionControl().hasBackgroundScenery();
			hasHeavyObstacles = remotePhysicsSimulation.getWorldDescriptionControl().hasHeavyObstacles();
			//camear = remotePhysicsSimulation.getWorldDescription().getCameraPosition();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.setValueAt(planeSize, 0, 1);
		
		
		//SimulationTab.getComboBox3().setSelected(true);
		//Tests
		model.setValueAt(TextureDescriptions.MARS_TEXTURE.toString(), 1, 1);
		model.setValueAt(CameraPosition.DEFAULT, 2, 1);
		model.setValueAt(theWorldIsFlat, 3, 1);
		model.setValueAt(hasBackgroundScenery, 4, 1);
		model.setValueAt(hasHeavyObstacles, 5, 1);
		
		
		
		
		//TableColumn column = SimulationTab.getJTable1().getColumnModel().getColumn(1);
		//String[] values = new String[]{""+planeSize};
		//column.setCellEditor(new SpinnerEditor());
		//column.getCellEditor().s
		//model.addRow(new Object[]{"Plane size",""+ planeSize});
		//model.addRow(new Object[]{"Plane size", planeSize,"Some",false});
		//TableColumn column = SimulationTab.getJTable1().getColumnModel().getColumn(1);
		//String[] values = new String[]{""+planeSize};
		//column.setCellEditor(new DefaultCellEditor());
		
		
	}
	
	

}
