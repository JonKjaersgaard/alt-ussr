package ussr.builder.constructionTools;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import ckbot.CKBotStandard;

import ussr.builder.BuilderHelper;
import ussr.description.geometry.RotationDescription;
import ussr.description.geometry.VectorDescription;
import ussr.description.setup.ModulePosition;
import ussr.model.Module;
import ussr.physics.jme.JMESimulation;
import ussr.samples.atron.ATRON;

public class CKBotOperationsTemplate extends CommonOperationsTemplate {

	/**
	 * Supports construction of CKBot modular robot morphology in more abstract module oriented way.
	 * @param simulation, the physical simulation.
	 */
	public CKBotOperationsTemplate(JMESimulation simulation) {
		super(simulation);		
	}
	
	
	@Override
	public void addDefaultModule(String type, VectorDescription modulePosition,RotationDescription moduleRotation, List<Color> colorsComponents,ArrayList<Color> colorsConectors) {
		colorsComponents.add(Color.RED);colorsComponents.add(Color.BLUE);
		colorsConectors.add(Color.WHITE);colorsConectors.add(Color.WHITE);
		colorsConectors.add(Color.WHITE);colorsConectors.add(Color.WHITE);
		moduleRotation = CKBotStandard.ROTATION_0_MINUS90X_MINUS90Z;
		addNewModule(new ModulePosition(type+ BuilderHelper.getRandomInt(),"CKBotStandard",modulePosition,moduleRotation),colorsComponents,colorsConectors);
		
	}

	@Override
	public Module createNewModule(Module selectedModule) {
		Module module = addNewCopyModule(selectedModule);
		return module;
	}

	@Override
	public ConstructionTemplate getConstruction() {
			return construction = new CKBotConstructionTemplate(simulation);
	}

	@Override
	public void variateSpecificModule(Module selectedModule) {
		// TODO Auto-generated method stub
		
	}

}
