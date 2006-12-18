package com.jmex.physics;

import com.jme.math.FastMath;
import com.jme.math.Vector3f;
import com.jme.scene.Node;
import com.jmex.physics.contact.ContactHandlingDetails;
import com.jmex.physics.contact.MutableContactInfo;
import com.jmex.physics.contact.PendingContact;
import com.jmex.physics.geometry.PhysicsBox;
import com.jmex.physics.geometry.PhysicsSphere;
import com.jmex.physics.material.Material;
import junit.framework.TestCase;

/**
 * @author Irrisor
 */
public class PhysicsNodeTest extends TestCase {
    public void testMaterial() {
        Material a = new Material();
        Material b = new Material();

        ContactHandlingDetails details = a.getContactHandlingDetails( b );
        assertNull( "unspecified details", details );

        MutableContactInfo info = new MutableContactInfo();
        info.setMu( 0.5f );
        a.putContactHandlingDetails( b, info );

        details = a.getContactHandlingDetails( b );
        assertNotNull( "specified details not found", details );
        assertEquals( "mu", 0.5f, details.getMu(), 0.001f );

        info.setMu( 0.9f );
        details = a.getContactHandlingDetails( b );
        assertEquals( "mu should not change afterwards", 0.5f, details.getMu(), 0.001f );

        a.putContactHandlingDetails( null, info );
        details = a.getContactHandlingDetails( new Material() );
        assertNotNull( "default details not found", details );
        assertEquals( "mu should not change afterwards", 0.9f, details.getMu(), 0.001f );
    }

    public void testMassComputation() {
        PhysicsSpace physicsSpace = PhysicsSpace.create();
        DynamicPhysicsNode node = physicsSpace.createDynamicNode();
        PhysicsSphere sphere = node.createSphere( "test" );
        sphere.setLocalScale( 2 );

        node.updateGeometricState( 0, true );

        assertEquals( "volue", FastMath.PI * 4, sphere.getVolume(), 0.01f );

        Material material = new Material();
        material.setDensity( 2.5f );
        sphere.setMaterial( material );

        node.computeMass();

        assertEquals( "mass", FastMath.PI * 4 * 2.5f, node.getMass(), 0.01f );
    }

    public void testGravity() {
        PhysicsSpace physicsSpace = PhysicsSpace.create();
        DynamicPhysicsNode node = physicsSpace.createDynamicNode();
        node.createSphere( "test" );

        physicsSpace.setDirectionalGravity( new Vector3f( 0, 0, 0 ) );

        node.updateGeometricState( 0, true );
        physicsSpace.update( 1 );
        assertEquals( "velocity after time 1 no gravity", 0, node.getLinearVelocity( null ).y, 0.01f );

        physicsSpace.setDirectionalGravity( new Vector3f( 0, -10, 0 ) );

        node.updateGeometricState( 0, true );
        physicsSpace.update( 1 );
        assertEquals( "velocity after time 2 - time 1 gravity", -10, node.getLinearVelocity( null ).y, 0.01f );

        physicsSpace.setDirectionalGravity( new Vector3f( 0, 0, 0 ) );

        node.updateGeometricState( 0, true );
        physicsSpace.update( 1 );
        assertEquals( "velocity after time 3 - gravity off again", -10, node.getLinearVelocity( null ).y, 0.01f );
    }

    public void testCollide() {
        Node rootNode = new Node( "root" );

        PhysicsSpace physicsSpace = PhysicsSpace.create();
        DynamicPhysicsNode ball = physicsSpace.createDynamicNode();
        ball.createSphere( "test" );
        rootNode.attachChild( ball );

        StaticPhysicsNode fixed = physicsSpace.createStaticNode();
        fixed.createBox( null );
        fixed.getLocalTranslation().set( 0, -3, 0 );
        rootNode.attachChild( fixed );

        physicsSpace.setDirectionalGravity( new Vector3f( 0, -10, 0 ) );

        rootNode.updateGeometricState( 0, true );
        physicsSpace.update( 6 );

        assertEquals( "position after time 5", -1.5, ball.getLocalTranslation().y, 0.01f );
    }

    public void testMaterialBounce() {
        Node rootNode = new Node( "root" );

        PhysicsSpace physicsSpace = PhysicsSpace.create();
        final DynamicPhysicsNode ball = physicsSpace.createDynamicNode();
        final PhysicsSphere ballSphere = ball.createSphere( "test" );
        rootNode.attachChild( ball );

        ball.setMaterial( new Material( "Shock absorber" ) );
        MutableContactInfo info = new MutableContactInfo();
        info.setMu( 100f );
        info.setBounce( Float.NaN );
        ball.getMaterial().putContactHandlingDetails( physicsSpace.getDefaultMaterial(), info );

        final StaticPhysicsNode fixed = physicsSpace.createStaticNode();
        final PhysicsBox fixedBox = fixed.createBox( null );
        fixed.getLocalTranslation().set( 0, -3, 0 );
        rootNode.attachChild( fixed );

        // simulate a contact to see if material is applied
        PendingContact testContact = new PendingContact() {
            public Vector3f getContactNormal( Vector3f store ) {
                return null;
            }

            public Vector3f getContactPosition( Vector3f store ) {
                return null;
            }

            public PhysicsCollisionGeometry getGeometry1() {
                return ballSphere;
            }

            public PhysicsCollisionGeometry getGeometry2() {
                return fixedBox;
            }

            public PhysicsNode getNode1() {
                return ball;
            }

            public PhysicsNode getNode2() {
                return fixed;
            }

            public float getPenetrationDepth() {
                return 0;
            }

            public void getDefaultFrictionDirections( Vector3f primaryStore, Vector3f secondaryStore ) {
                primaryStore.set( Float.NaN, Float.NaN, Float.NaN );
                secondaryStore.set( Float.NaN, Float.NaN, Float.NaN );
            }

            public float getTime() {
                return 0;
            }
        };
        physicsSpace.adjustContact( testContact );
        assertEquals( "mu", 100, testContact.getMu(), 0.001f );
        assertTrue( "bounce", Float.isNaN( testContact.getBounce() ) );

        physicsSpace.setDirectionalGravity( new Vector3f( 0, -10, 0 ) );

        rootNode.updateGeometricState( 0, true );
        physicsSpace.update( 0.8f );

        assertEquals( "position after time 5", -1.5, ball.getLocalTranslation().y, 0.01f );

        ball.getLocalTranslation().set( 0, 0, 0 );
        ball.clearDynamics();
        ball.setMaterial( physicsSpace.getDefaultMaterial() );
        rootNode.updateGeometricState( 0, true );
        physicsSpace.update( 0.8f );

        assertTrue( "position after time 5 - ball should have bounced", ball.getLocalTranslation().y > -1.5 );
    }

    public void testSurfaceMotion() {
        Node rootNode = new Node( "root" );

        PhysicsSpace physicsSpace = PhysicsSpace.create();
        DynamicPhysicsNode ball = physicsSpace.createDynamicNode();
        ball.createSphere( "test" );
        rootNode.attachChild( ball );

        StaticPhysicsNode fixed = physicsSpace.createStaticNode();
        fixed.createBox( null );
        fixed.getLocalTranslation().set( 0, -3, 0 );
        rootNode.attachChild( fixed );

        physicsSpace.setDirectionalGravity( new Vector3f( 0, -10, 0 ) );

        rootNode.updateGeometricState( 0, true );
        physicsSpace.update( 6 );

        assertEquals( "position after time 5: x", 0, ball.getLocalTranslation().x, 0.01f );
        assertEquals( "position after time 5: y", -1.5, ball.getLocalTranslation().y, 0.01f );
        assertEquals( "position after time 5: z", 0, ball.getLocalTranslation().z, 0.01f );

        Material material = new Material( "conveyor" );
        material.setSurfaceMotion( new Vector3f( 1, 0, 0 ) );
        fixed.setMaterial( material );

        rootNode.updateGeometricState( 0, true );
        physicsSpace.update( 1 );

        assertEquals( "position after time 5: x", 0.5f, ball.getLocalTranslation().x, 0.01f );
        assertEquals( "position after time 5: y", -1.5, ball.getLocalTranslation().y, 0.01f );
        assertEquals( "position after time 5: z", 0, ball.getLocalTranslation().z, 0.01f );

        fixed.getLocalRotation().fromAngleNormalAxis( FastMath.PI / 2, new Vector3f( 0, 1, 0 ) );

        rootNode.updateGeometricState( 0, true );
        physicsSpace.update( 1 );

        assertEquals( "position after time 5: x", 0.5f, ball.getLocalTranslation().x, 0.01f );
        assertEquals( "position after time 5: y", -1.5, ball.getLocalTranslation().y, 0.01f );
        assertEquals( "position after time 5: z", 0.5f, ball.getLocalTranslation().z, 0.01f );
    }
}