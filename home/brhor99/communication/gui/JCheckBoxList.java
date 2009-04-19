package communication.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;



public class JCheckBoxList extends JList {
	private static final long serialVersionUID = 1L;
	protected static Border noFocusBorder = new EmptyBorder(1, 1, 1, 1);

	public JCheckBoxList() {
		setCellRenderer(new CheckBoxCellRenderer());

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

	/*
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

		frame.getContentPane().add(scrollPane);
		frame.pack();
		frame.setVisible(true);
	}
	*/
}
