package ussr.aGui.tabs.views.constructionTab;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.font.TextAttribute;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.AbstractButton;
import javax.swing.Action;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButton;



import ussr.aGui.FramesInter;
import ussr.aGui.GuiFrames;
import ussr.aGui.MainFrameInter;

import ussr.aGui.tabs.controllers.ConstructionTabController;
import ussr.aGui.tabs.views.Tabs;
import ussr.builder.SupportedModularRobots;
import ussr.physics.jme.JMESimulation;

/**
 * Defines visual appearance of the tab called "1 Step: Construct Robot".  
 * @author Konstantinas
 */
public class ConstructionTab extends Tabs {


	private static ArrayList<JComponent> jCompnents =  new ArrayList<JComponent>() ;

	private static ArrayList<AbstractButton> jRadioButtons =  new ArrayList<AbstractButton>() ;

	/**
	 * The constants of grid bag layout used during design of the tab.
	 */
	private GridBagConstraints gridBagConstraints = new GridBagConstraints();	
	
	
	/**
	 * Defines visual appearance of the tab called "1 Step: Construct Robot".
	 * @param firstTabbedPane,
	 * @param tabTitle, the title of the tab
	 * @param jmeSimulation, the physical simulation.
	 * @param imageIconDirectory,
	 */
	public ConstructionTab(boolean firstTabbedPane,String tabTitle,JMESimulation jmeSimulation,String imageIconDirectory){
		super(firstTabbedPane,tabTitle,jmeSimulation,imageIconDirectory);
		
		/*instantiate new panel, which will be the container for all components situated in the tab*/		
		super.jComponent = new javax.swing.JPanel(new GridBagLayout());
		super.fontAttributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
		initComponents();
	}



	/**
	 * Initializes the visual appearance of all components in the tab.
	 * Follows Strategy  pattern.
	 */
	/* (non-Javadoc)
	 * @see ussr.aGui.tabs.views.Tabs#initComponents()
	 */
	public void initComponents(){

		/*Instantiation of components*/
		jToolBar1 = new javax.swing.JToolBar();
		jToolBar2 = new javax.swing.JToolBar();

		jButtonDelete =   new javax.swing.JButton();
		jButtonMove =   new javax.swing.JButton();	
		jButtonColorConnetors =   new javax.swing.JButton();
		button14 =   new javax.swing.JButton();
		jButton15 =   new javax.swing.JButton();

		jLabel1000 = new javax.swing.JLabel();
		jLabel10002 = new javax.swing.JLabel();	
		jLabel10003 = new javax.swing.JLabel();	

		jComboBox1 = new javax.swing.JComboBox();
		jComboBox2 = new javax.swing.JComboBox();


		jLabel10002.setText("Add initial module:");
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		super.jComponent.add(jLabel10002,gridBagConstraints);

		final ButtonGroup buttonGroup = new ButtonGroup() ;

		radionButtonATRON =  new JRadioButton();
		radionButtonATRON.setFocusable(true);// direct the user to what should be done first
		radionButtonATRON.setText("ATRON");		
		radionButtonATRON.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				ConstructionTabController.jButtonGroupActionPerformed(radionButtonATRON,jmeSimulation);
			}
		});
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 1;
		//gridBagConstraints.ipady = 10; 
		super.jComponent.add(radionButtonATRON,gridBagConstraints);
		buttonGroup.add(radionButtonATRON);
		jRadioButtons.add(radionButtonATRON);

		radionButtonODIN =  new JRadioButton();
		radionButtonODIN.setText("Odin");
		radionButtonODIN.setFocusable(false);
		radionButtonODIN.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				ConstructionTabController.jButtonGroupActionPerformed(radionButtonODIN,jmeSimulation);
			}
		});
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridy = 1;
		//gridBagConstraints.ipady = 10; 
		super.jComponent.add(radionButtonODIN,gridBagConstraints);
		buttonGroup.add(radionButtonODIN);
		jRadioButtons.add(radionButtonODIN);

		radioButtonMTRAN =  new JRadioButton();
		radioButtonMTRAN.setText("MTran");
		radioButtonMTRAN.setFocusable(false);
		radioButtonMTRAN.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				ConstructionTabController.jButtonGroupActionPerformed(radioButtonMTRAN,jmeSimulation);
			}
		});
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.gridx = 3;
		gridBagConstraints.gridy = 1;
		//gridBagConstraints.ipady = 10; 
		super.jComponent.add(radioButtonMTRAN,gridBagConstraints);
		buttonGroup.add(radioButtonMTRAN);
		jRadioButtons.add(radioButtonMTRAN);

		radionButtonCKBOTSTANDARD =  new JRadioButton();
		radionButtonCKBOTSTANDARD.setText("CKBotStandard");
		radionButtonCKBOTSTANDARD.setFocusable(false);
		radionButtonCKBOTSTANDARD.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				ConstructionTabController.jButtonGroupActionPerformed(radionButtonCKBOTSTANDARD,jmeSimulation);
			}
		});
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.gridx = 4;
		gridBagConstraints.gridy = 1;
		//gridBagConstraints.ipady = 40; 
		super.jComponent.add(radionButtonCKBOTSTANDARD,gridBagConstraints);
		buttonGroup.add(radionButtonCKBOTSTANDARD);	
		jRadioButtons.add(radionButtonCKBOTSTANDARD);	

		/*jLabel10001 = new javax.swing.JLabel();		
		jLabel10001.setText("When done with constructing press ready button");
		jLabel10001.setFont( new Font("Times New Roman", Font.PLAIN, 12).deriveFont(fontAttributes));
		jLabel10001.setVisible(false);
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 3;
		gridBagConstraints.gridwidth = 4;
		gridBagConstraints.insets = new Insets(0,30,0,0);  //left padding
		super.jComponent.add(jLabel10001,gridBagConstraints);*/

		/*		button14.setText("Robot Ready");		
		button14.setToolTipText("Robot is ready");		
		button14.setFocusable(false); 
		button14.setPreferredSize(new java.awt.Dimension(30, 30));	
		button14.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				ConstructionTabController.jButton14ActionPerformed(jmeSimulation, button14);
			}
		});
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.gridx = 4;
		gridBagConstraints.gridy = 3;
		super.jComponent.add(button14,gridBagConstraints);	*/	

		jLabel1000.setText("Apply rotation:");		
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;		
		super.jComponent.add(jLabel1000,gridBagConstraints);

		jToolBar2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
		jToolBar2.setFloatable(false);//user can not make the tool bar to float
		jToolBar2.setRollover(true);// the components inside are roll over
		jToolBar2.setToolTipText("Rotation tools");
		jToolBar2.setPreferredSize(new Dimension(185,GuiFrames.COMMON_HEIGHT+2));
		/*External layout of the toolbar in the panel*/
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.gridx = 1;		
		gridBagConstraints.gridy = 3;
		gridBagConstraints.gridwidth = 4;	


		jButton15.setToolTipText("Opposite");		
		jButton15.setIcon(new javax.swing.ImageIcon(DIRECTORY_ICONS + OPPOSITE));
		jButton15.setDisabledIcon(new javax.swing.ImageIcon(MainFrameInter.DIRECTORY_ICONS + MainFrameInter.NO_ENTRANCE));
		jButton15.setFocusable(false); 
		jButton15.setEnabled(false);
		jButton15.setPreferredSize(new java.awt.Dimension(FramesInter.BUTTONS_WIDTH, FramesInter.COMMON_HEIGHT-3));	
		jButton15.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				ConstructionTabController.jButton15ActionPerformed(jmeSimulation);
			}
		});

		jComboBox2.setToolTipText("Specific");
		jComboBox2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { " " }));
		jComboBox2.setPreferredSize(new java.awt.Dimension(140, GuiFrames.COMMON_HEIGHT-3));
		jComboBox2.setEnabled(false);
		jComboBox2.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				ConstructionTabController.jComboBox2ActionPerformed(jmeSimulation);
			}
		});

		/*Internal layout of the toolbar*/
		GroupLayout jToolBar2Layout = new GroupLayout(jToolBar2);
		jToolBar2.setLayout(jToolBar2Layout);


		jToolBar2Layout.setHorizontalGroup(
				jToolBar2Layout.createSequentialGroup()
				//Forces preferred side of component
				.addComponent(jButton15,GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
						GroupLayout.PREFERRED_SIZE)
						.addComponent(jComboBox2,GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)				
		);

		jToolBar2Layout.setVerticalGroup(
				jToolBar2Layout.createSequentialGroup()
				.addGroup(jToolBar2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(jButton15,GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
								.addComponent(jComboBox2,GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))						
		);

		super.jComponent.add(jToolBar2,gridBagConstraints);

		jLabel10003.setText("Generic funtionality for:");
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 4;
		gridBagConstraints.gridwidth = 1;// reset grid width
		gridBagConstraints.insets = new Insets(0,0,0,5);  //bring padding back
		super.jComponent.add(jLabel10003,gridBagConstraints);		

		jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Module", "Robot" }));
		jComboBox1.setPreferredSize(new java.awt.Dimension(59, GuiFrames.COMMON_HEIGHT));
		jComboBox1.setEnabled(false);
		jComboBox1.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				ConstructionTabController.jComboBox1ActionPerformed(jComboBox1,jmeSimulation);
			}
		});
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 5;
		super.jComponent.add(jComboBox1,gridBagConstraints);

		/*	jLabel10004 = new javax.swing.JLabel();		
		jLabel10004.setText("Generic functionality:");
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 5;
		super.jComponent.add(jLabel10004,gridBagConstraints);	*/	

		jToolBar1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
		jToolBar1.setFloatable(false);//user can not make the tool bar to float
		jToolBar1.setRollover(true);// the buttons inside are roll over
		jToolBar1.setToolTipText("Generic tools");
		jToolBar1.setPreferredSize(new Dimension(195,GuiFrames.COMMON_HEIGHT));		
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.gridx = 2;		
		gridBagConstraints.gridy = 5;
		gridBagConstraints.gridwidth = 3;		

		jButtonMove.setToolTipText("Move");		
		jButtonMove.setIcon(new javax.swing.ImageIcon(DIRECTORY_ICONS + MOVE));
		jButtonMove.setDisabledIcon(new javax.swing.ImageIcon(MainFrameInter.DIRECTORY_ICONS + MainFrameInter.NO_ENTRANCE));
		jButtonMove.setFocusable(false); 
		jButtonMove.setEnabled(false);
		jButtonMove.setPreferredSize(new java.awt.Dimension(FramesInter.BUTTONS_WIDTH, FramesInter.COMMON_HEIGHT));	
		jButtonMove.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				ConstructionTabController.jButton11ActionPerformed(jmeSimulation);
			}
		});
		jToolBar1.add(jButtonMove);

		jButtonDelete.setToolTipText("Delete");
		jButtonDelete.setIcon(new javax.swing.ImageIcon(DIRECTORY_ICONS + DELETE));
		jButtonDelete.setDisabledIcon(new javax.swing.ImageIcon(MainFrameInter.DIRECTORY_ICONS + MainFrameInter.NO_ENTRANCE));		
		jButtonDelete.setFocusable(false);
		jButtonDelete.setEnabled(false);
		jButtonDelete.setPreferredSize(new java.awt.Dimension(FramesInter.BUTTONS_WIDTH, FramesInter.COMMON_HEIGHT));
		jButtonDelete.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				ConstructionTabController.jButton10ActionPerformed(jmeSimulation);
			}
		});

		jToolBar1.add(jButtonDelete);		

		jButtonColorConnetors.setToolTipText("Color Connectors");
		jButtonColorConnetors.setIcon(new javax.swing.ImageIcon(DIRECTORY_ICONS + COLOUR_CONNECTORS));
		jButtonColorConnetors.setDisabledIcon(new javax.swing.ImageIcon(MainFrameInter.DIRECTORY_ICONS + FramesInter.COMMON_HEIGHT));		
		jButtonColorConnetors.setFocusable(false);
		jButtonColorConnetors.setEnabled(false);
		jButtonColorConnetors.setPreferredSize(new java.awt.Dimension(FramesInter.BUTTONS_WIDTH, FramesInter.BUTTONS_WIDTH));
		jButtonColorConnetors.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				ConstructionTabController.jButton12ActionPerformed(jmeSimulation);
			}
		});

		jToolBar1.add(jButtonColorConnetors);

		super.jComponent.add(jToolBar1,gridBagConstraints);








		for (int index =0; index < super.jComponent.getComponentCount();index++){
			jCompnents.add((JComponent) super.jComponent.getComponent(index));
		}


	}



	public void setEnabled(){
		super.jComponent.setEnabled(false);
	}

	public static void setRadioButtonsEnabled(boolean enabled){
		for (AbstractButton radioButton: jRadioButtons ){
			radioButton.setEnabled(enabled);
		}
	}

	/*Getters*/

	public static ArrayList<JComponent> getJCompnents() {
		return jCompnents;
	}

	public static void setEnabledAllComponents(boolean enabled){
		for (int index=0; index<getJCompnents().size(); index++){
			getJCompnents().get(index).setEnabled(enabled);
		}
		//FIXME WHY TOOL BAR IS NOT ADDED AUTOMATICALLY INTO ARRAY?
		jToolBar1.setEnabled(enabled);

	}



	public static void setEnabledGenericToolBar(boolean enable){
		jButtonDelete.setEnabled(enable);
		jButtonMove.setEnabled(enable);
		jButtonColorConnetors.setEnabled(enable);
	}



	public static void setEnabledRotationToolBar(boolean enable){
		jButton15.setEnabled(enable);
		jComboBox2.setEnabled(enable);
		//jButtonMove.setEnabled(enable);
		//jButtonColorConnetors.setEnabled(enable);
	}


	public static javax.swing.JComboBox getJComboBox1() {
		return jComboBox1;
	}

	public static javax.swing.AbstractButton getButton1() {
		return radionButtonATRON;
	}



	public static javax.swing.JLabel getJLabel10001() {
		return jLabel10001;
	}
	
	public static javax.swing.JComboBox getJComboBox2() {
		return jComboBox2;
	}

	/*Declaration of tab components*/
	private static javax.swing.JComboBox jComboBox1;
	private static javax.swing.JComboBox jComboBox2;

	private static javax.swing.JLabel jLabel1000;


	private static javax.swing.JLabel jLabel10001;
	private static javax.swing.JLabel jLabel10002;
	private static javax.swing.JLabel jLabel10003;
	private static javax.swing.JLabel jLabel10004;

	private static  javax.swing.AbstractButton radionButtonATRON;
	private static javax.swing.AbstractButton  radioButtonMTRAN;
	private static javax.swing.AbstractButton radionButtonODIN;
	private static javax.swing.AbstractButton radionButtonCKBOTSTANDARD;


	private  static javax.swing.JButton jButtonDelete;
	private  static javax.swing.JButton jButtonMove;
	private  static javax.swing.JButton jButtonColorConnetors;
	private  static javax.swing.JButton button14;
	private  static javax.swing.JButton jButton15;

	private static javax.swing.JToolBar jToolBar1;
	private static javax.swing.JToolBar jToolBar2;








}
