package ussr.physics.jme.pickers;

import ussr.physics.jme.JMESimulation;

import com.jme.input.InputHandler;
import com.jme.scene.Node;
import com.jmex.physics.PhysicsSpace;

public interface Picker {
    public void attach( JMESimulation simulation, InputHandler input, Node rootNode, PhysicsSpace physicsSpace );
    public void delete();
}
