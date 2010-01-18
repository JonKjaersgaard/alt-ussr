package ussr.aGui.designHelpers;


import ussr.aGui.enumerations.MainFrameComponentsText;
import ussr.aGui.enumerations.MainFrameIcons;
import ussr.aGui.fileChoosing.FileChoosingInter;

public class JComponentsFactory {

	
	
	/**
	 * Creates new label with specified text.
	 * @param labelText, the text of the label
	 * @return new label with specified text.
	 */
	public static javax.swing.JLabel createNewLabel(String labelText){
		javax.swing.JLabel newLabel =  new javax.swing.JLabel();
		newLabel.setText(labelText+" ");
		return newLabel;
	}
	
	
	/**
	 * Initializes and returns the button called Save.
	 * 
	 * @return the button called Save.
	 */
	public static javax.swing.JButton initSaveButton(){
		javax.swing.JButton jButtonSave = new javax.swing.JButton();		
		jButtonSave.setToolTipText(MainFrameComponentsText.SAVE.getUserFriendlyName());		
		jButtonSave.setIcon(MainFrameIcons.SAVE.getImageIcon());
		jButtonSave.setRolloverIcon(MainFrameIcons.SAVE_ROLLOVER.getImageIcon());
		jButtonSave.setDisabledIcon(MainFrameIcons.SAVE_DISABLED.getImageIcon());		
		jButtonSave.setFocusable(false);
		jButtonSave.setPreferredSize(new java.awt.Dimension(30, 30));	
		return jButtonSave;
	}
	
	
	/**
	 * Initializes and returns the button called Open.
	 * @param fcOpenFrame,the file chooser frame to associate the button with.
	 * @return the button called Open.
	 */
	public static javax.swing.JButton  initOpenButton(){
		javax.swing.JButton jButtonOpen = new javax.swing.JButton();
		jButtonOpen.setToolTipText(MainFrameComponentsText.OPEN.getUserFriendlyName());
		
		jButtonOpen.setIcon(MainFrameIcons.OPEN.getImageIcon());
		jButtonOpen.setRolloverIcon(MainFrameIcons.OPEN_ROLLOVER.getImageIcon());		
		jButtonOpen.setDisabledIcon(MainFrameIcons.OPEN_DISABLED.getImageIcon());
		
		jButtonOpen.setFocusable(false);		
		jButtonOpen.setPreferredSize(new java.awt.Dimension(30, 30));	
		return jButtonOpen;
	}
	
	
	
}
