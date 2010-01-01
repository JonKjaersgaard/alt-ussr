package ussr.aGui.tabs.simulation;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.border.TitledBorder;
import ussr.aGui.FramesInter;
import ussr.aGui.enumerations.tabs.TabsComponentsText;
import ussr.aGui.enumerations.tabs.TabsIcons;
import ussr.aGui.tabs.Tabs;
import ussr.aGui.tabs.controllers.SimulationTabController;
import ussr.aGui.tabs.simulation.enumerations.CameraPositions;
import ussr.aGui.tabs.simulation.enumerations.PhysicsParametersDefault;
import ussr.aGui.tabs.simulation.enumerations.PlaneMaterials;
import ussr.aGui.tabs.simulation.enumerations.TextureDescriptions;
import ussr.builder.helpers.StringProcessingHelper;
import ussr.description.setup.WorldDescription.CameraPosition;
import ussr.physics.PhysicsParameters;

/**
 * Contains methods  defining visual appearance of edit value panel in Simulation Tab for each
 * node in tree view. 
 * 
 * NOTE: each method is creating new panel, which is later added into  panel with title edit value.
 * @author Konstantinas
 *
 */
public class SimulationTreeEditors{
	/**
	 * Defines visual appearance of editor panel(edit value) for tree node called Robots.
	 * @return jPanelEditor, the editor panel with new components in it.
	 */
	public static javax.swing.JPanel  addRobotsEditor(){
		javax.swing.JPanel jPanelTreeNode = new javax.swing.JPanel();
		jPanelTreeNode.add(SimulationTab.createNewLabel(TabsComponentsText.LOAD_NEW_ROBOT.getUserFriendlyName()));
		jPanelTreeNode.add(Tabs.initOpenButton());
		return jPanelTreeNode;
	}

	/**
	 * Defines visual appearance of editor panel(edit value) for tree node called physics simulation step size.
	 * @return jPanelEditor, the editor panel with new components in it.
	 */
	public static javax.swing.JPanel addPhysicsSimulationStepSizeEditor() {
		javax.swing.JPanel jPanelTreeNode = new javax.swing.JPanel();
		jSpinnerPhysicsSimulationStepSize = new javax.swing.JSpinner();
		jSpinnerPhysicsSimulationStepSize.setPreferredSize(new Dimension(60,20));
		jSpinnerPhysicsSimulationStepSize.setModel(new javax.swing.SpinnerNumberModel(Float.valueOf(0.0f), null, null, Float.valueOf(1.0f)));
		jPanelTreeNode.add(jSpinnerPhysicsSimulationStepSize);	

		return jPanelTreeNode;
	}


	/**
	 * Defines visual appearance of editor panel(edit value) for tree node called RobotNr....
	 * @return jPanelEditor, the editor panel with new components in it.
	 */
	public static javax.swing.JPanel addRobotEditor(){
		javax.swing.JPanel jPanelTreeNode = new javax.swing.JPanel();
		jPanelMoveRobot = new javax.swing.JPanel(new GridBagLayout());
		jButtonYpositive = new javax.swing.JButton();
		jButtonYnegative = new javax.swing.JButton();
		jButtonXpositive = new javax.swing.JButton();
		jButtonXnegative = new javax.swing.JButton();
		jButtonZpositive = new javax.swing.JButton();
		jButtonZnegative = new javax.swing.JButton();
		jSpinnerCoordinateValue = new javax.swing.JSpinner();

		GridBagConstraints gridBagConstraintsMoveRobot = new GridBagConstraints();
		TitledBorder displayTitle1;
		displayTitle1 = BorderFactory.createTitledBorder(TabsComponentsText.NEW_POSITION.getUserFriendlyName());
		displayTitle1.setTitleJustification(TitledBorder.CENTER);
		jPanelMoveRobot.setBorder(displayTitle1);
		
		jButtonYpositive.setToolTipText(TabsComponentsText.MOVE_UP.getUserFriendlyName());
		jButtonYpositive.setIcon(TabsIcons.Y_POSITIVE_BIG.getImageIcon());
		jButtonYpositive.setRolloverIcon(TabsIcons.Y_POSITIVE_ROLLOVER_BIG.getImageIcon());					
		jButtonYpositive.setFocusable(false);		
		jButtonYpositive.setPreferredSize(new Dimension(45,30));
		jButtonYpositive.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				SimulationTabController.jButtonsCoordinateArrowsActionPerformed(jButtonYpositive);
			}
		});
		gridBagConstraintsMoveRobot.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraintsMoveRobot.gridx = 1;
		gridBagConstraintsMoveRobot.gridy = 0;	

		jPanelMoveRobot.add(jButtonYpositive,gridBagConstraintsMoveRobot);


		jButtonXnegative.setToolTipText(TabsComponentsText.MOVE_LEFT.getUserFriendlyName());
		jButtonXnegative.setIcon(TabsIcons.X_NEGATIVE_BIG.getImageIcon());	
		jButtonXnegative.setRolloverIcon(TabsIcons.X_NEGATIVE_ROLLOVER_BIG.getImageIcon());			
		jButtonXnegative.setFocusable(false);		
		jButtonXnegative.setPreferredSize(new Dimension(45,30));
		jButtonXnegative.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				SimulationTabController.jButtonsCoordinateArrowsActionPerformed(jButtonXnegative);
			}
		});
		gridBagConstraintsMoveRobot.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraintsMoveRobot.gridx = 0;
		gridBagConstraintsMoveRobot.gridy = 1;	
		jPanelMoveRobot.add(jButtonXnegative,gridBagConstraintsMoveRobot);

		jButtonZpositive.setToolTipText(TabsComponentsText.MOVE_CLOSER.getUserFriendlyName());
		jButtonZpositive.setIcon(TabsIcons.Z_POSITIVE_BIG.getImageIcon());	
		jButtonZpositive.setRolloverIcon(TabsIcons.Z_POSITIVE_ROLLOVER_BIG.getImageIcon());		
		jButtonZpositive.setFocusable(false);		
		jButtonZpositive.setPreferredSize(FramesInter.BUTTON_DIMENSION);
		jButtonZpositive.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				SimulationTabController.jButtonsCoordinateArrowsActionPerformed(jButtonZpositive);
			}
		});
		gridBagConstraintsMoveRobot.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraintsMoveRobot.gridx = 0;
		gridBagConstraintsMoveRobot.gridy = 2;	
		jPanelMoveRobot.add(jButtonZpositive,gridBagConstraintsMoveRobot);


		jSpinnerCoordinateValue = new javax.swing.JSpinner();
		jSpinnerCoordinateValue.setModel(new javax.swing.SpinnerNumberModel(Float.valueOf(0.1f), Float.valueOf(0.0f), Float.valueOf(100.0f), Float.valueOf(0.01f)));
		jSpinnerCoordinateValue.setPreferredSize(new Dimension(45,30));
		jSpinnerCoordinateValue.addChangeListener(new javax.swing.event.ChangeListener() {
	            public void stateChanged(javax.swing.event.ChangeEvent evt) {
	              SimulationTabController.setjSpinnerCoordinateValue(Float.parseFloat(jSpinnerCoordinateValue.getValue().toString()));
	            }
	        });
		
		gridBagConstraintsMoveRobot.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraintsMoveRobot.gridx = 1;
		gridBagConstraintsMoveRobot.gridy = 1;

		jPanelMoveRobot.add(jSpinnerCoordinateValue,gridBagConstraintsMoveRobot);

		jButtonZnegative.setToolTipText(TabsComponentsText.MOVE_AWAY.getUserFriendlyName());
		jButtonZnegative.setIcon(TabsIcons.Z_NEGATIVE_BIG.getImageIcon());				
		jButtonZnegative.setRolloverIcon(TabsIcons.Z_NEGATIVE_ROLLOVER_BIG.getImageIcon());	
		jButtonZnegative.setFocusable(false);		
		jButtonZnegative.setPreferredSize(new Dimension(45,30));
		jButtonZnegative.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				SimulationTabController.jButtonsCoordinateArrowsActionPerformed(jButtonZnegative);
			}
		});
		gridBagConstraintsMoveRobot.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraintsMoveRobot.gridx = 2;
		gridBagConstraintsMoveRobot.gridy = 0;	
		jPanelMoveRobot.add(jButtonZnegative,gridBagConstraintsMoveRobot);


		jButtonXpositive.setToolTipText(TabsComponentsText.MOVE_RIGHT.getUserFriendlyName());
		jButtonXpositive.setIcon(TabsIcons.X_POSITIVE_BIG.getImageIcon());	
		jButtonXpositive.setRolloverIcon(TabsIcons.X_POSITIVE_ROLLOVER_BIG.getImageIcon());		
		jButtonXpositive.setFocusable(false);		
		jButtonXpositive.setPreferredSize(new Dimension(45,30));
		jButtonXpositive.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				SimulationTabController.jButtonsCoordinateArrowsActionPerformed(jButtonXpositive);
			}
		});
		gridBagConstraintsMoveRobot.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraintsMoveRobot.gridx = 2;
		gridBagConstraintsMoveRobot.gridy = 1;	
		jPanelMoveRobot.add(jButtonXpositive,gridBagConstraintsMoveRobot);



		jButtonYnegative.setToolTipText(TabsComponentsText.MOVE_DOWN.getUserFriendlyName());
		jButtonYnegative.setIcon(TabsIcons.Y_NEGATIVE_BIG.getImageIcon());	
		jButtonYnegative.setRolloverIcon(TabsIcons.Y_NEGATIVE_ROLLOVER_BIG.getImageIcon());		
		jButtonYnegative.setFocusable(false);		
		jButtonYnegative.setPreferredSize(new Dimension(45,30));
		jButtonYnegative.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				SimulationTabController.jButtonsCoordinateArrowsActionPerformed(jButtonYnegative);
			}
		});
		gridBagConstraintsMoveRobot.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraintsMoveRobot.gridx = 1;
		gridBagConstraintsMoveRobot.gridy = 2;	
		jPanelMoveRobot.add(jButtonYnegative,gridBagConstraintsMoveRobot);
		jPanelTreeNode.add(jPanelMoveRobot);
		return jPanelTreeNode;
	}

	/**
	 * Defines visual appearance of editor panel(edit value) for tree node called Plane Size.
	 * @return jPanelEditor, the editor panel with new components in it.
	 */
	public static javax.swing.JPanel addPlaneSizeEditor(){
		javax.swing.JPanel jPanelTreeNode = new javax.swing.JPanel();
		jSpinnerPlaneSize = new javax.swing.JSpinner();
		jSpinnerPlaneSize.setModel(new javax.swing.SpinnerNumberModel(0, 0, 1000, 10));		
		jPanelTreeNode.add(jSpinnerPlaneSize);
		
		return jPanelTreeNode;
	}

	/**
	 * Defines visual appearance of editor panel(edit value) for tree node called Plane texture.
	 * @return jPanelEditor, the editor panel with new components in it.
	 */
	public static javax.swing.JPanel addPlaneTextureEditor(){
		javax.swing.JPanel jPanelTreeNode = new javax.swing.JPanel(new GridBagLayout());
		GridBagConstraints gridBagConstraintsTexture = new GridBagConstraints();

	    iconLabel = new javax.swing.JLabel();
	    iconLabel.setSize(100, 100); 
		
		jComboBoxPlaneTexture = new javax.swing.JComboBox(); 
		jComboBoxPlaneTexture.setModel(new DefaultComboBoxModel(TextureDescriptions.getAllInUserFriendlyFromat()));
		jComboBoxPlaneTexture.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				SimulationTabController.jComboBoxPlaneTextureActionPerformed(jComboBoxPlaneTexture,iconLabel);
			}
		});

		gridBagConstraintsTexture.fill = GridBagConstraints.CENTER;
		gridBagConstraintsTexture.gridx = 0;
		gridBagConstraintsTexture.gridy = 0;

		jPanelTreeNode.add(jComboBoxPlaneTexture,gridBagConstraintsTexture);

		javax.swing.JPanel previewPanel = new javax.swing.JPanel(new GridBagLayout());
		previewPanel.setPreferredSize(new Dimension(125,140));
		previewPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(""), "Preview", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.ABOVE_TOP));
		gridBagConstraintsTexture.fill = GridBagConstraints.CENTER;
		gridBagConstraintsTexture.gridx = 0;
		gridBagConstraintsTexture.gridy = 1;
		gridBagConstraintsTexture.gridwidth =1;
		gridBagConstraintsTexture.insets = new Insets(10,0,0,0);	  

		previewPanel.add(iconLabel);
		jPanelTreeNode.add(previewPanel,gridBagConstraintsTexture);
		
		return jPanelTreeNode;
	}

	/**
	 * Defines visual appearance of editor panel(edit value) for tree node called Camera position.
	 * @return jPanelEditor, the editor panel with new components in it.
	 */
	public static javax.swing.JPanel addCameraPositionEditor(){
		javax.swing.JPanel jPanelTreeNode = new javax.swing.JPanel();
		jComboBoxCameraPosition = new javax.swing.JComboBox(); 
		jComboBoxCameraPosition.setModel(new DefaultComboBoxModel(CameraPositions.getAllInUserFriendlyFromat()));
		jPanelTreeNode.add(jComboBoxCameraPosition);
		
		return jPanelTreeNode;
	}

	/**
	 * Defines visual appearance of editor panel(edit value) for tree node called The world is flat.
	 * @return jPanelEditor, the editor panel with new components in it.
	 */
	public static javax.swing.JPanel addTheWorldIsFlatEditor(){
		javax.swing.JPanel jPanelTreeNode = new javax.swing.JPanel();
		jCheckBoxTheWorldIsFlat =  new javax.swing.JCheckBox ();		
		jPanelTreeNode.add(jCheckBoxTheWorldIsFlat);
		
		return jPanelTreeNode;
	}

	/**
	 * Defines visual appearance of editor panel(edit value) for tree node called has background scenery.
	 * @return jPanelEditor, the editor panel with new components in it.
	 */
	public static javax.swing.JPanel addHasBackgroundSceneryEditor(){
		javax.swing.JPanel jPanelTreeNode = new javax.swing.JPanel();
		jCheckBoxHasBackgroundScenery =  new javax.swing.JCheckBox ();		
		jPanelTreeNode.add(jCheckBoxHasBackgroundScenery);
		return jPanelTreeNode;
	}

	/**
	 * Defines visual appearance of editor panel(edit value) for tree node called has heavy obstacles.
	 * @return jPanelEditor, the editor panel with new components in it.
	 */
	public static javax.swing.JPanel addHasHeavyObstaclesEditor(){
		javax.swing.JPanel jPanelTreeNode = new javax.swing.JPanel();
		jCheckBoxHasHeavyObstacles = new javax.swing.JCheckBox ();
		jPanelTreeNode.add(jCheckBoxHasHeavyObstacles);
		return jPanelTreeNode;
	}

	/**
	 * Defines visual appearance of editor panel(edit value) for tree node called is frame grabbing active.
	 * @return jPanelEditor, the editor panel with new components in it.
	 */
	public static javax.swing.JPanel addIsFrameGrabbingActiveEditor(){
		javax.swing.JPanel jPanelTreeNode = new javax.swing.JPanel();
		jCheckBoxIsFrameGrabbingActive = new javax.swing.JCheckBox ();
		jPanelTreeNode.add(jCheckBoxIsFrameGrabbingActive);
		return jPanelTreeNode;
	}

	/**
	 * Defines visual appearance of editor panel(edit value) for tree node called damping.
	 * @return jPanelEditor, the editor panel with new components in it.
	 */
	public static javax.swing.JPanel addDampingEditor(){
		GridBagConstraints gridBagConstraintsDamping = new GridBagConstraints();
		javax.swing.JPanel jPanelTreeNode = new javax.swing.JPanel(new GridBagLayout());	

		gridBagConstraintsDamping.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraintsDamping.gridx = 0;
		gridBagConstraintsDamping.gridy = 1;
		gridBagConstraintsDamping.gridwidth = 1;
		gridBagConstraintsDamping.insets = new Insets(20,0,0,0);

		jPanelTreeNode.add(SimulationTab.createNewLabel("Linear velocity"),gridBagConstraintsDamping);

		jSpinnerDampingLinearVelocity = new javax.swing.JSpinner();
		jSpinnerDampingLinearVelocity.setPreferredSize(new Dimension(60,20));
		jSpinnerDampingLinearVelocity.setModel(new javax.swing.SpinnerNumberModel(Float.valueOf(0.0f), null, null, Float.valueOf(1.0f)));		
		gridBagConstraintsDamping.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraintsDamping.gridx = 1;
		gridBagConstraintsDamping.gridy = 1;
		jPanelTreeNode.add(jSpinnerDampingLinearVelocity,gridBagConstraintsDamping);

		gridBagConstraintsDamping.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraintsDamping.gridx = 0;
		gridBagConstraintsDamping.gridy = 2;
		gridBagConstraintsDamping.insets = new Insets(10,0,0,0);
		jPanelTreeNode.add(SimulationTab.createNewLabel("Angular velocity"),gridBagConstraintsDamping);

		jSpinnerDampingAngularVelocity = new javax.swing.JSpinner();
		jSpinnerDampingAngularVelocity.setPreferredSize(new Dimension(60,20));
		jSpinnerDampingAngularVelocity.setModel(new javax.swing.SpinnerNumberModel(Float.valueOf(0.0f), null, null, Float.valueOf(1.0f)));		
		gridBagConstraintsDamping.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraintsDamping.gridx = 1;
		gridBagConstraintsDamping.gridy = 2;
		gridBagConstraintsDamping.insets = new Insets(10,0,0,0);
		jPanelTreeNode.add(jSpinnerDampingAngularVelocity,gridBagConstraintsDamping);	
		return jPanelTreeNode;
	}


	

	/**
	 * Defines visual appearance of editor panel(edit value) for tree node called Realistic Collision.
	 * @return jPanelEditor, the editor panel with new components in it.
	 */
	public static javax.swing.JPanel addRealisticCollisionEditor() {
		javax.swing.JPanel jPanelTreeNode = new javax.swing.JPanel();
		jCheckBoxRealisticCollision = new javax.swing.JCheckBox ();		
		jPanelTreeNode.add(jCheckBoxRealisticCollision);
		return jPanelTreeNode;
	}

	/**
	 * Defines visual appearance of editor panel(edit value) for tree node called gravity.
	 * @return jPanelEditor, the editor panel with new components in it.
	 */
	public static javax.swing.JPanel addGravityEditor() {
		javax.swing.JPanel jPanelTreeNode = new javax.swing.JPanel();
		jSpinnerGravity = new javax.swing.JSpinner();
		jSpinnerGravity.setPreferredSize(new Dimension(60,20));
		jSpinnerGravity.setModel(new javax.swing.SpinnerNumberModel(Float.valueOf(-100.0f), null, null, Float.valueOf(1.0f)));			
		jPanelTreeNode.add(jSpinnerGravity);	
		return jPanelTreeNode;
	}
	
	/**
	 * Defines visual appearance of editor panel(edit value) for tree node called Plane material.
	 * @return jPanelEditor, the editor panel with new components in it.
	 */
	public static javax.swing.JPanel addPlaneMaterialEditor() {
		javax.swing.JPanel jPanelTreeNode = new javax.swing.JPanel();
		jComboBoxPlaneMaterial = new javax.swing.JComboBox(); 
		jComboBoxPlaneMaterial.setModel(new DefaultComboBoxModel(PlaneMaterials.getAllInUserFriendlyFromat()));
		jComboBoxPlaneMaterial.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				SimulationTabController.jComboBoxPlaneMaterialActionPerformed(jComboBoxPlaneMaterial);
			}
		});
		jPanelTreeNode.add(jComboBoxPlaneMaterial);
		return jPanelTreeNode;
	}
	
	
	/**
	 * Defines visual appearance of editor panel(edit value) for tree node called Maintain rotational joint positions.
	 * @return jPanelEditor, the editor panel with new components in it.
	 */
	public static javax.swing.JPanel addMaintainRotationalJointPositionsEditor() {
		javax.swing.JPanel jPanelTreeNode = new javax.swing.JPanel();
		jCheckBoxMaintainRotJointPositions = new javax.swing.JCheckBox ();		
		jPanelTreeNode.add(jCheckBoxMaintainRotJointPositions);
		return jPanelTreeNode;
	}
	

	/**
	 * Defines visual appearance of editor panel(edit value) for tree node called Constraint Force Mix.
	 * @return jPanelEditor, the editor panel with new components in it.
	 */
	public static javax.swing.JPanel addConstraintForceMixEditor() {
		javax.swing.JPanel jPanelTreeNode = new javax.swing.JPanel();
		jSpinnerConstraintForceMix = new javax.swing.JSpinner();
		jSpinnerConstraintForceMix.setPreferredSize(new Dimension(60,20));
		jSpinnerConstraintForceMix.setModel(new javax.swing.SpinnerNumberModel(Float.valueOf(0.0f), null, null, Float.valueOf(1.0f)));		
		jPanelTreeNode.add(jSpinnerConstraintForceMix);
		return jPanelTreeNode;
	}

	/**
	 * Defines visual appearance of editor panel(edit value) for tree node called Error Reduction Parameter.
	 * @return jPanelEditor, the editor panel with new components in it.
	 */
	public static javax.swing.JPanel addErrorReductionParameterEditor() {
		javax.swing.JPanel jPanelTreeNode = new javax.swing.JPanel();
		jSpinnerErrorReductionParameter = new javax.swing.JSpinner();
		jSpinnerErrorReductionParameter.setPreferredSize(new Dimension(60,20));
		jSpinnerErrorReductionParameter.setModel(new javax.swing.SpinnerNumberModel(Float.valueOf(0.0f), Float.valueOf(0.0f), Float.valueOf(1.0f), Float.valueOf(0.1f)));	
		jPanelTreeNode.add(jSpinnerErrorReductionParameter);	
		return jPanelTreeNode;
	}

	/**
	 * Defines visual appearance of editor panel(edit value) for tree node called Resolution Factor.
	 * @return jPanelEditor, the editor panel with new components in it.
	 */
	public static javax.swing.JPanel addResolutionFactorEditor() {
		javax.swing.JPanel jPanelTreeNode = new javax.swing.JPanel();
		jSpinnerResolutionFactor = new javax.swing.JSpinner();
		jSpinnerResolutionFactor.setPreferredSize(new Dimension(60,20));
		jSpinnerResolutionFactor.setModel(new javax.swing.SpinnerNumberModel(1, null, null, 1));
		jPanelTreeNode.add(jSpinnerResolutionFactor);	
		return jPanelTreeNode;
	}

	/**
	 * Defines visual appearance of editor panel(edit value) for tree node called Use Module Event Queue.
	 * @return jPanelEditor, the editor panel with new components in it.
	 */
	public static javax.swing.JPanel addUseModuleEventQueueEditor() {
		javax.swing.JPanel jPanelTreeNode = new javax.swing.JPanel();
		jCheckBoxUseMouseEventQueue = new javax.swing.JCheckBox ();
		jPanelTreeNode.add(jCheckBoxUseMouseEventQueue);
		return jPanelTreeNode;
	}

	/**
	 * Defines visual appearance of editor panel(edit value) for tree node called Synchronize With Controllers.
	 * @return jPanelEditor, the editor panel with new components in it.
	 */
	public static  javax.swing.JPanel addSynchronizeWithControllersEditor() {
		javax.swing.JPanel jPanelTreeNode = new javax.swing.JPanel();
		jCheckBoxSynchronizeWithControllers = new javax.swing.JCheckBox ();		
		jPanelTreeNode.add(jCheckBoxSynchronizeWithControllers);	
		return jPanelTreeNode;
	}

	/**
	 * Defines visual appearance of editor panel(edit value) for tree node called Physics Simulation Controller Step Factor.
	 * @return jPanelEditor, the editor panel with new components in it.
	 */
	public static javax.swing.JPanel addPhysicsSimulationControllerStepFactor() {
		javax.swing.JPanel jPanelTreeNode = new javax.swing.JPanel();
		jPhysicsSimulationControllerStepFactor = new javax.swing.JSpinner();
		jPhysicsSimulationControllerStepFactor.setPreferredSize(new Dimension(60,20));
		jPhysicsSimulationControllerStepFactor.setModel(new javax.swing.SpinnerNumberModel(1, null, null, 1));		
		jPanelTreeNode.add(jPhysicsSimulationControllerStepFactor);
		return jPanelTreeNode;
	}

	public static void update (){
		
		/*World Description*/
		SimulationTabController.setJSpinnerPlaneSizeValue(jSpinnerPlaneSize);
		SimulationTabController.setValuejSpinnerPhysicsSimulationStepSize(jSpinnerPhysicsSimulationStepSize);
		SimulationTabController.setSelectedJComboBoxPlaneTexture(jComboBoxPlaneTexture,iconLabel);
		SimulationTabController.setSelectedJComboBoxCameraPosition(jComboBoxCameraPosition);
		SimulationTabController.setSelectedJCheckBoxTheWorldIsFlat(jCheckBoxTheWorldIsFlat);
		SimulationTabController.setSelectedJCheckBoxHasBackgroundScenery(jCheckBoxHasBackgroundScenery);
		SimulationTabController.setSelectedjCheckBoxHasHeavyObstacles(jCheckBoxHasHeavyObstacles);
		SimulationTabController.setSelectedJCheckBoxIsFrameGrabbingActive(jCheckBoxIsFrameGrabbingActive);
		
		/*Physics parameters*/
		SimulationTabController.setValuejSpinnerGravity(jSpinnerGravity);	
		SimulationTabController.setValuejSpinnerDampingLinearVelocity(jSpinnerDampingLinearVelocity);
		SimulationTabController.setValuejSpinnerDampingAngularVelocity(jSpinnerDampingAngularVelocity);
		SimulationTabController.setValuejSpinnerGravity(jSpinnerGravity);
		SimulationTabController.setSelectedJCheckBoxRealisticCollision(jCheckBoxRealisticCollision);		
		SimulationTabController.setValuejComboBoxPlaneMaterial(jComboBoxPlaneMaterial);
		SimulationTabController.setSelectedjCheckBoxMaintainRotJointPositions(jCheckBoxMaintainRotJointPositions);
		SimulationTabController.setValuejSpinnerConstraintForceMix(jSpinnerConstraintForceMix);
		SimulationTabController.setValueJSpinnerErrorReductionParameter(jSpinnerErrorReductionParameter);		
		SimulationTabController.setValueJSpinnerResolutionFactor(jSpinnerResolutionFactor);		
		SimulationTabController.setSelectedJCheckBoxUseMouseEventQueue(jCheckBoxUseMouseEventQueue);		
		SimulationTabController.setSelectedjCheckBoxSynchronizeWithControllers(jCheckBoxSynchronizeWithControllers);		
		SimulationTabController.setValuejPhysicsSimulationControllerStepFactor(jPhysicsSimulationControllerStepFactor);
		
	}

/*	public static void addMorphologyEditor() {
		Map<String,String> fileDescriptionsAndExtensions= new HashMap<String,String>();
		fileDescriptionsAndExtensions.put(FileChooserFrameInter.ROBOT_FILE_DESCRIPTION, FileChooserFrameInter.DEFAULT_FILE_EXTENSION);
		//System.out.println("BOOO"+RobotSpecification.getMorphologyLocation());

		//FileChooserFrameInter fcOpenFrame = new FileChooserOpenFrame(fileDescriptionsAndExtensions,FileChooserFrameInter.FC_XML_CONTROLLER,TemporaryRobotSpecification.getMorphologyLocation());
		//fcOpenFrame.setSelectedFile(new File("some.xml"));

		//jPanelEditor.add(MainFrames.initOpenButton(fcOpenFrame));
	}*/


	private static javax.swing.JButton jButtonYpositive,jButtonYnegative,
	jButtonXpositive,jButtonXnegative,
	jButtonZpositive,jButtonZnegative;

	private static javax.swing.JComboBox jComboBoxCameraPosition,jComboBoxPlaneMaterial,jComboBoxPlaneTexture;

	private static javax.swing.JCheckBox jCheckBoxTheWorldIsFlat,jCheckBoxHasBackgroundScenery, jCheckBoxHasHeavyObstacles,
	jCheckBoxIsFrameGrabbingActive,jCheckBoxRealisticCollision,jCheckBoxUseMouseEventQueue,
	jCheckBoxSynchronizeWithControllers,jCheckBoxMaintainRotJointPositions;

	private static javax.swing.JLabel iconLabel; 
	private static javax.swing.JPanel jPanelMoveRobot;
	private static javax.swing.JSpinner jSpinnerPlaneSize,jSpinnerDampingLinearVelocity, jSpinnerDampingAngularVelocity,
	jSpinnerPhysicsSimulationStepSize, jSpinnerGravity,jSpinnerConstraintForceMix,
	jSpinnerErrorReductionParameter, jSpinnerResolutionFactor, jPhysicsSimulationControllerStepFactor,
	jSpinnerCoordinateValue;
	
	
}
