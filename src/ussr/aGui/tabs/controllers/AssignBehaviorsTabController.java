package ussr.aGui.tabs.controllers;

import java.rmi.RemoteException;
import java.util.Vector;

import javax.swing.AbstractButton;
import javax.swing.JCheckBox;
import javax.swing.JRadioButton;
import javax.swing.JTable;

import ussr.aGui.MainFrameController;
import ussr.aGui.tabs.additionalResources.HintPanelInter;
import ussr.aGui.tabs.views.constructionTabs.AssignBehaviorsTab;
import ussr.aGui.tabs.views.constructionTabs.AssignBehaviorsTabInter;
import ussr.aGui.tabs.views.constructionTabs.AssignBehaviorsTabInter.EntitiesForLabelingText;
import ussr.builder.controllerAdjustmentTool.AssignControllerTool;
import ussr.builder.enums.SupportedModularRobots;
import ussr.builder.helpers.BuilderHelper;
import ussr.builder.labelingTools.LabeledEntities;
import ussr.builder.labelingTools.LabelingTemplate;
import ussr.builder.labelingTools.LabelingToolSpecification;
import ussr.builder.labelingTools.LabelingTools;
import ussr.model.Module;
import ussr.physics.jme.JMESimulation;
import ussr.remote.facade.BuilderControlInter;
import ussr.remote.facade.BuilderSupportingUnicastPickers;

public class AssignBehaviorsTabController implements AssignBehaviorsTabInter {

	/**
	 * Container for keeping all classes of controllers extracted from package "ussr.builder.controllerAdjustmentTool";
	 */
	private static  Vector<String> classesOfControllers ;
	
	/**
	 * Temporary container for keeping classes of controllers filtered out for specific modular robot.
	 */
	private static  Vector<String> tempClassesOfControllers =  new Vector<String> ()  ;
	
	/**
	 * Current entity for labeling;
	 */
	private static String chosenRadioEntityText;

	/**
	 * The physical simulation.
	 */
	private static JMESimulation localJMEsimulation;	

	/**
	 * The name of the package where all behaviors are stored for interactive adjustment of controller.
	 */
	private static final String packageName = "ussr.builder.controllerAdjustmentTool";

	/**
	 * Loads all existing names of controllers from package ussr.builder.controllerAdjustmentTool and filters
	 * out the ones for selected button (modular robot name).
	 * @param radionButton, the radio button representing modular robot name.
	 * @param jmeSimulation, the physical simulation.
	 */
	public static void jButtonGroupActionPerformed(javax.swing.AbstractButton radionButton,JMESimulation jmeSimulation){
		loadExistingControllers(AssignBehaviorsTab.getJList1());

		ModularRobotsNames[] modularRobotsNames = ModularRobotsNames.values();
		boolean modularRobotNameExists = false;
		for (int buttonTextItem=0;buttonTextItem<modularRobotsNames.length;buttonTextItem++){
			if (radionButton.getText().equals(modularRobotsNames[buttonTextItem].toString())){
				updateList(AssignBehaviorsTab.getJList1(),filterOut(modularRobotsNames[buttonTextItem]));
				modularRobotNameExists =true;
			}
		}
		
		if (modularRobotNameExists==false){
			throw new Error ("Not supported modulal robot name: "+ radionButton.getText());
		}
		/*Informing user*/
		AssignBehaviorsTab.getHintPanel().setText(HintPanelInter.builInHintsAssignBehaviorTab[1]);
	}
	
	/**
	 * Extracts and loads the names of controllers existing in the package "ussr.builder.controllerAdjustmentTool" into jList
	 * @param jList1, the component in GUI.
	 */
	public static void loadExistingControllers(javax.swing.JList jList1){
		
		Class[] classes = null;
		try {
			classes = BuilderHelper.getClasses(packageName);
		} catch (ClassNotFoundException e) {
			throw new Error ("The package named as: "+ packageName + "was not found in the directory ussr.builder.controllerAdjustmentTool");			
		}		

		/*Loop through the classes and take only controllers, but not the classes defining the tool*/
		classesOfControllers = new Vector<String>();
		for (int i=0; i<classes.length;i++){
			if (classes[i].toString().contains("AssignControllerTool")||classes[i].toString().contains("ControllerStrategy")){
				//do nothing	
			}else{
				classesOfControllers.add(classes[i].toString().replace("class "+packageName+".", ""));
			}
		}			
		updateList(jList1,classesOfControllers);
		/*Update the list with newly loaded names of controllers*/
	}


	/**
	 * Updates the list with the names of controllers.
	 * @param jList1,the component in GUI. 
	 * @param controllers, vector of controllers.
	 */
	@SuppressWarnings("serial")
	public static void updateList(javax.swing.JList jList1,final Vector<String> controllers ){
		jList1.setModel(new javax.swing.AbstractListModel() {
			Object[] strings =  controllers.toArray();
			public int getSize() { return strings.length; }
			public Object getElementAt(int i) { return strings[i]; }
		});		
	}
	
	/**
	 * Filters out the names of controller for specific modular robot name.
	 * @param modularRobotsName, modular robot name.
	 * @return tempClassesOfControllers, array of controllers for specific modular robot name.
	 */
	public static Vector<String> filterOut(ModularRobotsNames modularRobotsName){
		tempClassesOfControllers.removeAllElements();
		for (int index=0; index<classesOfControllers.size();index++){
			if (classesOfControllers.get(index).contains(modularRobotsName.toString())){
				tempClassesOfControllers.add(classesOfControllers.get(index));
			}
		}
		return tempClassesOfControllers;
	}

	/**
	 * Initializes the tool for assigning controller chosen by user in GUI component. 
	 * @param jList1,the component in GUI. 
	 * @param jmeSimulation, the physical simulation.
	 */
	public static void jList1MouseReleased(javax.swing.JList jList1,JMESimulation jmeSimulation) {
		jmeSimulation.setPicker(new AssignControllerTool(packageName+"."+jList1.getSelectedValue()));
	}

	
	/**
	 * Shows or hides the panel for controlling labels.
	 * @param jCheckBox, checkBox in GUI selected or deselected by user.
	 */
	public static void jCheckBoxShowLabelControlActionPerformed(JCheckBox jCheckBox) {
		if (jCheckBox.isSelected()){
			AssignBehaviorsTab.getLabelingPanel().setVisible(true);
			/*Informing user*/
			AssignBehaviorsTab.getHintPanel().setText(HintPanelInter.builInHintsAssignBehaviorTab[2]);
		}else{		
			AssignBehaviorsTab.getLabelingPanel().setVisible(false);
		}
	}
	

	/**
	 * @param button, radio button selected in GUI.
	 * @param jmeSimulation, the physical simulation.
	 */
	public static void radioButtonGroupEntitiesActionPerformed(AbstractButton button, JMESimulation jmeSimulation) {

		AssignBehaviorsTab.setEnabledControlButtons(true);

		chosenRadioEntityText = button.getText();
		localJMEsimulation = jmeSimulation;

		/*Updates table header, according to selected entity */
		updateTableHeader();
		/*Initialize the tool for reading labels*/
		jButtonReadLabelsActionPerformed();

		/*Informing user*/
		AssignBehaviorsTab.getHintPanel().setText(HintPanelInter.builInHintsAssignBehaviorTab[3]);
	}
	
	/**
	 * Updates table header according to the entity name selected in button group.
	 */
	private static void updateTableHeader(){
		String columnHeaderName ="";
		if (chosenRadioEntityText.equals(EntitiesForLabelingText.Sensors.toString())){
			AssignBehaviorsTab.getJToolBar3().setVisible(true);
		}else if (chosenRadioEntityText.equals(EntitiesForLabelingText.Proximity.toString())){
			AssignBehaviorsTab.getJToolBar3().setVisible(true);
			//FIXME
			columnHeaderName = chosenRadioEntityText+ " Sensor" + " Labels"; 
			AssignBehaviorsTab.getJTable2().getTableHeader().getColumnModel().getColumn(0).setHeaderValue(columnHeaderName);
		}else{
			columnHeaderName = chosenRadioEntityText + " Labels"; 
			AssignBehaviorsTab.getJTable2().getTableHeader().getColumnModel().getColumn(0).setHeaderValue(columnHeaderName);
			AssignBehaviorsTab.getJToolBar3().setVisible(false);

		}
	} 

	/**
	 * Initializes the tool for reading labels.
	 */
	public static void jButtonReadLabelsActionPerformed() {
		EntitiesForLabelingText currentText = EntitiesForLabelingText.valueOf(chosenRadioEntityText);

		switch(currentText){

		case Module:
			try {
					MainFrameController.getBuilderControl().setPicker(BuilderSupportingUnicastPickers.READ_MODULE_LABELS);
				} catch (RemoteException e) {
	
				}
			break;
		case Connector:
			try {
					MainFrameController.getBuilderControl().setPicker(BuilderSupportingUnicastPickers.READ_CONNECTOR_LABELS);
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			break;
		case Sensors:
		case Proximity:// go to proximity because only this type of sensor is supported right now.
			try {
					MainFrameController.getBuilderControl().setPicker(BuilderSupportingUnicastPickers.READ_SENSOR_LABELS);
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			break;
		default: throw new Error("Labeling is not supported for " + chosenRadioEntityText ); 
		}
		/*Informing user*/
		AssignBehaviorsTab.getHintPanel().setText(HintPanelInter.builInHintsAssignBehaviorTab[3]);
	}
	
	




	/**
	 * Displays labels in the table of GUI. Is called from  LabelEntityTemplate.java.
	 * @param labels, the string of labels to display in GUI.
	 */
	public static void updateTableLabels(String labels){
		// clear the column
		int amountRows = AssignBehaviorsTab.getJTable2().getRowCount() ;
		for (int rowNr =0;rowNr < amountRows; rowNr++){
			AssignBehaviorsTab.getJTable2().setValueAt("", rowNr, 0);
		}

		//populate column with labels
		String[] sepratedLabels = labels.split(LabelingTemplate.LABEL_SEPARATOR);
		if(amountRows>sepratedLabels.length&&labels.length()>0) {
			for (int index=0; index<sepratedLabels.length; index++){
				AssignBehaviorsTab.getJTable2().setValueAt(sepratedLabels[index], index, 0);
			}
		}else if (labels.length() ==0){
			AssignBehaviorsTab.getJTable2().setValueAt("none label", 0, 0);
		}else{
			throw new Error ("Addition of new rows is not supported yet");
		}

	}

	public static void jButtonAssignLabelsActionPerformed() {

		/*Get labels from the table*/
		int amountRows = AssignBehaviorsTab.getJTable2().getRowCount() ;
		String labelsInTable="";
		for (int rowNr =0;rowNr < amountRows; rowNr++){
			if (AssignBehaviorsTab.getJTable2().getValueAt(rowNr, 0).toString().isEmpty()){
				//do not store empty rows
			}else{
				labelsInTable= labelsInTable + AssignBehaviorsTab.getJTable2().getValueAt(rowNr, 0)+",";
			}
		}
	
		/*Assign labels to entity*/
		EntitiesForLabelingText currentText = EntitiesForLabelingText.valueOf(chosenRadioEntityText);
/*
		switch(currentText){

		case Module:			
			localJMEsimulation.setPicker(new LabelingToolSpecification(localJMEsimulation,LabeledEntities.MODULE,labelsInTable,LabelingTools.LABEL_MODULE));
			break;
		case Connector:
			localJMEsimulation.setPicker(new LabelingToolSpecification(localJMEsimulation,LabeledEntities.CONNECTOR,labelsInTable,LabelingTools.LABEL_CONNECTOR));
			break;
		case Sensors:
		case Proximity:// go to proximity because only this type of sensor is supported right now.
			localJMEsimulation.setPicker(new LabelingToolSpecification(localJMEsimulation,LabeledEntities.SENSOR,labelsInTable,LabelingTools.LABEL_SENSOR));
			break;
		default: throw new Error("Labeling is not supported for " + chosenRadioEntityText ); 
		}*/
		/*Informing user*/
		AssignBehaviorsTab.getHintPanel().setText(HintPanelInter.builInHintsAssignBehaviorTab[4]);
	}

	/**
	 * Sets builder controller of remote simulation for this controller.
	 * @param builderController,builder controller of remote simulation.
	 */
/*	public static void setBuilderControl(BuilderControlInter builderController) {
		MainFrameController.builderControl = builderController;
	}*/
}
