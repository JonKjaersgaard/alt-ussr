package ussr.builder.assigmentLabels;

import ussr.builder.QuickPrototyping;
import ussr.model.Module;

public interface Labeling {

	
	public abstract void labelObjects(String label,Module selectedModule, int nr);
	
	public abstract void removeLabel(String label,Module selectedModule, int nr);
	
	public abstract void readLabels(Module selectedModule, int nr, QuickPrototyping quickPrototyping);
}
