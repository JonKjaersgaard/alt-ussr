package ussr.builder.labels;

import ussr.builder.BuilderHelper;
import ussr.model.Entity;

/**
 * The main responsibility of this class to provide common methods to the children classes
 * in relation to manipulation of labels.
 * @author Konstantinas
 *
 */
public abstract class Label implements Labels {
	
	/**
	 * Returns the labels assigned to the entity.
	 * It is common method to children classes.
	 * @param entity, the entity to get the labels from
	 * @return labels, all labels of the entity separated by comma
	 */
	public  String getLabels(Entity entity){
		String labels = entity.getProperty(BuilderHelper.getLabelsKey());
		if (labels ==null){
			return "none";
		}
		return labels;
	};
	
	/**
	 * Checks if the entity was assigned the label passed as a string.
	 * NOTE: IT IS SIMPLE CONTAINS CHECK FOR NOW.
	 * It is the method following STRATEGY pattern.
	 * @param label, the label name to check;
	 * @return true, if passed label was assigned, false - if not. 
	 */
	public abstract boolean has(String label);
}
