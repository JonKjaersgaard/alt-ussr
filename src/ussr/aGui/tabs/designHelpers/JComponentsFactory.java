package ussr.aGui.tabs.designHelpers;


import ussr.aGui.FramesInter;
import ussr.aGui.enumerations.MainFrameComponentsText;
import ussr.aGui.enumerations.MainFrameIcons;
import ussr.aGui.enumerations.tabs.TabsComponentsText;
import ussr.aGui.enumerations.tabs.TabsIcons;

/**
 * Contains methods for initialization of JComponents frequently used in the design of GUI.
 * @author Konstantinas
 *
 */
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
	 * @return the button called Save.
	 */
	public static javax.swing.JButton initSaveButton(){
		javax.swing.JButton jButtonSave = new javax.swing.JButton();		
		jButtonSave.setToolTipText(MainFrameComponentsText.SAVE.getUserFriendlyName());		
		jButtonSave.setIcon(MainFrameIcons.SAVE.getImageIcon());
		jButtonSave.setRolloverIcon(MainFrameIcons.SAVE_ROLLOVER.getImageIcon());
		jButtonSave.setDisabledIcon(MainFrameIcons.SAVE_DISABLED.getImageIcon());		
		jButtonSave.setFocusable(false);
		jButtonSave.setPreferredSize(FramesInter.BUTTON_DIMENSION);	
		return jButtonSave;
	}
	
	
	/**
	 * Initializes and returns the button called Open.
	 * @return the button called Open.
	 */
	public static javax.swing.JButton  initOpenButton(){
		javax.swing.JButton jButtonOpen = new javax.swing.JButton();
		jButtonOpen.setToolTipText(MainFrameComponentsText.OPEN.getUserFriendlyName());
		
		jButtonOpen.setIcon(MainFrameIcons.OPEN.getImageIcon());
		jButtonOpen.setRolloverIcon(MainFrameIcons.OPEN_ROLLOVER.getImageIcon());		
		jButtonOpen.setDisabledIcon(MainFrameIcons.OPEN_DISABLED.getImageIcon());
		
		jButtonOpen.setFocusable(false);		
		jButtonOpen.setPreferredSize(FramesInter.BUTTON_DIMENSION);	
		return jButtonOpen;
	}
	
	/**
	 * Initializes and returns the button for coloring connectors on the modules.
	 * @return the togglebutton called Color Connectors.
	 */
	public static javax.swing.JToggleButton  initColorConnectorsOfModulesButton(){

		 javax.swing.JToggleButton  jToggleButtonColorConnetors = new javax.swing.JToggleButton(); 
		jToggleButtonColorConnetors.setToolTipText(TabsComponentsText.COLOR_MODULE_CONNECTORS.getUserFriendlyName());
		jToggleButtonColorConnetors.setIcon(TabsIcons.COLOR_CONNECTORS.getImageIcon());
		jToggleButtonColorConnetors.setSelectedIcon(TabsIcons.COLOR_CONNECTORS.getImageIcon());
		jToggleButtonColorConnetors.setRolloverIcon(TabsIcons.COLOR_CONNECTORS_ROLLOVER.getImageIcon());
		jToggleButtonColorConnetors.setDisabledIcon(TabsIcons.COLOR_CONNECTORS_DISABLED.getImageIcon());		
		jToggleButtonColorConnetors.setFocusable(false);
		jToggleButtonColorConnetors.setEnabled(true);
		jToggleButtonColorConnetors.setPreferredSize(FramesInter.BUTTON_DIMENSION);
		
		return jToggleButtonColorConnetors;
	}
}
