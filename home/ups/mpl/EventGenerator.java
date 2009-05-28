package mpl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import com.jmex.physics.DynamicPhysicsNode;
import com.jmex.physics.PhysicsNode;

import ussr.description.geometry.RotationDescription;
import ussr.description.geometry.VectorDescription;
import ussr.description.setup.BoxDescription;
import ussr.description.setup.WorldDescription;
import ussr.physics.PhysicsObserver;
import ussr.physics.PhysicsSimulation;
import ussr.physics.jme.JMEGeometryHelper;
import ussr.physics.jme.JMESimulation;

public class EventGenerator implements PhysicsObserver {

    public static abstract class Event implements Comparable<Event> {
        private float time;
        public Event(float time) { this.time = time; }
        public abstract void perform(PhysicsSimulation simulation);
        public float getTime() { return time; }
        public int compareTo(Event other) {
            if(time<other.getTime()) return -1;
            if(time==other.getTime()) return 0;
            return 1;
        }
    }
    
    private PriorityQueue<Event> events = new PriorityQueue<Event>();
    private VectorDescription initialBoxPosition;
    
    public void physicsTimeStepHook(PhysicsSimulation simulation) {
        if(events.size()==0) return;
        float time = simulation.getTime();
        if(time>events.peek().getTime())
            events.poll().perform(simulation);
    }

    public EventGenerator(final VectorDescription target, final String outputFileName) {
        events.add(new Event(0) { public void perform(PhysicsSimulation sim) {
            initialBoxPosition = getBoxPosition(sim);
            writeToFile(outputFileName,Configuration.FITNESS_SIMULATION_FAILED);
        } });
        events.add(new Event(20.0f) { public void perform(PhysicsSimulation sim) {
            VectorDescription vector = getBoxPosition(sim);
            float result = Configuration.computeFitness(initialBoxPosition,vector,target);
            writeToFile(outputFileName,result);
            System.err.println("QUIT");
            System.exit(0);
        }});
    }
    


    private VectorDescription getBoxPosition(PhysicsSimulation sim) {
        List<VectorDescription> obstaclePositions = sim.getObstaclePositions();
        VectorDescription vector = obstaclePositions.get(0);
        return vector;
    } 
    
    protected void writeToFile(String outputFileName, float result) {
        File file = new File(outputFileName);
        try {
            PrintWriter writer = new PrintWriter(new FileWriter(file));
            writer.println(result);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            throw new Error("Unable to write output");
        }
    }

    public void prepareWorld(WorldDescription world) {
        BoxDescription[] boxes = new BoxDescription[] { 
                new BoxDescription(Configuration.boxInitialPosition(), Configuration.BOX_SIZE, Configuration.boxInitialRotation(), Configuration.BOX_MASS)
        };
        world.setBigObstacles(boxes);
    }
    
}
