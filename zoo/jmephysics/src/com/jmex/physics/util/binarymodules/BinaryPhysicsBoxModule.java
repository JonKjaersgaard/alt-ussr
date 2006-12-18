package com.jmex.physics.util.binarymodules;

import java.io.IOException;

import com.jme.util.export.InputCapsule;
import com.jme.util.export.Savable;
import com.jme.util.export.binary.BinaryLoaderModule;
import com.jmex.physics.PhysicsCollisionGeometry;
import com.jmex.physics.PhysicsNode;
import com.jmex.physics.geometry.PhysicsBox;

public class BinaryPhysicsBoxModule implements BinaryLoaderModule {
	public String getKey() {
        return PhysicsBox.class.getName();
	}

	public Savable load(InputCapsule inputCapsule) throws IOException {
		PhysicsNode parent = PhysicsCollisionGeometry.readPhysicsNodeFromInputCapsule( inputCapsule );
		return parent.createBox( null );
	}
}
