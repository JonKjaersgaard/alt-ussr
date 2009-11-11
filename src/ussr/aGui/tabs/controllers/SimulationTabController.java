package ussr.aGui.tabs.controllers;

import javax.swing.table.DefaultTableModel;

import ussr.aGui.tabs.view.visualizer.ModuleCommunicationVisualizer;
import ussr.aGui.tabs.views.SimulationTab;
import ussr.builder.SimulationDescriptionConverter;
import ussr.builder.enumerations.XMLTagsUsed;


public class SimulationTabController {

	
	private static SimulationDescriptionConverter descriptionConverter;
	
	public static void setDescriptionConverter(SimulationDescriptionConverter descriptionConverter) {
		SimulationTabController.descriptionConverter = descriptionConverter;
	}

	public static void updateTable(){
		
		javax.swing.table.DefaultTableModel model = (DefaultTableModel) SimulationTab.getJTable1().getModel();
		//descriptionConverter.
		model.addRow(new Object[]{"Plane size",descriptionConverter.convertPlaneSize()});
	}
	
	

}
