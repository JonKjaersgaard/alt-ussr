package ussr.aGui.tabs.views.constructionTab;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.font.TextAttribute;

import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;
import javax.swing.JToolBar;

import ussr.aGui.tabs.Tabs;
import ussr.aGui.tabs.controllers.AssignBehaviorsTabController;
import ussr.physics.jme.JMESimulation;

/**
 * Defines visual appearance of the tab called "2 Step: Assign Behaviors".  
 * @author Konstantinas
 */
public class AssignBehaviorsTab extends Tabs {
	
	/**
	 * The constrains of grid bag layout used during design of the tab.
	 */
	private GridBagConstraints gridBagConstraints = new GridBagConstraints();

	/**
	 * The dimensions of the List component.
	 */
	private final int J_LIST_WIDTH = 250, J_LIST_HEIGHT = 200;
	
	
	private final int jToolBar1Width = 125;
	
	/**
	 * Defines visual appearance of the tab called "2 Step: Assign Behaviors".
	 * @param firstTabbedPane,
	 * @param tabTitle, the title of the tab
	 * @param jmeSimulation, the physical simulation.
	 * @param imageIconDirectory
	 */
	public AssignBehaviorsTab(boolean firstTabbedPane, String tabTitle,JMESimulation jmeSimulation,String imageIconDirectory){
		super(firstTabbedPane,tabTitle,jmeSimulation,imageIconDirectory);	
		
		/*instantiate new panel, which will be the container for all components situated in the tab*/		
		this.jComponent = new javax.swing.JPanel(new GridBagLayout());
		super.fontAttributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
		initComponents();
	}

	/**
	 * Initializes the visual appearance of all components in the tab.
	 * Follows Strategy  pattern.
	 */
	public void initComponents() {

		/*Instantiation of components*/
		jToolBar1 = new javax.swing.JToolBar();
		
		jLabel10002 = new javax.swing.JLabel();
		jLabel10003 = new javax.swing.JLabel();
		jLabel1000 = new javax.swing.JLabel();
		jLabel10004 = new javax.swing.JLabel();	
		jLabel10005 = new javax.swing.JLabel();	
		
		jList1 = new javax.swing.JList();
		
		jScrollPane2 = new javax.swing.JScrollPane();		
		/*Instantiation of components*/
		
		
		/*Description of appearance*/
		jLabel10004.setText("Shortcut:");
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		super.jComponent.add(jLabel10004,gridBagConstraints);		
		
		jLabel10003.setText("Filter out for:");
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 1;
		super.jComponent.add(jLabel10003,gridBagConstraints);
		
		jLabel10002.setText("Choose controller beneath and select module(s).When done run simulation.");
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridy = 1;
		super.jComponent.add(jLabel10002,gridBagConstraints);
		
		jToolBar1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
		jToolBar1.setFloatable(false);//user can not make the tool bar to float
		jToolBar1.setRollover(true);// the buttons inside are roll over
		jToolBar1.setToolTipText("Generic tools");
		jToolBar1.setPreferredSize(new Dimension(jToolBar1Width,J_LIST_HEIGHT-70));
		jToolBar1.setOrientation(JToolBar.VERTICAL);
		
		
		final ButtonGroup buttonGroup = new ButtonGroup() ;

		radionButtonATRON =  new JRadioButton();		
		radionButtonATRON.setText("ATRON");	
		radionButtonATRON.setFocusable(true);// direct the user to what should be done first
		radionButtonATRON.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				AssignBehaviorsTabController.jButtonGroupActionPerformed(radionButtonATRON,jmeSimulation);
			}
		});
	
		jToolBar1.add(radionButtonATRON);
		buttonGroup.add(radionButtonATRON);

		radionButtonODIN =  new JRadioButton();
		radionButtonODIN.setText("Odin");
		radionButtonODIN.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				AssignBehaviorsTabController.jButtonGroupActionPerformed(radionButtonODIN,jmeSimulation);
			}
		});

		jToolBar1.add(radionButtonODIN);
		buttonGroup.add(radionButtonODIN);


		radioButtonMTRAN =  new JRadioButton();
		radioButtonMTRAN.setText("MTran");
		radioButtonMTRAN.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				AssignBehaviorsTabController.jButtonGroupActionPerformed(radioButtonMTRAN,jmeSimulation);
			}
		});
	
		jToolBar1.add(radioButtonMTRAN);
		buttonGroup.add(radioButtonMTRAN);

		radionButtonCKBOTSTANDARD =  new JRadioButton();
		radionButtonCKBOTSTANDARD.setText("CKBotStandard");
		radionButtonCKBOTSTANDARD.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				//AssignBehaviorsTabController.jButtonGroupActionPerformed(radionButtonCKBOTSTANDARD,jmeSimulation);
			}
		});
		
		jToolBar1.add(radionButtonCKBOTSTANDARD);
		buttonGroup.add(radionButtonCKBOTSTANDARD);		
		
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 2;
		super.jComponent.add(jToolBar1,gridBagConstraints);			
		
		jList1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
		jList1.setPreferredSize(new java.awt.Dimension(J_LIST_WIDTH, J_LIST_HEIGHT));	
		AssignBehaviorsTabController.loadExistingControllers(jList1);
		
		jList1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
            	AssignBehaviorsTabController.jList1MouseReleased( jList1,jmeSimulation);
            }
        });
		jScrollPane2.setViewportView(jList1);
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridy = 2;
		super.jComponent.add(jScrollPane2,gridBagConstraints);				
			
		jLabel10005.setText("Controller was assigned successfully.");
		jLabel10005.setFont( new Font("Times New Roman", Font.BOLD, 12));
		jLabel10005.setVisible(false);
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridy = 3;
		//jLabel10005.setIcon(new javax.swing.ImageIcon(DIRECTORY_ICONS + ATTENTION));		
		super.jComponent.add(jLabel10005,gridBagConstraints);		
		
		jLabel1000.setIcon(new javax.swing.ImageIcon(DIRECTORY_ICONS + INFORMATION));		
		jLabel1000.setText("It also possible to assign controllers during simulation run time.");
		jLabel1000.setFont( new Font("Times New Roman", Font.PLAIN, 12).deriveFont(fontAttributes));
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridy = 4;
		jLabel1000.setVisible(false);
		//gridBagConstraints.weighty = 0.5;   //request any extra vertical space
		super.jComponent.add(jLabel1000,gridBagConstraints);
		
		/*jLabel10005 = new javax.swing.JLabel();		
		jLabel10005.setText("Assign them in simulation run time.");
		jLabel10005.setFont( new Font("Times New Roman", Font.BOLD, 12));
		jLabel10005.setVisible(false);
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 3;
		jLabel10005.setIcon(new javax.swing.ImageIcon(DIRECTORY_ICONS + ATTENTION));		
		
		jComponent.add(jLabel10005,gridBagConstraints);	*/

	}

	
	public static javax.swing.JList getJList1() {
		return jList1;
	}
	
	public static javax.swing.JLabel getJLabel10005() {
		return jLabel10005;
	}
	public static javax.swing.JLabel getJLabel1000() {
		return jLabel1000;
	}


	
	/*Declaration of components*/
	private static javax.swing.JList jList1;	

	private javax.swing.JScrollPane jScrollPane2;
	
	/*Labels*/
	private static javax.swing.JLabel jLabel10002;
	private static javax.swing.JLabel jLabel10003;
	private static javax.swing.JLabel jLabel10004;
	private static javax.swing.JLabel jLabel10005;
	private static javax.swing.JLabel jLabel1000;	

	/*Radio Buttons*/
	private static  javax.swing.AbstractButton radionButtonATRON;
	private static javax.swing.AbstractButton  radioButtonMTRAN;
	private static javax.swing.AbstractButton radionButtonODIN;
	private static javax.swing.AbstractButton radionButtonCKBOTSTANDARD;
	
	private static javax.swing.JToolBar jToolBar1;
}
