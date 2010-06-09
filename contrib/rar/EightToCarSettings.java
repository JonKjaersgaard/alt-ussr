package rar;

public interface EightToCarSettings {

    // Selection of experiments
    public static final boolean SKIP_EFFICIENCY = true;
    public static final boolean SKIP_ROBUSTNESS = true;
    public static final boolean SKIP_RESET = false;
    public static final boolean SKIP_EFFICIENCY_EXTRA = true;
    public static final Class<?> EXPERIMENTS[] = new Class<?>[] {
        //EightToCarRobustnessExperimentSafeToken32.class,
        //EightToCarRobustnessExperimentSafeToken128.class,
        //EightToCarRobustnessExperimentSafeTokenMaxint.class,
        //EightToCarRobustnessExperimentBroadcast.class,
        //EightToCarRobustnessExperimentParallelLim.class,
        //EightToCarRobustnessExperimentParallelStd.class
        SnakeRobustnessExperimentBroadcast.class,
        SnakeRobustnessExperimentParallelStd.class
    };
    
    // Basic experimental settings
    public static final float TIMEOUT = 200f;
    public static final int N_REPEAT = 7;
    
    // Risk of packet loss
    public static final float START_RISK = 0.0f;
    public static final float END_RISK = 0.0f;
    public static final float RISK_DELTA = 0f;
    public static final float RISK_INC = 0.02f;
    
    // Risk of permanent communication failure
    public static final float START_FAIL = 0.0f;
    public static final float END_FAIL = 0.0f;
    public static final float FAIL_INC = 0.02f;
    public static final float FAIL_COMM_RISK = 0.0f;
    public static final float COMPLETE_FAILURE_DEGREE = 0.99f;
    
    // Risk of spontaneous reset
    public static final float RESET_RISK_PER_TS_MIN = 0.70f;
    public static final float RESET_RISK_PER_TS_MAX = 1.02f;
    public static final float RESET_RISK_PER_TS_DELTA = 0.2f;
    public static final float RESET_RISK_TS_SIZE_MIN = 1;
    public static final float RESET_RISK_TS_SIZE_MAX = 1f;
    public static final float RESET_RISK_TS_SIZE_DELTA = 2;
    
    // Max number of parallel simulations
    public static final int N_PARALLEL_SIMS = 7;
}
