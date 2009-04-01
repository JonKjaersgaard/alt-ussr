package ussr.builder.labelingTools;

/**
 * The main responsibility of this class is to act according to Factory pattern and 
 * create specific object of entities for labeling.For example: LabelModule or LabelConnector.
 * @author Konstantinas
 */
public class LabelingFactory {

	/**
	 * The interface to labeling of module entities. 
	 */
	private Labeling labeling;
	
	/**
	 * Returns object of specific entity for labeling.
	 * @param entityToLabel, the entity to be labeled.
	 * @return labeling, object of specific entity for labeling.
	 */
	public Labeling getLabeling(String entityToLabel){		
		if (entityToLabel.equalsIgnoreCase("Module")){
			labeling = new LabelModule();
		}else if(entityToLabel.equalsIgnoreCase("Connector")){
			labeling = new LabelConnector();
		}else throw new Error("This entity is not supported yet or the name of it is misspelled");
		return labeling;		
	}
}
