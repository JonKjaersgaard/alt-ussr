package ussr.builder.labelingTools;

import javax.swing.JOptionPane;
import com.jme.scene.Spatial;
import ussr.builder.BuilderHelper;
import ussr.builder.QuickPrototyping;
import ussr.model.Module;
import ussr.physics.jme.JMEModuleComponent;
import ussr.physics.jme.JMESimulation;
import ussr.physics.jme.pickers.CustomizedPicker;

/**
 * The main responsibility of this class is to specify the tool for labeling of
 * entities composing the module and module itself. In order to do that, some parameters should be passed in 
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

	/* Method executed when the module is selected with the left side of the mouse in simulation environment.
	 * Here the connector number is extracted from the string of TriMesh. Initial format of string is for example: "Connector 1 #1"
	 * @see ussr.physics.jme.pickers.CustomizedPicker#pickTarget(com.jme.scene.Spatial)
	 */
	protected void pickTarget(Spatial target) {		
		this.selectedConnectorNr = BuilderHelper.extractConnectorNr(simulation, target);		
	}

	/**
	 * Calls specific tool for labeling of entities. 
	 */
	private void callSpecificTool(){

		switch(toolName){
		case LABEL_CONNECTOR:
			if (selectedConnectorNr == 1000){// in case when user selects the module instead of connector  				
				 JOptionPane.showMessageDialog(null, "You do not selected connector. Please zoom in and select the connector instead. ","Error", JOptionPane.ERROR_MESSAGE);				
			}else{
			this.labeling.labelSpecificEntity(this);
			}
			break;
		case LABEL_MODULE:
			this.labeling.labelSpecificEntity(this);
			break;
		case READ_LABELS:	
			this.labeling.readLabels(this);
			break;
		case DELETE_LABEL:	
			this.labeling.removeLabel(this);
			break;
		default: throw new Error ("The tool name:" +toolName+ ", is not supported yet");
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
