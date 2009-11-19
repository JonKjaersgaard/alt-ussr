package ussr.aGui.tabs.controllers;

import java.rmi.RemoteException;
import java.util.Hashtable;
import java.util.Map;

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
import ussr.description.setup.WorldDescription;
import ussr.description.setup.WorldDescription.CameraPosition;
import ussr.description.setup.WorldDescription.TextureDescription;


public class SimulationTabController extends GeneralController {

	
	//private static SimulationDescriptionConverter descriptionConverter;
	
/*	public static void setDescriptionConverter(SimulationDescriptionConverter descriptionConverter) {
		SimulationTabController.descriptionConverter = descriptionConverter;
	}*/
	private static Map<String,Object> table = new Hashtable<String,Object>();

	public static void updateTable(){
		
		SimulationTab.setJTablesVisible(true);
		
		javax.swing.table.DefaultTableModel model = (DefaultTableModel) SimulationTab.getJTableWorldDescription().getModel();
		//descriptionConverter.
		int planeSize=0;
		CameraPosition cameraPosition = null;
		boolean hasBackgroundScenery = false, hasHeavyObstacles = false,theWorldIsFlat = false,
		isFrameGrabingActive = false;
		TextureDescription planeTexture = null;
	/*	final String[] worldDescriptionParameters = {"Plane Size","Plane Texture","Camera Position",
                "The world is flat","Has background scenery",
                "Has heavy obstacles","Is frame grabbing active", 
                		"Big obstacles"};*/
		
		try {
			planeSize = remotePhysicsSimulation.getWorldDescriptionControl().getPlaneSize();
			theWorldIsFlat = remotePhysicsSimulation.getWorldDescriptionControl().theWorldIsFlat();
			hasBackgroundScenery = 	remotePhysicsSimulation.getWorldDescriptionControl().hasBackgroundScenery();
			hasHeavyObstacles = remotePhysicsSimulation.getWorldDescriptionControl().hasHeavyObstacles();
			cameraPosition = remotePhysicsSimulation.getWorldDescriptionControl().getCameraPosition();
			//isFrameGrabingActive = remotePhysicsSimulation.getWorldDescription().getIsFrameGrabbingActive();
			//String texture = TextureDescriptions.valueOf(remotePhysicsSimulation.getWorldDescriptionControl().getPlaneTexture().toString());
		} catch (RemoteException e) {
			throw new Error("Failed to exctract one of simulation description values, due to remote exception");
		}
		
		model.setValueAt(planeSize, 0, 1);		
		model.setValueAt(WorldDescription.GRASS_TEXTURE.toString(), 1, 1);//for testing
		model.setValueAt(CameraPosition.DEFAULT.toString(), 2, 1);
		
		
		model.setValueAt(theWorldIsFlat, 3, 1);
		model.setValueAt(hasBackgroundScenery, 4, 1);
		model.setValueAt(hasHeavyObstacles, 5, 1);
		//model.setValueAt(isFrameGrabingActive, 6, 1);
		
		
		
		
		//TableColumn column = SimulationTab.getJTableWorldDescription().getColumnModel().getColumn(1);
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
