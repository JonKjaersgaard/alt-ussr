package ussr.aGui.tabs.constructionTabs;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import ussr.aGui.MainFrames;

import ussr.aGui.controllers.MainFrameSeparateController;
import ussr.aGui.enumerations.ComponentsFrame;
import ussr.aGui.enumerations.JOptionPaneMessages;

import ussr.aGui.enumerations.hintpanel.HintsAssignLabelsTab;
import ussr.aGui.enumerations.hintpanel.HintsConstructRobotTab;
import ussr.aGui.enumerations.tabs.IconsNumbersConnectors;

import ussr.aGui.tabs.Tabs;
import ussr.aGui.tabs.controllers.SimulationTabController;
import ussr.aGui.tabs.designHelpers.ComboBoxRenderer;
import ussr.aGui.tabs.designHelpers.hintPanel.HintPanelTypes;
import ussr.builder.enumerations.SupportedModularRobots;

/**
 * Holds common methods for definition of construction tabs visual appearance. Tabs such as: ConstrutRobotTab.java and AssignBehaviors.java. 
 * @author Konstantinas
 */
public abstract class ConstructionTabs extends Tabs{

	/**
	 * The custom renderers for comboBox with numbers of connectors and colors beside them for each modular robot.
	 */
	public final static ComboBoxRenderer ATRON_RENDERER =  new ComboBoxRenderer(IconsNumbersConnectors.getAllImageIcons(),SupportedModularRobots.ATRON_CONNECTORS),
	ODIN_RENDERER = new ComboBoxRenderer(IconsNumbersConnectors.getAllImageIcons(),SupportedModularRobots.ODIN_BALL_CONNECTORS), 
	MTRAN_RENDERER = new ComboBoxRenderer(IconsNumbersConnectors.getAllImageIcons(),SupportedModularRobots.MTRAN_CONNECTORS),
	CKBOT_STANDARD_RENDERER =  new ComboBoxRenderer(IconsNumbersConnectors.getAllImageIcons(),SupportedModularRobots.CKBOTSTANDARD_CONNECTORS);
	/**
	 * Defines visual appearance of the tabs for construction of modular robot.
	 * @param firstTabbedPane,true if the tab is visible after activation of main GUI window. 
	 * @param tabTitle, the title of the tab
	 * @param imageIconDirectory,the directory for icon displayed in the top-left corner of the tab.
	 */
	protected ConstructionTabs(boolean initiallyVisible, boolean firstTabbedPane, String tabTitle, String imageIconDirectory) {
		super(initiallyVisible,firstTabbedPane, tabTitle, imageIconDirectory);
	}

	/**
	 * Controls selection and deselection of button. Only one button can be selected, others deselected.
	 * @param jButton, the button to control selection and deselection.
	 */
	public void setSelectionDeselection(javax.swing.JButton  jButton){
		/* go through each component in parent component*/
		for(int index =0;index<jComponent.getComponents().length; index++ ){			
			String className = jComponent.getComponent(index).getClass().toString();
			if (className.contains(ComponentsFrame.JToolBar.toString())){
				javax.swing.JToolBar  currentToolBar = (JToolBar) jComponent.getComponent(index);
				/*Go through components in the toolbar*/
				for(int inde =0;inde<currentToolBar.getComponents().length; inde++ ){
					String classNameNew = currentToolBar.getComponent(inde).toString();
					if (classNameNew.contains(ComponentsFrame.JButton.toString())){
						javax.swing.JButton  button = (JButton) currentToolBar.getComponent(inde);
						if (button.isSelected()){
							button.setSelected(false);
						}
					}/*else if (classNameNew.contains("JComboBox")){
						javax.swing.JComboBox  comboBox = (JComboBox) currentToolBar.getComponent(inde);
						if (comboBox.hasFocus()){
							comboBox.setFocusable(false);
						}
					}	*/				
				}					
			}else if (className.contains("JPanel")){
				JPanel panel = (JPanel)jComponent.getComponent(index); 
				for (int in =0; in<panel.getComponents().length; in ++){
					String className1 = panel.getComponent(in).getClass().toString();
					if (className1.contains("JToolBar")){
						javax.swing.JToolBar  currentToolBar = (JToolBar) panel.getComponent(in);
						/*Go through components in the toolbar*/
						for(int inde =0;inde<currentToolBar.getComponents().length; inde++ ){
							String classNameNew = currentToolBar.getComponent(inde).toString();
							if (classNameNew.contains("JButton")){
								javax.swing.JButton  button = (JButton) currentToolBar.getComponent(inde);
								if (button.isSelected()){
									button.setSelected(false);
								}
							}/*else if (classNameNew.contains("JComboBox")){
								javax.swing.JComboBox  comboBox = (JComboBox) currentToolBar.getComponent(inde);
								if (comboBox.hasFocus()){
									comboBox.setFocusable(false);
								}
							}*/						
						}					
					}
				}
			}
		}

		jButton.setSelected(true);
	}


	/**
	 * Controls adaptation of tabs named as ConstructRobotTab and AssignLabels tab, to amount of robots in simulation environment.
	 * Essentially is responsible to limit the user to use construction only on one robot at a time.
	 * @param rememberedChoice, the users choice from JOptionPane.
	 */
	public static void adaptToNrRobots(int rememberedChoice){
		int amountRobots = SimulationTabController.getSimulationSpecification().getRobotsInSimulation().size();
		boolean simulationState = MainFrameSeparateController.isSimulationRunning();

		if (amountRobots>1&& simulationState==false){
			int value;
			if (rememberedChoice!=-2){
				value = rememberedChoice; 
			}else{
				ConstructRobotTab.getHintPanel().setType(HintPanelTypes.ERROR);
				ConstructRobotTab.getHintPanel().setText(HintsConstructRobotTab.TAB_NOT_AVAILABLE_DUE_TO_AMOUNT_ROBOTS.getHintText());
				ConstructRobotTab.setTabEnabled(false);
				
				AssignLabelsTab.getHintPanel().setType(HintPanelTypes.ERROR);
				AssignLabelsTab.getHintPanel().setText(HintsConstructRobotTab.TAB_NOT_AVAILABLE_DUE_TO_AMOUNT_ROBOTS.getHintText());
				AssignLabelsTab.setTabEnabled(false);
				

				Object returnedValue = JOptionPaneMessages.ROBOT_CONSTRUCTION_TABS_LIMITATION.displayMessage();
				JCheckBox rememberCheckBox = (JCheckBox)JOptionPaneMessages.ROBOT_CONSTRUCTION_TABS_LIMITATION.getMessage()[1];
				value= Integer.parseInt(returnedValue.toString());
				if (rememberCheckBox.isSelected()){
					MainFrames.setRememberedChoice(value);
				}
			}
			switch(value){
			case 0:// Start new robot
				ConstructRobotTab.setTabEnabled(true);
				ConstructRobotTab.getJButtonStartNewRobot().doClick();	
				ConstructRobotTab.getHintPanel().setType(HintPanelTypes.INFORMATION);
				ConstructRobotTab.getHintPanel().setText(HintsConstructRobotTab.START_NEW_ROBOT.getHintText());
				AssignLabelsTab.setTabEnabled(true);
				AssignLabelsTab.getHintPanel().setType(HintPanelTypes.INFORMATION);
				AssignLabelsTab.getHintPanel().setText(HintsAssignLabelsTab.DEFAULT.getHintText());
				break;
			case 1: // Continue anyway
				ConstructRobotTab.setTabEnabled(true);
				ConstructRobotTab.setVisibleFirstModuleOperations(false);
				ConstructRobotTab.getHintPanel().setType(HintPanelTypes.INFORMATION);
				ConstructRobotTab.getHintPanel().setText(HintsConstructRobotTab.DEFAULT.getHintText());
				AssignLabelsTab.setTabEnabled(true);
				AssignLabelsTab.getHintPanel().setType(HintPanelTypes.INFORMATION);
				AssignLabelsTab.getHintPanel().setText(HintsAssignLabelsTab.DEFAULT.getHintText());
				break;
			case 2: // Cancel
			case -1: //Exit				
				break;
			default: throw new Error("The value named as " + value +" is not supported yet.");

			}
		}

	}
}
