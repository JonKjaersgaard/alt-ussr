package communication.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import ussr.model.Module;
import ussr.physics.jme.JMEBasicGraphicalSimulation;
import ussr.physics.jme.JMESimulation;

//TODO 
//1) Make printing in text area possible
//2) Create connection to JMESimulator from GUI (play, pause, and stop buttons)
//3) Extract information from simulator into GUI visualizer eg. number of modules
//4) Make customization of the size of the visualization GUI possible
//5) Create a class/interface between the GUI visualizer and the actual simulation

public class CommunicationVisualizerGUI extends JPanel implements ItemListener {

	private static final long serialVersionUID = 1L;
	private JMESimulation simulation;
	private List<Module> modules;
	
	private static int numberOfModules;
	private static boolean showTransmitters;
	private static boolean showReceivers;
	private static boolean showPackets;
	private boolean isSimulationRunning = false;
	private static boolean instanceFlag = false;
	
	private JTextArea textArea;
	private DrawingCanvas canvas;
	
	private JCheckBox checkboxTransmitter;
	private JCheckBox checkboxReceiver;
	private JCheckBox checkboxPacket;

	private JLabel labelTransmitterModules;
	private JLabel labelReceiverModules;
	private JLabel labelPacketModules;
	
	private JCheckBoxList listCheckBoxModules;
	
	private JComboBox comboboxTransmitterModules;
	private JComboBox comboboxReceiverModules;
	private JComboBox comboboxPacketModules;

	private JButton startSimulationButton;
	private JButton pauseSimulationButton;
	private JButton stopSimulationButton;

	public CommunicationVisualizerGUI(JMEBasicGraphicalSimulation simulation) {
		//super(new GridLayout(5, 1));
		super(new GridLayout(4, 1));
		this.simulation = (JMESimulation) simulation;
		modules = this.simulation.getModules();				
		numberOfModules = modules.size();		
		initializeComponents();
		add(createTextArea());
		add(createCanvasArea());
		add(createFilterArea());
		//add(createControllArea());
		add(createSimulationArea());
		instanceFlag = true;
		start();
	}
	
	public void start() {
        canvas.start();
    }
    
    public void stop() {
        canvas.stop();
    }
		
	private void initializeComponents() {
		
		canvas = new DrawingCanvas(simulation, 55, numberOfModules);
	    canvas.addMouseListener(new CanvasMouseListener());
		
	    String[] items = createComboboxItems();
		checkboxTransmitter = new JCheckBox("Transmitters");
		checkboxReceiver = new JCheckBox("Receivers");
		checkboxPacket = new JCheckBox("Packets");

		checkboxTransmitter.addItemListener(this);
		checkboxReceiver.addItemListener(this);
		checkboxPacket.addItemListener(this);

		labelTransmitterModules = new JLabel("Modules");
		labelReceiverModules = new JLabel("Modules");
		labelPacketModules = new JLabel("Modules");
		
		listCheckBoxModules = new JCheckBoxList();

		comboboxTransmitterModules = new JComboBox(items);
		comboboxReceiverModules = new JComboBox(items);
		comboboxPacketModules = new JComboBox(items);
						
		comboboxTransmitterModules.setEnabled(false);
		comboboxReceiverModules.setEnabled(false);
		comboboxPacketModules.setEnabled(false);
		
		startSimulationButton = new JButton("Start simulation");
		pauseSimulationButton = new JButton("Pause simulation");
		stopSimulationButton = new JButton("Stop simulation");

		startSimulationButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				startSimulationButtonAction(event);
			}
		});
		
		pauseSimulationButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				pauseSimulationButtoAction(event);				
			}
		});
		
		stopSimulationButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				stopSimulationButtonAction(event);
			}
		});
	}

	private synchronized void startSimulationButtonAction(ActionEvent event) {
		isSimulationRunning = true;
		System.out.println("Start simulation button pressed");
		textArea.append("Simulation started \n");
		simulation.setPause(false);
		
		//TODO
		//1) When the start simulation button is pressed the communication should be visualized
		//2) For each module it should be checked if it transmits data
		//3) If it transmits data the receiver(s) should be obtained
		//4) If it transmits data the packet should be obtained
		//5) The moduleID of the transmitter and the receivers are extracted 
		//6) An arrow from the transmitter module to the receiver module(s) should be drawn on the canvas
		//7) The content of the packet should be drawn above the arrow		
	}
	
	private void pauseSimulationButtoAction(ActionEvent event) {
		System.out.println("Pause simulation button pressed");
		if(simulation.isPaused() == false) {
			simulation.setPause(true);
		}
		else {
			simulation.setPause(false);
		}
	}
	
	private void stopSimulationButtonAction(ActionEvent event) {
		textArea.append("Simulation stopped \n");
		simulation.stop();
	}

	public static boolean getInstanceFlag() {
		return instanceFlag;
	}

	public static void activateCommunicationVisualizerGUI(final JMEBasicGraphicalSimulation simulator) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				JFrame.setDefaultLookAndFeelDecorated(true);
				JFrame frame = new JFrame("USSR - Module Communication Visualizer");
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				JComponent newContentPane = new CommunicationVisualizerGUI(simulator);
				newContentPane.setOpaque(true); 
				frame.setContentPane(newContentPane);
				frame.pack();
				frame.setVisible(true);
			}
		});
	}
	
	private String[] createComboboxItems() {
		String[] items = new String[numberOfModules];
		int i;
		for(i = 0; i < numberOfModules; i++) {
			items[i] = "#" + i;			
		}
		return items;		
	}
	
	private Object[] createCheckboxItems() {
		Object[] items = new Object[numberOfModules];
		int i;
		for(i = 0; i < numberOfModules; i++) {
			String moduleName = "#" + i;
			items[i] = new JCheckBox(moduleName);			
		}
		return items;
	}
	
	private JPanel createTextArea() {
		textArea = new JTextArea();
		textArea.setDragEnabled(true);
		JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setPreferredSize(new Dimension(600, 100));
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(scrollPane, BorderLayout.CENTER);
		panel.setBorder(BorderFactory.createTitledBorder("Status"));
		return panel;
	}

	private JPanel createCanvasArea() {
		//canvas = new DrawingCanvas(simulation, 20, simulation.getModules().size());
		JScrollPane scrollPane = new JScrollPane(canvas);
		//scrollPane.setPreferredSize(new Dimension(600, 200));
		scrollPane.setPreferredSize(new Dimension(1000, 1000));
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(scrollPane, BorderLayout.CENTER);
		panel.setBorder(BorderFactory.createTitledBorder("Module Communication"));
		return panel;
	}
	
	private JPanel createFilterArea() {
		JScrollPane scrollPane = new JScrollPane(listCheckBoxModules);		
		Object[] checkboxItems = createCheckboxItems();	
		listCheckBoxModules.setListData(checkboxItems);
		listCheckBoxModules.selectAllEntries();
		scrollPane.setPreferredSize(new Dimension(200, 100));
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(scrollPane, BorderLayout.CENTER);
		panel.setBorder(BorderFactory.createTitledBorder("Module Filter"));
		return panel;
	}

	private JPanel createControllArea() {
		JPanel panel = new JPanel(new GridLayout(4, 4));
		panel.setPreferredSize(new Dimension(100, 100));		
		panel.add(checkboxTransmitter);
		panel.add(labelTransmitterModules);
		panel.add(comboboxTransmitterModules);		
		panel.add(checkboxReceiver);
		panel.add(labelReceiverModules);
		panel.add(comboboxReceiverModules);		
		panel.add(checkboxPacket);
		panel.add(labelPacketModules);
		panel.add(comboboxPacketModules);
		panel.setBorder(BorderFactory.createTitledBorder("Visualization Filter"));
		return panel;
	}

	private JPanel createSimulationArea() {
		JPanel panel = new JPanel(new GridLayout(3, 1));
		panel.setPreferredSize(new Dimension(100, 100));
		panel.add(startSimulationButton);
		panel.add(pauseSimulationButton);
		panel.add(stopSimulationButton);
		return panel;
	}
	
	public void itemStateChanged(ItemEvent e) {
		Object source = e.getItemSelectable();
		if (source == checkboxTransmitter) {
			if (e.getStateChange() == ItemEvent.SELECTED) {
				System.out.println("All transmitters selected");
				textArea.append("All transmitters selected \n");
				comboboxTransmitterModules.setEnabled(true);
			} else {
				System.out.println("All transmitters deselected");
				textArea.append("All transmitters deselected \n");
				comboboxTransmitterModules.setEnabled(false);
			}
		} else if (source == checkboxReceiver) {
			if (e.getStateChange() == ItemEvent.SELECTED) {
				System.out.println("All receivers selected");
				textArea.append("All receivers selected \n");
				comboboxReceiverModules.setEnabled(true);
			} else {				
				System.out.println("All receivers deselected");
				textArea.append("All receivers deselected \n");
				comboboxReceiverModules.setEnabled(false);
			}
		} else if (source == checkboxPacket) {
			if (e.getStateChange() == ItemEvent.SELECTED) {
				System.out.println("All packets selected");
				textArea.append("All packets selected \n");
				comboboxPacketModules.setEnabled(true);
			} else {
				System.out.println("All packets deselected");
				textArea.append("All packets deselected \n");
				comboboxPacketModules.setEnabled(false);
			}
		}
	}
	
	class CanvasMouseListener extends MouseAdapter{
		  public void mousePressed(MouseEvent e){
			canvas.setMouseClickX(e.getX());
			canvas.setMouseClickY(e.getY());
			int mouseX = canvas.getMouseClickX();
			int mouseY = canvas.getMouseClickY();
		    System.out.println("Mouse position (x, y) = " + "(" + mouseX + ", " + mouseY + ")");
		    e.getComponent().repaint();		    
		  }
		}
}
