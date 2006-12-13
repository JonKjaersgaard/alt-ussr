package ussr.physics;

import ussr.description.WorldDescription;
import ussr.model.Robot;

public interface PhysicsSimulation {

    public void addInputHandler(String keyName, Handler handler);

    public void setRobot(Robot bot);

    public void setWorld(WorldDescription world);

    public static interface Handler { 
        public void handle();
    }
    
    public void start();
    
    // Temporary hack
    public boolean getConnectorsAreActive();
    public void setConnectorsAreActive(boolean connectorsAreActive);
}