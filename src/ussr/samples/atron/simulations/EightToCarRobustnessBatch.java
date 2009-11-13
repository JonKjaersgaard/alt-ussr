package ussr.samples.atron.simulations;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import ussr.samples.atron.simulations.EightToCarRobustnessExperiment.Parameters;

import ussr.remote.AbstractSimulationBatch;
import ussr.remote.facade.ParameterHolder;

public class EightToCarRobustnessBatch extends AbstractSimulationBatch {
    private static final float TIMEOUT = 270f;
    public static final int N_REPEAT = 15;
    public static final float START_RISK = 0;
    public static final float END_RISK = 0.8f;
    public static final float RISK_DELTA = 0.4f;
    public static final float RISK_INC = 0.1f;
    public static final float START_FAIL = 0;
    public static final float END_FAIL = 0.1f;
    public static final float FAIL_INC = 0.1f;
    public static final int N_PARALLEL_SIMS = 2;
    public static final Class<? extends EightToCarRobustnessExperiment> MAINCLASS = EightToCarRobustnessExperimentToken.class;
    
    private List<ParameterHolder> parameters = new LinkedList<ParameterHolder>();
    private PrintWriter logfile;
    
    public static void main(String argv[]) {
        new EightToCarRobustnessBatch(MAINCLASS).start(N_PARALLEL_SIMS);
    }
    
    public EightToCarRobustnessBatch(Class<?> mainClass) {
        super(mainClass);
        int counter = 0;
        for(float fail = START_FAIL; fail<=END_FAIL; fail+=FAIL_INC) {
            for(float risk = START_RISK; risk<=END_RISK; risk+=RISK_INC) {
                for(int i=0; i<N_REPEAT; i++) {
                    parameters.add(new Parameters(counter++,Math.max(0, risk-RISK_DELTA),risk,fail,TIMEOUT));
                }
            }
        }
        try {
            logfile = new PrintWriter(new BufferedWriter(new FileWriter("eight-log.txt")));
        } catch(IOException exn) {
            throw new Error("Unable to open log file");
        }
        logfile.println("Starting "+parameters.size()+" experiments");
    }

    @Override
    protected ParameterHolder getNextParameters() {
        logfile.println("experiment "+parameters.get(0)+" starting"); logfile.flush();
        return parameters.remove(0);
    }

    @Override
    protected boolean runMoreSimulations() {
        return parameters.size()>0;
    }

    public void provideReturnValue(String experiment, String name, Object value) throws RemoteException {
        logfile.print("experiment "+experiment+" completed: ");
        if(name.equals("success")) {
            float time = (Float)value;
            logfile.println("Time taken:"+time);
            recordSuccess(experiment,time);
        } else if(name.equals("timeout")) {
            logfile.println("Timeout:X");
            recordFailure(experiment);
        }
        else {
            logfile.println("Unknown value: "+name);
            recordFailure(experiment);
        }
        logfile.flush();
    }

}
