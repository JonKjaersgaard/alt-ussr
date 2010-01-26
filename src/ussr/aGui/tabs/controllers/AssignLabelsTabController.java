package ussr.aGui.tabs.controllers;

import java.rmi.RemoteException;

import javax.swing.AbstractButton;

import ussr.aGui.designHelpers.hintPanel.HintPanelTypes;
import ussr.aGui.enumerations.hintpanel.HintsAssignLabelsTab;
import ussr.aGui.enumerations.tabs.EntitiesForLabelingText;
import ussr.aGui.tabs.constructionTabs.AssignLabelsTab;
import ussr.builder.enumerations.tools.LabeledEntities;
import ussr.builder.enumerations.tools.LabelingTools;
import ussr.builder.labelingTools.LabelingTemplate;

/**
 * Controls events from AssignLabelsTab (visual appearance).
 * @author Konstantinas
 *
 */
public class AssignLabelsTabController extends TabsControllers{

	/**
	 * Current entity for labeling;
	 */
	private static String chosenRadioEntityText;
	
	/**
	 * @param button, radio button selected in GUI.
	 */
	public static void radioButtonGroupEntitiesActionPerformed(AbstractButton button) {

		AssignLabelsTab.setEnabledControlButtons(true);

		chosenRadioEntityText = button.getText();

		/*Updates table header, according to selected entity */
		updateTableHeader();
		/*Initialize the tool for reading labels*/
		jButtonReadLabelsActionPerformed();
	}
	
	/**
	 * Updates table header according to the entity name selected in the button group.
	 */
	private static void updateTableHeader(){
		String columnHeaderName ="";
		if (chosenRadioEntityText.equals(EntitiesForLabelingText.Sensors.toString())){
			AssignLabelsTab.getJToolBarTypesSensors().setVisible(true);
		}else if (chosenRadioEntityText.equals(EntitiesForLabelingText.Proximity.toString())){
			AssignLabelsTab.getJToolBarTypesSensors().setVisible(true);
			columnHeaderName = chosenRadioEntityText+ " Sensor" + " Labels"; 
			AssignLabelsTab.getJTableLabels().getTableHeader().getColumnModel().getColumn(0).setHeaderValue(columnHeaderName);
		}else{
			columnHeaderName = chosenRadioEntityText + " Labels"; 
			AssignLabelsTab.getJTableLabels().getTableHeader().getColumnModel().getColumn(0).setHeaderValue(columnHeaderName);
			AssignLabelsTab.getJToolBarTypesSensors().setVisible(false);

		}
	} 
	
	
	/**
	 * Displays labels in the table of GUI.
	 * @param labels, the string of labels to display in GUI.
	 */
	public static void updateTableLabels(String labels){
		// clear the column
		int amountRows = AssignLabelsTab.getJTableLabels().getRowCount() ;
		for (int rowNr =0;rowNr < amountRows; rowNr++){
			AssignLabelsTab.getJTableLabels().setValueAt("", rowNr, 0);
		}

		//populate column with labels
		String[] sepratedLabels = labels.split(LabelingTemplate.LABEL_SEPARATOR);
		if(amountRows>sepratedLabels.length&&labels.length()>0) {
			for (int index=0; index<sepratedLabels.length; index++){
				AssignLabelsTab.getJTableLabels().setValueAt(sepratedLabels[index], index, 0);
			}
		}else if (labels.length() ==0){
			//TODO
			AssignLabelsTab.getJTableLabels().setValueAt("none label", 0, 0);
		}else{
			throw new Error ("Addition of new rows is not supported yet");
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
		AssignLabelsTab.getHintPanel().setType(HintPanelTypes.INFORMATION);
		AssignLabelsTab.getHintPanel().setText(HintsAssignLabelsTab.ENTITY_CHOSEN.getHintText());
		
	}
	
	
	/**
	 * Initializes the tools for labeling different entities in simulation environment.
	 */
	public static void jButtonAssignLabelsActionPerformed() {

		/*Get labels from the table*/
		int amountRows = AssignLabelsTab.getJTableLabels().getRowCount() ;
		String labelsInTable="";
		for (int rowNr =0;rowNr < amountRows; rowNr++){
			if (AssignLabelsTab.getJTableLabels().getValueAt(rowNr, 0)==null){
				//do not store empty rows
			}else{
				labelsInTable= labelsInTable + AssignLabelsTab.getJTableLabels().getValueAt(rowNr, 0)+",";
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
		AssignLabelsTab.getHintPanel().setType(HintPanelTypes.INFORMATION);
		AssignLabelsTab.getHintPanel().setText(HintsAssignLabelsTab.ASSIGN_LABELS.getHintText());
	}

}
