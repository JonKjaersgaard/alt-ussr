package ussr.builder.assigmentLabels;

import com.jme.scene.Spatial;
import com.jme.scene.TriMesh;

import ussr.builder.QuickPrototyping;
import ussr.model.Module;
import ussr.physics.jme.JMEModuleComponent;
import ussr.physics.jme.JMESimulation;
import ussr.physics.jme.pickers.CustomizedPicker;

//TODO INTODUCE DIFFERENT KEYS FOR LABELING MODULES AND CONNECTORS
public class LabelingToolSpecification extends CustomizedPicker {

	private JMESimulation simulation;
	private Module selectedModule;
	private String label;	
	private LabelingTools toolName;
	private String objectToLabel;
	private Labeling labeling;
	private QuickPrototyping quickPrototyping;
	/**
	 * The connector number on the module, selected with the left side of mouse in simulation environment or chosen in GUI comboBox.
	 */	
	private int selectedConnectorNr = 1000;//just to avoid having default 0 value, which is also number of connector. 
	/**
	 * Symbol used to extract the connector number from the string.  
	 */
	private static final String SPLIT_SYMBOL = "#";
	
	/**
	 * The identifier user to locate the string.
	 */
	private static final String CONNECTOR ="Connector";
	
	
	public LabelingToolSpecification(JMESimulation simulation,String objectToLabel,String label, LabelingTools toolName){
		this.simulation = simulation;
		this.objectToLabel = objectToLabel;
		this.label = label;		
		this.toolName = toolName;
		this.labeling = new LabelingFactory().getLabeling(objectToLabel);
	}
	
	public LabelingToolSpecification(JMESimulation simulation,String objectToLabel, LabelingTools toolName, QuickPrototyping quickPrototyping){
		this.simulation = simulation;
		this.objectToLabel = objectToLabel;			
		this.toolName = toolName;
		this.labeling = new LabelingFactory().getLabeling(objectToLabel);
		this.quickPrototyping = quickPrototyping;
		
	}

	@Override
	protected void pickModuleComponent(JMEModuleComponent component) {
		this.selectedModule = component.getModel();
		callSpecificTool();
	}

	@Override
	protected void pickTarget(Spatial target) {
		if(target instanceof TriMesh) {			
			String name = simulation.getGeometryName((TriMesh)target);
			if(name!=null && name.contains(CONNECTOR)){ 
				//System.out.println("Connector: "+name);//For debugging				
				String [] temp = null;	         
				temp = name.split(SPLIT_SYMBOL);// Split by #, into two parts, line describing the connector. For example "Connector 1 #1"
				this.selectedConnectorNr= Integer.parseInt(temp[1].toString());// Take only the connector number, in above example "1" (at the end)
				System.out.println("Connector: "+this.selectedConnectorNr);//For debugging	
			}
		}	
		
	}
	
	private void callSpecificTool(){
	if (objectToLabel.equalsIgnoreCase("Module")&& toolName.equals(LabelingTools.LABEL_MODULE) ){
		this.labeling.labelObjects(this.label, this.selectedModule,1000);
	}else if (objectToLabel.equalsIgnoreCase("Connector")&& toolName.equals(LabelingTools.LABEL_CONNECTOR)){
		this.labeling.labelObjects(this.label, this.selectedModule,selectedConnectorNr);
	}else if (objectToLabel.equalsIgnoreCase("Module")&& toolName.equals(LabelingTools.DELETE_LABEL)){
		this.labeling.removeLabel(label, selectedModule, 1000);
	}else if (objectToLabel.equalsIgnoreCase("Connector")&& toolName.equals(LabelingTools.DELETE_LABEL)){
		this.labeling.removeLabel(label, selectedModule, selectedConnectorNr);
	}else if (objectToLabel.equalsIgnoreCase("Module")&& toolName.equals(LabelingTools.READ_LABELS)){
		this.labeling.readLabels(selectedModule, selectedConnectorNr,quickPrototyping);
	}else if (objectToLabel.equalsIgnoreCase("Connector")&& toolName.equals(LabelingTools.READ_LABELS)){
		this.labeling.readLabels(selectedModule, selectedConnectorNr,quickPrototyping);
	}
	}
	
	
}
