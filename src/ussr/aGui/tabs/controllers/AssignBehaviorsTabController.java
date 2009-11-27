package ussr.aGui.tabs.controllers;

import java.rmi.RemoteException;
import java.util.Vector;

import javax.swing.AbstractButton;
import javax.swing.JCheckBox;


import ussr.aGui.tabs.additionalResources.HintPanel;
import ussr.aGui.tabs.additionalResources.HintPanelInter;
import ussr.aGui.tabs.additionalResources.HintPanelTypes;
import ussr.aGui.tabs.constructionTabs.AssignBehaviorsTab;
import ussr.aGui.tabs.constructionTabs.AssignBehaviorsTabInter;

import ussr.builder.enumerations.LabeledEntities;
import ussr.builder.enumerations.LabelingTools;
import ussr.builder.enumerations.SupportedModularRobots;
import ussr.builder.helpers.BuilderHelper;
import ussr.builder.helpers.FileDirectoryHelper;
import ussr.builder.labelingTools.LabelingTemplate;

public class AssignBehaviorsTabController extends TabsControllers implements AssignBehaviorsTabInter {

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
	 * The name of the package where all behaviors are stored for interactive adjustment of controller.
	 */
	private static final String packageName = "ussr.builder.controllerAdjustmentTool";


	/**
	 * Loads all existing names of controllers from package ussr.builder.controllerAdjustmentTool and filters
	 * out the ones for selected button (modular robot name).
	 * @param radionButton, the radio button representing modular robot name.
	 */
	public static void jButtonGroupActionPerformed(javax.swing.AbstractButton radionButton){
		loadExistingControllers(AssignBehaviorsTab.getJListAvailableControllers());

		ModularRobotsNames[] modularRobotsNames = ModularRobotsNames.values();
		boolean modularRobotNameExists = false;
		for (int buttonTextItem=0;buttonTextItem<modularRobotsNames.length;buttonTextItem++){
			if (radionButton.getText().equals(modularRobotsNames[buttonTextItem].toString())){
				updateList(AssignBehaviorsTab.getJListAvailableControllers(),filterOut(modularRobotsNames[buttonTextItem]));
				modularRobotNameExists =true;
			}
		}

		if (modularRobotNameExists==false){
			throw new Error ("Not supported modulal robot name: "+ radionButton.getText());
		}
		/*Informing user*/
		AssignBehaviorsTab.getHintPanel().setType(HintPanelTypes.INFORMATION);
		AssignBehaviorsTab.getHintPanel().setText(HintPanelInter.builInHintsAssignBehaviorTab[1]);
	}

	/**
	 * Extracts and loads the names of controllers existing in the package "ussr.builder.controllerAdjustmentTool" into jList
	 * @param jList1, the component in GUI.
	 */
	public static void loadExistingControllers(javax.swing.JList jList1){

		Class[] classes = null;
		try {
			classes = FileDirectoryHelper.getClasses(packageName);
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
	 */
	public static void jListAvailableControllersMouseReleased(javax.swing.JList jList1) {
		try {
			builderControl.setAdjustControllerPicker(packageName+"."+jList1.getSelectedValue());			
		} catch (RemoteException e) {
			throw new Error("Failed to initate picker called "+ "AssignControllerTool" + ", due to remote exception");
		}		
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
	 */
	public static void radioButtonGroupEntitiesActionPerformed(AbstractButton button) {

		AssignBehaviorsTab.setEnabledControlButtons(true);

		chosenRadioEntityText = button.getText();

		/*Updates table header, according to selected entity */
		updateTableHeader();
		/*Initialize the tool for reading labels*/
		jButtonReadLabelsActionPerformed();

		/*Informing user*/
		AssignBehaviorsTab.getHintPanel().setText(HintPanelInter.builInHintsAssignBehaviorTab[3]);
	}

	/**
	 * Updates table header according to the entity name selected in the button group.
	 */
	private static void updateTableHeader(){
		String columnHeaderName ="";
		if (chosenRadioEntityText.equals(EntitiesForLabelingText.Sensors.toString())){
			AssignBehaviorsTab.getJToolBarTypesSensors().setVisible(true);
		}else if (chosenRadioEntityText.equals(EntitiesForLabelingText.Proximity.toString())){
			AssignBehaviorsTab.getJToolBarTypesSensors().setVisible(true);
			columnHeaderName = chosenRadioEntityText+ " Sensor" + " Labels"; 
			AssignBehaviorsTab.getJTableLabels().getTableHeader().getColumnModel().getColumn(0).setHeaderValue(columnHeaderName);
		}else{
			columnHeaderName = chosenRadioEntityText + " Labels"; 
			AssignBehaviorsTab.getJTableLabels().getTableHeader().getColumnModel().getColumn(0).setHeaderValue(columnHeaderName);
			AssignBehaviorsTab.getJToolBarTypesSensors().setVisible(false);

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
				builderControl.setLabelingToolReadLabels(LabeledEntities.MODULE, LabelingTools.READ_LABELS);					
			} catch (RemoteException e) {
				throw new Error("Failed to initate picker called "+ LabelingTools.READ_LABELS.toString() + " of "+LabeledEntities.MODULE.toString()  + ", due to remote exception");
			}
			break;
		case Connector:
			try {
				builderControl.setLabelingToolReadLabels(LabeledEntities.CONNECTOR, LabelingTools.READ_LABELS);
			} catch (RemoteException e) {
				throw new Error("Failed to initate picker called "+ LabelingTools.READ_LABELS.toString() + " of "+LabeledEntities.CONNECTOR.toString()  + ", due to remote exception");
			}
			break;
		case Sensors:
		case Proximity:// go to proximity because only this type of sensor is supported right now.
			try {
				builderControl.setLabelingToolReadLabels(LabeledEntities.SENSOR, LabelingTools.READ_LABELS);
			} catch (RemoteException e) {
				throw new Error("Failed to initate picker called "+ LabelingTools.READ_LABELS.toString() + " of "+LabeledEntities.SENSOR.toString()  + ", due to remote exception");
			}
			break;
		default: throw new Error("Labeling is not supported for " + chosenRadioEntityText ); 
		}
		/*Informing user*/
		AssignBehaviorsTab.getHintPanel().setText(HintPanelInter.builInHintsAssignBehaviorTab[3]);
	}


	/**
	 * Displays labels in the table of GUI.
	 * @param labels, the string of labels to display in GUI.
	 */
	public static void updateTableLabels(String labels){
		// clear the column
		int amountRows = AssignBehaviorsTab.getJTableLabels().getRowCount() ;
		for (int rowNr =0;rowNr < amountRows; rowNr++){
			AssignBehaviorsTab.getJTableLabels().setValueAt("", rowNr, 0);
		}

		//populate column with labels
		String[] sepratedLabels = labels.split(LabelingTemplate.LABEL_SEPARATOR);
		if(amountRows>sepratedLabels.length&&labels.length()>0) {
			for (int index=0; index<sepratedLabels.length; index++){
				AssignBehaviorsTab.getJTableLabels().setValueAt(sepratedLabels[index], index, 0);
			}
		}else if (labels.length() ==0){
			//TODO
			AssignBehaviorsTab.getJTableLabels().setValueAt("none label", 0, 0);
		}else{
			throw new Error ("Addition of new rows is not supported yet");
		}

	}

	/**
	 * Initializes the tools for labeling different entities in simulation environment.
	 */
	public static void jButtonAssignLabelsActionPerformed() {

		/*Get labels from the table*/
		int amountRows = AssignBehaviorsTab.getJTableLabels().getRowCount() ;
		String labelsInTable="";
		for (int rowNr =0;rowNr < amountRows; rowNr++){
			if (AssignBehaviorsTab.getJTableLabels().getValueAt(rowNr, 0).toString().isEmpty()){
				//do not store empty rows
			}else{
				labelsInTable= labelsInTable + AssignBehaviorsTab.getJTableLabels().getValueAt(rowNr, 0)+",";
			}
		}

		/*Assign labels to entity*/
		EntitiesForLabelingText currentText = EntitiesForLabelingText.valueOf(chosenRadioEntityText);

		try {
			switch(currentText){		
			case Module:		
				builderControl.setLabelingToolAssignLabels(LabeledEntities.MODULE,LabelingTools.LABEL_MODULE,labelsInTable);			
				break;
			case Connector:			
				builderControl.setLabelingToolAssignLabels(LabeledEntities.CONNECTOR,LabelingTools.LABEL_CONNECTOR,labelsInTable);			
				break;
			case Sensors:
			case Proximity:// go to proximity because only this type of sensor is supported right now.			
				builderControl.setLabelingToolAssignLabels(LabeledEntities.SENSOR,LabelingTools.LABEL_SENSOR,labelsInTable);			
				break;
			default: throw new Error("Labeling is not supported for " + chosenRadioEntityText ); 
			}

		} catch (RemoteException e) {
			throw new Error("Failed to initialize the tools for labeling different entities in simulation environment");
		}
		/*Informing user*/
		AssignBehaviorsTab.getHintPanel().setText(HintPanelInter.builInHintsAssignBehaviorTab[4]);
	}

	/**
	 * Adapts Assign Behaviors Tab to the the type of first module in simulation environment.
	 * TODO MAKE IT MORE GENERIC BY MEANS OF IDENTIFYING THE LAST TYPE OF MODULE IN XML FILE
	 * OR SOMETHING SIMILLAR.
	 */
	public static void adaptTabToModuleInSimulation(){
		int amountModules =0;		
		try {
			amountModules =  builderControl.getIDsModules().size();
		} catch (RemoteException e) {
			throw new Error("Failed to identify amount of modules in simulation environment, due to remote exception");
		}

		if (amountModules>0){
			/*Adapt to first module*/
			String modularRobotName ="";
			try {
				modularRobotName = builderControl.getModuleType(0);
			} catch (RemoteException e) {
				throw new Error ("Failed to identify the type of the first module in simulation environment, due to remote exception.");
			}
			
			if (modularRobotName.toUpperCase().contains(SupportedModularRobots.ATRON.toString())){
				jButtonGroupActionPerformed(AssignBehaviorsTab.getRadionButtonATRON());
				AssignBehaviorsTab.getRadionButtonATRON().setSelected(true);
			} else if (modularRobotName.toUpperCase().contains(SupportedModularRobots.ODIN.toString())){
				jButtonGroupActionPerformed(AssignBehaviorsTab.getRadioButtonODIN());
				AssignBehaviorsTab.getRadioButtonODIN().setSelected(true);
			} else if (modularRobotName.toUpperCase().contains(SupportedModularRobots.MTRAN.toString())){
				jButtonGroupActionPerformed(AssignBehaviorsTab.getRadioButtonMTRAN());
				AssignBehaviorsTab.getRadioButtonMTRAN().setSelected(true);
			}else if(modularRobotName.toUpperCase().contains(SupportedModularRobots.CKBOTSTANDARD.toString())){
				jButtonGroupActionPerformed(AssignBehaviorsTab.getRadionButtonCKBOTSTANDARD());
				AssignBehaviorsTab.getRadionButtonCKBOTSTANDARD().setSelected(true);
			}		
		}
	}
	
	
	public static void updateHintPanel(HintPanelTypes hintPanelTypes,String text){
		AssignBehaviorsTab.getHintPanel().setType(hintPanelTypes);
		AssignBehaviorsTab.getHintPanel().setText(text);
	}
	
	
}
