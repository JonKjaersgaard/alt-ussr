package ussr.aGui.tabs.additionalResources;

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.util.EventObject;

import javax.swing.AbstractCellEditor;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

public class Editors extends AbstractCellEditor implements TableCellEditor{

	@Override
	public Component getTableCellEditorComponent(JTable arg0, Object arg1,
			boolean arg2, int arg3, int arg4) {
		return null;
	}

	@Override
	public Object getCellEditorValue() {
		return null;
	}
	
	// Enables the editor only for double-clicks.
	public boolean isCellEditable(EventObject evt) {
		if (evt instanceof MouseEvent) {
			return ((MouseEvent)evt).getClickCount() >= 1;
		}
		return true;
	}

}
