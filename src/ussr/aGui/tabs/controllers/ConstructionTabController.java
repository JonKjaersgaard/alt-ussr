package ussr.aGui.tabs.controllers;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;

import ussr.aGui.MainFrame;
import ussr.aGui.tabs.TabsInter;
import ussr.builder.SupportedModularRobots;
import ussr.builder.constructionTools.ATRONOperationsTemplate;
import ussr.builder.constructionTools.CKBotOperationsTemplate;
import ussr.builder.constructionTools.CommonOperationsTemplate;
import ussr.builder.constructionTools.ConstructionToolSpecification;
import ussr.builder.constructionTools.ConstructionTools;
import ussr.builder.constructionTools.MTRANOperationsTemplate;
import ussr.builder.constructionTools.OdinOperationsTemplate;
import ussr.builder.genericTools.ColorConnectors;
import ussr.builder.genericTools.RemoveModule;
import ussr.builder.helpers.BuilderHelper;
import ussr.description.geometry.VectorDescription;
import ussr.model.Module;
import ussr.physics.jme.JMESimulation;
import ussr.physics.jme.pickers.PhysicsPicker;
import ussr.samples.odin.modules.Odin;
import ussr.aGui.tabs.views.constructionTab.ConstructionTab;


public class ConstructionTabController {


	private static String chosenItem ="Module" ;

	/**
	 * Default positions of default construction modules for each modular robot
	 */
	private final static VectorDescription atronDefaultPosition = new VectorDescription(0,-0.441f,0.5f),
	mtranDefaultPosition = new VectorDescription(-1f,-0.4621f,0.5f),	
	odinDefaultPosition = new VectorDescription(1f,-0.4646f,0.5f),	
	ckbotDefaultPosition = new VectorDescription(2f,-0.4646f,0.5f); 

	/**
	 *  The name of the current modular robot
	 */
	private static SupportedModularRobots chosenMRname = SupportedModularRobots.ATRON; //MR- modular robot. Default is ATRON, just do not have the case when it is empty String


	public static void jButtonGroupActionPerformed(AbstractButton button,JMESimulation jmeSimulation ) {

		/*Disable and enable available components*/
		ConstructionTab.setRadioButtonsEnabled(false);
		ConstructionTab.getJComboBox1().setEnabled(true);
		ConstructionTab.setEnabledRotationToolBar(true);
		ConstructionTab.setEnabledGenericToolBar(true);
		ConstructionTab.setEnabledConstructionToolsToolBar(true);

		String chosenModularRobot = button.getText();			
		chosenMRname = SupportedModularRobots.valueOf(chosenModularRobot.toUpperCase());

		/*Adapt tab to chosen modular robot*/
		if (chosenMRname.equals(SupportedModularRobots.ATRON)){

			ConstructionTab.getjComboBoxStandardRotations().setModel(new javax.swing.DefaultComboBoxModel(TabsInter.ATRONStandardRotations.values()));
			ConstructionTab.getJComboBoxNrConnectorsConstructionTool().setModel(new javax.swing.DefaultComboBoxModel(TabsInter.ATRON_CONNECTORS));

		}else if (chosenMRname.equals(SupportedModularRobots.MTRAN)){

			ConstructionTab.getjComboBoxStandardRotations().setModel(new javax.swing.DefaultComboBoxModel(TabsInter.MTRANStandardRotations.values()));
			ConstructionTab.getJComboBoxNrConnectorsConstructionTool().setModel(new javax.swing.DefaultComboBoxModel(TabsInter.MTRAN_CONNECTORS));

		}else if (chosenMRname.equals(SupportedModularRobots.ODIN)){
			ConstructionTab.getjComboBoxStandardRotations().setEnabled(false);// for Odin not yet relevant
			ConstructionTab.getJComboBoxNrConnectorsConstructionTool().setModel(new javax.swing.DefaultComboBoxModel(TabsInter.ODIN_CONNECTORS));

			
		}else if (chosenMRname.equals(SupportedModularRobots.CKBOTSTANDARD)){
			ConstructionTab.getjComboBoxStandardRotations().setModel(new javax.swing.DefaultComboBoxModel( TabsInter.CKBotStandardRotations.values() ));
			ConstructionTab.getJComboBoxNrConnectorsConstructionTool().setModel(new javax.swing.DefaultComboBoxModel(TabsInter.CKBOT_CONNECTORS));
		}

		//nextjButton.setEnabled(false);
		//previousjButton.setEnabled(false);
		addNewDefaultConstructionModule(jmeSimulation); //Add default construction module
		// Set default construction tool to be "On selected  connector"
		jmeSimulation.setPicker(new ConstructionToolSpecification(jmeSimulation, chosenMRname,ConstructionTools.ON_SELECTED_CONNECTOR));		

	}





	public static void addNewDefaultConstructionModule(JMESimulation jmeSimulation){
		//System.out.println("Modular robot specific--> New Module");//for debugging     	
		CommonOperationsTemplate comATRON = new ATRONOperationsTemplate(jmeSimulation);
		CommonOperationsTemplate comMTRAN = new MTRANOperationsTemplate(jmeSimulation);
		CommonOperationsTemplate comOdin = new OdinOperationsTemplate(jmeSimulation);
		CommonOperationsTemplate comCKBot = new CKBotOperationsTemplate(jmeSimulation);

		VectorDescription zeroPosition = new VectorDescription(0,0,0);		

		if (chosenMRname.equals(SupportedModularRobots.ATRON)&& moduleExists(atronDefaultPosition,jmeSimulation)==false ){
			comATRON.addDefaultConstructionModule(/*this.chosenMRname.toString()*/"default", atronDefaultPosition);
		}else if (chosenMRname.equals(SupportedModularRobots.MTRAN)&& moduleExists(mtranDefaultPosition,jmeSimulation)==false){
			comMTRAN.addDefaultConstructionModule(chosenMRname.toString(),mtranDefaultPosition );
		}else if (chosenMRname.equals(SupportedModularRobots.ODIN)&& moduleExists(odinDefaultPosition,jmeSimulation)==false){
			Odin.setDefaultConnectorSize(0.006f);// make connector bigger in order to select them sucessfuly with "on Connector tool"
			comOdin.addDefaultConstructionModule(chosenMRname.toString(), odinDefaultPosition);
		}else if (chosenMRname.equals(SupportedModularRobots.CKBOTSTANDARD)&& moduleExists(ckbotDefaultPosition,jmeSimulation)==false){			
			comCKBot.addDefaultConstructionModule(chosenMRname.toString(), ckbotDefaultPosition);
		}else {
			//com.addDefaultConstructionModule(this.chosenMRname, zeroPosition);
			//com1.addDefaultConstructionModule(this.chosenMRname,mtranPosition );
			//com2.addDefaultConstructionModule(this.chosenMRname,mtranPosition );
		}

	}


	/**
	 * Checks if module already exists at current position.
	 * @param currentPosition, the position of the module to check.     
	 *@return true, if module exists at current position.
	 */	
	public static boolean moduleExists(VectorDescription currentPosition,JMESimulation jmeSimulation){
		int amountModules = jmeSimulation.getModules().size();
		for (int module =0;module<amountModules;module++){
			Module currentModule =jmeSimulation.getModules().get(module); 
			String moduleType = currentModule.getProperty(BuilderHelper.getModuleTypeKey());
			VectorDescription modulePosition;
			if (moduleType.equalsIgnoreCase("MTRAN")){
				modulePosition = currentModule.getPhysics().get(1).getPosition(); 
			}else{
				modulePosition = currentModule.getPhysics().get(0).getPosition();
			}
			if (modulePosition.equals(currentPosition)){
				return true;
			}
		}
		return false;    	
	}





	/**
	 *  Initializes the tool for variating the properties of modules selected in simulation environment. 
	 * @param jmeSimulation
	 */
	public static void jButton6ActionPerformed(JMESimulation jmeSimulation) {		
		jmeSimulation.setPicker(new ConstructionToolSpecification(jmeSimulation,chosenMRname,ConstructionTools.VARIATION));
	}


	/**
	 * Initializes the tool for placing the modules on connectors selected with left side of the mouse in simulation environment. 
	 * @param evt, selection with left side of the mouse event (jButton selection).     
	 */	
	public static void jButton7ActionPerformed(JMESimulation jmeSimulation) {		
		jmeSimulation.setPicker(new ConstructionToolSpecification(jmeSimulation, chosenMRname,ConstructionTools.ON_SELECTED_CONNECTOR));
	}

	public static void jButton10ActionPerformed(JMESimulation jmeSimulation) {
		if (chosenItem.equalsIgnoreCase("Module")){
			jmeSimulation.setPicker(new RemoveModule());
		}else if (chosenItem.equalsIgnoreCase("Robot")){
			//TODO
		}

	}

	public static void jButton11ActionPerformed(JMESimulation jmeSimulation) {	
		if (chosenItem.equalsIgnoreCase("Module")){
			jmeSimulation.setPicker(new PhysicsPicker(true, true));
		}else if (chosenItem.equalsIgnoreCase("Robot")){
			//TODO
		}
	}

	public static void jButton12ActionPerformed(JMESimulation jmeSimulation) {	
		if (chosenItem.equalsIgnoreCase("Module")){
			jmeSimulation.setPicker(new ColorConnectors());	
		}else if (chosenItem.equalsIgnoreCase("Robot")){
			//TODO
		}

	}





	public static void jComboBox1ActionPerformed(JComboBox jComboBox1,JMESimulation jmeSimulation) {
		chosenItem = jComboBox1.getSelectedItem().toString();
		if (chosenItem.equalsIgnoreCase("Module") ){
			ConstructionTab.setEnabledGenericToolBar(true);
		}else if(chosenItem.equalsIgnoreCase("Robot")){
			//TODO Support it
			ConstructionTab.setEnabledGenericToolBar(false);
		}		
	}




	/**
	 * Initializes the tool for rotating modules selected in simulation environment with opposite rotation. 
	 * @param evt, selection with left side of the mouse event (jButton selection).     
	 */	
	public static void jButton15ActionPerformed(JMESimulation jmeSimulation) {
		jmeSimulation.setPicker(new ConstructionToolSpecification(jmeSimulation, chosenMRname,ConstructionTools.OPPOSITE_ROTATION));        
	}







	/**
	 * Initializes the tool for rotating default modules selected in simulation environment with standard rotations. 
	 *  
	 */	
	public static void jComboBox2ActionPerformed(JMESimulation jmeSimulation) {
		jmeSimulation.setPicker(new ConstructionToolSpecification(jmeSimulation, chosenMRname,ConstructionTools.STANDARD_ROTATIONS,ConstructionTab.getjComboBoxStandardRotations().getSelectedItem().toString()));		
	}




	/**
	 * Initializes the tool for placing the modules on connectors selected with left side of the mouse in simulation environment. 
	 *      
	 */	
	public static void jButtonOnSelectedConnectorActionPerformed(JMESimulation jmeSimulation) {
		jmeSimulation.setPicker(new ConstructionToolSpecification(jmeSimulation, chosenMRname,ConstructionTools.ON_SELECTED_CONNECTOR));
	}





	public static void jComboBoxNrConnectorsConstructionToolActionPerformed(JComboBox comboBoxNrConnectorsConstructionTool,JMESimulation jmeSimulation) {
		int chosenConnectorNr = Integer.parseInt(comboBoxNrConnectorsConstructionTool.getSelectedItem().toString());
	
	jmeSimulation.setPicker(new ConstructionToolSpecification(jmeSimulation, chosenMRname,ConstructionTools.ON_CHOSEN_CONNECTOR,chosenConnectorNr));
		
	}





	

}
