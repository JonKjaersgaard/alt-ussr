package ussr.aGui.tabs.simulation;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.border.TitledBorder;

import ussr.aGui.FramesInter;
import ussr.aGui.enumerations.tabs.SimulationTabComponentsText;
import ussr.aGui.enumerations.tabs.SimulationTabTreeNodes;
import ussr.aGui.enumerations.tabs.TabsIcons;
import ussr.aGui.enumerations.tabs.TextureDescriptions;
import ussr.aGui.fileChooser.views.FileChooserFrameInter;
import ussr.aGui.helpers.hintPanel.HintPanel;
import ussr.aGui.tabs.Tabs;
import ussr.aGui.tabs.controllers.SimulationTabController;
import ussr.builder.helpers.StringProcessingHelper;
import ussr.description.setup.WorldDescription.CameraPosition;

public class SimulationTreeEditors{

	private static javax.swing.JPanel jPanelEditor = SimulationTab.getJPanelEditor();
	
	public static void addRobotsEditor(){
	     jPanelEditor.add(createNewLabel("Load new robot "));
		jPanelEditor.add(Tabs.initOpenButton());
	}



	public static void addRobotEditor(){
		//jPanelEditor.add(createNewLabel("Robot"));
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
		displayTitle1 = BorderFactory.createTitledBorder("New position");
		displayTitle1.setTitleJustification(TitledBorder.CENTER);
		jPanelMoveRobot.setBorder(displayTitle1);
		
		jButtonYpositive.setToolTipText(StringProcessingHelper.replaceUnderscoreWithSpace(SimulationTabComponentsText.Move_up));
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


		jButtonXnegative.setToolTipText(StringProcessingHelper.replaceUnderscoreWithSpace(SimulationTabComponentsText.Move_left));
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

		jButtonZpositive.setToolTipText(StringProcessingHelper.replaceUnderscoreWithSpace(SimulationTabComponentsText.Move_closer));
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

		jButtonZnegative.setToolTipText(StringProcessingHelper.replaceUnderscoreWithSpace(SimulationTabComponentsText.Move_away));
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


		jButtonXpositive.setToolTipText(StringProcessingHelper.replaceUnderscoreWithSpace(SimulationTabComponentsText.Move_right));
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



		jButtonYnegative.setToolTipText(StringProcessingHelper.replaceUnderscoreWithSpace(SimulationTabComponentsText.Move_down));
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


		jPanelEditor.add(jPanelMoveRobot);




	}

	public static javax.swing.JPanel getJPanelEditor() {

		return jPanelEditor;
	}





	public static void addPlaneSizeEditor(){
		jPanelEditor.add(createNewLabel(StringProcessingHelper.replaceUnderscoreWithSpace(SimulationTabTreeNodes.Plane_size)));
		jSpinnerPlaneSize = new javax.swing.JSpinner();
		jSpinnerPlaneSize.setModel(new javax.swing.SpinnerNumberModel(0, 0, 1000, 10));
		SimulationTabController.setJSpinnerPlaneSizeValue(jSpinnerPlaneSize);
		jPanelEditor.add(jSpinnerPlaneSize);
	}



	public static void addPlaneTextureEditor(){
		GridBagConstraints gridBagConstraintsTexture = new GridBagConstraints();

		gridBagConstraintsTexture.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraintsTexture.gridx = 0;
		gridBagConstraintsTexture.gridy = 0;

		jPanelEditor.add(createNewLabel(StringProcessingHelper.replaceUnderscoreWithSpace(SimulationTabTreeNodes.Plane_texture)),gridBagConstraintsTexture);


		jComboBoxPlaneTexture = new javax.swing.JComboBox(); 
		jComboBoxPlaneTexture.setModel(new DefaultComboBoxModel(TextureDescriptions.values()));

		gridBagConstraintsTexture.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraintsTexture.gridx = 1;
		gridBagConstraintsTexture.gridy = 0;

		jPanelEditor.add(jComboBoxPlaneTexture,gridBagConstraintsTexture);

		javax.swing.JPanel previewPanel = new javax.swing.JPanel(new GridBagLayout());
		previewPanel.setPreferredSize(new Dimension(100,100));
		previewPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(""), "Preview", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.ABOVE_TOP));
		gridBagConstraintsTexture.fill = GridBagConstraints.CENTER;
		gridBagConstraintsTexture.gridx = 0;
		gridBagConstraintsTexture.gridy = 1;
		gridBagConstraintsTexture.gridwidth =2;
		gridBagConstraintsTexture.insets = new Insets(10,0,0,0);

		javax.swing.JLabel iconLabel = new javax.swing.JLabel();

		SimulationTabController.setSelectedJComboBoxPlaneTexture(jComboBoxPlaneTexture,iconLabel);

		previewPanel.add(iconLabel);




		jPanelEditor.add(previewPanel,gridBagConstraintsTexture);
	}

	public static void addCameraPositionEditor(){

		jPanelEditor.add(createNewLabel(StringProcessingHelper.replaceUnderscoreWithSpace(SimulationTabTreeNodes.Camera_position)));
		jComboBoxCameraPosition = new javax.swing.JComboBox(); 
		jComboBoxCameraPosition.setModel(new DefaultComboBoxModel(CameraPosition.values()));
		SimulationTabController.setSelectedJComboBoxCameraPosition(jComboBoxCameraPosition);
		jPanelEditor.add(jComboBoxCameraPosition);

	}

	public static void addTheWorldIsFlatEditor(){
		jPanelEditor.add(createNewLabel(StringProcessingHelper.replaceUnderscoreWithSpace(SimulationTabTreeNodes.The_world_is_flat)));
		jCheckBoxTheWorldIsFlat =  new javax.swing.JCheckBox ();
		SimulationTabController.setSelectedJCheckBoxTheWorldIsFlat(jCheckBoxTheWorldIsFlat);

		jPanelEditor.add(jCheckBoxTheWorldIsFlat);
	}


	public static void addHasBackgroundSceneryEditor(){
		jPanelEditor.add(createNewLabel(StringProcessingHelper.replaceUnderscoreWithSpace(SimulationTabTreeNodes.Has_background_scenery)));
		jCheckBoxHasBackgroundScenery =  new javax.swing.JCheckBox ();
		SimulationTabController.setSelectedJCheckBoxHasBackgroundScenery(jCheckBoxHasBackgroundScenery);
		jPanelEditor.add(jCheckBoxHasBackgroundScenery);
	}

	public static void addHasHeavyObstaclesEditor(){
		jPanelEditor.add(createNewLabel(StringProcessingHelper.replaceUnderscoreWithSpace(SimulationTabTreeNodes.Has_heavy_obstacles)));
		jCheckBoxHasHeavyObstacles = new javax.swing.JCheckBox ();
		SimulationTabController.setSelectedjCheckBoxHasHeavyObstacles(jCheckBoxHasHeavyObstacles);

		jPanelEditor.add(jCheckBoxHasHeavyObstacles);

	}

	public static void addIsFrameGrabbingActiveEditor(){
		jPanelEditor.add(createNewLabel(StringProcessingHelper.replaceUnderscoreWithSpace(SimulationTabTreeNodes.Is_frame_grabbing_active)));
		jCheckBoxIsFrameGrabbingActive = new javax.swing.JCheckBox ();
		SimulationTabController.setSelectedJCheckBoxIsFrameGrabbingActive(jCheckBoxIsFrameGrabbingActive);

		jPanelEditor.add(jCheckBoxIsFrameGrabbingActive);

	}

	public static void addRobotTypeEditor(){
		jLabelRobotType = new javax.swing.JLabel(); 
		SimulationTabController.setJLabelRobotType(jLabelRobotType);

		jPanelEditor.add(jLabelRobotType);

	}

	public static void addDampingEditor(){

		GridBagConstraints gridBagConstraintsDamping = new GridBagConstraints();


		gridBagConstraintsDamping.fill = GridBagConstraints.CENTER;
		gridBagConstraintsDamping.gridx = 0;
		gridBagConstraintsDamping.gridy = 0;
		gridBagConstraintsDamping.gridwidth = 2;
		jPanelEditor.add(createNewLabel(StringProcessingHelper.replaceUnderscoreWithSpace(SimulationTabTreeNodes.Damping)),gridBagConstraintsDamping);

		gridBagConstraintsDamping.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraintsDamping.gridx = 0;
		gridBagConstraintsDamping.gridy = 1;
		gridBagConstraintsDamping.gridwidth = 1;
		gridBagConstraintsDamping.insets = new Insets(20,0,0,0);

		jPanelEditor.add(createNewLabel(StringProcessingHelper.replaceUnderscoreWithSpace(SimulationTabTreeNodes.Linear_velocity)),gridBagConstraintsDamping);

		jSpinnerDampingLinearVelocity = new javax.swing.JSpinner();
		jSpinnerDampingLinearVelocity.setPreferredSize(new Dimension(60,20));
		jSpinnerDampingLinearVelocity.setModel(new javax.swing.SpinnerNumberModel(Float.valueOf(0.0f), null, null, Float.valueOf(1.0f)));
		SimulationTabController.setValuejSpinnerDampingLinearVelocity(jSpinnerDampingLinearVelocity);		
		gridBagConstraintsDamping.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraintsDamping.gridx = 1;
		gridBagConstraintsDamping.gridy = 1;
		jPanelEditor.add(jSpinnerDampingLinearVelocity,gridBagConstraintsDamping);

		gridBagConstraintsDamping.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraintsDamping.gridx = 0;
		gridBagConstraintsDamping.gridy = 2;
		gridBagConstraintsDamping.insets = new Insets(10,0,0,0);
		jPanelEditor.add(createNewLabel(StringProcessingHelper.replaceUnderscoreWithSpace(SimulationTabTreeNodes.Angular_velocity)),gridBagConstraintsDamping);

		jSpinnerDampingAngularVelocity = new javax.swing.JSpinner();
		jSpinnerDampingAngularVelocity.setPreferredSize(new Dimension(60,20));
		jSpinnerDampingAngularVelocity.setModel(new javax.swing.SpinnerNumberModel(Float.valueOf(0.0f), null, null, Float.valueOf(1.0f)));
		SimulationTabController.setValuejSpinnerDampingAngularVelocity(jSpinnerDampingAngularVelocity);
		gridBagConstraintsDamping.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraintsDamping.gridx = 1;
		gridBagConstraintsDamping.gridy = 2;
		gridBagConstraintsDamping.insets = new Insets(10,0,0,0);
		jPanelEditor.add(jSpinnerDampingAngularVelocity,gridBagConstraintsDamping);	


	}



	public static void addPhysicsSimulationStepSizeEditor() {
		jPanelEditor.add(createNewLabel(StringProcessingHelper.replaceUnderscoreWithSpace(SimulationTabTreeNodes.Physics_simulation_step_size)));
		jSpinnerPhysicsSimulationStepSize = new javax.swing.JSpinner();
		jSpinnerPhysicsSimulationStepSize.setPreferredSize(new Dimension(60,20));
		jSpinnerPhysicsSimulationStepSize.setModel(new javax.swing.SpinnerNumberModel(Float.valueOf(0.0f), null, null, Float.valueOf(1.0f)));
		SimulationTabController.setValuejSpinnerPhysicsSimulationStepSize(jSpinnerPhysicsSimulationStepSize);		
		jPanelEditor.add(jSpinnerPhysicsSimulationStepSize);	

	}

	public static void addRealisticCollisionEditor() {
		jPanelEditor.add(createNewLabel(StringProcessingHelper.replaceUnderscoreWithSpace(SimulationTabTreeNodes.Realistic_collision)));
		jCheckBoxRealisticCollision = new javax.swing.JCheckBox ();
		SimulationTabController.setSelectedJCheckBoxRealisticCollision(jCheckBoxRealisticCollision);		
		jPanelEditor.add(jCheckBoxRealisticCollision);

	}

	public static void addGravityEditor() {
		jPanelEditor.add(createNewLabel(StringProcessingHelper.replaceUnderscoreWithSpace(SimulationTabTreeNodes.Gravity)));
		jSpinnerGravity = new javax.swing.JSpinner();
		jSpinnerGravity.setPreferredSize(new Dimension(60,20));
		jSpinnerGravity.setModel(new javax.swing.SpinnerNumberModel(Float.valueOf(-100.0f), null, null, Float.valueOf(1.0f)));
		SimulationTabController.setValuejSpinnerGravity(jSpinnerGravity);		
		jPanelEditor.add(jSpinnerGravity);	

	}

	public static void addConstraintForceMixEditor() {

		jPanelEditor.add(createNewLabel(StringProcessingHelper.replaceUnderscoreWithSpace(SimulationTabTreeNodes.Constraint_force_mixing)));
		jSpinnerConstraintForceMix = new javax.swing.JSpinner();
		jSpinnerConstraintForceMix.setPreferredSize(new Dimension(60,20));
		jSpinnerConstraintForceMix.setModel(new javax.swing.SpinnerNumberModel(Float.valueOf(0.0f), null, null, Float.valueOf(1.0f)));
		SimulationTabController.setValuejSpinnerConstraintForceMix(jSpinnerConstraintForceMix);		
		jPanelEditor.add(jSpinnerConstraintForceMix);		
	}

	public static void addErrorReductionParameterEditor() {
		jPanelEditor.add(createNewLabel(StringProcessingHelper.replaceUnderscoreWithSpace(SimulationTabTreeNodes.Error_reduction_parameter)));
		jSpinnerErrorReductionParameter = new javax.swing.JSpinner();
		jSpinnerErrorReductionParameter.setPreferredSize(new Dimension(60,20));
		jSpinnerErrorReductionParameter.setModel(new javax.swing.SpinnerNumberModel(Float.valueOf(0.0f), Float.valueOf(0.0f), Float.valueOf(1.0f), Float.valueOf(0.1f)));
		SimulationTabController.setValueJSpinnerErrorReductionParameter(jSpinnerErrorReductionParameter);		
		jPanelEditor.add(jSpinnerErrorReductionParameter);	

	}

	public static void addResolutionFactorEditor() {
		jPanelEditor.add(createNewLabel(StringProcessingHelper.replaceUnderscoreWithSpace(SimulationTabTreeNodes.Resolution_Factor)));
		jSpinnerResolutionFactor = new javax.swing.JSpinner();
		jSpinnerResolutionFactor.setPreferredSize(new Dimension(60,20));
		jSpinnerResolutionFactor.setModel(new javax.swing.SpinnerNumberModel(1, null, null, 1));
		SimulationTabController.setValueJSpinnerResolutionFactor(jSpinnerResolutionFactor);		
		jPanelEditor.add(jSpinnerResolutionFactor);			
	}

	public static void addUseMouseEventQueueEditor() {
		jPanelEditor.add(createNewLabel(StringProcessingHelper.replaceUnderscoreWithSpace(SimulationTabTreeNodes.Use_module_event_queue)));
		jCheckBoxUseMouseEventQueue = new javax.swing.JCheckBox ();
		SimulationTabController.setSelectedJCheckBoxUseMouseEventQueue(jCheckBoxUseMouseEventQueue);		
		jPanelEditor.add(jCheckBoxUseMouseEventQueue);		
	}

	public static void addSynchronizeWithControllersEditor() {
		jPanelEditor.add(createNewLabel(StringProcessingHelper.replaceUnderscoreWithSpace(SimulationTabTreeNodes.Synchronize_with_controllers)));
		jCheckBoxSynchronizeWithControllers = new javax.swing.JCheckBox ();
		SimulationTabController.setSelectedjCheckBoxSynchronizeWithControllers(jCheckBoxSynchronizeWithControllers);		
		jPanelEditor.add(jCheckBoxSynchronizeWithControllers);	

	}

	public static void addPhysicsSimulationControllerStepFactor() {
		jPanelEditor.add(createNewLabel(StringProcessingHelper.replaceUnderscoreWithSpace(SimulationTabTreeNodes.Physics_simulation_controller_step_factor)));
		jPhysicsSimulationControllerStepFactor = new javax.swing.JSpinner();
		jPhysicsSimulationControllerStepFactor.setPreferredSize(new Dimension(60,20));
		jPhysicsSimulationControllerStepFactor.setModel(new javax.swing.SpinnerNumberModel(1, null, null, 1));
		SimulationTabController.setValuejPhysicsSimulationControllerStepFactor(jPhysicsSimulationControllerStepFactor);		
		jPanelEditor.add(jPhysicsSimulationControllerStepFactor);	

	}





	public static void addMorphologyEditor() {
		Map<String,String> fileDescriptionsAndExtensions= new HashMap<String,String>();
		fileDescriptionsAndExtensions.put(FileChooserFrameInter.ROBOT_FILE_DESCRIPTION, FileChooserFrameInter.DEFAULT_FILE_EXTENSION);
		//System.out.println("BOOO"+RobotSpecification.getMorphologyLocation());

		//FileChooserFrameInter fcOpenFrame = new FileChooserOpenFrame(fileDescriptionsAndExtensions,FileChooserFrameInter.FC_XML_CONTROLLER,TemporaryRobotSpecification.getMorphologyLocation());
		//fcOpenFrame.setSelectedFile(new File("some.xml"));

		//jPanelEditor.add(MainFrames.initOpenButton(fcOpenFrame));
	}


	private static javax.swing.JLabel createNewLabel(String labelText){
		newLabel =  new javax.swing.JLabel();
		newLabel.setText(labelText+" ");
		return newLabel;
	}





	private static javax.swing.JTree jTreeSimulation;
	private static javax.swing.JScrollPane jScrollPaneTreeSimulation;
	private static javax.swing.JButton jButtonOpenMorphology,jButtonYpositive,jButtonYnegative,
	jButtonXpositive,jButtonXnegative,
	jButtonZpositive,jButtonZnegative;

	private static javax.swing.JSpinner  jSpinnerPlaneSize ;
	private static javax.swing.JComboBox jComboBoxPlaneTexture;
	private static javax.swing.JComboBox jComboBoxCameraPosition;

	private static javax.swing.JCheckBox jCheckBoxTheWorldIsFlat,jCheckBoxHasBackgroundScenery, jCheckBoxHasHeavyObstacles,
	jCheckBoxIsFrameGrabbingActive,jCheckBoxRealisticCollision,jCheckBoxUseMouseEventQueue,
	jCheckBoxSynchronizeWithControllers;

	private static javax.swing.JLabel jLabelRobotType;

	private static javax.swing.JLabel newLabel;
	private static javax.swing.JPanel jPanelMoveRobot;
	private static javax.swing.JSpinner jSpinnerDampingLinearVelocity, jSpinnerDampingAngularVelocity,
	jSpinnerPhysicsSimulationStepSize, jSpinnerGravity,jSpinnerConstraintForceMix,
	jSpinnerErrorReductionParameter, jSpinnerResolutionFactor, jPhysicsSimulationControllerStepFactor,
	jSpinnerCoordinateValue;
	
	
}
