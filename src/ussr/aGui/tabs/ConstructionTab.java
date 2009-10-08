package ussr.aGui.tabs;

import java.util.ArrayList;

import javax.swing.JTabbedPane;

import ussr.physics.jme.JMESimulation;

public class ConstructionTab extends Tabs {

	private static ArrayList<javax.swing.JComboBox> comboBoxes =  new ArrayList<javax.swing.JComboBox>();
	

	public ConstructionTab(/*String tabTitle,*/JMESimulation jmeSimulation){
		//this.jTabbedPane = jTabbedPane;
		//this.jTabbedPane = new javax.swing.JTabbedPane();
		this.jPanel1000 = new javax.swing.JPanel();
		//this.tabTitle = tabTitle;
		this.jmeSimulation = jmeSimulation;
		initComponents();
	}

	/* (non-Javadoc)
	 * @see ussr.aGui.tabs.Tabs#initComponents()
	 */
	public void initComponents(){
		jComboBox2 = new javax.swing.JComboBox();
		comboBoxes.add(jComboBox2);
		jComboBox3 = new javax.swing.JComboBox();		
		comboBoxes.add(jComboBox3);

		jLabel1000 = new javax.swing.JLabel();
		jPanel1000 = new javax.swing.JPanel();
		
		jComboBox1000 = new javax.swing.JComboBox();
		comboBoxes.add(jComboBox1000);

		jLabel1000.setText("1) Choose modular robot:");
		jPanel1000.add(jLabel1000);

		jComboBox1000.setToolTipText("Supported modular robots");
		jComboBox1000.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "ATRON", "ODIN", "MTRAN", "CKBOT" }));
		jComboBox1000.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				ConstructionTabController.jComboBox1ActionPerformed(jComboBox1000,jmeSimulation);
			}
		});
		jPanel1000.add(jComboBox1000);

		javax.swing.JLabel jLabel999 = new javax.swing.JLabel();
		jLabel999.setText(" and locate it in the simulation environment;");
		jPanel1000.add(jLabel999);

		javax.swing.JLabel jLabel3 = new javax.swing.JLabel();
		jLabel3.setText("2) Choose module rotation (in the one of the following three components)");
		jPanel1000.add(jLabel3);       

		jComboBox2.setToolTipText("Standard rotations of modules");
		jComboBox2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "" }));
		jComboBox2.setEnabled(false);
		jComboBox2.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				ConstructionTabController.jComboBox2ActionPerformed(jmeSimulation);
			}
		});
		jPanel1000.add(jComboBox2);

		javax.swing.JButton jButton5 = new javax.swing.JButton();
		jButton5.setToolTipText("Rotate opposite");        
		jButton5.setText("Opposite");        
		jButton5.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				ConstructionTabController.jButton5ActionPerformed(jmeSimulation);
			}
		});
		jPanel1000.add(jButton5);

		javax.swing.JButton jButton6 = new javax.swing.JButton();
		jButton6.setToolTipText("Variation of module rotations");
		jButton6.setText("Variation");
		jButton6.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				ConstructionTabController.jButton6ActionPerformed(jmeSimulation);
			}
		});
		jPanel1000.add(jButton6);

		javax.swing.JLabel jLabel998 = new javax.swing.JLabel();
		jLabel998.setText(" and select the module in the simulation environment to apply it to;");
		jPanel1000.add(jLabel998);

		javax.swing.JLabel jLabel997 = new javax.swing.JLabel();
		jLabel997.setText(" 3)Choose one of the following three construction tools:");
		jPanel1000.add(jLabel997);
		
		javax.swing.JButton jButton7 = new javax.swing.JButton();
		jButton7.setToolTipText("Variation of module rotations");
		jButton7.setText("On Selected Connector");
		jButton7.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				ConstructionTabController.jButton7ActionPerformed(jmeSimulation);
			}
		});
		jPanel1000.add(jButton7);
		
		javax.swing.JLabel jLabel996 = new javax.swing.JLabel();
		jLabel996.setText("here select connector on the module(white or black geometric shapes) ");
		jPanel1000.add(jLabel996);
		
		
		jComboBox3.setToolTipText("Numbers of connectors on the module");
		jComboBox3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "" }));
		jComboBox3.setEnabled(false);
		jComboBox3.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				//ConstructionTabController.jComboBox3ActionPerformed(jComboBox1000,jmeSimulation);
			}
		});
		jPanel1000.add(jComboBox3);
		
		
		/*javax.swing.JButton jButton8 = new javax.swing.JButton();
		jButton8.setToolTipText("Variation of module rotations");
		jButton8.setText("On Selected Connector");
		jButton8.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				ConstructionTabController.jButton8ActionPerformed(jmeSimulation);
			}
		});
		jPanel1000.add(jButton7);*/

		//this.jTabbedPane.add(jPanel1000);
	}
	
	public static void setEnabled(boolean state){
		for (int index=0; index<comboBoxes.size(); index++){
			comboBoxes.get(index).setEnabled(false);
		}
		
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
