package ussr.aGui;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JToolBar;
import javax.swing.SwingWorker;

import ussr.builder.BuilderMultiRobotSimulation;
import ussr.physics.jme.JMEBasicGraphicalSimulation;
import ussr.physics.jme.JMESimulation;
import ussr.samples.atron.simulations.ATRONSnakeSimulation;

import com.jme.system.DisplaySystem;

/**
 *
 * @author Konstantinas
 */
public class MainFrame extends GuiFrames implements MainFrameInter{

	/**
	 * The physical simulation
	 */	   
	private JMESimulation jmeSimulation;
	
	private ArrayList<JToolBar> toolBars = new ArrayList<JToolBar>() ;

	private static MainFrame mainFrame;

	private javax.swing.JMenuBar jMenuBar1;
	
	private javax.swing.JMenu jMenu1;
	private javax.swing.JMenu jMenu2;
	private javax.swing.JMenu jMenu3;
	
	
	private javax.swing.JMenuItem jMenuItem1;
	private javax.swing.JMenuItem jMenuItem2;
	private javax.swing.JMenuItem jMenuItem3;
	private javax.swing.JMenuItem jMenuItem4;
	
	private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem1;
	private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem2;
	private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem3;
	private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem4;
	private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem5;
	private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem6;
	
	 
	private javax.swing.JSeparator jSeparator1;
	private javax.swing.JSeparator jSeparator2;
	
	
	
	
	/*TOOL_BARS*/
	private javax.swing.JToolBar jToolBar1;
	private javax.swing.JToolBar jToolBar2;

	private javax.swing.JButton jButton1;

	private javax.swing.JLabel jLabel1;
	private javax.swing.JTextField jTextField1;

	private javax.swing.JTabbedPane jTabbedPane1;
	private javax.swing.JTabbedPane jTabbedPane2;
	private javax.swing.JTabbedPane jTabbedPane3;
	private javax.swing.JTabbedPane jTabbedPane4;

	private boolean constructionDisplayed;
	//private boolean controllerDisplayed;

	public MainFrame() {
		initComponents();				
	}

	public MainFrame(boolean constructionDisplayed) {
		this.constructionDisplayed = constructionDisplayed;
		initComponents();				
	}
	
	
	/**
	 * Instance flag for current frame, used to keep track that only one instance of the frame is instantiated.
	 */
	private static boolean instanceFlag = false;
	
	/**
	 * 
	 * Starts the main GUI window (frame) during the simulation.
	 * This can be achieved by pressing "O" on keyboard. 
	 * @param JMESimulation, the physical simulation
	 */
	public MainFrame(JMEBasicGraphicalSimulation jmeSimulation){
		this.jmeSimulation = (JMESimulation) jmeSimulation;
		
		initComponents();		
		instanceFlag = true;// the frame is instantiated
		// Overrides event for closing the frame, in order for the frame to do not open several times with several times pressing on the button "O" on keyboard.
		addWindowListener (new WindowAdapter() {			
			public void windowClosing(WindowEvent event) {
				instanceFlag = false; // reset the flag after closing the frame
				event.getWindow().dispose();	                     
			}
		}
		);		
	}
	
	/**
	 * Returns true if the frame(main GUI window) is already instantiated.
	 * @return true, if the frame(main GUI window) is already instantiated.
	 */
	public static boolean isInstanceFlag() {
		return instanceFlag;
	}

	/*	public MainFrame(boolean constructionDisplayed, boolean controllerDisplayed) {
		this.constructionDisplayed = constructionDisplayed;
		this.controllerDisplayed = controllerDisplayed;
	}*/

	/* (non-Javadoc)
	 * @see ussr.aGui.GuiFrames#initComponents()
	 */
	protected void initComponents() {
		jMenuBar1 = new javax.swing.JMenuBar();
		
		jMenu1 = new javax.swing.JMenu();
		jMenu2 = new javax.swing.JMenu();
		jMenu3 = new javax.swing.JMenu();
		
		
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

		jTabbedPane1 = new javax.swing.JTabbedPane();
		jTabbedPane2 = new javax.swing.JTabbedPane();
		jTabbedPane3 = new javax.swing.JTabbedPane();
		jTabbedPane4 = new javax.swing.JTabbedPane();

		jButton1 = new javax.swing.JButton();

		jLabel1 = new javax.swing.JLabel();
		jTextField1 = new javax.swing.JTextField();       

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("Unified Simulator for Self-Reconfigurable Robots");
		getContentPane().setLayout(new java.awt.FlowLayout());


		jToolBar2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
		jToolBar2.setRollover(true);
		jToolBar2.setToolTipText("Simulation Control");
		jToolBar2.setPreferredSize(new Dimension((int)SCREEN_DIMENSION.getWidth()/2-PADDING,TOOLBAR_HEIGHT));

		jButton1.setIcon(new javax.swing.ImageIcon(DIRECTORY_ICONS + PLAY));
		jButton1.setToolTipText("Play/Pause");
		jButton1.setBorder(javax.swing.BorderFactory.createEtchedBorder());//On Vista does not work(test it)
		jButton1.setFocusable(false);    
		jButton1.setPreferredSize(new java.awt.Dimension(30, 30));      
		jButton1.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				MainFrameController.jButton1ActionPerformed(jButton1, jmeSimulation);        	  
			}
		});
		jToolBar2.add(jButton1);       

		getContentPane().add(jToolBar2);


		jTabbedPane1.setPreferredSize(new Dimension((int)SCREEN_DIMENSION.getWidth()/2-PADDING, 200));

		jTabbedPane1.addTab("1 Step: Contruct Robot", jTabbedPane2);
		if (constructionDisplayed){		

			jTabbedPane2.setFocusable(true);
		}
		//if (controllerDisplayed){
		jTabbedPane1.addTab("2 Step: Assign Behavior", jTabbedPane3);
		//}
		//jTabbedPane1.addTab("tab3", jTabbedPane4);'


		getContentPane().add(jTabbedPane1);       

		jToolBar1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
		jToolBar1.setFloatable(false);//user can not make the tool bar to float
		jToolBar1.setRollover(true);// the buttons inside are roll over
		jToolBar1.setToolTipText("Command Line Interface");
		jToolBar1.setPreferredSize(new Dimension((int)SCREEN_DIMENSION.getWidth()/2-PADDING,TOOLBAR_HEIGHT));
		toolBars.add(jToolBar1);
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
				MainFrameController.jMenuItem2ActionPerformed(evt);
			}
		});

		jMenu1.add(jMenuItem2);

		jMenuItem4.setText("Open default");
		jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				/*MainFrameController.*/jMenuItem4ActionPerformed(evt);
			}
		});
		jMenu1.add(jMenuItem4);      

		jMenu1.add(jSeparator2);

		jMenuItem3.setText("Save");
		jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				MainFrameController.jMenuItem3ActionPerformed(evt);
			}
		});

		jMenu1.add(jMenuItem3);
		jMenu1.add(jSeparator1);

		jMenuItem1.setText("Exit");
		jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				MainFrameController.jMenuItem1ActionPerformed(mainFrame);
			}
		});
		jMenu1.add(jMenuItem1);

		jMenuBar1.add(jMenu1);

		jMenu2.setText("View");
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

        jCheckBoxMenuItem5.setSelected(true);
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

		//setSizeFullScreen(this);
		setSizeHalfScreen(this);
		changeToSetLookAndFeel(this);// makes troubles with the borders of the buttons 
	}


	public javax.swing.JTabbedPane getJTabbedPane1() {
		return jTabbedPane1;
	}

	public  void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {
		//BuilderMultiRobotSimulation.main(null);

		/*Robot robot = null;
	try {
		robot = new Robot();
	} catch (AWTException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	robot.keyPress(KeyEvent.VK_O);
	robot.keyRelease(KeyEvent.VK_O);*/
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
	public void activateDuringSimulation(final JMEBasicGraphicalSimulation simulation){
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {            	
				mainFrame = new MainFrame(simulation);
				mainFrame.setVisible(true);
			}
		});
	}
	
	
	

	/**
	 * Starts the main GUI window (frame).
	 * @param args
	 */
	public static void main(String[] args) {
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				//mainFrame = new MainFrame();
				mainFrame = new MainFrame(false);				
				mainFrame.setVisible(true);
			}
		});
	}

}
