package ussr.aGui;

/**
*
* @author Konstantinas
*/
public class MainFrame extends GuiFrame {

   
   private javax.swing.JMenu jMenu1;
   private javax.swing.JMenu jMenu2;
   private javax.swing.JMenuBar jMenuBar1;
   private javax.swing.JMenuItem jMenuItem1;
   private javax.swing.JMenuItem jMenuItem2;
   private javax.swing.JMenuItem jMenuItem3;
   private javax.swing.JSeparator jSeparator1;
   private javax.swing.JSeparator jSeparator2;
   
   public MainFrame() {
       initComponents();
   }

private void initComponents() {

       jMenuBar1 = new javax.swing.JMenuBar();
       jMenu1 = new javax.swing.JMenu();
       jMenuItem2 = new javax.swing.JMenuItem();
       jSeparator2 = new javax.swing.JSeparator();
       jMenuItem3 = new javax.swing.JMenuItem();
       jSeparator1 = new javax.swing.JSeparator();
       jMenuItem1 = new javax.swing.JMenuItem();
       jMenu2 = new javax.swing.JMenu();

       setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
       setTitle("Unified Simulator for Self-Reconfigurable Robots");
       getContentPane().setLayout(new java.awt.FlowLayout());

       jMenu1.setText("File");

       jMenuItem2.setText("Open");
       jMenu1.add(jMenuItem2);
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
   }


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
               new MainFrame() ;
            }
        });

	}

}
