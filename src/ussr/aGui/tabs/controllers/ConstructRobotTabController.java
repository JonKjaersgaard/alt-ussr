package ussr.aGui.tabs.controllers;

import javax.swing.AbstractButton;
import javax.swing.JComboBox;

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
import ussr.aGui.tabs.additionalResources.HintPanelInter;
import ussr.aGui.tabs.views.constructionTabs.ConstructRobotTab;
import ussr.aGui.tabs.views.constructionTabs.ConstructRobotTabInter;


/**
 * Controls 
 * @author Konstantinas
 */
public class ConstructRobotTabController implements ConstructRobotTabInter{

	/**
	 * Local reference to physical simulation.
	 */
	private static JMESimulation jmeSimulationLocal;

	/**
	 * Default positions of initial construction modules for each modular robot
	 * TODO CHANGE TO ADDING INITIAL MODULES INTO THE POINT OF VIEW OR ROTATE CAMERA POSITION.
	 */
	private final static VectorDescription atronDefaultPosition = new VectorDescription(0,-0.441f,0.5f),
	mtranDefaultPosition = new VectorDescription(-1f,-0.4621f,0.5f),	
	odinDefaultPosition = new VectorDescription(1f,-0.4646f,0.5f),	
	ckbotDefaultPosition = new VectorDescription(2f,-0.4646f,0.5f); 

	/**
	 *  The name of  default(chosen) modular robot.
	 *  Just do not have the case when it is empty.
	 */
	private static SupportedModularRobots chosenMRname = SupportedModularRobots.ATRON; 


	/**
	 * Adds initial construction module according to selected module type and adapts the Tab to modular robot type.
	 * @param button, button selected in the group of radio button.
	 * @param jmeSimulation, the physical simulation.
	 */
	public static void jButtonGroupActionPerformed(AbstractButton button,JMESimulation jmeSimulation ) {
		jmeSimulationLocal = jmeSimulation;

		String chosenModularRobot = button.getText();			
		chosenMRname = SupportedModularRobots.valueOf(chosenModularRobot.toUpperCase());

		/*Adapt Tab components to chosen modular robot */
		adaptTabToChosenMR(chosenMRname);

		/*Add initial construction module*/
		addNewDefaultConstructionModule(jmeSimulation); 

		/* Set default construction tool to be "On selected  connector"*/
		jmeSimulation.setPicker(new ConstructionToolSpecification(jmeSimulation, chosenMRname,ConstructionTools.ON_SELECTED_CONNECTOR));

		/*Informing user*/
		ConstructRobotTab.getHintPanel().setText(HintPanelInter.builInHintsConstrucRobotTab[1]);

	}


	/**
	 * Adapt the tab to chosen modular robot.
	 * @param chosenMRname, modular robot name chosen in button group.
	 */
	private static void adaptTabToChosenMR(SupportedModularRobots chosenMRname){

		/*Disable and enable components*/
		ConstructRobotTab.setRadioButtonsEnabled(false);
		ConstructRobotTab.getJComboBoxEntity().setEnabled(true);
		ConstructRobotTab.setEnabledRotationToolBar(true);
		ConstructRobotTab.setEnabledGenericToolBar(true);

		ConstructRobotTab.setEnabledConstructionToolsToolBar(true);
		ConstructRobotTab.setEnabledButtonsArrows(false);

		/*Adapt tab to chosen modular robot*/
		if (chosenMRname.equals(SupportedModularRobots.ATRON)){
			adaptTabToATRON();
		}else if (chosenMRname.equals(SupportedModularRobots.MTRAN)){
			adaptTabToMTRAN();
		}else if (chosenMRname.equals(SupportedModularRobots.ODIN)){
			adaptTabToOdin();
		}else if (chosenMRname.equals(SupportedModularRobots.CKBOTSTANDARD)){
			adaptTabToCKBOTSTANDARD();			
		}
	}

	/**
	 * Adapts tab to ATRON modular robot
	 */
	private static void adaptTabToATRON(){
		ConstructRobotTab.getRadionButtonATRON().setSelected(true);
		ConstructRobotTab.getjComboBoxStandardRotations().setModel(new javax.swing.DefaultComboBoxModel(ATRONStandardRotations.values()));
		ConstructRobotTab.getJComboBoxNrConnectorsConstructionTool().setModel(new javax.swing.DefaultComboBoxModel(ATRON_CONNECTORS));
	}

	/**
	 * Adapts tab to MTRAN modular robot
	 */
	private static void adaptTabToMTRAN(){
		ConstructRobotTab.getRadioButtonMTRAN().setSelected(true);
		ConstructRobotTab.getJButtonMove().setEnabled(false);
		ConstructRobotTab.getjComboBoxStandardRotations().setModel(new javax.swing.DefaultComboBoxModel(MTRANStandardRotations.values()));
		ConstructRobotTab.getJComboBoxNrConnectorsConstructionTool().setModel(new javax.swing.DefaultComboBoxModel(MTRAN_CONNECTORS));
	}

	/**
	 * Adapts tab to Odin modular robot
	 */
	private static void adaptTabToOdin(){
		ConstructRobotTab.getRadionButtonODIN().setSelected(true);
		ConstructRobotTab.setEnabledRotationToolBar(false);// for Odin not yet relevant
		ConstructRobotTab.getJComboBoxNrConnectorsConstructionTool().setModel(new javax.swing.DefaultComboBoxModel(ODIN_CONNECTORS));
	}

	/**
	 * Adapts tab to CKBOTSTANDARD modular robot
	 */
	private static void adaptTabToCKBOTSTANDARD(){
		ConstructRobotTab.getRadionButtonCKBOTSTANDARD().setSelected(true);
		ConstructRobotTab.getjComboBoxStandardRotations().setModel(new javax.swing.DefaultComboBoxModel( CKBotStandardRotations.values() ));
		ConstructRobotTab.getJComboBoxNrConnectorsConstructionTool().setModel(new javax.swing.DefaultComboBoxModel(CKBOT_CONNECTORS));
	}


	/**
	 * Adds initial construction module according to chosen modular robot.
	 * TODO SHOULD CHANGE WHEN MODULE WILL BE ADDED TO VIEW POINT OR ROTATE CAMERA POSITION. 
	 * @param jmeSimulation, the physical simulation.
	 */
	private static void addNewDefaultConstructionModule(JMESimulation jmeSimulation){
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
	 * TODO SHOULD CHANGE WHEN MODULE WILL BE ADDED TO VIEW POINT OR ROTATE CAMERA POSITION. 
	 * @param currentPosition, the position of the module to check.
	 * @param jmeSimulation, the physical simulation.    
	 *@return true, if module exists at current position.
	 */	
	private static boolean moduleExists(VectorDescription currentPosition,JMESimulation jmeSimulation){
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
	 * Initializes the tool for variating the properties of modules selected in simulation environment.
	 * TODO DECIDE WHERE TO ADD IT IN THE TAB. 
	 * @param jmeSimulation
	 */
	public static void jButton6ActionPerformed(JMESimulation jmeSimulation) {		
		jmeSimulation.setPicker(new ConstructionToolSpecification(jmeSimulation,chosenMRname,ConstructionTools.VARIATION));
	}

	/**
	 * Default chosen entity for operations on existing modules or robot. 
	 */
	private static String chosenItem ="Module" ;

	/**
	 * Initializes the tool for deleting module or robot with the mouse selecting.
	 * @param jmeSimulation, the physical simulation.
	 */
	public static void jButtonDeleteActionPerformed(JMESimulation jmeSimulation) {
		if (chosenItem.equalsIgnoreCase("Module")){
			jmeSimulation.setPicker(new RemoveModule());
		}else if (chosenItem.equalsIgnoreCase("Robot")){
			//TODO SUPPORT ROBOT DELETION
		}
		/*Informing user*/
		ConstructRobotTab.getHintPanel().setText(HintPanelInter.builInHintsConstrucRobotTab[5]); 
	}

	/**
	 * Initializes the tool for moving module or robot with the mouse.
	 * @param jmeSimulation, the physical simulation
	 */
	public static void jButtonMoveActionPerformed(JMESimulation jmeSimulation) {	
		if (chosenItem.equalsIgnoreCase("Module")){
			jmeSimulation.setPicker(new PhysicsPicker(true, true));
		}else if (chosenItem.equalsIgnoreCase("Robot")){
			//TODO Support robot deletion, moving and coloring of connectors.
		}
		/*Informing user*/
		ConstructRobotTab.getHintPanel().setText(HintPanelInter.builInHintsConstrucRobotTab[3]);//Informing user
	}

	/**
	 * Initializes the tool for coloring  the connectors of the module or robot with color coding. 
	 * @param jmeSimulation, the physical simulation.
	 */
	public static void jButtonColorConnectorsActionPerformed(JMESimulation jmeSimulation) {	
		if (chosenItem.equalsIgnoreCase("Module")){
			jmeSimulation.setPicker(new ColorConnectors());	
		}else if (chosenItem.equalsIgnoreCase("Robot")){
			//TODO  Support robot deletion, moving and coloring of connectors.
		}
		/*Informing user*/
		ConstructRobotTab.getHintPanel().setText(HintPanelInter.builInHintsConstrucRobotTab[6]);//Informing user

	}

	/**
	 * Enables or disables the components in generic toolbar because of missing implementation for robot.
	 * TODO WILL CHANGE WITH ADDDING ROBOT SUPPORT.
	 * @param jComboBoxEntity
	 * @param jmeSimulation
	 */
	public static void jComboBoxEntityActionPerformed(JComboBox jComboBoxEntity,JMESimulation jmeSimulation) {
		chosenItem = jComboBoxEntity.getSelectedItem().toString();
		if (chosenItem.equalsIgnoreCase("Module") ){
			ConstructRobotTab.setEnabledGenericToolBar(true);
		}else if(chosenItem.equalsIgnoreCase("Robot")){
			//TODO  Support robot deletion, moving and coloring of connectors.
			ConstructRobotTab.setEnabledGenericToolBar(false);
		}		
	}

	/**
	 * Initializes the tool for rotating modules selected in simulation environment with opposite rotation. 
	 * @param jmeSimulation, the physical simulation.     
	 */	
	public static void jButtonOppositeRotationActionPerformed(JMESimulation jmeSimulation) {
		jmeSimulation.setPicker(new ConstructionToolSpecification(jmeSimulation, chosenMRname,ConstructionTools.OPPOSITE_ROTATION));
		/*Informing user*/
		ConstructRobotTab.getHintPanel().setText(HintPanelInter.builInHintsConstrucRobotTab[2]);
	}

	/**
	 * Initializes the tool for rotating initial module selected in simulation environment with standard rotations. 
	 * @param jmeSimulation, the physical simulation.  
	 */	
	public static void jComboBoxStandardRotationsActionPerformed(JMESimulation jmeSimulation) {
		jmeSimulation.setPicker(new ConstructionToolSpecification(jmeSimulation, chosenMRname,ConstructionTools.STANDARD_ROTATIONS,ConstructRobotTab.getjComboBoxStandardRotations().getSelectedItem().toString()));
		/*Informing user*/
		ConstructRobotTab.getHintPanel().setText(HintPanelInter.builInHintsConstrucRobotTab[2]);
	}

	/**
	 * Initializes the tool for adding new modules on selected connectors of the module in interest in simulation environment. 
	 * @param jmeSimulation, the physical simulation.
	 */
	public static void jButtonOnSelectedConnectorActionPerformed(JMESimulation jmeSimulation) {
		/*Disable tab components no longer available*/
		ConstructRobotTab.setEnabledRotationToolBar(false);
		ConstructRobotTab.getJButtonMove().setEnabled(false);

		jmeSimulation.setPicker(new ConstructionToolSpecification(jmeSimulation, chosenMRname,ConstructionTools.ON_SELECTED_CONNECTOR));
		/*Informing user*/
		ConstructRobotTab.getHintPanel().setText(HintPanelInter.builInHintsConstrucRobotTab[7]); 
	}

	/**
	 * Initializes the tool for adding new modules on connector chosen in combo box(GUI) by user. Later user selects the module to apply it to.
	 * @param comboBoxNrConnectorsConstructionTool, JComboBox containing the number of connectors.
	 * @param jmeSimulation, the physical simulation.
	 */
	public static void jComboBoxNrConnectorsConstructionToolActionPerformed(JComboBox comboBoxNrConnectorsConstructionTool,JMESimulation jmeSimulation) {
		/*Disable tab components no longer available*/
		ConstructRobotTab.setEnabledRotationToolBar(false);
		ConstructRobotTab.getJButtonMove().setEnabled(false);

		int chosenConnectorNr = Integer.parseInt(comboBoxNrConnectorsConstructionTool.getSelectedItem().toString());
		jmeSimulation.setPicker(new ConstructionToolSpecification(jmeSimulation, chosenMRname,ConstructionTools.ON_CHOSEN_CONNECTOR,chosenConnectorNr));
		/*Informing user*/
		ConstructRobotTab.getHintPanel().setText(HintPanelInter.builInHintsConstrucRobotTab[8]); 
	}

	/**
	 * Initializes the tool for adding new modules to all connectors of selected module.
	 * @param jmeSimulation, the physical simulation.
	 */
	public static void jButtonConnectAllModulesActionPerformed(JMESimulation jmeSimulation) {
		/*Disable tab components no longer available*/
		ConstructRobotTab.setEnabledRotationToolBar(false);
		ConstructRobotTab.getJButtonMove().setEnabled(false);

		jmeSimulation.setPicker(new ConstructionToolSpecification(jmeSimulation, chosenMRname,ConstructionTools.ON_ALL_CONNECTORS));
		/*Informing user*/
		ConstructRobotTab.getHintPanel().setText(HintPanelInter.builInHintsConstrucRobotTab[9]);

	}

	/**
	 * Default construction tool for moving module from one connector onto another one.
	 */
	private static ConstructionToolSpecification constructionTools = new ConstructionToolSpecification(jmeSimulationLocal, chosenMRname,ConstructionTools.LOOP,0);

	/**
	 * Used to keep track on which connector number the module is positioned. 
	 */
	private static int connectorNr;

	/**
	 * Initializes the tool for moving new module from connector to another connector.
	 * @param jmeSimulation, the physical simulation.
	 */
	public static void jButtonJumpFromConnToConnectorActionPerformed(JMESimulation jmeSimulation) {
		/*Disable tab components no longer available*/
		ConstructRobotTab.setEnabledRotationToolBar(false);
		ConstructRobotTab.getJButtonMove().setEnabled(false);

		connectorNr =0;
		ConstructionToolSpecification constructionToolsnew = new ConstructionToolSpecification(jmeSimulation, chosenMRname,ConstructionTools.LOOP,connectorNr);
		constructionTools = constructionToolsnew;
		jmeSimulation.setPicker(constructionToolsnew); 
		/*Informing user*/
		ConstructRobotTab.getHintPanel().setText(HintPanelInter.builInHintsConstrucRobotTab[10]); 
	}

	/**
	 * Moves new module from connector to another connector with increasing number of connector.
	 * @param jmeSimulation, the physical simulation.
	 */
	public static void jButtonOnNextConnectorActionPerformed(JMESimulation jmeSimulation) {
        
		connectorNr++;
		if (chosenMRname.equals(SupportedModularRobots.ATRON)){
			if (connectorNr>ATRON_CONNECTORS.length){ connectorNr=0;} //reset to zero      
		}else if (chosenMRname.equals(SupportedModularRobots.MTRAN)){
			if (connectorNr>MTRAN_CONNECTORS.length){ connectorNr=0;}
		}else if (chosenMRname.equals(SupportedModularRobots.ODIN)){
			if (connectorNr>12){ connectorNr=0;}// OdinBall
		}else if(chosenMRname.equals(SupportedModularRobots.CKBOTSTANDARD)){
			if (connectorNr>CKBOT_CONNECTORS.length){ connectorNr=0;}
		}
		//TODO NEED TO GET BACK FOR ODIN WHICH TYPE OF MODULE IS SELECTED. 
		int amountModules = jmeSimulation.getModules().size();
		String lastModuleType = jmeSimulation.getModules().get(amountModules-1).getProperty(BuilderHelper.getModuleTypeKey());
		if (lastModuleType.equalsIgnoreCase("OdinBall")){
			//do nothing
		}else{
			constructionTools.moveToNextConnector(connectorNr);
		}
		//TODO CHECK IF THE MODULE IS ALREADY ON CONNECTOR AND THEN DO NOT PLACE NEW ONE THERE.		
	}

	/**
	 * Moves new module from connector to another connector with decreasing number of connector.
	 * @param jmeSimulation, the physical simulation.
	 */
	public static void jButtonOnPreviousConnectorActionPerformed(JMESimulation jmeSimulation) {
		connectorNr--;
		if (chosenMRname.equals(SupportedModularRobots.ATRON) && connectorNr<0){
			connectorNr =ATRON_CONNECTORS.length-1;//reset
		}else if (chosenMRname.equals(SupportedModularRobots.MTRAN) && connectorNr<0){connectorNr=MTRAN_CONNECTORS.length-1;}
		else if (chosenMRname.equals(SupportedModularRobots.ODIN) && connectorNr<0){connectorNr=11;
		}else if (chosenMRname.equals(SupportedModularRobots.CKBOTSTANDARD) && connectorNr<0){connectorNr= CKBOT_CONNECTORS.length-1;}

		int amountModules = jmeSimulation.getModules().size();
		String lastModuleType = jmeSimulation.getModules().get(amountModules-1).getProperty(BuilderHelper.getModuleTypeKey());
		if (lastModuleType.equalsIgnoreCase("OdinBall")){
			//do nothing
		}else{
			constructionTools.moveToNextConnector(connectorNr);
		}
		//TODO CHECK IF THE MODULE IS ALREADY ON CONNECTOR AND THEN DO NOT PLACE NEW ONE THERE
	}

	/**
	 * Adapts
	 * Called from ConstructionToolSpecification.
	 * @param supportedModularRobot, supported modular robot.
	 */
	public static void adjustTabToSelectedModule(SupportedModularRobots supportedModularRobot){
		ConstructRobotTab.setRadioButtonsEnabled(true);
		if (supportedModularRobot.equals(SupportedModularRobots.ATRON)){
			ConstructRobotTab.getRadionButtonATRON().setSelected(true);			
		}else if (supportedModularRobot.equals(SupportedModularRobots.ODIN)){
			ConstructRobotTab.getRadionButtonODIN().setSelected(true);
		}else if (supportedModularRobot.equals(SupportedModularRobots.MTRAN)){			
			ConstructRobotTab.getRadioButtonMTRAN().setSelected(true);
		}else if (supportedModularRobot.equals(SupportedModularRobots.CKBOTSTANDARD)){
			ConstructRobotTab.getRadionButtonCKBOTSTANDARD().setSelected(true);
		}
		adaptTabToChosenMR(supportedModularRobot);
		ConstructRobotTab.setRadioButtonsEnabled(false);
	}


	/**
	 * @param jmeSimulation, the physical simulation.
	 */
	public static void jButtonStartNewRobotActionPerformed(JMESimulation jmeSimulation) {
		ConstructRobotTab.setRadioButtonsEnabled(true);
		/*Informing user*/
		ConstructRobotTab.getHintPanel().setText(HintPanelInter.builInHintsConstrucRobotTab[11]); 
		BuilderHelper.deleteAllModules(jmeSimulation);
	}

	//FIXME MAKE IT MORE GENERIC BY MEANS OF IDENTIFYING THE LAST TYPE OF MODULE IN XML FILE
	public static void adaptTabToModuleInSimulation(JMESimulation jmeSimulation){
		if (jmeSimulation.getModules().size()>0){
			/*Adapt first module*/
			String modularRobotName = jmeSimulation.getModules().get(0).getProperty(BuilderHelper.getModuleTypeKey());
			if (modularRobotName.toUpperCase().contains(SupportedModularRobots.ATRON.toString())){

				adaptTabToChosenMR(SupportedModularRobots.ATRON);
			} else if (modularRobotName.toUpperCase().contains(SupportedModularRobots.ODIN.toString())){
				adaptTabToChosenMR(SupportedModularRobots.ODIN);
			} else if (modularRobotName.toUpperCase().contains(SupportedModularRobots.MTRAN.toString())){
				adaptTabToChosenMR(SupportedModularRobots.MTRAN);
			}else if(modularRobotName.toUpperCase().contains(SupportedModularRobots.CKBOTSTANDARD.toString())){
				adaptTabToChosenMR(SupportedModularRobots.CKBOTSTANDARD);
			}

			jmeSimulation.setPicker(new ConstructionToolSpecification(jmeSimulation, chosenMRname,ConstructionTools.ON_SELECTED_CONNECTOR));
		}

	}

}
