package ussr.builder.construction;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import ussr.builder.BuilderHelper;
import ussr.description.geometry.RotationDescription;
import ussr.description.geometry.VectorDescription;
import ussr.description.setup.ModuleConnection;
import ussr.description.setup.ModulePosition;
import ussr.model.Module;
import ussr.physics.jme.JMESimulation;
import ussr.samples.mtran.MTRANSimulation;

public class MTRANOperationsTemplate extends CommonOperationsTemplate{
	
	public MTRANOperationsTemplate(JMESimulation simulation) {
		super(simulation);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Adds default (the first) MTRAN construction module at specified position.
	 * This method is so-called "Primitive operation" for  TEMPLATE method, called "addDefaultConstructionModule(String type, VectorDescription modulePosition)".	 
	 * @param type, the type of modular robot. In this case it is MTRAN.
	 * @param modulePosition, the position of the MTRAN module in simulation environment.
	 * @param moduleRotation, the rotation of the MTRAN module.
	 * @param colorsComponents, the colours of components constituting the MTRAN module.
	 * @param colorsConectors, the colours of connectors on the MTRAN module.
	 */
	@Override
	public void addDefaultModule(String type, VectorDescription modulePosition,	RotationDescription moduleRotation, List<Color> colorsComponents, ArrayList<Color> colorsConectors) {
		colorsComponents.add(Color.BLUE);colorsComponents.add(Color.BLACK);colorsComponents.add(Color.RED);
		colorsConectors.add(Color.BLACK);colorsConectors.add(Color.BLACK);
		colorsConectors.add(Color.BLACK);colorsConectors.add(Color.WHITE);
		colorsConectors.add(Color.WHITE);colorsConectors.add(Color.WHITE);
		moduleRotation = MTRANSimulation.ORI2;
		addNewModule(new ModulePosition(type+ BuilderHelper.getRandomInt(),type,modulePosition,moduleRotation),colorsComponents,colorsConectors);
	}	

	/**
	 * Creates and returns new copy of MTRAN module from the selected module.
	 * This method is so-called "Primitive operation" for above TEMPLATE method, called "addNewModuleOnConnector(ConstructionToolSpecification toolSpecification)".	 
	 * @param selectedModule,the module selected in simulation environment.
	 * @return module, newly created MTRAN module.  
	 */	
	@Override
	public Module createNewModule(Module selectedModule) {
		Module module = addNewCopyModule(selectedModule);
		return module;
	}

	/**
	 * Additional method for implementing unique properties of modular robots.Here the new functionality
	 * is that each MTRAN module can be rotated 90 degrees around its axis. 
	 * This method is so-called "Primitive operation" for TEMPLATE method, called "variateModule(ConstructionToolSpecification toolSpecification)".	    
	 * @param selectedModule,the module selected in simulation environment.	
	 */
	@Override
	public void variateSpecificModule(Module selectedModule) {
		getConstruction().variateModuleProperties(selectedModule);		
	}
	
}