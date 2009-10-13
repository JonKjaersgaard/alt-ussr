package ussr.aGui.tabs;

import ussr.physics.jme.JMESimulation;

/**
 * Defines visual appearance of the tab called "1 Step: Construct Robot".  
 * @author Konstantinas
 */
public class ConstructionTab extends Tabs {

	/**
	 * Defines visual appearance of the tab called "1 Step: Construct Robot".
	 * @param tabTitle, the title of the tab
	 * @param jmeSimulation, the physical simulation.
	 * @param firstTabbedPane,
	 */
	public ConstructionTab(boolean firstTabbedPane,String tabTitle,JMESimulation jmeSimulation){
		this.firstTabbedPane = firstTabbedPane;
		this.tabTitle = tabTitle;
		this.jmeSimulation = jmeSimulation;
		/*instantiate new panel, which will be the container for all components situated in the tab*/		
		this.jComponent = new javax.swing.JPanel();
		initComponents();
	}

	/**
     * Initializes the visual appearance of all components in the tab.
     * Follows Strategy  pattern.
     */
	public void initComponents(){
		
		/*Instantiation of tab components*/
		jComboBox2 = new javax.swing.JComboBox();		
		jComboBox3 = new javax.swing.JComboBox();		
		jComboBox1000 = new javax.swing.JComboBox();

		jLabel1000 = new javax.swing.JLabel();	
		
		/*Definition of visual appearance for each instantiated component*/
		jLabel1000.setText("1) Choose modular robot:");
		/*Add your component into panel.*/
		jComponent.add(jLabel1000);

		jComboBox1000.setToolTipText("Supported modular robots");
		jComboBox1000.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "ATRON", "ODIN", "MTRAN", "CKBOT" }));
		jComboBox1000.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				ConstructionTabController.jComboBox1ActionPerformed(jComboBox1000,jmeSimulation);
			}
		});
		jComponent.add(jComboBox1000);

		javax.swing.JLabel jLabel999 = new javax.swing.JLabel();
		jLabel999.setText(" and locate it in the simulation environment;");
		jComponent.add(jLabel999);

		javax.swing.JLabel jLabel3 = new javax.swing.JLabel();
		jLabel3.setText("2) Choose module rotation (in the one of the following three components)");
		jComponent.add(jLabel3);       

		jComboBox2.setToolTipText("Standard rotations of modules");
		jComboBox2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "" }));
		jComboBox2.setEnabled(false);
		jComboBox2.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				ConstructionTabController.jComboBox2ActionPerformed(jmeSimulation);
			}
		});
		jComponent.add(jComboBox2);

		javax.swing.JButton jButton5 = new javax.swing.JButton();
		jButton5.setToolTipText("Rotate opposite");        
		jButton5.setText("Opposite");        
		jButton5.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				ConstructionTabController.jButton5ActionPerformed(jmeSimulation);
			}
		});
		jComponent.add(jButton5);

		javax.swing.JButton jButton6 = new javax.swing.JButton();
		jButton6.setToolTipText("Variation of module rotations");
		jButton6.setText("Variation");
		jButton6.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				ConstructionTabController.jButton6ActionPerformed(jmeSimulation);
			}
		});
		jComponent.add(jButton6);

		javax.swing.JLabel jLabel998 = new javax.swing.JLabel();
		jLabel998.setText(" and select the module in the simulation environment to apply it to;");
		jComponent.add(jLabel998);

		javax.swing.JLabel jLabel997 = new javax.swing.JLabel();
		jLabel997.setText(" 3)Choose one of the following three construction tools:");
		jComponent.add(jLabel997);
		
		javax.swing.JButton jButton7 = new javax.swing.JButton();
		jButton7.setToolTipText("Variation of module rotations");
		jButton7.setText("On Selected Connector");
		jButton7.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				ConstructionTabController.jButton7ActionPerformed(jmeSimulation);
			}
		});
		jComponent.add(jButton7);
		
		javax.swing.JLabel jLabel996 = new javax.swing.JLabel();
		jLabel996.setText("here select connector on the module(white or black geometric shapes) ");
		jComponent.add(jLabel996);
		
		
		jComboBox3.setToolTipText("Numbers of connectors on the module");
		jComboBox3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "" }));
		jComboBox3.setEnabled(false);
		jComboBox3.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				//ConstructionTabController.jComboBox3ActionPerformed(jComboBox1000,jmeSimulation);
			}
		});
		jComponent.add(jComboBox3);
		
		
		/*javax.swing.JButton jButton8 = new javax.swing.JButton();
		jButton8.setToolTipText("Variation of module rotations");
		jButton8.setText("On Selected Connector");
		jButton8.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				ConstructionTabController.jButton8ActionPerformed(jmeSimulation);
			}
		});
		jPanel1000.add(jButton7);*/
	}
	

	public static javax.swing.JComboBox getJComboBox2() {
		return jComboBox2;
	}
	
	public static javax.swing.JComboBox getJComboBox3() {
		return jComboBox3;
	}

	
	/*Declaration of tab components*/
    private static javax.swing.JComboBox jComboBox2;	
	private static javax.swing.JComboBox jComboBox3;
	private static javax.swing.JLabel jLabel1000;
	private static javax.swing.JComboBox jComboBox1000;




}
