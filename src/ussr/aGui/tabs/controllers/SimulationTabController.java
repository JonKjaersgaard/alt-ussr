package ussr.aGui.tabs.controllers;



import java.awt.Component;
import java.rmi.RemoteException;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;


import ussr.aGui.MainFramesInter;
import ussr.aGui.enumerations.TextureDescriptions;
import ussr.aGui.enumerations.TreeElements;
import ussr.aGui.tabs.SimulationTab;
import ussr.physics.PhysicsParameters;



public class SimulationTabController extends TabsControllers {





	//private static int  firtsTime =0;
	/**
	 * @param selectedNode
	 */
	public static void jTreeItemSelectedActionPerformed(String selectedNode) {
		
	   SimulationTab.getJPanelEditor().removeAll();
	   SimulationTab.getJPanelEditor().revalidate();
	   SimulationTab.getJPanelEditor().repaint();
	

		switch(TreeElements.valueOf(selectedNode.replace(" ", "_"))){
		
		case Physics_Simulation_Step_Size:
			SimulationTab.addPhysicsSimulationStepSizeEditor();
			break;
		case Resolution_Factor:
			SimulationTab.addResolutionFactorEditor();
			break;
		case Type:
			//TODO
			break;
		case Morphology_Location:
			//TODO
			break;
		case Controller_Location:
			//TODO
			break;
		case Plane_Size:
			SimulationTab.addPlaneSizeEditor();
			break;
		case Plane_Texture:
			SimulationTab.addPlaneTextureEditor();
			break;
		case Camera_Position:
			SimulationTab.addCameraPositionEditor();
			break;
		case The_World_Is_Flat:
			SimulationTab.addTheWorldIsFlatEditor();
			break;
		case Has_Background_Scenery:
			SimulationTab.addHasBackgroundSceneryEditor();
			break;
		case Has_Heavy_Obstacles:
			SimulationTab.addHasHeavyObstaclesEditor();
			break;			
		case Is_Frame_Grabbing_Active:
			SimulationTab.addIsFrameGrabbingActiveEditor();
			break;
		case Linear_Velocity:
			SimulationTab.addDampingLinearVelocityEditor();
			break;
		case Angular_Velocity:
			SimulationTab.addDampingAngularVelocityEditor();
			break;
		case Realistic_Collision:
			SimulationTab.addRealisticCollisionEditor();
			break;
		case Gravity:
			SimulationTab.addGravityEditor();
			break;
		case Constraint_Force_Mix:
			SimulationTab.addConstraintForceMixEditor();
			break;
		case Error_Reduction_Parameter:
			SimulationTab.addErrorReductionParameterEditor();
			break;
		case Use_Mouse_Event_Queue:
			SimulationTab.addUseMouseEventQueueEditor();
			break;
		case Synchronize_With_Controllers:
			SimulationTab.addSynchronizeWithControllersEditor();
			break;
		case Physics_Simulation_Controller_Step_Factor:
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
	
	public static void setSelectedJComboBoxPlaneTexture(JComboBox jComboBoxPlaneTexture){
		String fileName;
		try {
         fileName = remotePhysicsSimulation.getWorldDescriptionControl().getPlaneTextureFileName();
		} catch (RemoteException e) {
			throw new Error("Failed to extract plane texture file name, due to remote exception.");
		}	
		
		for (int text=0;text<TextureDescriptions.values().length;text++){
			
			if (fileName.equals(TextureDescriptions.values()[text].getFileName())){
				jComboBoxPlaneTexture.setSelectedItem(TextureDescriptions.values()[text]);
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
