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
	
	public AssignRemoveLabels(String label, boolean remove){
		this.label = label;
		this.remove = remove;		
	}
	
	
	@Override
	protected void pickModuleComponent(JMEModuleComponent component) {	
		Module selectedModule = component.getModel();
		String labels = selectedModule.getProperty(BuilderHelper.getModuleLabelsKey());
		if (labels == null && remove == false){
			selectedModule.setProperty(BuilderHelper.getModuleLabelsKey(), label +",");			
		}else if (labels != null && remove ==true && labels.contains(label)){
			String changedLabels = labels.replaceAll(label+",", "");
			selectedModule.setProperty(BuilderHelper.getModuleLabelsKey(), changedLabels);
		}else if (remove == false){
			if (labels.contains(label)){
				//do nothing
			}else{
			selectedModule.setProperty(BuilderHelper.getModuleLabelsKey(), labels+label+",");
			}
		}
		System.out.println("L:"+ selectedModule.getProperty(BuilderHelper.getModuleLabelsKey()));
	}

	@Override
	protected void pickTarget(Spatial target) {
		// TODO Auto-generated method stub
		
	}	

}
