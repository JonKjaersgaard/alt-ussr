package ussr.sandbox;

import java.util.logging.Level;

import ussr.description.GeometryDescription;
import ussr.description.SphereShape;
import ussr.description.VectorDescription;
import ussr.description.WorldDescription;
import ussr.physics.jme.JMESimulation;

import com.jme.math.Vector3f;
import com.jme.util.LoggingSystem;

public class StickyBotSimulation {
    public static void main( String[] args ) {
        System.out.println("java.library.path="+System.getProperty("java.library.path"));
        LoggingSystem.getLogger().setLevel( Level.WARNING );
        JMESimulation simulation = new JMESimulation();
        simulation.setRobot(new StickyBot());
        simulation.setWorld(createWorld());
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
