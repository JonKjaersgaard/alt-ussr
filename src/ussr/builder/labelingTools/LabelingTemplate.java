package ussr.builder.labelingTools;

/**
 * Supports labeling of entities composing the module and module itself. Currently these can be:
 * modules and connectors.
 * @author Konstantinas
 */
public interface LabelingTemplate {	
		
	/**
	 * Separator used to separate the labels. 
	 */
	public static final String LABEL_SEPARATOR =",";
	
	/**
	 *  The output used to indicate that entity do not have labels.
	 */
	public static final String NONE = "none";	
	
	/**
	 * Assigns (adds) new label to the entity. If entity already has several labels, just adds current label to them.
	 * This operation is TEMPLATE method. Operation means that it should be executed on the object. 
	 * @param specification, object containing information about entity to label, the name of the label and so on.
	 */	
	public abstract void labelSpecificEntity(LabelingToolSpecification specification);
	
	/**
	 * Removes specific label from the string of labels assigned to the entity.
	 * This operation is TEMPLATE method. Operation means that it should be executed on the object. 
	 * @param specification, object containing information about entity to label, the name of the label and so on.
	 */	
	public abstract void removeLabel(LabelingToolSpecification specification);
	
	/**
	 * Reads and displays in GUI the labels assigned to the entity.
	 * This operation is TEMPLATE method. Operation means that it should be executed on the object. 
	 * @param specification, object containing information about entity to label, the name of the label and so on.
	 */	
	public abstract void readLabels(LabelingToolSpecification specification);
}
