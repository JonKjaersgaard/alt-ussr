package ussr.aGui.tabs.controllers;

import java.awt.event.MouseEvent;
import javax.swing.JSpinner;
import javax.swing.event.ChangeEvent;

import ussr.physics.jme.JMESimulation;

public class GeneralTabController {

	public static void jSpinner1StateChanged(ChangeEvent evt,JMESimulation jmeSimulation) {
		javax.swing.JSpinner spinner = (JSpinner)evt.getSource();
		//int planeSize = Integer.parseInt(.getValue().toString());
		System.out.println("Out"+ spinner.getValue().toString() );
		jmeSimulation.getWorldDescription().setPlaneSize(Integer.parseInt(spinner.getValue().toString()));
		
		//FIXME
	}

	//public static void jSpinner1MouseClicked(javax.swing.JSpinner jSpinner1) {
		//int planeSize = Integer.parseInt(jSpinner1.getValue().toString());
		//System.out.println("Out"+ planeSize );
		
	//}

}
