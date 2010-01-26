package ussr.aGui.tabs.constructionTabs;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.JRadioButton;
import javax.swing.JToolBar;

import ussr.aGui.FramesInter;
import ussr.aGui.controllers.MainFrameSeparateController;

import ussr.aGui.tabs.controllers.AssignControllerTabController;
import ussr.aGui.tabs.designHelpers.hintPanel.HintPanel;
import ussr.aGui.tabs.designHelpers.hintPanel.HintPanelTypes;
import ussr.aGui.tabs.enumerations.TabsComponentsText;
import ussr.aGui.tabs.enumerations.TabsIcons;
import ussr.aGui.tabs.enumerations.hintPanel.HintsAssignControllersTab;
import ussr.builder.enumerations.SupportedModularRobots;

/**
 * Defines visual appearance of the tab called "2 Step: Assign Behaviors".  
 * @author Konstantinas
 */
public class AssignControllerTab extends ConstructionTabs{

	/**
	 * Used to display hints to the user. Feedback to the user.
	 */
	private static HintPanel hintPanel;

	/**
	 * The dimensions of the List component.
	 */
	private final int J_LIST_WIDTH = 150, J_LIST_HEIGHT = 180;


	/**
	 * Width of the toolbar for filtering supported modular robot.
	 */
	private final int jToolBar1Width = 125;
	
	
	/**
	 * Initial state of selection for button Edit values.
	 */
	private static  boolean jToggleButtonEditValuesIsSelected = false;
	
	/**
	 * The container for radio buttons of supported modular robots for assigning controllers.
	 */
	private static ArrayList<AbstractButton> jRadioButtonSupportedMR =  new ArrayList<AbstractButton>() ;	

	/**
	 * The constrains of grid bag layout used during design of both tabs.
	 */
	public  GridBagConstraints gridBagConstraints = new GridBagConstraints();

	/**
	 * Defines visual appearance of the tab called "2 Step: Assign Behaviors".
	 * @param initiallyVisible, true if the tab is visible after activation of main GUI window. 
	 * @param firstTabbedPane, location of the tab in the main GUI frame. True if it is the first tabbed pane from the top. 
	 * @param tabTitle, the title of the tab.
	 * @param imageIconDirectory,the directory for icon displayed in the top-left corner of the tab.
	 */
	public AssignControllerTab(boolean initiallyVisible, boolean firstTabbedPane, String tabTitle,String imageIconDirectory){
		super(initiallyVisible,firstTabbedPane,tabTitle,imageIconDirectory);	

		/*instantiate new panel, which will be the container for all components situated in the tab*/		
		super.jComponent = new javax.swing.JPanel(new GridBagLayout());

		initComponents();
	}

	/**
	 * Initializes the visual appearance of all components in the tab.
	 * Follows Strategy  pattern.
	 */
	public void initComponents() {

		/*Instantiation of components*/
		jToolBarGeneralControl = new javax.swing.JToolBar();
		jToolBarFilterForModularRobot = new javax.swing.JToolBar();
		
		jToggleButtonEditValues =   new javax.swing.JToggleButton();

		jListAvailableControllers = new javax.swing.JList();

		jScrollPaneAvailableControllers = new javax.swing.JScrollPane();

		jPanelAssignBehaviors = new javax.swing.JPanel();
		jPanelEditValue = new javax.swing.JPanel(new GridBagLayout());

		radionButtonATRON =  new JRadioButton();
		radioButtonODIN =  new JRadioButton();
		radioButtonMTRAN =  new JRadioButton();
		radionButtonCKBOTSTANDARD =  new JRadioButton();
		
		buttonGroup = new ButtonGroup() ;
		
		/*Description of components*/
		jToolBarGeneralControl.setBorder(FramesInter.TOOLBAR_BORDER);
		jToolBarGeneralControl.setToolTipText(TabsComponentsText.GENERAL_CONTROL.getUserFriendlyName());
		jToolBarGeneralControl.setFloatable(false);//user can not make the tool bar to float
		jToolBarGeneralControl.setRollover(true);// the components inside are roll over
		jToolBarGeneralControl.setPreferredSize(new Dimension(375,FramesInter.HORIZONTAL_TOOLBAR_HEIGHT));
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.insets = new Insets(0, 0, 30, 0);
		
		jToggleButtonEditValues.setToolTipText(TabsComponentsText.ENTER_VALUES.getUserFriendlyName());
		jToggleButtonEditValues.setIcon(TabsIcons.ENTER_VALUES.getImageIcon());
		jToggleButtonEditValues.setSelectedIcon(TabsIcons.ENTER_VALUES_ROLLOVER.getImageIcon());
		jToggleButtonEditValues.setRolloverIcon(TabsIcons.ENTER_VALUES_ROLLOVER.getImageIcon());
		jToggleButtonEditValues.setFocusable(false);
		jToggleButtonEditValues.setEnabled(true);
		jToggleButtonEditValues.setPreferredSize(FramesInter.BUTTON_DIMENSION);
		jToggleButtonEditValues.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				AssignControllerTabController.jToggleButtonEditValuesActionPerformed(jToggleButtonEditValues);
			}
		});
		
		jToolBarGeneralControl.add(jToggleButtonEditValues);
				
		super.jComponent.add(jToolBarGeneralControl,gridBagConstraints);

		jToolBarFilterForModularRobot.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(""), "Filter out for:", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.ABOVE_TOP));		
		jToolBarFilterForModularRobot.setFloatable(false);//user can not make the tool bar to float
		jToolBarFilterForModularRobot.setRollover(true);// the buttons inside are roll over
		jToolBarFilterForModularRobot.setToolTipText(TabsComponentsText.FILTER_OUT_FOR.getUserFriendlyName());
		jToolBarFilterForModularRobot.setPreferredSize(new Dimension(jToolBar1Width,J_LIST_HEIGHT));
		jToolBarFilterForModularRobot.setOrientation(JToolBar.VERTICAL);		


		radionButtonATRON.setText(SupportedModularRobots.ATRON.getUserFriendlyName());	
		radionButtonATRON.setFocusable(true);// direct the user to what should be done first
		radionButtonATRON.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				AssignControllerTabController.jButtonGroupActionPerformed(radionButtonATRON);
			}
		});
		jRadioButtonSupportedMR.add(radionButtonATRON);
		jToolBarFilterForModularRobot.add(radionButtonATRON);
		buttonGroup.add(radionButtonATRON);

		radioButtonODIN.setText(SupportedModularRobots.ODIN.getUserFriendlyName());
		radioButtonODIN.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				AssignControllerTabController.jButtonGroupActionPerformed(radioButtonODIN);
			}
		});
		jRadioButtonSupportedMR.add(radioButtonODIN);
		jToolBarFilterForModularRobot.add(radioButtonODIN);
		buttonGroup.add(radioButtonODIN);

		radioButtonMTRAN.setText(SupportedModularRobots.MTRAN.getUserFriendlyName());
		radioButtonMTRAN.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				AssignControllerTabController.jButtonGroupActionPerformed(radioButtonMTRAN);
			}
		});
		jRadioButtonSupportedMR.add(radioButtonMTRAN);
		jToolBarFilterForModularRobot.add(radioButtonMTRAN);
		buttonGroup.add(radioButtonMTRAN);

		radionButtonCKBOTSTANDARD.setText(SupportedModularRobots.CKBOT_STANDARD.getUserFriendlyName());
		radionButtonCKBOTSTANDARD.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				AssignControllerTabController.jButtonGroupActionPerformed(radionButtonCKBOTSTANDARD);
			}
		});
		jRadioButtonSupportedMR.add(radionButtonCKBOTSTANDARD);
		jToolBarFilterForModularRobot.add(radionButtonCKBOTSTANDARD);
		buttonGroup.add(radionButtonCKBOTSTANDARD);			

		jListAvailableControllers.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
		jListAvailableControllers.setPreferredSize(new java.awt.Dimension(J_LIST_WIDTH+60, J_LIST_HEIGHT));	
		jListAvailableControllers.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseReleased(java.awt.event.MouseEvent evt) {
				AssignControllerTabController.jListAvailableControllersMouseReleased( jListAvailableControllers);
			}
		});		
		jScrollPaneAvailableControllers.setViewportView(jListAvailableControllers);
		jScrollPaneAvailableControllers.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(""), "Available controllers", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.ABOVE_TOP));

		jPanelEditValue.setPreferredSize(new Dimension(J_LIST_WIDTH+30,J_LIST_HEIGHT));
		jPanelEditValue.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(""), "Edit Value", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.ABOVE_TOP));
		jPanelEditValue.setVisible(false);

		/*Internal layout of the panel*/
		GroupLayout jPanelAssignBehaviorsLayout = new GroupLayout(jPanelAssignBehaviors);
		jPanelAssignBehaviors.setLayout(jPanelAssignBehaviorsLayout);

		jPanelAssignBehaviorsLayout.setHorizontalGroup(
				jPanelAssignBehaviorsLayout.createSequentialGroup()
				.addComponent(jToolBarFilterForModularRobot,GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE,
						GroupLayout.PREFERRED_SIZE)								
						.addComponent(jScrollPaneAvailableControllers,GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE,
								GroupLayout.PREFERRED_SIZE)
								.addComponent(jPanelEditValue,GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE,
										GroupLayout.PREFERRED_SIZE)	
		);

		jPanelAssignBehaviorsLayout.setVerticalGroup(
				jPanelAssignBehaviorsLayout.createSequentialGroup()
				.addGroup(jPanelAssignBehaviorsLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
								.addComponent(jToolBarFilterForModularRobot,GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE,
										GroupLayout.PREFERRED_SIZE)							
										.addComponent(jScrollPaneAvailableControllers,GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE,
												GroupLayout.PREFERRED_SIZE)
												.addComponent(jPanelEditValue,GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE,
														GroupLayout.PREFERRED_SIZE))
				);


		/*External layout of the panel*/
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;

		super.jComponent.add(jPanelAssignBehaviors,gridBagConstraints);			

		/*Display for hints. Feedback to the user.*/
		hintPanel = initHintPanel(400,HINT_PANEL_HEIGHT,HintsAssignControllersTab.DEFAULT.getHintText());		
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.gridx = 0;		
		gridBagConstraints.gridy = 2;
		gridBagConstraints.gridwidth = 2;
		super.jComponent.add(hintPanel,gridBagConstraints);	

	}

	/*Getters and Setter*/
	/**
	 * Returns the button for coloring connectors of modules. 
	 * @return the button for coloring connectors of modules.
	 */
	public static javax.swing.JToggleButton getJToggleButtonColorConnetors() {
		return jToggleButtonColorConnetors;
	}
	
	/**
	 * Returns the list for displaying available controllers for user to choose from.
	 * @return the list for displaying available controllers for user to choose from.
	 */
	public static javax.swing.JList getJListAvailableControllers() {
		return jListAvailableControllers;
	}

	/**
	 * Returns hint panel, which is used to display feedback to the user.
	 * @return hint panel, which is used to display feedback to the user.
	 */
	public static HintPanel getHintPanel(){
		return hintPanel;
	}
	
	/**
	 * Sets the state of selection for button edit values.
	 * @param toggleButtonEditValuesIsSelected the state of selection for button edit values.
	 */
	public static void setJToggleButtonEditValuesIsSelected(boolean toggleButtonEditValuesIsSelected) {
		jToggleButtonEditValuesIsSelected = toggleButtonEditValuesIsSelected;
	}
	
	/**
	 * Returns the state of selection for button edit values.
	 * @return the state of selection for button edit values.
	 */
	public static boolean isJToggleButtonEditValuesIsSelected() {
		return jToggleButtonEditValuesIsSelected;
	}

	/**
	 * Sets whenever the radio buttons for supported  modular robots should be enabled or not.
	 * @param enabled, the radio buttons for supported  modular robots should be enabled or not.
	 */
	public static void setEnabledRadioButtons(boolean enabled){
		for (AbstractButton radioButton: jRadioButtonSupportedMR ){
			radioButton.setEnabled(enabled);
		}
	}

	/**
	 * Enables or disables the tab;
	 * @param enabled, true if the tab is enabled.
	 */
	public static void setTabEnabled (boolean enabled){
		setEnabledRadioButtons(enabled);
		jToggleButtonEditValues.setEnabled(enabled);
	
		jListAvailableControllers.setEnabled(enabled);
		jPanelEditValue.setEnabled(enabled);
		if (MainFrameSeparateController.isSimulationRunning()==false){
			hintPanel.setType(HintPanelTypes.ATTENTION);
			hintPanel.setText(HintsAssignControllersTab.TAB_AVAILABLE_DURING_RUNNING_SIMULATION.getHintText());
		}else{
			hintPanel.setType(HintPanelTypes.INFORMATION);
			hintPanel.setText(HintsAssignControllersTab.DEFAULT.getHintText());
		}
	}

	/**
	 * Returns radio button representing the choice of CKBotStandard modular robot.
	 * @return radio button representing the choice of CKBotStandard modular robot.
	 */
	public static javax.swing.AbstractButton getRadionButtonCKBOTSTANDARD() {
		return radionButtonCKBOTSTANDARD;
	}

	/**
	 * Returns radio button representing the choice of Odin modular robot.
	 * @return radio button representing the choice of Odin modular robot.
	 */
	public static javax.swing.AbstractButton getRadioButtonODIN() {
		return radioButtonODIN;
	}

	/**
	 * Returns radio button representing the choice of MTRAN modular robot.
	 * @return radio button representing the choice of MTRAN modular robot.
	 */
	public static javax.swing.AbstractButton getRadioButtonMTRAN() {
		return radioButtonMTRAN;
	}

	/**
	 * Returns radio button representing the choice of ATRON modular robot.
	 * @return radio button representing the choice of ATRON modular robot.
	 */
	public static javax.swing.AbstractButton getRadionButtonATRON() {
		return radionButtonATRON;
	}	
	
	/**
	 * Returns the panel for editing values of controller.
	 * @return the panel for editing values of controller.
	 */
	public static javax.swing.JPanel getJPanelEditValue() {
		return jPanelEditValue;
	}
	
	/**
	 * Returns the toggle button associated with edition of controller values. 
	 * @return the toggle button associated with edition of controller values.
	 */
	public static javax.swing.JToggleButton getJToggleButtonEditValues() {
		return jToggleButtonEditValues;
	}
	
	/**
	 * Returns the group of radio buttons representing supported modular robots. 
	 * @return the group of radio buttons representing supported modular robots. 
	 */
	public static ButtonGroup getButtonGroup() {
		return buttonGroup;
	}
	
	/*Declaration of components*/
	private static javax.swing.JList jListAvailableControllers;	
	private javax.swing.JPanel jPanelAssignBehaviors;

	private javax.swing.JScrollPane jScrollPaneAvailableControllers;

	private static  javax.swing.AbstractButton radionButtonATRON,radioButtonMTRAN,
	radioButtonODIN,radionButtonCKBOTSTANDARD;

	private static javax.swing.JToolBar jToolBarFilterForModularRobot,jToolBarGeneralControl;

	private static javax.swing.JPanel jPanelEditValue;
	
	private static javax.swing.JToggleButton jToggleButtonEditValues,jToggleButtonColorConnetors;

	private static ButtonGroup buttonGroup;	
}
