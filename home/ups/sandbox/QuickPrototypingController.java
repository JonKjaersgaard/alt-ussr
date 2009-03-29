package sandbox;

import ussr.model.Module;

public interface QuickPrototypingController {
    public String[] labels();
    public void run(Module module, QuickPrototypingRuntime runtime);
    public void handle(String messageName, Object[] arguments);
}
