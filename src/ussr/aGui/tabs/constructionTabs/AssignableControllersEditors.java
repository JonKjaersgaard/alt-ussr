package ussr.aGui.tabs.constructionTabs;

import java.awt.Dimension;

public class AssignableControllersEditors {

	
	public static javax.swing.JPanel addRotateContinuousEditor(){
		javax.swing.JPanel jPanelEditor = new javax.swing.JPanel();
		jSpinnerRotateContinuous = new javax.swing.JSpinner();
		jSpinnerRotateContinuous.setPreferredSize(new Dimension(60,20));
		jSpinnerRotateContinuous.setModel(new javax.swing.SpinnerNumberModel(Float.valueOf(-1.0f), Float.valueOf(-1.0f), Float.valueOf(1.0f), Float.valueOf(0.01f)));
		jPanelEditor.add(jSpinnerRotateContinuous);	
		return jPanelEditor;
	}
	
	private static javax.swing.JSpinner jSpinnerRotateContinuous;
	
	
	
	
}
