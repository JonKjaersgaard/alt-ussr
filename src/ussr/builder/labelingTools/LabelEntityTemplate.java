package ussr.builder.labelingTools;

import java.io.Serializable;
import java.util.ArrayList;

import ussr.aGui.tabs.controllers.AssignBehaviorsTabController;
import ussr.builder.QuickPrototyping;
import ussr.builder.helpers.BuilderHelper;
import ussr.model.Entity;

/**
 * Supports labeling of entities composing the module and module itself. Currently these can be:
 * modules and connectors. Follows design pattern called TEMPLATE METHOD with template operations
 * and primitive operations implemented in children classes.
 * @author Konstantinas
 *
 */
public abstract class LabelEntityTemplate implements LabelingTemplate {	
	
	/**
	 * Empty string used to replace the deleted label. 
	 */
	public static final String EMPTY ="";	

	/**
	 *  The output used to indicate that entity do not have labels.
	 */
	public static final String NONE = "none";	
	
	/**
	 * Assigns (adds) new label to the entity. If entity already has several labels, just adds current label to them.
	 * This operation is TEMPLATE method. Operation means that it should be executed on the object. 
	 * @param specification, object containing information about entity to label, the name of the label and so on.
	 */	
	public void labelSpecificEntity(LabelingToolSpecification specification){
		/*Get the entity to label, the new label to assign and the labels currently assigned to entity */
		Entity currentEntity = getCurrentEntity(specification);
		String newLabels = specification.getLabels();		
		String labels = currentEntity.getProperty(BuilderHelper.getLabelsKey());

		if (labels == null){// entity do not even have the property for labels set 
			currentEntity.setProperty(BuilderHelper.getLabelsKey(), newLabels);			
		}else{// to old labels of the entity add new label.
			currentEntity.setProperty(BuilderHelper.getLabelsKey(), newLabels);
		}		
		System.out.println("L:"+ currentEntity.getProperty(BuilderHelper.getLabelsKey()));// for debugging
	};	

	/**
	 * Removes specific label from the string of labels assigned to the entity.
	 * This operation is TEMPLATE method. Operation means that it should be executed on the object. 
	 * @param specification, object containing information about entity to label, the name of the label and so on.
	 */	
	public void removeLabel(LabelingToolSpecification specification){
		Entity currentEntity = getCurrentEntity(specification);
		String label = specification.getLabels();		
		String labels = currentEntity.getProperty(BuilderHelper.getLabelsKey());		
		if (labels != null  && labels.contains(label)){
			String changedLabels = labels.replaceAll(label+LABEL_SEPARATOR, EMPTY);
			currentEntity.setProperty(BuilderHelper.getLabelsKey(), changedLabels);
		}		
		//System.out.println("L:"+ currentEntity.getProperty(BuilderHelper.getLabelsKey()));// for debugging
	};

	/**
	 * Reads and displays in GUI the labels assigned to the entity.
	 * This operation is TEMPLATE method. Operation means that it should be executed on the object. 
	 * @param specification, object containing information about entity to label, the name of the label and so on.
	 */	
	public void readLabels(LabelingToolSpecification specification){
		Entity currentEntity = getCurrentEntity(specification);				
		String labels = currentEntity.getProperty(BuilderHelper.getLabelsKey());
		//FIXME IF exception then the problem is that I am not yet saving labels of sensor
		if (labels==null){
			AssignBehaviorsTabController.updateTableLabels("");
		}else{
			AssignBehaviorsTabController.updateTableLabels(labels);
		}
		
		/*FOR QPSS*/
		/*QuickPrototyping quickPrototyping = specification.getQuickPrototyping();
		if (labels == null){
			quickPrototyping.getModuleLabelsjComboBox().setModel(new javax.swing.DefaultComboBoxModel(new String[] {NONE}));
		}else{
			quickPrototyping.getCurrentLabeljTextField().setText(labels);
			String[] arrayLabels = labels.split(LABEL_SEPARATOR);    	
			quickPrototyping.getModuleLabelsjComboBox().setModel(new javax.swing.DefaultComboBoxModel(arrayLabels));

		}*/
			
		//System.out.println("Labels:"+ labels.length());// for debugging
		//System.out.println("Length:"+ labels.length());// for debugging
		
	};
	
	/**
	 * Returns the entity for labeling.
	 * This method is so-called "Primitive operation" for above TEMPLATE methods. 
	 * @param specification, object containing information about entity to label, the name of the label and so on.
	 * @return entity, the entity for labeling. 
	 */
	public abstract Entity getCurrentEntity(LabelingToolSpecification specification);
	
}
