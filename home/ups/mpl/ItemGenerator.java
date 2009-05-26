package mpl;

import java.util.ArrayList;
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

public class ItemGenerator implements PhysicsObserver {

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
    
    PriorityQueue<Event> events = new PriorityQueue<Event>();
    
    public void physicsTimeStepHook(PhysicsSimulation simulation) {
        if(events.size()==0) return;
        float time = simulation.getTime();
        if(time>events.peek().getTime())
            events.poll().perform(simulation);
    }

    public ItemGenerator() {
        //events.add(new Event(2.0f) { public void perform(PhysicsSimulation s) { addBox(s); } });
    }
    
    public void addBox(PhysicsSimulation s) {
        JMESimulation jmes = (JMESimulation)s;
        JMEGeometryHelper helper = (JMEGeometryHelper)jmes.getHelper();
        DynamicPhysicsNode node = (DynamicPhysicsNode)helper.createBox(0.1f, 0.01f, 0.1f, 1f, false);
        node.getLocalTranslation().set(0,1f,0);
        node.getLocalRotation().set(0,0,0,1);
        node.clearDynamics();
        jmes.getObstacles().add(node);
    }

    public void prepareWorld(WorldDescription world) {
        BoxDescription[] boxes = new BoxDescription[] { 
                new BoxDescription(new VectorDescription(0.1f,-0.3f,0.4f), new VectorDescription(0.2f,0.01f,0.2f), new RotationDescription(0,0,0), 10f)
        };
        world.setBigObstacles(boxes);
    }
    
}
