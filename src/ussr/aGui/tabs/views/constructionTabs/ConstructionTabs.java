package ussr.aGui.tabs.views.constructionTabs;

import java.awt.GridBagConstraints;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import ussr.aGui.tabs.Tabs;
import ussr.aGui.tabs.additionalResources.HintPanel;
import ussr.aGui.tabs.additionalResources.HintPanelInter;
import ussr.physics.jme.JMESimulation;

public abstract class ConstructionTabs extends Tabs {

	
	public static HintPanel hintPanel;

	protected ConstructionTabs(boolean firstTabbedPane, String tabTitle,
			JMESimulation jmeSimulation, String imageIconDirectory) {
		super(firstTabbedPane, tabTitle, jmeSimulation, imageIconDirectory);
		// TODO Auto-generated constructor stub
	}
	
	
	
	/**
	 * Controls selection and deselection of button. Only one button can be selected, others desected.
	 * @param jButton, the button to control selection and deselection.
	 */
	public void setSelectionDeselection(javax.swing.JButton  jButton){
		/* go through each component in parent component*/
     for(int index =0;index<jComponent.getComponents().length; index++ ){			
			String className = jComponent.getComponent(index).getClass().toString();
			if (className.contains("JToolBar")){
				javax.swing.JToolBar  currentToolBar = (JToolBar) jComponent.getComponent(index);
				/*Go through components in the toolbar*/
				for(int inde =0;inde<currentToolBar.getComponents().length; inde++ ){
					String classNameNew = currentToolBar.getComponent(inde).toString();
					if (classNameNew.contains("JButton")){
						javax.swing.JButton  button = (JButton) currentToolBar.getComponent(inde);
						if (button.isSelected()){
							button.setSelected(false);
						}
					}/*else if (classNameNew.contains("JComboBox")){
						javax.swing.JComboBox  comboBox = (JComboBox) currentToolBar.getComponent(inde);
						if (comboBox.hasFocus()){
							comboBox.setFocusable(false);
						}
					}	*/				
				}					
			}else if (className.contains("JPanel")){
				JPanel panel = (JPanel)jComponent.getComponent(index); 
				for (int in =0; in<panel.getComponents().length; in ++){
					String className1 = panel.getComponent(in).getClass().toString();
					if (className1.contains("JToolBar")){
						javax.swing.JToolBar  currentToolBar = (JToolBar) panel.getComponent(in);
						/*Go through components in the toolbar*/
						for(int inde =0;inde<currentToolBar.getComponents().length; inde++ ){
							String classNameNew = currentToolBar.getComponent(inde).toString();
							if (classNameNew.contains("JButton")){
								javax.swing.JButton  button = (JButton) currentToolBar.getComponent(inde);
								if (button.isSelected()){
									button.setSelected(false);
								}
							}/*else if (classNameNew.contains("JComboBox")){
								javax.swing.JComboBox  comboBox = (JComboBox) currentToolBar.getComponent(inde);
								if (comboBox.hasFocus()){
									comboBox.setFocusable(false);
								}
							}*/						
						}					
					}
				}
			}
		}
     
     jButton.setSelected(true);
	}
	
	
	/**
	 * Initializes and returns the panel responsible for displaying feedback to the user.
	 * @param width, the width of the panel.
	 * @param height, the height of the panel.
	 * @param initialHint, initial hint to display.
	 * @return panel, responsible for displaying feedback to the user.
	 */
	public HintPanel initHintPanel(int width, int height, String initialHint){
		HintPanel hintPanel  = new HintPanel(width,height);//custom panel
		hintPanel.setText(initialHint);
		hintPanel.setBorderTitle(HintPanelInter.commonTitle);
		return hintPanel;
	}
	
	/**
	 * Returns the panel responsible for displaying feedback to the user.
	 * @return hintPanel, the panel responsible for displaying feedback to the user.
	 */
	public static HintPanel getHintPanel() {
		return hintPanel;
	}

}
