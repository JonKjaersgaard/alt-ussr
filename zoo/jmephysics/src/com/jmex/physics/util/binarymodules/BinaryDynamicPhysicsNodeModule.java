package com.jmex.physics.util.binarymodules;

import com.jme.util.export.InputCapsule;
import com.jme.util.export.Savable;
import com.jme.util.export.binary.BinaryLoaderModule;
import com.jmex.physics.DynamicPhysicsNode;
import com.jmex.physics.PhysicsSpace;

public class BinaryDynamicPhysicsNodeModule implements BinaryLoaderModule {
	private PhysicsSpace physicsSpace;
	
	public BinaryDynamicPhysicsNodeModule( PhysicsSpace physicsSpace ) {
		this.physicsSpace = physicsSpace;
	}

	public String getKey() {
        return DynamicPhysicsNode.class.getName();
	}

	public Savable load(InputCapsule inputCapsule) {
		return physicsSpace.createDynamicNode();
	}
}
