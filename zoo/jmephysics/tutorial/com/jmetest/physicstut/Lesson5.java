/*Copyright*/
package com.jmetest.physicstut;

import java.util.logging.Level;

import com.jme.input.InputHandler;
import com.jme.input.KeyInput;
import com.jme.input.action.InputAction;
import com.jme.input.action.InputActionEvent;
import com.jme.math.Vector3f;
import com.jme.util.LoggingSystem;
import com.jmex.physics.DynamicPhysicsNode;
import com.jmex.physics.PhysicsSpace;
import com.jmex.physics.PhysicsUpdateCallback;
import com.jmex.physics.StaticPhysicsNode;
import com.jmex.physics.geometry.PhysicsBox;
import com.jmex.physics.util.SimplePhysicsGame;

/**
 * This class shows how to apply forces continuously. Again a sphere that can be moved with forces
 * by pressing keys.
 *
 * @author Irrisor
 */
public class Lesson5 extends SimplePhysicsGame {
    private InputHandler physicsStepInputHandler;
    private DynamicPhysicsNode dynamicNode;

    protected void simpleInitGame() {
        // first we will create a floor and sphere like in Lesson4
        StaticPhysicsNode staticNode = getPhysicsSpace().createStaticNode();
        rootNode.attachChild( staticNode );
        PhysicsBox floorBox = staticNode.createBox( "floor" );
        floorBox.getLocalScale().set( 10, 0.5f, 10 );
        dynamicNode = getPhysicsSpace().createDynamicNode();
        rootNode.attachChild( dynamicNode );
        dynamicNode.createSphere( "rolling sphere" );
        dynamicNode.getLocalTranslation().set( 0, 5, 0 );

        // we want to take in account now what was already mentioned in Lesson3:
        // forces must be applied for each physics step if you want a constant force applied
        // thus we create an input handler that gets invoked each physics step
        physicsStepInputHandler = new InputHandler();
        getPhysicsSpace().addToUpdateCallbacks( new PhysicsUpdateCallback() {
            public void beforeStep( PhysicsSpace space, float time ) {
                physicsStepInputHandler.update( time );
            }
            public void afterStep( PhysicsSpace space, float time ) {

            }
        } );

        // now we add an input action to move the sphere while a key is pressed
        physicsStepInputHandler.addAction( new MyInputAction( new Vector3f( 7, 0, 0 ) ),
                InputHandler.DEVICE_KEYBOARD, KeyInput.KEY_HOME, InputHandler.AXIS_NONE, true );
        // the action is defined below
        // we register it to be invoked every update of the input handler while the HOME key (POS1) is down
        //                              ( last parameter value is 'true' )
        // note: as the used input handler gets updated each physics step the force is framerate independent -
        //       we can't use the normal input handler here!

        // register an action for the other direction, too
        physicsStepInputHandler.addAction( new MyInputAction( new Vector3f( -7, 0, 0 ) ),
                InputHandler.DEVICE_KEYBOARD, KeyInput.KEY_END, InputHandler.AXIS_NONE, true );

        // again we have created only physics - activate physics debug mode to see something
        showPhysics = true;
    }

    /**
     * An action that get's invoked while a key is down.
     */
    private class MyInputAction extends InputAction {
        private final Vector3f direction;
        private final Vector3f appliedForce = new Vector3f();

        /**
         * The action get the node it should move and the direction it should move in.
         *
         * @param direction force that should be applied on each invocation of the action
         */
        public MyInputAction( Vector3f direction ) {
            // simply remember in member variables
            this.direction = direction;
        }

        /**
         * This method gets invoked upon key event
         *
         * @param evt more data about the event (we don't need it)
         */
        public void performAction( InputActionEvent evt ) {
            appliedForce.set( direction ).multLocal( evt.getTime() );
            // the really important line: apply a force to the moved node
            dynamicNode.addForce( direction );
        }
    }

    @Override
    protected void simpleUpdate() {
        // put the sphere back on the floor when it fell down
        if ( dynamicNode.getWorldTranslation().y < -20 ) {
            // ok it has definately fallen off the floor
            // clear speed and forces
            dynamicNode.clearDynamics();
            // then put it over the floor again
            dynamicNode.getLocalTranslation().set( 0, 5, 0 );
        }
    }

    /**
     * The main method to allow starting this class as application.
     *
     * @param args command line arguments
     */
    public static void main( String[] args ) {
        LoggingSystem.getLogger().setLevel( Level.WARNING ); // to see the important stuff
        new Lesson5().start();
    }
}

/*
 * $log$
 */

