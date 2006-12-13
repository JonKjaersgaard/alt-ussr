package ussr.sandbox;

import java.util.logging.Level;

import ussr.description.GeometryDescription;
import ussr.description.SphereShape;
import ussr.description.VectorDescription;
import ussr.description.WorldDescription;
import ussr.physics.PhysicsFactory;
import ussr.physics.PhysicsSimulation;
import ussr.physics.jme.JMESimulation;

import com.jme.math.Vector3f;
import com.jme.util.LoggingSystem;

public class StickyBotSimulation {
    
    private static long lastConnectorToggleTime = -1;

    public static void main( String[] args ) {
        System.out.println("java.library.path="+System.getProperty("java.library.path"));
        LoggingSystem.getLogger().setLevel( Level.WARNING );
        final PhysicsSimulation simulation = PhysicsFactory.createSimulator();;
        simulation.setRobot(new StickyBot());
        simulation.setWorld(createWorld());
        // Add control

        // Global connector activation toggle 
        simulation.addInputHandler("Z", new PhysicsSimulation.Handler() {
            public void handle() {
                if(System.currentTimeMillis()-lastConnectorToggleTime<1000) return;
                lastConnectorToggleTime = System.currentTimeMillis();
                simulation.setConnectorsAreActive(!simulation.getConnectorsAreActive());
                if(simulation.getConnectorsAreActive()) System.out.println("Connectors are now active");
                else System.out.println("Connectors are now inactive");
            }
        });

        // Start
        simulation.start();
    }
    
    private static WorldDescription createWorld() {
        WorldDescription world = new WorldDescription();
        world.setNumberOfModules(7);
        world.setPlaneSize(25);
        world.setObstacles(new VectorDescription[] {
                new VectorDescription(0,-2.5f,0),
                new VectorDescription(5,-1.5f,2)
        });
        return world;
    }

}
