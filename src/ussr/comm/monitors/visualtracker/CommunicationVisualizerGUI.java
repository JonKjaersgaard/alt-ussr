package ussr.comm.monitors.visualtracker;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import ussr.aGui.tabs.view.visualizer.DrawingCanvas;
import ussr.model.Module;
import ussr.physics.jme.JMEBasicGraphicalSimulation;
import ussr.physics.jme.JMESimulation;


public class CommunicationVisualizerGUI extends JPanel {

	private static final long serialVersionUID = 1L;
	private JMESimulation simulation;
	private List<Module> modules;
	
	private static int numberOfModules;
	private static boolean instanceFlag = false;
	
	private JFrame frame;
	private DrawingCanvas canvas;
	
	private JButton startSimulationButton;
	private JButton modulesButton;
	private JButton packetsButton;
		
	public CommunicationVisualizerGUI(JFrame frame, JMEBasicGraphicalSimulation simulation) {
		super(new GridLayout(2, 0));
		this.frame = frame;
		this.simulation = (JMESimulation) simulation;
		modules = this.simulation.getModules();				
		numberOfModules = modules.size();		
		initializeComponents();
		add(createCanvasArea());
		add(createPluginArea());
		instanceFlag = true;
		initializeDefaultSettings();
		start();
	}
	
	private void initializeDefaultSettings() {
        for(Module module: modules)
            canvas.getModuleToDraw().add(module.getID());
        canvas.setDrawDecimalPackets(true);
    }

    public JMESimulation getSimulation() {
		return simulation;
	}
	
	public void start() {
        canvas.start();
    }
    
    public void stop() {
        canvas.stop();
    }
    
    public DrawingCanvas getDrawingCanvas() {
    	return canvas;
    }
		
	private void initializeComponents() {		
		/*canvas = new DrawingCanvas(simulation, 55, numberOfModules);*/
	    canvas.addMouseListener(new CanvasMouseListener());	    
	    startSimulationButton = new JButton("Start simulation");
	    modulesButton = new JButton("Modules");
	    packetsButton = new JButton("Packets");
			
	    startSimulationButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				startSimulationButtonAction(event);
			}
		});	
	    
		modulesButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				modulesButtonAction(event);
			}		
		});
		
		packetsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				packetsButtonAction(event);
			}		
		});			
	}
	
	private void startSimulationButtonAction(ActionEvent event) {
		System.out.println("Start simulation button pressed");
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
		
	private void modulesButtonAction(ActionEvent event) {
		new ModuleFilterDialog(frame, this);
	}
	
	private void packetsButtonAction(ActionEvent event) {
		new PacketFilterDialog(frame, this);
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
				JComponent newContentPane = new CommunicationVisualizerGUI(frame, simulator);
				newContentPane.setOpaque(true); 
				frame.setContentPane(newContentPane);
				frame.pack();
				frame.setVisible(true);
			}
		});
	}
	
	private JPanel createCanvasArea() {
		JScrollPane scrollPane = new JScrollPane(canvas);
		scrollPane.setPreferredSize(new Dimension(900, 2000));				
		JPanel panel = new JPanel(new BorderLayout());
		panel.setPreferredSize(new Dimension(900, 1000));
		panel.add(startSimulationButton, BorderLayout.SOUTH);
		panel.add(scrollPane, BorderLayout.CENTER);		
		panel.setBorder(BorderFactory.createTitledBorder("Module Communication"));
		return panel;
	}
		
	private JPanel createPluginArea() {
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		c.gridx = 0;
		c.gridy = 0;
		panel.add(modulesButton);		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		c.gridx = 0;
		c.gridy = 1;		
		panel.add(packetsButton);				
		panel.setBorder(BorderFactory.createTitledBorder("Visualization Filter"));
		return panel;
	}
			
	class CanvasMouseListener extends MouseAdapter {
		public void mousePressed(MouseEvent e) {
			canvas.setMouseClickX(e.getX());
			canvas.setMouseClickY(e.getY());
			int mouseX = canvas.getMouseClickX();
			int mouseY = canvas.getMouseClickY();
			System.out.println("Mouse position (x, y) = " + "(" + mouseX + ", " + mouseY + ")");
			e.getComponent().repaint();
		}
	}
}
