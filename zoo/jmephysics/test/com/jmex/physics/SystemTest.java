package com.jmex.physics;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import com.jme.scene.Node;
import com.jme.util.export.binary.BinaryExporter;
import com.jme.util.export.binary.BinaryImporter;

/**
 * @author Irrisor
 */
public class SystemTest extends junit.framework.TestCase {
    public void testCreate() {
        PhysicsSpace physicsSpace = PhysicsSpace.create();
        assertNotNull( "physics space should not be null", physicsSpace );

        DynamicPhysicsNode dynamicNode = physicsSpace.createDynamicNode();
        assertNotNull( "create node should not return null", dynamicNode );

        StaticPhysicsNode staticNode = physicsSpace.createStaticNode();
        assertNotNull( "create node should not return null", staticNode );

        Joint joint = physicsSpace.createJoint();
        assertNotNull( "create joint should not return null", joint );
    }

    public void testBinaryLoading() throws IOException {
    	PhysicsSpace physicsSpace = PhysicsSpace.create();
    	physicsSpace.setupBinaryClassLoader( BinaryImporter.getInstance() );

        Node parent = new Node();
        DynamicPhysicsNode dynamicNode = physicsSpace.createDynamicNode();
        dynamicNode.createSphere( null );
        dynamicNode.createBox( null );
        dynamicNode.createCylinder( null );
        dynamicNode.createCapsule( null );
//        dynamicNode.createMesh( null );
        parent.attachChild( dynamicNode );
        dynamicNode.setMass( 10.0f );
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        BinaryExporter.getInstance().save( dynamicNode, baos );
        DynamicPhysicsNode loadedDynamicNode = (DynamicPhysicsNode) BinaryImporter.getInstance().load( new ByteArrayInputStream( baos.toByteArray() ) );
        assertNotNull( "loaded node should not be null", loadedDynamicNode );
        assertNull( "loaded node's parent should be null", loadedDynamicNode.getParent() );
        assertEquals( "loaded node should have 4 children", 4, loadedDynamicNode.getChildren().size() );
        assertEquals( "loaded node should have mass 10", 10.0f, loadedDynamicNode.getMass() );

        StaticPhysicsNode staticNode = physicsSpace.createStaticNode();
		baos = new ByteArrayOutputStream();
        BinaryExporter.getInstance().save( staticNode, baos );
        StaticPhysicsNode loadedStaticNode = (StaticPhysicsNode) BinaryImporter.getInstance().load( new ByteArrayInputStream( baos.toByteArray() ) );
        assertNotNull( "loaded node should not be null", loadedStaticNode );

        Joint joint = physicsSpace.createJoint();
        joint.createRotationalAxis();
        joint.createTranslationalAxis();
		baos = new ByteArrayOutputStream();
        BinaryExporter.getInstance().save( joint, baos );
        Joint loadedJoint = (Joint) BinaryImporter.getInstance().load( new ByteArrayInputStream( baos.toByteArray() ) );
        assertNotNull( "loaded joint should not be null", loadedJoint );
        assertEquals( "loaded joint should have 2 axes", 2, loadedJoint.getAxes().size() );

    }
}

/*
* $log$
*/