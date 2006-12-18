package com.jmex.physics.util.binarymodules;

import com.jme.util.export.InputCapsule;
import com.jme.util.export.Savable;
import com.jme.util.export.binary.BinaryLoaderModule;
import com.jmex.physics.Joint;
import com.jmex.physics.PhysicsSpace;

public class BinaryJointModule implements BinaryLoaderModule {
	private PhysicsSpace physicsSpace;
	
	public BinaryJointModule( PhysicsSpace physicsSpace ) {
		this.physicsSpace = physicsSpace;
	}

	public String getKey() {
		return Joint.class.getName();
	}

	public Savable load(InputCapsule inputCapsule) {
		return physicsSpace.createJoint();
	}
}
