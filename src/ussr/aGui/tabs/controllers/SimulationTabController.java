package ussr.aGui.tabs.controllers;

import java.awt.event.MouseEvent;
import java.rmi.RemoteException;
import java.util.Hashtable;
import java.util.Map;

import javax.swing.table.DefaultTableModel;

import ussr.aGui.enumerations.TextureDescriptions;
import ussr.aGui.tabs.SimulationTab;

import ussr.description.setup.WorldDescription;
import ussr.description.setup.WorldDescription.CameraPosition;
import ussr.description.setup.WorldDescription.TextureDescription;
import ussr.physics.PhysicsParameters;


public class SimulationTabController extends TabsControllers {

	
	//private static SimulationDescriptionConverter descriptionConverter;
	
/*	public static void setDescriptionConverter(SimulationDescriptionConverter descriptionConverter) {
		SimulationTabController.descriptionConverter = descriptionConverter;
	}*/
	//private static Map<String,Object> table = new Hashtable<String,Object>();
	
	//static javax.swing.table.DefaultTableModel model1;

	/*public static void updateTable(){
		
		SimulationTab.setJTablesVisible(true);
		
		javax.swing.table.DefaultTableModel model = (DefaultTableModel) SimulationTab.getJTableWorldDescription().getModel();
		//descriptionConverter.
		int planeSize=0;
		CameraPosition cameraPosition = null;
		boolean hasBackgroundScenery = false, hasHeavyObstacles = false,theWorldIsFlat = false,
		isFrameGrabingActive = false, bigObstacles = false;
		TextureDescription planeTexture = null;
		final String[] worldDescriptionParameters = {"Plane Size","Plane Texture","Camera Position",
                "The world is flat","Has background scenery",
                "Has heavy obstacles","Is frame grabbing active", 
                		"Big obstacles"};
		String teture = null;
		try {
			planeSize = remotePhysicsSimulation.getWorldDescriptionControl().getPlaneSize();
			theWorldIsFlat = remotePhysicsSimulation.getWorldDescriptionControl().theWorldIsFlat();
			hasBackgroundScenery = 	remotePhysicsSimulation.getWorldDescriptionControl().hasBackgroundScenery();
			hasHeavyObstacles = remotePhysicsSimulation.getWorldDescriptionControl().hasHeavyObstacles();
			cameraPosition = remotePhysicsSimulation.getWorldDescriptionControl().getCameraPosition();
			isFrameGrabingActive = remotePhysicsSimulation.getWorldDescriptionControl().getIsFrameGrabbingActive();
			//TextureDescription texture =remotePhysicsSimulation.getWorldDescriptionControl().getPlaneTexture();
			// teture = TextureDescriptions.texture(texture);
			//bigObstacles = remotePhysicsSimulation.getWorldDescriptionControl()
		} catch (RemoteException e) {
			throw new Error("Failed to exctract one of simulation description values, due to remote exception");
		}
		
		model.setValueAt(planeSize, 0, 1);		
		//model.setValueAt(texture, 1, 1);//for testing
		model.setValueAt(cameraPosition.name(), 2, 1);		
		model.setValueAt(theWorldIsFlat, 3, 1);
		model.setValueAt(hasBackgroundScenery, 4, 1);
		model.setValueAt(hasHeavyObstacles, 5, 1);
		model.setValueAt(isFrameGrabingActive, 6, 1);
		
		
		
		javax.swing.table.DefaultTableModel model1 = (DefaultTableModel) SimulationTab.getJTablePhysicsParameters().getModel();
		model1.setValueAt(PhysicsParameters.get().getWorldDampingLinearVelocity(), 0, 1);
		model1.setValueAt(PhysicsParameters.get().getWorldDampingAngularVelocity(), 1, 1);
		model1.setValueAt(PhysicsParameters.get().getPhysicsSimulationStepSize(), 2, 1);
		model1.setValueAt(PhysicsParameters.get().getRealisticCollision(), 3, 1);
		model1.setValueAt(PhysicsParameters.get().getGravity(), 4, 1);
		model1.setValueAt(PhysicsParameters.get().getPlaneMaterial().toString(), 5, 1);
		model1.setValueAt(PhysicsParameters.get().getMaintainRotationalJointPositions(), 6, 1);
		//model1.setValueAt(PhysicsParameters.get(), 6, 1); bIG oBSTACLES.
		//model1.setValueAt(PhysicsParameters.get().ge, 7, 1); Constarint force mix
		model1.setValueAt(PhysicsParameters.get().getResolutionFactor(), 8, 1); 
		//model1.setValueAt(PhysicsParameters.get()., 9, 1);//mouse evenet queq
		model1.setValueAt(PhysicsParameters.get().syncWithControllers(), 10, 1);
		model1.setValueAt(PhysicsParameters.get().getPhysicsSimulationControllerStepFactor(), 11, 1); 
	}

	public static void jTablePhysicsParametersMouseReleasedActionPerformed(
			MouseEvent evt) {
		
		System.out.println("INNNNNNNNN");
		//PhysicsParameters.get().setGravity(	0/*Float.parseFloat(model1.getValueAt(4, 1).toString()));*/
/*		try {
			remotePhysicsSimulation.getWorldDescriptionControl().setPlaneSize(2);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	
		
	//}
	
	

}
