package ussr.builder.saveLoadXML;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import ussr.builder.helpers.BuilderHelper;
import ussr.description.geometry.RotationDescription;
import ussr.description.geometry.VectorDescription;
import ussr.description.setup.ModulePosition;
import ussr.description.setup.WorldDescription;
import ussr.model.Module;
import ussr.physics.PhysicsLogger;


public class PreSimulationXMLSerializer extends SaveLoadXMLBuilderTemplate {
    /**
     * The world description being filled out
     */
    private WorldDescription world;

    
   // private PhysicsParameters physicsParameters;
    

	/**
     * 
     * For loading simulation robot into simulation.
     * @param world, description of world properties.
     */
    public PreSimulationXMLSerializer(WorldDescription world) {
        this.world = world;
        world.setModulePositions(new ArrayList<ModulePosition>());
    }

    
    /**
     * For loading simulation.
     */
    public PreSimulationXMLSerializer(/*PhysicsParameters physicsParameters*/) {
       /*this.physicsParameters = physicsParameters;*/
    }
    
    @Override
    protected int numberOfSimulatedModules() {
        throw new Error("Pre-sim deserializer does not support serialization");
    }

    @Override
    protected Module getModuleByIndex(int module) {
        throw new Error("Pre-sim deserializer does not support serialization");
    }

    protected void createNewModule(String moduleName, String moduleType, VectorDescription modulePosition, RotationDescription moduleRotation, LinkedList<Color> listColorsComponents,LinkedList<Color> listColorsConnectors, String labelsModule, String[] labelsConnectors){
        ModulePosition modulePos;
        if (moduleType.contains("ATRON")||moduleType.equalsIgnoreCase("default")){
            modulePos= new ModulePosition(moduleName,"ATRON",modulePosition,moduleRotation);
        } else {
            modulePos= new ModulePosition(moduleName,moduleType,modulePosition,moduleRotation);
        }

        world.getModulePositions().add(modulePos);
        
        if(labelsModule.contains(BuilderHelper.getTempLabel())){            
            //do nothing
        }else{  
            // Store labels in module properties, plus extract @-prefixed labels and define as properties
            HashMap<String,String> properties = new HashMap<String,String>();
            properties.put(BuilderHelper.getLabelsKey(), labelsModule);
            modulePos.setProperties(properties);
            String[] labels = labelsModule.split(",");
            for(int i=0; i<labels.length; i++) {
                if(labels[i].startsWith("@") && labels[i].indexOf('=')>1) {
                    String key = labels[i].split("=")[0].substring(1);
                    String value = labels[i].split("=")[1];
                    properties.put(key, value);
                    System.out.println("Setting property "+key+" to "+value);
                }
            }
        }
        
        PhysicsLogger.log("Warning: colors not transferred"); //newModule.setColorList(listColorsComponents);
    }



	@Override
	protected WorldDescription getWorldDescription() {
		 throw new Error("Pre-sim deserializer does not support serialization");
	}


	@Override
	protected InSimulationXMLSerializer getInstance() {
		 throw new Error("Pre-sim deserializer does not support serialization");
	}
}
