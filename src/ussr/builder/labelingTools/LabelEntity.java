package ussr.builder.labelingTools;

/**
 * Supports labeling of entities composing the module and module itself. Currently these can be:
 * modules and connectors.
 * @author Konstantinas
 *
 */
public abstract class LabelEntity implements Labeling {	
	
	/**
	 * Separator used to separate the labels. 
	 */
	public static final String LABEL_SEPARATOR =",";
	
	/**
	 * Empty string used to replace the deleted label. 
	 */
	public static final String EMPTY ="";	
		
	/**
	 *  The output used to indicate that entity do not have none labels.
	 */
	public static final String NONE_LABELS = "none labels";	
	
	
	
	public abstract void labelEntity(LabelingToolSpecification specification);
	
	public abstract void removeLabel(LabelingToolSpecification specification);

	public abstract void readLabels(LabelingToolSpecification specification);
}
