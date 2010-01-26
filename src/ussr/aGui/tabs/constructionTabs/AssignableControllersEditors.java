package ussr.aGui.tabs.constructionTabs;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import ussr.aGui.tabs.controllers.AssignControllerTabController;
import ussr.aGui.tabs.designHelpers.JComponentsFactory;
import ussr.builder.enumerations.SupportedModularRobots;

/**
 * Contains methods  defining visual appearance of edit value panel in Assign Controller Tab for each
 * assignable controller.
 *  
 * @author Konstantinas
 */
public class AssignableControllersEditors  {

	/**
	 * Defines visual appearance of editor panel(edit value) for ATRON controller called RotateContinuous.
	 * @return jPanelEditor, the editor panel with new components in it.
	 */
	public static javax.swing.JPanel addATRONRotateContinuousEditor(){
		javax.swing.JPanel jPanelEditor = new javax.swing.JPanel();
		jSpinnerAtronSpeedRotateContinuous = new javax.swing.JSpinner();
		jSpinnerAtronSpeedRotateContinuous.setPreferredSize(new Dimension(60,20));
		jSpinnerAtronSpeedRotateContinuous.setModel(new javax.swing.SpinnerNumberModel(Float.valueOf(-0.05f), Float.valueOf(-1.0f), Float.valueOf(1.0f), Float.valueOf(0.1f)));
		jSpinnerAtronSpeedRotateContinuous.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
            	AssignControllerTabController.activateAssignmentTool(AssignableControllers.ROTATE_CONTINUOUS);
            }
        });
		jPanelEditor.add(jSpinnerAtronSpeedRotateContinuous);	
		return jPanelEditor;
	}
	
	/**
	 * Defines visual appearance of editor panel(edit value) for ATRON controller called Rotate Degrees.
	 * @return jPanelEditor, the editor panel with new components in it.
	 */
	public static javax.swing.JPanel addATRONRotateDegreesEditor(){
		javax.swing.JPanel jPanelEditor = new javax.swing.JPanel();
		jSpinnerAtronRotateDegrees = new javax.swing.JSpinner();
		jSpinnerAtronRotateDegrees.setPreferredSize(new Dimension(60,20));
		jSpinnerAtronRotateDegrees.setModel(new javax.swing.SpinnerNumberModel(2, -360, 360, 1));
		jSpinnerAtronRotateDegrees.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
            	AssignControllerTabController.activateAssignmentTool(AssignableControllers.ROTATE_DEGREES);
            }
        });
		jPanelEditor.add(jSpinnerAtronRotateDegrees);	
		return jPanelEditor;
	}
	
	/**
	 * Defines visual appearance of editor panel(edit value) for ATRON controller called Disconnect Specific Connector.
	 * @return jPanelEditor, the editor panel with new components in it.
	 */
	public static javax.swing.JPanel addATRONDisconnectSpecificConnectorEditor(){
		javax.swing.JPanel jPanelEditor = new javax.swing.JPanel();	    
		atronNrsConnectors = new  javax.swing.JComboBox();
		atronNrsConnectors.setRenderer(ConstructionTabs.ATRON_RENDERER);
		atronNrsConnectors.setModel(new javax.swing.DefaultComboBoxModel(SupportedModularRobots.ATRON_CONNECTORS));
		atronNrsConnectors.setPreferredSize(new java.awt.Dimension(50, 26));
		atronNrsConnectors.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent evt) {
					if(AssignControllerTab.getJToggleButtonColorConnetors().isSelected()){
						// do nothing						
					}else{
						AssignControllerTab.getJToggleButtonColorConnetors().doClick();
					}
					AssignControllerTabController.activateAssignmentTool(AssignableControllers.DISCONNECT_SPECIFIC_CONNECTOR);
				}
			});	
		jPanelEditor.add(atronNrsConnectors);	
		return jPanelEditor;
	}
	
	/**
	 * Returns nr. of connector selected(chosen) by user in edit value editor for ATRON.
	 * @return  nr. of connector selected(chosen) by user in edit value editor for ATRON.
	 */
	public static Integer getValueAtronNrsConnectors() {
		return Integer.parseInt(atronNrsConnectors.getSelectedItem().toString());
	}
	
	/**
	 * Returns the speed value of continuous rotation for ATRON entered by user in edit value editor.
	 * @return the speed value of continuous rotation for ATRON entered by user in edit value editor.
	 */
	public static Float getValueJSpinnerAtronSpeedRotateContinuous() {
		return Float.parseFloat(jSpinnerAtronSpeedRotateContinuous.getValue().toString());
	}
	
	/**
	 * Returns the degrees of rotation for ATRON entered by user in edit value editor.
	 * @return the degrees of rotation for ATRON entered by user in edit value editor.
	 */
	public static Integer getValuejSpinnerAtronRotateDegrees() {
		return Integer.parseInt(jSpinnerAtronRotateDegrees.getValue().toString());
	}
	
	/**
	 * Defines visual appearance of editor panel(edit value) for Odin controller called Actuate(Expand/Contract) Continuously.
	 * @return jPanelEditor, the editor panel with new components in it.
	 */
	public static javax.swing.JPanel addODINActuateContinuouslyEditor(){
		javax.swing.JPanel jPanelEditor = new javax.swing.JPanel();
		jSpinnerOdinActuateContinuously = new javax.swing.JSpinner();
		jSpinnerOdinActuateContinuously.setPreferredSize(new Dimension(60,20));
		jSpinnerOdinActuateContinuously.setModel(new javax.swing.SpinnerNumberModel(Float.valueOf(-1.0f), Float.valueOf(-1.0f), Float.valueOf(1.0f), Float.valueOf(0.1f)));
		jSpinnerOdinActuateContinuously.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
            	AssignControllerTabController.activateAssignmentTool(AssignableControllers.EXPAND_CONTRACT_CONSTINUOUSLY);
            }
        });
		jPanelEditor.add(jSpinnerOdinActuateContinuously);	
		return jPanelEditor;
	}
	
	/**
	 * Returns the speed of actuation for Odin entered by user in edit value editor.
	 * @return the speed of actuation for Odin entered by user in edit value editor.
	 */
	public static Float getValuejSpinnerOdinActuateContinuously() {
		return Float.parseFloat(jSpinnerOdinActuateContinuously.getValue().toString());
	}
	
	/**
	 * Defines visual appearance of editor panel(edit value) for MTRAN controller called RotateContinuous.
	 * @return jPanelEditor, the editor panel with new components in it.
	 */
	public static javax.swing.JPanel addMTRANRotateContinuouslyEditor(){
		javax.swing.JPanel jPanelEditor = new javax.swing.JPanel(new GridBagLayout());
		
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.gridx =0;
		gridBagConstraints.gridy =0;
		
		jPanelEditor.add(JComponentsFactory.createNewLabel("Actuator nr:"),gridBagConstraints);
		
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.gridx =1;
		gridBagConstraints.gridy =0;
		
		jComboBoxMtranNrsActuators = new  javax.swing.JComboBox();
		jComboBoxMtranNrsActuators.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"0","1"}));
		jComboBoxMtranNrsActuators.setPreferredSize(new java.awt.Dimension(50, 26));
		jComboBoxMtranNrsActuators.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent evt) {
					AssignControllerTabController.activateAssignmentTool(AssignableControllers.MTRAN_ROTATE_CONTINUOUS);
				}
			});	
		jPanelEditor.add(jComboBoxMtranNrsActuators, gridBagConstraints);
		
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.gridx =0;
		gridBagConstraints.gridy =1;
		gridBagConstraints.insets = new Insets(10,0,0,0);
		
		jPanelEditor.add(JComponentsFactory.createNewLabel("Velocity:"),gridBagConstraints);
		
		jSpinnerMTRANrotateContinuously = new javax.swing.JSpinner();
		jSpinnerMTRANrotateContinuously.setPreferredSize(new Dimension(60,20));
		jSpinnerMTRANrotateContinuously.setModel(new javax.swing.SpinnerNumberModel(1, -1, 1, 1));
		jSpinnerMTRANrotateContinuously.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
            	AssignControllerTabController.activateAssignmentTool(AssignableControllers.MTRAN_ROTATE_CONTINUOUS);
            }
        });
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.gridx =1;
		gridBagConstraints.gridy =1;
		
		jPanelEditor.add(jSpinnerMTRANrotateContinuously,gridBagConstraints);
		return jPanelEditor;
	}
	
	/**
	 * Returns the speed of rotation for Mtran entered by user in edit value editor.
	 * @return the speed of rotation for Mtran entered by user in edit value editor.
	 */
	public static int getValuejSpinnerMtranRotateContinuously() {
		return Integer.parseInt(jSpinnerMTRANrotateContinuously.getValue().toString());
	}
	
	/**
	 * Returns the number of actuator for rotation selected (chosen) by user in edit value editor.
	 * @return the number of actuator for rotation selected (chosen) by user in edit value editor.
	 */
	public static int getSelectedjComboBoxMtranNrsActuators() {
		return Integer.parseInt(jComboBoxMtranNrsActuators.getSelectedItem().toString());
	}
	
	private static javax.swing.JComboBox atronNrsConnectors,jComboBoxMtranNrsActuators;
	
	private static javax.swing.JSpinner jSpinnerAtronSpeedRotateContinuous,jSpinnerOdinActuateContinuously,
	jSpinnerAtronRotateDegrees,jSpinnerMTRANrotateContinuously;	
}
