package communication.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import ussr.model.Module;
import ussr.physics.jme.JMESimulation;

public class ModuleFilterDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private CommunicationVisualizerGUI communicationVisualizer;
	private JScrollPane scrollPane;
	private List<JCheckBox> checkboxList = new ArrayList<JCheckBox>();
	private Map<Integer, Integer> modulesToBeDrawnMap = new HashMap<Integer, Integer>();
	private JButton doneButton;
	
	
	public ModuleFilterDialog(JFrame aFrame, CommunicationVisualizerGUI parent) {
		super(aFrame, "Module Filter", true);
		this.communicationVisualizer = parent;
		setPreferredSize(new Dimension(300, 300));
		createModulePanel();
		pack();
		setLocationRelativeTo(parent);
		setVisible(true);
	}
				
	private JPanel createModulePanel() {
		JMESimulation simulation = communicationVisualizer.getSimulation();
		List<Module> modules = simulation.getModules();
		int numberOfModules = modules.size();
		
		GridLayout checkboxLayout = new GridLayout(numberOfModules, 0);
		JPanel panel = new JPanel();
		panel.setLayout(checkboxLayout);
		
		for (Module m : modules) {
			JCheckBox moduleCheckbox = new JCheckBox("Module #" + m.getID());
			//moduleCheckbox.setSelected(true);
			moduleCheckbox.addActionListener(new ModuleSelectListener());
			panel.add(moduleCheckbox);			
			checkboxList.add(moduleCheckbox);
		}
		scrollPane = new JScrollPane(panel);		
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		doneButton = new JButton("Done");
		doneButton.addActionListener(new DrawModuleListener());
		getContentPane().add(doneButton, BorderLayout.SOUTH);
		panel.setBorder(BorderFactory.createTitledBorder("Module selection"));
		return panel;
	}
			
    class ModuleSelectListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
        	int i;
    		for (i = 0; i < checkboxList.size(); i++) {
    			JCheckBox checkbox = checkboxList.get(i);
    			if (checkbox.isSelected()) {
    				
    				if (!communicationVisualizer.getDrawingCanvas().getModuleToDraw().containsKey(new Integer(i))) {
    					communicationVisualizer.getDrawingCanvas().addModuleToDraw(new Integer(i)); 
    				}

    				if(!modulesToBeDrawnMap.containsKey(new Integer(i))) {
    					modulesToBeDrawnMap.put(new Integer(i), new Integer(i));
    				}    				
    			}    		    			
    			else {
    				communicationVisualizer.getDrawingCanvas().getModuleToDraw().remove(new Integer(i));
    				modulesToBeDrawnMap.remove(new Integer(i));
    			}
    		}    		
    		System.out.println(modulesToBeDrawnMap.toString());
    		System.out.println(modulesToBeDrawnMap.keySet().toString());
    		System.out.println(communicationVisualizer.getDrawingCanvas().getModuleToDraw().toString());
    		System.out.println(communicationVisualizer.getDrawingCanvas().getModuleToDraw().keySet().toString());
        }        
    }
    
    class DrawModuleListener implements ActionListener {
    	public void actionPerformed(ActionEvent e) {
    		if (modulesToBeDrawnMap.isEmpty()) {
    			System.out.println("Nothing to draw");
    		}
    		else {
    			//Something like:
    			//canvas.drawModules(modulesToBeDrawnMap);
    			//Consider making the canvas hold a static list of
    			//module id's that specify which modules to draw. Provide
    			//a method to access this list (set) from the outside
    			//
    			System.out.println(modulesToBeDrawnMap.keySet().toString());
    		}
    		dispose();
    	}
    }
}

