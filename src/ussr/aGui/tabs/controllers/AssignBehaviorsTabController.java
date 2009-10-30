package ussr.aGui.tabs.controllers;

import java.util.Vector;

import javax.swing.AbstractButton;
import javax.swing.JRadioButton;
import javax.swing.JTable;

import ussr.aGui.tabs.additionalResources.HintPanelInter;
import ussr.aGui.tabs.views.constructionTabs.AssignBehaviorsTab;
import ussr.aGui.tabs.views.constructionTabs.AssignBehaviorsTabInter.TabJComponentsText;
import ussr.builder.SupportedModularRobots;
import ussr.builder.controllerAdjustmentTool.AssignControllerTool;
import ussr.builder.genericTools.Identifier;
import ussr.builder.genericTools.TypesIdentifier;
import ussr.builder.helpers.BuilderHelper;
import ussr.builder.labelingTools.LabeledEntities;
import ussr.builder.labelingTools.LabelingTemplate;
import ussr.builder.labelingTools.LabelingToolSpecification;
import ussr.builder.labelingTools.LabelingTools;
import ussr.physics.jme.JMESimulation;

public class AssignBehaviorsTabController {

	private static Object[] strings;

	private static  Vector<String> classesOfControllers ;	


	private static  Vector<String> tempClassesOfControllers =  new Vector<String> ()  ;

	//private static javax.swing.JList tempjList1;

	/**
	 * The name of the package where all behaviors are stored for interactive adjustment of controller.
	 */
	private static final String packageName = "ussr.builder.controllerAdjustmentTool";


	/**
	 * Loads the names of the controllers existing in the package "ussr.builder.controllerReassignmentTool" into List
	 * @param jList1
	 */
	public static void loadExistingControllers(javax.swing.JList jList1){
		//tempjList1 = jList1;
		Class[] classes = null;
		try {
			classes = BuilderHelper.getClasses(packageName);
		} catch (ClassNotFoundException e) {
			throw new Error ("The package named as: "+ packageName + "was not found in the directory of USSR");			
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

	public static Vector<String> getClassesOfControllers() {
		return classesOfControllers;
	}

	public static void updateList(javax.swing.JList jList1,final Vector<String> controllers ){
		jList1.setModel(new javax.swing.AbstractListModel() {
			Object[] strings =  controllers.toArray();
			public int getSize() { return strings.length; }
			public Object getElementAt(int i) { return strings[i]; }
		});		
	}

	public static void jList1MouseReleased(javax.swing.JList jList1,JMESimulation jmeSimulation) {
		AssignBehaviorsTab.getJLabel10005().setVisible(false);
		jmeSimulation.setPicker(new AssignControllerTool(packageName+"."+jList1.getSelectedValue()));
	}

	public static void jButtonGroupActionPerformed(javax.swing.AbstractButton radionButton,JMESimulation jmeSimulation){

		if (radionButton.getText().contains("ATRON")){		  
			updateList(AssignBehaviorsTab.getJList1(),filterOut("ATRON"));
		}else if (radionButton.getText().contains("Odin")){
			updateList(AssignBehaviorsTab.getJList1(),filterOut("Odin"));
		}else if (radionButton.getText().contains("MTran")){
			updateList(AssignBehaviorsTab.getJList1(),filterOut("MTRAN"));
		}else if (radionButton.getText().contains("CKBotStandard")){
			updateList(AssignBehaviorsTab.getJList1(),filterOut("CKBotStandard"));
		}
	}

	public static Vector<String> filterOut(String modularRobotName){
		tempClassesOfControllers.removeAllElements();
		for (int index=0; index<classesOfControllers.size();index++){
			if (classesOfControllers.get(index).contains(modularRobotName.toString())){
				tempClassesOfControllers.add(classesOfControllers.get(index));
			}
		}
		/*	  if (tempClassesOfControllers.isEmpty()){
		  tempClassesOfControllers.add(filterValue + "is not supported yet");
	  }*/
		return tempClassesOfControllers;

	}

	
	private  static Identifier currentIdentifier;
	
	public static void radioButtonGroupEntitiesActionPerformed(AbstractButton button, JMESimulation jmeSimulation) {
	
		String buttonText = button.getText();
		
		
		String columnHeaderName ="";
		if (buttonText.equals(TabJComponentsText.Sensors.toString())){
			AssignBehaviorsTab.getJToolBar3().setVisible(true);
		}else if (buttonText.equals(TabJComponentsText.Proximity.toString())){
			AssignBehaviorsTab.getJToolBar3().setVisible(true);
			//FIXME
			columnHeaderName = buttonText+ " Sensor" + " Labels"; 
			AssignBehaviorsTab.getJTable2().getTableHeader().getColumnModel().getColumn(0).setHeaderValue(columnHeaderName);
		}else{
			columnHeaderName = buttonText + " Labels"; 
			AssignBehaviorsTab.getJTable2().getTableHeader().getColumnModel().getColumn(0).setHeaderValue(columnHeaderName);
			AssignBehaviorsTab.getJToolBar3().setVisible(false);
			
		}
		
		
		TabJComponentsText currentText = TabJComponentsText.valueOf(buttonText);
		
	   switch(currentText){
	
	   case Module:
		   //TODO ELIMINATE null at the end if QPSS should be eliminated.
		   jmeSimulation.setPicker(new LabelingToolSpecification(jmeSimulation,LabeledEntities.MODULE,LabelingTools.READ_LABELS, null));
		   break;
	   case Connector:
		   jmeSimulation.setPicker(new LabelingToolSpecification(jmeSimulation,LabeledEntities.CONNECTOR,LabelingTools.READ_LABELS, null));
		   break;
	   case Sensors:
	   case Proximity:
		   jmeSimulation.setPicker(new LabelingToolSpecification(jmeSimulation,LabeledEntities.SENSOR,LabelingTools.READ_LABELS, null));
		   break;
	    default: throw new Error("Labeling is not supported for " + buttonText ); 
	}
		
		
		
		//currentIdentifier = new Identifier(TypesIdentifier.MODULE);
		//jmeSimulation.setPicker(currentIdentifier);
		
		
		
		AssignBehaviorsTab.getHintPanel().setText(HintPanelInter.builInHintsAssignBehaviorTab[1]);

	}
	
	
/*FIXME NOT USED*/
	public static void jTable1RowDoubleSelectedActionPerformed(JTable jTable1) {
		String rowValue = (String)jTable1.getValueAt(jTable1.getSelectedRow(),0);
        System.out.println("out"+ rowValue);
	}

	
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
		
		
		
		
		
		
		
		
		
		
		//AssignBehaviorsTab.getJTable2().setValueAt(aValue, row, column);
		//AssignBehaviorsTab.getJTable2().getRowCount();
		//AssignBehaviorsTab.getJTable2().getColumnClass(0)
	}


}
