package ussr.builder.labelingTools;

import ussr.builder.BuilderHelper;
import ussr.model.Module;

public class ModuleLabel extends Label {
	
	private Module module;
	
	public ModuleLabel(Module module){
	this.module = module;	
	}

	
	
	
	public String getLabels(){
		String labels = module.getProperty(BuilderHelper.getLabelsKey());
		if (labels ==null){
			return "none";
		}
		return labels;
	}




	@Override
	public boolean has(String label) {
		
		if (getLabels().contains(label)){
			return true;
		}
		return false;		
	}
	
	
}
