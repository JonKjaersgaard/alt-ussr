package rar;

public interface EightToCarSettings {

    public static final boolean SKIP_EFFICIENCY = false;
    public static final boolean SKIP_ROBUSTNESS = true;
    public static final float TIMEOUT = 400f;
    public static final int N_REPEAT = 20;
    public static final float START_RISK = 0.8f;
    public static final float END_RISK = 0.99f;
    public static final float RISK_DELTA = 0.0f;
    public static final float RISK_INC = 0.02f;
    public static final float START_FAIL = 0;
    public static final float END_FAIL = 0.101f;
    public static final float FAIL_INC = 0.01f;
    public static final int N_PARALLEL_SIMS = 2;
    public static final Class<?> EXPERIMENTS[] = new Class<?>[] {
        EightToCarRobustnessExperimentSafeToken32.class,
        EightToCarRobustnessExperimentSafeToken128.class,
        EightToCarRobustnessExperimentSafeTokenMaxint.class,
        EightToCarRobustnessExperimentBroadcast.class,
        EightToCarRobustnessExperimentParallelLim.class,
        EightToCarRobustnessExperimentParallelStd.class
    };

}
