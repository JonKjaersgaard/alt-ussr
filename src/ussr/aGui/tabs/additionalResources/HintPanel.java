package ussr.aGui.tabs.additionalResources;

import javax.swing.JPanel;
import ussr.aGui.tabs.TabsInter;

public class HintPanel extends JPanel implements HintPanelInter {

	/**
	 * WHAT IS THAT?
	 */
	private static final long serialVersionUID = 1L;

	public HintPanel(){
	
		initComponents();
	}
	
	private void initComponents() {			 		 
		 jLabel10001 = new javax.swing.JLabel();			 
		 add(jLabel10001);
		 setVisible(true);	
	}

	
	
	public void setBorderTitle(String borderTitle){
		setBorder(javax.swing.BorderFactory.createTitledBorder(borderTitle));
	}
	
	public void setType(HintPanelTypesOfIcons typeOfIcon){
		 jLabel10001.setIcon(new javax.swing.ImageIcon(TabsInter.DIRECTORY_ICONS + typeOfIcon.toString().toLowerCase() + ".jpg" ));
	}
	
	public void setText(String hintText){
		
		if (hintText.length()>30){
			//TODO SPLIT THE STRING, SO THAT IT WOULD BE POSSIBLE TO GIVE LONGER HINTS.
			/*jLabel10001.setText(hintText.)
			javax.swing.JLabel jLabel100 = new javax.swing.JLabel();			
			this.add(jLabel100);*/
		}
		jLabel10001.setText(hintText);	
	}
	
	private javax.swing.JLabel jLabel10001;
}
