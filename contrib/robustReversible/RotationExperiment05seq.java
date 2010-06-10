/**
 * Unified Simulator for Self-Reconfigurable Robots (USSR)
 * (C) University of Southern Denmark 2008
 * This software is distributed under the BSD open-source license.
 * For licensing see the file LICENCE.txt included in the root of the USSR distribution.
 */
package robustReversible;


import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import robustReversible.gen.carsnakeSimpleGen_seq;
import robustReversible.gen.rotateGen10s;
import robustReversible.gen.rotateGen5s;
import robustReversible.gen.snakeGen_seq;


import ussr.description.Robot;
import ussr.description.geometry.RotationDescription;
import ussr.description.geometry.VectorDescription;
import ussr.description.setup.ModulePosition;
import ussr.description.setup.WorldDescription;
import ussr.model.Controller;
import ussr.physics.PhysicsFactory;
import ussr.physics.PhysicsLogger;
import ussr.physics.PhysicsObserver;
import ussr.physics.PhysicsParameters;
import ussr.physics.PhysicsSimulation;
import ussr.physics.TimedPhysicsObserver;
import ussr.remote.SimulationClient;
import ussr.remote.facade.ParameterHolder;
import ussr.samples.atron.ATRON;
import ussr.samples.atron.ATRONBuilder;
import ussr.samples.atron.ATRONController;
import ussr.samples.atron.GenericATRONSimulation;
import ussr.samples.atron.ATRONBuilder.ModuleSelector;
import ussr.samples.atron.ATRONBuilder.Namer;

/**
 * Port of the eight-to-car simulation to Java.  Classical ATRON self-reconfiguration example.
 * 
 * @author ups
 */ 
public class RotationExperiment05seq extends EightToCarRobustnessExperiment implements ExperimentResultRegistrant {

    public static void main(String argv[]) {
        if(ParameterHolder.get()==null)
            //ParameterHolder.set(new EightToCarRobustnessBatch.Parameters(null,0,0.5f,0.75f,0.0f,Float.MAX_VALUE,17));
        //ParameterHolder.set(new Parameters(0,0.0f,0.0f,0.0f,Float.MAX_VALUE));
        ParameterHolder.set(new EightToCarRobustnessBatch.Parameters(null,0,0.0f,0.0f,0.0f,Float.MAX_VALUE,100f,0.0f,1));
        new RotationExperiment05seq().main(); 
    }

    @Override
    protected Robot getRobot() {
        return new ATRON() {
            public Controller createController() {
                StateMachine machine = new rotateGen5s();
                return new ATRONStateMachineAPI(machine,RotationExperiment05seq.this);
            }
        };
    }

    protected ArrayList<ModulePosition> buildRobot() {
        ATRONBuilder builder = new ATRONBuilder();
        Namer namer = new Namer() {
            @Override
            public String name(int number, VectorDescription pos, RotationDescription rot, int lx, int ly, int lz) {
                return "m"+number;
            }
        };
        ModuleSelector selector = new ModuleSelector() {
            @Override
            public String select(String name, int index, VectorDescription pos, RotationDescription rot, int lx, int ly, int lz) {
                return null;
            }
            
        };
        return builder.buildAsNamedLattice(5, 5, 1, 2, namer, selector, ATRON.UNIT);
    }
    @Override
    protected void setupPhysicsHook() {
        super.setupPhysicsHook();
        DistributedStateManager.MAX_N_PENDING_STATES = 10;
    }
}
