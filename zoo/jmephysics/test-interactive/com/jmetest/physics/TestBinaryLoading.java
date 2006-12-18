/*Copyright*/
package com.jmetest.physics;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.logging.Level;

import com.jme.input.MouseInput;
import com.jme.scene.Node;
import com.jme.util.LoggingSystem;
import com.jme.util.export.binary.BinaryExporter;
import com.jme.util.export.binary.BinaryImporter;
import com.jmex.physics.PhysicsSpace;

public class TestBinaryLoading extends TestBasicJoints {
    protected void simpleInitGame() {
    		super.simpleInitGame();
        MouseInput.get().setCursorVisible( true );
    		
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
   		try {
			BinaryExporter.getInstance().save( rootNode, baos );
	        PhysicsSpace space = PhysicsSpace.create();
       		setPhysicsSpace( space );

        	space.setupBinaryClassLoader( BinaryImporter.getInstance() );
        	rootNode = (Node) BinaryImporter.getInstance().load( new ByteArrayInputStream( baos.toByteArray() ) );
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

	public static void main( String[] args ) {
        LoggingSystem.getLogger().setLevel( Level.WARNING );
        new TestBinaryLoading().start();
    }
}

/*
 * $log$
 */
