package ussr.aGui.helpers;

import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

class ComboBoxRenderer extends JLabel implements ListCellRenderer {

	
	
//	public ComboBoxRenderer(ImageIcon[] images,){}
	
	public ComboBoxRenderer() {
		setOpaque(true);
		setHorizontalAlignment(CENTER);
		setVerticalAlignment(CENTER);
	}

	/*
	 * This method finds the image and text corresponding
	 * to the selected value and returns the label, set up
	 * to display the text and image.
	 */
	public Component getListCellRendererComponent(JList list,Object value, int index,boolean isSelected, boolean cellHasFocus) {
		//Get the selected index. (The index param isn't
		//always valid, so just use the value.)
		int selectedIndex = ((Integer)value).intValue();

		if (isSelected) {
			setBackground(list.getSelectionBackground());
			setForeground(list.getSelectionForeground());
		} else {
			setBackground(list.getBackground());
			setForeground(list.getForeground());
		}

		//Set the icon and text.  If icon was null, say so.
	/*	ImageIcon icon = images[selectedIndex];
		String pet = petStrings[selectedIndex];
		setIcon(icon);
		if (icon != null) {
			setText(pet);
			setFont(list.getFont());
		} else {
			//setUhOhText(pet + " (no image available)",
				//	list.getFont());
		}*/

		return this;
	}

}


