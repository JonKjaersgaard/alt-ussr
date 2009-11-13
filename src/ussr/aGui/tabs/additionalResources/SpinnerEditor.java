package ussr.aGui.tabs.additionalResources;

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.util.EventObject;

import javax.swing.AbstractCellEditor;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.SpinnerListModel;
import javax.swing.table.TableCellEditor;

public class SpinnerEditor extends Editors {
	final JSpinner spinner = new JSpinner();

	// Initializes the spinner.
	public SpinnerEditor() {
		spinner.setModel(new javax.swing.SpinnerNumberModel(0, 0, 1000, 5));
	}

	// Prepares the spinner component and returns it.
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		//spinner.setValue(value);
		return spinner;
	}

	// Returns the spinners current value.
	public Object getCellEditorValue() {
		return spinner.getValue();
	}
}

