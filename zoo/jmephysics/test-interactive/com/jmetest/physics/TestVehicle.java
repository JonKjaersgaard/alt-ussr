/*Copyright*/
package com.jmetest.physics;

import java.util.logging.Level;

import com.jme.input.InputHandler;
import com.jme.input.KeyInput;
import com.jme.input.action.InputAction;
import com.jme.input.action.InputActionEvent;
import com.jme.math.Vector3f;
import com.jme.util.LoggingSystem;
import com.jmex.physics.DynamicPhysicsNode;
import com.jmex.physics.Joint;
import com.jmex.physics.JointAxis;
import com.jmex.physics.StaticPhysicsNode;
import com.jmex.physics.material.Material;
import com.jmex.physics.util.SimplePhysicsGame;

/**
 * @author Irrisor
 */
public class TestVehicle extends SimplePhysicsGame {
    protected void simpleInitGame() {
        createFloor();

        final DynamicPhysicsNode chassis = getPhysicsSpace().createDynamicNode();
        chassis.createBox( "chassis" );
        chassis.setLocalScale( new Vector3f( 1, 0.1f, 1 ) );
        rootNode.attachChild( chassis );

        for ( int i = 0; i < 4; i++ ) {
            DynamicPhysicsNode tire = getPhysicsSpace().createDynamicNode();
            tire.createCapsule( "tire geom" );
            tire.setMaterial( Material.RUBBER );
            tire.setLocalScale( 0.3f );
            tire.getLocalTranslation().set( ( 0.5f - ( i & 1 ) ), 0, ( 1 - ( i & 2 ) ) * 0.5f );
            tire.computeMass();
            tire.setMass( 100 );
            rootNode.attachChild( tire );

            Joint joint = getPhysicsSpace().createJoint();
            joint.attach( chassis, tire );
            joint.setAnchor( tire.getLocalTranslation() );
            final JointAxis axis1 = joint.createRotationalAxis();
            axis1.setDirection( new Vector3f( 0, 1, 0 ) );
            axis1.setPositionMinimum( -0.5f );
            axis1.setPositionMaximum( 0.5f );
            axis1.setAvailableAcceleration( 100 );
            axis1.setDesiredVelocity( 0 );
            final JointAxis axis2 = joint.createRotationalAxis();
            axis2.setDirection( new Vector3f( 0, 0, 1 ) );
            axis2.setAvailableAcceleration( 100 );
            axis2.setRelativeToSecondObject( true );

            if ( ( i & 1 ) == 0 ) {
                input.addAction( new SteerAction( axis1, 10 ), InputHandler.DEVICE_KEYBOARD, KeyInput.KEY_NUMPAD6, InputHandler.AXIS_NONE, false );
                input.addAction( new SteerAction( axis1, -10 ), InputHandler.DEVICE_KEYBOARD, KeyInput.KEY_NUMPAD4, InputHandler.AXIS_NONE, false );
            }
            input.addAction( new SteerAction( axis2, 5 ), InputHandler.DEVICE_KEYBOARD, KeyInput.KEY_NUMPAD8, InputHandler.AXIS_NONE, false );
            input.addAction( new SteerAction( axis2, -5 ), InputHandler.DEVICE_KEYBOARD, KeyInput.KEY_NUMPAD2, InputHandler.AXIS_NONE, false );
        }

//        getPhysicsSpace().createJoint().attach( chassis ); // fix chassis to world for testing

        showPhysics = true;
    }

    private void createFloor() {
        final StaticPhysicsNode staticNode = getPhysicsSpace().createStaticNode();
        staticNode.createBox( "box physics" );
        staticNode.setLocalScale( new Vector3f( 10, 0.2f, 10 ) );
        staticNode.getLocalTranslation().set( 0, -2, 0 );
        staticNode.getLocalScale().multLocal( 1.2f );
        staticNode.setMaterial( Material.CONCRETE );
        rootNode.attachChild( staticNode );
    }

    public static void main( String[] args ) {
        LoggingSystem.getLogger().setLevel( Level.WARNING );
        new TestVehicle().start();
    }

    private static class SteerAction extends InputAction {
        private final JointAxis axis1;
        private float velocity;

        public SteerAction( JointAxis axis1, float velocity ) {
            this.axis1 = axis1;
            this.velocity = velocity;
        }

        public void performAction( InputActionEvent evt ) {
            if ( evt.getTriggerPressed() ) {
                axis1.setDesiredVelocity( velocity );
            }
            else {
                axis1.setDesiredVelocity( 0 );
            }
        }
    }
}

/*
 * $log$
 */

