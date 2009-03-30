package ussr.builder.genericSelectionTools;

import com.jme.scene.Spatial;
import ussr.builder.BuilderHelper;
import ussr.builder.QuickPrototyping;
import ussr.model.Module;
import ussr.physics.jme.JMEModuleComponent;
import ussr.physics.jme.pickers.CustomizedPicker;

/**
 * @author Konstantinas
 *
 */
public class ReadLabels extends CustomizedPicker  {

	private QuickPrototyping quickPrototyping;
	
	public ReadLabels(QuickPrototyping quickPrototyping){ 
		this.quickPrototyping = quickPrototyping;
	}
	
	
	@Override
	protected void pickModuleComponent(JMEModuleComponent component) {	
		Module selectedModule = component.getModel();
		String labels = selectedModule.getProperty(BuilderHelper.getLabelsKey());	
    	if (labels == null){
    		this.quickPrototyping.getModuleLabelsjComboBox().setModel(new javax.swing.DefaultComboBoxModel(new String[] { "none labels"}));
    	}else{
    		this.quickPrototyping.getCurrentLabeljTextField().setText(labels);
    		String[] arrayLabels = labels.split(",");    	
    		this.quickPrototyping.getModuleLabelsjComboBox().setModel(new javax.swing.DefaultComboBoxModel(arrayLabels));
    		
    	}
	}

	@Override
	protected void pickTarget(Spatial target) {
		// TODO Auto-generated method stub
		
	}	

}
