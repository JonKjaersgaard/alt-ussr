package ussr.builder.saveLoadXML;

import java.awt.Color;
import java.util.ArrayList;
import java.util.LinkedList;

import ussr.builder.BuilderHelper;
import ussr.description.geometry.RotationDescription;
import ussr.description.geometry.VectorDescription;
import ussr.description.setup.ModulePosition;
import ussr.description.setup.WorldDescription;
import ussr.model.Connector;
import ussr.model.Module;
import ussr.physics.PhysicsLogger;
import ussr.physics.PhysicsSimulation;
import ussr.physics.jme.JMESimulation;

public class PreSimulationXMLSerializer extends SaveLoadXMLBuilderTemplate {
    /**
     * The world description being filled out
     */
    private WorldDescription world;

    public PreSimulationXMLSerializer(WorldDescription world) {
        this.world = world;
        world.setModulePositions(new ArrayList<ModulePosition>());
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
            PhysicsLogger.log("Warning: labels not transferred");
        }
        
        PhysicsLogger.log("Warning: colors not transferred"); //newModule.setColorList(listColorsComponents);
    }

}
