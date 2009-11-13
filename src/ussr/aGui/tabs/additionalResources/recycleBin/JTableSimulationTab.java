package ussr.aGui.tabs.additionalResources.recycleBin;

import javax.swing.*;
import javax.swing.table.*;
import java.util.Vector;


public class JTableSimulationTab extends JTable
{
	protected RowEditorModel rowEditorModel;

	public JTableSimulationTab()
	{
		super();
		rowEditorModel = null;
	}

	public JTableSimulationTab(TableModel tm)
	{
		super(tm);
		rowEditorModel = null;
	}

	public JTableSimulationTab(TableModel tm, TableColumnModel cm)
	{
		super(tm,cm);
		rowEditorModel = null;
	}

	public JTableSimulationTab(TableModel tableModel, TableColumnModel cm,
			ListSelectionModel sm)
	{
		super(tableModel,cm,sm);
		rowEditorModel = null;
	}

	public JTableSimulationTab(int rows, int cols)
	{
		super(rows,cols);
		rowEditorModel = null;
	}

	public JTableSimulationTab(final Vector rowData, final Vector columnNames)
	{
		super(rowData, columnNames);
		rowEditorModel = null;
	}

	public JTableSimulationTab(final Object[][] rowData, final Object[] colNames)
	{
		super(rowData, colNames);
		rowEditorModel = null;
	}

	// new constructor
	public JTableSimulationTab(TableModel tm, RowEditorModel rm)
	{
		super(tm,null,null);
		this.rowEditorModel = rm;
	}

	public void setRowEditorModel(RowEditorModel rm)
	{
		this.rowEditorModel = rm;
	}

	public RowEditorModel getRowEditorModel()
	{
		return rowEditorModel;
	}

	public TableCellEditor getCellEditor(int row, int col)
	{
		TableCellEditor tmpEditor = null;
		if (rowEditorModel!=null)
			tmpEditor = rowEditorModel.getEditor(row);
		if (tmpEditor!=null)
			return tmpEditor;
		return super.getCellEditor(row,col);
	}
}



