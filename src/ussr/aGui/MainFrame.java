package ussr.aGui;


import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import java.util.ArrayList;

import javax.tools.JavaCompiler;

import ussr.aGui.fileChooser.appearance.FileChooserOpenFrame;
import ussr.aGui.fileChooser.appearance.FileChooserSaveFrame;
import ussr.aGui.fileChooser.controllers.FileChooserControllerInter;
import ussr.aGui.fileChooser.controllers.FileChooserXMLController;
import ussr.aGui.tabs.TabsInter;
import ussr.physics.jme.JMEBasicGraphicalSimulation;
import ussr.physics.jme.JMESimulation;


/**
 *
 * @author Konstantinas
 */
public class MainFrame extends GuiFrames implements MainFrameInter {

	/**
	 * The physical simulation
	 */	   
	private JMESimulation jmeSimulation;

	//private ArrayList<String> namesTabs = new ArrayList<String>() ;

	private static MainFrame mainFrame;


	private  GuiInter fcOpenFrame;

	private  GuiInter fcSaveFrame;

	private ArrayList<TabsInter> tabs;
	
	private ArrayList<javax.swing.JComponent> components = new ArrayList<javax.swing.JComponent>();  

	private int windowHeight=0;

	public MainFrame() {		
		initFileChoosers();
		initComponents();		
	}




	/**
	 * 
	 * Starts the main GUI window (frame) during the simulation.
	 * This can be achieved by pressing "O" on keyboard after starting the simulation. 
	 * @param JMESimulation, the physical simulation
	 */
	public MainFrame(JMEBasicGraphicalSimulation jmeSimulation, ArrayList<TabsInter> tabs){
		this.jmeSimulation = (JMESimulation) jmeSimulation;
		this.tabs = tabs;
		//this.namesTabs = namesTabs;
		//this.tabs = tabs;
		initFileChoosers();		
		initComponents();
		changeInstanceFlag();		
		
		this.addWindowStateListener (new WindowAdapter() {	
			    public void windowStateChanged(WindowEvent e) {
			    	System.out.println(
			          "All:"+e);
			    	int newState = e.getNewState();
			    	System.out.println( "State:"+e.getNewState());
			    	if (newState == 6){//MAXIMIZED
			    		System.out.println( "YES");
			    		for(int index=0;index<components.size();index++){
			    			components.get(index).setPreferredSize(new Dimension((int)SCREEN_DIMENSION.getWidth()-PADDING/2,components.get(index).getHeight()));
			    		}			    		
			    	}else if(newState == 0){//Restore down
			    		System.out.println( "YES1");
			    		for(int index=0;index<components.size();index++){
			    			components.get(index).setPreferredSize(new Dimension((int)SCREEN_DIMENSION.getWidth()/2-PADDING,components.get(index).getHeight()));
			    		}
			    	}
			    }
			    
			    
		}
		);		
	}
	

	/**
	 * Instance flag for current frame, used to keep track that only one instance of the frame is instantiated.
	 */
	private static boolean instanceFlag = false;

	/**
	 * Changes the flag for window instantiation. If it was once instantiated it is true. When the window closes the flag is reset to false.
	 */
	private void changeInstanceFlag(){
		instanceFlag = true;// the frame is instantiated
		// Overrides event for closing the frame, in order for the frame to do not open several times with several times pressing on the button on keyboard.		
		addWindowListener (new WindowAdapter() {			
			public void windowClosing(WindowEvent event) {
				instanceFlag = false; // reset the flag after closing the frame event appears
				event.getWindow().dispose();	                     
			}			
		}
		);		
	}



	/**
	 * Initializes file choosers in two forms: 1)Open and 2)Save dialog.
	 */
	private  void initFileChoosers () {	
		ArrayList <String> fileExtensions = new ArrayList<String>();
		fileExtensions.add(".xml");

		FileChooserControllerInter fcXMLController = new FileChooserXMLController();
		ArrayList<FileChooserControllerInter> fcControllers = new ArrayList<FileChooserControllerInter>();
		fcControllers.add(fcXMLController);

		fcOpenFrame = new FileChooserOpenFrame(fileExtensions,fcControllers);	
		fcSaveFrame = new FileChooserSaveFrame(fileExtensions,fcControllers);

	}






	/*	public MainFrame(boolean constructionDisplayed, boolean controllerDisplayed) {
		this.constructionDisplayed = constructionDisplayed;
		this.controllerDisplayed = controllerDisplayed;
	}*/

	/* (non-Javadoc)
	 * @see ussr.aGui.GuiFrames#initComponents()
	 */
	protected void initComponents() {
	
		/*Instantiation of MainFrame components*/
		jMenuBar1 = new javax.swing.JMenuBar();		

		jMenu1 = new javax.swing.JMenu();
		jMenu2 = new javax.swing.JMenu();
		jMenu3 = new javax.swing.JMenu();
		jMenu4 = new javax.swing.JMenu();


		jMenuItem2 = new javax.swing.JMenuItem();
		jMenuItem4 = new javax.swing.JMenuItem();

		jSeparator2 = new javax.swing.JSeparator();
		jMenuItem3 = new javax.swing.JMenuItem();
		jSeparator1 = new javax.swing.JSeparator();
		jMenuItem1 = new javax.swing.JMenuItem();		

		jCheckBoxMenuItem1 = new javax.swing.JCheckBoxMenuItem();
		jCheckBoxMenuItem2 = new javax.swing.JCheckBoxMenuItem();
		jCheckBoxMenuItem3 = new javax.swing.JCheckBoxMenuItem();
		jCheckBoxMenuItem4 = new javax.swing.JCheckBoxMenuItem();
		jCheckBoxMenuItem5 = new javax.swing.JCheckBoxMenuItem();
		jCheckBoxMenuItem6 = new javax.swing.JCheckBoxMenuItem();

		jToolBar1 = new javax.swing.JToolBar();
		jToolBar2 = new javax.swing.JToolBar();

		

		jButton1 = new javax.swing.JButton();
		jButton2 = new javax.swing.JButton();
		jButton3 = new javax.swing.JButton();
		jButton6 = new javax.swing.JButton();
		jButton7 = new javax.swing.JButton();

		jLabel1 = new javax.swing.JLabel();
		jTextField1 = new javax.swing.JTextField(); 
		
		jScrollPane1 = new javax.swing.JScrollPane();
		jTextArea1 = new javax.swing.JTextArea();	
		
		
		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		setUSSRicon(this);
		setTitle("Unified Simulator for Self-Reconfigurable Robots");
		getContentPane().setLayout(new java.awt.FlowLayout());


		jToolBar2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
		jToolBar2.setRollover(true);
		jToolBar2.setToolTipText("Simulation Control");
		jToolBar2.setPreferredSize(new Dimension((int)SCREEN_DIMENSION.getWidth()/2-PADDING,TOOLBAR_HEIGHT));
		//jToolBar2.setPreferredSize(new Dimension((int)SCREEN_DIMENSION.getWidth()-PADDING,TOOLBAR_HEIGHT));

		jButton1.setIcon(new javax.swing.ImageIcon(DIRECTORY_ICONS + RUN_REAL_TIME));
		jButton1.setToolTipText("Run real time");
		jButton1.setBorder(javax.swing.BorderFactory.createEtchedBorder());//On Vista does not work(test it)
		jButton1.setFocusable(false);    
		jButton1.setPreferredSize(new java.awt.Dimension(30, 30));      
		jButton1.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				MainFrameController.jButton1ActionPerformed(jmeSimulation);        	  
			}
		});
		jToolBar2.add(jButton1);

		jButton6.setIcon(new javax.swing.ImageIcon(DIRECTORY_ICONS + RUN_FAST));
		jButton6.setToolTipText("Run fast");
		jButton6.setFocusable(false);
		jButton6.setPreferredSize(new java.awt.Dimension(30, 30));      
		jButton6.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				MainFrameController.jButton6ActionPerformed(jmeSimulation);        	  
			}
		});

		jToolBar2.add(jButton6);

		jButton2.setIcon(new javax.swing.ImageIcon(DIRECTORY_ICONS + STEP_BY_STEP));
		jButton2.setToolTipText("Run step by step");
		jButton2.setBorder(javax.swing.BorderFactory.createEtchedBorder());//On Vista does not work(test it)
		jButton2.setFocusable(false);
		jButton2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		jButton2.setPreferredSize(new java.awt.Dimension(30, 30));
		jButton2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
		jButton2.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				MainFrameController.jButton2ActionPerformed(jmeSimulation);
			}
		});
		jToolBar2.add(jButton2);

		jButton7.setIcon(new javax.swing.ImageIcon(DIRECTORY_ICONS + PAUSE));
		jButton7.setToolTipText("Pause");
		jButton7.setFocusable(false);   
		jButton7.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				MainFrameController.jButton7ActionPerformed(jmeSimulation);
			}
		});
		jToolBar2.add(jButton7);


		jButton3.setIcon(new javax.swing.ImageIcon(DIRECTORY_ICONS + SAVE));
		jButton3.setToolTipText("Save");
		jButton3.setFocusable(false);
		jButton3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		jButton3.setPreferredSize(new java.awt.Dimension(30, 30));
		jButton3.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
		jButton3.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				MainFrameController.jMenuItem3ActionPerformed(fcSaveFrame);
			}
		});
		jToolBar2.add(jButton3);

		getContentPane().add(jToolBar2);

		jTabbedPane1.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);
		jTabbedPane1.setPreferredSize(new Dimension((int)SCREEN_DIMENSION.getWidth()/2-PADDING, TAB_PANE_HEIGHT1));
		//jTabbedPane1.setPreferredSize(new Dimension((int)SCREEN_DIMENSION.getWidth()-PADDING,TAB_PANE_HEIGHT1));
		jTabbedPane1.setFocusable(false);
		/*	jTabbedPaneNew = new javax.swing.JTabbedPane();
		jTabbedPane1.addTab("New", jTabbedPaneNew);

		jCheckBoxMenuItemNew = new javax.swing.JCheckBoxMenuItem();
		jCheckBoxMenuItemNew.setSelected(true);
		jCheckBoxMenuItemNew.setText("New");
		jCheckBoxMenuItemNew.addActionListener(new java.awt.event.ActionListener() {
	            public void actionPerformed(java.awt.event.ActionEvent evt) {
	                MainFrameController.jCheckBoxMenuItemActionPerformedNew(jCheckBoxMenuItemNew, jTabbedPaneNew);
	            }
	        });
		jMenu4.add(jCheckBoxMenuItemNew);*/

		final ArrayList<javax.swing.JCheckBoxMenuItem> checkBoxMenuItems = new ArrayList<javax.swing.JCheckBoxMenuItem>();
		for (int index =0; index < tabs.size(); index++){					  
			TabsInter currentTab = tabs.get(index); 
			jTabbedPane1.add(tabs.get(index).getTabTitle(),currentTab.getJPanel1000());

			jCheckBoxMenuItemNew = new javax.swing.JCheckBoxMenuItem();
			jCheckBoxMenuItemNew.setSelected(true);
			jCheckBoxMenuItemNew.setText(tabs.get(index).getTabTitle());
			checkBoxMenuItems.add(jCheckBoxMenuItemNew);
			jCheckBoxMenuItemNew.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent evt) {
					MainFrameController.jCheckBoxMenuItemActionPerformedNew(checkBoxMenuItems, jTabbedPane1,tabs);
				}
			});
			jMenu4.add(jCheckBoxMenuItemNew);			
		}

		/*	for (int index=0; index <checkBoxMenuItems.size();index++){

			 checkBoxMenuItems.get(index).addActionListener(new java.awt.event.ActionListener() {
	            public void actionPerformed(java.awt.event.ActionEvent evt) {
	                MainFrameController.jCheckBoxMenuItemActionPerformedNew(checkBoxMenuItems.get(index), checkBoxMenuItems, jTabbedPane1,specificationTabs);
	            }
	        });
		}*/


		getContentPane().add(jTabbedPane1); 

		//jTextArea1.setColumns(20);
		//jTextArea1.setRows(5);
		//jTextArea1.setPreferredSize(new Dimension((int)SCREEN_DIMENSION.getWidth()/2-PADDING,TOOLBAR_HEIGHT));
		//jScrollPane1.setViewportView(jTextArea1);

		//getContentPane().add(jScrollPane1);
		
		jTabbedPane3.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);
		jTabbedPane3.setPreferredSize(new Dimension((int)SCREEN_DIMENSION.getWidth()/2-PADDING, TAB_PANE_HEIGHT2));
		//jTabbedPane3.setPreferredSize(new Dimension((int)SCREEN_DIMENSION.getWidth()-PADDING,TAB_PANE_HEIGHT1));
        getContentPane().add(jTabbedPane3);


		jToolBar1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
		jToolBar1.setFloatable(false);//user can not make the tool bar to float
		jToolBar1.setRollover(true);// the buttons inside are roll over
		jToolBar1.setToolTipText("Command Line Interface");
		jToolBar1.setPreferredSize(new Dimension((int)SCREEN_DIMENSION.getWidth()/2-PADDING,TOOLBAR_HEIGHT));
		getContentPane().add(jToolBar1);       

		jLabel1.setText(" CLI ");
		jLabel1.setToolTipText("Command Line Interface");
		jToolBar1.add(jLabel1);

		jTextField1.setText("Enter command");
		jTextField1.setToolTipText("Command Line Interface");
		jTextField1.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusGained(java.awt.event.FocusEvent evt) {
				MainFrameController.jTextField1FocusGained( jTextField1 );
			}
		});
		jToolBar1.add(jTextField1);

		jMenu1.setText("File");

		jMenuItem2.setText("Open");
		jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				MainFrameController.jMenuItem2ActionPerformed(fcOpenFrame);
			}
		});

		jMenu1.add(jMenuItem2);

		jMenuItem4.setText("Open default");
		jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				/*MainFrameController.*///jMenuItem4ActionPerformed(evt);
			}
		});
		jMenu1.add(jMenuItem4);      

		jMenu1.add(jSeparator2);

		jMenuItem3.setIcon(new javax.swing.ImageIcon(DIRECTORY_ICONS+SAVE_SMALL));
		jMenuItem3.setText("Save");
		jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				MainFrameController.jMenuItem3ActionPerformed(fcSaveFrame);
			}
		});

		jMenu1.add(jMenuItem3);
		jMenu1.add(jSeparator1);

		jMenuItem1.setIcon(new javax.swing.ImageIcon(DIRECTORY_ICONS+EXIT));
		jMenuItem1.setText("Exit");
		jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				MainFrameController.jMenuItem1ActionPerformed(mainFrame);
			}
		});
		jMenu1.add(jMenuItem1);

		jMenuBar1.add(jMenu1);

		jMenu2.setText("View");
		jMenu4.setText("Tabs");

		//      jMenuItem5.setText("Construct");
		//        jMenu4.add(jMenuItem5);

		//jMenuItem6.setText("Assign");
		// jMenu4.add(jMenuItem6);

		jMenu2.add(jMenu4);

		jMenuBar1.add(jMenu2);  


		jMenu3.setText("Render");
		jCheckBoxMenuItem1.setSelected(false);
		jCheckBoxMenuItem1.setText("Physics");
		jCheckBoxMenuItem1.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				MainFrameController.jCheckBoxMenuItem1ActionPerformed(jCheckBoxMenuItem1,jmeSimulation);
			}
		});
		jMenu3.add(jCheckBoxMenuItem1);

		jCheckBoxMenuItem2.setSelected(false);
		jCheckBoxMenuItem2.setText("Wire Frame");
		jCheckBoxMenuItem2.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				MainFrameController.jCheckBoxMenuItem2ActionPerformed(jCheckBoxMenuItem2,jmeSimulation);
			}
		});
		jMenu3.add(jCheckBoxMenuItem2);

		jCheckBoxMenuItem3.setSelected(false);
		jCheckBoxMenuItem3.setText("Bounds");
		jCheckBoxMenuItem3.setText("Bounds");
		jCheckBoxMenuItem3.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				MainFrameController.jCheckBoxMenuItem3ActionPerformed(jCheckBoxMenuItem3,jmeSimulation);
			}
		});
		jMenu3.add(jCheckBoxMenuItem3);

		jCheckBoxMenuItem4.setSelected(false);
		jCheckBoxMenuItem4.setText("Normals");
		jCheckBoxMenuItem4.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				MainFrameController.jCheckBoxMenuItem4ActionPerformed(jCheckBoxMenuItem4,jmeSimulation);
			}
		});
		jMenu3.add(jCheckBoxMenuItem4);

		jCheckBoxMenuItem5.setSelected(false);
		jCheckBoxMenuItem5.setText("Lights");
		jCheckBoxMenuItem5.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				MainFrameController.jCheckBoxMenuItem5ActionPerformed(jCheckBoxMenuItem5,jmeSimulation);
			}
		});
		jMenu3.add(jCheckBoxMenuItem5);

		jCheckBoxMenuItem6.setSelected(false);
		jCheckBoxMenuItem6.setText("Buffer Depth");
		jCheckBoxMenuItem6.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				MainFrameController.jCheckBoxMenuItem6ActionPerformed(jCheckBoxMenuItem6,jmeSimulation);
			}
		});

		jMenu3.add(jCheckBoxMenuItem6);

		jMenuBar1.add(jMenu3);

		setJMenuBar(jMenuBar1);
		pack();   
		
		
		components.add(jMenuBar1);
		components.add(jToolBar2);
		components.add(jTabbedPane1);
		components.add(jTabbedPane3);
		components.add(jToolBar1);
		
		for (int  index =0;index<components.size();index++){
			windowHeight = windowHeight+ components.get(index).getHeight();
			//System.out.println("height:"+heightComponents.get(index).getHeight());
		}
		
		setDimensionsOf(this,(int)SCREEN_DIMENSION.getWidth()/2,windowHeight+PADDING);
		//setSizeFullScreen(this);
		//setSizeHalfScreen(this);
		changeToSetLookAndFeel(this);// makes troubles with the borders of the buttons 
	}

	public javax.swing.JTabbedPane getJTabbedPane1() {
		return jTabbedPane1;
	}




	/**
	 * Enables and disables menu components opening file choosers. 
	 * @param state
	 */
	public static void setSaveOpenEnabled (boolean state){
		jButton3.setEnabled(state);
		jMenuItem2.setEnabled(state);
		jMenuItem3.setEnabled(state);		
	} 

	/**
	 * Starts the main GUI window (frame).
	 * Follows strategy pattern. 
	 */
	public void activate(){
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {            	
				mainFrame = new MainFrame();
				mainFrame.setVisible(true);

			}
		});
	}

	/**
	 * Starts the main GUI window (frame) after the simulation was started.
	 * This can be achieved by pressing "O" on keyboard.
	 * @param simulation, the basic graphical simulation.
	 */
	public void activateDuringSimulation(){
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {            	
				mainFrame = new MainFrame(jmeSimulation,tabs);
				mainFrame.setVisible(true);
				jmeSimulation.setPause(true);
			}
		});
	}	

	/**
	 * Starts the main GUI window (frame) stand alone.
	 * @param args
	 */
	public static void main(String[] args) {
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				mainFrame = new MainFrame();				
				mainFrame.setVisible(true);
			}
		});
	}

	/**
	 * Returns true if the frame(main GUI window) is already instantiated.
	 * @return true, if the frame(main GUI window) is already instantiated.
	 */
	public static boolean isInstanceFlag() {
		return instanceFlag;
	}


	/*Declaration of MainFrame components*/
	private javax.swing.JMenuBar jMenuBar1;

	private javax.swing.JMenu jMenu1;
	private javax.swing.JMenu jMenu2;
	private javax.swing.JMenu jMenu3;
	private javax.swing.JMenu jMenu4;	

	private javax.swing.JMenuItem jMenuItem1;
	private static javax.swing.JMenuItem jMenuItem2;
	private static javax.swing.JMenuItem jMenuItem3;
	private javax.swing.JMenuItem jMenuItem4;

	private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem1;
	private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem2;
	private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem3;
	private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem4;
	private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem5;
	private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem6;	
	private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItemNew;

	private javax.swing.JSeparator jSeparator1;
	private javax.swing.JSeparator jSeparator2;

	private javax.swing.JToolBar jToolBar1;
	private javax.swing.JToolBar jToolBar2;

	private javax.swing.JButton jButton1;
	private javax.swing.JButton jButton2;
	private static javax.swing.JButton jButton3;

	private javax.swing.JButton jButton6;
	private javax.swing.JButton jButton7;

	private javax.swing.JLabel jLabel1;
	private javax.swing.JTextField jTextField1;

	private static javax.swing.JTabbedPane jTabbedPane1  = new javax.swing.JTabbedPane();
	private static javax.swing.JTabbedPane jTabbedPane3 = new javax.swing.JTabbedPane();

	private javax.swing.JTextArea jTextArea1;
	private javax.swing.JScrollPane jScrollPane1;

}
