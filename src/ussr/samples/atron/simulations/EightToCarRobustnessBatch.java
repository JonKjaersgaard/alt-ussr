package ussr.samples.atron.simulations;

import java.rmi.RemoteException;
import ussr.samples.atron.simulations.EightToCarRobustnessExperiment.Parameters;

import ussr.remote.AbstractSimulationBatch;
import ussr.remote.facade.ParameterHolder;

public class EightToCarRobustnessBatch extends AbstractSimulationBatch {

    public static void main(String argv[]) {
        new EightToCarRobustnessBatch(EightToCarRobustnessExperiment.class).start();
    }
    
    public EightToCarRobustnessBatch(Class<?> mainClass) {
        super(mainClass);
    }

    @Override
    protected ParameterHolder getNextParameters() {
        Parameters p = new Parameters(0.01f,0.05f,0,70f);
        return p;
    }

    private int c = 3;
    
    @Override
    protected boolean runMoreSimulations() {
        return c-->0;
    }

    public void provideReturnValue(String name, Object value) throws RemoteException {
        if(name.equals("success")) {
            float time = (Float)value;
            System.out.println("Time taken: "+time);
        } else
            System.out.println("Unknown value: "+name);
    }

}
