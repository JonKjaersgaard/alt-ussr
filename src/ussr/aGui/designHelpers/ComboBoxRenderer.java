package ussr.aGui.designHelpers;

import java.awt.Component;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

/**
 * Special renderer for comboBox. Supports rendering of text and icon in the same comboBox cell.
 * @author http://java.sun.com/docs/books/tutorial/uiswing/components/combobox.html 
 * @author Konstantinas modified for USSR use.
 */
public class ComboBoxRenderer extends JLabel implements ListCellRenderer {

	/**
	 * Image icons to render in the comboBox.
	 */
	private ImageIcon[] imageIcons;
	
	/**
	 * Texts to render near the icons.
	 */
	private String[]textsImages;
		
	/**
	 * Special renderer for comboBox. Supports rendering of text and icon in the same comboBox cell.
	 * @param images,image icons to render in the comboBox.
	 * @param textsImages
	 */
	public ComboBoxRenderer(ImageIcon[] imageIcons,String[]textsImages) {
		this.imageIcons = imageIcons;
		this.textsImages = textsImages;
		setHorizontalAlignment(CENTER);
		setVerticalAlignment(CENTER);
	}

	/*
	 * This method finds the image and text corresponding to the selected value and returns the label, set up
	 * to display the text and image.
	 */
	public Component getListCellRendererComponent(JList list,Object value, int index,boolean isSelected, boolean cellHasFocus) {
		/*Get the selected index.*/		
		int selectedIndex = Integer.parseInt(value.toString());
		
		/*Set the icon and text.  If icon was null, say so.*/
		ImageIcon icon = imageIcons[selectedIndex];
		String text = textsImages[selectedIndex];
		setIcon(icon);
		if (icon != null) {
			setText(text);
		} else {
			throw new Error("Image icon not found ");
		}
		return this;
	}

}


