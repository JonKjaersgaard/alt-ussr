package ussr.aGui.tabs;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;

import ussr.aGui.tabs.controllers.YourNewTabController;
import ussr.aGui.tabs.designHelpers.hintPanel.HintPanel;
import ussr.aGui.tabs.enumerations.hintPanel.HintsYourNewTab;


/**
 * Defines visual appearance of YOUR NEW tab implemented by YOU. Serves as an example for how to design and add new tab in main GUI.
 * Replicates part of design from Construct Robot Tab and explains it in step by step fashion.
 * Please leave this class alone for future developers and use the copy of it.  
 * @author Konstantinas
 */
public class YourNewTab extends Tabs {

	/**
	 * The constants of grid bag layout used during design of the tab.
	 * This layout is used often during design of GUI, because of its flexibility to positioning components of GUI. 
	 */
	private GridBagConstraints gridBagConstraints = new GridBagConstraints();	
	

	/**
	 * Defines visual appearance of YOUR NEW tab implemented by YOU. Serves as an example for how to design and add new tab in main GUI.
     * Replicates part of design from Construct Robot Tab and explains it in step by step fashion.
     * Please leave this class alone for future developers and use the copy of it.
     * @param initiallyVisible, true if the tab is visible after activation of main GUI window. 
	 * @param firstTabbedPane,location of the tab in the main GUI frame. True if it is the first tabbed pane. 
	 * @param tabTitle, the title of the tab.
	 * @param imageIconDirectory,the directory for icon displayed in the top-left corner of the tab.
	 */
	public YourNewTab(boolean initiallyVisible ,boolean firstTabbedPane, String tabTitle, String imageIconDirectory){
		super(initiallyVisible,firstTabbedPane,tabTitle,imageIconDirectory);
		
		/*JComponent, is the main container of the tab. Place all your components in it.
		 *Here is used GridBagLayout manager and JPanel as main container.*/		
		super.jComponent = new javax.swing.JPanel(new GridBagLayout());
		initComponents();
	}

	/**
     * Initializes the visual appearance of all components in the tab.
     * Follows Strategy  pattern.
     */
	public void initComponents() {
		
		/*1) Describe first component*/
		final javax.swing.JButton jButtonStartNewRobot = new javax.swing.JButton();
		jButtonStartNewRobot.setText("Start new robot");
		jButtonStartNewRobot.setRolloverEnabled(true);
		jButtonStartNewRobot.setToolTipText("Start constructing new robot");
		jButtonStartNewRobot.setFocusable(true);
		jButtonStartNewRobot.setPreferredSize(new Dimension(110,32));
        jButtonStartNewRobot.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {            	 
            	//Place controller call here.
            	YourNewTabController.jButtonStartNewRobotActionPerformed(jButtonStartNewRobot);
            }
        });        
        gridBagConstraints.fill = GridBagConstraints.FIRST_LINE_START;// position on the first line start
        gridBagConstraints.gridx = 0;// x goes from left to the right of the screen
		gridBagConstraints.gridy = 0;// y goes from top to the bottom of the screen
		/*2) Add it to the main container of the tab*/
		super.jComponent.add(jButtonStartNewRobot,gridBagConstraints);
	
		javax.swing.ButtonGroup  buttonGroupModularRobots = new ButtonGroup();
		
		/*3)Describe second component*/
		final javax.swing.AbstractButton radionButtonATRON =  new JRadioButton();
		radionButtonATRON.setFocusable(false);
		radionButtonATRON.setText("ATRON");
		radionButtonATRON.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				//Place controller call here.
				YourNewTabController.radionButtonATRONActionPerformed(radionButtonATRON);
			}
		});
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.gridx = 1;//second column
		gridBagConstraints.gridy = 1;//second row
		gridBagConstraints.insets = new Insets(0,0,0,0);//reset
		/*4)Add it to the main container of the tab*/
		super.jComponent.add(radionButtonATRON,gridBagConstraints);
		buttonGroupModularRobots.add(radionButtonATRON);
		
		/*5)Describe third component*/
		final javax.swing.AbstractButton radionButtonODIN =  new JRadioButton();
		radionButtonODIN.setText("Odin");
		radionButtonODIN.setFocusable(false);
		radionButtonODIN.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				YourNewTabController.radionButtonOdinActionPerformed(radionButtonODIN);
				//Place controller call here.
			}
		});
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.gridx = 2;//third column
		gridBagConstraints.gridy = 1;//second row
		/*6)Add it to the main container of the tab*/
		super.jComponent.add(radionButtonODIN,gridBagConstraints);
		buttonGroupModularRobots.add(radionButtonODIN);
		
		/*7)Describe fourth component*/
		final javax.swing.AbstractButton radioButtonMTRAN =  new JRadioButton();
		radioButtonMTRAN.setText("Mtran");
		radioButtonMTRAN.setFocusable(false);
		radioButtonMTRAN.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				YourNewTabController.radionButtonMTRANActionPerformed(radioButtonMTRAN);
				//Place controller call here.
			}
		});
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.gridx = 3;//fourth column
		gridBagConstraints.gridy = 1;//second row
		/*8)Add it to the main container of the tab*/
		super.jComponent.add(radioButtonMTRAN,gridBagConstraints);
		
		/*9)Describe fifth component*/
		/*Display for hints. Feedback to the user.*/
		hintPanel =  initHintPanel(430,HINT_PANEL_HEIGHT,HintsYourNewTab.DEFAULT.getHintText());			
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.gridx = 0;		
		gridBagConstraints.gridy = 2;
		gridBagConstraints.gridwidth = 4;
		/*10)Add it to the main container of the tab*/
		super.jComponent.add(hintPanel,gridBagConstraints);
		
		/*Look for inspiration in design of other tabs or create your own. The only thing to remember is
		 * that jComponent is your main container.*/
	}
	
	private static HintPanel hintPanel;

}
