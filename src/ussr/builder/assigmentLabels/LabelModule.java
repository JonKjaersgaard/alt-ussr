package ussr.builder.assigmentLabels;

import ussr.builder.BuilderHelper;
import ussr.builder.QuickPrototyping;
import ussr.model.Module;


/**
 * Supports labeling of modules. The precondition is that
 * the module is selected with the mouse in simulation environment.
 * @author Konstantinas
 */
public class LabelModule extends LabelEntity {

	
	
	
	public void labelEntity(LabelingToolSpecification specification){
		Module selectedModule = specification.getSelectedModule();
		String label = specification.getLabel();
		String labels = selectedModule.getProperty(BuilderHelper.getModuleLabelsKey());
		
		if (labels == null){
			selectedModule.setProperty(BuilderHelper.getModuleLabelsKey(), label +LABEL_SEPARATOR);			
		}else if (labels.contains(label)){
			//do nothing
		}else{
			selectedModule.setProperty(BuilderHelper.getModuleLabelsKey(), labels+label+LABEL_SEPARATOR);
			}
		
		System.out.println("L:"+ selectedModule.getProperty(BuilderHelper.getModuleLabelsKey()));
	}

	@Override
	public void removeLabel(LabelingToolSpecification specification) {
		Module selectedModule = specification.getSelectedModule();		
		String label = specification.getLabel();
		
		String labels = selectedModule.getProperty(BuilderHelper.getModuleLabelsKey());
		 if (labels != null  && labels.contains(label)){
		String changedLabels = labels.replaceAll(label+LABEL_SEPARATOR, EMPTY);
		selectedModule.setProperty(BuilderHelper.getModuleLabelsKey(), changedLabels);
	}		
		 System.out.println("L:"+ selectedModule.getProperty(BuilderHelper.getModuleLabelsKey()));
	}

	public void readLabels(LabelingToolSpecification specification) {
		Module selectedModule = specification.getSelectedModule();
		QuickPrototyping quickPrototyping = specification.getQuickPrototyping(); 
		
		String labels = selectedModule.getProperty(BuilderHelper.getModuleLabelsKey());
		if (labels == null){
    		quickPrototyping.getModuleLabelsjComboBox().setModel(new javax.swing.DefaultComboBoxModel(new String[] {NONE_LABELS}));
    	}else{
    		quickPrototyping.getCurrentLabeljTextField().setText(labels);
    		String[] arrayLabels = labels.split(LABEL_SEPARATOR);    	
    		quickPrototyping.getModuleLabelsjComboBox().setModel(new javax.swing.DefaultComboBoxModel(arrayLabels));
    		
    	}
		System.out.println("L:"+ labels);
	}	
}
