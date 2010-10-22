package morphingProductionLine.abstractConveyor;

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

public abstract class AbstractEventGenerator implements PhysicsObserver {

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

    public abstract void prepareWorld(WorldDescription world);

    protected void addEvent(Event event) {
        events.add(event);
    }



    
}
