package ussr.aGui.tabs.constructionTabs;

import java.awt.Dimension;


import ussr.aGui.tabs.controllers.AssignControllerTabController;
import ussr.builder.enumerations.SupportedModularRobots;

public class AssignableControllersEditors  {

	public static javax.swing.JPanel addRotateContinuousEditor(){
		javax.swing.JPanel jPanelEditor = new javax.swing.JPanel();
		jSpinnerRotateContinuous = new javax.swing.JSpinner();
		jSpinnerRotateContinuous.setPreferredSize(new Dimension(60,20));
		jSpinnerRotateContinuous.setModel(new javax.swing.SpinnerNumberModel(Float.valueOf(-1.0f), Float.valueOf(-1.0f), Float.valueOf(1.0f), Float.valueOf(0.1f)));
		jSpinnerRotateContinuous.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
            	//AssignControllerTabController.jSpinnerRotateContinuousActionPerformed(Float.parseFloat(jSpinnerRotateContinuous.getValue().toString()));
            	AssignControllerTabController.activateAssignmentTool(AssignableControllers.ROTATE_CONTINUOUS);
            }
        });
		jPanelEditor.add(jSpinnerRotateContinuous);	
		return jPanelEditor;
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
	


	public static javax.swing.JPanel addDisconnectSpecificConnectorEditor(){
		javax.swing.JPanel jPanelEditor = new javax.swing.JPanel();	    
		nrsConnectors = new  javax.swing.JComboBox();
		nrsConnectors.setRenderer(ConstructionTabs.ATRON_RENDERER);
		nrsConnectors.setModel(new javax.swing.DefaultComboBoxModel(SupportedModularRobots.ATRON_CONNECTORS));
		nrsConnectors.setPreferredSize(new java.awt.Dimension(50, 26));
		nrsConnectors.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent evt) {
					if(AssignControllerTab.getJToggleButtonColorConnetors().isSelected()){
						// do nothing						
					}else{
						AssignControllerTab.getJToggleButtonColorConnetors().doClick();
					}
					AssignControllerTabController.activateAssignmentTool(AssignableControllers.DISCONNECT_SPECIFIC_CONNECTOR);
				}
			});	
		jPanelEditor.add(nrsConnectors);	
		return jPanelEditor;
	}
	
	
	
	public static javax.swing.JPanel addActuateContinuouslyEditor(){
		javax.swing.JPanel jPanelEditor = new javax.swing.JPanel();
		jSpinnerActuateContinuously = new javax.swing.JSpinner();
		jSpinnerActuateContinuously.setPreferredSize(new Dimension(60,20));
		jSpinnerActuateContinuously.setModel(new javax.swing.SpinnerNumberModel(Float.valueOf(-1.0f), Float.valueOf(-1.0f), Float.valueOf(1.0f), Float.valueOf(0.1f)));
		jSpinnerActuateContinuously.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
            	//AssignControllerTabController.jSpinnerRotateContinuousActionPerformed(Float.parseFloat(jSpinnerRotateContinuous.getValue().toString()));
            	AssignControllerTabController.activateAssignmentTool(AssignableControllers.EXPAND_CONTRACT_CONSTINUOUSLY);
            }
        });
		jPanelEditor.add(jSpinnerActuateContinuously);	
		return jPanelEditor;
	}
	
	
	public static Float getValueJSpinnerRotateContinuous() {
		return Float.parseFloat(jSpinnerRotateContinuous.getValue().toString());
	}
	
	public static Float getValuejSpinnerActuateContinuously() {
		return Float.parseFloat(jSpinnerActuateContinuously.getValue().toString());
	}
	
	public static Integer getValueNrsConnectors() {
		return Integer.parseInt(nrsConnectors.getSelectedItem().toString());
	}
	
	
	
	public static Integer getValuejSpinnerRotateDegrees() {
		return Integer.parseInt(jSpinnerRotateDegrees.getValue().toString());
	}
	
	
	private static javax.swing.JComboBox nrsConnectors;
	
	private static javax.swing.JSpinner jSpinnerRotateContinuous,jSpinnerActuateContinuously,
	jSpinnerRotateDegrees;

	
}
