package ussr.builder.labelingTools;

import ussr.builder.BuilderHelper;
import ussr.builder.QuickPrototyping;
import ussr.model.Connector;
import ussr.model.Module;

/**
 * Supports labeling of connectors on the modules. The precondition is that
 * the connector is selected with the mouse in simulation environment.
 * @author Konstantinas
 */
public class LabelConnector extends LabelEntity {

	@Override
	public void labelEntity(LabelingToolSpecification specification) {
		Module selectedModule = specification.getSelectedModule();
		String label = specification.getLabel();
		int connectorNr = specification.getSelectedConnectorNr();
		
		Connector connector =selectedModule.getConnectors().get(connectorNr); 
       String labels =connector.getProperty(BuilderHelper.getLabelsKey());
		
		if (labels == null){
			connector.setProperty(BuilderHelper.getLabelsKey(), label +LABEL_SEPARATOR);			
		}else if (labels.contains(label)){
			// do nothin
		}else {
			connector.setProperty(BuilderHelper.getLabelsKey(), labels+label+LABEL_SEPARATOR);
			}
		
		System.out.println("L:"+ connector.getProperty(BuilderHelper.getLabelsKey()));
		
	}

	@Override
	public void removeLabel(LabelingToolSpecification specification) {
		Module selectedModule = specification.getSelectedModule();		
		int connectorNr = specification.getSelectedConnectorNr();
		String label = specification.getLabel();
		
		Connector connector =selectedModule.getConnectors().get(connectorNr); 
		String labels = connector.getProperty(BuilderHelper.getLabelsKey());
		 if (labels != null  && labels.contains(label)){
		String changedLabels = labels.replaceAll(label+LABEL_SEPARATOR, EMPTY);
		connector.setProperty(BuilderHelper.getLabelsKey(), changedLabels);
	}		
		 System.out.println("L:"+ connector.getProperty(BuilderHelper.getLabelsKey()));
	}

	public void readLabels(LabelingToolSpecification specification) {
		Module selectedModule = specification.getSelectedModule();		
		int connectorNr = specification.getSelectedConnectorNr();
		QuickPrototyping quickPrototyping = specification.getQuickPrototyping();
		
		
		String labels = selectedModule.getConnectors().get(connectorNr).getProperty(BuilderHelper.getLabelsKey());
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
