package ussr.builder.labelingTools;

import com.jme.scene.Spatial;
import com.jme.scene.TriMesh;

import ussr.builder.QuickPrototyping;
import ussr.model.Module;
import ussr.physics.jme.JMEModuleComponent;
import ussr.physics.jme.JMESimulation;
import ussr.physics.jme.pickers.CustomizedPicker;

/**
 * The main responsibility of this class is to specify the tool for labeling of
 * entities composing the module and module itself . In order to do that, some parameters should be passed in 
 * constructor others are extracted from simulation environment (when user selects the
 * modules or connectors on the modules).
 * @author Konstantinas
 */
public class LabelingToolSpecification extends CustomizedPicker {

	/**
	 * The physical simulation.
	 */
	private JMESimulation simulation;
	
	/**
	 * The module selected in simulation environment.
	 */
	private Module selectedModule;	

	/**
	 * The label to assign to entity.
	 */
	private String label;	
	
	/**
	 * The name of the tool to be used.
	 */
	private LabelingTools toolName;	
	
	/**
	 * The interface to labeling.
	 */
	private Labeling labeling;
	
	/**
	 * The object of GUI.
	 */
	private QuickPrototyping quickPrototyping;
	
	/**
	 * The connector number on the module, selected with the left side of mouse in simulation environment. 
	 */	
	private int selectedConnectorNr = 1000;//just to avoid having default 0 value, which is also number of connector. 
	
	/**
	 * Symbol used to extract the connector number from the string.  
	 */
	private static final String SPLIT_SYMBOL = "#";
	
	/**
	 * The identifier, used to locate the string.
	 */
	private static final String CONNECTOR ="Connector";
	
	
	/**
	 * For calling tools handling labeling of entities, in particular tools like "LABEL_MODULE","LABEL_CONNECTOR" and "DELETE_LABEL". 
	 * @param simulation, the physical simulation.
	 * @param entityToLabel, the entity to be labeled. For example: "Module" or "Connector".
	 * @param label, the label to be assigned. Can be any String.
	 * @param toolName, the name of the tool to be used.
	 */
	public LabelingToolSpecification(JMESimulation simulation,String entityToLabel,String label, LabelingTools toolName){
		this.simulation = simulation;		
		this.label = label;		
		this.toolName = toolName;
		this.labeling = new LabelingFactory().getLabeling(entityToLabel);		
	}
	
	/**
	 * For calling tools handling labeling of entities, in particular tool called "READ_LABELS". 
	 * @param simulation, the physical simulation.
	 * @param entityToLabel, the entity to be labeled. For example: "Module" or "Connector".
	 * @param toolName, the name of the tool to be used.
	 * @param quickPrototyping, the QuickPrototyping frame.
	 */
	public LabelingToolSpecification(JMESimulation simulation,String entityToLabel, LabelingTools toolName, QuickPrototyping quickPrototyping){
		this.simulation = simulation;				
		this.toolName = toolName;
		this.labeling = new LabelingFactory().getLabeling(entityToLabel);
		this.quickPrototyping = quickPrototyping;
		
	}

	/* Method executed when the module is selected with the left side of the mouse in simulation environment.
	 * Here is identified the module selected in simulation environment and the call for appropriate tool is made. 
	 * @see ussr.physics.jme.pickers.CustomizedPicker#pickModuleComponent(ussr.physics.jme.JMEModuleComponent)
	 */
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
				String [] temp = null;	         
				temp = name.split(SPLIT_SYMBOL);// Split by #, into two parts, line describing the connector. For example "Connector 1 #1"
				this.selectedConnectorNr= Integer.parseInt(temp[1].toString());// Take only the connector number, in above example "1" (at the end)
				System.out.println("Connector: "+this.selectedConnectorNr);//For debugging	
			}
		}	
		
	}
	
	/**
	 * Calls the tool for labeling of entities. 
	 */
	private void callSpecificTool(){		
	if (toolName.equals(LabelingTools.LABEL_CONNECTOR)|| toolName.equals(LabelingTools.LABEL_MODULE) ){				
		this.labeling.labelEntity(this);
	}else if (toolName.equals(LabelingTools.READ_LABELS)){		
		this.labeling.readLabels(this);
	}else if (toolName.equals(LabelingTools.DELETE_LABEL)){		
		this.labeling.removeLabel(this);
	}
	}
	
	/**
	 * Returns the module selected in simulation environment and associated with the tool.
	 * @return selectedModule,the module selected in simulation environment and associated with the tool.
	 */
	public Module getSelectedModule() {
		return selectedModule;
	}

	/**
	 * Returns the label associated with the current tool.
	 * @return label, the label associated with the current tool.
	 */
	public String getLabel() {
		return label;
	}
	
	/**
	 * Returns the connector number selected in simulation environment and associated with the tool.
	 * @return selectedConnectorNr, the connector number selected in simulation environment and associated with the tool.
	 */
	public int getSelectedConnectorNr() {
		return selectedConnectorNr;
	}

	/**
	 * Returns the current instance of GUI and associated with the tool.
	 * @return quickPrototyping, the current instance of GUI and associated with the tool.
	 */
	public QuickPrototyping getQuickPrototyping() {
		return quickPrototyping;
	}	
}
