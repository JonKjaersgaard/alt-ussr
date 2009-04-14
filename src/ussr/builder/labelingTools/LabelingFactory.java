package ussr.builder.labelingTools;

/**
 * The main responsibility of this class is to act according to Factory pattern and 
 * create specific object of entities for labeling. For example: LabelModule() for entity LabeledEntities.MODULE  
 * or LabelConnector() for entity LabeledEntities.CONNECTOR.
 * @author Konstantinas
 */
public class LabelingFactory {

	/**
	 * The interface to labeling of entities. 
	 */
	private Labeling labeling;
	
	/**
	 * The array containing the mapping of currently supported entities and the classes responsible
	 * for their labeling.  
	 */
	private final static LabelingFactoryMapHelper[] labelingMap = {
		new LabelingFactoryMapHelper(LabeledEntities.MODULE, new LabelModule()),
		new LabelingFactoryMapHelper(LabeledEntities.CONNECTOR, new LabelConnector()),
		// add here newly supported entity to label 
	};
	
	/**
	 * Returns object of specific entity for labeling.
	 * @param entityToLabel, the entity to be labeled.
	 * @return labeling, object of specific entity for labeling.
	 */
	public Labeling getLabeling(LabeledEntities entityToLabel){
		for (int entry=0; entry < labelingMap.length;entry++){
			/*If string representation of entity to label is found return the class for labeling*/
			if (labelingMap[entry].getEntityForLabeling().equals(entityToLabel)){
				return labeling = (Labeling) labelingMap[entry].getEntityLabellingClass();				
			}
		}
		return labeling;		
	}	
}
