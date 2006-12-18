/*Copyright*/
package com.jmetest.physics;

import java.util.logging.Level;

import com.jme.input.InputHandler;
import com.jme.input.KeyInput;
import com.jme.input.action.InputAction;
import com.jme.input.action.InputActionEvent;
import com.jme.input.util.SyntheticButton;
import com.jme.math.Vector3f;
import com.jme.scene.TriMesh;
import com.jme.scene.shape.Box;
import com.jme.scene.shape.Sphere;
import com.jme.util.LoggingSystem;
import com.jmex.physics.DynamicPhysicsNode;
import com.jmex.physics.PhysicsCollisionGeometry;
import com.jmex.physics.StaticPhysicsNode;
import com.jmex.physics.contact.ContactInfo;
import com.jmex.physics.geometry.PhysicsMesh;
import com.jmex.physics.geometry.PhysicsSphere;
import com.jmex.physics.util.SimplePhysicsGame;

/**
 * @author Irrisor
 */
public class TestBasicRepresentation extends SimplePhysicsGame {
    protected void simpleInitGame() {
//        rootNode.getLocalRotation().fromAngleNormalAxis( -0.1f, new Vector3f( 0, 0, 1 ) );

        boolean useTriMesh = false;

        final StaticPhysicsNode staticNode = getPhysicsSpace().createStaticNode();
        final PhysicsCollisionGeometry floorGeom;
        if ( !useTriMesh ) {
            floorGeom = staticNode.createBox( "box physics" );
            staticNode.setLocalScale( new Vector3f( 30, 1, 30 ) );
        }
        else {
            TriMesh trimesh = new Box( "trimesh", new Vector3f(), 15, 0.5f, 15 );
            PhysicsMesh mesh = staticNode.createMesh( "mesh" );
            mesh.copyFrom( trimesh );
            floorGeom = mesh;
            staticNode.attachChild( trimesh );
        }

        staticNode.getLocalTranslation().set( 0, -5, 0 );
//        staticNode.getLocalScale().multLocal( 1.2f );
        rootNode.attachChild( staticNode );

        final DynamicPhysicsNode dynamicNode = getPhysicsSpace().createDynamicNode();
        if ( !useTriMesh ) {
            PhysicsSphere sphere = dynamicNode.createSphere( "sphere physics" );
            sphere.setLocalScale( 2 );
            sphere.getLocalTranslation().set( -1, 0, 0 );
        }
        else {
            Sphere meshSphere = new Sphere( "meshsphere", 10, 10, 2 );
            PhysicsMesh sphere = dynamicNode.createMesh( "sphere mesh" );
            sphere.getLocalTranslation().set( -1, 0, 0 );
            meshSphere.getLocalTranslation().set( -1, 0, 0 );
            sphere.copyFrom( meshSphere );
            dynamicNode.attachChild( meshSphere );
        }
        final PhysicsSphere sphere2 = dynamicNode.createSphere( "sphere physics" );
        sphere2.getLocalTranslation().set( 0.3f, 0, 0 );
        dynamicNode.detachChild( sphere2 );
//        dynamicNode.setLocalScale( 0.8f );
        rootNode.attachChild( dynamicNode );
        dynamicNode.computeMass();
//        dynamicNode.setCenterOfMass( new Vector3f( -1, 0, 0 ) );

        final DynamicPhysicsNode dynamicNode3 = getPhysicsSpace().createDynamicNode();
        if ( !useTriMesh ) {
            PhysicsSphere sphere3 = dynamicNode3.createSphere( "sphere physics" );
            sphere3.setLocalScale( 2 );
        }
        else {
            Sphere meshSphere3 = new Sphere( "meshsphere", 10, 10, 2 );
            PhysicsMesh sphere3 = dynamicNode3.createMesh( "sphere mesh" );
            sphere3.copyFrom( meshSphere3 );
            dynamicNode3.attachChild( meshSphere3 );
        }

        rootNode.attachChild( dynamicNode3 );
        dynamicNode3.computeMass();

        final DynamicPhysicsNode cylinderNode = getPhysicsSpace().createDynamicNode();
        cylinderNode.createCylinder( "cylinder" );
        rootNode.attachChild( cylinderNode );
        cylinderNode.setLocalScale( 2 );

        showPhysics = true;

        final InputAction resetAction = new InputAction() {
            public void performAction( InputActionEvent evt ) {
                if ( evt == null || evt.getTriggerPressed() ) {
                    dynamicNode.getLocalTranslation().set( 0, 3, 0 );
                    dynamicNode.getLocalRotation().set( 0, 0, 0, 1 );
                    dynamicNode.clearDynamics();

                    dynamicNode3.getLocalTranslation().set( 0, -2.5f, 0 );
                    dynamicNode3.getLocalRotation().set( 0, 0, 0, 1 );
                    dynamicNode3.clearDynamics();

                    cylinderNode.getLocalTranslation().set( 2, 2, 4 );
                    cylinderNode.getLocalRotation().fromAngleNormalAxis( 0.5f, new Vector3f( 1, 0, 0 ) );
                    cylinderNode.clearDynamics();
                }
            }
        };
        input.addAction( resetAction, InputHandler.DEVICE_KEYBOARD, KeyInput.KEY_R, InputHandler.AXIS_NONE, false );
        resetAction.performAction( null );

        InputAction detachAction = new InputAction() {
            public void performAction( InputActionEvent evt ) {
                if ( sphere2.getParent() != null ) {
                    dynamicNode.detachChild( sphere2 );
                }
                else {
                    dynamicNode.attachChild( sphere2 );
                }
            }
        };
        input.addAction( detachAction, InputHandler.DEVICE_KEYBOARD, KeyInput.KEY_INSERT, InputHandler.AXIS_NONE, false );

        InputAction removeAction = new InputAction() {
            public void performAction( InputActionEvent evt ) {
                staticNode.setActive( !staticNode.isActive() );
            }
        };
        input.addAction( removeAction, InputHandler.DEVICE_KEYBOARD, KeyInput.KEY_DELETE, InputHandler.AXIS_NONE, false );

        SyntheticButton collisionEventHandler = getPhysicsSpace().getCollisionEventHandler();
        input.addAction( new InputAction() {
            public void performAction( InputActionEvent evt ) {
                ContactInfo info = (ContactInfo) evt.getTriggerData();
                if ( info.getGeometry1() != floorGeom && info.getGeometry2() != floorGeom ) {
                    System.out.println( evt.getTriggerData() );
                }
            }
        }, collisionEventHandler.getDeviceName(), collisionEventHandler.getIndex(), InputHandler.AXIS_NONE, false );
    }

    public static void main( String[] args ) {
        LoggingSystem.getLogger().setLevel( Level.WARNING );
        new TestBasicRepresentation().start();
    }
}

/*
 * $log$
 */

