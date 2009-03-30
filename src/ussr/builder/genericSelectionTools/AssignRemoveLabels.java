package ussr.builder.genericSelectionTools;

import com.jme.scene.Spatial;
import ussr.builder.BuilderHelper;
import ussr.model.Module;
import ussr.physics.jme.JMEModuleComponent;
import ussr.physics.jme.pickers.CustomizedPicker;

/**
 * @author Konstantinas
 *
 */
public class AssignRemoveLabels extends CustomizedPicker  {

	private String label;	

	private boolean remove;
	
	private static final String labelSeparator =",";
	
	public AssignRemoveLabels(String label, boolean remove){
		this.label = label;
		this.remove = remove;		
	}
	
	
	@Override
	protected void pickModuleComponent(JMEModuleComponent component) {	
		Module selectedModule = component.getModel();
		String labels = selectedModule.getProperty(BuilderHelper.getLabelsKey());
		if (labels == null && remove == false){
			selectedModule.setProperty(BuilderHelper.getLabelsKey(), label +labelSeparator);			
		}else if (labels != null && remove ==true && labels.contains(label)){
			String changedLabels = labels.replaceAll(label+labelSeparator, "");
			selectedModule.setProperty(BuilderHelper.getLabelsKey(), changedLabels);
		}else if (remove == false){
			if (labels.contains(label)){
				//do nothing
			}else{
			selectedModule.setProperty(BuilderHelper.getLabelsKey(), labels+label+labelSeparator);
			}
		}
		System.out.println("L:"+ selectedModule.getProperty(BuilderHelper.getLabelsKey()));
	}

	@Override
	protected void pickTarget(Spatial target) {
		// TODO Auto-generated method stub
		
	}	

}
