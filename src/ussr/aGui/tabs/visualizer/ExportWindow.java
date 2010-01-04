package ussr.aGui.tabs.visualizer;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import ussr.aGui.GuiFrames;
import ussr.aGui.MainFrameSeparateController;
import ussr.aGui.MainFramesInter;
import ussr.aGui.tabs.TabsInter;

public class ExportWindow extends GuiFrames {

	
	private JFrame exportWindow;
	
	private boolean instanceFlag =false;
	
	TabsInter visualizer = MainFramesInter.MODULE_COMMUNICATION_VISUALIZER_TAB;
	
	public ExportWindow(){
		if (instanceFlag == false){
			instanceFlag = true;
		initComponents();
		setSize(500,500);
		setVisible(true);
		}
		
	/*	this.addWindowListener (new WindowAdapter() {			
			public void windowClosing(WindowEvent event) {
				
				for (int component=0;component<visualizer.getComponents().size();component++){
					visualizer.add(exportWindow.getComponents()[component]);
				}
				                 
			}
		}
		);*/
	}
	
	@Override
	public void activate() {
		exportWindow = new ExportWindow();
		exportWindow.setVisible(true);
		
	}

	@Override
	protected void initComponents() {
	
		for (int component=0;component<visualizer.getComponents().size();component++){
			this.add(visualizer.getComponents().get(component));
		}
		
		visualizer.getJComponent().removeAll();
		visualizer.getJComponent().repaint();
		
	}

}
