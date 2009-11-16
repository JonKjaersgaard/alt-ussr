package ussr.aGui.tabs.additionalResources;



import java.awt.Component;
import java.awt.event.MouseEvent;
import java.util.EventObject;

import javax.swing.AbstractCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.SpinnerListModel;
import javax.swing.table.TableCellEditor;

public class CheckBoxEditor extends Editors {
	final JCheckBox jCheckBox = new JCheckBox();

	// Initializes the spinner.
	public CheckBoxEditor() {
		jCheckBox.setSelected(false);
		jCheckBox.setText("");
	}

	// Prepares the spinner component and returns it.
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		//spinner.setValue(value);
		return jCheckBox;
	}

	// Returns the spinners current value.
	public Object getCellEditorValue() {
		return jCheckBox.isSelected();
	}
}