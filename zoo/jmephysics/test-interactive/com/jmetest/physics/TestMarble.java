package com.jmetest.physics;

import java.util.logging.Level;

import com.jme.image.Texture;
import com.jme.input.InputHandler;
import com.jme.input.KeyInput;
import com.jme.input.action.InputAction;
import com.jme.input.action.InputActionEvent;
import com.jme.math.Vector3f;
import com.jme.scene.shape.Box;
import com.jme.scene.shape.Sphere;
import com.jme.scene.state.TextureState;
import com.jme.util.LoggingSystem;
import com.jme.util.TextureManager;
import com.jmex.physics.DynamicPhysicsNode;
import com.jmex.physics.Joint;
import com.jmex.physics.RotationalJointAxis;
import com.jmex.physics.util.SimplePhysicsGame;
import jmetest.TestChooser;

/**
 * A test showing a floor, controlled by keys H, U, J, K, with a marble rolling on it.
 */
public class TestMarble extends SimplePhysicsGame {

    protected void simpleInitGame() {
        // create a dynamic node with a box geometry and generate collision geometry
        final DynamicPhysicsNode floor = getPhysicsSpace().createDynamicNode();
        rootNode.attachChild( floor );
        final Box visualFloor = new Box( "floor", new Vector3f(), 10, 0.25f, 10 );
        floor.attachChild( visualFloor );
        visualFloor.getLocalTranslation().set( 0, 0, 0 );
        floor.generatePhysicsGeometry();
        floor.setAffectedByGravity( false );
        final TextureState wallTextureState = display.getRenderer().createTextureState();
        wallTextureState.setTexture( TextureManager.loadTexture( TestChooser.class.getResource( "data/texture/wall.jpg" ),
                Texture.FM_LINEAR, Texture.FM_LINEAR ) );
        visualFloor.setRenderState( wallTextureState );

        // create another dynamic node for the marble - a simple sphere
        DynamicPhysicsNode dynamicNode = getPhysicsSpace().createDynamicNode();
        rootNode.attachChild( dynamicNode );
        final Sphere visualFallingBox = new Sphere( "falling box", new Vector3f(), 16, 16, 1 );
        dynamicNode.attachChild( visualFallingBox );
        dynamicNode.generatePhysicsGeometry();
        final DynamicPhysicsNode ball = dynamicNode;
        ball.getLocalTranslation().set( 0, 1, 0 );
        final TextureState textureState = display.getRenderer().createTextureState();
        textureState.setTexture( TextureManager.loadTexture( TestChooser.class.getResource( "data/texture/water.png" ),
                Texture.FM_LINEAR, Texture.FM_LINEAR ) );
        ball.setRenderState( textureState );

        // now create a joint for fastening and controlling the floor
        final Joint joint = getPhysicsSpace().createJoint();
        // the joint gets two degrees of freedom:
        // rotation around the z-axis
        final RotationalJointAxis rotAxisZ = joint.createRotationalAxis();
        rotAxisZ.setDirection( new Vector3f( 0, 0, 1 ) );
        rotAxisZ.setAvailableAcceleration( 100 );
        // and rotation around the x-axis
        final RotationalJointAxis rotAxisX = joint.createRotationalAxis();
        rotAxisX.setDirection( new Vector3f( 1, 0, 0 ) );
        rotAxisX.setRelativeToSecondObject( true );
        rotAxisX.setAvailableAcceleration( 100 );
        // then attach it to the floor
        joint.attach( floor );

        // now set up input for rotating around the joint axes
        input.addAction( new RotateAxisAction( rotAxisZ, 1 ),
                InputHandler.DEVICE_KEYBOARD, KeyInput.KEY_H, InputHandler.AXIS_NONE, false );
        input.addAction( new RotateAxisAction( rotAxisZ, -1 ),
                InputHandler.DEVICE_KEYBOARD, KeyInput.KEY_K, InputHandler.AXIS_NONE, false );
        input.addAction( new RotateAxisAction( rotAxisX, 1 ),
                InputHandler.DEVICE_KEYBOARD, KeyInput.KEY_J, InputHandler.AXIS_NONE, false );
        input.addAction( new RotateAxisAction( rotAxisX, -1 ),
                InputHandler.DEVICE_KEYBOARD, KeyInput.KEY_U, InputHandler.AXIS_NONE, false );

        // finally adjust the cam a bit
        cam.getLocation().y += 10;
        cam.lookAt( new Vector3f(), new Vector3f( 0, 0, -1 ) );
        cameraInputHandler.setEnabled( false );
    }

    /**
     * Action for rotating via buttons/keys.
     */
    private static class RotateAxisAction extends InputAction {
        private final RotationalJointAxis rotAxis;
        private final int velocity;

        public RotateAxisAction( RotationalJointAxis rotAxis, int velocity ) {
            this.rotAxis = rotAxis;
            this.velocity = velocity;
        }

        public void performAction( InputActionEvent evt ) {
            if ( evt.getTriggerPressed() ) {
                // when the button/key is pressed we set the velocity around our axis to a high value
                rotAxis.setDesiredVelocity( velocity );
            } else {
                // when the button/key is released the velocity should get back to 0
                rotAxis.setDesiredVelocity( 0 );
            }
        }
    }

    public static void main( String[] args ) {
        LoggingSystem.getLogger().setLevel( Level.WARNING ); // to see the important stuff
        new TestMarble().start();
    }
}