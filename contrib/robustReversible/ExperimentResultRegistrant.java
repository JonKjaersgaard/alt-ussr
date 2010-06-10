package robustReversible;

public interface ExperimentResultRegistrant {

    void reportResult(boolean b);

    void reportEvent(String name, float time);

}
