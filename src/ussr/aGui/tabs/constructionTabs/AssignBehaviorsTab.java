package ussr.aGui.tabs.constructionTabs;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.JToolBar;

import ussr.aGui.enumerations.ModularRobotsNames;
import ussr.aGui.enumerations.tabs.TabsComponentsText;
import ussr.aGui.helpers.hintPanel.HintPanel;
import ussr.aGui.helpers.hintPanel.HintPanelInter;
import ussr.aGui.tabs.controllers.AssignBehaviorsTabController;
import ussr.builder.helpers.StringProcessingHelper;

/**
 * Defines visual appearance of the tab called "2 Step: Assign Behaviors".  
 * @author Konstantinas
 */
public class AssignBehaviorsTab extends ConstructionTabs{
	
	/**
	 * Used to display hints to the user. Feedback to the user.
	 */
	private static HintPanel hintPanel;
	
	/**
	 * The dimensions of the List component.
	 */
	private final int J_LIST_WIDTH = 150, J_LIST_HEIGHT = 180;


	private final int jToolBar1Width = 125;

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
	public AssignBehaviorsTab(boolean initiallyVisible, boolean firstTabbedPane, String tabTitle,String imageIconDirectory){
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
		jToolBarFilterForModularRobot = new javax.swing.JToolBar();
		
		jListAvailableControllers = new javax.swing.JList();

		jScrollPaneAvailableControllers = new javax.swing.JScrollPane();

		jPanelAssignBehaviors = new javax.swing.JPanel();		
		
		radionButtonATRON =  new JRadioButton();
		radioButtonODIN =  new JRadioButton();
		radioButtonMTRAN =  new JRadioButton();
		radionButtonCKBOTSTANDARD =  new JRadioButton();

		/*Description of components*/		
		gridBagConstraints.fill = GridBagConstraints.FIRST_LINE_END;
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		
		jToolBarSaveLoad = initSaveLoadJToolbar();
		super.jComponent.add(jToolBarSaveLoad,gridBagConstraints);	
		
		jToolBarFilterForModularRobot.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(""), "Filter out for:", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.ABOVE_TOP));		
		jToolBarFilterForModularRobot.setFloatable(false);//user can not make the tool bar to float
		jToolBarFilterForModularRobot.setRollover(true);// the buttons inside are roll over
		jToolBarFilterForModularRobot.setToolTipText(StringProcessingHelper.replaceUnderscoreWithSpace(TabsComponentsText.Filter_out_for));
		jToolBarFilterForModularRobot.setPreferredSize(new Dimension(jToolBar1Width,J_LIST_HEIGHT-50));
		jToolBarFilterForModularRobot.setOrientation(JToolBar.VERTICAL);		

		final ButtonGroup buttonGroup = new ButtonGroup() ;
	
		radionButtonATRON.setText(ModularRobotsNames.ATRON.toString());	
		radionButtonATRON.setFocusable(true);// direct the user to what should be done first
		radionButtonATRON.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				AssignBehaviorsTabController.jButtonGroupActionPerformed(radionButtonATRON);
			}
		});

		jToolBarFilterForModularRobot.add(radionButtonATRON);
		buttonGroup.add(radionButtonATRON);

		radioButtonODIN.setText(ModularRobotsNames.Odin.toString());
		radioButtonODIN.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				AssignBehaviorsTabController.jButtonGroupActionPerformed(radioButtonODIN);
			}
		});

		jToolBarFilterForModularRobot.add(radioButtonODIN);
		buttonGroup.add(radioButtonODIN);

		radioButtonMTRAN.setText(ModularRobotsNames.MTRAN.toString());
		radioButtonMTRAN.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				AssignBehaviorsTabController.jButtonGroupActionPerformed(radioButtonMTRAN);
			}
		});

		jToolBarFilterForModularRobot.add(radioButtonMTRAN);
		buttonGroup.add(radioButtonMTRAN);

		radionButtonCKBOTSTANDARD.setText(ModularRobotsNames.CKBotStandard.toString());
		radionButtonCKBOTSTANDARD.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				AssignBehaviorsTabController.jButtonGroupActionPerformed(radionButtonCKBOTSTANDARD);
			}
		});

		jToolBarFilterForModularRobot.add(radionButtonCKBOTSTANDARD);
		buttonGroup.add(radionButtonCKBOTSTANDARD);			
		
		jListAvailableControllers.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
		jListAvailableControllers.setPreferredSize(new java.awt.Dimension(J_LIST_WIDTH+60, J_LIST_HEIGHT));	
		jListAvailableControllers.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseReleased(java.awt.event.MouseEvent evt) {
				AssignBehaviorsTabController.jListAvailableControllersMouseReleased( jListAvailableControllers);
			}
		});		
		jScrollPaneAvailableControllers.setViewportView(jListAvailableControllers);
		jScrollPaneAvailableControllers.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(""), "Available behaviors", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.ABOVE_TOP));

			
		/*Internal layout of the panel*/
		GroupLayout jPanelAssignBehaviorsLayout = new GroupLayout(jPanelAssignBehaviors);
		jPanelAssignBehaviors.setLayout(jPanelAssignBehaviorsLayout);

		jPanelAssignBehaviorsLayout.setAutoCreateGaps(true);
		jPanelAssignBehaviorsLayout.setHorizontalGroup(
				jPanelAssignBehaviorsLayout.createSequentialGroup()			
				.addComponent(jToolBarFilterForModularRobot,GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
						GroupLayout.PREFERRED_SIZE)						
						.addComponent(jScrollPaneAvailableControllers,GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)							
		);

		jPanelAssignBehaviorsLayout.setVerticalGroup(
				jPanelAssignBehaviorsLayout.createSequentialGroup()
				.addGroup(jPanelAssignBehaviorsLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(jToolBarFilterForModularRobot,GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
								.addComponent(jScrollPaneAvailableControllers,GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))												
		);
		/*External layout of the panel*/
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		
		super.jComponent.add(jPanelAssignBehaviors,gridBagConstraints);			
		
		/*Display for hints. Feedback to the user.*/
		hintPanel = initHintPanel(400,HINT_PANEL_HEIGHT,HintPanelInter.builInHintsAssignBehaviorTab[0]);		
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.gridx = 0;		
		gridBagConstraints.gridy = 4;
		gridBagConstraints.gridwidth = 2;
		super.jComponent.add(hintPanel,gridBagConstraints);	

	}

/*Getters and Setter*/
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
	 * Enables or disables the tab;
	 * @param enabled, true if the tab is enabled.
	 */
	public static void setTabEnabled (boolean enabled){
//MORE HERE
		for (int button=0; button<jToolBarSaveLoad.getComponentCount();button++ ){
			JButton jButton = (JButton)jToolBarSaveLoad.getComponent(button);
			jButton.setEnabled(enabled);
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
	
	/*Declaration of components*/
	private static javax.swing.JList jListAvailableControllers;	
	private javax.swing.JPanel jPanelAssignBehaviors;

	private javax.swing.JScrollPane jScrollPaneAvailableControllers;
	
	private static  javax.swing.AbstractButton radionButtonATRON,radioButtonMTRAN,
	radioButtonODIN,radionButtonCKBOTSTANDARD;

	private static javax.swing.JToolBar jToolBarFilterForModularRobot,jToolBarSaveLoad;
}
