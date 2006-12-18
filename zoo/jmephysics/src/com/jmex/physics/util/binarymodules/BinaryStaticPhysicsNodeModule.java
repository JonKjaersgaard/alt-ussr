package com.jmex.physics.util.binarymodules;

import com.jme.util.export.InputCapsule;
import com.jme.util.export.Savable;
import com.jme.util.export.binary.BinaryLoaderModule;
import com.jmex.physics.PhysicsSpace;
import com.jmex.physics.StaticPhysicsNode;

public class BinaryStaticPhysicsNodeModule implements BinaryLoaderModule {
	private PhysicsSpace physicsSpace;
	
	public BinaryStaticPhysicsNodeModule( PhysicsSpace physicsSpace ) {
		this.physicsSpace = physicsSpace;
	}

	public String getKey() {
		return StaticPhysicsNode.class.getName();
	}

	public Savable load(InputCapsule inputCapsule) {
		return physicsSpace.createStaticNode();
	}
}
