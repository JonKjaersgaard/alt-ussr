package ussr.builder.assigmentLabels;

/**
 * Supports labeling of entities composing the module and module itself. Currently these can be:
 * modules and connectors.
 * @author Konstantinas
 */
public interface Labeling {

	
	
	
	
	
	public abstract void labelEntity(LabelingToolSpecification specification);
	
	public abstract void removeLabel(LabelingToolSpecification specification);
	
	public abstract void readLabels(LabelingToolSpecification specification);
}
