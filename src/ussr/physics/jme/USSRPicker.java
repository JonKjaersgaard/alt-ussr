package ussr.physics.jme;

import com.jme.input.InputHandler;
import com.jme.math.Vector3f;
import com.jme.scene.Node;
import com.jmex.physics.DynamicPhysicsNode;
import com.jmex.physics.PhysicsSpace;

import com.jme.input.InputHandler;
import com.jme.input.action.InputAction;
import com.jme.input.action.InputActionEvent;
import com.jme.intersection.PickData;
import com.jme.intersection.TrianglePickResults;
import com.jme.math.Ray;
import com.jme.math.Vector2f;
import com.jme.math.Vector3f;
import com.jme.scene.Node;
import com.jme.scene.Spatial;
import com.jme.system.DisplaySystem;
import com.jmex.physics.DynamicPhysicsNode;
import com.jmex.physics.Joint;
import com.jmex.physics.PhysicsSpace;

/**
 * A small tool to be able to pick the visual of physics nodes and move them around with the mouse.
 *
 * @author Irrisor, modified by ups to be USSR-specific
 */
public class USSRPicker {

    private JMESimulation simulation;
    /**
     * root node of the scene - used for picking.
     */
    private final Node rootNode;
    private USSRPicker.PickAction pickAction;
    private USSRPicker.MoveAction moveAction;

    /**
     * Constructor of the class.
     *
     * @param input        where {@link #getInputHandler()} is attached to
     * @param rootNode     root node of the scene - used for picking
     * @param physicsSpace physics space to create joints in (picked nodes must reside in there)
     */
    public USSRPicker( JMESimulation simulation, InputHandler input, Node rootNode, PhysicsSpace physicsSpace ) {
        this.simulation = simulation;
        this.inputHandler = new InputHandler();
        input.addToAttachedHandlers( this.inputHandler );
        this.rootNode = rootNode;
        activateUSSRPicker();
    }

    /**
     * @return the input handler for this picker
     */
    public InputHandler getInputHandler() {
        return inputHandler;
    }

    private InputHandler inputHandler;

    private final Vector2f mousePosition = new Vector2f();

    private void activateUSSRPicker() {
        pickAction = new PickAction();
        inputHandler.addAction( pickAction, InputHandler.DEVICE_MOUSE, 0, InputHandler.AXIS_NONE, false );
        moveAction = new MoveAction();
        inputHandler.addAction( moveAction, InputHandler.DEVICE_MOUSE, InputHandler.BUTTON_NONE, InputHandler.AXIS_ALL, false );
    }

    private void attach(DynamicPhysicsNode node) {
        for(JMEModuleComponent component: simulation.getModuleComponents()) {
            if(component.getNodes().contains(node)) {
                System.out.println("Found: "+component.getModel().getProperty("name"));
                for(DynamicPhysicsNode part: component.getNodes())
                    part.setLocalTranslation(part.getLocalTranslation().add(new Vector3f(0.2f,0.2f,0.2f)));
            }
        }
    }

    public void delete() {
        inputHandler.removeAction( pickAction );
        inputHandler.removeAction(moveAction);
    }
    
    private class PickAction extends InputAction {
        private final Ray pickRay = new Ray();
        private final TrianglePickResults pickResults = new TrianglePickResults();

        public void performAction( InputActionEvent evt ) {
            if ( evt.getTriggerPressed() ) {
                DisplaySystem.getDisplaySystem().getWorldCoordinates( mousePosition, 0, pickRay.origin );
                DisplaySystem.getDisplaySystem().getWorldCoordinates( mousePosition, 0.3f, pickRay.direction );
                pickRay.direction.subtractLocal( pickRay.origin ).normalizeLocal();

                pickResults.clear();
                pickResults.setCheckDistance( true );
                rootNode.findPick( pickRay, pickResults );
                /* To avoid using overly large amount of memory on interactive clicking, clear the collision tree */
                rootNode.clearCollisionTree();
                loopResults:
                    for ( int i = 0; i < pickResults.getNumber(); i++ ) {
                        PickData data = pickResults.getPickData( i );
                        if ( data.getTargetTris() != null && data.getTargetTris().size() > 0 ) {
                            Spatial target = data.getTargetMesh().getParentGeom();
                            while ( target != null ) {
                                if ( target instanceof DynamicPhysicsNode ) {
                                    DynamicPhysicsNode picked = (DynamicPhysicsNode) target;
                                    attach( picked );
                                    break loopResults;
                                }
                                target = target.getParent();
                            }
                        }
                    }
            }
        }
    }

    private class MoveAction extends InputAction {

        public void performAction( InputActionEvent evt ) {

            switch ( evt.getTriggerIndex() ) {
            case 0:
                mousePosition.x = evt.getTriggerPosition() * DisplaySystem.getDisplaySystem().getWidth();
                break;
            case 1:
                mousePosition.y = evt.getTriggerPosition() * DisplaySystem.getDisplaySystem().getHeight();
                break;
            }
        }

    }
}
