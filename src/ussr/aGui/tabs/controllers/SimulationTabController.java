package ussr.aGui.tabs.controllers;



import java.awt.Component;
import java.rmi.RemoteException;

import javax.swing.JPanel;
import javax.swing.JSpinner;


import ussr.aGui.MainFramesInter;
import ussr.aGui.enumerations.TreeElements;
import ussr.aGui.tabs.SimulationTab;



public class SimulationTabController extends TabsControllers {





	//private static int  firtsTime =0;
	/**
	 * @param selectedNode
	 */
	public static void jTreeItemSelectedActionPerformed(String selectedNode) {
		

			//SimulationTab.getJPanelEditor().removeAll();
		/*	int i= panelEditor.getComponentCount();
			System.out.println(i);
		while(panelEditor.getComponentCount()!=0){
			i--;
			panelEditor.getComponent(i).removeNotify();
		}*/
	   SimulationTab.getJPanelEditor().removeAll();
	   SimulationTab.getJPanelEditor().revalidate();
	   SimulationTab.getJPanelEditor().repaint();
	/*	for (int com=panelEditor.getComponentCount()-1;com>-1;com--){
			//panelEditor.remove(panelEditor.getComponent(com));
			//Component component = (Component)panelEditor.getComponent(com);
		
			panelEditor.remove(panelEditor.getComponent(com));
			//panelEditor.revalidate();
		}
		panelEditor.revalidate();*/

		switch(TreeElements.valueOf(selectedNode.replace(" ", "_"))){
		case Plane_Size:
			SimulationTab.addPlaneSizeEditor();
			break;
		case Plane_Texture:
			SimulationTab.addPlaneTextureEditor();
			break;
		}
		SimulationTab.getJPanelEditor().validate();

	}

	/**
	 * Sets the value of plane size in the spinner component.
	 * @param spinnerPlaneSize, the component in the tab
	 */
	public static void setJSpinnerPlaneSizeValue(JSpinner spinnerPlaneSize) {
		try {
			spinnerPlaneSize.setValue(remotePhysicsSimulation.getWorldDescriptionControl().getPlaneSize());
		} catch (RemoteException e) {
			throw new Error("Failed to extract plane size, due to remote exception");
		}		
	}

	public static void jButtonPlaneTiltRightActionPerformed() {
		// TODO Auto-generated method stub

	}

}
