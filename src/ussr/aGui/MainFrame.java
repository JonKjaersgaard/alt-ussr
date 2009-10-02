package ussr.aGui;

import java.awt.Dimension;
import java.awt.Frame;
import java.util.ArrayList;

import javax.swing.JToolBar;
import javax.swing.SwingWorker;

import ussr.builder.BuilderMultiRobotSimulation;

import com.jme.system.DisplaySystem;

/**
*
* @author Konstantinas
*/
public class MainFrame extends GuiFrame {

	
   private ArrayList<JToolBar> toolBars = new ArrayList<JToolBar>() ;	
	
   private javax.swing.JMenu jMenu1;
   private javax.swing.JMenu jMenu2;
   private javax.swing.JMenuBar jMenuBar1;
   private javax.swing.JMenuItem jMenuItem1;
   private javax.swing.JMenuItem jMenuItem2;
   private javax.swing.JMenuItem jMenuItem3;
   private javax.swing.JMenuItem jMenuItem4;
   private javax.swing.JSeparator jSeparator1;
   private javax.swing.JSeparator jSeparator2;
   private javax.swing.JToolBar jToolBar1;
   private javax.swing.JLabel jLabel1;
   private javax.swing.JTextField jTextField1;
   
   public MainFrame() {
       initComponents();
   }

private void initComponents() {

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
       jLabel1 = new javax.swing.JLabel();
       jTextField1 = new javax.swing.JTextField();
       

       setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
       setTitle("Unified Simulator for Self-Reconfigurable Robots");
       getContentPane().setLayout(new java.awt.FlowLayout());
       
       jToolBar1.setFloatable(false);//user can not make the tool bar to float
       jToolBar1.setRollover(true);// the buttons inside are roll over
       jToolBar1.setPreferredSize(new java.awt.Dimension(100,40));
       toolBars.add(jToolBar1);
       getContentPane().add(jToolBar1);
       
       jLabel1.setText("CML");
       jToolBar1.add(jLabel1);
       
       jTextField1.setText("");
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
        	   MainFrameController.jMenuItem4ActionPerformed(evt);
           }
       });
       jMenu1.add(jMenuItem4);      
       
       jMenu1.add(jSeparator2);

       jMenuItem3.setText("Save");
       jMenu1.add(jMenuItem3);
       jMenu1.add(jSeparator1);

       jMenuItem1.setText("Exit");
       jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
           public void actionPerformed(java.awt.event.ActionEvent evt) {
               MainFrameController.jMenuItem1ActionPerformed(evt);
           }
       });
       jMenu1.add(jMenuItem1);

       jMenuBar1.add(jMenu1);

       jMenu2.setText("View");
       jMenuBar1.add(jMenu2);
       

       setJMenuBar(jMenuBar1);

       pack();      
       setSizeHalfScreen(this);
       changeToSetLookAndFeel(this);       
       setVisible(true);
      // adjustSizeToolbars();
   }
/*STOPPED HERE*/
private void adjustSizeToolbars(){
	 toolBars.get(0).setPreferredSize(dimension);
}


	/**
	 * @param args
	 */
	public static void main(String[] args) {
	/*	SwingWorker swing = new SwingWorker(){

			@Override
			protected Object doInBackground() throws Exception {
				
				return new MainFrame() ;
			}
			
		}; */
		
		java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
            	
            	new MainFrame() ;
            	
               
            }
        });

	}

}
