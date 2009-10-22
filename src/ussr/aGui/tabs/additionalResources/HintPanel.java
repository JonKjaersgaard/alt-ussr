package ussr.aGui.tabs.additionalResources;

import java.awt.Dimension;

import javax.swing.GroupLayout;
import javax.swing.JPanel;
import ussr.aGui.tabs.TabsInter;

public class HintPanel extends JPanel implements HintPanelInter {

	/**
	 * WHAT IS THAT?
	 */
	private static final long serialVersionUID = 1L;
	
	private int width,height;

	public HintPanel(int width, int height){
		this.width = width;
		this.height = height;
		initComponents();
		}
	
	private void initComponents() {			 		 
		 setPreferredSize(new Dimension(width,height));
		 
		 jLabel10001 =  new javax.swing.JLabel();
		 jLabel10001.setIcon(new javax.swing.ImageIcon(TabsInter.DIRECTORY_ICONS + TabsInter.INFORMATION ));
		 //add(jLabel10001);
		 
		 jScrollPane2 = new javax.swing.JScrollPane();
		 
		 jTextPane1 = new javax.swing.JTextPane();		
		 jTextPane1.setPreferredSize(new Dimension(width-50,height-35));
		 jTextPane1.setEditable(false);
		 
		 jScrollPane2.setViewportView(jTextPane1);		 
		 //add(jScrollPane2);
		 
		 /*Internal layout of the panel*/
			GroupLayout jPanelLayout = new GroupLayout(this);
			this.setLayout(jPanelLayout);
 
			jPanelLayout.setAutoCreateGaps(true);
			jPanelLayout.setHorizontalGroup(
					jPanelLayout.createSequentialGroup()
					//Forces preferred side of component
					.addComponent(jLabel10001,GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
							GroupLayout.PREFERRED_SIZE)
							.addComponent(jScrollPane2,GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
									GroupLayout.PREFERRED_SIZE)				
			);

			jPanelLayout.setVerticalGroup(
					jPanelLayout.createSequentialGroup()
					.addGroup(jPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
							.addComponent(jLabel10001,GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
									GroupLayout.PREFERRED_SIZE)
									.addComponent(jScrollPane2,GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
											GroupLayout.PREFERRED_SIZE))						
			);
		 
		 
		 
	}

	
	
	public void setBorderTitle(String borderTitle){
		setBorder(javax.swing.BorderFactory.createTitledBorder(borderTitle));
	}
	
	public void setType(HintPanelTypesOfIcons typeOfIcon){
		 jLabel10001.setIcon(new javax.swing.ImageIcon(TabsInter.DIRECTORY_ICONS + typeOfIcon.toString().toLowerCase() + ".jpg" ));
	}
	
	public void setText(String hintText){	
		jTextPane1.setText(hintText);	
	}
	
	private javax.swing.JLabel jLabel10001;
	private javax.swing.JTextPane jTextPane1;
	private javax.swing.JScrollPane jScrollPane2;
}
