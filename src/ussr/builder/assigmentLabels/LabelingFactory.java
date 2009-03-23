package ussr.builder.assigmentLabels;

public class LabelingFactory {

	
	public Labeling getLabeling(String objectToLabel){
		Labeling labeling=null;
		if (objectToLabel.equalsIgnoreCase("Module")){
			labeling = new LabelModules();
		}else if(objectToLabel.equalsIgnoreCase("Connector")){
			labeling = new LabelConnectors();
		}
		return labeling;		
	}
}
