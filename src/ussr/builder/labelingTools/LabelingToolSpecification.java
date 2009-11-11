package ussr.builder.labelingTools;

import java.io.Serializable;

import com.jme.scene.Geometry;

import ussr.aGui.tabs.controllers.AssignBehaviorsTabController;
import ussr.builder.QuickPrototyping;
import ussr.builder.enumerations.LabeledEntities;
import ussr.builder.enumerations.LabelingTools;
import ussr.builder.helpers.BuilderHelper;
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
public class LabelingToolSpecification extends CustomizedPicker implements Serializable {

	/**
	 * The physical simulation.
	 */
	private JMESimulation jmeSimulation;

	/**
	 * The module selected in simulation environment.
	 */
	private Module selectedModule;	

	/**
	 * The label to assign to entity.
	 */
	private String labels;	

	/**
	 * The name of the tool to be used.
	 */
	private LabelingTools toolName;	

	/**
	 * The interface to labeling.
	 */
	private LabelingTemplate labeling;
	
	private LabeledEntities entityToLabel;

	
	/**
	 * The object of GUI.
	 */
	/*private QuickPrototyping quickPrototyping;*/



	/**
	 * The connector number on the module, selected with the left side of mouse in simulation environment. 
	 */	
	private int selectedConnectorNr = 1000;//just to avoid having default 0 value, which is also number of connector. 

	/**
	 * For calling tools handling labeling of entities, in particular tools like "LABEL_MODULE","LABEL_CONNECTOR" and "DELETE_LABEL". 
	 * @param simulation, the physical simulation.
	 * @param entityToLabel, the entity to be labeled. For example: LabeledEntities.MODULE or LabeledEntities.CONNECTOR.
	 * @param labels, the labels to be assigned.
	 * @param toolName, the name of the tool to be used.
	 */
	public LabelingToolSpecification(/*JMESimulation ,*/LabeledEntities entityToLabel,String labels, LabelingTools toolName){
		/*this.jmeSimulation = jmeSimulation;	*/	
		this.labels = labels;		
		this.toolName = toolName;
		this.entityToLabel = entityToLabel;
		
				
	}

	/**
	 * For calling tools handling labeling of entities, in particular tool called "READ_LABELS". 
	 * @param simulation, the physical simulation.
	 * @param entityToLabel, the entity to be labeled. For example: LabeledEntities.MODULE or LabeledEntities.CONNECTOR.
	 * @param toolName, the name of the tool to be used.
	 * @param quickPrototyping, the QuickPrototyping frame.
	 */
	public LabelingToolSpecification(/*JMESimulation simulation,*/LabeledEntities entityToLabel, LabelingTools toolName/*, QuickPrototyping quickPrototyping*/){
		/*this.jmeSimulation = simulation;*/				
		this.toolName = toolName;
		this.entityToLabel = entityToLabel;
		
		//this.labeling = new LabelingFactory().getLabeling(entityToLabel);
		/*this.quickPrototyping = quickPrototyping;*/

	}

	/* Method executed when the module is selected with the left side of the mouse in simulation environment.
	 * Here is identified the module selected in simulation environment and the call for appropriate tool is made. 
	 * @see ussr.physics.jme.pickers.CustomizedPicker#pickModuleComponent(ussr.physics.jme.JMEModuleComponent)
	 */
	@Override
	protected void pickModuleComponent(JMEModuleComponent component) {
		this.jmeSimulation = (JMESimulation)component.getModel().getSimulation();
		this.selectedModule = component.getModel();
		this.labeling = new LabelingFactory().getLabeling(entityToLabel);
		callSpecificTool();
	}

	/* Method executed when the module is selected with the left side of the mouse in simulation environment.
	 * Here the connector number is extracted from the string of TriMesh. Initial format of string is for example: "Connector 1 #1"
	 * @see ussr.physics.jme.pickers.CustomizedPicker#pickTarget(com.jme.scene.Spatial)
	 */
	protected void pickTarget(Geometry target,JMESimulation jmeSimulation) {		
		this.jmeSimulation = jmeSimulation;
		this.selectedConnectorNr = BuilderHelper.extractConnectorNr(jmeSimulation, target);
		this.labeling = new LabelingFactory().getLabeling(entityToLabel);
	}

	/**
	 * Calls specific tool for labeling of entities. 
	 */
	private void callSpecificTool(){
		if(this.labeling instanceof  LabelConnectorTemplate && selectedConnectorNr == 1000){// the case when user selects the module  or something else instead of connector.
			//Do nothing
		}else{
			switch(toolName){
			case LABEL_CONNECTOR:		
				//this.labeling.labelSpecificEntity(this);			
				//break;
			case LABEL_MODULE:
				//this.labeling.labelSpecificEntity(this);
				//break;
			case LABEL_SENSOR:
				this.labeling.labelSpecificEntity(this);
				break;
			case READ_LABELS:	
				this.labeling.readLabels(this);
				break;
			default: throw new Error ("The tool name:" +toolName+ ", is not supported yet");
			}		
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
	 * Returns the labels associated with the current tool.
	 * @return labels, the labels associated with the current tool.
	 */
	public String getLabels() {
		return labels;
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
	/*public QuickPrototyping getQuickPrototyping() {
		return quickPrototyping;
	}*/
	public LabelingTemplate getLabeling() {
		return labeling;
	}
}
