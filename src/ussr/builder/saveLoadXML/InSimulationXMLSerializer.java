package ussr.builder.saveLoadXML;

import java.awt.Color;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.LinkedList;

import ussr.builder.helpers.BuilderHelper;
import ussr.description.geometry.RotationDescription;
import ussr.description.geometry.VectorDescription;
import ussr.description.setup.ModulePosition;
import ussr.description.setup.WorldDescription;
import ussr.model.Connector;
import ussr.model.Module;
import ussr.physics.PhysicsParameters;
import ussr.physics.PhysicsSimulation;
import ussr.physics.jme.JMESimulation;
import ussr.remote.facade.BuilderControlInter;

public class InSimulationXMLSerializer extends SaveLoadXMLBuilderTemplate{
    /**
     * The physical simulation
     */
    private JMESimulation simulation;
    
    private BuilderControlInter builderControl;

    public InSimulationXMLSerializer(PhysicsSimulation simulation) {
        if(!(simulation instanceof JMESimulation)) throw new Error("Internal error: only JME-based simulations supported by builder");
        this.simulation = (JMESimulation)simulation;
        
    }
    
    public InSimulationXMLSerializer(BuilderControlInter builderControl) {
      this.builderControl = builderControl;        
    }
    

    @Override
    protected int numberOfSimulatedModules() {
        //return simulation.getModules().size();
    	
    	int amountModules = 0;
    	try {
    		amountModules = builderControl.getModules().size();
		} catch (RemoteException e) {
			throw new Error("Failed to extract amount of modules from simulation environment, due to remote exception.");
		}
		return amountModules;
    }

    @Override
    protected Module getModuleByIndex(int module) {
        //return simulation.getModules().get(module);
    	Module moduleByIndex = null;
    	try {
			moduleByIndex = builderControl.getModuleByIndex(module);
		} catch (RemoteException e) {
			throw new Error("Failed get module with index "+ module+", due to remote exception.");
		}
    	
    	return moduleByIndex;
    }

    protected void createNewModule(String moduleName, String moduleType, VectorDescription modulePosition, RotationDescription moduleRotation, LinkedList<Color> listColorsComponents,LinkedList<Color> listColorsConnectors, String labelsModule, String[] labelsConnectors){
        Module newModule;
        if (moduleType.contains("ATRON")||moduleType.equalsIgnoreCase("default")){
            ModulePosition modulePos= new ModulePosition(moduleName,"ATRON",modulePosition,moduleRotation);
            //ModulePosition modulePos= new ModulePosition(moduleName,"default",modulePosition,moduleRotation);
            try {
				newModule=  builderControl.createModule(modulePos, true);
			} catch (RemoteException e) {
				throw new Error("Failed to create new module of type "+ modulePos.getType()+", due to remote exception.");
			}
            
            
           // newModule = simulation.createModule(modulePos,true);            
        }else{
            ModulePosition modulePos= new ModulePosition(moduleName,moduleType,modulePosition,moduleRotation);
            try {
				newModule=  builderControl.createModule(modulePos, true);
			} catch (RemoteException e) {
				throw new Error("Failed to create new module of type "+ modulePos.getType()+", due to remote exception.");
			}         
        }

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
        WorldDescription world;
        try {
			world = builderControl.getWorldDescription();
		} catch (RemoteException e) {
			throw new Error ("Failed to extract simulation world decription, due to remote exception.");
		}
		return world;
	}



}
