package ussr.builder.genericTools;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.jme.scene.Geometry;

import ussr.builder.labelingTools.LabelingToolSpecification;
import ussr.model.Module;
import ussr.physics.jme.JMEModuleComponent;
import ussr.physics.jme.JMESimulation;
import ussr.physics.jme.pickers.CustomizedPicker;

public class Identifier extends CustomizedPicker {


	private List<Color> moduleComponentsColors;

	private TypesIdentifier typeIndentifier;


	private Module selectedModule;	

	private LabelingToolSpecification labelingToolSpecification;

	public Identifier(TypesIdentifier typeIndentifier, LabelingToolSpecification labelingToolSpecification){
		this.typeIndentifier = typeIndentifier;
		this.labelingToolSpecification = labelingToolSpecification;
	}


	@Override
	protected void pickModuleComponent(JMEModuleComponent component) {
		
		if (typeIndentifier.equals(TypesIdentifier.MODULE)){
			this.moduleComponentsColors = component.getModel().getColorList();

		    selectedModule = component.getModel();

			component.getModel().setColor(Color.GREEN);

		}
		//JMESimulation sim = (JMESimulation)component.getSimulation();
		//sim.setPicker(labelingToolSpecification);
		labelingToolSpecification.getLabeling().readLabels(labelingToolSpecification);
		
		//jmeSimulation.setPicker(new LabelingToolSpecification(jmeSimulation,LabeledEntities.MODULE,LabelingTools.READ_LABELS, null));

	}

	@Override
	protected void pickTarget(Geometry target) {
		// TODO Auto-generated method stub

	}

	public List<Color> getModuleComponentsColors() {
		return moduleComponentsColors;
	}

	
	public Module getSelectedModule() {
		return selectedModule;
	}

}
