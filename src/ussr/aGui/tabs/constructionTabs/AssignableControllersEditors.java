package ussr.aGui.tabs.constructionTabs;

import java.awt.Dimension;

import ussr.aGui.tabs.controllers.AssignControllerTabController;
import ussr.aGui.tabs.controllers.SimulationTabController;

public class AssignableControllersEditors {

	
	public static javax.swing.JPanel addRotateContinuousEditor(){
		javax.swing.JPanel jPanelEditor = new javax.swing.JPanel();
		jSpinnerRotateContinuous = new javax.swing.JSpinner();
		jSpinnerRotateContinuous.setPreferredSize(new Dimension(60,20));
		jSpinnerRotateContinuous.setModel(new javax.swing.SpinnerNumberModel(Float.valueOf(-1.0f), Float.valueOf(-1.0f), Float.valueOf(1.0f), Float.valueOf(0.01f)));
		jSpinnerRotateContinuous.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
            	//AssignControllerTabController.jSpinnerRotateContinuousActionPerformed(Float.parseFloat(jSpinnerRotateContinuous.getValue().toString()));
            	AssignControllerTabController.activateAssignmentTool(AssignableControllers.ROTATE_CONTINUOUS);
            }
        });
		jPanelEditor.add(jSpinnerRotateContinuous);	
		return jPanelEditor;
	}
	
	private static javax.swing.JSpinner jSpinnerRotateContinuous;

	public static Float getValueJSpinnerRotateContinuous() {
		return Float.parseFloat(jSpinnerRotateContinuous.getValue().toString());
	}
	
	
	public static javax.swing.JPanel addRotateDegreesEditor(){
		javax.swing.JPanel jPanelEditor = new javax.swing.JPanel();
		jSpinnerRotateDegrees = new javax.swing.JSpinner();
		jSpinnerRotateDegrees.setPreferredSize(new Dimension(60,20));
		jSpinnerRotateDegrees.setModel(new javax.swing.SpinnerNumberModel(45, -360, 360, 1));
		jSpinnerRotateDegrees.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
            	AssignControllerTabController.activateAssignmentTool(AssignableControllers.ROTATE_DEGREES);
            }
        });
		jPanelEditor.add(jSpinnerRotateDegrees);	
		return jPanelEditor;
	}
	
	private static javax.swing.JSpinner jSpinnerRotateDegrees;
	
	public static Integer getValuejSpinnerRotateDegrees() {
		return Integer.parseInt(jSpinnerRotateDegrees.getValue().toString());
	}
	
	
	
	
	
}
