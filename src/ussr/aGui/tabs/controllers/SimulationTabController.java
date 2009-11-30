package ussr.aGui.tabs.controllers;

import java.awt.Component;
import java.rmi.RemoteException;

import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;


import ussr.aGui.MainFramesInter;
import ussr.aGui.enumerations.TextureDescriptions;
import ussr.aGui.enumerations.SimulationTabTreeNodes;
import ussr.aGui.tabs.SimulationTab;
import ussr.aGui.tabs.additionalResources.HintPanelInter;
import ussr.physics.PhysicsParameters;



public class SimulationTabController extends TabsControllers {

	
	
	/**
	 * @param selectedNode
	 */
	public static void jTreeItemSelectedActionPerformed(String selectedNode) {
		
	   SimulationTab.getJPanelEditor().removeAll();
	   SimulationTab.getJPanelEditor().revalidate();
	   SimulationTab.getJPanelEditor().repaint();
	

		switch(SimulationTabTreeNodes.valueOf(selectedNode.replace(" ", "_"))){
		
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
		case Linear_velocity:
			SimulationTab.addDampingLinearVelocityEditor();
			break;
		case Angular_velocity:
			SimulationTab.addDampingAngularVelocityEditor();
			break;
		case Realistic_collision:
			SimulationTab.addRealisticCollisionEditor();
			break;
		case Gravity:
			SimulationTab.addGravityEditor();
			break;
		case Constraint_force_mix:
			SimulationTab.addConstraintForceMixEditor();
			break;
		case Error_reduction_parameter:
			SimulationTab.addErrorReductionParameterEditor();
			break;
		case Use_mouse_event_queue:
			SimulationTab.addUseMouseEventQueueEditor();
			break;
		case Synchronize_with_controllers:
			SimulationTab.addSynchronizeWithControllersEditor();
			break;
		case Physics_simulation_controller_step_factor:
			SimulationTab.addPhysicsSimulationControllerStepFactor();
			break;
		default: throw new Error("The node "+ selectedNode + "is not supported yet.");
		
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

	public static void jButtonPlaneTiltRightActionPerformed() {
		// TODO Auto-generated method stub

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
	}

	public static void setValueJSpinnerErrorReductionParameter(JSpinner spinnerErrorReductionParameter) {
		spinnerErrorReductionParameter.setValue(PhysicsParameters.get().getErrorReductionParameter());
		SimulationTab.getHintPanel().setText(HintPanelInter.builInHintsSimulationTab[0]);
		
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
	
	

}
