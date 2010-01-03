package ussr.aGui.designHelpers;

public class JComponentsFactoryHelper {

	
	
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
}
