package ussr.comm.monitors.trackergui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class PacketFilterDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private CommunicationVisualizerGUI communicationVisualizer;
	private final String itemZero = "Do not show packages"; 
	private final String itemOne = "Show normal packet format";
	private final String itemTwo = "Show decimal packet format";
	private final String itemThree = "Show hexadecimal packet format";
	private boolean doNotDrawPackets = false;
	private boolean doDrawNormalPackets = false;
	private boolean doDrawDecimalPackets = false;
	private boolean doDrawHexaDecimalPackets = false;	
	
	public PacketFilterDialog(JFrame aFrame, CommunicationVisualizerGUI parent) {
		super(aFrame, "Packet Filter", true);
		this.communicationVisualizer = parent;
		setPreferredSize(new Dimension(300, 300));
		createPacketPanel();
		pack();
		setLocationRelativeTo(parent);
		setVisible(true);
	}
			
	private JPanel createPacketPanel() {
		GridLayout checkboxLayout = new GridLayout(4, 0);
		JPanel panel = new JPanel();
		panel.setLayout(checkboxLayout);
								
		Object[] items = {itemZero, itemOne, itemTwo, itemThree};
		JComboBox packetFormatter = new JComboBox(items);
		packetFormatter.addActionListener(new PacketFormatListener());
		panel.add(packetFormatter);
				
		JScrollPane scrollPane = new JScrollPane(panel);		
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		JButton doneButton = new JButton("Done");
		doneButton.addActionListener(new DrawPacketListener());
		getContentPane().add(doneButton, BorderLayout.SOUTH);
		panel.setBorder(BorderFactory.createTitledBorder("Packet format selection"));
		return panel;
	}
	
	class PacketFormatListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JComboBox combobox = (JComboBox) e.getSource();
			String selected = (String) combobox.getSelectedItem();

			if (selected.equals(itemZero)) {
				doNotDrawPackets = true;
				doDrawNormalPackets = false;
				doDrawDecimalPackets = false;
				doDrawHexaDecimalPackets = false;				
			}
			else if (selected.equals(itemOne)) {
				doNotDrawPackets = false;
				doDrawNormalPackets = true;
				doDrawDecimalPackets = false;
				doDrawHexaDecimalPackets = false;				
			}
			else if (selected.equals(itemTwo)) {
				doNotDrawPackets = false;
				doDrawNormalPackets = false;
				doDrawDecimalPackets = true;
				doDrawHexaDecimalPackets = false;
				
			}
			else if (selected.equals(itemThree)) {
				doNotDrawPackets = false;
				doDrawNormalPackets = false;
				doDrawDecimalPackets = false;
				doDrawHexaDecimalPackets = true;				
			}
		}
	}
	
	class DrawPacketListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (doNotDrawPackets) {
				communicationVisualizer.getDrawingCanvas().setDrawPackes(true);
				System.out.println("Packets are not drawn");
			}
			else if (doDrawNormalPackets) {
				communicationVisualizer.getDrawingCanvas().setDrawNormalPackets(true);
				System.out.println("Packets are drawn normally");
			}
			else if (doDrawDecimalPackets) {
				communicationVisualizer.getDrawingCanvas().setDrawDecimalPackets(true);
				System.out.println("Packets are drawn decimally");
			}
			else if (doDrawHexaDecimalPackets) {
				communicationVisualizer.getDrawingCanvas().setDrawHexaDecimalPackets(true);
				System.out.println("Packets are drawn hexadecimally");
			}
			dispose();
		}		
	}	
}
