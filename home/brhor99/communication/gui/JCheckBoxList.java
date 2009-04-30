package communication.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


public class JCheckBoxList extends JList implements ListSelectionListener {
	
	private static final long serialVersionUID = 1L;
	private HashSet<Integer> selectionCache = new HashSet<Integer>();
	protected static Border noFocusBorder = new EmptyBorder(1, 1, 1, 1);

	public JCheckBoxList() {
		super();
		setCellRenderer(new CheckBoxCellRenderer());
		addListSelectionListener(this);

		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				int index = locationToIndex(e.getPoint());
				if (index != -1) {
					JCheckBox checkbox = (JCheckBox) getModel().getElementAt(index);
					checkbox.setSelected(!checkbox.isSelected());
					repaint();
				}
			}
		});

		addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_SPACE) {
					int index = getSelectedIndex();
					if (index != -1) {
						JCheckBox checkbox = (JCheckBox) getModel().getElementAt(index);
						checkbox.setSelected(!checkbox.isSelected());
						repaint();
					}
				}
			}
		});
		setSelectionMode(ListSelectionModel.SINGLE_SELECTION);				
	}
	
	public void clearSelection() {
		int i = 0;
		for (i = 0; i < getModel().getSize(); i++) {
			JCheckBox checkbox = (JCheckBox) getModel().getElementAt(i);
			checkbox.setSelected(false);
		}
	}
	
	public int getMaxSelectionIndex() {
		int maxIndex = -1;
		int i;
		for (i = 0; i < getModel().getSize(); i++) {
			JCheckBox checkbox = (JCheckBox) getModel().getElementAt(i);
			if (checkbox.isSelected() && i > maxIndex) {
				maxIndex = i;
			}
		}
		return maxIndex;		
	}
	
	public int getMinSelectionIndex() {
		int minIndex = Integer.MAX_VALUE;
		int i;
		for (i = 0; i < getModel().getSize(); i++) {
			JCheckBox checkbox = (JCheckBox) getModel().getElementAt(i);
			if (checkbox.isSelected() && i < minIndex) {
				minIndex = i;
			}
		}
		return minIndex;
	}
	
	public int getSelectedIndex() {
		int firstSelectedIndex = -1;
		int i;
		for (i = 0; i < getModel().getSize(); i++) {
			JCheckBox checkbox = (JCheckBox) getModel().getElementAt(i);
			if (checkbox.isSelected()) {
				return i;
			}
		}
		return firstSelectedIndex;			
	}
	
	public int[] getSelectedIndices() {
		int i;
		List<Integer> selectedItems = new ArrayList<Integer>();
		for (i = 0; i < getModel().getSize(); i++) {
			JCheckBox checkbox = (JCheckBox) getModel().getElementAt(i);
			if (checkbox.isSelected()) {
				selectedItems.add(new Integer(i));
			}
		}
		int[] result = new int[selectedItems.size()];
		int j;
		for (j = 0; j < result.length; j++) {
			result[j] = j;
		}
		return result;
	}
	
	public boolean isSelectedIndex(int index) {
		if (index < 0 || index > getModel().getSize()) {
			return false;
		}
		JCheckBox checkbox = (JCheckBox) getModel().getElementAt(index);
		if (checkbox.isSelected()) {
			return true;
		}
		return false;
	}
	
	public boolean isSelectionEmpty() {		
		int i;
		for (i = 0; i < getModel().getSize(); i++) {
			JCheckBox checkbox = (JCheckBox) getModel().getElementAt(i);
			if (checkbox.isSelected()) {
				return false;
			}
		}
		return true;
	}
	
	public void setSelectedIndex(int index) {
		if (index < 0 || index > getModel().getSize()) {
			return;
		}
		JCheckBox checkbox = (JCheckBox) getModel().getElementAt(index);
		checkbox.setSelected(true);		
	}
	
	public void setSelectedIndices(int[] indices) {
		if (indices.length <= getModel().getSize()) {
			int i;
			for (i = 0; i < indices.length; i++) {
				setSelectedIndex(indices[i]);
			}
		}
	}
	
	public void selectAllEntries() {
		int i = 0;
		for (i = 0; i < getModel().getSize(); i++) {
			JCheckBox checkbox = (JCheckBox) getModel().getElementAt(i);
			checkbox.setSelected(true);
		}
	}
	
	public void valueChanged(ListSelectionEvent e) {
		if (!e.getValueIsAdjusting()) {
			removeListSelectionListener(this);
			
			// Remember everything selected as a result of this action
			HashSet<Integer> newSelections = new HashSet<Integer>();
			int size = getModel().getSize();
			for (int i = 0; i < size; i++) {
				if (getSelectionModel().isSelectedIndex(i)) {
					newSelections.add(new Integer(i));
				}
			}
			// Turn on everything that was previously selected
			Iterator<Integer> it = selectionCache.iterator();
			while (it.hasNext()) {
				int index = ((Integer) it.next()).intValue();
				System.out.println("adding " + index);
				getSelectionModel().addSelectionInterval(index, index);
			}
			// Add or remove the delta
			it = newSelections.iterator();
			while (it.hasNext()) {
				Integer nextInt = (Integer) it.next();
				int index = nextInt.intValue();
				if (selectionCache.contains(nextInt)) {
					getSelectionModel().removeSelectionInterval(index, index);
				}
				else {
					getSelectionModel().addSelectionInterval(index, index);
				}
			}
			// Save selections for next time
			selectionCache.clear();
			for (int i = 0; i < size; i++) {
				if (getSelectionModel().isSelectedIndex(i)) {
					System.out.println("caching " + i);				
					selectionCache.add(new Integer(i));
				}
			}
			addListSelectionListener(this);
		}
	}
	
	protected class CheckBoxCellRenderer implements ListCellRenderer {
		
		public Component getListCellRendererComponent(JList list, Object value,	int index, boolean isSelected, boolean cellHasFocus) {
			JCheckBox checkbox = (JCheckBox) value;
			checkbox.setBackground(isSelected ? getSelectionBackground() : getBackground());
			checkbox.setForeground(isSelected ? getSelectionForeground() : getForeground());

			checkbox.setEnabled(isEnabled());
			checkbox.setFont(getFont());
			checkbox.setFocusPainted(false);

			checkbox.setBorderPainted(true);
			checkbox.setBorder(isSelected ? UIManager.getBorder("List.focusCellHighlightBorder") : noFocusBorder);
			return checkbox;
		}
	}

	public static void main(String args[]) {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout());

		JCheckBoxList cbList = new JCheckBoxList();
		JScrollPane scrollPane = new JScrollPane(cbList);

		Object[] cbArray = new Object[10];
		cbArray[0] = new JCheckBox("Module 0");
		cbArray[1] = new JCheckBox("Module 1");
		cbArray[2] = new JCheckBox("Module 2");
		cbArray[3] = new JCheckBox("Module 3");
		cbArray[4] = new JCheckBox("Module 4");
		cbArray[5] = new JCheckBox("Module 5");
		cbArray[6] = new JCheckBox("Module 6");
		cbArray[7] = new JCheckBox("Module 7");
		cbArray[8] = new JCheckBox("Module 8");
		cbArray[9] = new JCheckBox("Module 9");
		cbList.setListData(cbArray);
		//cbList.selectAllEntries();
		
		/*
		cbList.setSelectedIndex(0);
		cbList.setSelectedIndex(2);
		cbList.setSelectedIndex(4);
		cbList.setSelectedIndex(6);
		cbList.setSelectedIndex(8);
		System.out.println(cbList.isSelectedIndex(0));
		System.out.println(cbList.isSelectedIndex(1));
		System.out.println(cbList.isSelectedIndex(2));
		System.out.println(cbList.isSelectedIndex(3));
		System.out.println(cbList.isSelectedIndex(4));
		System.out.println(cbList.isSelectedIndex(5));
		System.out.println(cbList.isSelectedIndex(6));
		System.out.println(cbList.isSelectedIndex(7));
		System.out.println(cbList.isSelectedIndex(8));
		System.out.println(cbList.isSelectedIndex(9));

		int[] a = cbList.getSelectedIndices();
		System.out.println(a.length);
		System.out.println(cbList.getMaxSelectionIndex());
		System.out.println(cbList.getMinSelectionIndex());
		System.out.println(cbList.getSelectedIndex());
		System.out.println(cbList.isSelectionEmpty());
		int[] b = {1, 3, 5, 7, 9};
		cbList.setSelectedIndices(b);
		cbList.clearSelection();
		*/

		frame.getContentPane().add(scrollPane);
		frame.pack();
		frame.setVisible(true);
	}
}
