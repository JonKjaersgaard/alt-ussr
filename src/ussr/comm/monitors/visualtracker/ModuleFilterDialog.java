package ussr.comm.monitors.visualtracker;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
	private Set<Integer> modulesToBeDrawn = new HashSet<Integer>();
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
			
	public Set<Integer> getModulesDrawnSet() {
	    return this.modulesToBeDrawn;
	}
	
    class ModuleSelectListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
        	int i;
    		for (i = 0; i < checkboxList.size(); i++) {
    			JCheckBox checkbox = checkboxList.get(i);
    			if (checkbox.isSelected()) {
    			    communicationVisualizer.getDrawingCanvas().addModuleToDraw(i); 
    				modulesToBeDrawn.add(i);
    			}    		    			
    			else {
    				communicationVisualizer.getDrawingCanvas().getModuleToDraw().remove(new Integer(i));
    				modulesToBeDrawn.remove(i);
    			}
    		}    		
    		System.out.println(modulesToBeDrawn.toString());
    		System.out.println(communicationVisualizer.getDrawingCanvas().getModuleToDraw().toString());
        }        
    }
    
    class DrawModuleListener implements ActionListener {
    	public void actionPerformed(ActionEvent e) {
    		if (modulesToBeDrawn.isEmpty()) {
    			System.out.println("Nothing to draw");
    		}
    		else {
    			//Something like:
    			//canvas.drawModules(modulesToBeDrawnMap);
    			//Consider making the canvas hold a static list of
    			//module id's that specify which modules to draw. Provide
    			//a method to access this list (set) from the outside
    			//
    			System.out.println(modulesToBeDrawn.toString());
    		}
    		dispose();
    	}
    }
}

