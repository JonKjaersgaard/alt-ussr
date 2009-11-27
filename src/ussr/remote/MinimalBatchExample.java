package ussr.remote;

import java.rmi.RemoteException;

import ussr.remote.facade.ParameterHolder;
import ussr.samples.atron.simulations.ATRONCarController1;
import ussr.samples.atron.simulations.ATRONCarSimulation;

public class MinimalBatchExample extends AbstractSimulationBatch {

    public static final int N_SIMULATIONS = 5;

    private int simulationsRun;
    
    public static class Parameters extends ParameterHolder {
        private int nSecondsToRun;
        public Parameters(int nSecondsToRun) {
            super(MinimalBatchExampleSimulation.class);
            this.nSecondsToRun = nSecondsToRun;
        }
        public int getSecondsToRun() { return nSecondsToRun; }
        // Note: toString method used to identify experiments
        public String toString() { return "Example, runtime="+nSecondsToRun; }
    }
    
    @Override
    protected ParameterHolder getNextParameters() {
        return new Parameters(simulationsRun++);
    }

    @Override
    protected boolean runMoreSimulations() {
        return simulationsRun<N_SIMULATIONS;
    }

    @Override
    public void provideEventNotification(String experiment, String name, float time) throws RemoteException {
        System.out.println("Event received from "+experiment+": "+name+" at time "+time);
    }

    @Override
    public void provideReturnValue(String experiment, String result, Object value) throws RemoteException {
        System.out.println("Experiment "+experiment+" completed with result "+result+", data="+value);
    }

    public static void main(String argv[]) {
        new MinimalBatchExample().start(1,false); // 1 for desktops, 10 works well for server in basement...
    }
    
}
