package ussr.aGui.tabs.views.constructionTabs;

import java.awt.Dimension;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import ussr.aGui.GuiFrames;
import ussr.aGui.MainFrames;
import ussr.aGui.enumerations.ComponentsFrame;
import ussr.aGui.fileChooser.views.FileChooserFrameInter;
import ussr.aGui.fileChooser.views.FileChooserOpenFrame;
import ussr.aGui.fileChooser.views.FileChooserSaveFrame;
import ussr.aGui.tabs.Tabs;
import ussr.aGui.tabs.additionalResources.HintPanel;
import ussr.aGui.tabs.additionalResources.HintPanelInter;

public abstract class ConstructionTabs extends Tabs implements ConstructionTabsInter  {


	/**
	 * @param initiallyVisible
	 * @param firstTabbedPane
	 * @param tabTitle
	 * @param imageIconDirectory
	 */
	protected ConstructionTabs(boolean initiallyVisible, boolean firstTabbedPane, String tabTitle, String imageIconDirectory) {
		super(initiallyVisible,firstTabbedPane, tabTitle, imageIconDirectory);
	}
	
	
	
	/**
	 * Controls selection and deselection of button. Only one button can be selected, others desected.
	 * @param jButton, the button to control selection and deselection.
	 */
	public void setSelectionDeselection(javax.swing.JButton  jButton){
		/* go through each component in parent component*/
     for(int index =0;index<jComponent.getComponents().length; index++ ){			
			String className = jComponent.getComponent(index).getClass().toString();
			if (className.contains(ComponentsFrame.JToolBar.toString())){
				javax.swing.JToolBar  currentToolBar = (JToolBar) jComponent.getComponent(index);
				/*Go through components in the toolbar*/
				for(int inde =0;inde<currentToolBar.getComponents().length; inde++ ){
					String classNameNew = currentToolBar.getComponent(inde).toString();
					if (classNameNew.contains(ComponentsFrame.JButton.toString())){
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
	
	
	
	public javax.swing.JToolBar initSaveLoadJToolbar(){
		javax.swing.JToolBar jToolBarSaveLoad = new javax.swing.JToolBar();
		
		jToolBarSaveLoad.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
		jToolBarSaveLoad.setFloatable(false);//user can not make the tool bar to float
		jToolBarSaveLoad.setRollover(true);// the components inside are roll over
		jToolBarSaveLoad.setToolTipText(COMMON_TOOL_TIP_TEXTS[0]);
		jToolBarSaveLoad.setPreferredSize(new Dimension(60,GuiFrames.COMMON_HEIGHT+2));
		
		Map<String,String> fileDescriptionsAndExtensions= new HashMap<String,String>();
		fileDescriptionsAndExtensions.put(FileChooserFrameInter.ROBOT_FILE_DESCRIPTION, FileChooserFrameInter.DEFAULT_FILE_EXTENSION);

		FileChooserFrameInter fcOpenFrame = new FileChooserOpenFrame(fileDescriptionsAndExtensions,FileChooserFrameInter.FC_XML_CONTROLLER,FileChooserFrameInter.DIRECTORY_ROBOTS),
		                      fcSaveFrame = new FileChooserSaveFrame(fileDescriptionsAndExtensions,FileChooserFrameInter.FC_XML_CONTROLLER,FileChooserFrameInter.DIRECTORY_ROBOTS);
		
		/*Reuse the buttons for saving and loading  already initialized in the main window*/
		jToolBarSaveLoad.add(MainFrames.initSaveButton(fcSaveFrame));
		jToolBarSaveLoad.add(MainFrames.initOpenButton(fcOpenFrame));
		
		return jToolBarSaveLoad;
	}

	

}
