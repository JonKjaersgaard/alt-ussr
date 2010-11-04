package morphingProductionLine.simpleExample;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import morphingProductionLine.abstractConveyor.AbstractMPLSimulation;
import morphingProductionLine.abstractConveyor.Element;

import ussr.description.Robot;
import ussr.description.geometry.RotationDescription;
import ussr.description.geometry.VectorDescription;
import ussr.description.setup.BoxDescription;
import ussr.description.setup.ModulePosition;
import ussr.description.setup.WorldDescription;
import ussr.physics.PhysicsFactory;
import ussr.physics.PhysicsParameters;
import ussr.physics.PhysicsSimulation;
import ussr.samples.atron.ATRON;
import ussr.samples.atron.ATRONBuilder;
import ussr.samples.atron.GenericATRONSimulation;
import ussr.samples.atron.ATRONBuilder.ModuleSelector;
import ussr.samples.atron.ATRONBuilder.Namer;

/**
 * A simulation of an ATRON conveyor belt that moves a box. Preliminary!
 * 
 * @author Modular Robots @ MMMI
 */
public class SimpleMPLSimulation extends AbstractMPLSimulation {

    public SimpleMPLSimulation() {
        setEventGenerator(new SimpleEventGenerator());
    }

    public static void main(String argv[]) {
        String homeDir = parseParam(argv,"home","../ussr/"); 
        if(homeDir!=null) PhysicsFactory.getOptions().setResourceDirectory(homeDir);
        SimpleMPLSimulation main = new SimpleMPLSimulation();
        main.main();
    }

    private static String parseParam(String[] argv, String name, String defaultValue) {
        for(int i=0; i<argv.length; i++) {
            if(argv[i].startsWith(name+'=')) {
                return argv[i].substring(argv[i].indexOf('=')+1);
            }
        }
        return defaultValue;
    }

    private static final String layout = 
        "PrPrPrPrPP"+
        "PPPrPPPrPP"+
        "PPPPPPPPPP"+
        "PPrPPPrPPP"+
        "PsPsPsPsPP";
    
    private int upperLayerCount;
    @Override
    protected String getTransportLayerModuleBehavior(int id, int x, int z) {
        Element element = Element.fromChar(upperLayerCount<layout.length() ? layout.charAt(upperLayerCount++) : 'P');
        if(element==Element.ROTATING_CLOCKWISE)
            return CONVEYOR_TAG+CLOCKWISE_TAG+id;
        else if(element==Element.ROTATING_COUNTERCW)
            return CONVEYOR_TAG+COUNTERCW_TAG+id;
        else if(element==Element.BLOCKER) {
            setMagicGlobalLiftingModuleCounter(getMagicGlobalLiftingModuleCounter() + 1);
            return CONVEYOR_TAG+BLOCKER_TAG+id;
        } else if(element==Element.SPINNER) { 
            setMagicGlobalLiftingModuleCounter(getMagicGlobalLiftingModuleCounter() + 1);
            return CONVEYOR_TAG+BLOCKER_TAG+SPINNER_TAG+id;
        } else if(element==Element.COUNTER_SPINNER) { 
            setMagicGlobalLiftingModuleCounter(getMagicGlobalLiftingModuleCounter() + 1);
            return CONVEYOR_TAG+BLOCKER_TAG+COUNTER_SPINNER_TAG+id;
        } else if(!(element==Element.PLAIN))
            throw new Error("Unknown element type: "+element);
        return null;
    }

}
