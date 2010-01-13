package ussr.builder.saveLoadXML;

import java.awt.Color;
import java.util.LinkedList;

import ussr.builder.helpers.BuilderHelper;
import ussr.description.geometry.RotationDescription;
import ussr.description.geometry.VectorDescription;
import ussr.description.setup.ModulePosition;
import ussr.description.setup.WorldDescription;
import ussr.model.Connector;
import ussr.model.Module;
import ussr.physics.PhysicsSimulation;
import ussr.physics.jme.JMESimulation;

public class InSimulationXMLSerializer extends SaveLoadXMLBuilderTemplate{
    /**
     * The physical simulation
     */
    private JMESimulation simulation;
    
  
    public InSimulationXMLSerializer(PhysicsSimulation simulation) {
        if(!(simulation instanceof JMESimulation)) throw new Error("Internal error: only JME-based simulations supported by builder");
        this.simulation = (JMESimulation)simulation;
        
    }
    
    /**
	  * Returns instance of XML processing class.
	 * @return  current instance of XML processing.
	 */
    protected  InSimulationXMLSerializer getInstance(){
    	return this;
    }
    
    
    @Override
    protected int numberOfSimulatedModules() {
        return simulation.getModules().size();
    }

    @Override
    protected Module getModuleByIndex(int module) {
        return simulation.getModules().get(module);    	
    }

    protected void createNewModule(String moduleName, String moduleType, VectorDescription modulePosition, RotationDescription moduleRotation, LinkedList<Color> listColorsComponents,LinkedList<Color> listColorsConnectors, String labelsModule, String[] labelsConnectors){
        Module newModule;
     /*   if (moduleType.contains("ATRON")||moduleType.equalsIgnoreCase("default")){
            ModulePosition modulePos= new ModulePosition(moduleName,"ATRON",modulePosition,moduleRotation);
            //ModulePosition modulePos= new ModulePosition(moduleName,"default",modulePosition,moduleRotation);
            newModule = simulation.createModule(modulePos,true);            
        }else{*/
            ModulePosition modulePos= new ModulePosition(moduleName,moduleType,modulePosition,moduleRotation);
            newModule = simulation.createModule(modulePos,true);        
        //}

        if(labelsModule.contains(BuilderHelper.getTempLabel())){            
            //do nothing
        }else{  
        newModule.setProperty(BuilderHelper.getLabelsKey(), labelsModule);
        }
        
        newModule.setColorList(listColorsComponents);

        int amountConnentors  = newModule.getConnectors().size();

        for (int connector =0; connector< amountConnentors; connector++ ){
            Connector currentConnector =newModule.getConnectors().get(connector); 
            currentConnector.setColor(listColorsConnectors.get(connector));
            if(labelsConnectors[connector].contains(BuilderHelper.getTempLabel())){
                //do nothing
            }else{
            currentConnector.setProperty(BuilderHelper.getLabelsKey(), labelsConnectors[connector]);
            }
        }
        
        
        //newModule.setColorList(colorsComponents);     
        //setColorsConnectors(simulation,newModule.getID(),colorsConnectors);       
    }

	@Override
	protected WorldDescription getWorldDescription() {
      return simulation.getWorldDescription();
	}

	



}
