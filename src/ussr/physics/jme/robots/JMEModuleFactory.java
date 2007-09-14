package ussr.physics.jme.robots;

import ussr.model.Module;
import ussr.physics.jme.JMESimulation;
import ussr.robotbuildingblocks.Robot;

public interface JMEModuleFactory {
    public void createModule(int module_id, final Module module, Robot robot, String module_name);
    public String getModulePrefix();
    public void setSimulation(JMESimulation simulation);
}
