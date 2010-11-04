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

    // Naming of modules (used to control behavior)
    static final String COUNTERCW_TAG = "#cc#_";
    static final String CLOCKWISE_TAG = "#cw#_";
    static final String BLOCKER_TAG = "#block#_";
    static final String SPINNER_TAG = "#spin#_";
    static final String COUNTER_SPINNER_TAG = "#counterspin#_";
    private static final String CONVEYOR_TAG = "conveyor_";

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
        char element = upperLayerCount<layout.length() ? layout.charAt(upperLayerCount++) : 'P';
        if(element=='R')
            return CONVEYOR_TAG+CLOCKWISE_TAG+id;
        else if(element=='r')
            return CONVEYOR_TAG+COUNTERCW_TAG+id;
        else if(element=='b') {
            setMagicGlobalLiftingModuleCounter(getMagicGlobalLiftingModuleCounter() + 1);
            return CONVEYOR_TAG+BLOCKER_TAG+id;
        } else if(element=='s') { 
            setMagicGlobalLiftingModuleCounter(getMagicGlobalLiftingModuleCounter() + 1);
            return CONVEYOR_TAG+BLOCKER_TAG+SPINNER_TAG+id;
        } else if(element=='S') { 
            setMagicGlobalLiftingModuleCounter(getMagicGlobalLiftingModuleCounter() + 1);
            return CONVEYOR_TAG+BLOCKER_TAG+COUNTER_SPINNER_TAG+id;
        } else if(!(element=='P'))
            throw new Error("Unknown element type: "+element);
        return null;
    }

}
