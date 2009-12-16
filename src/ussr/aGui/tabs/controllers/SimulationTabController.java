package ussr.aGui.tabs.controllers;

import java.awt.Component;
import java.rmi.RemoteException;
import java.util.Map;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;


import ussr.aGui.MainFramesInter;
import ussr.aGui.enumerations.hintpanel.HintsSimulationTab;
import ussr.aGui.enumerations.tabs.SimulationTabTreeNodes;
import ussr.aGui.enumerations.tabs.TabsIcons;
import ussr.aGui.enumerations.tabs.TextureDescriptions;
import ussr.aGui.helpers.hintPanel.HintPanelInter;
import ussr.aGui.tabs.simulation.TemporaryRobotSpecification;
import ussr.aGui.tabs.simulation.SimulationTab;
import ussr.builder.simulationLoader.SimulationSpecification;
import ussr.description.geometry.VectorDescription;
import ussr.description.setup.ModulePosition;
import ussr.physics.PhysicsParameters;
import ussr.remote.facade.SimulationTabControlInter;



public class SimulationTabController extends TabsControllers {



	private static String selectedNodeName;

	private static SimulationSpecification simulationSpecification;

	public static void setSimulationSpecification(SimulationSpecification simulationSpecification) {
		SimulationTabController.simulationSpecification = simulationSpecification;
	}

	/**
	 * @param selectedNode
	 */
	public static void jTreeItemSelectedActionPerformed(String selectedNode) {
		selectedNodeName = selectedNode;


		SimulationTab.getJPanelEditor().removeAll();
		SimulationTab.getJPanelEditor().revalidate();
		SimulationTab.getJPanelEditor().repaint();

		if (selectedNode.contains("Robot Nr.")){
			SimulationTab.addRobotEditor();
		}else{
			switch(SimulationTabTreeNodes.valueOf(selectedNode.replace(" ", "_"))){

			case Simulation://  break through
			case World_description:
			case Physics_parameters:
				break;
				//case Robot+"nR":

				//break;
				/*	case Robot:
			SimulationTab.addRobotEditor();
			break;*/
			case Robots:
				//SimulationTab.addRobotNode();
				SimulationTab.addRobotsEditor();
				break;
			case Physics_simulation_step_size:
				SimulationTab.addPhysicsSimulationStepSizeEditor();
				break;
			case Resolution_Factor:
				SimulationTab.addResolutionFactorEditor();
				break;		
			case Type:
				//TODO
				break;
			case Morphology:
				SimulationTab.addMorphologyEditor();
				break;
			case Controller:
				//TODO
				break;
			case Plane_size:
				SimulationTab.addPlaneSizeEditor();
				break;
			case Plane_texture:
				SimulationTab.addPlaneTextureEditor();
				break;
			case Camera_position:
				SimulationTab.addCameraPositionEditor();
				break;
			case The_world_is_flat:
				SimulationTab.addTheWorldIsFlatEditor();
				break;
			case Has_background_scenery:
				SimulationTab.addHasBackgroundSceneryEditor();
				break;
			case Has_heavy_obstacles:
				SimulationTab.addHasHeavyObstaclesEditor();
				break;			
			case Is_frame_grabbing_active:
				SimulationTab.addIsFrameGrabbingActiveEditor();
				break;
			case Damping:
				SimulationTab.addDampingEditor();
				break;	
			case Realistic_collision:
				SimulationTab.addRealisticCollisionEditor();
				break;
			case Gravity:
				SimulationTab.addGravityEditor();
				break;
			case Constraint_force_mixing:
				SimulationTab.addConstraintForceMixEditor();
				break;
			case Error_reduction_parameter:
				SimulationTab.addErrorReductionParameterEditor();
				break;
			case Use_module_event_queue:
				SimulationTab.addUseMouseEventQueueEditor();
				break;
			case Synchronize_with_controllers:
				SimulationTab.addSynchronizeWithControllersEditor();
				break;
			case Physics_simulation_controller_step_factor:
				SimulationTab.addPhysicsSimulationControllerStepFactor();
				break;
			default: throw new Error("The node "+ selectedNode + " is not supported yet.");

			}
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
			throw new Error("Failed to extract plane size, due to remote exception.");
		}		
	}


	public static void setSelectedJComboBoxPlaneTexture(JComboBox jComboBoxPlaneTexture, JLabel iconLabel){
		String fileName;
		try {
			fileName = remotePhysicsSimulation.getWorldDescriptionControl().getPlaneTextureFileName();
		} catch (RemoteException e) {
			throw new Error("Failed to extract plane texture file name, due to remote exception.");
		}	

		for (int text=0;text<TextureDescriptions.values().length;text++){

			if (fileName.equals(TextureDescriptions.values()[text].getFileName())){
				jComboBoxPlaneTexture.setSelectedItem(TextureDescriptions.values()[text]);
				ImageIcon imageIcon = new ImageIcon(TextureDescriptions.values()[text].getFileName());
				iconLabel.setIcon(imageIcon);
				iconLabel.setSize(100, 100);
			}
		}





	}


	public static void setSelectedJComboBoxCameraPosition(JComboBox comboBoxCameraPosition) {
		try {
			comboBoxCameraPosition.setSelectedItem(remotePhysicsSimulation.getWorldDescriptionControl().getCameraPosition());
		} catch (RemoteException e) {
			throw new Error("Failed to extract camera position, due to remote exception.");
		}

	}

	public static void setSelectedJCheckBoxTheWorldIsFlat(JCheckBox checkBoxTheWorldIsFlat) {

		try {
			checkBoxTheWorldIsFlat.setSelected(remotePhysicsSimulation.getWorldDescriptionControl().theWorldIsFlat());
		} catch (RemoteException e) {
			throw new Error("Failed to extract the world is flat, due to remote exception.");
		}

	}

	public static void setSelectedJCheckBoxHasBackgroundScenery(JCheckBox checkBoxHasBackgroundScenery) {
		try {
			checkBoxHasBackgroundScenery.setSelected(remotePhysicsSimulation.getWorldDescriptionControl().hasBackgroundScenery());
		} catch (RemoteException e) {
			throw new Error("Failed to extract has background scenery, due to remote exception.");
		}

	}

	public static void setSelectedjCheckBoxHasHeavyObstacles(JCheckBox checkBoxHasHeavyObstacles) {
		try {
			checkBoxHasHeavyObstacles.setSelected(remotePhysicsSimulation.getWorldDescriptionControl().hasHeavyObstacles());
		} catch (RemoteException e) {
			throw new Error("Failed to extract has heavy obstacles, due to remote exception.");
		}

	}

	public static void setSelectedJCheckBoxIsFrameGrabbingActive(JCheckBox checkBoxIsFrameGrabbingActive) {
		try {
			checkBoxIsFrameGrabbingActive.setSelected(remotePhysicsSimulation.getWorldDescriptionControl().getIsFrameGrabbingActive());
		} catch (RemoteException e) {
			throw new Error("Failed to extract is frame grabbing active, due to remote exception.");
		}


	}

	public static void setJLabelRobotType(JLabel labelRobotType) {
		//TODO

	}

	public static void setValuejSpinnerDampingLinearVelocity(JSpinner spinnerDampingLinearVelocity) {
		spinnerDampingLinearVelocity.setValue(PhysicsParameters.get().getWorldDampingLinearVelocity());

	}

	public static void setValuejSpinnerDampingAngularVelocity(JSpinner spinnerDampingAngularVelocity) {
		spinnerDampingAngularVelocity.setValue(PhysicsParameters.get().getWorldDampingAngularVelocity());

	}

	public static void setValuejSpinnerPhysicsSimulationStepSize(JSpinner spinnerPhysicsSimulationStepSize) {
		spinnerPhysicsSimulationStepSize.setValue(PhysicsParameters.get().getPhysicsSimulationStepSize());
	}

	public static void setSelectedJCheckBoxRealisticCollision(JCheckBox checkBoxRealisticCollision) {
		checkBoxRealisticCollision.setSelected(PhysicsParameters.get().getRealisticCollision());
	}

	public static void setValuejSpinnerGravity(JSpinner spinnerGravity) {
		spinnerGravity.setValue(PhysicsParameters.get().getGravity());

	}

	public static void setValuejSpinnerConstraintForceMix(JSpinner spinnerConstraintForceMix) {
		spinnerConstraintForceMix.setValue(PhysicsParameters.get().getConstraintForceMix());
		SimulationTab.getHintPanel().setText(HintsSimulationTab.CONSTRAINT_FORCE_MIXING.getHintText());
	}

	public static void setValueJSpinnerErrorReductionParameter(JSpinner spinnerErrorReductionParameter) {
		spinnerErrorReductionParameter.setValue(PhysicsParameters.get().getErrorReductionParameter());
		SimulationTab.getHintPanel().setText(HintsSimulationTab.ERROR_REDUCTION_PARAMETER.getHintText());

	}

	public static void setValueJSpinnerResolutionFactor(JSpinner spinnerResolutionFactor) {
		spinnerResolutionFactor.setValue(PhysicsParameters.get().getResolutionFactor());
	}

	public static void setSelectedJCheckBoxUseMouseEventQueue(JCheckBox checkBoxUseMouseEventQueue) {
		checkBoxUseMouseEventQueue.setSelected(PhysicsParameters.get().useModuleEventQueue());

	}

	public static void setSelectedjCheckBoxSynchronizeWithControllers(JCheckBox checkBoxSynchronizeWithControllers) {
		checkBoxSynchronizeWithControllers.setSelected(PhysicsParameters.get().syncWithControllers());
	}

	public static void setValuejPhysicsSimulationControllerStepFactor(JSpinner physicsSimulationControllerStepFactor) {
		physicsSimulationControllerStepFactor.setValue(PhysicsParameters.get().getPhysicsSimulationControllerStepFactor());

	}


	private static float jSpinnerStepValue=0.1f; 

	public static void setjSpinnerCoordinateValue(float spinnerStepValue) {
		jSpinnerStepValue = spinnerStepValue;
	}

	public static void jButtonsCoordinateArrowsActionPerformed(JButton jButton) {


		VectorDescription changeInPosition = new VectorDescription(0,0,0);

		//float step = Float.parseFloat(SimulationTab.getJSpinnerCoordinateValue().getValue().toString());

		Icon icon = jButton.getIcon();
		if(icon.equals(TabsIcons.Y_POSITIVE_BIG.getImageIcon())){			
			changeInPosition = new VectorDescription(0,jSpinnerStepValue,0);
		}else if(icon.equals(TabsIcons.Y_NEGATIVE_BIG.getImageIcon())){
			changeInPosition = new VectorDescription(0,-jSpinnerStepValue,0);			
		}else if (icon.equals(TabsIcons.X_POSITIVE_BIG.getImageIcon())){
			changeInPosition = new VectorDescription(jSpinnerStepValue,0,0);
		}else if (icon.equals(TabsIcons.X_NEGATIVE_BIG.getImageIcon())){
			changeInPosition = new VectorDescription(-jSpinnerStepValue,0,0);
		}else if (icon.equals(TabsIcons.Z_POSITIVE_BIG.getImageIcon())){
			changeInPosition = new VectorDescription(0,0,jSpinnerStepValue);
		}else if (icon.equals(TabsIcons.Z_NEGATIVE_BIG.getImageIcon())){
			changeInPosition = new VectorDescription(0,0,-jSpinnerStepValue);
		}

		int stringLenght = selectedNodeName.toCharArray().length;
		int selectedRobot=-1;

		switch(stringLenght){
		case 10:// up to 9 robots
			char robotNumber = selectedNodeName.toCharArray()[9];
			selectedRobot = Integer.parseInt(robotNumber+"");
			break;
		case 11:// up to 99 robots (NOT TESTED YET)
			char robotNr1 = selectedNodeName.toCharArray()[9];
			char robotNr2 = selectedNodeName.toCharArray()[10];
			selectedRobot = Integer.parseInt(robotNr1+robotNr2+"");
			break;
		default: throw new Error("Robot title changed in tree structure or missing support.The robot title should be for instance: Robot Nr.1");
		}

		int amountRobotModules = simulationSpecification.getRobotsInSimulation().get(selectedRobot-1).getAmountModules();

		if (selectedRobot==1){			
			moveRobot(0,amountRobotModules,changeInPosition); 
		}else{
			int amountAllRobotsModules=0;

			while(selectedRobot!= 0){

				amountAllRobotsModules =amountAllRobotsModules+ simulationSpecification.getRobotsInSimulation().get(selectedRobot-1).getAmountModules();
				selectedRobot--;
				System.out.println("All"+amountAllRobotsModules );
			}
			moveRobot(amountAllRobotsModules-amountRobotModules,amountAllRobotsModules,changeInPosition);
		}


	}

	private static void moveRobot(int firstModuleId,int amountModules,VectorDescription changeInPosition){
		for (int moduleID=firstModuleId; moduleID<amountModules;moduleID++){
			try {
				VectorDescription modulePosition = remotePhysicsSimulation.getSimulationTabControl().getModulePosition(moduleID);	
				remotePhysicsSimulation.getSimulationTabControl().setModulePosition(moduleID, new VectorDescription(modulePosition.getX()+changeInPosition.getX(),modulePosition.getY()+changeInPosition.getY(),modulePosition.getZ()+changeInPosition.getZ()));
			} catch (RemoteException e) {
				//throw new Error("SOME");
			}
		}
	}



}
