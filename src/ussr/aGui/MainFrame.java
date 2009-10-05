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
import ussr.samples.atron.simulations.ATRONSnakeSimulation;

import com.jme.system.DisplaySystem;

/**
 *
 * @author Konstantinas
 */
public class MainFrame extends GuiFrames {


	private ArrayList<JToolBar> toolBars = new ArrayList<JToolBar>() ;

	private static MainFrame mainFrame;

	private javax.swing.JMenu jMenu1;
	private javax.swing.JMenu jMenu2;
	private javax.swing.JMenuBar jMenuBar1;
	private javax.swing.JMenuItem jMenuItem1;
	private javax.swing.JMenuItem jMenuItem2;
	private javax.swing.JMenuItem jMenuItem3;
	private javax.swing.JMenuItem jMenuItem4;
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

	public MainFrame() {
		initComponents();
		//setSizeFullScreen(this);
		setSizeHalfScreen(this);
		changeToSetLookAndFeel(this);// makes troubles with the borders of the buttons 		
	}

	protected void initComponents() {
		jMenuBar1 = new javax.swing.JMenuBar();
		jMenu1 = new javax.swing.JMenu();
		jMenuItem2 = new javax.swing.JMenuItem();
		jMenuItem4 = new javax.swing.JMenuItem();
		jSeparator2 = new javax.swing.JSeparator();
		jMenuItem3 = new javax.swing.JMenuItem();
		jSeparator1 = new javax.swing.JSeparator();
		jMenuItem1 = new javax.swing.JMenuItem();
		jMenu2 = new javax.swing.JMenu();

		jToolBar1 = new javax.swing.JToolBar();
		jToolBar2 = new javax.swing.JToolBar();

		jTabbedPane1 = new javax.swing.JTabbedPane();
		jTabbedPane2 = new javax.swing.JTabbedPane();
		jTabbedPane3 = new javax.swing.JTabbedPane();
		jTabbedPane4 = new javax.swing.JTabbedPane();

		jButton1 = new javax.swing.JButton();

		jLabel1 = new javax.swing.JLabel();
		jTextField1 = new javax.swing.JTextField();       

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
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
				MainFrameController.jButton1ActionPerformed(jButton1);        	  
			}
		});
		jToolBar2.add(jButton1);       

		getContentPane().add(jToolBar2);


		jTabbedPane1.setPreferredSize(new Dimension((int)SCREEN_DIMENSION.getWidth()/2-PADDING, 200));
		jTabbedPane1.addTab("1 Step: Contruct Robot", jTabbedPane2);
		jTabbedPane1.addTab("2 Step: Assign Behavior", jTabbedPane3);
		jTabbedPane1.addTab("tab3", jTabbedPane4);

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

		setJMenuBar(jMenuBar1);

		pack();         
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


	public void activate(){
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {            	
				mainFrame = new MainFrame();
				mainFrame.setVisible(true);
			}
		});
	}

	/**
	 * @param args
	 *//*
	public static void main(String[] args) {
	    mainFrame = new MainFrame();		
	}*/

}
