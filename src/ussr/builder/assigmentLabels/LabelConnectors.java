package ussr.builder.assigmentLabels;

import ussr.builder.BuilderHelper;
import ussr.builder.QuickPrototyping;
import ussr.model.Connector;
import ussr.model.Module;

public class LabelConnectors extends LabelObjects {

	@Override
	public void labelObjects(String label, Module selectedModule, int nr) {
		Connector connector =selectedModule.getConnectors().get(nr); 
       String labels =connector.getProperty(BuilderHelper.getModuleLabelsKey());
		
		if (labels == null){
			connector.setProperty(BuilderHelper.getModuleLabelsKey(), label +LABEL_SEPARATOR);			
		}else if (labels.contains(label)){
			// do nothin
		}else {
			connector.setProperty(BuilderHelper.getModuleLabelsKey(), labels+label+LABEL_SEPARATOR);
			}
		
		System.out.println("L:"+ connector.getProperty(BuilderHelper.getModuleLabelsKey()));
		
	}

	@Override
	public void removeLabel(String label, Module selectedModule, int nr) {
		Connector connector =selectedModule.getConnectors().get(nr); 
		String labels = connector.getProperty(BuilderHelper.getModuleLabelsKey());
		 if (labels != null  && labels.contains(label)){
		String changedLabels = labels.replaceAll(label+LABEL_SEPARATOR, "");
		connector.setProperty(BuilderHelper.getModuleLabelsKey(), changedLabels);
	}		
		 System.out.println("L:"+ connector.getProperty(BuilderHelper.getModuleLabelsKey()));
	}

	public void readLabels(Module selectedModule, int nr, QuickPrototyping quickPrototyping) {
		String labels = selectedModule.getConnectors().get(nr).getProperty(BuilderHelper.getModuleLabelsKey());
		if (labels == null){
    		quickPrototyping.getModuleLabelsjComboBox().setModel(new javax.swing.DefaultComboBoxModel(new String[] { "none labels"}));
    	}else{
    		quickPrototyping.getCurrentLabeljTextField().setText(labels);
    		String[] arrayLabels = labels.split(",");    	
    		quickPrototyping.getModuleLabelsjComboBox().setModel(new javax.swing.DefaultComboBoxModel(arrayLabels));
    		
    	}
		System.out.println("L:"+ labels);
		System.out.println("L:"+ labels);		
	}	

}
