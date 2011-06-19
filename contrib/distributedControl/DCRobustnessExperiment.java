/**
 * Unified Simulator for Self-Reconfigurable Robots (USSR)
 * (C) University of Southern Denmark 2008
 * This software is distributed under the BSD open-source license.
 * For licensing see the file LICENCE.txt included in the root of the USSR distribution.
 */
package distributedControl;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import ussr.description.Robot;
import ussr.description.geometry.VectorDescription;
import ussr.description.setup.ModulePosition;
import ussr.description.setup.WorldDescription;
import ussr.model.Controller;
import ussr.model.Module;
import ussr.physics.PhysicsFactory;
import ussr.physics.PhysicsLogger;
import ussr.physics.PhysicsObserver;
import ussr.physics.PhysicsParameters;
import ussr.physics.PhysicsSimulation;
import ussr.physics.TimedPhysicsObserver;
import ussr.remote.SimulationClient;
import ussr.remote.facade.ParameterHolder;
import ussr.samples.GenericSimulation;
import ussr.samples.atron.ATRON;
import ussr.samples.atron.ATRONBuilder;
import ussr.samples.atron.ATRONController;
import ussr.samples.atron.GenericATRONSimulation;
import ussr.visualization.VisualizationParameters;

/**
 * Port of the eight-to-car simulation to Java.  Classical ATRON self-reconfiguration example.
 * 
 * @author ups
 */ 
public abstract class DCRobustnessExperiment extends GenericATRONSimulation {

    public static final boolean USE_BLOCKING_ROTATE = true;
    public static final boolean CORRECT_CAR_WHEELS = false;
    public static final boolean VERIFY_OPERATIONS = false;
    public static final int CACHE_SIZE = 64;

    @Override
    protected void simulationHook(PhysicsSimulation simulation) {
        float maxTime = ((DistributedControlRobustnessBatch.Parameters)DistributedControlRobustnessBatch.Parameters.get()).maxTime;
        simulation.waitForPhysicsTimestep(new TimedPhysicsObserver(maxTime) {
            public void physicsTimeStepHook(PhysicsSimulation simulation) {
                reportResult(false);
            }
        });
        ATRONController.activatePacketCounting();
    }
    
    @Override
    protected void setupPhysicsHook() {
        PhysicsLogger.setDisplayInfo(true);
        PhysicsParameters.get().setMaintainRotationalJointPositions(true);
        PhysicsParameters.get().setRealisticCollision(true);
        PhysicsParameters.get().setPhysicsSimulationStepSize(0.005f); // before: 0.0005f
        PhysicsParameters.get().setWorldDampingLinearVelocity(0.9f);
        PhysicsParameters.get().setUseModuleEventQueue(true);
        PhysicsParameters.get().setRealtime(false);
        PhysicsFactory.getOptions().setStartPaused(false);
        //PhysicsFactory.getOptions().setHeadlessNoGraphics(true);
        //PhysicsFactory.getOptions().setHeadlessNoWindow(true);
        //VisualizationParameters.get().setAlwaysShowConnectors(true);
        PhysicsParameters.get().setGravity(0);
    }
    
    protected void changeWorldHook(WorldDescription world) {
        //world.setPlaneTexture(WorldDescription.WHITE_GRID_TEXTURE);
        //world.setHasBackgroundScenery(false);
    }

    protected ArrayList<ModulePosition> buildRobotX() {
        float Yoffset = 0.25f+2*ATRON.UNIT;
        return new ATRONBuilder().buildCar(2, new VectorDescription(0,Yoffset,0));
    }

    protected ArrayList<ModulePosition> buildRobot() {
        float Yoffset = 0.25f+2*ATRON.UNIT;
        ArrayList<ModulePosition> mPos = new ArrayList<ModulePosition>();
        mPos.add(new ModulePosition("#0", new VectorDescription(0*ATRON.UNIT,0*ATRON.UNIT-Yoffset,0*ATRON.UNIT), ATRON.ROTATION_NS_BROKEN));
        mPos.add(new ModulePosition("#1", new VectorDescription(1*ATRON.UNIT,0*ATRON.UNIT-Yoffset,1*ATRON.UNIT), ATRON.ROTATION_EW));
        mPos.add(new ModulePosition("#2", new VectorDescription(1*ATRON.UNIT,0*ATRON.UNIT-Yoffset,-1*ATRON.UNIT), ATRON.ROTATION_EW));
        mPos.add(new ModulePosition("#3", new VectorDescription(2*ATRON.UNIT,0*ATRON.UNIT-Yoffset,0*ATRON.UNIT), ATRON.ROTATION_NS_BROKEN));
            mPos.add(new ModulePosition("#4", new VectorDescription(3*ATRON.UNIT,0*ATRON.UNIT-Yoffset,1*ATRON.UNIT), ATRON.ROTATION_EW));
            mPos.add(new ModulePosition("#5", new VectorDescription(3*ATRON.UNIT,0*ATRON.UNIT-Yoffset,-1*ATRON.UNIT), ATRON.ROTATION_EW));
        mPos.add(new ModulePosition("#6", new VectorDescription(4*ATRON.UNIT,0*ATRON.UNIT-Yoffset,0*ATRON.UNIT), ATRON.ROTATION_NS_BROKEN));
        return mPos;
    }

    public void reportResult(boolean success) throws Error {
        PhysicsSimulation sim = GenericSimulation.getPhysicsSimulation();
        String experiment = ParameterHolder.get().toString();
        try {
            if(SimulationClient.getReturnHandler()==null) {
                System.err.println("No return handler specified; time taken = "+sim.getTime());
                System.exit(0);
            }
            if(success)
                SimulationClient.getReturnHandler().provideReturnValue(experiment,"success",new Object[] { sim.getTime(), ATRONController.getPacketsSentCount() });
            else
                SimulationClient.getReturnHandler().provideReturnValue(experiment,"timeout", ATRONController.getPacketsSentCount());
            System.exit(0);
        } catch(RemoteException exn) {
            throw new Error("Unable to report return value");
        }
    }
    
    public void reportEvent(String name, float time) {
        String experiment = ParameterHolder.get().toString();
        if(SimulationClient.getReturnHandler()==null) {
            System.err.println("No return handler specified, cannot report event");
            return;
        }
        try {
            SimulationClient.getReturnHandler().provideEventNotification(experiment,name,time);
        } catch (RemoteException e) {
            System.err.println("Unable to report event");
        }
    }

}
