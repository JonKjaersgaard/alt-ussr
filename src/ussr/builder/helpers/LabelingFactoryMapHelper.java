package ussr.builder.helpers;

import ussr.builder.enumerations.tools.LabeledEntities;

/**
 * Acts as a helper class for LabelingFactory.java in a way that maps specific entity for labeling
 * to specific class responsible for supporting it.
 * @author Konstantinas
 */
public class LabelingFactoryMapHelper {
	
	/**
	 * The entity to be labeled.
	 */
	private LabeledEntities entityForLabeling;
	
	/**
	 * The object responsible for labeling specific entity. 
	 */
	private Object entityLabellingClass;		

	/**
	 * Maps specific entity for labeling to specific class responsible for supporting it.
	 * @param entityForLabeling, the entity to be labeled.
	 * @param entityLabellingClass, the object responsible for labeling specific entity
	 */
	public LabelingFactoryMapHelper(LabeledEntities entityForLabeling, Object entityLabellingClass ){
		this.entityForLabeling = entityForLabeling; 
		this.entityLabellingClass = entityLabellingClass;
	}	
	
	/**
	 * Returns entity to be labeled. 
	 * @return entityForLabeling, entity to be labeled.
	 */
	public LabeledEntities getEntityForLabeling() {
		return entityForLabeling;
	}
	
	/**
	 * Returns the object responsible for labeling specific entity.
	 * @return entityLabellingClass, the object responsible for labeling specific entity.
	 */
	public Object getEntityLabellingClass() {
		return entityLabellingClass;
	}
	
}
